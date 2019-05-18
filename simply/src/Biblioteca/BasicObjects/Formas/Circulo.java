package Biblioteca.BasicObjects.Formas;

import Biblioteca.BasicObjects.VisibleObjectHandler;
import static Biblioteca.LogicClasses.Matematicas.random;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class Circulo extends Circle implements Forma{
    
    public Circulo(double radius, Paint color, double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean move_with_new_stroke_width){
        ySetRadius(radius);
        setFill(color);
        ySetStroke(stroke_width, stroke_color, stroke_type, move_with_new_stroke_width);
    }
    
    public Circulo(double radius, Paint color, double stroke_width, Paint stroke_color){
        this(radius, color, stroke_width, stroke_color, StrokeType.CENTERED, true);
    }
    
    public Circulo(double radius, Paint color){
        ySetRadius(radius);
        setFill(color);
    }
    
    /**
     * Cria uma instancia vazia de um circulo.
     */
    public Circulo(){
    }

    public Circulo(double raio){
        this(raio, Color.color(random(1), random(1), random(1)), 0, Color.BLACK);
    }
    
    public void ySetRadius(double raio){
        double where_wasX = yGetTranslateX(0);
        double where_wasY = yGetTranslateY(0);
        
        setRadius(raio);
        ySetTranslateX(where_wasX, 0);
        ySetTranslateY(where_wasY, 0);
    }

    @Override
    public double yGetWidth() {
        return VisibleObjectHandler.getWidth(this);
    }

    @Override
    public double yGetHeight() {
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
        
        ySetRadius(new_width/2);
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
        
        ySetRadius(new_height/2);
    }

    @Override
    public double yGetTranslateX(double pivo) {
        return getTranslateX() + yGetWidth()*(pivo - 0.5);
    }

    @Override
    public double yGetTranslateY(double pivo) {
        return getTranslateY() + yGetHeight()*(pivo - 0.5);
    }

    @Override
    public void ySetTranslateX(double position, double pivo) {//-0.5 pois o ponto de pivo do circulo é no meio dele
        VisibleObjectHandler.setTranslateX(this, position, pivo - 0.5);
    }

    @Override
    public void ySetTranslateY(double position, double pivo) {//-0.5 pois o ponto de pivo do circulo é no meio dele
        VisibleObjectHandler.setTranslateY(this, position, pivo - 0.5);
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
