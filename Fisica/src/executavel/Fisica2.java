package executavel;

import Biblioteca.BasicObjects.BarraDeslisanteGOD;
import Biblioteca.OrganizadoresDeNodos.MathGrid;
import Biblioteca.OrganizadoresDeNodos.Caixa;
import static executavel.CriadorMenu.espacinho;
import static executavel.CriadorMenu.largura_menu;
import static executavel.CriadorMenu.scene;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Fisica2 extends Application {
    public static Pane pane_principal = new Pane();
    public static Caixa caixa_adicionar_carga;
    public static MathGrid grade;
    public static ArrayList<Entidade> cargas = new ArrayList();
    BarraDeslisanteGOD barra_escala;
    
    @Override
    public void start(Stage primaryStage) {
        caixa_adicionar_carga = CriadorMenu.criar_caixa_adicionar();
        caixa_adicionar_carga.setVisible(false);
        caixa_adicionar_carga.setDisable(true);
        caixa_adicionar_carga.setTranslateX(largura_menu + espacinho/2);
        caixa_adicionar_carga.setTranslateY(largura_menu*0.6 + espacinho/2);
        
        Caixa menu = CriadorMenu.cria_menu();
        menu.caixa.scaleYProperty().bind(primaryStage.heightProperty());
        caixa_adicionar_carga.translateXProperty().bind(menu.translateXProperty().add(largura_menu + espacinho/2));
        
        Caixa botao_menu = CriadorMenu.cria_botao_menu(menu);
        botao_menu.setTranslateX(botao_menu.getTranslateX() + botao_menu.caixa.getStrokeWidth());
        
        grade = new MathGrid(1, 800, 800);
        barra_escala = new BarraDeslisanteGOD(160, 12, 1, 100, 50, 1, null);
        
        pane_principal.getChildren().addAll(grade, barra_escala, menu, botao_menu, caixa_adicionar_carga);

        grade.mathgridSetedUp();
        barra_escala.translateXProperty().bind(pane_principal.widthProperty().subtract(30));
        barra_escala.translateYProperty().bind(pane_principal.heightProperty().subtract(196));
        
        scene = new Scene(pane_principal);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}