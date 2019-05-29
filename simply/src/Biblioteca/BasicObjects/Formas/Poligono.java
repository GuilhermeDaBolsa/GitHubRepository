package Biblioteca.BasicObjects.Formas;

import Biblioteca.BasicObjects.VisibleObjectHandler;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

public class Poligono extends Polygon implements Forma{
    double left_X;
    double right_X;
    double up_Y;
    double down_Y;
    
    /*public Poligono(int sides){
        
    }*/
    
    public Poligono(double... points){
        super(points);
        if(points.length % 2 != 0)
            getPoints().add(0.0);
        calc_points();
    }
    
    protected void calc_points(){
        left_X = getPoints().get(0);
        right_X = getPoints().get(0);
        up_Y = getPoints().get(1);
        down_Y = getPoints().get(1);
        for (int i = 0; i < getPoints().size(); i+=2) {
            double X = getPoints().get(i);
            double Y = getPoints().get(i+1);
            
            if(X < left_X){
                left_X = X;
            }else if(X > right_X){
                right_X = X;
            }
            
            if(Y < up_Y){
                up_Y = Y;
            }else if(Y > down_Y){
                down_Y = Y;
            }
        }
    }

    /**
     * @return The width base just in the points (right_X - left_X).
     */
    public double getWidth(){
        return right_X - left_X;
    }
    
    /**
     * @return The height base just in the points (down_Y - up_Y).
     */
    public double getHeight(){
        return down_Y - up_Y;
    }

    /**
     * @return The width that the object ocuppies in the scene.
     */
    @Override
    public double yGetWidth() {
        return VisibleObjectHandler.getWidth(this);
    }

    /**
     * @return The height that the object ocuppies in the scene.//PODE DAR A RESPOSTA ERRADAAAAAAAAAAAAAAAAAAAAAAA
     */
    @Override
    public double yGetHeight() {
        return VisibleObjectHandler.getHeight(this);
    }

    @Override
    public void ySetWidth(double width, boolean stroke_included, boolean correct_location) {
        if(stroke_included)
            width -= yGetStrokeOcupation()/2;
        
        double pivo = getWidth()/2;
        double increment = width - right_X;
        
        for (int i = 0; i < getPoints().size(); i+=2) {
            double X = getPoints().get(i);
            getPoints().set(i, X + ((X - left_X)/getWidth()) * increment);
        }
        right_X += increment;
    }

    @Override
    public void ySetHeight(double height, boolean stroke_included, boolean correct_location) {
        if(stroke_included)
            height -= yGetStrokeOcupation()/2;
        
        double pivo = getHeight()/2;
        double increment = height - down_Y;
        
        for (int i = 1; i < getPoints().size(); i+=2) {
            double Y = getPoints().get(i);
            getPoints().set(i, Y + ((Y - up_Y)/getHeight()) * increment);
        }
        down_Y += increment;
    }

    @Override
    public double yGetTranslateX(double pivo) {
        return (getTranslateX() + getWidth()/2) + yGetWidth()*(pivo - 0.5);
    }

    @Override
    public double yGetTranslateY(double pivo) {
        return (getTranslateY() + getHeight()/2) + yGetHeight()*(pivo - 0.5);
    }

    @Override
    public void ySetTranslateX(double position, double pivo) {
        VisibleObjectHandler.setTranslateX(this, (position - getWidth()/2) + yGetWidth()/2, pivo);
    }

    @Override
    public void ySetTranslateY(double position, double pivo) {//NAO FUNCIONA DIREITO OS POSICIONAMENTO PQ A FIGURA NAO É REGULAR, A BORDA DE CIMA PDC MAIOR Q A DE BAIXO, ENTAO UMA MEDIA NAO FUNFA
        VisibleObjectHandler.setTranslateY(this, (position - getHeight()/2) + yGetHeight()/2, pivo);
    }

    @Override
    public void ySetTranslateZ(double position, double pivo) {
        VisibleObjectHandler.setTranslateZ(this, position, pivo);
    }

    /**
     * @return The average stroke ocupation in this shape.
     */
    @Override
    public double yGetStrokeOcupation() {
        return YshapeHandler.yGetStrokeOcupation(this);
    }
    
    @Override
    public void ySetStroke(Double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean correct_location) {
        YshapeHandler.ySetStroke(this, stroke_width, stroke_color, stroke_type, correct_location);
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
    
}