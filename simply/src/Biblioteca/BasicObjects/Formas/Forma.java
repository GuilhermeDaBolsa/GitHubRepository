package Biblioteca.BasicObjects.Formas;

import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeType;

public interface Forma{

    public double yGetWidth();
    
    public double yGetHeight();
    
    public void ySetTranslateX(double position, double pivo);//ESSES PIVOS VAO DE 0 a 1 PRA FAZER A PORCENTAGEM DO OBJETO 0 é 0% e 1 é 100%
    
    public void ySetTranslateY(double position, double pivo);
    
    public void ySetTranslateZ(double position, double pivo);
    
    public double yGetTranslateX(double pivo);
    
    public double yGetTranslateY(double pivo);
    
    public double yGetStrokeOcupation();
    
    public void ySetStroke(Double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean move_with_new_stroke_width);
    
    public void ySetWidthWithScale(double width, boolean stroke_included, boolean correct_location);
    
    public void ySetHeigthWithScale(double height, boolean stroke_included, boolean correct_location);
}
