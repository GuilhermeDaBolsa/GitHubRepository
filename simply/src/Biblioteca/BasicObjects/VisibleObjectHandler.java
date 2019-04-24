package Biblioteca.BasicObjects;

import javafx.scene.Node;

public abstract class VisibleObjectHandler{
    
    public static double getWidth(Node nodo){
        return nodo.getBoundsInLocal().getWidth();
    }
    
    public static double getHeigth(Node nodo){
        return nodo.getBoundsInLocal().getHeight();
    }
    
    public static double getDepth(Node nodo){
        return nodo.getBoundsInLocal().getDepth();
    }
    
    /**
     * Método para mover a forma com base  no pivo.
     * @param pivo Pivo é o ponto referente, ou seja, é ele que ficará no X informado,
     * ele é dado por valores entre 0 e 1, sendo 0 o ponto mais a esquerda e 1 o mais a direita.
     * (nao precisa ser necessariamente entre 0 e 1 :D)
     * @param X Onde a forma deve ficar, no eixo X.
     */
    public static void setTranslateX(Node nodo, double pivo, double X){
        nodo.setTranslateX(X - getWidth(nodo)*pivo);
    }
    
    /**
     * Método para mover a forma com base  no pivo.
     * @param pivo Pivo é o ponto referente, ou seja, é ele que ficará no Y informado,
     * ele é dado por valores entre 0 e 1, sendo 0 o ponto mais a cima e 1 o mais a baixo.
     * (nao precisa ser necessariamente entre 0 e 1 :D)
     * @param Y Onde a forma deve ficar, no eixo Y.
     */
    public static void setTranslateY(Node nodo, double pivo, double Y){
        nodo.setTranslateY(Y - getHeigth(nodo)*pivo);
    }
    
    /**
     * Método para mover a forma com base  no pivo.
     * @param pivo Pivo é o ponto referente, ou seja, é ele que ficará no Y informado,
     * ele é dado por valores entre 0 e 1, sendo 0 o ponto mais a cima e 1 o mais a baixo.
     * (nao precisa ser necessariamente entre 0 e 1 :D)
     * @param Z Onde a forma deve ficar, no eixo Z.
     */
    public static void setTranslateZ(Node nodo, double pivo, double Z){
        nodo.setTranslateZ(Z - getDepth(nodo)*pivo);
    }
}
