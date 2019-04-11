package Biblioteca.BasicObjects.Formas;

import Biblioteca.InteractiveObjects.ObjetoInteragivel;
import static Biblioteca.LogicClasses.Matematicas.random;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


public class Retangulo extends ObjetoInteragivel{
    public Rectangle retangulo;
    
    public Retangulo(double largura, double altura, double grossuraBorda, Paint corFundo, Paint corBorda){
        objetoVisivel = new Rectangle(largura, altura, corFundo);
        retangulo.setStrokeWidth(grossuraBorda);
        retangulo.setStroke(corBorda);
        retangulo = ((Rectangle) objetoVisivel);
        
        this.getChildren().add(objetoVisivel);
    }
    
    /**
     * Cria uma instancia vazia de um retângulo.
     */
    public Retangulo(){
        objetoVisivel = new Rectangle();
        retangulo = ((Rectangle) objetoVisivel);
        
        this.getChildren().add(objetoVisivel);
    }
    
    public Retangulo(double largura, double altura){
        this(largura, altura, 0, Color.color(random(1), random(1), random(1)), Color.BLACK);
    }
    
    public Retangulo(double largura, double altura, Paint cor){
        this(largura, altura, 0, cor, Color.BLACK);
    }
}
