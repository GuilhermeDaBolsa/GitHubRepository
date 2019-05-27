package executavel;

import Biblioteca.BasicObjects.Formas.Circulo;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class teste extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane teste = new Pane();
        
        Tabela testeTabela = new Tabela(20, 20, true, true);
        
        Tabela vem = new Tabela(10, 10, true, true);
        
        vem.add(new Texto("aaaa"), 1, 1);
        
        MathGrid aaaa = new MathGrid(100,100);
        
        vem.add(aaaa, 2, 2);
        
        aaaa.bind_rodinha_mouse();
        aaaa.bind_tela_movel();
        
        Rectangle b = (Rectangle) vem.getCaixaCelulas().caixa;
        aaaa.binda_tamanho(b.widthProperty(), b.heightProperty());
        
        testeTabela.add(vem, 3, 1);
        
        MathGrid grade = new MathGrid(100,100);
        VisualizadorImagemEgif portal = new VisualizadorImagemEgif("assets/ac.png", 150, 100);
        VisualizadorImagemEgif chara = new VisualizadorImagemEgif("assets/chara.jpg", 130, 200);
        VisualizadorImagemEgif dados = new VisualizadorImagemEgif("assets/dados.png", 50, 50);
        VisualizadorImagemEgif gif = new VisualizadorImagemEgif("assets/giphy.gif", 200, 200);
        VisualizadorVideo video = new VisualizadorVideo("assets/sample.mp4", 150, 100);
        Audio musica = new Audio("assets/teste.mp3");
        
        testeTabela.add(new Circulo(50, Color.CORNFLOWERBLUE), 2, 1);
        testeTabela.add(new Circulo(5, Color.CORNFLOWERBLUE), 3, 2);
        testeTabela.add(new Rectangle(80, 80, Color.LAVENDER), 1, 2);
        testeTabela.add(dados, 3, 3);
        testeTabela.add(chara, 2, 2);
        Caixa cc = new Caixa(40, 45, Color.WHITE, 1, Color.BLACK);
        cc.add(new Circulo(5, Color.CORNFLOWERBLUE));
        cc.alinhar_conteudos_centro();
        testeTabela.add(cc, 4, 3);
        testeTabela.add(portal, 2, 3);
        testeTabela.add(gif, 1, 1);
        testeTabela.add(video, 1, 3);
        testeTabela.add(grade, 4, 2);
        
        Rectangle caixaGrade = (Rectangle) testeTabela.getCaixaCelulas().caixa;
        grade.bind_rodinha_mouse();
        grade.bind_tela_movel();
        grade.binda_tamanho(caixaGrade.widthProperty(), caixaGrade.heightProperty());//DAR UM JEITO NESSES CAST DE RECTANGLE
        //E VER cOMO MUDAR PQ O BINDA TAMANHO TEM Q TA ANTES DO MONTAR TABELA, SE NAO O NEGOCIO ENLOUQUECE :P
        
        testeTabela.setModeloLinhaX(2, Color.RED);
        testeTabela.setModeloLinhaY(2, Color.RED);
        
        Caixa envolocro = new Caixa(13.5, 10, Color.WHITE, 8, Color.DIMGRAY);
        envolocro.add(testeTabela);
        //envolocro.scaleBoxWithItsContent(false, false, true, false);//AS LINHA NAO TAO SE SOBREPONTO PCAUSA DISSO AQUI (q?, n lembro o q q eu queria dizer)
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