package Biblioteca.InteractiveObjects;

import Biblioteca.BasicObjects.CenaVisivel;
import Biblioteca.BasicObjects.Formas.Texto;
import Biblioteca.Lists.yCircularArray;
import Biblioteca.LogicClasses.Matematicas;
import Biblioteca.OrganizadoresDeNodos.Caixa;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class BarraDeslisante extends CenaVisivel {
    private Path path;
    public Caixa slider;
    public Texto text;
    
    private PathTransition path_animation;
    private yCircularArray<Point2D> PATH_POINT_LIST;
    private int FRAMES;
    private boolean CYCLIC;
    
    private double MIN;
    private double MAX;
    private DoubleProperty CURRENT_VALUE;
    private int CURRENT_POSITION_INDEX;
    
    private double mouseX;
    private double mouseY;
    private double tINITx;
    private double tINITy;
    
    public boolean TEXT_AS_PERCENTAGE = true;

    public BarraDeslisante(double endX, double endY, double stroke_width, Caixa slider, int FRAMES, double MIN, double MAX, double INITIAL_VALUE) {
        
    }

    public BarraDeslisante(/*Path*/Shape path, Caixa slider, int FRAMES, double MIN, double MAX, boolean cyclic) {
        //this.path = path;
        this.slider = slider;
        this.FRAMES = FRAMES;
        this.MIN = MIN;
        this.MAX = MAX;
        this.CURRENT_POSITION_INDEX = 0;
        this.CYCLIC = cyclic;
        CURRENT_VALUE = new SimpleDoubleProperty(0);
        
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
        PATH_POINT_LIST = new yCircularArray(FRAMES);
        savePositions(path_animation);
        
        slider.setOnMousePressed((event) -> {
            // Store initial position
            tINITx = slider.yGetTranslateX(0.5);
            tINITy = slider.yGetTranslateY(0.5);
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });
        
        slider.setOnMouseDragged((event) -> {
            double dragX = event.getSceneX() - mouseX;
            double dragY = event.getSceneY() - mouseY;
            
            double newXPosition = tINITx + dragX;
            double newYPosition = tINITy + dragY;
            
            boolean sucess;
            
            do{//acho que o motivo do lag vem desse do while que nao deveria estar aqui no mouse dragged...ou pcausa do jeito...se fizesse usando força vetorial podia n da
                int index = CURRENT_POSITION_INDEX;

                if(CYCLIC)
                    sucess = nextFrame(index - 1, index + 1, newXPosition, newYPosition);
                else
                    sucess = nextFrame(index - 1 <= 0 ? 0 : index - 1, index + 1 > FRAMES-1 ? FRAMES-1 : index + 1, newXPosition, newYPosition);

            }while(sucess);
        });
        
        text = new Texto("ab");//ver do problemaaquiAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa
        setValue(MIN);
        
        getChildren().addAll(path, this.slider);
    }
    
    public void displayValue(boolean asPercentage){
        this.TEXT_AS_PERCENTAGE = asPercentage;

        getChildren().add(text);
    }
    
    public void unDisplayValue(){
        getChildren().remove(text);
    }
    
    /**
     * Sets a new value to the CURRENT_VALUE of the bar, also changing the slider position.
     * If the value > MAX then value = MAX
     * If the value < 0 then value = 0
     * @param value The new current value of the slider.
     */
    public void setValue(double value){
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
        CURRENT_POSITION_INDEX = Math.round((float) ((FRAMES-1) * getPercentage()));
        
        path_animation.jumpTo(Duration.seconds(PATH_POINT_LIST.get_real_index(CURRENT_POSITION_INDEX)));
        syncTextValue();
    }
    
    /**
     * Sets a new value based on a percentage and MIN & MAX variables
     * @param percentage Number from 0 to 1 coresponding the percentage (0 - begining, 1 - end)
     * @see #setValue(double) 
     */
    public void setValueByPercentage(double percentage){
        setValue(percentage * (MAX - MIN) + MIN);
    }
    
    /**
     * @return The current value of the sliding bar.
     */
    public double getValue(){
        return CURRENT_VALUE.get();
    }
    
    public DoubleProperty valueProperty(){
        return CURRENT_VALUE;
    }
    
    /**
     * @return The percentage of the sliding bar from 0 (begining) to 1 (end).
     */
    public double getPercentage(){
        return MAX != MIN ? (CURRENT_VALUE.get() - MIN) / (MAX - MIN) : 0;
    }

    /**
     * Save the position of the slider for every second of the animation in
     * a list.
     */
    private void savePositions(PathTransition path_animation) {
        if (PATH_POINT_LIST == null)
            return;

        for (int i=0; i<(int)FRAMES; i++) {
            path_animation.jumpTo(Duration.seconds(i));
            PATH_POINT_LIST.set(i, new Point2D(slider.yGetTranslateX(0.5), slider.yGetTranslateY(0.5)));
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
            setValueByPercentage(PATH_POINT_LIST.lenght()-1 != 0 ? ((double) PATH_POINT_LIST.get_real_index(index % FRAMES)) / (PATH_POINT_LIST.lenght()-1) : 0);
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
    
    private void syncTextValue(){
        if(TEXT_AS_PERCENTAGE){
            text.ySetText(String.format("%.2f", getPercentage()*100) + "%");
        }else{
            text.ySetText(String.format("%.2f", getValue()));
        }
    }
}