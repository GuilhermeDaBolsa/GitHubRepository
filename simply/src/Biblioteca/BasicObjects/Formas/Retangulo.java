package Biblioteca.BasicObjects.Formas;

import Biblioteca.BasicObjects.VisibleObjectHandler;
import static Biblioteca.LogicClasses.Matematicas.random;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;


public class Retangulo extends Rectangle implements Forma{//TODAS AS FORMAS TEM METODOS EM COMUM....
    public YstrokeOcupation yOutsideStrokeOcupation = new YstrokeOcupation();
    
    public Retangulo(double largura, double altura, Paint corFundo, double grossuraBorda, Paint corBorda, StrokeType stroke_type, boolean correct_location){
        setWidth(largura);
        setHeight(altura);
        setFill(corFundo);
        ySetStroke(grossuraBorda, corBorda, stroke_type, correct_location);
    }
    
    public Retangulo(double largura, double altura, Paint corFundo, double grossuraBorda, Paint corBorda){
        this(largura, altura, corFundo, grossuraBorda, corBorda, StrokeType.CENTERED, true);
    }
    
    public Retangulo(double largura, double altura, Paint cor){
        this(largura, altura, cor,  0, Color.BLACK);
    }
    
    public Retangulo(double largura, double altura){
        this(largura, altura, Color.color(random(1), random(1), random(1)));
    }
    
    /**
     * Cria uma instancia vazia de um retângulo.
     */
    public Retangulo(){
    }
    
    /**
     * @return The width of this shape.
     */
    @Override
    public double yGetWidth(boolean plusStroke){
        double width = getWidth();
        if(plusStroke)
            width += yOutsideStrokeOcupation.WIDTH.doubleValue();
        
        return width;
    }
    
    /**
     * @return The height of this shape.
     */
    @Override
    public double yGetHeight(boolean plusStroke){
        double height = getHeight();
        if(plusStroke)
            height += yOutsideStrokeOcupation.HEIGHT.doubleValue();
        
        return height;
    }
    
    @Override
    public double yGetWidth() {
        return VisibleObjectHandler.getWidth(this);
    }

    @Override
    public double yGetHeight() {
        return VisibleObjectHandler.getHeight(this);
    }
    
    @Override
    public void ySetWidth(double width, boolean stroke_included, boolean correct_location) {
        double where_wasX = yGetTranslateX(0);
        
        if(stroke_included)
            width -= yOutsideStrokeOcupation.WIDTH.doubleValue();
        
        setWidth(width);
        
        if(correct_location)
            ySetTranslateX(where_wasX, 0);
    }

    @Override
    public void ySetHeight(double height, boolean stroke_included, boolean correct_location) {
        double where_wasY = yGetTranslateY(0);
        
        if(stroke_included)
            height -= yOutsideStrokeOcupation.HEIGHT.doubleValue();
        
        setHeight(height);
        
        if(correct_location)
            ySetTranslateY(where_wasY, 0);
    }
    
    @Override
    public double yGetTranslateX(double pivo) {
        return (getTranslateX() + yGetWidth(false)/2) + yGetWidth(true)*(pivo - 0.5);
    }

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
    public void ySetPosition(double X, double Y, double pivoX, double pivoY){
        ySetTranslateX(X, pivoX);
        ySetTranslateY(Y, pivoY);
    }

    /**
     * 
     * @param stroke_width
     * @param stroke_color
     * @param stroke_type 
     * @param correct_location If a new stroke_width is defined, it will "grow from inside" keeping the object where it was, unless this parameter is true.
     * @see #setStrokeType(javafx.scene.shape.StrokeType) 
     */
    @Override
    public void ySetStroke(Double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean correct_location) {
        YshapeHandler.ySetStroke(this, stroke_width, stroke_color, stroke_type, yOutsideStrokeOcupation, correct_location);
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
    
    public void ySetCornerRoundness(double roudness){
        setArcWidth(roudness);
        setArcHeight(roudness);
    }
}