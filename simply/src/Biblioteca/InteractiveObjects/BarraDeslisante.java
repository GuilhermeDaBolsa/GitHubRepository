package Biblioteca.InteractiveObjects;

import Biblioteca.BasicObjects.CenaVisivel;
import Biblioteca.Lists.yCircularArray;
import Biblioteca.LogicClasses.Matematicas;
import Biblioteca.OrganizadoresDeNodos.Caixa;
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
    
    private double mouse_positionX;
    private double mouse_positionY;
    private double forceX;
    private double forceY;

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
        path_animation.playFromStart();
        path_animation.pause();
        path_animation.jumpTo("end");

        // Save the positions on the path
        PATH_POINT_LIST = new yCircularArray(FRAMES);
        savePositions(path_animation);
        
        slider.setOnMousePressed((event) -> { 
            mouse_positionX = event.getSceneX();
            mouse_positionY = event.getSceneY(); 
            
            while(true){
                int index = CURRENT_POSITION_INDEX - 1;
                if(CYCLIC){
                    if(!nextFrame(index, forceX, forceY))
                        nextFrame(index + 2, forceX, forceY);
                }else{
                    boolean sucess = false;

                    if(!(index < 0))
                        sucess = nextFrame(index, forceX, forceY);

                    index+=2;
                    if(!sucess && index < PATH_POINT_LIST.array.length)
                        nextFrame(index, forceX, forceY);
                }
            }
        });
        slider.setOnMouseDragged((event) -> {
            forceX = event.getSceneX() - mouse_positionX;
            forceY = event.getSceneY() - mouse_positionY;
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
    
    private boolean nextFrame(int frame_index, double fx, double fy){
        double next_frame_x = PATH_POINT_LIST.get(frame_index).getX();
        double next_frame_y = PATH_POINT_LIST.get(frame_index).getY();
        double distance_x = next_frame_x - fx;
        double distance_y = next_frame_y - fy;
        
        if(Matematicas.hypotenuse(distance_x, distance_y) < Matematicas.hypotenuse(slider.yGetTranslateX(0.5) - fx, slider.yGetTranslateY(0.5) - fy)){
            slider.ySetTranslateX(next_frame_x, 0.5);
            slider.ySetTranslateY(next_frame_y, 0.5);
            CURRENT_POSITION_INDEX = frame_index;
            return true;
        }
        return false;
    }
}