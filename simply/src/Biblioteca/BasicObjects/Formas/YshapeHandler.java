package Biblioteca.BasicObjects.Formas;

import Biblioteca.BasicObjects.VisibleObjectHandler;
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
    
    /**
     * Scale the shape in the X axis (scale it's width).
     * @param forma The shape to be scaled.
     * @param width The width that the shape will have after the scale.
     * @param stroke_included If the shape has stroke width, it will count as beeing 
     * part of the shape, so after the scale the shape plus it's stroke will ocuppie the width.
     * @param correct_location Correct the location after the scale (the scale scales the shape from inside, so it will grow to the four sides)
     */
    public static void ySetWidthWithScale(Shape forma, double width, boolean stroke_included, boolean correct_location) {
        double where_wasX = ((Forma) forma).yGetTranslateX(0);
        double where_wasY = ((Forma) forma).yGetTranslateY(0);
        double shape_width = ((Forma) forma).yGetWidth();
        
        if (!stroke_included)
            shape_width -= yGetStrokeOcupation(forma)*forma.getScaleX();
        
        double scale = width / shape_width;
        
        forma.setScaleX(forma.getScaleX()*scale);
        
        if(correct_location){
            ((Forma) forma).ySetTranslateX(where_wasX, 0);
            ((Forma) forma).ySetTranslateY(where_wasY, 0);
        }
    }
    
    public static void ySetHeigthWithScale(Shape forma, double height, boolean stroke_included, boolean correct_location){
        double where_wasX = ((Forma) forma).yGetTranslateX(0);
        double where_wasY = ((Forma) forma).yGetTranslateY(0);
        double shape_height = ((Forma) forma).yGetHeight();
        
        if(!stroke_included)
            shape_height -= yGetStrokeOcupation(forma)*forma.getScaleY();

        double scale = height/shape_height;
        
        forma.setScaleY(forma.getScaleY()*scale);
        
        if(correct_location){
            ((Forma) forma).ySetTranslateX(where_wasX, 0);
            ((Forma) forma).ySetTranslateY(where_wasY, 0);
        }
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
