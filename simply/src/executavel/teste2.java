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
                50, 0
        );
        a.ySetStroke(20.0, Color.RED, StrokeType.OUTSIDE, true);
        //a.ySetTranslateX(200, 0);
        //a.ySetTranslateY(200, 0);
        a.yGetTranslateY(0);
        
        Linha c = new Linha(100, 0, 5, Color.VIOLET);
        c.ySetTranslateX(165, 0);
        c.ySetTranslateY(a.yGetHeight(), 0);
        
        teste.getChildren().addAll(a, c);
        
        Scene scene = new Scene(teste, 1440, 900);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();  
    }

    public static void main(String[] args) {
        launch(args);
    }
}