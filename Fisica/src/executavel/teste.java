package executavel;

import Biblioteca.BasicObjects.Formas.Circulo;
import Biblioteca.OrganizadoresDeNodos.Caixa;
import Biblioteca.OrganizadoresDeNodos.MathGrid;
import Biblioteca.OrganizadoresDeNodos.Tabela;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class teste extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane teste = new Pane();
        MathGrid grade = new MathGrid(1, 20, 22);
        
        Tabela testeTabela = new Tabela(20, 20, true, true, true);
        
        testeTabela.add(new Circulo(50, Color.CORNFLOWERBLUE), 2, 1);
        testeTabela.add(new Circulo(5, Color.CORNFLOWERBLUE), 2, 4);
        testeTabela.add(new Rectangle(80, 80, Color.LAVENDER), 1, 2);
        
        testeTabela.add(grade, 4, 2);
        //grade.mathgridSetedUp();
        
        testeTabela.add(new Caixa(40, 45, 2, Color.WHITE, Color.BLACK), 5, 3);
        
        testeTabela.montarTabela(0, 0);
        
        testeTabela.setTranslateX(100);
        testeTabela.setTranslateY(100);
        
        teste.getChildren().add(testeTabela);

        Scene scene = new Scene(teste, 800, 800);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();
        grade.mathgridSetedUp();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
