package executavel;

import Biblioteca.BasicObjects.Formas.Ycircle;
import Biblioteca.BasicObjects.Formas.yText;
import Biblioteca.Midia.Yaudio;
import Biblioteca.Midia.Yimage;
import Biblioteca.Midia.YvideoViwer;
import Biblioteca.OrganizadoresDeNodos.YBox;
import Biblioteca.OrganizadoresDeNodos.YmathGrid;
import Biblioteca.OrganizadoresDeNodos.Ytable;
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
        
        Ytable testeTabela = new Ytable(5, 5, 5, 5, true, true);
        testeTabela.ySetCellConfig(2.0, Color.BROWN, null);
        testeTabela.ySetXlines(1.0, Color.LIGHTBLUE);
        testeTabela.ySetYlines(1.0, Color.LIGHTBLUE);
        
        Ytable vem = new Ytable(5, 5, 5, 5, true, true);
        vem.ySetCellConfig(2.0, Color.CADETBLUE, null);
        
        vem.yAdd(new yText("aaaa"), 0, 0);
        YmathGrid aaaa = new YmathGrid(80, 80);
        aaaa.yBindMouseScrollZoom();
        aaaa.yBindMouseMoveble();
        vem.yAdd(aaaa, 1, 1);
        
        
        testeTabela.yAdd(vem, 3, 1);
        
        YmathGrid grade = new YmathGrid(200,200);
        grade.yBindMouseScrollZoom();
        grade.yBindMouseMoveble();
        //grade.yBindSize(testeTabela., altura);
        testeTabela.yAdd(grade, 0, 2);
        
        
        Yimage portal = new Yimage("assets/ac.png", 150, 100);
        Yimage chara = new Yimage("assets/chara.jpg", 130, 200);
        Yimage dados = new Yimage("assets/dados.png", 50, 50);
        Yimage gif = new Yimage("assets/giphy.gif", 200, 200);
        YvideoViwer video = new YvideoViwer("assets/sample.mp4", 150, 100);
        Yaudio musica = new Yaudio("assets/teste.mp3");
        
        testeTabela.yAdd(new Ycircle(50, Color.CORNFLOWERBLUE), 2, 1);
        testeTabela.yAdd(new Ycircle(5, Color.CORNFLOWERBLUE), 3, 2);
        testeTabela.yAdd(new Rectangle(80, 80, Color.LAVENDER), 1, 2);
        testeTabela.yAdd(dados, 3, 0);
        testeTabela.yAdd(chara, 2, 2);
        YBox cc = new YBox(40, 45, Color.WHITE, 1, Color.BLACK);
        cc.yAddContent(new Ycircle(5, Color.CORNFLOWERBLUE));
        cc.yAlignContents(0.5, 0.5, 0.5, 0.5);
        testeTabela.yAdd(cc, 0, 0);
        testeTabela.yAdd(portal, 2, 0);
        testeTabela.yAdd(gif, 1, 1);
        testeTabela.yAdd(video, 1, 0);
        
        vem.yRefresh();
        testeTabela.yRefresh();
        
        YBox envolocro = new YBox(13.5, 10, Color.WHITE, 8, Color.DIMGRAY);
        envolocro.yAddContent(testeTabela);
        envolocro.ySetBoxSizeWithItsContent(false, false, true, false);
        envolocro.ySetStroke(null, null, StrokeType.OUTSIDE, true);
        envolocro.yAlignContents(0.5, 0.5, 0.5, 0.5);
        
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