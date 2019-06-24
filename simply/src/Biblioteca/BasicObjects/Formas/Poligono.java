package Biblioteca.BasicObjects.Formas;

import javafx.scene.shape.Polygon;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import java.util.HashMap;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeType;
import javafx.beans.value.ObservableValue;
import javafx.beans.binding.DoubleBinding;
import Biblioteca.BasicObjects.VisibleObjectHandler;
import javafx.beans.property.DoubleProperty;

public class Poligono extends Polygon implements Forma{
    public YstrokeOcupation yOutsideStrokeOcupation = new YstrokeOcupation();
    public HashMap<String, ObservableValue<? extends Number>> yWeak_listeners = new HashMap();
    DoubleProperty left_X;
    DoubleProperty right_X;
    DoubleProperty up_Y;
    DoubleProperty down_Y;
    
    /*public Poligono(int sides){
        
    }*/
    
    public Poligono(double... points){
        super(points);
        if(points.length % 2 != 0)
            getPoints().add(0.0);
        calc_points();
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
        return (getTranslateX() + yGetWidth(false)/2) + yGetWidth(true)*(pivo - 0.5);
    }

    @Override
    public double yGetTranslateY(double pivo) {
        return (getTranslateY() + yGetHeight(false)/2) + yGetHeight(true)*(pivo - 0.5);
    }

    @Override
    public void ySetTranslateX(double position, double pivo) {
        YshapeHandler.setTranslateX(this, (position - yGetWidth(false)/2) + yGetWidth(true)/2, pivo);
    }

    @Override
    public void ySetTranslateY(double position, double pivo) {
        YshapeHandler.setTranslateY(this, (position - yGetHeight(false)/2) + yGetHeight(true)/2, pivo);
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
        
        //MANDA O CALCULATE AI MAGRAO, !NAO SERIA AQUI MAGRAO!
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
    
    //STROKEEEEEEEEEEEEE AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA A A A A A A A 
    
    /**
     * @param points Pontos que formam a forma.
     */
    public void calculate(Double... points){
        if(points.length <= 2){
            System.out.println("Not enought points.");
            return;
        }
        
        yCircularArray<Double> cPoints = new yCircularArray(points);
        
        double left = 0;
        double right = 0;
        double up = 0;
        double down = 0;
        
        for (int i = 0; i < points.length; i+=2) {
            Point2D pN1 = new Point2D(cPoints.get(i-2), cPoints.get(i-1));
            Point2D p0 = new Point2D(cPoints.get(i), cPoints.get(i+1));
            Point2D p1 = new Point2D(cPoints.get(i+2), cPoints.get(i+3));
        }
    }
    
    public void reta_paralela(Point2D a, Point2D b){
            double m = 0;
            double n = 0;
            
            try {
                m = (b.getY() - a.getY()) / (b.getX() - a.getX());
            } catch (Exception e) {
                //VER DEPOIS
            }
//            bOne = p1.getY() - aOne * p1.getX();
//            bNone = pN1.getY() - aNone * pN1.getX();
            
            double reflected_m = -1/m;
            
            double complement = (YshapeHandler.yGetStrokeOcupation(this)/2)/Math.sqrt(1+(reflected_m * reflected_m));
            
            double x1 = a.getX() + complement;
            double y1 = reflected_m * (x1 - a.getX()) + a.getY();
            double x2 = a.getX() - complement;
            double y2 = reflected_m * (x2 - a.getX()) + a.getY();
            
            
    }
    
    public void calculate(ObservableList<Double> points){
        calculate((Double[]) points.toArray());
    }
}