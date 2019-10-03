package SimplY.BasicObjects.Shapes;

import javafx.scene.shape.Circle;
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

public class YCircle extends Circle implements YShape, YCoolBindings{
    private YEventsHandler yEvents_Handler = new YEventsHandler(this);
    public YSimpleMap<String, ObservableValue> yWeak_listeners = new YSimpleMap();
    
    private YStrokeOcupation yOutsideStrokeOcupation = new YStrokeOcupation();
    private Rotate yRotation = new Rotate(0);
    
    public DoubleProperty yMax_width = new SimpleDoubleProperty(-1);
    public DoubleProperty yMax_height = new SimpleDoubleProperty(-1);
    
    public YCircle(double raio){
        this(raio, Color.BLACK, 0, Color.BLACK);
    }
    
    public YCircle(double radius, Paint color){
        this(radius, color, 0, Color.BLACK);
    }
    
    public YCircle(double radius, Paint color, double stroke_width, Paint stroke_color){
        this(radius, color, stroke_width, stroke_color, null, true);
    }
    
    public YCircle(double radius, Paint color, double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean correct_location){
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
        
        return width * getScaleX();
    }
    
    @Override
    public double yGetHeight(boolean plusStroke) {
        double height = getRadius() * 2;
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
        width /= 2;
        width = YShapeHandler.ySizeControler(width, stroke_included, yOutsideStrokeOcupation.WIDTH / 2, yMax_width.get());
                    
        if(correct_location)
            ySetRadius(width);
        else
            setRadius(width);
    }

    @Override
    public void ySetHeight(double height, boolean stroke_included, boolean correct_location) {
        height /= 2;
        height = YShapeHandler.ySizeControler(height, stroke_included, yOutsideStrokeOcupation.HEIGHT / 2, yMax_height.get());
                    
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
    public void ySetTranslateX(double X, double pivo) {
        YShapeHandler.setTranslateX(this, X, pivo);
    }

    @Override
    public void ySetTranslateY(double Y, double pivo) {
        YShapeHandler.setTranslateY(this, Y, pivo);
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
    public void yAddBind(String bind_name, ObservableValue<? extends Number> bind){
        YShapeHandler.yAddBind(yWeak_listeners, bind_name, bind);
    }
    
    @Override
    public DoubleBinding yTranslateXbind(double pivo){
        return YShapeHandler.yTranslateXbind(this, pivo - 0.5);
    }
    
    @Override
    public DoubleBinding yTranslateYbind(double pivo){
        return YShapeHandler.yTranslateYbind(this, pivo - 0.5);
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
    public DoubleBinding yWidthBind(boolean stroke_included){//esses tipo é so pra pegar o valor/ter um listener..., nao pra mudar
        return YShapeHandler.yWidthBind(radiusProperty().multiply(2), stroke_included ? yOutsideStrokeOcupation : null);
    }
    
    @Override
    public DoubleBinding yHeightBind(boolean stroke_included){
        return YShapeHandler.yHeightBind(radiusProperty().multiply(2), stroke_included ? yOutsideStrokeOcupation : null);
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