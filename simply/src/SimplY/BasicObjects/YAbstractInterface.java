package SimplY.BasicObjects;

import SimplY.Interactions.YEventsHandler;

/**
 * Says what methods every visual object should have.
 */
public interface YAbstractInterface {
    
    /**
     * @return The handler that deal with the events of this object.
     */
    public YEventsHandler yGetEventsHandler();
    
    /**
     * @return The width that the entire shape (with all effects and attributes) occupies in the scene.
     */
    public double yGetWidth();
    
    /**
     * @return The height that the entire object (with all effects and attributes) occupies in the scene.
     */
    public double yGetHeight();
    
    /**
     * Gets the X location of the object based on a pivo.
     * @param pivo The porcentage point of the object, for example, 0 is the upper point of the object,
     * 0.5 is the middle and 1 is the bottom point (it is not limited from 0 to 1, you can go further).
     * @return The X location of the pivo point.
     */
    public double yGetTranslateX(double pivo);
    
    /**
     * Gets the Y location of the object based on a pivo.
     * @param pivo The porcentage point of the object, for example, 0 is the upper point of the object,
     * 0.5 is the middle and 1 is the bottom point (it is not limited from 0 to 1, you can go further).
     * @return The Y location of the pivo point.
     */
    public double yGetTranslateY(double pivo);
    
    /**
     * Sets a new X location to the object based on a pivo.
     * @param pivo The porcentage point of the object that will be realocated to the
     * new X location, for example, 0 is the leftmost point of the object, 0.5 is the middle
     * and 1 is the rightmost point (it is not limited from 0 to 1, you can go further).
     * @param X The new position in the X axis.
     */
    public void ySetTranslateX(double X, double pivo);
    
    /**
     * Sets a new Y location to the object based on a pivo.
     * @param pivo The porcentage point of the object that will be realocated to the
     * new Y location, for example, 0 is the leftmost point of the object, 0.5 is the middle
     * and 1 is the rightmost point (it is not limited from 0 to 1, you can go further).
     * @param Y The new position in the X axis.
     */
    public void ySetTranslateY(double Y, double pivo);
    
    
    //maybe a setRotation too?
}