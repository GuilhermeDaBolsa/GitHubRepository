package SimplY.BasicObjects;

import javafx.scene.Node;

/**
 * Helps to handle with any node, espetialy with YvisibleScene that can't extends from 2 classes :T.
 */
public abstract class YVisibleObjectHandler{
    
    /**
     * @return The width that the entire shape (with all effects and attributes) occupies in the scene.
     */
    public static double yGetWidth(Node nodo){
        return nodo.getBoundsInLocal().getWidth()*nodo.getScaleX();//VER ESSE NEGOCIO DO SCALE AQUI EM... ACHO Q SE DEIXAR SEPARADO É MELHOR EM.......
    }
    
    /**
     * @return The height that the entire object (with all effects and attributes) occupies in the scene.
     */
    public static double yGetHeight(Node nodo){
        return nodo.getBoundsInLocal().getHeight()*nodo.getScaleY();
    }
    
    /**
     * Gets the X location of the object based on a pivo.
     * @param pivo The porcentage point of the object, for example, 0 is the upper point of the object,
     * 0.5 is the middle and 1 is the bottom point (it is not limited from 0 to 1, you can go further).
     * @return The X location of the pivo point.
     */
    public static double yGetTranslateX(Node nodo, double pivo){
        return nodo.getTranslateX() + yGetWidth(nodo)*pivo;
    }
    
    /**
     * Gets the Y location of the object based on a pivo.
     * @param pivo The porcentage point of the object, for example, 0 is the upper point of the object,
     * 0.5 is the middle and 1 is the bottom point (it is not limited from 0 to 1, you can go further).
     * @return The Y location of the pivo point.
     */
    public static double yGetTranslateY(Node nodo, double pivo){
        return nodo.getTranslateY() + yGetHeight(nodo)*pivo;
    }
    
    /**
     * Sets a new X location to the object based on a pivo.
     * @param pivo The porcentage point of the object that will be realocated to the
     * new X location, for example, 0 is the leftmost point of the object, 0.5 is the middle
     * and 1 is the rightmost point (it is not limited from 0 to 1, you can go further).
     * @param X The new position in the X axis.
     */
    public static void ySetTranslateX(Node nodo, double X, double pivo){
        nodo.setTranslateX(X - yGetWidth(nodo)*pivo);
    }
    
    /**
     * Sets a new Y location to the object based on a pivo.
     * @param pivo The porcentage point of the object that will be realocated to the
     * new Y location, for example, 0 is the leftmost point of the object, 0.5 is the middle
     * and 1 is the rightmost point (it is not limited from 0 to 1, you can go further).
     * @param Y The new position in the X axis.
     */
    public static void ySetTranslateY(Node nodo, double Y, double pivo){
        nodo.setTranslateY(Y - yGetHeight(nodo)*pivo);
    }
    
    /**
     * Hides and makes the node uninteractive.
     * @param nodo The node to be deactivated.
     */
    public static void yDeactivate(Node nodo){
        nodo.setVisible(false);
        nodo.setDisable(true);
    }
    
    /**
     * Makes the node visible and interactive.
     * @param nodo The node to be activated.
     */
    public static void yActivate(Node nodo){
        nodo.setVisible(true);
        nodo.setDisable(false);
    }
    
    /**
     * Switch between activated and deactivated mode.
     * @param nodo The node to be activated or deactivated.
     * @see #yActivate() 
     * @see #yDeactivate() 
     */
    public static void ySwitchOnOff(Node nodo){
        nodo.setVisible(!nodo.isVisible());
        nodo.setDisable(!nodo.isDisable());
    }
    
    //TALVEZ FAZER UMA INTERFASSE PRA PODER FAZER UMA CENAVISIVEL
    //FAZER UM SET ROTATE
    //VER QUE MAIS METODOS PODERIA VIR AQUI (TALVEZ UNS LA DA YSHAPEHANDLER)
}