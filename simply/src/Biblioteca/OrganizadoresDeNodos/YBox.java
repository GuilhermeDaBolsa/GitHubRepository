package Biblioteca.OrganizadoresDeNodos;

import Biblioteca.BasicObjects.YvisibleScene;
import Biblioteca.BasicObjects.Formas.*;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

/**
 * Class to create a box with content (it is a shape, with a pane)
 */
public class YBox extends YvisibleScene {
    public Shape box;
    public Pane content;
    
    /**
     * Creates a box with a custom shape.
     * @param shape The shape.
     * @param stroke_width Stroke width.
     * @param background_color The background color of the shape.
     * @param stroke_color The stroke color.
     */
    public YBox(Shape shape, Paint background_color, double stroke_width, Paint stroke_color){
        box = shape;//IT IS NOT A COPY 
        
        content = new Pane();
        box.setFill(background_color);
        ySetStroke(stroke_width, stroke_color, StrokeType.OUTSIDE, true);
        
        yRealocateContent(0.0, 0.0);
        
        getChildren().addAll(box, content);
    }
    
    /**
     * Creates a rectangular box.
     * @param width Box's Width.
     * @param height Box's height.
     */
    public YBox(double width, double height, Paint background_color, double stroke_width, Paint stroke_color) {
        this(new Yrectangle(width, height), background_color, stroke_width, stroke_color);
    }
    
    /**
     * Creates a rectangular (10 x 10) box to be a model.
     */
    public YBox(Paint background_color, double stroke_width, Paint stroke_color) {
        this(new Yrectangle(10, 10), background_color, stroke_width, stroke_color);
    }
    
    /**
     * Creates a circular box.
     * @param radius Box's radius.
     */
    public YBox(double radius, Paint background_color, double stroke_width, Paint stroke_color) {
        this(new Ycircle(radius), background_color, stroke_width, stroke_color);
    }

    public void ySetStroke(Double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean correct_location) {
        ((Yshape) box).ySetStroke(stroke_width, stroke_color, stroke_type, correct_location);
    }

    /**
     * Adds some content to the box.
     * @param contents The content to be added.
    */
    public void yAddContent(Node... contents) {
        for (int i = 0; i < contents.length; i++) {
            content.getChildren().add(contents[i]);
        }
    }
    
    /**
     * Adds some content (stored in a ArrayList) to the box.
     * @param contents The ArrayList with the content.
    */
    public void yAddContent(ArrayList<Node> contents) {
        for (int i = 0; i < contents.size(); i++) {
            YBox.this.yAddContent(contents.get(i));  
        }
    }
    
    /**
     * Remove an object from the box.
     * @param object Object to be removed.
     */
    public void yRemoveContent(Node object){
        content.getChildren().remove(object);
    }
    
    /**
     * Clear the box.
     */
    public void yClear(){
        content.getChildren().clear();
        yRealocateContent(0.0, 0.0);
    }
    
    /**
     * Sets a new content in place of other.
     * NOTE: if old_content == null, the box will be cleaned.
     * @param old_content Old content.
     * @param new_content New content;
     */
    public void ySetContent(Node old_content, Node... new_content) {
        if(old_content != null)
            yRemoveContent(old_content);
        else
            yClear();
        
        yAddContent(new_content);
    }
    
    /**
     * @return The objects stored in this box.
     */
    public Node[] yGetContent(){
        return content.getChildren().toArray(new Node[content.getChildren().size()]);
    }
    
    /**
     * @return The content width.
     */
    public double yGetContentWidth(){
        return content.getBoundsInLocal().getWidth();
    }
    
    /**
     * @return The content height.
     */
    public double yGetContentHeight(){
        return content.getBoundsInLocal().getHeight();
    }
    
    /**
     * @return The box width.
     */
    public double yGetBoxWidth(){
        return ((Yshape) box).yGetWidth(true);
    }
    
    /**
     * @return The box height.
     */
    public double yGetBoxHeight(){
        return ((Yshape) box).yGetHeight(true);
    }
    
    /**
     * Move all objects inside the box to the desired position (if null it will not be moved).
     * @param X X position.
     * @param Y Y position.
     */
    public void yRealocateContent(Double X, Double Y){
        if(X != null)
            content.setTranslateX(X + ((Yshape) box).yGetStrokeOcupation().LEFT);
        if(Y != null)
            content.setTranslateY(Y + ((Yshape) box).yGetStrokeOcupation().UP);
    }
    
