package Biblioteca.BasicObjects.Formas;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

public abstract class YshapeHandler{
    
    /**
     * @param shape The shape to be translated.
     * @see Forma#ySetTranslateX(double, double) 
     */
    public static void setTranslateX(Shape shape, double X, double pivo){
        shape.setTranslateX(X - ((Forma) shape).yGetWidth(true) * pivo);
    }
    
    /**
     * @param shape The shape to be translated.
     * @see Forma#ySetTranslateY(double, double) 
     */
    public static void setTranslateY(Shape shape, double Y, double pivo){
        shape.setTranslateY(Y - ((Forma) shape).yGetHeight(true) * pivo);
    }
    
    /**
     * @param shape The shape to get it's stroke width.
     * @return How much does the stroke occupie outside the shape, use it for regular shapes (rectangle, circle, etc)
     * because it calculates the average stroke width based just on the stroke width value and the stroke type.
     */
    public static double yGetStrokeOcupation(Shape shape) {
        double coeficient = 1;
        if(shape.getStrokeType() == StrokeType.OUTSIDE)
            coeficient = 2;
        else if(shape.getStrokeType() == StrokeType.INSIDE)
            coeficient = 0;
        
        return shape.getStrokeWidth()*coeficient;
    }
    
    /**
     * @param shape The shape to change the stroke.
     * @see Forma#ySetStroke(java.lang.Double, javafx.scene.paint.Paint, javafx.scene.shape.StrokeType, boolean) 
     */
    public static void ySetStroke(Shape shape, Double stroke_width, Paint stroke_color, StrokeType stroke_type, YstrokeOcupation stroke_ocupation, boolean correct_location) {
        double where_wasX = ((Forma) shape).yGetTranslateX(0);
        double where_wasY = ((Forma) shape).yGetTranslateY(0);
        
        if(stroke_color != null)
            shape.setStroke(stroke_color);
        if(stroke_width != null)
            shape.setStrokeWidth(stroke_width);
        if(stroke_type != null)
            shape.setStrokeType(stroke_type);
        
        double real_stroke_width = yGetStrokeOcupation(shape);
        stroke_ocupation.setStrokeOcupation(real_stroke_width, real_stroke_width);
        
        if(correct_location){
            ((Forma) shape).ySetTranslateX(where_wasX, 0);
            ((Forma) shape).ySetTranslateY(where_wasY, 0);
        }
    }
    
    /**
     * @param shape The shape to be scaled.
     * @see Forma#ySetScaleX(double, boolean) 
     */
    public static void ySetScaleX(Shape shape, double scale, boolean correct_location){
        double where_wasX = ((Forma) shape).yGetTranslateX(0);
        
        shape.setScaleX(scale);
        
        if(correct_location)
            ((Forma) shape).ySetTranslateX(where_wasX, 0);
    }
    
    /**
     * @param shape The shape to be scaled.
     * @see Forma#ySetScaleY(double, boolean) 
     */
    public static void ySetScaleY(Shape shape, double scale, boolean correct_location){
        double where_wasY = ((Forma) shape).yGetTranslateY(0);
        
        shape.setScaleY(scale);
        
        if(correct_location)
            ((Forma) shape).ySetTranslateY(where_wasY, 0);
    }
    
    /**
     * @param shape The shape to be scaled.
     * @see Forma#yScaleXby(double, boolean) 
     */
    public static void yScaleXby(Shape shape, double multiplier, boolean correct_location){
        ySetScaleX(shape, shape.getScaleX()*multiplier, correct_location);
    }
    
    /**
     * @param shape The shape to be scaled.
     * @see Forma#yScaleYby(double, boolean) 
     */
    public static void yScaleYby(Shape shape, double multiplier, boolean correct_location){
        ySetScaleY(shape, shape.getScaleY()*multiplier, correct_location);
    }
    
    /**
     * @param shape The shape to be scaled.
     * @see Forma#ySetWidthWithScale(double, boolean, boolean) 
     */
    public static void ySetWidthWithScale(Shape shape, double width, boolean stroke_included, boolean correct_location) {
        double shape_width = ((Forma) shape).yGetWidth();
        if (!stroke_included)
            shape_width -= yGetStrokeOcupation(shape)*shape.getScaleX();
        double scale = width / shape_width;
        
        yScaleXby(shape, scale, correct_location);
    }
    
    /**
     * @param shape The shape to be scaled.
     * @see Forma#ySetHeigthWithScale(double, boolean, boolean)
     */
    public static void ySetHeigthWithScale(Shape shape, double height, boolean stroke_included, boolean correct_location){
        double shape_height = ((Forma) shape).yGetHeight();
        if(!stroke_included)
            shape_height -= yGetStrokeOcupation(shape)*shape.getScaleY();
        double scale = height/shape_height;
        
        yScaleYby(shape, scale, correct_location);
    }
    
