package Biblioteca.BasicObjects.Formas;

import javafx.scene.paint.Paint;

public interface Forma{

    public double getWidth();
    
    public double getHeight();
    
    public void setTranslateX(double position, double pivo);
    
    public void setTranslateY(double position, double pivo);
    
    public void setTranslateZ(double position, double pivo);
    
    /**
     * Muda a grossura e a cor da borda do retangulo.
     * @param grossura Nova grossura da borda.
     * @param cor Nova cor.
     */
    public void setStroke(Double grossura, Paint cor);
    
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
