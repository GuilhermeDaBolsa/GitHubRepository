package Biblioteca.BasicObjects.Formas;

import javafx.scene.shape.Rectangle;
import Biblioteca.Lists.ySimpleMap;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.beans.value.ObservableValue;
import javafx.beans.binding.DoubleBinding;
import Biblioteca.BasicObjects.VisibleObjectHandler;

public class Retangulo extends Rectangle implements Forma{//TODAS AS FORMAS TEM METODOS EM COMUM....
    public YstrokeOcupation yOutsideStrokeOcupation = new YstrokeOcupation();
    public ySimpleMap<String, ObservableValue> yWeak_listeners = new ySimpleMap();
    
    public Retangulo(){
        //fazer um standard? atribuir coisas com base no nodo pai? só bota qlcr coisa? deixar em branco?
    }
    
    public Retangulo(double largura, double altura){
        this(largura, altura, Color.BLACK);
    }
    
    public Retangulo(double largura, double altura, Paint cor){
        this(largura, altura, cor,  0, Color.BLACK);
    }
    
    public Retangulo(double largura, double altura, Paint corFundo, double grossuraBorda, Paint corBorda){
        this(largura, altura, corFundo, grossuraBorda, corBorda, null, true);
    }
    
    public Retangulo(double largura, double altura, Paint corFundo, double grossuraBorda, Paint corBorda, StrokeType stroke_type, boolean correct_location){
        setWidth(largura);
        setHeight(altura);
        setFill(corFundo);
        ySetStroke(grossuraBorda, corBorda, stroke_type, correct_location);
    }
    
    public void ySetCornerRoundness(double roudness){
        setArcWidth(roudness);
        setArcHeight(roudness);
    }
    
    
    
    //----------------------------- SIZE METHODS -----------------------------\\
    
    @Override
    public double yGetWidth(boolean plusStroke){
        double width = getWidth();
        if(plusStroke)
            width += yOutsideStrokeOcupation.WIDTH;
        
        return width;
    }
    
    @Override
    public double yGetHeight(boolean plusStroke){
        double height = getHeight();
        if(plusStroke)
            height += yOutsideStrokeOcupation.HEIGHT;
        
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
            width -= yOutsideStrokeOcupation.WIDTH;
        
        setWidth(width);
        
        if(correct_location)
            ySetTranslateX(where_wasX, 0);
    }

    @Override
    public void ySetHeight(double height, boolean stroke_included, boolean correct_location) {
        double where_wasY = yGetTranslateY(0);
        
        if(stroke_included)
            height -= yOutsideStrokeOcupation.HEIGHT;
        
        setHeight(height);
        
        if(correct_location)
            ySetTranslateY(where_wasY, 0);
    }
    
    
    
    //----------------------------- TRANSLATE METHODS -----------------------------\\
    
    @Override
    public double yGetTranslateX(double pivo) {
        return getTranslateX() - yOutsideStrokeOcupation.LEFT + yGetWidth(true) * pivo;
    }

    @Override
    public double yGetTranslateY(double pivo) {
        return getTranslateY() - yOutsideStrokeOcupation.UP + yGetHeight(true) * pivo;
    }
    
    @Override
    public void ySetTranslateX(double position, double pivo) {
        YshapeHandler.setTranslateX(this, position + yOutsideStrokeOcupation.LEFT, pivo);
    }

    @Override
    public void ySetTranslateY(double position, double pivo) {
        YshapeHandler.setTranslateY(this, position + yOutsideStrokeOcupation.UP, pivo);
    }
    
    @Override
    public void ySetPosition(double X, double Y, double pivoX, double pivoY){
        ySetTranslateX(X, pivoX);
        ySetTranslateY(Y, pivoY);
    }
    
    
    
    //----------------------------- STROKE METHODS -----------------------------\\

    @Override
    public void ySetStroke(Double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean correct_location) {
        YshapeHandler.ySetStroke(this, stroke_width, stroke_color, stroke_type, yOutsideStrokeOcupation, correct_location);
    }
    
    
    
    //----------------------------- SCALE METHODS -----------------------------\\
    
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
    

    
    //----------------------------- BIND/LISTENER METHODS -----------------------------\\
    
    @Override
    public void yAddBind(String bind_name, ObservableValue bind){
        YshapeHandler.yAddBind(yWeak_listeners, bind_name, bind);
    }
    
    @Override
    public DoubleBinding yTranslateXbind(double pivo){
        return YshapeHandler.yTranslateXbind(this, pivo);
    }
    
    @Override
    public DoubleBinding yTranslateYbind(double pivo){
        return YshapeHandler.yTranslateYbind(this, pivo);
    }
    
    @Override
    public void yBindTranslateX(String bind_name, ObservableValue<? extends Number> X, double pivo){
        YshapeHandler.yBindTranslateX(this, yWeak_listeners, bind_name, X, pivo);
    }
    
    @Override
    public void yBindTranslateY(String bind_name, ObservableValue<? extends Number> Y, double pivo){
        YshapeHandler.yBindTranslateY(this, yWeak_listeners, bind_name, Y, pivo);
    }
    
    @Override
    public DoubleBinding yWidthBind(boolean stroke_included){
        return YshapeHandler.yWidthBind(widthProperty().add(0), stroke_included ? yOutsideStrokeOcupation : null);
    }
    
    @Override
    public DoubleBinding yHeightBind(boolean stroke_included){
        return YshapeHandler.yHeightBind(heightProperty().add(0), stroke_included ? yOutsideStrokeOcupation : null);
    }
    
    @Override
    public void yBindWidth(String bind_name, ObservableValue<? extends Number> width, boolean stroke_included){
        YshapeHandler.yBindWidth(this, yWeak_listeners, bind_name, width, stroke_included);
    }
    
    @Override
    public void yBindHeight(String bind_name, ObservableValue<? extends Number> height, boolean stroke_included){
        YshapeHandler.yBindHeight(this, yWeak_listeners, bind_name, height, stroke_included);
    }
    
    @Override
    public void yUnbind(String bind_name){
        YshapeHandler.yUnbind(yWeak_listeners, bind_name);
    }
}