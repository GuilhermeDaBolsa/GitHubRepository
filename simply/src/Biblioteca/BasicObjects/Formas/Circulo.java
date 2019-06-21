package Biblioteca.BasicObjects.Formas;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.beans.value.ObservableValue;
import javafx.beans.binding.DoubleBinding;
import Biblioteca.BasicObjects.VisibleObjectHandler;
import static Biblioteca.LogicClasses.Matematicas.random;
import java.util.HashMap;

public class Circulo extends Circle implements Forma{
    public YstrokeOcupation yOutsideStrokeOcupation = new YstrokeOcupation();
    public HashMap<String, ObservableValue<? extends Number>> yWeak_listeners = new HashMap();
    
    public Circulo(){
        //fazer um standard? atribuir coisas com base no nodo pai? só bota qlcr coisa? deixar em branco?
    }
    
    public Circulo(double raio){
        this(raio, Color.color(random(1), random(1), random(1)), 0, Color.BLACK);
    }
    
    public Circulo(double radius, Paint color){
        this(radius, color, 0, Color.BLACK);
    }
    
    public Circulo(double radius, Paint color, double stroke_width, Paint stroke_color){
        this(radius, color, stroke_width, stroke_color, StrokeType.CENTERED, true);
    }
    
    public Circulo(double radius, Paint color, double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean correct_location){
        ySetRadius(radius);
        setFill(color);
        ySetStroke(stroke_width, stroke_color, stroke_type, correct_location);
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
            width += yOutsideStrokeOcupation.WIDTH.doubleValue();
        
        return width;
    }

    @Override
    public double yGetHeight(boolean plusStroke) {
        double height = getRadius() * 2;
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
        width /= 2;
        if(stroke_included)
            width -= yOutsideStrokeOcupation.WIDTH.doubleValue() / 2;
                    
        if(correct_location)
            ySetRadius(width);
        else
            setRadius(width);
    }

    @Override
    public void ySetHeight(double height, boolean stroke_included, boolean correct_location) {
        height /= 2;
        if(stroke_included)
            height -= yOutsideStrokeOcupation.HEIGHT.doubleValue() / 2;
                    
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
    
    //COLOCAR ISSO NO FORMA E PADRONIZAR E ETC....
    
    public DoubleBinding yTranslateXbind(double pivo){
        return translateXProperty().add(yWidthBind(true).multiply(pivo - 0.5));
    }
    
    public DoubleBinding yTranslateYbind(double pivo){
        return translateYProperty().add(yHeightBind(true).multiply(pivo - 0.5));
    }
    
    public void yBindTranslateX(String bind_name, ObservableValue<? extends Number> X, double pivo){
        X.addListener((observable) -> {
            ySetTranslateX(X.getValue().doubleValue(), pivo);
        });
        yWeak_listeners.put(bind_name, X);
    }
    
    public void yBindTranslateY(String bind_name, ObservableValue<? extends Number> Y, double pivo){
        Y.addListener((observable) -> {
            ySetTranslateY(Y.getValue().doubleValue(), pivo);
        });
        yWeak_listeners.put(bind_name, Y);
    }
    
    public DoubleBinding yWidthBind(boolean stroke_included){//esses tipo é so pra pegar o valor/ter um listener..., nao pra mudar
        DoubleBinding radiusP = radiusProperty().multiply(2);
        if(stroke_included)
            radiusP = radiusP.add(yOutsideStrokeOcupation.WIDTH);
        
        return radiusP;
    }
    
    public DoubleBinding yHeightBind(boolean stroke_included){
        DoubleBinding radiusP = radiusProperty().multiply(2);
        if(stroke_included)
            radiusP = radiusP.add(yOutsideStrokeOcupation.HEIGHT);
        
        return radiusP;
    }
    
    public void yBindWidth(String bind_name, ObservableValue<? extends Number> width, boolean stroke_included){
        width.addListener((observable) -> {
            ySetWidth(width.getValue().doubleValue(), stroke_included, true);
        });
        yWeak_listeners.put(bind_name, width);
    }
    
    public void yBindHeight(String bind_name, ObservableValue<? extends Number> height, boolean stroke_included){
        height.addListener((observable) -> {
            ySetHeight(height.getValue().doubleValue(), stroke_included, true);
        });
        yWeak_listeners.put(bind_name, height);
    }
    
    public void yUnbind(String bind_name){
        yWeak_listeners.remove(bind_name);
        System.gc();
    }
}