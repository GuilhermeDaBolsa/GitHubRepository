package executavel;

import Biblioteca.BasicObjects.Formas.Circulo;
import Biblioteca.BasicObjects.Formas.Linha;
import Biblioteca.BasicObjects.Formas.Poligono;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

public class teste2 extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane teste = new Pane();
        
        /*Caixa t1 = new Caixa(1000, 1000, Color.ALICEBLUE, 5, Color.GREY);//CLONEAAAA
        t1.ySetTranslateX(500, 0);
        t1.ySetTranslateY(500, 0);
        
        Caixa t2 = new Caixa(t1);//AAA
        t2.ySetTranslateX(600, 0);
        t2.ySetTranslateY(600, 0);*/
        
        
        Poligono a = new Poligono(
                20, 100,
                100, 100,
                100, 100,
                20
        );
        a.setFill(Color.ALICEBLUE);
        a.ySetStroke(5.0, Color.RED, StrokeType.OUTSIDE, true);
        a.ySetTranslateX(600, 0);
        a.ySetTranslateY(600, 0);
        
        Linha c = new Linha(50, 150, 40, Color.DARKSEAGREEN);
        c.ySetTranslateX(0, 0);
        c.ySetTranslateY(0, 0);
        
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