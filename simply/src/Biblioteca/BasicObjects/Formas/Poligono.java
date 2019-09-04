package Biblioteca.BasicObjects.Formas;

import Biblioteca.BasicObjects.YcoolBindings;
import Biblioteca.Lists.yCircularArray;
import javafx.scene.shape.Polygon;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import Biblioteca.Lists.ySimpleMap;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeType;
import javafx.beans.value.ObservableValue;
import javafx.beans.binding.DoubleBinding;
import Biblioteca.BasicObjects.YvisibleObjectHandler;
import Biblioteca.Interactions.YEventsHandler;
import static Biblioteca.LogicClasses.Matematicas.calculate_angle;
import static Biblioteca.LogicClasses.Matematicas.hypotenuse;
import static Biblioteca.LogicClasses.Matematicas.modulo;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.transform.Rotate;

/**
 * This class is an upgrade of Oracle's polygon class.
 * Note that it is highly recommended that the points be in clock-wise order and that the figure
 * is not a complex polygon (self-intersecting) because of border and angle calculations.
 */
public class Poligono extends Polygon implements Yshape, YcoolBindings{
    private YEventsHandler yEvents_Handler = new YEventsHandler(this);
    public ySimpleMap<String, ObservableValue> yWeak_listeners = new ySimpleMap();
    
    private YstrokeOcupation yOutsideStrokeOcupation = new YstrokeOcupation();
    private Rotate yRotation = new Rotate(0);
    
    public DoubleProperty yMax_width = new SimpleDoubleProperty(-1);
    public DoubleProperty yMax_height = new SimpleDoubleProperty(-1);
    
    public DoubleProperty yLeft_X = new SimpleDoubleProperty(0);
    public DoubleProperty yRight_X = new SimpleDoubleProperty(0);
    public DoubleProperty yUp_Y = new SimpleDoubleProperty(0);
    public DoubleProperty yDown_Y = new SimpleDoubleProperty(0);
    
    /*public Poligono(int sides){
        
    }*/
    
    public Poligono(double... points){
        super(points);

        if(points.length % 2 != 0)
            getPoints().add(0.0);
        
        calc_points();
    }
    
    /**
     * Ever call this method when a change in the points is made.
     */
    public void change_in_points(){
        calc_points();
        calculate_stroke(getPoints());
    }
    
    protected void calc_points(){
        yLeft_X.setValue(getPoints().get(0));
        yRight_X.setValue(getPoints().get(0));
        yUp_Y.setValue(getPoints().get(1));
        yDown_Y.setValue(getPoints().get(1));
        for (int i = 0; i < getPoints().size(); i+=2) {
            double X = getPoints().get(i);
            double Y = getPoints().get(i+1);
            
            if(X < yLeft_X.get()){
                yLeft_X.setValue(X);
            }else if(X > yRight_X.get()){
                yRight_X.setValue(X);
            }
            
            if(Y < yUp_Y.get()){
                yUp_Y.setValue(Y);
            }else if(Y > yDown_Y.get()){
                yDown_Y.setValue(Y);
            }
        }
    }
    
    
    
    //----------------------------- SIZE METHODS -----------------------------\\

    /**
     * @return The width based just in the points (yRight_X - yLeft_X).
     */
    @Override
    public double yGetWidth(boolean plusStroke){
        double width = yRight_X.get() - yLeft_X.get();
        if(plusStroke)
            width += yOutsideStrokeOcupation.WIDTH;
        
        return width;
    }
    
    /**
     * @return The height based just in the points (yDown_Y - yUp_Y).
     */
    @Override
    public double yGetHeight(boolean plusStroke){
        double height = yDown_Y.get() - yUp_Y.get();
        if(plusStroke)
            height += yOutsideStrokeOcupation.HEIGHT;
        
        return height;
    }

    @Override
    public double yGetWidth() {
        return YvisibleObjectHandler.yGetWidth(this);
    }

