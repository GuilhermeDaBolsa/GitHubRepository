package Biblioteca.OrganizadoresDeNodos;

import Biblioteca.BasicObjects.CenaVisivel;
import Biblioteca.BasicObjects.Formas.*;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

/**
 * Esta classe serve para criar Shapes que contém elementos dentro (Nodos). //VER PRA TER BORDAS INDEPENDENTES (BORDA INFERIOR, DIREITA, SUPERIOR..)
 */
public class Caixa extends CenaVisivel {
    public Shape caixa = null;
    public Pane container;
    public ArrayList<Node> conteudo_caixa = new ArrayList();
    
    /**
     * Cria uma caixa com forma personalizada.
     * @param forma Forma da caixa.
     * @param grossura_borda Grossura da borda da caixa.
     * @param cor_fundo Cor do fundo da caixa.
     * @param cor_borda Cor da borda da caixa.
     */
    public Caixa(Shape forma, Paint cor_fundo, double grossura_borda, Paint cor_borda){
        caixa = forma;//AAAAAAAAAAAAAAAA AINDA É UM PONTEIRRROOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
        container = new Pane();
        caixa.setFill(cor_fundo);
        ySetStroke(grossura_borda, cor_borda, StrokeType.CENTERED, true);
        
        events_handler.setUpInteractiveObject();//BOTAR ISSO NAS OUTRAS CLASSES PQ N BOTEI :P
        
        getChildren().addAll(caixa, container);
    }
    
    /**
     * Cria uma caixa quadrada apenas para servir de modelo.
     */
    public Caixa(Paint cor_fundo, double grossura_borda, Paint cor_borda) {
        this(new Retangulo(10, 10), cor_fundo, grossura_borda, cor_borda);
    }
    
    /**
     * Cria uma caixa quadrada.
     * @param tamanhoX Largura da caixa.
     * @param tamanhoY Altura da caixa.
     */
    public Caixa(double tamanhoX, double tamanhoY, Paint cor_fundo, double grossura_borda, Paint cor_borda) {
        this(new Retangulo(tamanhoX, tamanhoY), cor_fundo, grossura_borda, cor_borda);
    }
    
    /**
     * Cria uma caixa redonda.
     * @param raio Raio da caixa.
     */
    public Caixa(double raio, Paint cor_fundo, double grossura_borda, Paint cor_borda) {
        this(new Circulo(raio), cor_fundo, grossura_borda, cor_borda);
    }
    
    public Caixa(Caixa caixa){
        this(caixa.caixa, caixa.caixa.getFill(), caixa.caixa.getStrokeWidth(), caixa.caixa.getStroke());
    }
    
    public void ySetStroke(Double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean correct_location) {
        double X = caixa.getTranslateX();
        double Y = caixa.getTranslateY();
        
        ((Forma) caixa).ySetStroke(stroke_width, stroke_color, stroke_type, correct_location);
        
        mover_conteudos(caixa.getTranslateX() - X, caixa.getTranslateY() - Y);
    }

    /**
       * Adiciona determinados elementos a caixa (Nodo(s)).
       * @param conteudo Conteúdo a ser adicionado.
    */
    public void add(Node... conteudo) {
        for (int i = 0; i < conteudo.length; i++) {
            conteudo_caixa.add(conteudo[i]);
            container.getChildren().add(conteudo[i]);
        }
    }
    
    /**
       * Adiciona uma array de Texto a caixa.
       * @param conteudo Array com os elementos a serem adicionados.
    */
    public void add(ArrayList<Node> conteudo) {
        for (int i = 0; i < conteudo.size(); i++) {
            add(conteudo.get(i));  
        }
    }
    
    /**
     * Remove um conteúdo da caixa.
     * @param conteudo Conteúdo a ser removido.
     */
    public void remover(Node conteudo){
        container.getChildren().remove(conteudo);
        conteudo_caixa.remove(conteudo);
    }
    
    /**
     * Remove todos os conteúdos da caixa.
     */
    public void limpar_caixa(){
        container.getChildren().clear();
        conteudo_caixa.clear();
    }
    
    /**
     * Método para alterar o conteúdo de uma caixa.
     * OBS: se o conteudo_velho for == null, a caixa será limpa.
     * @param conteudo_velho O conteúdo velho.
     * @param conteudo_novo O conteúdo novo;
     */
    public void alterar(Node conteudo_velho, Node conteudo_novo) {
        realocar_conteudos(0.0, 0.0);
        container.getChildren().clear();
        if(conteudo_velho == null){
            conteudo_caixa.clear();
            Caixa.this.add(conteudo_novo);
        }else{
            for (int i = 0; i < conteudo_caixa.size(); i++) {
                if(conteudo_caixa.get(i) == conteudo_velho){
                    conteudo_caixa.set(i, conteudo_novo);
                }
            }
            container.getChildren().addAll(conteudo_caixa);
        }
    }
    
