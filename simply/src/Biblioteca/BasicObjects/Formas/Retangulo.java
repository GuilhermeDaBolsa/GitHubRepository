package Biblioteca.BasicObjects.Formas;

import Biblioteca.BasicObjects.VisibleObjectHandler;
import static Biblioteca.LogicClasses.Matematicas.random;
import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


public class Retangulo extends Rectangle implements Forma{
    
    public Retangulo(double largura, double altura, double grossuraBorda, Paint corFundo, Paint corBorda){
        super(largura, altura, corFundo);
        setStroke(grossuraBorda, corBorda);
    }
    
    /**
     * Cria uma instancia vazia de um retângulo.
     */
    public Retangulo(){
    }
    
    public Retangulo(double largura, double altura){
        this(largura, altura, 0, Color.color(random(1), random(1), random(1)), Color.BLACK);
    }
    
    public Retangulo(double largura, double altura, Paint cor){
        this(largura, altura, 0, cor, Color.BLACK);
    }
    
    public void setLargura(double largura){
        setWidth(largura);
    }
    
    public void setAltura(double altura){
        setHeight(altura);
    }
    
    public DoubleProperty getBindLargura(){
        return widthProperty();
    }
    
    public DoubleProperty getBindAltura(){
        return heightProperty();
    }
    
    public void bindLargura(DoubleProperty largura){
        widthProperty().bind(largura);
    }
    
    public void bindAltura(DoubleProperty altura){
        heightProperty().bind(altura);
    }

    @Override
    public void setTranslateX(double position, double pivo) {
        VisibleObjectHandler.setTranslateX(this, pivo, position);
    }

    @Override
    public void setTranslateY(double position, double pivo) {
        VisibleObjectHandler.setTranslateY(this, pivo, position);
    }

    @Override
    public void setTranslateZ(double position, double pivo) {
        VisibleObjectHandler.setTranslateZ(this, pivo, position);
    }

    @Override
    public void setStroke(Double grossura, Paint cor) {
        if(cor != null)
            setStroke(cor);
        if(grossura != null){
            setStrokeWidth(grossura);
            setTranslateX(getTranslateX() + grossura/2);///grossura borda?????????
            setTranslateY(getTranslateY() + grossura/2);///grossura borda?????????
        }
    }
}
