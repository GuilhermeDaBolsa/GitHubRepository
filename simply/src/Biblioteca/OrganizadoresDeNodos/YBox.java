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
     * Create a box with a custom shape.
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
        
        realocar_conteudos(0.0, 0.0);
        
        getChildren().addAll(box, content);
    }
    
    /**
     * Creates a rectangular box.
     * @param width Box's Width.
     * @param height Box's height.
     */
    public YBox(double width, double height, Paint background_color, double stroke_width, Paint stroke_color) {
        this(new Retangulo(width, height), background_color, stroke_width, stroke_color);
    }
    
    /**
     * Creates a rectangular (10 x 10) box to be a model.
     */
    public YBox(Paint background_color, double stroke_width, Paint stroke_color) {
        this(new Retangulo(10, 10), background_color, stroke_width, stroke_color);
    }
    
    /**
     * Creates a circular box.
     * @param radius Box's radius.
     */
    public YBox(double radius, Paint background_color, double stroke_width, Paint stroke_color) {
        this(new Circulo(radius), background_color, stroke_width, stroke_color);
    }
    
    public void ySetStroke(Double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean correct_location) {
        ((Yshape) box).ySetStroke(stroke_width, stroke_color, stroke_type, correct_location);
    }

    /**
       * Adiciona determinados elementos a box (Nodo(s)).
       * @param conteudo Conteúdo a ser adicionado.
    */
    public void yAddContent(Node... conteudo) {
        for (int i = 0; i < conteudo.length; i++) {
            content.getChildren().add(conteudo[i]);
        }
    }
    
    /**
       * Adiciona uma array de Texto a box.
       * @param conteudo Array com os elementos a serem adicionados.
    */
    public void yAddContent(ArrayList<Node> conteudo) {
        for (int i = 0; i < conteudo.size(); i++) {
            YBox.this.yAddContent(conteudo.get(i));  
        }
    }
    
    /**
     * Remove um conteúdo da box.
     * @param conteudo Conteúdo a ser removido.
     */
    public void yRemoveContent(Node conteudo){
        content.getChildren().remove(conteudo);
    }
    
    /**
     * Remove todos os conteúdos da box.
     */
    public void yClear(){
        content.getChildren().clear();
        realocar_conteudos(0.0, 0.0);
    }
    
    /**
     * Método para ySetContent o conteúdo de uma box.
     * OBS: se o conteudo_velho for == null, a box será limpa.
     * @param conteudo_velho O conteúdo velho.
     * @param conteudo_novo O conteúdo novo;
     */
    public void ySetContent(Node conteudo_velho, Node... conteudo_novo) {
        if(conteudo_velho != null)
            yRemoveContent(conteudo_velho);
        else
            yClear();
        
        yAddContent(conteudo_novo);
    }
    
    public Node[] yGetContent(){
        return content.getChildren().toArray(new Node[content.getChildren().size()]);
    }
    
    /**
     * @return Quanto de largura os objetos da box ocupam.
     */
    public double yGetContentWidth(){
        return content.getBoundsInLocal().getWidth();
    }
    
    /**
     * @return Quanto de altura os objetos da box ocupam.
     */
    public double yGetContentHeight(){
        return content.getBoundsInLocal().getHeight();
    }
    
    /**
     * @return Quanto de largura a box ocupa.
     */
    public double yGetBoxWidth(){
        return ((Yshape) box).yGetWidth(true);
    }
    
    /**
     * @return Quanto de altura a box ocupa.
     */
    public double yGetBoxHeight(){
        return ((Yshape) box).yGetHeight(true);
    }
    
    /**
     * Move os conteúdos da box para a posição desejada.
     * @param X Distância em pixels para mover no eixo X.
     * @param Y Distância em pixels para mover no eixo Y.
     */
    public void realocar_conteudos(Double X, Double Y){
        if(X != null)
            content.setTranslateX(X + ((Yshape) box).yGetStrokeOcupation().LEFT);
        if(Y != null)
            content.setTranslateY(Y + ((Yshape) box).yGetStrokeOcupation().UP);
    }
    
    /**
     * Move os conteúdos da box de acordo com a posição atual deles.
     * @param X Distância em pixels para mover no eixo X.
     * @param Y Distância em pixels para mover no eixo Y.
     */
     public void yMoveContents(double X, double Y){
         realocar_conteudos(content.getTranslateX() + X - ((Yshape) box).yGetStrokeOcupation().LEFT,
                 content.getTranslateY() + Y - ((Yshape) box).yGetStrokeOcupation().UP);
    }
     
     /**
     * Método para tentar alinhar o conteúdo da box ao centro da mesma (pode não funcionar
 dependendo do elemento).
     */
    public void yAlignContents(double pivoelementoX, double pivoelementoY, double pivoCaixaX, double pivoCaixaY){
        realocar_conteudos(((Yshape) box).yGetTranslateX(0) + ((Yshape) box).yGetWidth(false) * pivoCaixaX - yGetContentWidth() * pivoelementoX,
                ((Yshape) box).yGetTranslateY(0) + ((Yshape) box).yGetHeight(false) * pivoCaixaY - yGetContentHeight() * pivoelementoY);
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