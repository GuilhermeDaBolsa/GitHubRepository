package SimplY.BasicObjects;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableValue;

/**
 * Some short but interesting binds some object could have.
 */
public interface YCoolBindings {
    /**
     * The porpouse of this method is to store any bind, because depending on
     * the circunstances it can be caught by garbadge collector.
     * @param bind_name Binding name
     * @param bind Bind itself
     */
    public void yAddBind(String bind_name, ObservableValue<? extends Number> bind);

    /**
     * @param pivo The porcentage point of the object, for example, 0 is the
     * leftmost point of the object, 0.5 is the middle and 1 is the rightmost
     * point (it is not limited from 0 to 1, you can go further).
     * @return A DoubleBinding that is linked to the X location of the object, in
     * other words, whenever the X location of the object changes, the
     * DoubleBinding will change automaticaly with it. (this bind is linked to
     * the X location but the opposite is not true).
     */
    public DoubleBinding yTranslateXbind(double pivo);

    /**
     * @param pivo The porcentage point of the object, for example, 0 is the
     * upper point of the object, 0.5 is the middle and 1 is the bottom point (it
     * is not limited from 0 to 1, you can go further).
     * @return A DoubleBinding that is linked to the Y location of the object, in
     * other words, whenever the Y location of the object changes, the
     * DoubleBinding will change automaticaly with it. (this bind is linked to
     * the Y location but the opposite is not true).
     */
    public DoubleBinding yTranslateYbind(double pivo);

    /**
     * Links the X position of the object with an observable value, so whenever
     * it changes, the object's X position will change too.
     *
     * @param bind_name The name for this link.
     * @param X The observable value to link the object's X position.
     * @param pivo The porcentage point of the object, for example, 0 is the
     * leftmost point of the object, 0.5 is the middle and 1 is the rightmost
     * point (it is not limited from 0 to 1, you can go further).
     */
    public void yBindTranslateX(String bind_name, ObservableValue<? extends Number> X, double pivo);

    /**
     * Links the Y position of the object with an observable value, so whenever
     * it changes, the object's Y position will change too.
     *
     * @param bind_name The name for this link.
     * @param Y The observable value to link the object's Y position.
     * @param pivo The porcentage point of the object, for example, 0 is the
     * upper point of the object, 0.5 is the middle and 1 is the bottom point (it
     * is not limited from 0 to 1, you can go further).
     */
    public void yBindTranslateY(String bind_name, ObservableValue<? extends Number> Y, double pivo);

    /**
     * @param stroke_included If you want the stroke of the object to be part of
     * the new width, mark this as true.
     * @return A DoubleBinding that is linked to the width of the object, in
     * other words, whenever the width of the object changes, the DoubleBinding
     * will change automaticaly with it. (this bind is linked to the width but
     * the opposite is not true).
     */
    public DoubleBinding yWidthBind(boolean stroke_included);

    /**
     * @param stroke_included If you want the stroke of the object to be part of
     * the new height, mark this as true.
     * @return A DoubleBinding that is linked to the height of the object, in
     * other words, whenever the height of the object changes, the DoubleBinding
     * will change automaticaly with it. (this bind is linked to the height but
     * the opposite is not true).
     */
    public DoubleBinding yHeightBind(boolean stroke_included);

    /**
     * Links the width of the object with an observable value, so whenever it
     * changes, the object's width will change too.
     *
     * @param bind_name The name for this link.
     * @param width The observable value to link the object's width.
     * @param stroke_included If you want the stroke of the object to be part of
     * the new width, mark this as true.
     */
    public void yBindWidth(String bind_name, ObservableValue<? extends Number> width, boolean stroke_included);

    /**
     * Links the height of the object with an observable value, so whenever it
     * changes, the object's height will change too.
     *
     * @param bind_name The name for this link.
     * @param height The observable value to link the object's height.
     * @param stroke_included If you want the stroke of the object to be part of
     * the new height, mark this as true.
     */
    public void yBindHeight(String bind_name, ObservableValue<? extends Number> height, boolean stroke_included);

    /**
     * Breaks any bind created previously based on the name of the bind.
     * @param bind_name Name of the bind to be broken.
     */
    public void yUnbind(String bind_name);
}