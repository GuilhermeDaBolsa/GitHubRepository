package Biblioteca.BasicObjects.Formas;

import Biblioteca.InteractiveObjects.ObjetoInteragivel;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Linha extends ObjetoInteragivel {
    public Line linha;
    
    /**
     * Cria uma linha.
     * @param Xinicial Coordenada X do inicio da linha.
     * @param Yinicial Coordenada Y do inicio da linha
     * @param Xfinal Coordenada X do final da linha.
     * @param Yfinal Coordenada Y do final da linha
     * @param grossura É a grossura da linha
     * @param cor Cor da linha
     */
    public Linha(double Xinicial, double Yinicial, double Xfinal, double Yfinal, double grossura, Paint cor){
        linha = new Line(Xinicial, Yinicial, Xfinal, Yfinal);
        setBorda(grossura, cor);

        this.getChildren().add(linha);
    }
    
    public Linha(double Xfinal, double Yfinal, double grossura, Paint cor){
        this(0, 0, Xfinal, Yfinal, grossura, cor);
    }
    
    public Linha(Linha line){
        this(line.linha.getStartX(), line.linha.getStartY(), line.linha.getEndX(), line.linha.getEndY(), line.linha.getStrokeWidth(), line.linha.getStroke());
    }
    
    public void setInitialPoint(double Xinicial, double Yinicial){
        if(Xinicial != Double.NaN)
            linha.setStartX(Xinicial);
        if(Yinicial != Double.NaN)
            linha.setStartY(Yinicial);
    }
    
    public void setFinalPoint(double Xfinal, double Yfinal){
        if(Xfinal != Double.NaN)
            linha.setEndX(Xfinal);
        if(Yfinal != Double.NaN)
            linha.setEndY(Yfinal);
    }
    
    public void setBorda(double grossura, Paint cor){
        linha.setStroke(cor);
        linha.setStrokeWidth(grossura);
        linha.setTranslateX(grossura/2);
        linha.setTranslateY(grossura/2);
    }

    /**
     * Faz a animação de crescimento da linha a partir do centro.
     * @param segundos Quantos segundos dura a animação
     * @param ao_finalizar_animacao O que fazer ao finalizar a animação.
     */
    public void iniciarAnimacaoTamanho(double segundos, Runnable ao_finalizar_animacao) {
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
    public void iniciarAnimacaoEscala(double segundos, double tamanho_inicial, double novo_tamanho) {
        double novo_tam_perU = novo_tamanho / tamanho_inicial;
        
        ScaleTransition animacao_tamanho = new ScaleTransition(Duration.seconds(segundos), linha);
        animacao_tamanho.setToY(novo_tam_perU);
        iniciarAnimacaoXY(segundos, Double.NaN, (novo_tamanho - tamanho_inicial) /2);
        animacao_tamanho.play();
    }

    /**
     * Muda a posicao da linha
     * @param segundos Duração da animação
     * @param X Ponto em X para onde a linha irá
     * @param Y Ponto em Y para onde a linha irá
     */
    public void iniciarAnimacaoXY(double segundos, double X, double Y) {
        TranslateTransition XYtransicao = new TranslateTransition(Duration.seconds(segundos), linha);
        XYtransicao.setToX(X);
        XYtransicao.setToY(Y);
        XYtransicao.play();
    }
}