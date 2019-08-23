package executavel;

import Biblioteca.BasicObjects.Formas.Forma;
import Biblioteca.OrganizadoresDeNodos.BarraDeslisante;
import Biblioteca.OrganizadoresDeNodos.MathGrid;
import Biblioteca.OrganizadoresDeNodos.YBox;
import static executavel.CriadorMenu.largura_menu;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Fisica2 extends Application {
    public static Pane pane_principal = new Pane();
    public static YBox caixa_adicionar_carga;
    public static MathGrid grade;
    public static ArrayList<Entidade> cargas = new ArrayList();
    //BarraDeslisante barra_escala;
    
    @Override
    public void start(Stage primaryStage) {
        caixa_adicionar_carga = CriadorMenu.criar_caixa_adicionar();
        caixa_adicionar_carga.setTranslateX(largura_menu + 10);
        caixa_adicionar_carga.setTranslateY(largura_menu*0.6 + 10);
        caixa_adicionar_carga.desativar();
        
        YBox menu = CriadorMenu.cria_menu();
        ((Forma) menu.box).yBindHeight("altura", pane_principal.heightProperty(), true);
        System.out.println("caixa     " +((Forma) menu.box).yGetTranslateX(0) + "  ,  " + ((Forma) menu.box).yGetTranslateX(0) + "  ---  " + ((Forma) menu.box).yGetWidth(true) + "   x   " + ((Forma) menu.box).yGetHeight(true));
        System.out.println("container   " + menu.content.getTranslateX() + "    ,   " + menu.getTranslateY() + "  ---  " + menu.yGetContentWidth() + "   x   " + menu.yGetContentHeight());
        menu.yAlignContents(0.5, 0, 0.5, 0);
        menu.yMoveContents(0, 20);
        
        
        
        caixa_adicionar_carga.translateXProperty().bind(menu.translateXProperty().add(largura_menu + 10));
        
        YBox botao_menu = CriadorMenu.cria_botao_menu(menu);
        botao_menu.setTranslateX(botao_menu.box.getStrokeWidth());
        
        grade = new MathGrid(200, 200);
        //barra_escala = new BarraDeslisante(160, 0, 1, 100, 50, 2);
        
        //barra_escala.translateXProperty().bind(pane_principal.widthProperty().subtract(196));
        //barra_escala.translateYProperty().bind(pane_principal.heightProperty().subtract(30));
        
        pane_principal.getChildren().addAll(grade, menu, botao_menu, caixa_adicionar_carga);
        
        grade.binda_tamanho(primaryStage.widthProperty(), primaryStage.heightProperty());
        grade.bind_rodinha_mouse();
        grade.bind_tela_movel();
        
        Scene cena = new Scene(pane_principal, 800, 640);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(cena);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}