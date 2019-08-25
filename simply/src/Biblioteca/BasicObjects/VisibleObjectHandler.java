package Biblioteca.BasicObjects;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.Node;

public abstract class VisibleObjectHandler{
    
    public static double getWidth(Node nodo){
        return nodo.getBoundsInLocal().getWidth()*nodo.getScaleX();//VER ESSE NEGOCIO DO SCALE AQUI EM... ACHO Q SE DEIXAR SEPARADO É MELHOR EM.......
    }
    
    public static double getHeight(Node nodo){
        return nodo.getBoundsInLocal().getHeight()*nodo.getScaleY();
    }
    
    public static double yGetTranslateX(Node nodo, double pivo){
        return nodo.getTranslateX() + getWidth(nodo)*pivo;
    }
    
    public static double yGetTranslateY(Node nodo, double pivo){
        return nodo.getTranslateY() + getHeight(nodo)*pivo;
    }
    
    /**
     * Método para mover a forma com base  no pivo.
     * @param pivo Pivo é o ponto referente, ou seja, é ele que ficará no X informado,
     * ele é dado por valores entre 0 e 1, sendo 0 o ponto mais a esquerda e 1 o mais a direita.
     * (nao precisa ser necessariamente entre 0 e 1 :D)
     * @param X Onde a forma deve ficar, no eixo X.
     */
    public static void setTranslateX(Node nodo, double X, double pivo){
        nodo.setTranslateX(X - getWidth(nodo)*pivo);
    }
    
    /**
     * Método para mover a forma com base  no pivo.
     * @param pivo Pivo é o ponto referente, ou seja, é ele que ficará no Y informado,
     * ele é dado por valores entre 0 e 1, sendo 0 o ponto mais a cima e 1 o mais a baixo.
     * (nao precisa ser necessariamente entre 0 e 1 :D)
     * @param Y Onde a forma deve ficar, no eixo Y.
     */
    public static void setTranslateY(Node nodo, double Y, double pivo){
        nodo.setTranslateY(Y - getHeight(nodo)*pivo);
    }
    
    /**
     * Faz com que o objeto desapareça e não interaja com nada. Porém ele 
     * continua com suas propriedades, apenas se tornará invisivel e inalvejavel.
     */
    public static void desativar(Node nodo){
        nodo.setVisible(false);
        nodo.setDisable(true);
    }
    
    /**
     * Faz com que o objeto apareça e interaja com a cena. Tornando-o visivel e alvejavel.
     */
    public static void ativar(Node nodo){
        nodo.setVisible(true);
        nodo.setDisable(false);
    }
    
    /**
     * Caso o objeto esteja desativado, será ativado e vice-versa.
     */
    public static void switchAtivarDesativar(Node nodo){
        nodo.setVisible(!nodo.isVisible());
        nodo.setDisable(!nodo.isDisable());
    }
    
    public static void bindPosicaoX(Node nodo, DoubleBinding posicao, double eixo){
        nodo.translateXProperty().bind(posicao.subtract(eixo*getWidth(nodo)));
    }
    
    public static void bindPosicaoY(Node nodo, DoubleBinding posicao, double eixo){
        nodo.translateYProperty().bind(posicao.subtract(eixo*getHeight(nodo)));
    }
    
    //TALVEZ FAZER UMA INTERFASSE PRA PODER FAZER UMA CENAVISIVEL
    //FAZER UM SET ROTATE
    //VER QUE MAIS METODOS PODERIA VIR AQUI (TALVEZ UNS LA DA YSHAPEHANDLER)
}
