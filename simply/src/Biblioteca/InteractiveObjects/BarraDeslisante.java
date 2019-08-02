package Biblioteca.InteractiveObjects;

import Biblioteca.BasicObjects.CenaVisivel;
import Biblioteca.Lists.yCircularArray;
import Biblioteca.LogicClasses.Matematicas;
import Biblioteca.OrganizadoresDeNodos.Caixa;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.geometry.Point2D;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

//FAZER OS ESQUEMAS DO TRAKER E, COM BASE NO PONTO ATUAL DA ANIMAÇÃO + POSICAO DO MOUSE, CALCULAR O VETOR DE FORÇA QUE O MOUSE ESTÁ "APLICANDO" NO ELEMENTO
//E VER SE A FORÇA APLICADA AO ELEMENTO O TRAZ MAIS PERTO DO PROXIMO PONTO.... (n fazer com os esquemas de pegar o ponto mais proximo :)

public class BarraDeslisante extends CenaVisivel {
    private Path path;
    private Caixa slider;
    
    private PathTransition path_animation;
    private yCircularArray<Point2D> PATH_POINT_LIST;
    private int FRAMES;
    private boolean CYCLIC;
    
    private double MIN;
    private double MAX;
    private double INITIAL_VALUE;
    private int CURRENT_POSITION_INDEX;
    
    private double mouseX;
    private double mouseY;
    private double tINITx;
    private double tINITy;

    public BarraDeslisante(double endX, double endY, double stroke_width, Caixa slider, int FRAMES, double MIN, double MAX, double INITIAL_VALUE) {
        
    }

    public BarraDeslisante(/*Path*/Shape path, Caixa slider, int FRAMES, double MIN, double MAX, boolean cyclic) {
        //this.path = path;
        this.slider = slider;
        this.FRAMES = FRAMES-1;
        this.MIN = MIN;
        this.MAX = MAX;
        this.CURRENT_POSITION_INDEX = 0;
        this.CYCLIC = cyclic;
        
        path_animation = new PathTransition();
        path_animation.setDuration(Duration.seconds(FRAMES));
        path_animation.setPath(path);
        path_animation.setNode(slider);
        path_animation.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);//ISSO NAO VAI FUNFA PQ TA SETANDO A POSICAO, TEM Q SETAR O TEMPO DE ANIMAÇÂO, QU#E NEM O CARA
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
                    sucess = nextFrame(index - 1 <= 0 ? 0 : index - 1, index + 1 > FRAMES ? FRAMES : index + 1, newXPosition, newYPosition);
            }while(sucess);
        });
        
        getChildren().addAll(path, this.slider);
    }
    
    public void setValue(double value){
        if(value > MAX)
            value = MAX;
        else if(value < MIN)
            value = MIN;
        
        double percentage = (MAX != MIN ? (value - MIN) / (MAX - MIN) : 0);
        path_animation.jumpTo(Duration.seconds((int) (FRAMES * percentage)));
    }
    
    /**
     * Save the position of the slider for every second of the animation in
     * a list.
     */
    private void savePositions(PathTransition path_animation) {
        if (PATH_POINT_LIST == null)
            return;

        for (int i=0; i<=(int)FRAMES; i++) {
            path_animation.jumpTo(Duration.seconds(i));
            PATH_POINT_LIST.set(i, new Point2D(slider.yGetTranslateX(0.5), slider.yGetTranslateY(0.5)));
        }
    }
    
    private boolean nextFrame(int frame_index1, int frame_index2, double pX, double pY){
        double distance1 = distanceToNextPoint(frame_index1, pX, pY);
        double distance2 = distanceToNextPoint(frame_index2, pX, pY);
        double minor_distance;
        int index;
        
        if(distance1 < distance2){
            minor_distance = distance1;
            index = frame_index1;
        }else{
            minor_distance = distance2;
            index = frame_index2;
        }
        
        if(minor_distance < Matematicas.hypotenuse(pX - PATH_POINT_LIST.get(CURRENT_POSITION_INDEX).getX(), pY - PATH_POINT_LIST.get(CURRENT_POSITION_INDEX).getY())){
            CURRENT_POSITION_INDEX = index % (FRAMES+1);
            path_animation.jumpTo(Duration.seconds(PATH_POINT_LIST.get_real_index(CURRENT_POSITION_INDEX)));
            return true;
        }
        return false;
    }
    
    private double distanceToNextPoint(int frame_index, double pX, double pY){
        return Matematicas.hypotenuse(pX - PATH_POINT_LIST.get(frame_index).getX(), pY - PATH_POINT_LIST.get(frame_index).getY());
    }
}