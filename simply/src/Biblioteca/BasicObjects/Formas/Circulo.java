package Biblioteca.BasicObjects.Formas;

import Biblioteca.BasicObjects.VisibleObjectHandler;
import static Biblioteca.LogicClasses.Matematicas.random;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Circulo extends Circle implements Forma{
    
    public Circulo(double radius, Paint color, double stroke_width, Paint stroke_color){
        setNewRadius(radius);
        setFill(color);
        setStroke(stroke_width, stroke_color);
    }
    
    public Circulo(double radius, Paint color){
        setNewRadius(radius);
        setFill(color);
    }
    
    /**
     * Cria uma instancia vazia de um circulo.
     */
    public Circulo(){
    }

    public Circulo(double raio){
        this(raio, Color.color(random(1), random(1), random(1)), 0, Color.BLACK);
    }
    
    public void setNewRadius(double raio){
        setRadius(raio);
        setTranslateX(getTranslateX() + getRadius());
        setTranslateY(getTranslateY() + getRadius());
    }

    public ReadOnlyDoubleProperty getBindRaio(){
        return radiusProperty();
    }
    
    public void bindRaio(DoubleProperty largura){
        radiusProperty().bind(largura);
    }

    @Override
    public double getWidth() {//DA PRA CALCULAR COM BASE NO RADIUS E NO STROKE
        return VisibleObjectHandler.getWidth(this);
    }

    @Override
    public double getHeight() {//DA PRA CALCULAR COM BASE NO RADIUS E NO STROKE
        return VisibleObjectHandler.getHeigth(this);
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

    @Override
    public void setTranslateX(double position, double pivo) {//-0.5 pois o ponto de pivo do circulo é no meio dele
        VisibleObjectHandler.setTranslateX(this, pivo - 0.5, position);
    }

    @Override
    public void setTranslateY(double position, double pivo) {//-0.5 pois o ponto de pivo do circulo é no meio dele
        VisibleObjectHandler.setTranslateY(this, pivo - 0.5, position);
    }

    @Override
    public void setTranslateZ(double position, double pivo) {
        VisibleObjectHandler.setTranslateZ(this, pivo, position);
    }
}
