package Biblioteca.BasicObjects.Formas;

import Biblioteca.InteractiveObjects.ObjetoInteragivel;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Circulo extends ObjetoInteragivel{
    public Circle circulo;
    
    /**
     * Cria uma instancia vazia de um circulo.
     */
    public Circulo(){
        objetoVisivel = new Circle();
        circulo = (Circle) objetoVisivel;

        this.getChildren().add(objetoVisivel);
    }
    
    public Circulo(double raio, Paint cor){
        objetoVisivel = new Circle(raio, cor);
        circulo = (Circle) objetoVisivel;
        
        circulo.setTranslateX(raio);
        circulo.setTranslateY(raio);
        
        this.getChildren().add(objetoVisivel);
    }
    
    public void setRaio(double raio){
        circulo.setRadius(raio);
        circulo.setTranslateX(raio);
        circulo.setTranslateY(raio);
    }
    
    
}
