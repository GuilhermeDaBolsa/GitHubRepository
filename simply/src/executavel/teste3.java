package executavel;

import Biblioteca.BasicObjects.Formas.Circulo;
import Biblioteca.BasicObjects.Formas.Linha;
import Biblioteca.BasicObjects.Formas.Poligono;
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
        double X = 200;
        double Y = 200;
        
        Pane teste = new Pane();
        
        Circulo p = new Circulo(3, Color.RED);
        p.ySetTranslateX(X, 0.5);
        p.ySetTranslateY(Y, 0.5);
        
        Poligono r = new Poligono(
                150, 100,
                200, 200,
                400, 400
        );
        Linha l = new Linha(100, 25, 3, Color.BLUEVIOLET);
        l.ySetTranslateX(100, 0);
        l.ySetTranslateY(200, 0);
        
        teste.widthProperty().addListener((observable, oldValue, newValue) -> {
            int inc = 1;
            if(newValue.doubleValue() < oldValue.doubleValue())
                inc = -1;
            
            r.yRotateBy(inc*5, X, Y);
            l.yRotateBy(inc * 3, X, Y);
        });
        
        
        teste.getChildren().addAll(r, l, p);
        
        Scene scene = new Scene(teste, 1440, 900);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
