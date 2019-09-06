package Biblioteca.Midia;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Shortcut to create an image/GIF object.
 */
public class Yimage extends ImageView{//NAO TEM OS EVENTOS DE CLICKE AAAAAAAAAAAAAAAAAAAAAAAAAA NAO DA PRA EXTENDER 2 CLASSES AAAAAAAAA
    //TRANSFERIR O MANIPULADOR IMAGEM??? TALVEZ N
    
    /**
     * Cria um objeto que mostra uma imagem.
     * @param caminho_img_ou_gif Caminho da imagem (começando pela pasta inicial do projeto), ex: "assets/imagens/imagem.png".
     */
    public Yimage(String caminho_img_ou_gif){
        super(new Image(new File(caminho_img_ou_gif).toURI().toString()));
    }

    /**
     * Cria um objeto que mostra uma imagem.
     * @param caminho_img_ou_gif Caminho da imagem (começando pela pasta inicial do projeto), ex: "assets/imagens/imagem.png".
     * @param largura Largura do objeto que mostra a imagem (a imagem será redimencionada para caber).
     * @param altura Altura do objeto que mostra a imagem (a imagem será redimencionada para caber).
     * CASO AO SER REDIMENSIONADO A QUALIDADE SEJA PERDIDA, CONSIDERAR USAR O MANIPULADOR IMAGEM PARA A REDIMENSIONAÇÃO E NÃO ESSE MÉTODO.
     */
    public Yimage(String caminho_img_ou_gif, double largura, double altura) {
        super(new Image(new File(caminho_img_ou_gif).toURI().toString()));
        setFitWidth(largura);
        setFitHeight(altura);//PARECE QUE NAO REDIMENSIONA COMO O CARA QUER, TIPO SE A IMG OCUPA 100px E TU BOTA 200 aqui ELE CONTINUA COM 100
    }
    
    
}
