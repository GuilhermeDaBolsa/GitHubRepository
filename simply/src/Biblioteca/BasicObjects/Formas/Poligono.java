package Biblioteca.BasicObjects.Formas;

import Biblioteca.Lists.yCircularArray;
import javafx.scene.shape.Polygon;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import Biblioteca.Lists.ySimpleMap;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeType;
import javafx.beans.value.ObservableValue;
import javafx.beans.binding.DoubleBinding;
import Biblioteca.BasicObjects.VisibleObjectHandler;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Poligono extends Polygon implements Forma{
    public YstrokeOcupation yOutsideStrokeOcupation = new YstrokeOcupation();
    public ySimpleMap<String, ObservableValue<? extends Number>> yWeak_listeners = new ySimpleMap();
    
    DoubleProperty left_X = new SimpleDoubleProperty(0);
    DoubleProperty right_X = new SimpleDoubleProperty(0);
    DoubleProperty up_Y = new SimpleDoubleProperty(0);
    DoubleProperty down_Y = new SimpleDoubleProperty(0);
    
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
        left_X.setValue(getPoints().get(0));
        right_X.setValue(getPoints().get(0));
        up_Y.setValue(getPoints().get(1));
        down_Y.setValue(getPoints().get(1));
        for (int i = 0; i < getPoints().size(); i+=2) {
            double X = getPoints().get(i);
            double Y = getPoints().get(i+1);
            
            if(X < left_X.get()){
                left_X.setValue(X);
            }else if(X > right_X.get()){
                right_X.setValue(X);
            }
            
            if(Y < up_Y.get()){
                up_Y.setValue(Y);
            }else if(Y > down_Y.get()){
                down_Y.setValue(Y);
            }
        }
    }
    
    
    
    //----------------------------- SIZE METHODS -----------------------------\\

    /**
     * @return The width based just in the points (right_X - left_X).
     */
    @Override
    public double yGetWidth(boolean plusStroke){
        double width = right_X.get() - left_X.get();
        if(plusStroke)
            width += yOutsideStrokeOcupation.WIDTH.get();
        
        return width;
    }
    
    /**
     * @return The height based just in the points (down_Y - up_Y).
     */
    @Override
    public double yGetHeight(boolean plusStroke){
        double height = down_Y.get() - up_Y.get();
        if(plusStroke)
            height += yOutsideStrokeOcupation.HEIGHT.get();
        
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
        if(stroke_included)
            width -= yOutsideStrokeOcupation.WIDTH.get()/2;
        
        double pivo = yGetWidth(false)/2;
        double increment = width - right_X.get();
        
        for (int i = 0; i < getPoints().size(); i+=2) {
            double X = getPoints().get(i);
            getPoints().set(i, X + ((X - left_X.get())/yGetWidth(false)) * increment);
        }
        right_X.set(right_X.get() + increment);
    }

    @Override
    public void ySetHeight(double height, boolean stroke_included, boolean correct_location) {
        if(stroke_included)
            height -= yOutsideStrokeOcupation.HEIGHT.get()/2;
        
        double pivo = yGetHeight(false)/2;
        double increment = height - down_Y.get();
        
        for (int i = 1; i < getPoints().size(); i+=2) {
            double Y = getPoints().get(i);
            getPoints().set(i, Y + ((Y - up_Y.get())/yGetHeight(false)) * increment);
        }
        down_Y.set(down_Y.get() + increment);
    }

    
    
    //----------------------------- TRANSLATE METHODS -----------------------------\\
    
    @Override
    public double yGetTranslateX(double pivo) {
        return getTranslateX() - yOutsideStrokeOcupation.LEFT.get() + left_X.get() + yGetWidth(true) * pivo;
    }

    @Override
    public double yGetTranslateY(double pivo) {
        return getTranslateY() - yOutsideStrokeOcupation.UP.get() + up_Y.get() + yGetHeight(true) * pivo;
    }
    
    @Override
    public void ySetTranslateX(double position, double pivo) {
        YshapeHandler.setTranslateX(this, position - left_X.get() + yOutsideStrokeOcupation.LEFT.get(), pivo);
    }

    @Override
    public void ySetTranslateY(double position, double pivo) {
        YshapeHandler.setTranslateY(this, position - up_Y.get() + yOutsideStrokeOcupation.UP.get(), pivo);
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
        
        YshapeHandler.ySetStroke(this, stroke_width, stroke_color, stroke_type, yOutsideStrokeOcupation, correct_location);
        calculate_stroke(getPoints());
        
        if(correct_location){
            ySetTranslateX(where_wasX, 0);
            ySetTranslateY(where_wasY, 0);
        }
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
        return YshapeHandler.yWidthBind(right_X.subtract(left_X), stroke_included ? yOutsideStrokeOcupation : null);
    }  
    
    @Override
    public DoubleBinding yHeightBind(boolean stroke_included){
        return YshapeHandler.yHeightBind(down_Y.subtract(up_Y), stroke_included ? yOutsideStrokeOcupation : null);
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
    public void calculate_stroke(Object... points){//ANGULOS MENORES DO QUE 11,475 (achar um valor mais preciso) TEM STROKE = 0 (NAO HA INTERSECÇÃO DE RETAS)
        if(points.length <= 2){
            System.out.println("Not enought points.");//if they are 2, isn't it a line? (ver isso dps)
            return;
        }
        
        yCircularArray<Double> cPoints = new yCircularArray(points);
        
        double left = left_X.get();
        double right = right_X.get();
        double up = up_Y.get();
        double down = down_Y.get();
        
        for (int i = 0; i < points.length; i+=2) {
            Point2D pN1 = new Point2D(cPoints.get(i-2), cPoints.get(i-1));
            Point2D p0 = new Point2D(cPoints.get(i), cPoints.get(i+1));
            Point2D p1 = new Point2D(cPoints.get(i+2), cPoints.get(i+3));
            
            Point2D crossed_array_r1[] = border_points(pN1, p0);
            Point2D crossed_array_r2[] = border_points(p0, p1);
            
            for (int j = 0; j < 3; j+=2) {
                for (int k = 0; k < 3; k+=2) {
                    Point2D point = calculateInterceptionPoint(crossed_array_r1[j], crossed_array_r1[j + 1], crossed_array_r2[k], crossed_array_r2[k + 1]);
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
            
            yOutsideStrokeOcupation = new YstrokeOcupation(left_X.get() - left, right - right_X.get(), up_Y.get() - up, down - down_Y.get());
        }
    }
    
    public static Point2D calculateInterceptionPoint(Point2D p1, Point2D p2, Point2D d1, Point2D d2) {
        double A1 = p1.getY() - p2.getY();
        double B1 = p2.getX() - p1.getX();
        double C1 = p1.getX() * p2.getY() - p2.getX() * p1.getY();
        
        double A2 = d1.getY() - d2.getY();
        double B2 = d2.getX() - d1.getX();
        double C2 = d1.getX() * d2.getY() - d2.getX() * d1.getY();
        
        if(A2 * B1 - A1 * B2 == 0)
            return null;
        
        double X = 0;
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
        
        //cases for 0 and 90 degrees lines
        if(a.getY() - b.getY() == 0){//equals 0 because it may serve for other cases beyond just equal
            crossed_array[0] = new Point2D(a.getX(), a.getY() + YshapeHandler.yGetStrokeOcupation(this));
            crossed_array[1] = new Point2D(b.getX(), b.getY() + YshapeHandler.yGetStrokeOcupation(this));
            crossed_array[2] = new Point2D(a.getX(), a.getY() - YshapeHandler.yGetStrokeOcupation(this));
            crossed_array[3] = new Point2D(b.getX(), b.getY() - YshapeHandler.yGetStrokeOcupation(this));
            
        }else if(a.getX() - b.getX() == 0){
            crossed_array[0] = new Point2D(a.getX() - YshapeHandler.yGetStrokeOcupation(this), a.getY());
            crossed_array[1] = new Point2D(b.getX() - YshapeHandler.yGetStrokeOcupation(this), b.getY());
            crossed_array[2] = new Point2D(a.getX() + YshapeHandler.yGetStrokeOcupation(this), a.getY());
            crossed_array[3] = new Point2D(b.getX() + YshapeHandler.yGetStrokeOcupation(this), b.getY());
            
        }else{
            //an almost general case :)
            double m = (b.getY() - a.getY()) / (b.getX() - a.getX());// coeficiente da reta criada pelos 2 pontos
            double inverse_m = -1/m;//coeficiente da reta perpendicular a reta criada pelos 2 pontos

            //do a sistem to discover the 2 points of a possible RETA of the stroke
            double complement = YshapeHandler.yGetStrokeOcupation(this)/Math.sqrt(1+(inverse_m * inverse_m)); //+/- result
            
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
    
    /*private double[] reta_paralela(Point2D a, Point2D b){
        double m = 0;
        double n = 0;

        m = (b.getY() - a.getY()) / (b.getX() - a.getX());// coeficiente da reta criada pelos 2 pontos

        double inverse_m = -1/m;//coeficiente da reta perpendicular a reta criada pelos 2 pontos

        //do a sistem to discover the 2 points of a possible RETA of the stroke
        double complement = (YshapeHandler.yGetStrokeOcupation(this)/2)/Math.sqrt(1+(inverse_m * inverse_m)); //+/- result

        //the 2 possible points for the RETA of the stroke calculated by the sistem (+/- because it is x²)
        double x1 = a.getX() + complement;
        double y1 = inverse_m * (x1 - a.getX()) + a.getY();
        double x2 = a.getX() - complement;
        double y2 = inverse_m * (x2 - a.getX()) + a.getY();

        double n1 = -m * x1 + y1;
        double n2 = -m * x2 + y2;

        //retorna os m's e n's para comparar com as proximas retas que vierem
        double arr[] = {m, n1, n2};
        return arr;
    }*/
}