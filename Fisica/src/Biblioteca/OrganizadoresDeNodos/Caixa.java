package Biblioteca.OrganizadoresDeNodos;

import Biblioteca.BasicObjects.ObjetoVisivelManipulador;
import Biblioteca.InteractiveObjects.ObjetoInteragivel;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Esta classe serve para criar Shapes que contém elementos dentro (Nodos), //SE PUDER FAZER PRA TER BORDAS INDEPENDENTES (BORDA INFERIOR, DIREITA, SUPERIOR..) E ARREDONDAR AS BORDAS
 * que podem aparecer na frente da caixa.
 * VER PRA ARREDONDAR OS CANTOS DE UMA CAIXA QUADRADA E ATE QUEM SABE ELIMINAR O CONSTRUTOR DO CIRCLE
 * USAR MINHAS FORMAS AO INVES DAS DO JAVA :P
 */
public class Caixa extends ObjetoInteragivel {
    public Shape caixa = null;
    public Pane container;
    public ArrayList<Node> conteudo_caixa = new ArrayList();
    
    /**
     * Cria uma caixa com forma personalizada.
     * @param grossura_borda Grossura da borda da caixa.
     * @param cor_fundo Cor do fundo da caixa.
     * @param cor_borda Cor da borda da caixa.
     */
    public Caixa(Shape forma, double grossura_borda, Paint cor_fundo, Paint cor_borda){
        container = new Pane();
        caixa = forma;
        setBorda(grossura_borda, cor_borda);
        setBackgroundColor(cor_fundo);
        
        setUpInteractiveObject();//E SE O CARA NAO QUIZER? E BOTAR ISSO NAS OUTRAS CLASSES PQ N BOTEI :P
        
        getChildren().addAll(caixa, container);
    }
    
    /**
     * Cria uma caixa quadrada apenas para servir de modelo.
     * @param grossura_borda Groassura da borda da caixa.
     * @param cor_fundo Cor do fundo da caixa.
     * @param cor_borda Cor da borda da caixa.
     */
    public Caixa(double grossura_borda, Paint cor_fundo, Paint cor_borda) {
        this(new Rectangle(50, 50), grossura_borda, cor_fundo, cor_borda);
    }
    
    /**
     * Cria uma caixa quadrada.
     * @param tamanhoX Largura da caixa.
     * @param tamanhoY Altura da caixa.
     * @param grossura_borda Groassura da borda da caixa.
     * @param cor_fundo Cor do fundo da caixa.
     * @param cor_borda Cor da borda da caixa.
     */
    public Caixa(double tamanhoX, double tamanhoY, double grossura_borda, Paint cor_fundo, Paint cor_borda) {
        this(new Rectangle(tamanhoX, tamanhoY), grossura_borda, cor_fundo, cor_borda);
    }
    
    /**
     * Cria uma caixa redonda.
     * @param raio Raio da caixa.
     * @param grossura_borda Grossura da borda da caixa.
     * @param cor_fundo Cor do fundo da caixa.
     * @param cor_borda Cor da borda da caixa.
     */
    public Caixa(double raio, double grossura_borda, Paint cor_fundo, Paint cor_borda) {
        this(new Circle(raio), grossura_borda, cor_fundo, cor_borda);
    }
    
    /**
     * @return A largura da caixa em pixels.
     */
    public double getLargura_caixa(){
        return caixa.getBoundsInLocal().getWidth();
    }
    
    /**
     * @return A altura da caixa em pixels.
     */
    public double getAltura_caixa(){
        return caixa.getBoundsInLocal().getHeight();
    }

    /**
     * Muda a grossura e a cor da borda da caixa.
     * @param grossura Nova grossura da borda.
     * @param cor Nova cor.
     */
    public void setBorda(double grossura, Paint cor){//TERIA Q AUMENTAR O TAMANHO DA CAIXA PQ A GROSSURA BORDA VAI PRA PORRA DE DENTRO DA CAIXA TBM AAAAAa
        if(cor != null)                              //MUDA NAS FORMAS, AI SE FOR INSTACE OF DAS FORMA MINHA FICA D BOA SE N USA SCALE......
            caixa.setStroke(cor);
        if(grossura != Double.NaN){
            caixa.setStrokeWidth(grossura);
            caixa.setTranslateX(grossura/2);///grossura borda?????????
            caixa.setTranslateY(grossura/2);///grossura borda?????????
            container.setTranslateX(grossura);///grossura borda?????????
            container.setTranslateY(grossura);///grossura borda?????????
        }
    }
    
    public double getGrossuraBorda(){
        return caixa.getStrokeWidth();
    }
    
    /**
     * Método para mudar a cor do fundo da caixa.
     * @param cor Nova cor.
     */
    public void setBackgroundColor(Paint cor) {
        caixa.setFill(cor);
    }
   
    public void resizeBoxWithItsContent(){
        //TALVEZ ATÉ UMA BIND... OU TUDO NA MAO TODA HR Q MUDA O CONTEUDO TBM
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
    
    /**
     * Método para tentar alinhar o conteúdo da caixa ao centro da mesma (pode não funcionar
     * dependendo do elemento).
     */
    public void alinhar_conteudos_centro(){
        realocar_conteudos((getLargura_caixa() - 2*getGrossuraBorda() - ObjetoVisivelManipulador.getLargura(container))/2,
                (getAltura_caixa() - 2*getGrossuraBorda() - ObjetoVisivelManipulador.getAltura(container))/2);
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
     * Move os conteúdos da caixa para a posição desejada.
     * @param X Distância em pixels para mover no eixo X.
     * @param Y Distância em pixels para mover no eixo Y.
     */
    public void realocar_conteudos(double X, double Y){
        if(X != Double.NaN)
            container.setTranslateX(X + getGrossuraBorda());
        if(Y != Double.NaN)
            container.setTranslateY(Y + getGrossuraBorda());
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
        realocar_conteudos(0, 0);
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
}