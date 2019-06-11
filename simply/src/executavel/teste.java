package executavel;

import Biblioteca.BasicObjects.Formas.Circulo;
import Biblioteca.BasicObjects.Formas.Forma;
import Biblioteca.BasicObjects.Formas.Texto;
import Biblioteca.Midia.Audio;
import Biblioteca.Midia.VisualizadorImagemEgif;
import Biblioteca.Midia.VisualizadorVideo;
import Biblioteca.OrganizadoresDeNodos.Caixa;
import Biblioteca.OrganizadoresDeNodos.MathGrid;
import Biblioteca.OrganizadoresDeNodos.Tabela;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

public class teste extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane teste = new Pane();
        
        Tabela testeTabela = new Tabela(5, 5, true, true);
        testeTabela.ySetCellConfig(10.0, Color.BROWN, null);
        testeTabela.ySetXlines(4.0, Color.RED);
        testeTabela.ySetYlines(4.0, Color.RED);
        
        Tabela vem = new Tabela(3, 3, true, true);
        vem.ySetCellConfig(2.0, Color.CADETBLUE, null);
        
        vem.yAdd(new Texto("aaaa"), 0, 0);
        MathGrid aaaa = new MathGrid(80, 80);
        aaaa.bind_rodinha_mouse();
        aaaa.bind_tela_movel();
        vem.yAdd(aaaa, 1, 1);
        aaaa.binda_tamanho(((Rectangle) vem.cells.get(0).caixa).widthProperty().subtract(vem.min_marginX),
                ((Rectangle) vem.cells.get(0).caixa).heightProperty().subtract(vem.min_marginY)); ///OLHA ESSA MERDA 
        
        testeTabela.yAdd(vem, 3, 1);
        
        MathGrid grade = new MathGrid(100,100);
        grade.bind_rodinha_mouse();
        grade.bind_tela_movel();
        testeTabela.yAdd(grade, 0, 2);
        grade.binda_tamanho(((Rectangle) testeTabela.cells.get(0).caixa).widthProperty().subtract(testeTabela.min_marginX),
                ((Rectangle) testeTabela.cells.get(0).caixa).heightProperty().subtract(testeTabela.min_marginX));///OLHA ESSA MERDA
        
        VisualizadorImagemEgif portal = new VisualizadorImagemEgif("assets/ac.png", 150, 100);
        VisualizadorImagemEgif chara = new VisualizadorImagemEgif("assets/chara.jpg", 130, 200);
        VisualizadorImagemEgif dados = new VisualizadorImagemEgif("assets/dados.png", 50, 50);
        VisualizadorImagemEgif gif = new VisualizadorImagemEgif("assets/giphy.gif", 200, 200);
        VisualizadorVideo video = new VisualizadorVideo("assets/sample.mp4", 150, 100);
        Audio musica = new Audio("assets/teste.mp3");
        
        testeTabela.yAdd(new Circulo(50, Color.CORNFLOWERBLUE), 2, 1);
        testeTabela.yAdd(new Circulo(5, Color.CORNFLOWERBLUE), 3, 2);
        testeTabela.yAdd(new Rectangle(80, 80, Color.LAVENDER), 1, 2);
        testeTabela.yAdd(dados, 3, 0);
        testeTabela.yAdd(chara, 2, 2);
        Caixa cc = new Caixa(40, 45, Color.WHITE, 1, Color.BLACK);
        cc.add(new Circulo(5, Color.CORNFLOWERBLUE));
        cc.alinhar_conteudos_centro();
        testeTabela.yAdd(cc, 0, 0);
        testeTabela.yAdd(portal, 2, 0);
        testeTabela.yAdd(gif, 1, 1);
        testeTabela.yAdd(video, 1, 0);
        
        vem.yRefresh();
        testeTabela.yRefresh();
        
        Caixa envolocro = new Caixa(13.5, 10, Color.WHITE, 8, Color.DIMGRAY);
        envolocro.add(testeTabela);
        envolocro.resizeBoxWithItsContent(false, false, true, false);
        envolocro.ySetStroke(null, null, StrokeType.OUTSIDE, true);
        envolocro.alinhar_conteudos_centro();
        
        teste.getChildren().addAll(envolocro);
        
        video.player.play();
        
        dados.setOnMouseClicked((event) -> {
            if(musica.player.getStatus() != musica.player.getStatus().PLAYING){
                musica.player.play();
            }else{
                musica.player.pause();
            } 
        });

        Scene scene = new Scene(teste, 1100, 800);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();  
    }

    public static void main(String[] args) {
        launch(args);
    }
}