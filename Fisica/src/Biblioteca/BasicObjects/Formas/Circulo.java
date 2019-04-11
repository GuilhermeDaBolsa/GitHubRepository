package Biblioteca.BasicObjects.Formas;

import Biblioteca.InteractiveObjects.ObjetoInteragivel;
import static Biblioteca.LogicClasses.Matematicas.random;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Circulo extends ObjetoInteragivel{
    public Circle circulo;
    
    public Circulo(double raio, Paint cor){
        if(raio == 0){
            objetoVisivel = new Circle();
        }else{
            objetoVisivel = new Circle(raio, cor);
        }
        
        circulo = (Circle) objetoVisivel;
        
        circulo.setTranslateX(raio);
        circulo.setTranslateY(raio);
        
        this.getChildren().add(objetoVisivel);
    }
    
    /**
     * Cria uma instancia vazia de um circulo.
     */
    public Circulo(){
        this(0, null);
    }

    public Circulo(double raio){
        this(raio, Color.color(random(1), random(1), random(1)));
    }
    
    public void setRaio(double raio){
        circulo.setRadius(raio);
        circulo.setTranslateX(raio);
        circulo.setTranslateY(raio);
    }
    
    
}
