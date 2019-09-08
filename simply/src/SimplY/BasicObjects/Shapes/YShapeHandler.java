package SimplY.BasicObjects.Shapes;

import SimplY.Lists.YSimpleMap;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Rotate;
import SimplY.BasicObjects.YCoolBindings;

public abstract class YShapeHandler{
    
    
    
    //----------------------------- TRANSLATE METHODS -----------------------------\\
    
    /**
     * @param shape The shape to be translated.
     * @see YShape#ySetTranslateX(double, double) 
     */
    public static void setTranslateX(Shape shape, double X, double pivo){
        shape.setTranslateX(X - ((YShape) shape).yGetWidth(true) * pivo);
    }
    
    /**
     * @param shape The shape to be translated.
     * @see YShape#ySetTranslateY(double, double) 
     */
    public static void setTranslateY(Shape shape, double Y, double pivo){
        shape.setTranslateY(Y - ((YShape) shape).yGetHeight(true) * pivo);
    }
    
    /**
     * Method created to controll an input that varies from 0 to max_value, adjusting it if stroke included.
     * @param size Input (generaly it is the size of a shape)
     * @param stroke_included If you want the stroke of the shape to be part of the size.
     * @param stroke_size The stroke size of the shape.
     * @param max_value The max value size can be.
     * @return The controled size.
     */
    public static double ySizeControler(double size, boolean stroke_included, double stroke_size, double max_value){
        if(size < 0)
            size = -size;
        
        if(max_value >= 0 && size > max_value)
            size = max_value;
        
        if(stroke_included)
            size -= stroke_size;
        
        return size;
    }
    
    
    
    //----------------------------- STROKE METHODS -----------------------------\\

    /**
     * @param shape The shape to get it's stroke width.
     * @return How much does the stroke occupie outside the shape, use it for regular shapes (rectangle, circle, etc)
     * because it calculates the average stroke width based just on the stroke width value and the stroke type.
     */
    public static double yGetStrokeOcupation(Shape shape) {
        double coeficient = 0.5;
        if(shape.getStrokeType() == StrokeType.OUTSIDE)
            coeficient = 1;
        else if(shape.getStrokeType() == StrokeType.INSIDE)
            coeficient = 0;
        
        return shape.getStrokeWidth()*coeficient;
    }
    
    /**
     * @param shape The shape to change the stroke.
     * @see YShape#ySetStroke(java.lang.Double, javafx.scene.paint.Paint, javafx.scene.shape.StrokeType, boolean) 
     */
    public static void ySetStroke(Shape shape, Double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean correct_location) {
        double where_wasX = ((YShape) shape).yGetTranslateX(0);
        double where_wasY = ((YShape) shape).yGetTranslateY(0);
        
        if(stroke_color != null)
            shape.setStroke(stroke_color);
        if(stroke_width != null)
            shape.setStrokeWidth(stroke_width);
        if(stroke_type != null)
            shape.setStrokeType(stroke_type);
        
        double real_stroke_width = yGetStrokeOcupation(shape);
        ((YShape) shape).yGetStrokeOcupation().setStrokeOcupation(real_stroke_width*2, real_stroke_width*2);
        
        if(correct_location){
            ((YShape) shape).ySetTranslateX(where_wasX, 0);
            ((YShape) shape).ySetTranslateY(where_wasY, 0);
        }
    }
    
    
    
    //----------------------------- ROTATE METHODS -----------------------------\\
    
    /**
     * Sets a rotation based on the actual angle (newAngle - oldAngle), and a point made of pivos for both axis (X and Y).
     * @param angle Add an angle rotation.
     * @param pivoX X pivo.
     * @param pivoY Y pivo.
     */
    public static void ySetRotate(Shape shape, Rotate rotation, double angle, double pivoX, double pivoY){
        shape.getTransforms().remove(rotation);
        
        double newAngle = angle - rotation.getAngle();
        rotation.setAngle(angle);
        double X = ((YShape) shape).yGetTranslateX(0.5) - pivoX;
        double Y = ((YShape) shape).yGetTranslateY(0.5) - pivoY;
        double newX = X * Math.cos(Math.toRadians(newAngle)) - Y * Math.sin(Math.toRadians(newAngle)) + pivoX;
        double newY = X * Math.sin(Math.toRadians(newAngle)) + Y * Math.cos(Math.toRadians(newAngle)) + pivoY;
        
        ((YShape) shape).ySetTranslateX(newX, 0.5);
        ((YShape) shape).ySetTranslateY(newY, 0.5);

        rotation.setPivotX(((YShape) shape).yGetWidth(false)/2);
        rotation.setPivotY(((YShape) shape).yGetHeight(false)/2);
        shape.getTransforms().add(rotation);
    }
    
    
    
