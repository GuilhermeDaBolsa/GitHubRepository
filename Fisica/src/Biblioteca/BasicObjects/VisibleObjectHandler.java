package Biblioteca.BasicObjects;

import javafx.scene.Node;

public abstract class VisibleObjectHandler{
    
    public static double getAlturaNodo(Node nodo){
        return nodo.getBoundsInLocal().getHeight();
    }
    
    public static double getLarguraNodo(Node nodo){
        return nodo.getBoundsInLocal().getWidth();
    }
    
    /**
     * Método para mudar o tamanho total do nodo.
     * (OBS: Caso o parametro for Double.NaN, não será mudado o tamanho do nodo no respectivo eixo do parametro).
     * @param escalaX Fator de escala da largura.
     * @param escalaY Fator de escala da altura
     */
    public static void mudarTamanhoNodo(Node nodo, float escalaX, float escalaY){
        if(escalaX != Double.NaN)
            nodo.setScaleX(escalaX);
        if(escalaY != Double.NaN)
            nodo.setScaleY(escalaY);
    }
    
    /**
     * Método para mudar o tamanho total do nodo.
     * (OBS: Caso o parametro for Double.NaN, não será mudado o tamanho do nodo no respectivo eixo do parametro).
     * @param escalaX Fator de escala da largura.
     * @param escalaY Fator de escala da altura
     */
    public static void mudarTamanhoNodo(Node nodo, double pixelsX, double pixelsY){
        if(pixelsX != Double.NaN)
            nodo.setScaleX(pixelsX/getLarguraNodo(nodo));
        if(pixelsY != Double.NaN)
            nodo.setScaleY(pixelsY/getAlturaNodo(nodo));
    }

}
