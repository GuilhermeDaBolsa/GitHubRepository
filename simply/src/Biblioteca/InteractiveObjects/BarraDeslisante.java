package Biblioteca.InteractiveObjects;

import Biblioteca.BasicObjects.CenaVisivel;
import Biblioteca.OrganizadoresDeNodos.Caixa;
import java.util.ArrayList;
import javafx.animation.PathTransition;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Path;
import javafx.util.Duration;

//FAZER OS ESQUEMAS DO TRAKER E, COM BASE NO PONTO ATUAL DA ANIMAÇÃO + POSICAO DO MOUSE, CALCULAR O VETOR DE FORÇA QUE O MOUSE ESTÁ "APLICANDO" NO ELEMENTO
//E VER SE A FORÇA APLICADA AO ELEMENTO O TRAZ MAIS PERTO DO PROXIMO PONTO.... (n fazer com os esquemas de pegar o ponto mais proximo :)

public class BarraDeslisante extends CenaVisivel {
    private Path path;
    private Caixa slider;
    
    private PathTransition path_animation;
    private ArrayList<Point2D> PATH_POINT_LIST;
    private int FRAMES;
    
    private double MIN;
    private double MAX;
    private double INITIAL_VALUE;
    
    private double deltaX_from_center;
    private double deltaY_from_center;

    public BarraDeslisante(double endX, double endY, double stroke_width, Caixa slider, int FRAMES, double MIN, double MAX, double INITIAL_VALUE) {
        
    }

    public BarraDeslisante(Path path, Caixa slider, int FRAMES, double MIN, double MAX, double INITIAL_VALUE) {
        this.path = path;
        this.slider = slider;
        this.FRAMES = FRAMES-1;
        this.MIN = MIN;
        this.MAX = MAX;
        this.INITIAL_VALUE = INITIAL_VALUE;
        
        path_animation = new PathTransition();
        path_animation.setDuration(Duration.seconds(FRAMES));
        path_animation.setPath(path);
        path_animation.setNode(slider);
        path_animation.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        path_animation.setCycleCount(1);
        path_animation.playFromStart();
        path_animation.pause();
        path_animation.jumpTo("end");

        // Save the positions on the path
        PATH_POINT_LIST = new ArrayList<>();
        savePositions();
        
        slider.setOnMousePressed((event) -> { 
            deltaX_from_center = slider.yGetTranslateX(0.5) - event.getSceneX();
            deltaY_from_center = slider.yGetTranslateY(0.5) - event.getSceneY(); 
        });
        
        slider.setOnMouseDragged((event) -> {
            //mouseEvent.getSceneX() + deltaX_from_center;
        });
    }
    
    /**
     * Save the position of the slider for every second of the animation in
     * a list.
     */
    private void savePositions() {
        if (PATH_POINT_LIST == null)
            return;

        for (int i=0; i<=(int)FRAMES; i++) {
            path_animation.jumpTo(Duration.seconds(i));
            PATH_POINT_LIST.add(new Point2D(slider.yGetTranslateX(0.5), slider.yGetTranslateY(0.5)));
        }
    }

}