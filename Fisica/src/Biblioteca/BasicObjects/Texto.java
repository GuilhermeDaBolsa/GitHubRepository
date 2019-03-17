package Biblioteca.BasicObjects;

import Biblioteca.InteractiveObjects.InteractiveObject;
import java.io.File;
import java.net.MalformedURLException;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.geometry.Point2D;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Texto extends InteractiveObject {

    private String texto;
    public Text nodoTexto = new Text();
    public Font fonte;
    public double largura_texto_total;
    public double altura_texto_total;

    public Texto(String texto, Font fonte, Color cor) {
        this.texto = texto;
        nodoTexto = new Text(texto);
        
        if (fonte != null) {
            nodoTexto.setFont(fonte);
        }
        if(cor == null){
            nodoTexto.setFill(Color.BLACK);
        }else{
            nodoTexto.setFill(cor);
        }
        recalcula_dados();
        
        getChildren().addAll(nodoTexto);
    }

    public Texto(String texto) {
        this.texto = texto;
        nodoTexto.setText(texto);
        nodoTexto.setFill(Color.BLACK);      
        recalcula_dados();
        
        getChildren().addAll(nodoTexto);
    }

    /**
     * @return Retorna a largura do espaço que o texto ocupa.
     */
    public double getLarguraTexto() {
        return nodoTexto.getLayoutBounds().getWidth();
    }

    /**
     * @return Retorna a altura do espaço que o texto ocupa.
     */
    public double getAlturaTexto() {
        return nodoTexto.getLayoutBounds().getHeight();
    }

    /**
     * @return O texto em forma de nodo (visualização).
     */
    public Text getText() {
        return nodoTexto;
    }

    /**
     * @return O texto em forma de String.
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Altera o nodo de texto atual, para um outro.
     * @param text Novo texto.
     */
    public void setText(Text text) {
        this.nodoTexto = text;
        this.texto = text.getText();
        recalcula_dados();
    }

    /**
     * Altera o texto mantendo a formatação.
     *
     * @param texto Novo texto.
     */
    public void setText(String texto) {
        this.nodoTexto.setText(texto);
        this.texto = texto;
        recalcula_dados();
    }

    public void mudar_cor(Paint cor) {
        nodoTexto.setFill(cor);
    }

    public void aparecer_texto(double duracao, Point2D ponto_relativo) {
        Animation animacao = new Transition() {
            {
                setCycleDuration(Duration.seconds(duracao));
            }

            protected void interpolate(double frac) {
                int tamanho_texto = texto.length();
                int n = Math.round(tamanho_texto * (float) frac);
                nodoTexto.setText(texto.substring(0, n));
                getChildren().get(0).setTranslateX(ponto_relativo.getX() - nodoTexto.getLayoutBounds().getWidth()/2);
                getChildren().get(0).setTranslateY(ponto_relativo.getY());
            }
        };
        animacao.play();
    }
    
    public void aparecer_texto_fixo(double duracao_por_caractere) {
        Animation animacao = new Transition() {
            {
                setCycleDuration(Duration.seconds(duracao_por_caractere*texto.length()));
            }

            @Override
            protected void interpolate(double frac) {
                int tamanho_texto = texto.length();
                int n = Math.round(tamanho_texto * (float) frac);
                nodoTexto.setText(texto.substring(0, n));
            }
        };
        animacao.play();
    }
    
    public void reorganiza_texto_conforme_espaco(double espacoX){
        espacoX *= 0.78; //???????? FUNCIONA//
        int tamanhoDeUmDigito = (int) (largura_texto_total / texto.length());
        int digitos_por_linha = (int) (espacoX/tamanhoDeUmDigito);
        String novo_texto = "";
        
        for (int i = 0; i < texto.length(); i++) {
            novo_texto += texto.charAt(i);
            if((i+1) % digitos_por_linha == 0 && i > 0){
                novo_texto += "\n";
            }
        }
        texto = novo_texto;
    }
    
    public static Font carregar_fonte(String caminho_fonte, double tamanho){
        try {
            return Font.loadFont(new File(caminho_fonte).toURI().toURL().toExternalForm(), tamanho);
        } catch (MalformedURLException ex) {
            System.out.println("Deu Pauuuu");
        }
        return Font.font("Arial", tamanho);
    }
    
    private void recalcula_dados(){
        largura_texto_total = getLarguraTexto();
        altura_texto_total = getAlturaTexto();
    }
}
