package Biblioteca.BasicObjects.Formas;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

public abstract class YshapeHandler {  
    
    public static double yGetStrokeOcupation(Shape forma) {
        double coeficient = 1;
        if(forma.getStrokeType() == StrokeType.OUTSIDE)
            coeficient = 2;
        else if(forma.getStrokeType() == StrokeType.INSIDE)
            coeficient = 0;
        
        return forma.getStrokeWidth()*coeficient;
    }
    
    public static void ySetScaleX(Shape forma, double scale, boolean correct_location){
        double where_wasX = ((Forma) forma).yGetTranslateX(0);
        
        forma.setScaleX(scale);
        
        if(correct_location)
            ((Forma) forma).ySetTranslateX(where_wasX, 0);
    }
    
    public static void ySetScaleY(Shape forma, double scale, boolean correct_location){
        double where_wasY = ((Forma) forma).yGetTranslateY(0);
        
        forma.setScaleY(scale);
        
        if(correct_location)
            ((Forma) forma).ySetTranslateY(where_wasY, 0);
    }
    
    /**
     * Scales the shape in the X axis by a multiplier, in other words, the final
     * scale of the object in the X axis will be the previous scale * multiplier.
     * @param forma The shape to be scaled.
     * @param multiplier The multiplier of the scale.
     * @param correct_location If you want the 0 point of the left part of the object to be 
     * in the same place where it was before the scale, mark this true.
     * @see #ySetScaleX(javafx.scene.shape.Shape, double, boolean) 
     */
    public static void yScaleXby(Shape forma, double multiplier, boolean correct_location){
        ySetScaleX(forma, forma.getScaleX()*multiplier, correct_location);
    }
    
    /**
     * Scales the shape in the Y axis by a multiplier, in other words, the final
     * scale of the object in the Y axis will be the previous scale * multiplier.
     * @param forma The shape to be scaled.
     * @param multiplier The multiplier of the scale.
     * @param correct_location If you want the 0 point of the upper part of the object to be 
     * in the same place where it was before the scale, mark this true.
     * @see #ySetScaleY(javafx.scene.shape.Shape, double, boolean) 
     */
    public static void yScaleYby(Shape forma, double multiplier, boolean correct_location){
        ySetScaleY(forma, forma.getScaleY()*multiplier, correct_location);
    }
    
    /**
     * Scale the shape in the X axis (scale it's width).
     * @param forma The shape to be scaled.
     * @param width The width that the shape will have after the scale.
     * @param stroke_included If the shape has stroke width, it will count as beeing 
     * part of the shape, so after the scale the shape plus it's stroke will ocuppie the width.
     * @param correct_location Correct the location after the scale (the scale scales the shape from inside, so it will grow to the two sides)
     * @see #yScaleXby(javafx.scene.shape.Shape, double, boolean) 
     */
    public static void ySetWidthWithScale(Shape forma, double width, boolean stroke_included, boolean correct_location) {
        double shape_width = ((Forma) forma).yGetWidth();
        if (!stroke_included)
            shape_width -= yGetStrokeOcupation(forma)*forma.getScaleX();
        double scale = width / shape_width;
        
        yScaleXby(forma, scale, correct_location);
    }
    
    /**
     * Scale the shape in the Y axis (scale it's height).
     * @param forma The shape to be scaled.
     * @param height The height that the shape will have after the scale.
     * @param stroke_included If the shape has stroke width, it will count as beeing 
     * part of the shape, so after the scale the shape plus it's stroke will ocuppie the height.
     * @param correct_location Correct the location after the scale (the scale scales the shape from inside, so it will grow to the two sides)
     * @see #yScaleYby(javafx.scene.shape.Shape, double, boolean) 
     */
    public static void ySetHeigthWithScale(Shape forma, double height, boolean stroke_included, boolean correct_location){
        double shape_height = ((Forma) forma).yGetHeight();
        if(!stroke_included)
            shape_height -= yGetStrokeOcupation(forma)*forma.getScaleY();
        double scale = height/shape_height;
        
        yScaleYby(forma, scale, correct_location);
    }
    
    /**
     * 
     * @param stroke_width
     * @param stroke_color
     * @param stroke_type 
     * @param correct_location If a new stroke_width is defined, depending on the type,
     * it will grow to the outside, keeping just the center point of the object where it was, unless this parameter is true.
     * @see #setStrokeType(javafx.scene.shape.StrokeType) 
     */
    public static void ySetStroke(Shape forma, Double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean correct_location) {
        double where_wasX = ((Forma) forma).yGetTranslateX(0);
        double where_wasY = ((Forma) forma).yGetTranslateY(0);
        
        if(stroke_color != null)
            forma.setStroke(stroke_color);
        if(stroke_width != null)
            forma.setStrokeWidth(stroke_width);
        if(stroke_type != null)
            forma.setStrokeType(stroke_type);
        
        if(correct_location){
            ((Forma) forma).ySetTranslateX(where_wasX, 0);
            ((Forma) forma).ySetTranslateY(where_wasY, 0);
        }
    }
}
