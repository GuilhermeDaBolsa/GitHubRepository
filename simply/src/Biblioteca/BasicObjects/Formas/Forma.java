package Biblioteca.BasicObjects.Formas;

import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeType;

public interface Forma{
    
    
    //------------- SIZE METHODS -------------\\

    public double yGetWidth();
    
    public double yGetHeight();
    
    public void ySetWidth(double width, boolean stroke_included, boolean correct_location); //DAR UMA OLHADA NESSES METODOS AQUI NAS CLASSE PQ N SEI SE FIZ OU SE FIZ DIREITO PLMNS

    public void ySetHeight(double height, boolean stroke_included, boolean correct_location);
    
    
    //------------- TRANSLATE METHODS -------------\\
    
    public double yGetTranslateX(double pivo);
    
    public double yGetTranslateY(double pivo);
    
    public void ySetTranslateX(double position, double pivo);//ESSES PIVOS VAO DE 0 a 1 PRA FAZER A PORCENTAGEM DO OBJETO 0 é 0% e 1 é 100%
    
    public void ySetTranslateY(double position, double pivo);
    
    public void ySetTranslateZ(double position, double pivo);
    
    
    //------------- STROKE METHODS -------------\\
    
    public double yGetStrokeOcupation();
    
    public void ySetStroke(Double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean correct_location);
    
    
    //------------- SCALE METHODS -------------\\
    
    public void ySetScaleX(double scale, boolean correct_location);
    
    public void ySetScaleY(double scale, boolean correct_location);
    
    public void yScaleXby(double multiplier, boolean correct_location);
    
    public void yScaleYby(double multiplier, boolean correct_location);
    
    public void ySetWidthWithScale(double width, boolean stroke_included, boolean correct_location);
    
    public void ySetHeigthWithScale(double height, boolean stroke_included, boolean correct_location);
}
