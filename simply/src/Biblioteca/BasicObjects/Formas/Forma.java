package Biblioteca.BasicObjects.Formas;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeType;

public interface Forma{
    
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
     * @return The width that the entire shape (with all effects and attributes) occupies in the scene.
     */
    public double yGetWidth();
    
    /**
     * @return The height that the entire shape (with all effects and attributes) occupies in the scene.
     */
    public double yGetHeight();
    
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
     * @param pivo The porcentage point of the shape, for example, 0 is the upper point of the shape,
     * 0.5 is the middle and 1 is the bottom point (it is not limited from 0 to 1, you can go further).
     * @return The X location of the pivo point.
     */
    public double yGetTranslateX(double pivo);
    
    /**
     * Gets the Y location of the shape based on a pivo.
     * @param pivo The porcentage point of the shape, for example, 0 is the upper point of the shape,
     * 0.5 is the middle and 1 is the bottom point (it is not limited from 0 to 1, you can go further).
     * @return The Y location of the pivo point.
     */
    public double yGetTranslateY(double pivo);
    
    /**
     * Sets a new X location to the shape based on a pivo.
     * @param pivo The porcentage point of the shape that will be realocated to the
     * new X location, for example, 0 is the leftmost point of the shape, 0.5 is the middle
     * and 1 is the rightmost point (it is not limited from 0 to 1, you can go further).
     * @param X The new position in the X axis.
     */
    public void ySetTranslateX(double X, double pivo);
    
    /**
     * Sets a new Y location to the shape based on a pivo.
     * @param pivo The porcentage point of the shape that will be realocated to the
     * new Y location, for example, 0 is the upper point of the shape, 0.5 is the middle
     * and 1 is the bottom point (it is not limited from 0 to 1, you can go further).
     * @param Y The new position in the Y axis.
     */
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
    
    
    
    //----------------------------- BIND/LISTENER METHODS -----------------------------\\REMOVE THIS OBRIGATIONAAAAAAAAAAAAAAAAAAAAAAA
    
    /**
     * The porpouse of this method is to store any bind, because depending on the circunstances it can be caught by garbadge collector.
     * @param bind_name Binding name
     * @param bind Bind itself
     */
    public void yAddBind(String bind_name, ObservableValue<? extends Number> bind);
    
    /**
     * @param pivo The porcentage point of the shape, for example, 0 is the leftmost point of the shape,
     * 0.5 is the middle and 1 is the rightmost point (it is not limited from 0 to 1, you can go further).
     * @return A DoubleBinding that is linked to the X location of the shape, in other words,
     * whenever the X location of the shape changes, the DoubleBinding will change automaticaly with it.
     * (this bind is linked to the X location but the opposite is not true).
     */
    public DoubleBinding yTranslateXbind(double pivo);
    
    /**
     * @param pivo The porcentage point of the shape, for example, 0 is the upper point of the shape,
     * 0.5 is the middle and 1 is the bottom point (it is not limited from 0 to 1, you can go further).
     * @return A DoubleBinding that is linked to the Y location of the shape, in other words,
     * whenever the Y location of the shape changes, the DoubleBinding will change automaticaly with it.
     * (this bind is linked to the Y location but the opposite is not true).
     */
    public DoubleBinding yTranslateYbind(double pivo);
    
    /**
     * Links the X position of the shape with an observable value, so whenever it changes, the shape's X position will change too.
     * @param bind_name The name for this link.
     * @param X The observable value to link the shape's X position.
     * @param pivo The porcentage point of the shape, for example, 0 is the leftmost point of the shape,
     * 0.5 is the middle and 1 is the rightmost point (it is not limited from 0 to 1, you can go further).
     * @see #ySetTranslateX(double, double) 
     */
    public void yBindTranslateX(String bind_name, ObservableValue<? extends Number> X, double pivo);
    
    /**
     * Links the Y position of the shape with an observable value, so whenever it changes, the shape's Y position will change too.
     * @param bind_name The name for this link.
     * @param Y The observable value to link the shape's Y position.
     * @param pivo The porcentage point of the shape, for example, 0 is the upper point of the shape,
     * 0.5 is the middle and 1 is the bottom point (it is not limited from 0 to 1, you can go further).
     * @see #ySetTranslateY(double, double) 
     */
    public void yBindTranslateY(String bind_name, ObservableValue<? extends Number> Y, double pivo);
    
    /**
     * @param stroke_included If you want the stroke of the shape to be part of the new width, mark this as true.
     * @return A DoubleBinding that is linked to the width of the shape, in other words,
     * whenever the width of the shape changes, the DoubleBinding will change automaticaly with it.
     * (this bind is linked to the width but the opposite is not true).
     */
    public DoubleBinding yWidthBind(boolean stroke_included);
    
    /**
     * @param stroke_included If you want the stroke of the shape to be part of the new height, mark this as true.
     * @return A DoubleBinding that is linked to the height of the shape, in other words,
     * whenever the height of the shape changes, the DoubleBinding will change automaticaly with it.
     * (this bind is linked to the height but the opposite is not true).
     */
    public DoubleBinding yHeightBind(boolean stroke_included);
            
    /**
     * Links the width of the shape with an observable value, so whenever it changes, the shape's width will change too.
     * @param bind_name The name for this link.
     * @param width The observable value to link the shape's width.
     * @param stroke_included If you want the stroke of the shape to be part of the new width, mark this as true.
     * @see #ySetWidth(double, boolean, boolean) 
     */
    public void yBindWidth(String bind_name, ObservableValue<? extends Number> width, boolean stroke_included);
    
    /**
     * Links the height of the shape with an observable value, so whenever it changes, the shape's height will change too.
     * @param bind_name The name for this link.
     * @param height The observable value to link the shape's height.
     * @param stroke_included If you want the stroke of the shape to be part of the new height, mark this as true.
     * @see #ySetHeight(double, boolean, boolean) 
     */
    public void yBindHeight(String bind_name, ObservableValue<? extends Number> height, boolean stroke_included);
    
    /**
     * Breaks any link created previously based on the name of the link
     * @param bind_name Name of the link to be broken.
     */
    public void yUnbind(String bind_name);
}