    /**
     * Move all objects inside the box based on the current position (oldX + X, oldY + Y).
     * @param X X distance.
     * @param Y Y distance.
     */
     public void yMoveContents(double X, double Y){
         yRealocateContent(content.getTranslateX() + X - ((Yshape) box).yGetStrokeOcupation().LEFT,
                 content.getTranslateY() + Y - ((Yshape) box).yGetStrokeOcupation().UP);
    }
     
    /**
     * Align the objects (as beeing one) inside the box.
     * @param elementXpivo
     * @param elementYpivo
     * @param boxXpivo
     * @param boxYpivo 
     */
    public void yAlignContents(double elementXpivo, double elementYpivo, double boxXpivo, double boxYpivo){
        yRealocateContent(((Yshape) box).yGetTranslateX(0) + ((Yshape) box).yGetWidth(false) * boxXpivo - yGetContentWidth() * elementXpivo,
                ((Yshape) box).yGetTranslateY(0) + ((Yshape) box).yGetHeight(false) * boxYpivo - yGetContentHeight() * elementYpivo);
    }
    
    public void ySetBoxSize(double width, double height, boolean strokeX_included, boolean strokeY_included, boolean correct_location){
        double content_delta_positionX = content.getTranslateX() - this.getTranslateX();
        double content_delta_positionY = content.getTranslateY() - this.getTranslateY();
        double deltaWidth = yGetBoxWidth();
        double deltaHeight = yGetBoxHeight();
        
        ((Yshape) box).ySetWidth(width, strokeX_included, correct_location);
        ((Yshape) box).ySetHeight(height, strokeY_included, correct_location);

        deltaWidth = yGetBoxWidth() - deltaWidth;
        deltaHeight = yGetBoxHeight() - deltaHeight;
        yMoveContents(-content_delta_positionX/this.yGetWidth()*deltaWidth, -content_delta_positionY/this.yGetHeight()*deltaHeight);
    }
    
    public void ySetBoxSizeWithItsContent(boolean strokeX_included, boolean strokeY_included, boolean correct_location, boolean proportion){
        double largura = yGetContentWidth();
        double altura = yGetContentHeight();
        
        if(proportion){
            if(largura > altura){
                double multiplier = altura / yGetBoxHeight();
                largura = yGetBoxWidth() * multiplier;
            }else{
                double multiplier = largura / yGetBoxWidth();
                altura = yGetBoxHeight() * multiplier;
            }
        }
        ySetBoxSize(largura, altura, strokeX_included, strokeY_included, correct_location);
    }
    
    public void yScaleBoxWithItsContent(boolean strokeX_included, boolean strokeY_included, boolean correct_location, boolean proportion){ //FAZER O TESTE DE ONDE TAVA PRA MEXER NO CONTEUDO JUNTO
        double largura = yGetContentWidth();
        double altura = yGetContentHeight();
        
        if(proportion){
            if(largura > altura){
                YshapeHandler.ySetHeigthWithScale(box, altura, strokeY_included, correct_location);
                YshapeHandler.yScaleXby(box, box.getScaleY(), correct_location);
            }else{
                YshapeHandler.ySetWidthWithScale(box, largura, strokeX_included, correct_location);
                YshapeHandler.yScaleYby(box, box.getScaleX(), correct_location);
            }
        }else{
            YshapeHandler.ySetWidthWithScale(box, largura, strokeX_included, correct_location);
            YshapeHandler.ySetHeigthWithScale(box, altura, strokeY_included, correct_location);
        }
    }
    
    /*public void reajustContentWithBoxSize(){
        if(box instanceof Circle){
                translateInX += box.getBoundsInLocal().getWidth()/2;
                translateInY += box.getBoundsInLocal().getHeight()/2;
            }else if(box instanceof Text || box instanceof Texto){
                translateInY += box.getBoundsInLocal().getHeight()/2;
            }
        
        for (int i = 0; i < todosOSELEMENTOS; i++) {
            if(box instanceof Circle){

            }else if(box instanceof Text || box instanceof Texot){

            }else if().......
        }
    }*/
}