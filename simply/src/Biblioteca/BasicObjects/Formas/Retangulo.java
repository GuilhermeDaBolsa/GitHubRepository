package Biblioteca.BasicObjects.Formas;

import Biblioteca.BasicObjects.VisibleObjectHandler;
import static Biblioteca.LogicClasses.Matematicas.random;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;


public class Retangulo extends Rectangle implements Forma{//TODAS AS FORMAS TEM METODOS EM COMUM....
    
    public Retangulo(double largura, double altura, Paint corFundo, double grossuraBorda, Paint corBorda, StrokeType stroke_type, boolean move_with_new_stroke_width){
        setWidth(largura);
        setHeight(altura);
        setFill(corFundo);
        ySetStroke(grossuraBorda, corBorda, stroke_type, move_with_new_stroke_width);
    }
    
    public Retangulo(double largura, double altura, Paint corFundo, double grossuraBorda, Paint corBorda){
        this(largura, altura, corFundo, grossuraBorda, corBorda, StrokeType.CENTERED, true);
    }
    
    public Retangulo(double largura, double altura, Paint cor){
        this(largura, altura, cor,  0, Color.BLACK);
    }
    
    public Retangulo(double largura, double altura){
        this(largura, altura, Color.color(random(1), random(1), random(1)), 0, Color.BLACK);
    }
    
    /**
     * Cria uma instancia vazia de um retângulo.
     */
    public Retangulo(){
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
            width -= yGetStrokeOcupation();
        
        setWidth(width);
        
        if(correct_location)
            ySetTranslateX(where_wasX, 0);
    }

    @Override
    public void ySetHeight(double height, boolean stroke_included, boolean correct_location) {
        double where_wasY = yGetTranslateY(0);
        
        if(stroke_included)
            height -= yGetStrokeOcupation();
        
        setHeight(height);
        
        if(correct_location)
            ySetTranslateY(where_wasY, 0);
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
    
    @Override
    public double yGetStrokeOcupation() {
        return YshapeHandler.yGetStrokeOcupation(this);
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
        YshapeHandler.ySetStroke(this, stroke_width, stroke_color, stroke_type, move_with_new_stroke_width);
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
}