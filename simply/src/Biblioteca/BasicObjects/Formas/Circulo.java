package Biblioteca.BasicObjects.Formas;

import javafx.scene.shape.Circle;
import Biblioteca.Lists.ySimpleMap;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.beans.value.ObservableValue;
import javafx.beans.binding.DoubleBinding;
import Biblioteca.BasicObjects.VisibleObjectHandler;
import Biblioteca.BasicObjects.YobjectEventsHandler;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.transform.Rotate;

public class Circulo extends Circle implements Forma{
    public YobjectEventsHandler yEvents = new YobjectEventsHandler(this);
    public ySimpleMap<String, ObservableValue> yWeak_listeners = new ySimpleMap();
    private YstrokeOcupation yOutsideStrokeOcupation = new YstrokeOcupation();
    private Rotate yRotation = new Rotate(0);
    public DoubleProperty yMax_width = new SimpleDoubleProperty(-1);
    public DoubleProperty yMax_height = new SimpleDoubleProperty(-1);
    
    public Circulo(double raio){
        this(raio, Color.BLACK, 0, Color.BLACK);
    }
    
    public Circulo(double radius, Paint color){
        this(radius, color, 0, Color.BLACK);
    }
    
    public Circulo(double radius, Paint color, double stroke_width, Paint stroke_color){
        this(radius, color, stroke_width, stroke_color, null, true);
    }
    
    public Circulo(double radius, Paint color, double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean correct_location){
        ySetRadius(radius);
        setFill(color);
        ySetStroke(stroke_width, stroke_color, stroke_type, correct_location);
    }
    
    
    
    //----------------------------- SIZE METHODS -----------------------------\\

    /**
     * Sets a new radius to the circle.
     * The difference between this method and the setRadius method (from Oracle)
     * is that the Oracle's one sets the new radius from inside (the center point of the
     * new circle will be the same as the old one, while this method keeps the left upper
     * point as beeing the same in the old and new circles).
     * @param radius The new radius.
     */
    public void ySetRadius(double radius){
        double where_wasX = yGetTranslateX(0);
        double where_wasY = yGetTranslateY(0);
        
        setRadius(radius);
        ySetTranslateX(where_wasX, 0);
        ySetTranslateY(where_wasY, 0);
    }
    
    @Override
    public double yGetWidth(boolean plusStroke) {
        double width = getRadius() * 2;
        if(plusStroke)
            width += yOutsideStrokeOcupation.WIDTH;
        
        return width;
    }
    
    @Override
    public double yGetHeight(boolean plusStroke) {
        double height = getRadius() * 2;
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
        width /= 2;
        width = YshapeHandler.ySizeControler(width, stroke_included, yOutsideStrokeOcupation.WIDTH / 2, yMax_width.get());
                    
        if(correct_location)
            ySetRadius(width);
        else
            setRadius(width);
    }

    @Override
    public void ySetHeight(double height, boolean stroke_included, boolean correct_location) {
        height /= 2;
        height = YshapeHandler.ySizeControler(height, stroke_included, yOutsideStrokeOcupation.HEIGHT / 2, yMax_height.get());
                    
        if(correct_location)
            ySetRadius(height);
        else
            setRadius(height);
    }
    
    
    
    //----------------------------- TRANSLATE METHODS -----------------------------\\
    
    @Override
    public double yGetTranslateX(double pivo) {
        return getTranslateX() + yGetWidth(true)*(pivo - 0.5);
    }

    @Override
    public double yGetTranslateY(double pivo) {
        return getTranslateY() + yGetHeight(true)*(pivo - 0.5);
    }

    @Override
    public void ySetTranslateX(double X, double pivo) {//-0.5 pois o ponto de pivo do circulo é no meio dele
        YshapeHandler.setTranslateX(this, X, pivo - 0.5);
    }

    @Override
    public void ySetTranslateY(double Y, double pivo) {//-0.5 pois o ponto de pivo do circulo é no meio dele
        YshapeHandler.setTranslateY(this, Y, pivo - 0.5);
    }
    
    @Override
    public void ySetPosition(double X, double Y, double pivoX, double pivoY){
        ySetTranslateX(X, pivoX);
        ySetTranslateY(Y, pivoY);
    }
    
    
    
    //----------------------------- STROKE METHODS -----------------------------\\
    
    @Override
    public void ySetStroke(Double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean correct_location) {
        YshapeHandler.ySetStroke(this, stroke_width, stroke_color, stroke_type, correct_location);
    }
    
    @Override
    public YstrokeOcupation yGetStrokeOcupation(){
        return yOutsideStrokeOcupation;
    }
    
    
    
    //----------------------------- ROTATE METHODS -----------------------------\\
    
    @Override
    public Rotate yGetRotate(){
        return yRotation;
    }
    
    @Override
    public void ySetRotate(double angle, double pivoX, double pivoY){
        YshapeHandler.ySetRotate(this, yRotation, angle, pivoX, pivoY);
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
    public void yAddBind(String bind_name, ObservableValue<? extends Number> bind){
        YshapeHandler.yAddBind(yWeak_listeners, bind_name, bind);
    }
    
    @Override
    public DoubleBinding yTranslateXbind(double pivo){
        return YshapeHandler.yTranslateXbind(this, pivo - 0.5);
    }
    
    @Override
    public DoubleBinding yTranslateYbind(double pivo){
        return YshapeHandler.yTranslateYbind(this, pivo - 0.5);
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
    public DoubleBinding yWidthBind(boolean stroke_included){//esses tipo é so pra pegar o valor/ter um listener..., nao pra mudar
        return YshapeHandler.yWidthBind(radiusProperty().multiply(2), stroke_included ? yOutsideStrokeOcupation : null);
    }
    
    @Override
    public DoubleBinding yHeightBind(boolean stroke_included){
        return YshapeHandler.yHeightBind(radiusProperty().multiply(2), stroke_included ? yOutsideStrokeOcupation : null);
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