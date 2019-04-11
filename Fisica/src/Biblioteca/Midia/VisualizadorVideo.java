package Biblioteca.Midia;

import java.io.File;
import javafx.geometry.NodeOrientation;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * Classe que representa a parte visivel do vídeo. NÃO SERVE PARA CONTROLÁ-LO, REPITO, É APENAS A PARTE VISÍSVEL.
 * Para controlar o vídeo use o player.
 */
public class VisualizadorVideo extends MediaView{//NAO TEM OS EVENTOS DE CLICKE AAAAAAAAAAAAAAAAAAAAAAAAAA NAO DA PRA EXTENDER 2 CLASSES AAAAAAAAA
    public MediaPlayer player; //FAZER UM PLAYER PRA CONTROLAR NÉÈÈÈÈÈÈÈÈÈ!!!!!!!! OU PELO MENOS DAR A OPÇÃO :P
    
    /**
     * Cria um objeto que mostra um video.
     * @param caminho_video Caminho do video (começando pela pasta inicial do projeto), ex: "assets/imagens/imagem.mp4".
     */
    public VisualizadorVideo(String caminho_video) {
        super();
        setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        player = new MediaPlayer(new Media(new File(caminho_video).toURI().toString()));
        setMediaPlayer(player);
    }
    
    /**
     * Cria um objeto que mostra um video.
     * @param caminho_video Caminho do video (começando pela pasta inicial do projeto), ex: "assets/imagens/imagem.mp4".
     * @param largura Largura do objeto que mostra a imagem (a imagem será redimencionada para caber).
     * @param altura Altura do objeto que mostra a imagem (a imagem será redimencionada para caber).
     */
    public VisualizadorVideo(String caminho_video, double largura, double altura) {
        super();
        setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        player = new MediaPlayer(new Media(new File(caminho_video).toURI().toString()));
        setMediaPlayer(player);
        setFitWidth(largura);
        setFitHeight(altura);//PARECE QUE NAO REDIMENSIONA COMO O CARA QUER, TIPO SE O VIDEO OCUPA 100px E TU BOTA 200 aqui ELE CONTINUA COM 100
    }
    
}
