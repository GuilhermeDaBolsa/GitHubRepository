package executavel;

import Biblioteca.BasicObjects.Formas.Linha;
import Biblioteca.OrganizadoresDeNodos.Caixa;
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
        
        //Caixa continua = new Caixa(50, 50, Color.DARKRED, 6, Color.BLACK);

        //ultimato.ySetFont(Font.font("Arial", 64));
        //ultimato.ySetStroke(5.0, Color.BISQUE, StrokeType.OUTSIDE, true);
        
                
        Linha vem1 = new Linha(100, 100, 200, 100, 1, Color.rgb(46, 75, 34, 0.5));
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
        
        teste.getChildren().addAll(vem1, vem2, vem3, vem4, vem5, vem6);

        //teste.getChildren().addAll(continua);
        
        Scene scene = new Scene(teste, 1440, 900);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();  
    }

    public static void main(String[] args) {
        launch(args);
    }
}