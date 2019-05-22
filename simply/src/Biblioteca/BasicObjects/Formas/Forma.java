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
    
    /**
     * Muda a grossura e a cor da borda do retangulo.
     * @param stroke_width Nova grossura da borda.
     * @param stroke_color Nova cor.
     */
    public void ySetStroke(Double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean move_with_new_stroke_width);
    
    /*public void setWidthWithScale(double width, boolean plusBorder){
        double scale = width/getLargura();
        if(plusBorder)
            scale = (width + (width/getLargura())*getGrossuraBorda()*2)/getLargura();
        forma.setScaleX(scale);
    }
    
    public void setHeigthWithScale(double heigth, boolean plusBorder){
        double scale = heigth/getAltura();
        if(plusBorder)
            scale = (heigth + (heigth/getAltura())*getGrossuraBorda()*2)/getAltura();
        forma.setScaleY(scale);
    }*/
}
