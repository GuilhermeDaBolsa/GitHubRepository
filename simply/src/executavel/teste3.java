package executavel;

import Biblioteca.BasicObjects.Formas.Circulo;
import Biblioteca.BasicObjects.Formas.Linha;
import Biblioteca.BasicObjects.Formas.Retangulo;
import Biblioteca.BasicObjects.Formas.Texto;
import Biblioteca.OrganizadoresDeNodos.BarraDeslisante;
import Biblioteca.OrganizadoresDeNodos.Caixa;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class teste3 extends Application {

    @Override
    public void start(Stage primaryStage) {
        double X = 15;
        double Y = 103;
        
        Pane teste = new Pane();
        
        Circulo p = new Circulo(3, Color.RED);
        p.ySetTranslateX(X, 0.5);
        p.ySetTranslateY(Y, 0.5);
        
        Retangulo r = new Retangulo(150, 100, Color.CYAN, 3, Color.BLACK);
        teste.widthProperty().addListener((observable, oldValue, newValue) -> {
            r.ySetRotate(teste.getWidth(), X, Y);
        });
        
        
        teste.getChildren().addAll(r, p);
        
        Scene scene = new Scene(teste, 1440, 900);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
