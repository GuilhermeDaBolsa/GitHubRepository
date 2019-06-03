package executavel;

import Biblioteca.BasicObjects.Formas.Circulo;
import Biblioteca.BasicObjects.Formas.Linha;
import Biblioteca.BasicObjects.Formas.Poligono;
import Biblioteca.BasicObjects.Formas.Retangulo;
import Biblioteca.BasicObjects.Formas.Texto;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.Rectangle2D;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

public class teste2 extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane teste = new Pane();
        
        Poligono a = new Poligono(
                0, 100,
                100, 100,
                50
        );
        a.ySetStroke(20.0, Color.RED, StrokeType.OUTSIDE, true);
        //a.ySetTranslateX(200, 0);
        //a.ySetTranslateY(200, 0);
        a.yGetTranslateY(0);
        System.out.println(a.yGetWidth());
        
        Linha c = new Linha(50, 150, 40, Color.VIOLET);
        //c.ySetTranslateX(10, 0);
        //c.ySetTranslateY(10, 0);
        
        System.out.println(c.yGetWidth() + " - " + c.getWidth());
        System.out.println(c.yGetHeight() + " - " + c.getHeight());
        System.out.println("/-----\\ translate");
        System.out.println(c.yGetTranslateX(0));
        System.out.println(c.yGetTranslateY(0));
        
        Circulo b = new Circulo(2);
        
        b.ySetTranslateX(c.yGetTranslateX(1), 0.5);
        b.ySetTranslateY(c.yGetTranslateY(1), 0.5);
        
        Circulo d = new Circulo(2);
        
        d.ySetTranslateX(c.yGetTranslateX(0), 0.5);
        d.ySetTranslateY(c.yGetTranslateY(0), 0.5);
        
        teste.getChildren().addAll(a, c, b, d);
        
        Scene scene = new Scene(teste, 1440, 900);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();  
    }

    public static void main(String[] args) {
        launch(args);
    }
}