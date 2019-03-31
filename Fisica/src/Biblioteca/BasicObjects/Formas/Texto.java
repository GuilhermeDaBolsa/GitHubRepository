package Biblioteca.BasicObjects.Formas;

import Biblioteca.InteractiveObjects.ObjetoInteragivel;
import java.io.File;
import java.net.MalformedURLException;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Texto extends ObjetoInteragivel {
    public Text texto;
    
    private void construtorPai(String texto, Font fonte, Color cor){
        objetoVisivel = new Text(texto);
        this.texto = (Text) objetoVisivel;
        
        if (fonte != null) {
            this.texto.setFont(fonte);
        }
        if(cor == null){
            this.texto.setFill(Color.BLACK);
        }else{
            this.texto.setFill(cor);
        }
        
        this.texto.setTranslateY(getAltura());
        
        getChildren().add(objetoVisivel);
    }
    
    public Texto(String texto, Font fonte, Color cor) {
        construtorPai(texto, fonte, cor);
    }

    public Texto(String texto) {
        construtorPai(texto, null, null);
    }

    /**
     * @return Retorna a largura do espaço que o texto ocupa.
     */
    @Override
    public double getLargura() {//VER SE COM OS METODOS JA DO OBJETO VISIVEL JA N FUNFA, Q DAI SE FUNFA GOD
        return texto.getLayoutBounds().getWidth();
    }

    /**
     * @return Retorna a altura do espaço que o texto ocupa.
     */
    @Override
    public double getAltura() {//VER SE COM OS METODOS JA DO OBJETO VISIVEL JA N FUNFA, Q DAI SE FUNFA GOD
        return texto.getLayoutBounds().getHeight();
    }

    public void mudar_cor(Paint cor) {
        texto.setFill(cor);
    }

    public void aparecer_texto(double duracao, Point2D ponto_relativo) {
        Animation animacao = new Transition() {
            {
                setCycleDuration(Duration.seconds(duracao));
            }

            @Override
            protected void interpolate(double frac) {
                int tamanho_texto = texto.getText().length();
                int n = Math.round(tamanho_texto * (float) frac);
                texto.setText(texto.getText().substring(0, n));
                getChildren().get(0).setTranslateX(ponto_relativo.getX() - texto.getLayoutBounds().getWidth()/2);
                getChildren().get(0).setTranslateY(ponto_relativo.getY());
            }
        };
        animacao.play();
    }
    
    public void aparecer_texto_fixo(double duracao_por_caractere) {
        Animation animacao = new Transition() {
            {
                setCycleDuration(Duration.seconds(duracao_por_caractere * texto.getText().length()));
            }

            @Override
            protected void interpolate(double frac) {
                int tamanho_texto = texto.getText().length();
                int n = Math.round(tamanho_texto * (float) frac);
                texto.setText(texto.getText().substring(0, n));
            }
        };
        animacao.play();
    }
    
    public void reorganiza_texto_conforme_espaco(double espacoX){
        espacoX *= 0.78; //???????? FUNCIONA//
        int tamanhoDeUmDigito = (int) (getLargura() / texto.getText().length());
        int digitos_por_linha = (int) (espacoX/tamanhoDeUmDigito);
        String novo_texto = "";
        
        for (int i = 0; i < texto.getText().length(); i++) {
            novo_texto += texto.getText().charAt(i);
            if((i+1) % digitos_por_linha == 0 && i > 0){
                novo_texto += "\n";
            }
        }
        texto.setText(novo_texto);
    }
    
    public static Font carregar_fonte(String caminho_fonte, double tamanho){
        try {
            return Font.loadFont(new File(caminho_fonte).toURI().toURL().toExternalForm(), tamanho);
        } catch (MalformedURLException ex) {
            System.out.println("Deu Pauuuu");
        }
        return Font.font("Arial", tamanho);
    }
}
