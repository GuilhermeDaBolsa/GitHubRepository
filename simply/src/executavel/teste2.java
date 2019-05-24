package executavel;

import Biblioteca.BasicObjects.Formas.Linha;
import Biblioteca.BasicObjects.Formas.Retangulo;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

public class teste2 extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane teste = new Pane();
        
        
        Retangulo r2 = new Retangulo(400, 300);
        
        Retangulo r = new Retangulo(30, 20);
        
        r.ySetStroke(5.0, Color.BLACK, StrokeType.OUTSIDE, false);
        
        r.ySetWidthWithScale(400, true, true);
        r.ySetHeigthWithScale(300, true, true);
        r.ySetWidthWithScale(400, true, true);
        r.ySetHeigthWithScale(300, true, true);
   
        r2.ySetTranslateX(200, 0);
        r2.ySetTranslateY(200, 0);
        r.ySetTranslateX(r2.yGetTranslateX(0.5), 0.5);
        r.ySetTranslateY(r2.yGetTranslateY(0.5), 0.5);
        
        
        teste.getChildren().addAll(r2, r);
        
        Scene scene = new Scene(teste, 1440, 900);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();  
    }

    public static void main(String[] args) {
        launch(args);
    }
}