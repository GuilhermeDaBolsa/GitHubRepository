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
     * Método para mover a forma com base  no pivo.
     * @param pivo Pivo é o ponto referente, ou seja, é ele que ficará no X informado,
     * ele é dado por valores entre 0 e 1, sendo 0 o ponto mais a esquerda e 1 o mais a direita da forma.
     * (nao precisa ser necessariamente entre 0 e 1 :D)
     * @param X Onde a forma deve ficar, no eixo X.
     */
    public static void setTranslateX(Shape shape, double X, double pivo){
        shape.setTranslateX(X - ((Forma) shape).yGetWidth(true) * pivo);
    }
    
    /**
     * Método para mover a forma com base  no pivo.
     * @param pivo Pivo é o ponto referente, ou seja, é ele que ficará no Y informado,
     * ele é dado por valores entre 0 e 1, sendo 0 o ponto mais a cima e 1 o mais a baixo da forma.
     * (nao precisa ser necessariamente entre 0 e 1 :D)
     * @param Y Onde a forma deve ficar, no eixo Y.
     */
    public static void setTranslateY(Shape shape, double Y, double pivo){
        shape.setTranslateY(Y - ((Forma) shape).yGetHeight(true) * pivo);
    }
    
    /*public static void setTranslateZ(Shape shape, double Z, double pivo){//VER QUAL Q É A DO EIXO Z ANTES DE SAIR FAZENDO AS COISAS NEééééé´DAR
        shape.setTranslateZ(Z - ((Forma) shape).yGetWidth(true)*pivo);
    }*/
    
    /**
     * @param forma The shape.
     * @return How much does the stroke occupie outside the shape, use it for regular shapes (rectangle, circle, etc)
     * because it calculates the average stroke width based just on the stroke width value and the stroke type.
     */
    public static double yGetStrokeOcupation(Shape forma) {
        double coeficient = 1;
        if(forma.getStrokeType() == StrokeType.OUTSIDE)
            coeficient = 2;
        else if(forma.getStrokeType() == StrokeType.INSIDE)
            coeficient = 0;
        
        return forma.getStrokeWidth()*coeficient;
    }
    
    /**
     * Sets a value to scale the object in the X axis (it's width).
     * @param forma The shape to be scaled.
     * @param scale How much the shape will be scaled.
     * @param correct_location If you want the 0 point of the left part of the object to be 
     * in the same place where it was before the scale, mark this true.
     */
    public static void ySetScaleX(Shape forma, double scale, boolean correct_location){
        double where_wasX = ((Forma) forma).yGetTranslateX(0);
        
        forma.setScaleX(scale);
        
        if(correct_location)
            ((Forma) forma).ySetTranslateX(where_wasX, 0);
    }
    
    /**
     * Sets a value to scale the object in the Y axis (it's height).
     * @param forma The shape to be scaled.
     * @param scale How much the shape will be scaled.
     * @param correct_location If you want the 0 point of the left part of the object to be 
     * in the same place where it was before the scale, mark this true.
     */
    public static void ySetScaleY(Shape forma, double scale, boolean correct_location){
        double where_wasY = ((Forma) forma).yGetTranslateY(0);
        
        forma.setScaleY(scale);
        
        if(correct_location)
            ((Forma) forma).ySetTranslateY(where_wasY, 0);
    }
    
    /**
     * Scales the shape in the X axis by a multiplier, in other words, the final
     * scale of the object in the X axis will be the previous scale * multiplier.
     * @param forma The shape to be scaled.
     * @param multiplier The multiplier of the scale.
     * @param correct_location If you want the 0 point of the left part of the object to be 
     * in the same place where it was before the scale, mark this true.
     * @see #ySetScaleX(javafx.scene.shape.Shape, double, boolean) 
     */
    public static void yScaleXby(Shape forma, double multiplier, boolean correct_location){
        ySetScaleX(forma, forma.getScaleX()*multiplier, correct_location);
    }
    
    /**
     * Scales the shape in the Y axis by a multiplier, in other words, the final
     * scale of the object in the Y axis will be the previous scale * multiplier.
     * @param forma The shape to be scaled.
     * @param multiplier The multiplier of the scale.
     * @param correct_location If you want the 0 point of the upper part of the object to be 
     * in the same place where it was before the scale, mark this true.
     * @see #ySetScaleY(javafx.scene.shape.Shape, double, boolean) 
     */
    public static void yScaleYby(Shape forma, double multiplier, boolean correct_location){
        ySetScaleY(forma, forma.getScaleY()*multiplier, correct_location);
    }
    
    /**
     * Scale the shape in the X axis (scale it's width).
     * @param forma The shape to be scaled.
     * @param width The width that the shape will have after the scale.
     * @param stroke_included If the shape has stroke width, it will count as beeing 
     * part of the shape, so after the scale the shape plus it's stroke will ocuppie the width.
     * @param correct_location Correct the location after the scale (the scale scales the shape from inside, so it will grow to the two sides)
     * @see #yScaleXby(javafx.scene.shape.Shape, double, boolean) 
     */
    public static void ySetWidthWithScale(Shape forma, double width, boolean stroke_included, boolean correct_location) {
        double shape_width = ((Forma) forma).yGetWidth();
        if (!stroke_included)
            shape_width -= yGetStrokeOcupation(forma)*forma.getScaleX();
        double scale = width / shape_width;
        
        yScaleXby(forma, scale, correct_location);
    }
    
    /**
     * Scale the shape in the Y axis (scale it's height).
     * @param forma The shape to be scaled.
     * @param height The height that the shape will have after the scale.
     * @param stroke_included If the shape has stroke width, it will count as beeing 
     * part of the shape, so after the scale the shape plus it's stroke will ocuppie the height.
     * @param correct_location Correct the location after the scale (the scale scales the shape from inside, so it will grow to the two sides)
     * @see #yScaleYby(javafx.scene.shape.Shape, double, boolean) 
     */
    public static void ySetHeigthWithScale(Shape forma, double height, boolean stroke_included, boolean correct_location){
        double shape_height = ((Forma) forma).yGetHeight();
        if(!stroke_included)
            shape_height -= yGetStrokeOcupation(forma)*forma.getScaleY();
        double scale = height/shape_height;
        
        yScaleYby(forma, scale, correct_location);
    }
    
    /**
     * 
     * @param forma
     * @param stroke_width
     * @param stroke_color
     * @param stroke_type 
     * @param stroke_ocupation 
     * @param correct_location If a new stroke_width is defined, depending on the type,
     * it will grow to the outside, keeping just the center point of the object where it was, unless this parameter is true.
     * @see #setStrokeType(javafx.scene.shape.StrokeType) 
     */
    public static void ySetStroke(Shape forma, Double stroke_width, Paint stroke_color, StrokeType stroke_type, YstrokeOcupation stroke_ocupation, boolean correct_location) {
        double where_wasX = ((Forma) forma).yGetTranslateX(0);
        double where_wasY = ((Forma) forma).yGetTranslateY(0);
        
        if(stroke_color != null)
            forma.setStroke(stroke_color);
        if(stroke_width != null)
            forma.setStrokeWidth(stroke_width);
        if(stroke_type != null)
            forma.setStrokeType(stroke_type);
        
        double real_stroke_width = yGetStrokeOcupation(forma);
        stroke_ocupation.setStrokeOcupation(real_stroke_width, real_stroke_width);
        
        if(correct_location){
            ((Forma) forma).ySetTranslateX(where_wasX, 0);
            ((Forma) forma).ySetTranslateY(where_wasY, 0);
        }
    }
    
    
    //BIND METODOS .... DAR UMA PADRONIZADA E UM JAVADOC Q FICA TININDO
    
    public static DoubleBinding yTranslateXbind(Shape shape, double pivo){
        return shape.translateXProperty().add(((Forma) shape).yWidthBind(true).multiply(pivo));
    }
    
    public static DoubleBinding yTranslateYbind(Shape shape, double pivo){
        return shape.translateYProperty().add(((Forma) shape).yHeightBind(true).multiply(pivo));
    }
    
    public static void yBindTranslateX(Shape shape, HashMap<String, ObservableValue<? extends Number>> map, String bind_name, ObservableValue<? extends Number> X, double pivo){
        X.addListener((observable) -> {
            ((Forma) shape).ySetTranslateX(X.getValue().doubleValue(), pivo);
        });
        map.put(bind_name, X);
    }
    
    public static void yBindTranslateY(Shape shape, HashMap<String, ObservableValue<? extends Number>> map, String bind_name, ObservableValue<? extends Number> Y, double pivo){
        Y.addListener((observable) -> {
            ((Forma) shape).ySetTranslateY(Y.getValue().doubleValue(), pivo);
        });
        map.put(bind_name, Y);
    }
    
    public static DoubleBinding yWidthBind(DoubleBinding width_property, YstrokeOcupation stroke_included){
        DoubleBinding radiusP = width_property.add(0);
        if(stroke_included != null)
            radiusP = radiusP.add(stroke_included.WIDTH);
        
        return radiusP;
    }
    
    public static DoubleBinding yHeightBind(DoubleBinding height_property, YstrokeOcupation stroke_included){
        DoubleBinding radiusP = height_property.add(0);
        if(stroke_included != null)
            radiusP = radiusP.add(stroke_included.HEIGHT);
        
        return radiusP;
    }
    
    public static void yBindWidth(Shape shape, HashMap<String, ObservableValue<? extends Number>> map, String bind_name, ObservableValue<? extends Number> width, boolean stroke_included){
        width.addListener((observable) -> {
            ((Forma) shape).ySetWidth(width.getValue().doubleValue(), stroke_included, true);
        });
        map.put(bind_name, width);
    }
    
    public static void yBindHeight(Shape shape, HashMap<String, ObservableValue<? extends Number>> map, String bind_name, ObservableValue<? extends Number> height, boolean stroke_included){
        height.addListener((observable) -> {
            ((Forma) shape).ySetHeight(height.getValue().doubleValue(), stroke_included, true);
        });
        map.put(bind_name, height);
    }
    
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