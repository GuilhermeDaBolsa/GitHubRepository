package Biblioteca.BasicObjects.Formas;

import Biblioteca.BasicObjects.VisibleObjectHandler;
import Biblioteca.LogicClasses.Matematicas;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

public class Linha extends Line implements Forma{
    
    /**
     * Cria uma linha. (OBS: Não há setFIll nas linhas, sua cor é definida por setStroke e apenas).
     * @param Xinicial Coordenada X do inicio da linha.
     * @param Yinicial Coordenada Y do inicio da linha
     * @param Xfinal Coordenada X do final da linha.
     * @param Yfinal Coordenada Y do final da linha
     * @param grossura É a grossura da linha
     * @param cor Cor da linha
     */
    public Linha(double Xinicial, double Yinicial, double Xfinal, double Yfinal, double grossura, Paint cor, StrokeType stroke_type, boolean correct_location){
        setPontoInicial(Xinicial, Yinicial);
        setPontoFinal(Xfinal, Yfinal);
        ySetStroke(grossura, cor, stroke_type, correct_location);
    }
    
    public Linha(double Xinicial, double Yinicial, double Xfinal, double Yfinal, double grossura, Paint cor){
        this(Xinicial, Yinicial, Xfinal, Yfinal, grossura, cor, StrokeType.CENTERED, true);
    }
    
    public Linha(double Xfinal, double Yfinal, double grossura, Paint cor){
        this(0, 0, Xfinal, Yfinal, grossura, cor);
    }
    
    public Linha(Line line){
        this(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY(), line.getStrokeWidth(), line.getStroke());
    }
    
    public Linha(Linha line){
        this((Line) line);
    }
    
    public void setPontoInicial(Double Xinicial, Double Yinicial){
        if(Xinicial != null)
            setStartX(Xinicial);
        if(Yinicial != null)
            setStartY(Yinicial);
    }
    
    public void setPontoFinal(Double Xfinal, Double Yfinal){
        if(Xfinal != null)
            setEndX(Xfinal);
        if(Yfinal != null)
            setEndY(Yfinal);
        
        if(getStartX() > Xfinal){
            double aux = getStartX();
            setStartX(Xfinal);
            setEndX(aux);
        }
    }
    
    /**
     * @return The lenght of the line, in other words, the hypotenuses created by the deltaX and deltaY lengths.
     * @see #yDeltaXpoints(boolean) 
     * @see #yDeltaYpoints(boolean) 
     */
    public double yDeltaHypotenuse(){
        double X = yDeltaXpoints(false);
        double Y = yDeltaYpoints(false);
        
        return Math.sqrt(X*X + Y*Y);
    }
    
    /**
     * @param plusStroke True if you want the stroke width to be counted. False, just the imaginary points.
     * @return The difference between the final X point and the initial X point.
     */
    public double yDeltaXpoints(boolean plusStroke){
        double delta = Matematicas.modulo(getEndX() - getStartX());
        if(!plusStroke)
            return delta;
        return delta + (yDeltaXpoints(false)/yDeltaHypotenuse())*getStrokeWidth();
    }
    
    /**
     * @param plusStroke True if you want the stroke width to be counted. False, just the imaginary points.
     * @return The difference between the final Y point and the initial Y point.
     */
    public double yDeltaYpoints(boolean plusStroke){
        double delta = Matematicas.modulo(getEndY() - getStartY());
        if(!plusStroke)
            return delta;
        return delta + (yDeltaYpoints(false)/yDeltaHypotenuse())*getStrokeWidth();
    }

    /**
     * In some cases, especially when the stroke width is big, it may give the
     * wrong value. If you want just the width of the line with it's stroke you
     * may use the yDeltaXpoints.
     * @return The width that this object occupies in the scene.
     * @see #yDeltaXpoints(boolean)
     */
    @Override
    public double yGetWidth() {
        return VisibleObjectHandler.getWidth(this);
    }

    /**
     * Em alguns casos, caso a grossura seja muito grande, pode retornar a mais do que o correto.
     * @return The height of the entire object (with stroke, etc)
     */
    @Override
    public double yGetHeight() {
        return VisibleObjectHandler.getHeight(this);
    }
    
    @Override
    public double yGetTranslateX(double pivo) {
        return (getTranslateX() + yDeltaXpoints(false)/2) + yDeltaXpoints(true)*(pivo - 0.5);//QUE IMPASSE, USAR O GETWIDTH Q DA O VALOR ERRADO OU DEIXAR OS DELTAS??????
    }

    //LEMBRAR OU AVISAR O USUARIO DE QUE SE ELE USAR O TRANSLATE DO JAVA PROVAVELMENTE O PONTO QUE SERA MOVIDO É O DA MEIUCA DO STROKE (O PONTO ORIGINAL PQ O STROKE SO VEM DPS)
    @Override
    public double yGetTranslateY(double pivo) {
        return (getTranslateY() + yDeltaYpoints(false)/2) + yDeltaYpoints(true)*(pivo - 0.5);//QUE IMPASSE, USAR O GETWIDTH Q DA O VALOR ERRADO OU DEIXAR OS DELTAS??????
    }
    
    @Override
    public double yGetStrokeOcupation() {
        return YshapeHandler.yGetStrokeOcupation(this);
    }

    @Override
    public void ySetTranslateX(double position, double pivo) {
        VisibleObjectHandler.setTranslateX(this, (position - yDeltaXpoints(false)/2) + yDeltaXpoints(true)/2, pivo);//QUE IMPASSE, USAR O GETWIDTH Q DA O VALOR ERRADO OU DEIXAR OS DELTAS??????
    }

    @Override
    public void ySetTranslateY(double position, double pivo) {
        VisibleObjectHandler.setTranslateY(this, (position - yDeltaYpoints(false)/2) + yDeltaYpoints(true)/2, pivo);//QUE IMPASSE, USAR O GETWIDTH Q DA O VALOR ERRADO OU DEIXAR OS DELTAS??????
    }

    @Override
    public void ySetTranslateZ(double position, double pivo) {
        VisibleObjectHandler.setTranslateZ(this, position, pivo);
    }
    
    @Override
    public void ySetWidthWithScale(double width, boolean stroke_included, boolean correct_location) {
        YshapeHandler.ySetWidthWithScale(this, width, stroke_included, correct_location);
    }

    @Override
    public void ySetHeigthWithScale(double height, boolean stroke_included, boolean correct_location) {
        YshapeHandler.ySetHeigthWithScale(this, height, stroke_included, correct_location);
    }
    
    /**
     * 
     * @param stroke_width
     * @param stroke_color
     * @param stroke_type 
     * @param correct_size If a new stroke_width is defined, it will make the line bigger, unless this parameter is true.
     * @see #setStrokeType(javafx.scene.shape.StrokeType) 
     * @see #setStrokeLineCap(javafx.scene.shape.StrokeLineCap) 
     */
    @Override
    public void ySetStroke(Double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean correct_size) {
        YshapeHandler.ySetStroke(this, stroke_width, stroke_color, stroke_type, false);
        if(correct_size)
            setStrokeLineCap(StrokeLineCap.BUTT);
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
}