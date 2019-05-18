package Biblioteca.BasicObjects.Formas;

import Biblioteca.BasicObjects.VisibleObjectHandler;
import Biblioteca.LogicClasses.Matematicas;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
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
    public Linha(double Xinicial, double Yinicial, double Xfinal, double Yfinal, double grossura, Paint cor, StrokeType stroke_type, boolean move_with_new_stroke_width){
        setPontoInicial(Xinicial, Yinicial);
        setPontoFinal(Xfinal, Yfinal);
        ySetStroke(grossura, cor, stroke_type, move_with_new_stroke_width);
    }
    
    public Linha(double Xinicial, double Yinicial, double Xfinal, double Yfinal, double grossura, Paint cor){
        this(Xinicial, Yinicial, Xfinal, Yfinal, grossura, cor, StrokeType.CENTERED, false);
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
    
    public double getWidth(){
        return Matematicas.modulo(getEndX() - getStartX());
    }
    
    public double getHeight(){
        return Matematicas.modulo(getEndY() - getStartY());
    }

    @Override
    public double yGetWidth() {//DA PRA CALCULAR COM BASE NO COMPRIMENTO E NO STROKE
        return VisibleObjectHandler.getWidth(this);
    }

    @Override
    public double yGetHeight() {//DA PRA CALCULAR COM BASE NO COMPRIMENTO E NO STROKE
        return VisibleObjectHandler.getHeigth(this);
    }
    
    @Override
    public void ySetWidth(double width, boolean stroke_included) {
        double new_width = width;
        
        if(stroke_included){
            if(getStrokeType() == StrokeType.CENTERED)
                new_width -= getStrokeWidth();
            else if(getStrokeType() == StrokeType.OUTSIDE)
                new_width -= getStrokeWidth()*2;
        }
        
        setEndX(getStartX() + new_width);
    }

    @Override
    public void ySetHeight(double height, boolean stroke_included) {
        double new_height = height;
        
        if(stroke_included){
            if(getStrokeType() == StrokeType.CENTERED)
                new_height -= getStrokeWidth();
            else if(getStrokeType() == StrokeType.OUTSIDE)
                new_height -= getStrokeWidth()*2;
        }
        
        setEndY(getStartY() + new_height);
    }
    
    @Override
    public double yGetTranslateX(double pivo) {
        return (getTranslateX() + getWidth()/2) + yGetWidth()*(pivo - 0.5);
    }

    @Override
    public double yGetTranslateY(double pivo) {
        return (getTranslateY() + getHeight()/2) + yGetHeight()*(pivo - 0.5);
    }

    @Override
    public void ySetTranslateX(double position, double pivo) {
        VisibleObjectHandler.setTranslateX(this, (position - getWidth()/2) + yGetWidth()/2, pivo);
    }

    @Override
    public void ySetTranslateY(double position, double pivo) {
        VisibleObjectHandler.setTranslateY(this, (position - getHeight()/2) + yGetHeight()/2, pivo);
    }

    @Override
    public void ySetTranslateZ(double position, double pivo) {
        VisibleObjectHandler.setTranslateZ(this, position, pivo);
    }
    
    /**
     * 
     * @param stroke_width
     * @param stroke_color
     * @param stroke_type 
     * @param move_with_new_stroke_width If a new stroke_width is defined, it will "grow from inside" keeping the object where it was, unless this parameter is true.
     * @see #setStrokeType(javafx.scene.shape.StrokeType) 
     */
    @Override
    public void ySetStroke(Double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean move_with_new_stroke_width) {
        double where_wasX = yGetTranslateX(0);
        double where_wasY = yGetTranslateY(0);
        
        if(stroke_color != null)
            setStroke(stroke_color);
        if(stroke_width != null)
            setStrokeWidth(stroke_width);
        if(stroke_type != null)
            setStrokeType(stroke_type);
            
        if(move_with_new_stroke_width){
            ySetTranslateX(where_wasX, 0);
            ySetTranslateY(where_wasY, 0);
        }
    }
}