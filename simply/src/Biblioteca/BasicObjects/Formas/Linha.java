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
    public YstrokeOcupation yOutsideStrokeOcupation = new YstrokeOcupation();
    
    /**
     * Cria uma linha. (OBS: Não há setFIll nas linhas, sua cor é definida por setStroke e apenas && strokeLineCap é RECOMENDADO sempre ser BUTT).
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
            //NAO SEI PQ  MAS TEM QUE TROCAR O Y TAMBÉM
            aux = getStartY();
            setStartY(Yfinal);
            setEndY(aux);
        }
    }
    
    /**
     * @return The lenght of the line, in other words, the hypotenuses created by the deltaX and deltaY lengths.
     * @see #yGetWidth(boolean)  
     * @see #yGetHeight(boolean) 
     */
    public double yDeltaHypotenuse(){
        double X = yGetWidth(false);
        double Y = yGetHeight(false);
        
        return Math.sqrt(X*X + Y*Y);
    }
    
    /**
     * @return The difference between the final X point and the initial X point.
     */
    public double yGetWidth(boolean plusStroke){
        double delta = Matematicas.modulo(getEndX() - getStartX());
        if(plusStroke)
            delta += yOutsideStrokeOcupation.WIDTH;
        
        return delta;
    }
    
    /**
     * @return The difference between the final Y point and the initial Y point.
     */
    public double yGetHeight(boolean plusStroke){
        double delta = Matematicas.modulo(getEndY() - getStartY());
        if(plusStroke)
            delta += yOutsideStrokeOcupation.HEIGHT;
        
        return delta;
    }

    /**
     * If you want just the width of the line and maybe it's stroke, consider using the yGetWidth(boolean).
     * @return The width that this object occupies in the scene.
     * @see #yGetWidth(boolean)
     */
    @Override
    public double yGetWidth() {
        return VisibleObjectHandler.getWidth(this);
    }

    /**
     * If you want just the height of the line and maybe it's stroke, consider using the yGetHeight(boolean).
     * @return The height that this object occupies in the scene.
     * @see #yGetHeight(boolean) 
     */
    @Override
    public double yGetHeight() {
        return VisibleObjectHandler.getHeight(this);
    }
    
    
    @Override
    public void ySetWidth(double width, boolean stroke_included, boolean correct_location) {
        if(width < 0)
            width = -width;
        
        if(stroke_included)
            width -= yOutsideStrokeOcupation.WIDTH;
        
        setPontoFinal(getStartX() + width, null);
    }

    @Override
    public void ySetHeight(double height, boolean stroke_included, boolean correct_location) {
        if(stroke_included)
            height += height > 0 ? -yOutsideStrokeOcupation.HEIGHT : yOutsideStrokeOcupation.HEIGHT;
            
        if(getStartY() > getEndY())
            height = -height;
            
        setPontoFinal(null, getStartY() + height);
    }
    
    @Override
    public double yGetTranslateX(double pivo) {
        return (getTranslateX() + yGetWidth(false)/2) + yGetWidth(true)*(pivo - 0.5);
    }

    //LEMBRAR OU AVISAR O USUARIO DE QUE SE ELE USAR O TRANSLATE DO JAVA PROVAVELMENTE O PONTO QUE SERA MOVIDO É O DA MEIUCA DO STROKE
    //(O IMAGINARIO, QUE ATÉ É BOM TER ISSO EM MAOS)
    
    @Override
    public double yGetTranslateY(double pivo) {
        return (getTranslateY() + yGetHeight(false)/2) + yGetHeight(true)*(pivo - 0.5);
    }
    
    @Override
    public void ySetTranslateX(double position, double pivo) {
        YshapeHandler.setTranslateX(this, (position - yGetWidth(false)/2) + yGetWidth(true)/2, pivo);
    }

    @Override
    public void ySetTranslateY(double position, double pivo) {
        YshapeHandler.setTranslateY(this, (position - yGetHeight(false)/2) + yGetHeight(true)/2, pivo);
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
     * @param correct_size If a new stroke_width is defined, it will make the line bigger, unless this parameter is true (TRUE HIGLY RECOMENDED).
     * @see #setStrokeType(javafx.scene.shape.StrokeType) 
     * @see #setStrokeLineCap(javafx.scene.shape.StrokeLineCap) 
     */
    @Override
    public void ySetStroke(Double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean correct_size) {
        double where_wasX = yGetTranslateX(0.5);
        double where_wasY = yGetTranslateY(0.5);
        
        if(correct_size)
            setStrokeLineCap(StrokeLineCap.BUTT);
        YshapeHandler.ySetStroke(this, stroke_width, stroke_color, stroke_type, yOutsideStrokeOcupation, false);
        
        yOutsideStrokeOcupation = new YstrokeOcupation((yGetHeight(false)/yDeltaHypotenuse())*getStrokeWidth(),
                (yGetWidth(false)/yDeltaHypotenuse())*getStrokeWidth());
        
        ySetTranslateX(where_wasX, 0.5);
        ySetTranslateY(where_wasY, 0.5);
    }
    
    @Override
    public void ySetScaleX(double scale, boolean correct_location) {
        YshapeHandler.ySetScaleX(this, scale, correct_location);
    }

    @Override
    public void ySetScaleY(double scale, boolean correct_location) {
        YshapeHandler.ySetScaleY(this, scale, correct_location);
    }

    @Override
    public void yScaleXby(double multiplier, boolean correct_location) {
        YshapeHandler.yScaleXby(this, multiplier, correct_location);
    }

    @Override
    public void yScaleYby(double multiplier, boolean correct_location) {
        YshapeHandler.yScaleYby(this, multiplier, correct_location);
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