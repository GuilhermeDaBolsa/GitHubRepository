package executavel;


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
    public static final double largura_inicial_tela = 800;
    public static final double altura_inicial_tela = 600;
    private static final int recuoX = 8;
    private static final int recuoY = 20;
    
    public static Caixa caixa_adicionar_carga;
    public static MathGrid grade;
    public static ArrayList<Entidade> cargas = new ArrayList();
    
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
        
        grade = new MathGrid(primaryStage, largura_inicial_tela, altura_inicial_tela, 1);
        grade.setTranslateX(grade.getTranslateX()-recuoX);
        grade.setTranslateY(grade.getTranslateY()-recuoY);
        
        pane_principal.getChildren().addAll(grade, menu, botao_menu, caixa_adicionar_carga);

        scene = new Scene(pane_principal, largura_inicial_tela, altura_inicial_tela);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