    //----------------------------- SCALE METHODS -----------------------------\\
    
    /**
     * @param shape The shape to be scaled.
     * @see YShape#ySetScaleX(double, boolean) 
     */
    public static void ySetScaleX(Shape shape, double scale, boolean correct_location){
        double where_wasX = ((YShape) shape).yGetTranslateX(0);
        
        shape.setScaleX(scale);
        
        if(correct_location)
            ((YShape) shape).ySetTranslateX(where_wasX, 0);
    }
    
    /**
     * @param shape The shape to be scaled.
     * @see YShape#ySetScaleY(double, boolean) 
     */
    public static void ySetScaleY(Shape shape, double scale, boolean correct_location){
        double where_wasY = ((YShape) shape).yGetTranslateY(0);
        
        shape.setScaleY(scale);
        
        if(correct_location)
            ((YShape) shape).ySetTranslateY(where_wasY, 0);
    }
    
    /**
     * @param shape The shape to be scaled.
     * @see YShape#yScaleXby(double, boolean) 
     */
    public static void yScaleXby(Shape shape, double multiplier, boolean correct_location){
        ySetScaleX(shape, shape.getScaleX()*multiplier, correct_location);
    }
    
    /**
     * @param shape The shape to be scaled.
     * @see YShape#yScaleYby(double, boolean) 
     */
    public static void yScaleYby(Shape shape, double multiplier, boolean correct_location){
        ySetScaleY(shape, shape.getScaleY()*multiplier, correct_location);
    }
    
    /**
     * @param shape The shape to be scaled.
     * @see YShape#ySetWidthWithScale(double, boolean, boolean) 
     */
    public static void ySetWidthWithScale(Shape shape, double width, boolean stroke_included, boolean correct_location) {
        double shape_width = ((YShape) shape).yGetWidth();
        if (!stroke_included)
            shape_width -= yGetStrokeOcupation(shape)*shape.getScaleX();
        double scale = width / shape_width;
        
        yScaleXby(shape, scale, correct_location);
    }
    
    /**
     * @param shape The shape to be scaled.
     * @see YShape#ySetHeigthWithScale(double, boolean, boolean)
     */
    public static void ySetHeigthWithScale(Shape shape, double height, boolean stroke_included, boolean correct_location){
        double shape_height = ((YShape) shape).yGetHeight();
        if(!stroke_included)
            shape_height -= yGetStrokeOcupation(shape)*shape.getScaleY();
        double scale = height/shape_height;
        
        yScaleYby(shape, scale, correct_location);
    }
    

    
    //----------------------------- BIND/LISTENER METHODS -----------------------------\\
    
    /**
     * The porpouse of this method is to store any bind, because depending on
     * the circunstances it can be caught by garbadge collector.
     * @param bind_name Binding name
     * @param bind Bind itself
     */
    public static void yAddBind(YSimpleMap<String, ObservableValue> map, String bind_name, ObservableValue bind){
        map.add(bind_name, bind);
    }
    
    /**
     * @param shape The shape to get the bind.
     * @see YShape#yTranslateXbind(double) 
     */
    public static DoubleBinding yTranslateXbind(Shape shape, double pivo){
        return shape.translateXProperty().add(((YCoolBindings) shape).yWidthBind(true).multiply(pivo));
    }
    
    /**
     * @param shape The shape to get the bind.
     * @see YShape#yTranslateXbind(double) 
     */
    public static DoubleBinding yTranslateYbind(Shape shape, double pivo){
        return shape.translateYProperty().add(((YCoolBindings) shape).yHeightBind(true).multiply(pivo));
    }
    
    /**
     * @param shape The shape to be binded.
     * @param map A map of Strings (keys) and ObservableValues (objects) to maintain the bind reference.
     * (otherwise it would be lost and caught by the garbage collector)
     * @see YShape#yBindTranslateX(java.lang.String, javafx.beans.value.ObservableValue, double) 
     */
    public static void yBindTranslateX(Shape shape, YSimpleMap<String, ObservableValue> map, String bind_name, ObservableValue<? extends Number> X, double pivo){
        X.addListener((observable) -> {
            ((YShape) shape).ySetTranslateX(X.getValue().doubleValue(), pivo);
        });
        map.add(bind_name, X);
    }
    
