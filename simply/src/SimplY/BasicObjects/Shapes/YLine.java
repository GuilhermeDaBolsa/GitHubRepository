package SimplY.BasicObjects.Shapes;

import javafx.scene.shape.Line;
import SimplY.Lists.YSimpleMap;
import SimplY.Math.YMath;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeType;
import javafx.scene.shape.StrokeLineCap;
import javafx.beans.value.ObservableValue;
import javafx.beans.binding.DoubleBinding;
import SimplY.BasicObjects.YVisibleObjectHandler;
import SimplY.Interactions.YEventsHandler;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.transform.Rotate;
import SimplY.BasicObjects.YCoolBindings;

public class YLine extends Line implements YShape, YCoolBindings{
    private YEventsHandler yEvents_Handler = new YEventsHandler(this);
    public YSimpleMap<String, ObservableValue> yWeak_listeners = new YSimpleMap();
    
    private YStrokeOcupation yOutsideStrokeOcupation = new YStrokeOcupation();
    private Rotate yRotation = new Rotate(0);
    
    public DoubleProperty yMax_width = new SimpleDoubleProperty(-1);
    public DoubleProperty yMax_height = new SimpleDoubleProperty(-1);
    
    public YLine(Line line){
        this(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY(), line.getStrokeWidth(), line.getStroke());
    }
    
    public YLine(double Xfinal, double Yfinal, double grossura, Paint cor){
        this(0, 0, Xfinal, Yfinal, grossura, cor);
    }
    
    public YLine(double Xinicial, double Yinicial, double Xfinal, double Yfinal, double grossura, Paint cor){
        this(Xinicial, Yinicial, Xfinal, Yfinal, grossura, cor, StrokeType.CENTERED, true);
    }
    
    /**
     * Cria uma linha. (OBS: Não há setFIll nas linhas, sua cor é definida por setStroke e apenas && strokeLineCap é RECOMENDADO sempre ser BUTT).
     * @param Xinicial Coordenada X do inicio da linha.
     * @param Yinicial Coordenada Y do inicio da linha
     * @param Xfinal Coordenada X do final da linha.
     * @param Yfinal Coordenada Y do final da linha
     * @param grossura É a grossura da linha
     * @param cor Cor da linha
     */
    public YLine(double Xinicial, double Yinicial, double Xfinal, double Yfinal, double grossura, Paint cor, StrokeType stroke_type, boolean correct_location){
        ySetInitialPoint(Xinicial, Yinicial);
        ySetEndPoint(Xfinal, Yfinal);
        ySetStroke(grossura, cor, stroke_type, correct_location);
    }
    
    
    
    //----------------------------- SIZE METHODS -----------------------------\\
    
    public void ySetInitialPoint(Double Xinicial, Double Yinicial){
        if(Xinicial != null)
            setStartX(Xinicial);
        if(Yinicial != null)
            setStartY(Yinicial);
    }
    
    public void ySetEndPoint(Double Xfinal, Double Yfinal){
        if(Xfinal != null)
            setEndX(Xfinal);
        if(Yfinal != null)
            setEndY(Yfinal);
        
        if(getStartX() > Xfinal){
            double aux = getStartX();
            setStartX(Xfinal);
            setEndX(aux);
            //NAO SEI PQ  MAS TEM QUE TROCAR O Y TAMBÉM
            aux = getStartY();
            setStartY(Yfinal);
            setEndY(aux);
        }
    }
    
    /**
     * @return The lenght of the line, in other words, the hypotenuses created by the deltaX and deltaY lengths.
     * @see #yGetWidth(boolean)  
     * @see #yGetHeight(boolean) 
     */
    public double yHypotenuse(){
        return YMath.yHypotenuse(yGetWidth(false), yGetHeight(false));
    }
    
    /**
     * @return The difference between the final X point and the initial X point.
     */
    @Override
    public double yGetWidth(boolean plusStroke){
        double delta = YMath.yModulo(getEndX() - getStartX());
        if(plusStroke)
            delta += yOutsideStrokeOcupation.WIDTH;
        
        return delta * getScaleX();
    }
    
