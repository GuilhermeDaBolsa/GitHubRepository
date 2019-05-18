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
        ySetStroke(grossuraBorda, corBorda, stroke_type, move_with_new_stroke_width);
    }
    
    public Retangulo(double largura, double altura, Paint corFundo, double grossuraBorda, Paint corBorda){
        this(largura, altura, corFundo, grossuraBorda, corBorda, StrokeType.CENTERED, true);
    }
    
    /**
     * Cria uma instancia vazia de um retângulo.
     */
    public Retangulo(){
    }
    
    public Retangulo(double largura, double altura){
        this(largura, altura, Color.color(random(1), random(1), random(1)), 0, Color.BLACK);
    }
    
    public Retangulo(double largura, double altura, Paint cor){
        this(largura, altura, cor,  0, Color.BLACK);
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
    public double yGetWidth() {//DA PRA CALCULAR
        return VisibleObjectHandler.getWidth(this);
    }

    @Override
    public double yGetHeight() {//DA PRA CALCULAR
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
        
        setWidth(new_width);
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
        
        setHeight(new_height);
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