    /**
     * @param shape The shape to be binded.
     * @param map A map of Strings (keys) and ObservableValues (objects) to maintain the bind reference.
     * (otherwise it would be lost and caught by the garbage collector)
     * @see YShape#yBindTranslateY(java.lang.String, javafx.beans.value.ObservableValue, double) 
     */
    public static void yBindTranslateY(Shape shape, YSimpleMap<String, ObservableValue> map, String bind_name, ObservableValue<? extends Number> Y, double pivo){
        Y.addListener((observable) -> {
            ((YShape) shape).ySetTranslateY(Y.getValue().doubleValue(), pivo);
        });
        map.add(bind_name, Y);
    }
    
    /**
     * @param width_property The width property of the shape.
     * @param stroke_included The stroke of the shape. If you dont want the stroke to be part of the width, mark as null.
     * @see YShape#yWidthBind(boolean) 
     */
    public static DoubleBinding yWidthBind(DoubleBinding width_property, YStrokeOcupation stroke_included){
        DoubleBinding width_bind = width_property.add(0);
        if(stroke_included != null)
            width_bind = width_bind.add(stroke_included.WIDTH);
        
        return width_bind;
    }
    
    /**
     * @param height_property The height property of the shape.
     * @param stroke_included The stroke of the shape. If you dont want the stroke to be part of the height, mark as null.
     * @see YShape#yHeightBind(boolean) 
     */
    public static DoubleBinding yHeightBind(DoubleBinding height_property, YStrokeOcupation stroke_included){
        DoubleBinding height_bind = height_property.add(0);
        if(stroke_included != null)
            height_bind = height_bind.add(stroke_included.HEIGHT);
        
        return height_bind;
    }
    
    /**
     * @param shape The shape to be binded.
     * @param map A map of Strings (keys) and ObservableValues (objects) to maintain the bind reference.
     * (otherwise it would be lost and caught by the garbage collector)
     * @see YShape#yBindWidth(java.lang.String, javafx.beans.value.ObservableValue, boolean) 
     */
    public static void yBindWidth(Shape shape, YSimpleMap<String, ObservableValue> map, String bind_name, ObservableValue<? extends Number> width, boolean stroke_included){
        width.addListener((observable) -> {
            ((YShape) shape).ySetWidth(width.getValue().doubleValue(), stroke_included, true);
        });
        map.add(bind_name, width);
    }
    
    /**
     * @param shape The shape to be binded.
     * @param map A map of Strings (keys) and ObservableValues (objects) to maintain the bind reference.
     * (otherwise it would be lost and caught by the garbage collector)
     * @see YShape#yBindWidth(java.lang.String, javafx.beans.value.ObservableValue, boolean) 
     */
    public static void yBindHeight(Shape shape, YSimpleMap<String, ObservableValue> map, String bind_name, ObservableValue<? extends Number> height, boolean stroke_included){
        height.addListener((observable) -> {
            ((YShape) shape).ySetHeight(height.getValue().doubleValue(), stroke_included, true);
        });
        map.add(bind_name, height);
    }
    
    /**
     * @param map A map of Strings (keys) and ObservableValues (objects) to maintain the binds references.
     * @see YShape#yUnbind(java.lang.String) 
     */
    public static void yUnbind(YSimpleMap<String, ObservableValue> map, String bind_name){
        map.remove(bind_name);
        System.gc();
    }
    
    
    
    
    
    //----------------------------- MISCELANEOUS METHODS -----------------------------\\

    
    /**
    * FALHOU NOS TESTES, PARECIA PROMISSOR :(
    */
    public static Object cloneObject(Object obj){
        try{
            Object clone = obj.getClass().newInstance();
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if(field.get(obj) == null || Modifier.isFinal(field.getModifiers())){
                    continue;
                }
                if(field.getType().isPrimitive() || field.getType().equals(String.class)
                        || field.getType().getSuperclass().equals(Number.class)
                        || field.getType().equals(Boolean.class)){
                    field.set(clone, field.get(obj));
                }else{
                    Object childObj = field.get(obj);
                    if(childObj == obj){
                        field.set(clone, clone);
                    }else{
                        field.set(clone, cloneObject(field.get(obj)));
                    }
                }
            }
            return clone;
        }catch(Exception e){
            return null;
        }
    }
}