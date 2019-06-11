package Biblioteca.BasicObjects.Formas;

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
     * because it calculates the average stroke width.
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
        //VER COM ELE TAMBÉM SOBRE COMO CLONAR UM OBJETO, OU UM GEITO DE CONTORNAR O PROBLEMA
        //FALAR SOBRE OS NEGOCIO DO VEIDE TAMBÉM (AINDA TEM Q IMPLEMENTAR ISSO)
        //VER SOBRE OS BINDING PRA JUNTA NUM SÓ.... TALVEZ UMA CLASSE? BINDING??? (MATHGRID TA COM PROBLEMAS COM ISSO, MAS SE CONSEGUIR RESOLVER SERVE PRA TUDO)
        //TEM QUE POR OS BINDINGS NAS FORMAS E DAI ACHO QUE ACABOU, VER COM O COELHO SE ELE JA PRECISOU DE OUTRA COISA
        //TODAS AS FORMAS TEM METODOS EM COMUM.... SERIA SUPER BOM EXTEND DE 2 CLASSES.......
        //CLASSE TEXTO É CHEIA DE PROBLEMAS
        
        if(correct_location){
            ((Forma) forma).ySetTranslateX(where_wasX, 0);
            ((Forma) forma).ySetTranslateY(where_wasY, 0);
        }
    }
}