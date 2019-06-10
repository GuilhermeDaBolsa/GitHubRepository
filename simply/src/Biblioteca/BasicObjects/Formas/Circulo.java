package Biblioteca.BasicObjects.Formas;

import Biblioteca.BasicObjects.VisibleObjectHandler;
import static Biblioteca.LogicClasses.Matematicas.random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class Circulo extends Circle implements Forma, Cloneable{
    public YstrokeOcupation yStrokeOcupation = new YstrokeOcupation();
    
    public Circulo(double radius, Paint color, double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean correct_location){
        ySetRadius(radius);
        setFill(color);
        ySetStroke(stroke_width, stroke_color, stroke_type, correct_location);
    }
    
    public Circulo(double radius, Paint color, double stroke_width, Paint stroke_color){
        this(radius, color, stroke_width, stroke_color, StrokeType.CENTERED, true);
    }
    
    public Circulo(double radius, Paint color){
        this(radius, color, 0, Color.BLACK);
    }
    
    public Circulo(double raio){
        this(raio, Color.color(random(1), random(1), random(1)), 0, Color.BLACK);
    }
    
    /**
     * Cria uma instancia vazia de um circulo.
     */
    public Circulo(){
    }
    
    @Override
    public Circulo clone(){
        try {
            return (Circulo) super.clone();
        } catch (CloneNotSupportedException ex) {
            System.out.println("CloneNotSupportedException on line 42, class yCircle");
        }
        return null;
    }

    public void ySetRadius(double raio){
        double where_wasX = yGetTranslateX(0);
        double where_wasY = yGetTranslateY(0);
        
        setRadius(raio);
        ySetTranslateX(where_wasX, 0);
        ySetTranslateY(where_wasY, 0);
    }
    
    @Override
    public double yGetWidth(boolean plusStroke) {
        double width = getRadius() * 2;
        if(plusStroke)
            width += yStrokeOcupation.WIDTH;
        
        return width;
    }

    @Override
    public double yGetHeight(boolean plusStroke) {
        double height = getRadius() * 2;
        if(plusStroke)
            height += yStrokeOcupation.HEIGHT;
        
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
        width /= 2;
        if(stroke_included)
            width -= yStrokeOcupation.WIDTH/2;
                    
        if(correct_location)
            ySetRadius(width);
        else
            setRadius(width);
    }

    @Override
    public void ySetHeight(double height, boolean stroke_included, boolean correct_location) {
        height /= 2;
        if(stroke_included)
            height -= yStrokeOcupation.HEIGHT/2;
                    
        if(correct_location)
            ySetRadius(height);
        else
            setRadius(height);
    }
    
    @Override
    public double yGetTranslateX(double pivo) {
        return getTranslateX() + yGetWidth(true)*(pivo - 0.5);
    }

    @Override
    public double yGetTranslateY(double pivo) {
        return getTranslateY() + yGetHeight(true)*(pivo - 0.5);
    }

    @Override
    public void ySetTranslateX(double position, double pivo) {//-0.5 pois o ponto de pivo do circulo é no meio dele
        YshapeHandler.setTranslateX(this, position, pivo - 0.5);
    }

    @Override
    public void ySetTranslateY(double position, double pivo) {//-0.5 pois o ponto de pivo do circulo é no meio dele
        YshapeHandler.setTranslateY(this, position, pivo - 0.5);
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
     * @param correct_location If a new stroke_width is defined, it will "grow from inside" keeping the object where it was, unless this parameter is true.
     * @see #setStrokeType(javafx.scene.shape.StrokeType) 
     */
    @Override
    public void ySetStroke(Double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean correct_location) {
        YshapeHandler.ySetStroke(this, stroke_width, stroke_color, stroke_type, correct_location);
        
        double real_stroke_width = YshapeHandler.yGetStrokeOcupation(this);
        yStrokeOcupation = new YstrokeOcupation(real_stroke_width, real_stroke_width);
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
