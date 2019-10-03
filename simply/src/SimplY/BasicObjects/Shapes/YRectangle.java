package SimplY.BasicObjects.Shapes;

import javafx.scene.shape.Rectangle;
import SimplY.Lists.YSimpleMap;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.beans.value.ObservableValue;
import javafx.beans.binding.DoubleBinding;
import SimplY.BasicObjects.YVisibleObjectHandler;
import SimplY.Interactions.YEventsHandler;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.transform.Rotate;
import SimplY.BasicObjects.YCoolBindings;

public class YRectangle extends Rectangle implements YShape, YCoolBindings{
    private YEventsHandler yEvents_Handler = new YEventsHandler(this);
    public YSimpleMap<String, ObservableValue> yWeak_listeners = new YSimpleMap();
    
    private YStrokeOcupation yOutsideStrokeOcupation = new YStrokeOcupation();
    private Rotate yRotation = new Rotate(0);
    
    public DoubleProperty yMax_width = new SimpleDoubleProperty(-1);
    public DoubleProperty yMax_height = new SimpleDoubleProperty(-1);
    
    public YRectangle(double largura, double altura){
        this(largura, altura, Color.BLACK);
    }
    
    public YRectangle(double largura, double altura, Paint cor){
        this(largura, altura, cor,  0, Color.BLACK);
    }
    
    public YRectangle(double largura, double altura, Paint corFundo, double grossuraBorda, Paint corBorda){
        this(largura, altura, corFundo, grossuraBorda, corBorda, null, true);
    }
    
    public YRectangle(double largura, double altura, Paint corFundo, double grossuraBorda, Paint corBorda, StrokeType stroke_type, boolean correct_location){
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
        
        return width * getScaleX();
    }
    
    @Override
    public double yGetHeight(boolean plusStroke){
        double height = getHeight();
        if(plusStroke)
            height += yOutsideStrokeOcupation.HEIGHT;
        
        return height * getScaleY();
    }
    
    @Override
    public double yGetWidth() {
        return YVisibleObjectHandler.yGetWidth(this);
    }

    @Override
    public double yGetHeight() {
        return YVisibleObjectHandler.yGetHeight(this);
    }
    
    @Override
    public void ySetWidth(double width, boolean stroke_included, boolean correct_location) {
        width = YShapeHandler.ySizeControler(width, stroke_included, yOutsideStrokeOcupation.WIDTH, yMax_width.get());
        
        setWidth(width);
    }

    @Override
    public void ySetHeight(double height, boolean stroke_included, boolean correct_location) {
        height = YShapeHandler.ySizeControler(height, stroke_included, yOutsideStrokeOcupation.HEIGHT, yMax_height.get());
        
        setHeight(height);
    }
    
    
    
    //----------------------------- TRANSLATE METHODS -----------------------------\\
    
    @Override
    public double yGetTranslateX(double pivo) {
        return getTranslateX() + getWidth()/2 + yGetWidth(true) * (pivo - 0.5);
    }

    @Override
    public double yGetTranslateY(double pivo) {
        return getTranslateY() + getHeight()/2 + yGetHeight(true) * (pivo - 0.5);
    }
    
    @Override
    public void ySetTranslateX(double position, double pivo) {
        YShapeHandler.setTranslateX(this, position - getWidth()/2, pivo);
    }

    @Override
    public void ySetTranslateY(double position, double pivo) {
        YShapeHandler.setTranslateY(this, position - getHeight()/2, pivo);
    }
    
    @Override
    public void ySetPosition(double X, double Y, double pivoX, double pivoY){
        ySetTranslateX(X, pivoX);
        ySetTranslateY(Y, pivoY);
    }
    
    
    
    //----------------------------- STROKE METHODS -----------------------------\\

    @Override
    public void ySetStroke(Double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean correct_location) {
        YShapeHandler.ySetStroke(this, stroke_width, stroke_color, stroke_type, correct_location);
    }
    
