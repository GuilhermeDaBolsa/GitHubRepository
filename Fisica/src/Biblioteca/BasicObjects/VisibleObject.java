package Biblioteca.BasicObjects;

import javafx.scene.layout.Pane;

public abstract class VisibleObject extends Pane{
    
    public double getAlturaNodo(){
        return VisibleObjectHandler.getAlturaNodo(this);
    }
    
    public double getLarguraNodo(){
        return VisibleObjectHandler.getLarguraNodo(this);
    }
    
    /**
     * Método para mudar o tamanho total do nodo.
     * (OBS: Caso o parametro for Double.NaN, não será mudado o tamanho do nodo no respectivo eixo do parametro).
     * @param escalaX Fator de escala da largura.
     * @param escalaY Fator de escala da altura
     */
    public void mudarTamanhoNodo(float escalaX, float escalaY){
        VisibleObjectHandler.mudarTamanhoNodo(this, escalaX, escalaY);
    }
    
    /**
     * Método para mudar o tamanho total do nodo.
     * (OBS: Caso o parametro for Double.NaN, não será mudado o tamanho do nodo no respectivo eixo do parametro).
     * @param largura Largura do novo tamanho em pixels.
     * @param altura Altura do novo tamanho em pixels.
     */
    public void mudarTamanhoNodo(double largura, double altura){
        VisibleObjectHandler.mudarTamanhoNodo(this, largura, altura);
    }
    
            //setEffect (VER SE JA NAO TEM PCAUSA DO PANE)
    //bind posicao/ tamanho 
}
