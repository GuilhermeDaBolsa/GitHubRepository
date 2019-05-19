package executavel;

import Biblioteca.BasicObjects.Formas.Circulo;
import Biblioteca.BasicObjects.Formas.Linha;
import Biblioteca.BasicObjects.Formas.Retangulo;
import Biblioteca.BasicObjects.Formas.Texto;
import Biblioteca.OrganizadoresDeNodos.Caixa;
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
        
        Circulo c = new Circulo(20, Color.BISQUE, 12, Color.BLACK, StrokeType.CENTERED, true);
        Retangulo r = new Retangulo(80, 40, Color.BLUE, 16, Color.DARKOLIVEGREEN, StrokeType.OUTSIDE, true);
        Texto t = new Texto("abcd");
        Linha l = new Linha(80+16*2, 0, 10, Color.CRIMSON);
        
        l.ySetTranslateX(80+16*2, 0);
        l.ySetTranslateY(40+16*2, 0);
        
        c.ySetTranslateX(80+16*2, 0);
        
        //Caixa continua = new Caixa(50, 50, Color.DARKRED, 6, Color.BLACK); 
                
        /*Linha vem1 = new Linha(100, 100, 200, 100, 1, Color.rgb(46, 75, 34, 0.5));
        Linha vem2 = new Linha(100, 100, 200, 100, 10, Color.rgb(46, 75, 34, 0.5));
        System.out.println(vem1.yGetWidth());
        System.out.println(vem2.yGetWidth());
        
        System.out.println("-------------");
        
        Linha vem3 = new Linha(100, 200, 200, 300, 1, Color.rgb(46, 75, 34, 0.5));
        Linha vem4 = new Linha(100, 200, 200, 300, 14, Color.rgb(46, 75, 34, 0.5));
        System.out.println(vem3.yGetWidth());
        System.out.println(vem4.yGetWidth());
        
        System.out.println("-------------");
        
        Linha vem5 = new Linha(100, 400, 100, 500, 1, Color.rgb(46, 75, 34, 0.5));
        Linha vem6 = new Linha(100, 400, 100, 500, 100, Color.rgb(46, 75, 34, 0.5));
        System.out.println(vem5.yGetWidth());
        System.out.println(vem6.yGetWidth());
        
        teste.getChildren().addAll(vem1, vem2, vem3, vem4, vem5, vem6);*/
        //teste.getChildren().addAll(continua);
        teste.getChildren().add(c);
        teste.getChildren().add(r);
        teste.getChildren().add(t);
        teste.getChildren().add(l);
        //teste.getChildren().addAll(c, r, t, l);
        
        Scene scene = new Scene(teste, 1440, 900);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();  
    }

    public static void main(String[] args) {
        launch(args);
    }
}