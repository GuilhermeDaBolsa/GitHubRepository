package executavel;

import Biblioteca.BasicObjects.Formas.Circulo;
import Biblioteca.BasicObjects.Formas.Retangulo;
import Biblioteca.InteractiveObjects.BarraDeslisante;
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
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class teste2_1_1 extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane teste = new Pane();
        
        BarraDeslisante k = new BarraDeslisante(new Circulo(60, Color.ALICEBLUE, 2, Color.BLACK), new Caixa(4, Color.WHITE, 2, Color.BLACK), 25, 0, 0, 0, false);
        
        k.ySetTranslateX(400, 0.5);
        k.ySetTranslateY(400, 0.5);
        
        teste.getChildren().addAll(k);
        
        Scene scene = new Scene(teste, 1440, 900);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