    /**
     * @return The difference between the final Y point and the initial Y point.
     */
    @Override
    public double yGetHeight(boolean plusStroke){
        double delta = YMath.yModulo(getEndY() - getStartY());
        if(plusStroke)
            delta += yOutsideStrokeOcupation.HEIGHT;
        
        return delta * getScaleY();
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
        
        ySetEndPoint(getStartX() + width, null);
    }

    @Override
    public void ySetHeight(double height, boolean stroke_included, boolean correct_location) {
        height = YShapeHandler.ySizeControler(height, stroke_included, yOutsideStrokeOcupation.HEIGHT, yMax_height.get());
        
        ySetEndPoint(null, getStartY() + height);
    }
    
    
    
    //----------------------------- TRANSLATE METHODS -----------------------------\\
     //LEMBRAR OU AVISAR O USUARIO DE QUE SE ELE USAR O TRANSLATE DO JAVA PROVAVELMENTE O PONTO QUE SERA MOVIDO É O DA MEIUCA DO STROKE
    //(O IMAGINARIO, QUE ATÉ É BOM TER ISSO EM MAOS)
    
    @Override
    public double yGetTranslateX(double pivo) {
        return (getTranslateX() + getStartX() + YMath.yModulo(getEndX() - getStartX())/2) + yGetWidth(true) * (pivo - 0.5);
    }

    @Override
    public double yGetTranslateY(double pivo) {
        return (getTranslateY() + getStartY() + YMath.yModulo(getEndY() - getStartY())/2) + yGetHeight(true) * (pivo - 0.5);
    }
    
    @Override
    public void ySetTranslateX(double position, double pivo) {
        YShapeHandler.setTranslateX(this, position - getStartX() - YMath.yModulo(getEndX() - getStartX())/2, pivo);
    }

    @Override
    public void ySetTranslateY(double position, double pivo) {
        YShapeHandler.setTranslateY(this, position - getStartY() - YMath.yModulo(getEndY() - getStartY())/2, pivo);
    }
    
    @Override
    public void ySetPosition(double X, double Y, double pivoX, double pivoY){
        ySetTranslateX(X, pivoX);
        ySetTranslateY(Y, pivoY);
    }
    
    
    
    //----------------------------- STROKE METHODS -----------------------------\\

    @Override
    public void ySetStroke(Double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean correct_size) {
        double where_wasX = yGetTranslateX(0);
        double where_wasY = yGetTranslateY(0);
        
        if(correct_size)
            setStrokeLineCap(StrokeLineCap.BUTT);
        YShapeHandler.ySetStroke(this, stroke_width, stroke_color, stroke_type, true);
        
        yOutsideStrokeOcupation = new YStrokeOcupation((yGetHeight(false)/yHypotenuse())*YShapeHandler.yGetStrokeOcupation(this)*2,
                (yGetWidth(false)/yHypotenuse())*YShapeHandler.yGetStrokeOcupation(this)*2);
        
        ySetTranslateX(where_wasX, 0);
        ySetTranslateY(where_wasY, 0);
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
        YShapeHandler.ySetRotate(this, yRotation, angle, getStartX() + pivoX, getStartY() + pivoY);
    }
    
    public void yRotateBy(double angle, double pivoX, double pivoY){
        double points[] = {getStartX(), getStartY(), getEndX(), getEndY()};
        
        pivoX -= getTranslateX();
        pivoY -= getTranslateY();
        
        for (int i = 0; i < points.length; i+=2) {
            double X = points[i] - pivoX;
            double Y = points[i + 1] - pivoY;
            double newX = X * Math.cos(Math.toRadians(angle)) - Y * Math.sin(Math.toRadians(angle)) + pivoX;
            double newY = X * Math.sin(Math.toRadians(angle)) + Y * Math.cos(Math.toRadians(angle)) + pivoY;

            points[i] = newX;
            points[i + 1] = newY;
        }
        ySetInitialPoint(points[0], points[1]);
        ySetEndPoint(points[2], points[3]);
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
        return YShapeHandler.yWidthBind(endXProperty().subtract(startXProperty()), stroke_included ? yOutsideStrokeOcupation : null);
    }
    
    @Override
    public DoubleBinding yHeightBind(boolean stroke_included){
        return YShapeHandler.yHeightBind(endYProperty().subtract(startYProperty()), stroke_included ? yOutsideStrokeOcupation : null);
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