    /**
     * @param shape The shape to get the bind.
     * @see Forma#yTranslateXbind(double) 
     */
    public static DoubleBinding yTranslateXbind(Shape shape, double pivo){
        return shape.translateXProperty().add(((Forma) shape).yWidthBind(true).multiply(pivo));
    }
    
    /**
     * @param shape The shape to get the bind.
     * @see Forma#yTranslateXbind(double) 
     */
    public static DoubleBinding yTranslateYbind(Shape shape, double pivo){
        return shape.translateYProperty().add(((Forma) shape).yHeightBind(true).multiply(pivo));
    }
    
    /**
     * @param shape The shape to be binded.
     * @param map A map of Strings (keys) and ObservableValues (objects) to maintain the bind reference.
     * (otherwise it would be lost and would be caught by the garbage collector)
     * @see Forma#yBindTranslateX(java.lang.String, javafx.beans.value.ObservableValue, double) 
     */
    public static void yBindTranslateX(Shape shape, HashMap<String, ObservableValue<? extends Number>> map, String bind_name, ObservableValue<? extends Number> X, double pivo){
        X.addListener((observable) -> {
            ((Forma) shape).ySetTranslateX(X.getValue().doubleValue(), pivo);
        });
        map.put(bind_name, X);
    }
    
    /**
     * @param shape The shape to be binded.
     * @param map A map of Strings (keys) and ObservableValues (objects) to maintain the bind reference.
     * (otherwise it would be lost and would be caught by the garbage collector)
     * @see Forma#yBindTranslateY(java.lang.String, javafx.beans.value.ObservableValue, double) 
     */
    public static void yBindTranslateY(Shape shape, HashMap<String, ObservableValue<? extends Number>> map, String bind_name, ObservableValue<? extends Number> Y, double pivo){
        Y.addListener((observable) -> {
            ((Forma) shape).ySetTranslateY(Y.getValue().doubleValue(), pivo);
        });
        map.put(bind_name, Y);
    }
    
    /**
     * @param width_property The width property of the shape.
     * @param stroke_included The stroke of the shape. If you dont want the stroke to be part of the width, mark as null.
     * @see Forma#yWidthBind(boolean) 
     */
    public static DoubleBinding yWidthBind(DoubleBinding width_property, YstrokeOcupation stroke_included){
        DoubleBinding width_bind = width_property.add(0);
        if(stroke_included != null)
            width_bind = width_bind.add(stroke_included.WIDTH);
        
        return width_bind;
    }
    
    /**
     * @param height_property The height property of the shape.
     * @param stroke_included The stroke of the shape. If you dont want the stroke to be part of the height, mark as null.
     * @see Forma#yHeightBind(boolean) 
     */
    public static DoubleBinding yHeightBind(DoubleBinding height_property, YstrokeOcupation stroke_included){
        DoubleBinding height_bind = height_property.add(0);
        if(stroke_included != null)
            height_bind = height_bind.add(stroke_included.HEIGHT);
        
        return height_bind;
    }
    
    /**
     * @param shape The shape to be binded.
     * @param map A map of Strings (keys) and ObservableValues (objects) to maintain the bind reference.
     * (otherwise it would be lost and would be caught by the garbage collector)
     * @see Forma#yBindWidth(java.lang.String, javafx.beans.value.ObservableValue, boolean) 
     */
    public static void yBindWidth(Shape shape, HashMap<String, ObservableValue<? extends Number>> map, String bind_name, ObservableValue<? extends Number> width, boolean stroke_included){
        width.addListener((observable) -> {
            ((Forma) shape).ySetWidth(width.getValue().doubleValue(), stroke_included, true);
        });
        map.put(bind_name, width);
    }
    
    /**
     * @param shape The shape to be binded.
     * @param map A map of Strings (keys) and ObservableValues (objects) to maintain the bind reference.
     * (otherwise it would be lost and would be caught by the garbage collector)
     * @see Forma#yBindWidth(java.lang.String, javafx.beans.value.ObservableValue, boolean) 
     */
    public static void yBindHeight(Shape shape, HashMap<String, ObservableValue<? extends Number>> map, String bind_name, ObservableValue<? extends Number> height, boolean stroke_included){
        height.addListener((observable) -> {
            ((Forma) shape).ySetHeight(height.getValue().doubleValue(), stroke_included, true);
        });
        map.put(bind_name, height);
    }
    
    /**
     * @param map A map of Strings (keys) and ObservableValues (objects) to maintain the binds references.
     * @see Forma#yUnbind(java.lang.String) 
     */
    public static void yUnbind(HashMap<String, ObservableValue<? extends Number>> map, String bind_name){
        map.remove(bind_name);
        System.gc();
    }
    
    
    
    
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