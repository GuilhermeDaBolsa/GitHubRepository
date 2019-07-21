package executavel;

import Biblioteca.BasicObjects.Formas.Circulo;
import Biblioteca.BasicObjects.Formas.Retangulo;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class teste3 extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane teste = new Pane();
        
        Retangulo p = new Retangulo(200, 100, Color.BEIGE, 5, Color.BURLYWOOD, StrokeType.OUTSIDE, true);
        p.ySetTranslateX(200, 0);
        p.ySetTranslateY(200, 0);
        
        Rotate rotate = new Rotate();
        rotate.setPivotX(100);
        rotate.setPivotY(50);
        rotate.setAngle(45);
        
        Rotate rotate2 = new Rotate();
        rotate2.setPivotX(100);
        rotate2.setPivotY(50);
        rotate2.setAngle(45);
        
        System.out.println(p.getTransforms().toString());
        
        //EU PRECISO DE SOMA DE ROTAÇÕES, PARA COM 2 (MESMO COM EIXOS DIFERENTES) CRIAR UMA NOVA, COM SEU PROPRIO EIXO
        //PQ SE NAO A MEMORIA VAI PRA CASA DO CARALHO
        
        p.getTransforms().add(rotate);
        p.getTransforms().add(rotate2);
        System.out.println("");
        System.out.println(p.getTransforms().toString());
        
        System.out.println(p.getRotate());
        
        Circulo o = new Circulo(3);
        o.ySetTranslateX(p.yGetTranslateX(0.5), 0.5);
        o.ySetTranslateY(p.yGetTranslateY(0.5), 0.5);
            
        teste.setOnMousePressed((event) -> {
            if(event.isSecondaryButtonDown()){
                p.ySetTranslateX(0, 0);
                p.ySetTranslateY(0, 0);
            }
        });

        teste.getChildren().addAll(p, o);
        
        Scene scene = new Scene(teste, 1440, 900);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
