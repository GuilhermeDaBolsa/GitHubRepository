package Biblioteca.BasicObjects.Formas;

import Biblioteca.BasicObjects.VisibleObjectHandler;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Linha extends Line implements Forma{
    
    /**
     * Cria uma linha. (OBS: SEMPRE CRIE UMA LINHA DA ESQUERDA PRA DIREITA, OU DE CIMA PARA BAIXO, NUNCA O CONTRÁRIO).
     * @param Xinicial Coordenada X do inicio da linha.
     * @param Yinicial Coordenada Y do inicio da linha
     * @param Xfinal Coordenada X do final da linha.
     * @param Yfinal Coordenada Y do final da linha
     * @param grossura É a grossura da linha
     * @param cor Cor da linha
     */
    public Linha(double Xinicial, double Yinicial, double Xfinal, double Yfinal, double grossura, Paint cor){
        super(Xinicial, Yinicial, Xfinal, Yfinal);
        setStroke(grossura, cor);
    }
    
    public Linha(double Xfinal, double Yfinal, double grossura, Paint cor){
        this(0, 0, Xfinal, Yfinal, grossura, cor);
    }
    
    public Linha(Line line){
        this(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY(), line.getStrokeWidth(), line.getStroke());
    }
    
    public Linha(Linha line){
        this(line.getInicioX(), line.getInicioY(), line.getFimX(), line.getFimY(), line.getStrokeWidth(), line.getStroke());
    }
    
    public double getInicioX(){
        return getStartX();
    }
    
    public double getInicioY(){
        return getStartY();
    }
    
    public double getFimX(){
        return getEndX();
    }
    
    public double getFimY(){
        return getEndY();
    }
    
    public void setPontoInicial(double Xinicial, double Yinicial){
        if(Xinicial != Double.NaN)
            setStartX(Xinicial);
        if(Yinicial != Double.NaN)
            setStartY(Yinicial);
    }
    
    public void setPontoFinal(double Xfinal, double Yfinal){
        if(Xfinal != Double.NaN)
            setEndX(Xfinal);
        if(Yfinal != Double.NaN)
            setEndY(Yfinal);
    }

    /**
     * Faz a animação de crescimento da linha a partir do centro.
     * @param segundos Quantos segundos dura a animação
     * @param ao_finalizar_animacao O que fazer ao finalizar a animação.
     */
    public void iniciarAnimacaoTamanho(double segundos, Runnable ao_finalizar_animacao) {
        ScaleTransition animacao_linha = new ScaleTransition(Duration.seconds(segundos), this);
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
        
        ScaleTransition animacao_tamanho = new ScaleTransition(Duration.seconds(segundos), this);
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
        TranslateTransition XYtransicao = new TranslateTransition(Duration.seconds(segundos), this);
        XYtransicao.setToX(X);
        XYtransicao.setToY(Y);
        XYtransicao.play();
    }

    @Override
    public double getWidth() {//DA PRA CALCULAR COM BASE NO RADIUS E NO STROKE
        return VisibleObjectHandler.getWidth(this);
    }

    @Override
    public double getHeight() {//DA PRA CALCULAR COM BASE NO RADIUS E NO STROKE
        return VisibleObjectHandler.getHeigth(this);
    }

    @Override
    public void setTranslateX(double position, double pivo) {
        VisibleObjectHandler.setTranslateX(this, pivo, position);
    }

    @Override
    public void setTranslateY(double position, double pivo) {
        VisibleObjectHandler.setTranslateY(this, pivo, position);
    }

    @Override
    public void setTranslateZ(double position, double pivo) {
        VisibleObjectHandler.setTranslateZ(this, pivo, position);
    }

    @Override
    public void setStroke(Double grossura, Paint cor) {
        if(cor != null)
            setStroke(cor);
        if(grossura != null){
            setStrokeWidth(grossura);
            setTranslateX(getTranslateX() + grossura/2);///grossura borda?????????
            setTranslateY(getTranslateY() + grossura/2);///grossura borda?????????
        }
    }
}