    @Override
    public double yGetHeight() {
        return YvisibleObjectHandler.yGetHeight(this);
    }

    @Override
    public void ySetWidth(double width, boolean stroke_included, boolean correct_location) {
        width = YshapeHandler.ySizeControler(width, stroke_included, yOutsideStrokeOcupation.WIDTH, yMax_width.get());
        
        double pivo = yGetWidth(false)/2;
        double increment = width - yGetWidth(stroke_included);
        
        for (int i = 0; i < getPoints().size(); i+=2) {
            double X = getPoints().get(i);
            getPoints().set(i, X + ((X - yLeft_X.get())/yGetWidth(false)) * increment);
        }
        yRight_X.set(yRight_X.get() + increment);
    }

    @Override
    public void ySetHeight(double height, boolean stroke_included, boolean correct_location) { 
        height = YshapeHandler.ySizeControler(height, stroke_included, yOutsideStrokeOcupation.HEIGHT, yMax_height.get());
        
        double pivo = yGetHeight(false)/2;
        double increment = height - yGetHeight(stroke_included);
        
        for (int i = 1; i < getPoints().size(); i+=2) {
            double Y = getPoints().get(i);
            getPoints().set(i, Y + ((Y - yUp_Y.get())/yGetHeight(false)) * increment);
        }
        yDown_Y.set(yDown_Y.get() + increment);
    }

    
    
    //----------------------------- TRANSLATE METHODS -----------------------------\\
    
    @Override
    public double yGetTranslateX(double pivo) {
        return getTranslateX() - yOutsideStrokeOcupation.LEFT + yLeft_X.get() + yGetWidth(true) * pivo;
    }

    @Override
    public double yGetTranslateY(double pivo) {
        return getTranslateY() - yOutsideStrokeOcupation.UP + yUp_Y.get() + yGetHeight(true) * pivo;
    }
    
    @Override
    public void ySetTranslateX(double position, double pivo) {
        YshapeHandler.setTranslateX(this, position - yLeft_X.get() + yOutsideStrokeOcupation.LEFT, pivo);
    }

    @Override
    public void ySetTranslateY(double position, double pivo) {
        YshapeHandler.setTranslateY(this, position - yUp_Y.get() + yOutsideStrokeOcupation.UP, pivo);
    }
    
    @Override
    public void ySetPosition(double X, double Y, double pivoX, double pivoY){
        ySetTranslateX(X, pivoX);
        ySetTranslateY(Y, pivoY);
    }
    
    
    
    //----------------------------- STROKE METHODS -----------------------------\\

    @Override
    public void ySetStroke(Double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean correct_location) {
        double where_wasX = yGetTranslateX(0);
        double where_wasY = yGetTranslateY(0);
        
        YshapeHandler.ySetStroke(this, stroke_width, stroke_color, stroke_type, correct_location);
        calculate_stroke(getPoints());
        
        if(correct_location){
            ySetTranslateX(where_wasX, 0);
            ySetTranslateY(where_wasY, 0);
        }
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
        YshapeHandler.ySetRotate(this, yRotation, angle, yLeft_X.get() + pivoX, yUp_Y.get() + pivoY);
    }
    
