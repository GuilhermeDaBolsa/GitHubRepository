package Biblioteca.OrganizadoresDeNodos;

import Biblioteca.BasicObjects.CenaVisivel;
import Biblioteca.BasicObjects.Formas.Linha;
import Biblioteca.BasicObjects.Formas.Texto;
import Biblioteca.Lists.yCircularArray;
import Biblioteca.LogicClasses.Matematicas;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class BarraDeslisante extends CenaVisivel {
    private Shape path;
    public Caixa slider;
    public Texto text;
    
    private PathTransition path_animation;
    public yCircularArray<Point2D> PATH_POINT_LIST;
    private int FRAMES;
    public boolean CYCLIC;
    
    public double MIN;
    public double MAX;
    public DoubleProperty CURRENT_VALUE;
    private int CURRENT_POSITION_INDEX;
    
    public DoubleProperty sliderXposition;
    public DoubleProperty sliderYposition;
    
    private double mouseX;
    private double mouseY;
    private double sliderX;
    private double sliderY;
    
    public boolean TEXT_AS_PERCENTAGE = true;

    //AO INVEZ DO USUARIO PASSAR O NUMERO DE FRAMES ELE PASSA O INCREMENTO, E BASEADO NISSO TU CALCULA O NUMERO DE FRAMES
    //CADE O TRAILL QUE O SLIDER DEIXA AO PASSAR AAAAAAAAAAAA
    
    public BarraDeslisante(Shape path, Caixa slider, int FRAMES, double MIN, double MAX, boolean cyclic) {
        this.path = path;
        this.slider = slider;
        this.FRAMES = FRAMES;
        this.MIN = MIN;
        this.MAX = MAX;
        this.CURRENT_POSITION_INDEX = 0;
        this.CYCLIC = cyclic;
        CURRENT_VALUE = new SimpleDoubleProperty(0);
        sliderXposition = new SimpleDoubleProperty(0);
        sliderYposition = new SimpleDoubleProperty(0);
        
        ySetSliderPath(path);

        slider.yEvents_Handler.onMousePressed().addHandleble("SliderPress", (event) -> {
            // Store initial position
            sliderX = slider.yGetTranslateX(0);
            sliderY = slider.yGetTranslateY(0);
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });
        
        slider.yEvents_Handler.onMouseDragged().addHandleble("SliderDrag", (event) -> {
            double dragX = event.getSceneX() - mouseX;
            double dragY = event.getSceneY() - mouseY;
            
            double newXPosition = sliderX + dragX;
            double newYPosition = sliderY + dragY;
            
            boolean sucess;
            
            do{//acho que o lag vem desse do while que nao deveria estar aqui no mouse dragged...ou pcausa do jeito...nsei
                int index = CURRENT_POSITION_INDEX;

                if(CYCLIC)
                    sucess = nextFrame(index - 1, index + 1, newXPosition, newYPosition);
                else
                    sucess = nextFrame(index - 1 <= 0 ? 0 : index - 1, index + 1 > FRAMES-1 ? FRAMES-1 : index + 1, newXPosition, newYPosition);

            }while(sucess);
        });
        
        text = new Texto("");
        ySetValue(MIN);
        
        getChildren().addAll(path, this.slider);
    }
    
    public BarraDeslisante(double endX, double endY, double stroke_width, Color line_color, Caixa slider, int FRAMES, double MIN, double MAX) {
        this(new Linha(endX, endY, stroke_width, line_color), slider, FRAMES, MIN, MAX, false);
    }
    
    public void ySetSliderPath(Shape path){
        path_animation = new PathTransition();
        path_animation.setDuration(Duration.seconds(FRAMES));
        path_animation.setPath(path);
        path_animation.setNode(slider);
        path_animation.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        path_animation.setCycleCount(1);
        path_animation.setInterpolator(Interpolator.LINEAR);
        path_animation.playFromStart();
        path_animation.pause();
        path_animation.jumpTo("end");
        
        // Save the positions on the path
        savePositions(path_animation);
    }
    
    /**
     * Shows a text with the current value of the slider (between MIN and MAX based on its position)
     * @param asPercentage If you want the value to be in percentage mark true.
     */
    public void yDisplayValue(boolean asPercentage){
        this.TEXT_AS_PERCENTAGE = asPercentage;

        getChildren().add(text);
    }
    
    /**
     * Stops showing the text with the slider's value.
     */
    public void yUnDisplayValue(){
        getChildren().remove(text);
    }
    
    /**
     * Sets a new value to the CURRENT_VALUE of the bar, also changing the slider position.
     * If the value > MAX then value = MAX
     * If the value < 0 then value = 0
     * @param value The new current value of the slider.
     */
    public void ySetValue(double value){
        if(MAX > MIN){
            if(value < MIN)
                value = MIN;
            else if(value > MAX)
                value = MAX;
        }else{
            if(value < MAX)
                value = MAX;
            else if(value > MIN)
                value = MIN;
        }
        
        CURRENT_VALUE.set(value);
        CURRENT_POSITION_INDEX = Math.round((float) ((FRAMES-1) * yGetPercentage()));
        sliderXposition.set(PATH_POINT_LIST.get(CURRENT_POSITION_INDEX).getX());
        sliderYposition.set(PATH_POINT_LIST.get(CURRENT_POSITION_INDEX).getY());
        
        path_animation.jumpTo(Duration.seconds(PATH_POINT_LIST.get_real_index(CURRENT_POSITION_INDEX)));
        syncTextValue();
    }
    
    /**
     * Sets a new value based on a percentage and MIN & MAX variables
     * @param percentage Number from 0 to 1 coresponding the percentage (0 - begining, 1 - end)
     * @see #ySetValue(double) 
     */
    public void ySetValueByPercentage(double percentage){
        ySetValue(percentage * (MAX - MIN) + MIN);
    }
    
    /**
     * @return The percentage of the sliding bar from 0 (begining) to 1 (end).
     */
    public double yGetPercentage(){
        return MAX != MIN ? (CURRENT_VALUE.get() - MIN) / (MAX - MIN) : 0;
    }
    
    /**
     * Changes the text with the current value of the slider.
     */
    private void syncTextValue(){
        if(TEXT_AS_PERCENTAGE){
            text.ySetText(String.format("%.2f", yGetPercentage()*100) + "%");
        }else{
            text.ySetText(String.format("%.2f", CURRENT_VALUE.get()));
        }
    }

    /**
     * Save the position of the slider for every second of the animation in
     * a list.
     */
    private void savePositions(PathTransition path_animation) {
        PATH_POINT_LIST = new yCircularArray(FRAMES);
        if (PATH_POINT_LIST == null)
            return;

        for (int i=0; i<(int)FRAMES; i++) {
            path_animation.jumpTo(Duration.seconds(i));
            PATH_POINT_LIST.set(i, new Point2D(slider.yGetTranslateX(0), slider.yGetTranslateY(0)));//a little bug: 0 is 0.5 here i dont know why
                                                                                //, na verdade parece que aqui dentro é um pivo e la fora é outro, com 0.5 de diferença entre os 2
        }
    }
    
    /**
     * Chose which frame is the nearest to a point.
     * @param frame_index1 Previous frame.
     * @param frame_index2 Next frame.
     * @param pX X point's position.
     * @param pY Y point's position.
     * @return True if the frame had changed
     */
    private boolean nextFrame(int frame_index1, int frame_index2, double pX, double pY){
        //calculate the distance between the next 2 frames and the mouse using pythagoras
        double distance1 = distanceToNextPoint(frame_index1, pX, pY);
        double distance2 = distanceToNextPoint(frame_index2, pX, pY);
        double minor_distance;
        int index;
        
        //sees whitch distance is the smaller one
        if(distance1 < distance2){
            minor_distance = distance1;
            index = frame_index1;
        }else{
            minor_distance = distance2;
            index = frame_index2;
        }
        
        //if the minor distance is smaller than the distance between the curent frame and the mouse, the smaller one will be the next frame
        if(minor_distance < Matematicas.hypotenuse(pX - PATH_POINT_LIST.get(CURRENT_POSITION_INDEX).getX(), pY - PATH_POINT_LIST.get(CURRENT_POSITION_INDEX).getY())){
            ySetValueByPercentage(PATH_POINT_LIST.lenght()-1 != 0 ? ((double) PATH_POINT_LIST.get_real_index(index % FRAMES)) / (PATH_POINT_LIST.lenght()-1) : 0);
            syncTextValue();
            
            return true;
        }
        return false;
    }
    
    /**
     * @param frame_index Frame index.
     * @param pX X point's position.
     * @param pY Y point's position.
     * @return The distance between a frame and a point using pythagoras.
     */
    private double distanceToNextPoint(int frame_index, double pX, double pY){
        return Matematicas.hypotenuse(pX - PATH_POINT_LIST.get(frame_index).getX(), pY - PATH_POINT_LIST.get(frame_index).getY());
    }
}