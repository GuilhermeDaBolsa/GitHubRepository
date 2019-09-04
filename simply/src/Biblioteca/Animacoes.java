package Biblioteca;

import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * This class is not completed, it is under development and it is not recomended to use it.
 */
public class Animacoes {
    /**
     * Anima o tamanho de um objeto (a escala).
     * 
     * @param nodo O objeto.
     * @param largura Largura que o objeto terá no ápice da animação (irá retornar
     * ao tamanho original após o ápice, sempre).
     * @param altura Altura que o objeto terá no ápice da animação (irá retornar
     * ao tamanho original após o ápice, sempre).
     * @param duracao Duração da animação em segundos.
     */
    public static void animacao_escala(Node nodo, double largura, double altura, double duracao) {
        ScaleTransition animacao_tamanho = new ScaleTransition(Duration.seconds(duracao), nodo);
        ScaleTransition animacao_tamanho2 = new ScaleTransition(Duration.seconds(0.2), nodo);
        
        animacao_tamanho.setToX(largura);
        animacao_tamanho.setToY(altura);
        animacao_tamanho.setOnFinished((ActionEvent e) -> {
            animacao_tamanho2.setToX(1);
            animacao_tamanho2.setToY(1);
            animacao_tamanho2.play();
        });
        animacao_tamanho.play();
    }
    
    public static void animacaoMovimento(Node nodo, double segundos, Double X, Double Y) {
        TranslateTransition XYtransicao = new TranslateTransition(Duration.seconds(segundos), nodo);
        if(X != null){
            XYtransicao.setToX(X);
        }
        if(Y != null){
            XYtransicao.setToY(Y);
        }
        XYtransicao.play();
    }
    
    
    
    //-------------------- TAVA NA CLASSE LINHA ----------------------\\
    
    /**
     * Faz a animação de crescimento da linha a partir do centro.
     * @param segundos Quantos segundos dura a animação
     * @param ao_finalizar_animacao O que fazer ao finalizar a animação.
     */
    public void iniciarAnimacaoTamanho(Line linha, double segundos, Runnable ao_finalizar_animacao) {
        ScaleTransition animacao_linha = new ScaleTransition(Duration.seconds(segundos), linha);
        animacao_linha.setToY(1);
        if (ao_finalizar_animacao != null) {
            animacao_linha.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    ao_finalizar_animacao.run();
                }
            });
        }
        animacao_linha.play();
    }
    
    /**
     * Muda o tamanho da linha para suportar o novo numero de botões.
     * @param segundos Duração da animação
     * @param novo_tamanho Novo tamanho da linha
     */
    public void iniciarAnimacaoEscala(Line linha, double segundos, double tamanho_inicial, double novo_tamanho) {
        double novo_tam_perU = novo_tamanho / tamanho_inicial;
        
        ScaleTransition animacao_tamanho = new ScaleTransition(Duration.seconds(segundos), linha);
        animacao_tamanho.setToY(novo_tam_perU);
        iniciarAnimacaoXY(linha, segundos, Double.NaN, (novo_tamanho - tamanho_inicial) /2);
        animacao_tamanho.play();
    }

    /**
     * Muda a posicao da linha
     * @param segundos Duração da animação
     * @param X Ponto em X para onde a linha irá
     * @param Y Ponto em Y para onde a linha irá
     */
    public void iniciarAnimacaoXY(Line linha, double segundos, double X, double Y) {
        TranslateTransition XYtransicao = new TranslateTransition(Duration.seconds(segundos), linha);
        XYtransicao.setToX(X);
        XYtransicao.setToY(Y);
        XYtransicao.play();
    }
    
    
    
    //-------------------- TAVA NA CLASSE TEXTO ----------------------\\
    
    public void aparecer_texto(Text texto, double duracao, Point2D ponto_relativo) {
        Animation animacao = new Transition() {
            {
                setCycleDuration(Duration.seconds(duracao));
            }

            @Override
            protected void interpolate(double frac) {
                int tamanho_texto = texto.getText().length();
                int n = Math.round(tamanho_texto * (float) frac);
                texto.setText(texto.getText().substring(0, n));
                texto.setTranslateX(ponto_relativo.getX() - texto.getLayoutBounds().getWidth()/2);
                texto.setTranslateY(ponto_relativo.getY());
            }
        };
        animacao.play();
    }
    
    public void aparecer_texto_fixo(Text texto, double duracao_por_caractere) {
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
    
    public void reorganiza_texto_conforme_espaco(Text texto, double espacoX){
        espacoX *= 0.78; //???????? FUNCIONA//
        int tamanhoDeUmDigito = (int) (texto.getLayoutBounds().getWidth() / texto.getText().length());
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
}
