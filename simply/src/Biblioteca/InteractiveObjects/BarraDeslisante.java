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

    public BarraDeslisante(/*Path*/Shape path, Caixa slider, int FRAMES, double MIN, double MAX, double INITIAL_VALUE, boolean cyclic) {
        //this.path = path;
        this.slider = slider;
        this.FRAMES = FRAMES-1;
        this.MIN = MIN;
        this.MAX = MAX;
        this.INITIAL_VALUE = INITIAL_VALUE;
        this.CURRENT_POSITION_INDEX = 0;
        this.CYCLIC = cyclic;
        
        PathTransition path_animation = new PathTransition();
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
            
            do{
                int index = CURRENT_POSITION_INDEX - 1;

                if(CYCLIC)
                    sucess = nextFrame(index, index+2, newXPosition, newYPosition);
                else
                    sucess = nextFrame(index < 0 ? 0 : index, index+2 > PATH_POINT_LIST.array.length ? PATH_POINT_LIST.array.length : index+2, newXPosition, newYPosition);
            
            }while(sucess);//EM VEZ DE FICAR DANDO O SET POSITION / PEGANDO POSITION / DANDO SET POSITION... SO CALCULAR POR DENTRO E MANDAR O PONTO DIRETAMENTE PRO LUGAR, ACHO QUE VAI TIRAR O LAGZIN
        });
        
        getChildren().addAll(path, this.slider);
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
    
    private double distanceToNextPoint(int frame_index, double pX, double pY){
        return Matematicas.hypotenuse(pX - PATH_POINT_LIST.get(frame_index).getX(), pY - PATH_POINT_LIST.get(frame_index).getY());
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
            slider.ySetTranslateX(PATH_POINT_LIST.get(index).getX(), 0.5);
            slider.ySetTranslateY(PATH_POINT_LIST.get(index).getY(), 0.5);
            CURRENT_POSITION_INDEX = index;
            return true;
        }
        return false;
    }
}