    /**
     * @return Quanto de largura os objetos da caixa ocupam.
     */
    public double getLarguraConteudo(){
        return container.getBoundsInLocal().getWidth();
    }
    
    /**
     * @return Quanto de altura os objetos da caixa ocupam.
     */
    public double getAlturaConteudo(){
        return container.getBoundsInLocal().getHeight();
    }
    
    /**
     * @return Quanto de largura a caixa ocupa.
     */
    public double getLarguraCaixa(){
        return ((Forma) caixa).yGetWidth();
    }
    
    /**
     * @return Quanto de altura a caixa ocupa.
     */
    public double getAlturaCaixa(){
        return ((Forma) caixa).yGetHeight();
    }
    
    /**
     * Move os conteúdos da caixa para a posição desejada.
     * @param X Distância em pixels para mover no eixo X.
     * @param Y Distância em pixels para mover no eixo Y.
     */
    public void realocar_conteudos(Double X, Double Y){
        if(X != null)
            container.setTranslateX(((Forma) caixa).yGetTranslateX(0) + X);
        if(Y != null)
            container.setTranslateY(((Forma) caixa).yGetTranslateY(0) + Y);
    }
    
    /**
     * Move os conteúdos da caixa de acordo com a posição atual deles.
     * @param X Distância em pixels para mover no eixo X.
     * @param Y Distância em pixels para mover no eixo Y.
     */
     public void mover_conteudos(double X, double Y){
         realocar_conteudos(container.getTranslateX() + X, container.getTranslateY() + Y);
    }
     
     /**
     * Método para tentar alinhar o conteúdo da caixa ao centro da mesma (pode não funcionar
     * dependendo do elemento).
     */
    public void alinhar_conteudos_centro(){
        realocar_conteudos((getLarguraCaixa() - getLarguraConteudo())/2,
                (getAlturaCaixa() - getAlturaConteudo())/2);
    }
    
    public void resizeBox(double width, double height, boolean strokeX_included, boolean strokeY_included, boolean correct_location){
        double content_delta_positionX = container.getTranslateX() - this.getTranslateX();
        double content_delta_positionY = container.getTranslateY() - this.getTranslateY();
        double deltaWidth = getLarguraCaixa();
        double deltaHeight = getAlturaCaixa();
        
        ((Forma) caixa).ySetWidth(width, strokeX_included, correct_location);
        ((Forma) caixa).ySetHeight(height, strokeY_included, correct_location);

        deltaWidth = getLarguraCaixa() - deltaWidth;
        deltaHeight = getAlturaCaixa() - deltaHeight;
        mover_conteudos(content_delta_positionX/this.getWidth()*deltaWidth, content_delta_positionY/this.getHeight()*deltaHeight);
    }
    
    public void resizeBoxWithItsContent(boolean strokeX_included, boolean strokeY_included, boolean correct_location, boolean proportion){
        double largura = getLarguraConteudo();
        double altura = getAlturaConteudo();
        
        if(proportion){
            if(largura > altura){
                double multiplier = altura / getAlturaCaixa();
                largura = getLarguraCaixa() * multiplier;
            }else{
                double multiplier = largura / getLarguraCaixa();
                altura = getAlturaCaixa() * multiplier;
            }
        }
        resizeBox(largura, altura, strokeX_included, strokeY_included, correct_location);
    }
    
    public void scaleBoxWithItsContent(boolean strokeX_included, boolean strokeY_included, boolean correct_location, boolean proportion){ //FAZER O TESTE DE ONDE TAVA PRA MEXER NO CONTEUDO JUNTO
        double largura = getLarguraConteudo();
        double altura = getAlturaConteudo();
        
        if(proportion){
            if(largura > altura){
                YshapeHandler.ySetHeigthWithScale(caixa, altura, strokeY_included, correct_location);
                YshapeHandler.yScaleXby(caixa, caixa.getScaleY(), correct_location);
            }else{
                YshapeHandler.ySetWidthWithScale(caixa, largura, strokeX_included, correct_location);
                YshapeHandler.yScaleYby(caixa, caixa.getScaleX(), correct_location);
            }
        }else{
            YshapeHandler.ySetWidthWithScale(caixa, largura, strokeX_included, correct_location);
            YshapeHandler.ySetHeigthWithScale(caixa, altura, strokeY_included, correct_location);
        }
    }
    
    public void reajustContentWithBoxSize(){
        /*if(caixa instanceof Circle){
                translateInX += caixa.getBoundsInLocal().getWidth()/2;
                translateInY += caixa.getBoundsInLocal().getHeight()/2;
            }else if(caixa instanceof Text || caixa instanceof Texto){
                translateInY += caixa.getBoundsInLocal().getHeight()/2;
            }
        
        for (int i = 0; i < todosOSELEMENTOS; i++) {
            if(caixa instanceof Circle){

            }else if(caixa instanceof Text || caixa instanceof Texot){

            }else if().......
        }*/
    }
}