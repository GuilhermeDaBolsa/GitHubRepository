package executavel;

import Biblioteca.BasicObjects.Formas.Circulo;
import Biblioteca.BasicObjects.Formas.Linha;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class teste2 extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane teste = new Pane();
//        Caixa avc = new Caixa(100, 100, 0, Color.GREY, Color.BLACK);
//        avc.setTranslateX(100);
//        avc.setTranslateY(100);
        Circulo teste_pos = new Circulo(12);
        //teste_pos.setTranslateX(0);
        //teste_pos.setTranslateY(0);

                
        Linha vem1 = new Linha(100, 100, 200, 100, 1, Color.rgb(46, 75, 34, 0.5));
        Linha vem2 = new Linha(100, 100, 200, 100, 10, Color.rgb(46, 75, 34, 0.5));
        System.out.println(vem1.getWidth());
        System.out.println(vem2.getWidth());
        
        System.out.println("-------------");
        
        Linha vem3 = new Linha(100, 200, 200, 300, 1, Color.rgb(46, 75, 34, 0.5));
        Linha vem4 = new Linha(100, 200, 200, 300, 14, Color.rgb(46, 75, 34, 0.5));
        System.out.println(vem3.getWidth());
        System.out.println(vem4.getWidth());
        
        System.out.println("-------------");
        
        Linha vem5 = new Linha(100, 400, 100, 500, 1, Color.rgb(46, 75, 34, 0.5));
        Linha vem6 = new Linha(100, 400, 100, 500, 100, Color.rgb(46, 75, 34, 0.5));
        System.out.println(vem5.getWidth());
        System.out.println(vem6.getWidth());

        teste.getChildren().addAll(vem1, vem2, vem3, vem4, vem5, vem6, teste_pos);
        
        Scene scene = new Scene(teste, 1440, 900);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();  
    }

    public static void main(String[] args) {
        launch(args);
    }
}