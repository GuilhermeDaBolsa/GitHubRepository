package executavel;

import Biblioteca.BasicObjects.Formas.Circulo;
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
        
        //FAZER A CAIXA PARA RECEBER APENAS ELEMENTOS DO TIPO FORMA :)
        
        Circulo a = new Circulo(2, Color.ANTIQUEWHITE, 1, Color.BLACK);
        a.ySetTranslateX(200, 0.5);
        a.ySetTranslateY(200, 0.5);
        
        Linha r2 = new Linha(10, 30, 3, Color.BLACK);
        
        Linha r = new Linha(-30, -30, 3, Color.BROWN);
        
        r.ySetTranslateX(100, 0);
        r.ySetTranslateY(100, 0);
        
        r2.ySetWidth(100, true, true);
        r2.ySetHeight(100, true, true);
        
        r2.ySetTranslateX(200, 0);
        r2.ySetTranslateY(200, 0);
        
        teste.getChildren().addAll(r2, r, a);
        
        Scene scene = new Scene(teste, 1440, 900);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();  
    }

    public static void main(String[] args) {
        launch(args);
    }
}