    public void yRotateBy(double angle, double pivoX, double pivoY){
        pivoX -= getTranslateX();
        pivoY -= getTranslateY();
        for (int i = 0; i < getPoints().size(); i+=2) {
            double X = getPoints().get(i) - pivoX;
            double Y = getPoints().get(i + 1) - pivoY;
            double newX = X * Math.cos(Math.toRadians(angle)) - Y * Math.sin(Math.toRadians(angle)) + pivoX;
            double newY = X * Math.sin(Math.toRadians(angle)) + Y * Math.cos(Math.toRadians(angle)) + pivoY;

            getPoints().set(i, newX);
            getPoints().set(i + 1, newY);
        }
        change_in_points();
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
    
    
    
    //----------------------------- EVENTS METHODS -----------------------------\\
    
    @Override
    public YEventsHandler yGetEventsHandler(){
        return yEvents_Handler;
    }
    
    
    
    //----------------------------- BIND/LISTENER METHODS -----------------------------\\
    
    @Override
    public void yAddBind(String bind_name, ObservableValue<? extends Number> bind){
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
        return YshapeHandler.yWidthBind(yRight_X.subtract(yLeft_X), stroke_included ? yOutsideStrokeOcupation : null);
    }  
    
    @Override
    public DoubleBinding yHeightBind(boolean stroke_included){
        return YshapeHandler.yHeightBind(yDown_Y.subtract(yUp_Y), stroke_included ? yOutsideStrokeOcupation : null);
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
    
    private void calculate_stroke(ObservableList<Double> points){
        calculate_stroke(points.toArray());
    }
    
    /**
     * @param points Pontos que formam a forma.
     */
    private void calculate_stroke(Object... points){//ANGULOS MENORES DO QUE 11,475 (achar um valor mais preciso) TEM STROKE = 0 (NAO HA INTERSECÇÃO DE RETAS)
        if(points.length <= 2){
            System.out.println("Not enought points.");//if they are 2, isn't it a line? (ver isso dps)
            return;
        }
        
        yCircularArray<Double> cPoints = new yCircularArray(points);
        
        double left = yLeft_X.get();
        double right = yRight_X.get();
        double up = yUp_Y.get();
        double down = yDown_Y.get();
        for (int i = 0; i < points.length; i+=2) {
            Point2D pN1 = new Point2D(cPoints.get(i-2), cPoints.get(i-1));
            Point2D p0 = new Point2D(cPoints.get(i), cPoints.get(i+1));
            Point2D p1 = new Point2D(cPoints.get(i+2), cPoints.get(i+3));
            Point2D array[] = new Point2D[4];
            double angle = calculate_angle(pN1, p0, p1);
            boolean nulo = false;
            
            if(angle >= 11.475){
                Point2D crossed_array_r1[] = border_points(pN1, p0);
                Point2D crossed_array_r2[] = border_points(p0, p1);

                for (int j = 0; j < 3; j+=2){//little complicated logic to store the 4 crossing points of the border lines
                    for (int k = 0; k < 3; k+=2){
                        array[j + k/2] = calculateInterceptionPoint(crossed_array_r1[j], crossed_array_r1[j + 1], crossed_array_r2[k], crossed_array_r2[k + 1]);
                        if(array[j + k/2] == null)
                            nulo = true;
                    }
                }
                if(!nulo && modulo(angle - 180) < 90){//<90 because in 90º the 4 points forms a perfect square.
                    double distance = 0;
                    int index1 = 0;
                    int index2 = 1;
                    for (int j = 0; j < 2; j++) {
                        for (int k = 0; k < array.length; k++) {
                            if(array[j].distance(array[k]) > distance){
                                distance = array[j].distance(array[k]);
                                index1 = j;
                                index2 = k;
                            }
                        }
                    }
                    array[index1] = array[index2] = null;
                }
            }else{
                Point2D nP = new Point2D((pN1.getX() + p1.getX())/2, (pN1.getY() + p1.getY())/2);
                double height = modulo(nP.getY() - p0.getY());
                double width = modulo(nP.getX() - p0.getX());
                double hypotenuse = hypotenuse(width, height);
                double widS = height / hypotenuse * YshapeHandler.yGetStrokeOcupation(this);
                double heigS = width / hypotenuse * YshapeHandler.yGetStrokeOcupation(this);
                
                array[0] = new Point2D(p0.getX() - widS, p0.getY() - heigS);
                array[1] = new Point2D(p0.getX() + widS, p0.getY() + heigS);
                array[2] = new Point2D(p0.getX() + widS, p0.getY() - heigS);
                array[3] = new Point2D(p0.getX() - widS, p0.getY() + heigS);
            }
            
            for (int j = 0; j < array.length; j++) {
                Point2D point = array[j];
                
                if(point == null)
                    continue;

                if(point.getX() < left)
                    left = point.getX();
                else if(point.getX() > right)
                    right = point.getX();

                if(point.getY() < up)
                    up = point.getY();
                else if(point.getY() > down)
                    down = point.getY();
            }
        }
        
        yOutsideStrokeOcupation = new YstrokeOcupation(yLeft_X.get() - left, right - yRight_X.get(), yUp_Y.get() - up, down - yDown_Y.get());
    }
    
    private Point2D calculateInterceptionPoint(Point2D p1, Point2D p2, Point2D d1, Point2D d2) {
        double A1 = p1.getY() - p2.getY();
        double B1 = p2.getX() - p1.getX();
        double C1 = p1.getX() * p2.getY() - p2.getX() * p1.getY();
        
        double A2 = d1.getY() - d2.getY();
        double B2 = d2.getX() - d1.getX();
        double C2 = d1.getX() * d2.getY() - d2.getX() * d1.getY();
        
        if(A2 * B1 - A1 * B2 == 0)
            return null;
        
        double X;
        double Y = (A1 * C2 - A2 * C1) / (A2 * B1 - A1 * B2);
        
        if(A1 == 0)
            X = (-B2 * Y - C2) / A2;
        else
            X = (-B1 * Y - C1) / A1;

        return new Point2D(X, Y);
    }
    
    //calculate the points that might belong to the stroke line
    private Point2D[] border_points(Point2D a, Point2D b){
        Point2D crossed_array[] = new Point2D[4];
        double stroke_ocupation = YshapeHandler.yGetStrokeOcupation(this);
        
        //cases for 0 and 90 degrees lines
        if(a.getY() - b.getY() == 0){//equals 0 because it may serve for other cases beyond just equal
            crossed_array[0] = new Point2D(a.getX(), a.getY() + stroke_ocupation);
            crossed_array[1] = new Point2D(b.getX(), b.getY() + stroke_ocupation);
            crossed_array[2] = new Point2D(a.getX(), a.getY() - stroke_ocupation);
            crossed_array[3] = new Point2D(b.getX(), b.getY() - stroke_ocupation);
            
        }else if(a.getX() - b.getX() == 0){
            crossed_array[0] = new Point2D(a.getX() - stroke_ocupation, a.getY());
            crossed_array[1] = new Point2D(b.getX() - stroke_ocupation, b.getY());
            crossed_array[2] = new Point2D(a.getX() + stroke_ocupation, a.getY());
            crossed_array[3] = new Point2D(b.getX() + stroke_ocupation, b.getY());
            
        }else{
            //an almost general case :)
            double m = (b.getY() - a.getY()) / (b.getX() - a.getX());// coeficiente da reta criada pelos 2 pontos
            double inverse_m = -1/m;//coeficiente da reta perpendicular a reta criada pelos 2 pontos

            //do a sistem to discover the 2 points of a possible RETA of the stroke
            double complement = stroke_ocupation/Math.sqrt(1+(inverse_m * inverse_m)); //+/- result
            
            //the 4 possible points for the RETA of the stroke calculated by the sistem (+/- because it is x²)
            double a1x = a.getX() + complement;
            double a1y = inverse_m * (a1x - a.getX()) + a.getY();
            double a2x = a.getX() - complement;
            double a2y = inverse_m * (a2x - a.getX()) + a.getY();
            
            double b1x = b.getX() + complement;
            double b1y = inverse_m * (b1x - b.getX()) + b.getY();
            double b2x = b.getX() - complement;
            double b2y = inverse_m * (b2x - b.getX()) + b.getY();
            
            crossed_array[0] = new Point2D(a1x, a1y);
            crossed_array[1] = new Point2D(b1x, b1y);
            crossed_array[2] = new Point2D(a2x, a2y);
            crossed_array[3] = new Point2D(b2x, b2y);
        }

        return crossed_array;
    }
}