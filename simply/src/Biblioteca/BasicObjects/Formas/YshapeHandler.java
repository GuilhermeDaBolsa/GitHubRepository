package Biblioteca.BasicObjects.Formas;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

public abstract class YshapeHandler {
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
        double coeficient = 0.5;
        
        if(stroke_color != null)
            forma.setStroke(stroke_color);
        if(stroke_width != null)
            forma.setStrokeWidth(stroke_width);
        if(stroke_type != null){
            forma.setStrokeType(stroke_type);
            if(stroke_type == StrokeType.OUTSIDE)
                coeficient = 1;
            else if(stroke_type == StrokeType.INSIDE)
                coeficient = 0;
        }
        
        coeficient *= stroke_width;
        
        if(correct_location){
            forma.setTranslateX(forma.getTranslateX() + coeficient);
            forma.setTranslateY(forma.getTranslateY() + coeficient);
        }
    }
}
