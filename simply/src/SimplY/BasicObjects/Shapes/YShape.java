package SimplY.BasicObjects.Shapes;

import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Rotate;
import SimplY.BasicObjects.YAbstractInterface;

/**
 * Standard methods to create a shape that is going to be used with this library.
 * NOTE: SHAPE here is almost always the shape and it's border (java does not take in count the border)
 */
public interface YShape extends YAbstractInterface{
    
    //----------------------------- SIZE METHODS -----------------------------\\
    
    /**
     * @param plusStroke If you want the width that the shape plus it's stroke occupies, mark this as true.
     * @return The width that JUST the shape in it's simple form occupies.
     */
    public double yGetWidth(boolean plusStroke);
    
    /**
     * @param plusStroke If you want the height that the shape plus it's stroke occupies, mark this as true.
     * @return The height that JUST the shape in it's simple form occupies.
     */
    public double yGetHeight(boolean plusStroke);
    
    /**
     * Sets a new width to the shape.
     * @param width The new width.
     * @param stroke_included If you want the stroke of the shape to be included in the width, mark true.
     * @param correct_location If you want the leftmost point of the new sized shape to be in the same place as it was before, mark true.
     */
    public void ySetWidth(double width, boolean stroke_included, boolean correct_location); //DAR UMA OLHADA NESSES METODOS AQUI NAS CLASSE PQ N SEI SE FIZ OU SE FIZ DIREITO PLMNS

    /**
     * Sets a new height to the shape.
     * @param height The new height.
     * @param stroke_included If you want the stroke of the shape to be included in the height, mark true.
     * @param correct_location If you want the upper point of the new sized shape to be in the same place as it was before, mark true.
     */
    public void ySetHeight(double height, boolean stroke_included, boolean correct_location);
    
    
    
    //----------------------------- TRANSLATE METHODS -----------------------------\\
    
    /**
     * Gets the X location of the shape based on a pivo.
     * @param pivo The porcentage point of the SHAPE, for example, 0 is the upper point of the shape,
     * 0.5 is the middle and 1 is the bottom point (it is not limited from 0 to 1, you can go further).
     * @return The X location of the pivo point.
     */
    @Override
    public double yGetTranslateX(double pivo);
    
    /**
     * Gets the Y location of the shape based on a pivo.
     * @param pivo The porcentage point of the SHAPE, for example, 0 is the upper point of the shape,
     * 0.5 is the middle and 1 is the bottom point (it is not limited from 0 to 1, you can go further).
     * @return The Y location of the pivo point.
     */
    @Override
    public double yGetTranslateY(double pivo);
    
    /**
     * Sets a new X location to the shape based on a pivo.
     * @param pivo The porcentage point of the SHAPE that will be realocated to the
     * new X location, for example, 0 is the leftmost point of the shape, 0.5 is the middle
     * and 1 is the rightmost point (it is not limited from 0 to 1, you can go further).
     * @param X The new position in the X axis.
     */
    @Override
    public void ySetTranslateX(double X, double pivo);
    
    /**
     * Sets a new Y location to the shape based on a pivo.
     * @param pivo The porcentage point of the SHAPE that will be realocated to the
     * new Y location, for example, 0 is the upper point of the shape, 0.5 is the middle
     * and 1 is the bottom point (it is not limited from 0 to 1, you can go further).
     * @param Y The new position in the Y axis.
     */
    @Override
    public void ySetTranslateY(double Y, double pivo);
    
    /**
     * Sets a new X and Y location to the shape based on a pivoX and a pivoY.
     * @param X The new position in the X axis.
     * @param Y The new position in the Y axis.
     * @param pivoX The porcentage point of the shape that will be realocated to the new X location.
     * @param pivoY The porcentage point of the shape that will be realocated to the new Y location.
     * @see #ySetTranslateX(double, double) 
     * @see #ySetTranslateY(double, double) 
     */
    public void ySetPosition(double X, double Y, double pivoX, double pivoY);
    
    
    
    //----------------------------- ROTATE METHODS -----------------------------\\
    
    /**
     * @return The last rotation made on the shape (except for the angle that is the actual angle).
     */
    public Rotate yGetRotate();
    
    /**
     * Sets a rotation based on the actual angle (newAngle - oldAngle), and a point made of pivos for both axis (X and Y).
     * @param angle Add an angle rotation.
     * @param pivoX X pivo.
     * @param pivoY Y pivo.
     */
    public void ySetRotate(double angle, double pivoX, double pivoY);
    
    
    //----------------------------- STROKE METHODS -----------------------------\\
    
    /**
     * Sets a new stroke to the shape.
     * @param stroke_width The width of the stroke.
     * @param stroke_color The color of the stroke.
     * @param stroke_type The type of stroke.
     * @param correct_location If you want the left upper point of the new shape to be the same as
     * the old one mark this as true, if false, the stroke will grow from inside to outside.
     * @see #setStrokeType(javafx.scene.shape.StrokeType) 
     */
    public void ySetStroke(Double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean correct_location);
    
    /**
     * @return The outside stroke ocupation handler.
     */
    public YStrokeOcupation yGetStrokeOcupation();
    
    
    
    //----------------------------- SCALE METHODS -----------------------------\\
    
    /**
     * Sets a value to scale the object in the X axis (it's width).
     * @param scale How much the shape will be scaled.
     * @param correct_location If you want the leftmost point of the shape to be 
     * in the same place where it was before the scale, mark this true, if false, the shape will
     * be scaled from inside.
     */
    public void ySetScaleX(double scale, boolean correct_location);
    
    /**
     * Sets a value to scale the object in the Y axis (it's height).
     * @param scale How much the shape will be scaled.
     * @param correct_location If you want the upper point of the shape to be in the
     * same place where it was before the scale, mark this true, if false, the shape will
     * be scaled from inside.
     */
    public void ySetScaleY(double scale, boolean correct_location);
    
    /**
     * Scales the shape in the X axis by a multiplier, in other words, the final
     * scale of the object in the X axis will be the previous scale times the multiplier.
     * @param multiplier The multiplier of the scale.
     * @see #ySetScaleX(javafx.scene.shape.Shape, double, boolean) 
     */
    public void yScaleXby(double multiplier, boolean correct_location);
    
    /**
     * Scales the shape in the Y axis by a multiplier, in other words, the final
     * scale of the object in the Y axis will be the previous scale times the multiplier.
     * @param multiplier The multiplier of the scale.
     * @see #ySetScaleY(javafx.scene.shape.Shape, double, boolean) 
     */
    public void yScaleYby(double multiplier, boolean correct_location);
    
    /**
     * Scale the shape in the X axis (scale it's width).
     * @param width The width that the shape will have after the scale.
     * @param stroke_included If the shape has stroke width and this parameter is true, the stroke will count as beeing 
     * part of the shape, so after the scale the shape plus it's stroke will ocuppie the width.
     * @see #yScaleXby(javafx.scene.shape.Shape, double, boolean) 
     */
    public void ySetWidthWithScale(double width, boolean stroke_included, boolean correct_location);
    
    /**
     * Scale the shape in the Y axis (scale it's height).
     * @param height The height that the shape will have after the scale.
     * @param stroke_included If the shape has stroke width and this parameter is true, the stroke will count as beeing 
     * part of the shape, so after the scale the shape plus it's stroke will ocuppie the height.
     * @see #yScaleYby(javafx.scene.shape.Shape, double, boolean) 
     */
    public void ySetHeigthWithScale(double height, boolean stroke_included, boolean correct_location);
}