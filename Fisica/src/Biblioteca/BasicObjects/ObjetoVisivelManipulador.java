package Biblioteca.BasicObjects;

import javafx.scene.Node;

public abstract class ObjetoVisivelManipulador{
    
    public static double getAltura(Node nodo){
        return nodo.getBoundsInLocal().getHeight();
    }
    
    public static double getLargura(Node nodo){
        return nodo.getBoundsInLocal().getWidth();
    }
    
    /**
     * Método para mover a forma com base  no pivo.
     * @param pivo Pivo é o ponto referente, ou seja, é ele que ficará no X informado,
     * ele é dado por valores entre 0 e 1, sendo 0 o ponto mais a esquerda e 1 o mais a direita.
     * (nao precisa ser necessariamente entre 0 e 1 :D)
     * @param X Onde a forma deve ficar, no eixo X.
     */
    public static void setPosicaoX(Node nodo, double pivo, double X){
        nodo.setTranslateX(X - getLargura(nodo)*pivo);
    }
    
    /**
     * Método para mover a forma com base  no pivo.
     * @param pivo Pivo é o ponto referente, ou seja, é ele que ficará no Y informado,
     * ele é dado por valores entre 0 e 1, sendo 0 o ponto mais a cima e 1 o mais a baixo.
     * (nao precisa ser necessariamente entre 0 e 1 :D)
     * @param Y Onde a forma deve ficar, no eixo Y.
     */
    public static void setPosicaoY(Node nodo, double pivo, double Y){
        nodo.setTranslateY(Y - getAltura(nodo)*pivo);
    }
}
