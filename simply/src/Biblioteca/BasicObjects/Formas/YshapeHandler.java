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
