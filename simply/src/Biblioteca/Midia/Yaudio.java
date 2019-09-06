package Biblioteca.Midia;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Shortcut to create an audio object.
 */
public class Yaudio {//FAZER UM PLAYER PRA FICA VISIVEL NÉÈÈÈÈÈÈÈÈÈ!!!!!!!!
    public MediaPlayer player;
    
    public Yaudio(String endereco_musica){
        player = new MediaPlayer(new Media(new File(endereco_musica).toURI().toString()));
    }
    
    /**
     * Repetirá o áudio automaticamente após tocá-lo.
     * @param vezes Número de vezes que o áudio repetirá (-1 para repetir até parar manualmente).
     */
    /*public void repetir(int vezes){
        player.setCycleCount(vezes);
    }*/
}