    @Override
    public YStrokeOcupation yGetStrokeOcupation(){
        return yOutsideStrokeOcupation;
    }
    
    
    
    //----------------------------- ROTATE METHODS -----------------------------\\
    
    @Override
    public Rotate yGetRotate(){
        return yRotation;
    }
    
    @Override
    public void ySetRotate(double angle, double pivoX, double pivoY){
        YShapeHandler.ySetRotate(this, yRotation, angle, pivoX, pivoY);
    }
    
    
    
    //----------------------------- SCALE METHODS -----------------------------\\
    
    @Override
    public void ySetScaleX(double scale, boolean correct_location) {
        YShapeHandler.ySetScaleX(this, scale, correct_location);
    }

    @Override
    public void ySetScaleY(double scale, boolean correct_location) {
        YShapeHandler.ySetScaleY(this, scale, correct_location);
    }

    @Override
    public void yScaleXby(double multiplier, boolean correct_location) {
        YShapeHandler.yScaleXby(this, multiplier, correct_location);
    }

    @Override
    public void yScaleYby(double multiplier, boolean correct_location) {
        YShapeHandler.yScaleYby(this, multiplier, correct_location);
    }
    
    @Override
    public void ySetWidthWithScale(double width, boolean stroke_included, boolean correct_location) {
        YShapeHandler.ySetWidthWithScale(this, width, stroke_included, correct_location);
    }

    @Override
    public void ySetHeigthWithScale(double height, boolean stroke_included, boolean correct_location) {
        YShapeHandler.ySetHeigthWithScale(this, height, stroke_included, correct_location);
    }

    
    
    //----------------------------- EVENTS METHODS -----------------------------\\
    
    @Override
    public YEventsHandler yGetEventsHandler(){
        return yEvents_Handler;
    }
    

    
    //----------------------------- BIND/LISTENER METHODS -----------------------------\\
    
    @Override
    public void yAddBind(String bind_name, ObservableValue bind){
        YShapeHandler.yAddBind(yWeak_listeners, bind_name, bind);
    }
    
    @Override
    public DoubleBinding yTranslateXbind(double pivo){
        return YShapeHandler.yTranslateXbind(this, pivo);
    }
    
    @Override
    public DoubleBinding yTranslateYbind(double pivo){
        return YShapeHandler.yTranslateYbind(this, pivo);
    }
    
    @Override
    public void yBindTranslateX(String bind_name, ObservableValue<? extends Number> X, double pivo){
        YShapeHandler.yBindTranslateX(this, yWeak_listeners, bind_name, X, pivo);
    }
    
    @Override
    public void yBindTranslateY(String bind_name, ObservableValue<? extends Number> Y, double pivo){
        YShapeHandler.yBindTranslateY(this, yWeak_listeners, bind_name, Y, pivo);
    }
    
    @Override
    public DoubleBinding yWidthBind(boolean stroke_included){
        return YShapeHandler.yWidthBind(widthProperty().add(0), stroke_included ? yOutsideStrokeOcupation : null);
    }
    
    @Override
    public DoubleBinding yHeightBind(boolean stroke_included){
        return YShapeHandler.yHeightBind(heightProperty().add(0), stroke_included ? yOutsideStrokeOcupation : null);
    }
    
    @Override
    public void yBindWidth(String bind_name, ObservableValue<? extends Number> width, boolean stroke_included){
        YShapeHandler.yBindWidth(this, yWeak_listeners, bind_name, width, stroke_included);
    }
    
    @Override
    public void yBindHeight(String bind_name, ObservableValue<? extends Number> height, boolean stroke_included){
        YShapeHandler.yBindHeight(this, yWeak_listeners, bind_name, height, stroke_included);
    }
    
    @Override
    public void yUnbind(String bind_name){
        YShapeHandler.yUnbind(yWeak_listeners, bind_name);
    }
}