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
        Caixa avc = new Caixa(100, 100, 10, Color.GREY, Color.BLACK);
        avc.setTranslateX(10);
        avc.setTranslateY(10);
        
        Linha vem = new Linha(10, 20, 110, 20, 1, Color.CADETBLUE);

        teste.getChildren().addAll(avc, vem);
        
        Scene scene = new Scene(teste, 800, 800);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();  
    }

    public static void main(String[] args) {
        launch(args);
    }
}