package Biblioteca.OrganizadoresDeNodos;

import Biblioteca.BasicObjects.CenaVisivel;
import Biblioteca.BasicObjects.Formas.*;
import Biblioteca.BasicObjects.VisibleObjectHandler;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

/**
 * Esta classe serve para criar Shapes que contém elementos dentro (Nodos). //VER PRA TER BORDAS INDEPENDENTES (BORDA INFERIOR, DIREITA, SUPERIOR..)
 * VER PRA ARREDONDAR OS CANTOS DE UMA CAIXA QUADRADA E ATE QUEM SABE ELIMINAR O CONSTRUTOR DO CIRCLE
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
    public Caixa(Shape forma, double grossura_borda, Paint cor_fundo, Paint cor_borda){
        caixa = forma;
        container = new Pane();
        setStroke(grossura_borda, cor_borda);
        caixa.setFill(cor_fundo);
        
        events_handler.setUpInteractiveObject();//E SE O CARA NAO QUIZER? E BOTAR ISSO NAS OUTRAS CLASSES PQ N BOTEI :P
        
        getChildren().addAll(caixa, container);
    }
    
    /**
     * Cria uma caixa quadrada apenas para servir de modelo.
     */
    public Caixa(double grossura_borda, Paint cor_fundo, Paint cor_borda) {
        this(new Retangulo(10, 10), grossura_borda, cor_fundo, cor_borda);
    }
    
    /**
     * Cria uma caixa quadrada.
     * @param tamanhoX Largura da caixa.
     * @param tamanhoY Altura da caixa.
     */
    public Caixa(double tamanhoX, double tamanhoY, double grossura_borda, Paint cor_fundo, Paint cor_borda) {
        this(new Retangulo(tamanhoX, tamanhoY), grossura_borda, cor_fundo, cor_borda);
    }
    
    /**
     * Cria uma caixa redonda.
     * @param raio Raio da caixa.
     */
    public Caixa(double raio, double grossura_borda, Paint cor_fundo, Paint cor_borda) {
        this(new Circulo(raio), grossura_borda, cor_fundo, cor_borda);
    }
    
    public Caixa(Caixa caixa){
        this(caixa.caixa, caixa.caixa.getStrokeWidth(), caixa.caixa.getFill(), caixa.caixa.getStroke());
    }
    
    public void setStroke(Double grossura, Paint cor) {
        if(cor != null)
            caixa.setStroke(cor);
        if(grossura != null){
            caixa.setStrokeWidth(grossura);
            setTranslateX(getTranslateX() + grossura/2);///grossura borda?????????
            setTranslateY(getTranslateY() + grossura/2);///grossura borda?????????
        }
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
        return caixa.getBoundsInLocal().getWidth();
    }
    
    /**
     * @return Quanto de altura a caixa ocupa.
     */
    public double getAlturaCaixa(){
        return caixa.getBoundsInLocal().getHeight();
    }
    
    /**
     * Move os conteúdos da caixa para a posição desejada.
     * @param X Distância em pixels para mover no eixo X.
     * @param Y Distância em pixels para mover no eixo Y.
     */
    public void realocar_conteudos(Double X, Double Y){
        if(X != null)
            container.setTranslateX(caixa.getTranslateX() + X);
        if(Y != null)
            container.setTranslateY(caixa.getTranslateY() + Y);
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
        realocar_conteudos((VisibleObjectHandler.getWidth(caixa) - VisibleObjectHandler.getWidth(container))/2,
                (VisibleObjectHandler.getHeigth(caixa) - VisibleObjectHandler.getHeigth(container))/2);
    }
    
    public void resizeBoxWithItsContent(boolean plusBorderX, boolean plusBorderY, boolean proportion){
        double largura = getLarguraConteudo();
        double altura = getAlturaConteudo();
        
        if(proportion){
            if(largura > altura){
                altura = largura;
            }else{
                largura = altura;
            }
        }
        
        setWidthWithScale(largura, plusBorderX);
        setHeigthWithScale(altura, plusBorderY);
        //TALVEZ ATÉ UMA BIND... OU TUDO NA MAO TODA HR Q MUDA O CONTEUDO TBM
        //TENTAR NAO USAR O SCALE....
    }
    
    public void setWidthWithScale(double width, boolean plusBorder){
        double scale = width/getLarguraCaixa();
        if(plusBorder)
            scale = (width + (width/getLarguraCaixa())*caixa.getStrokeWidth()*2)/getLarguraCaixa();
        caixa.setScaleX(scale);
    }
    
    public void setHeigthWithScale(double heigth, boolean plusBorder){
        double scale = heigth/getAlturaCaixa();
        if(plusBorder)
            scale = (heigth + (heigth/getAlturaCaixa())*caixa.getStrokeWidth()*2)/getAlturaCaixa();
        caixa.setScaleY(scale);
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