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
 * Esta classe serve para criar Shapes que contém elementos dentro (Nodos), //FAZER PRA TER BORDAS INDEPENDENTES (BORDA INFERIOR, DIREITA, SUPERIOR..) E ARREDONDAR AS BORDAS
 * que podem aparecer na frente da caixa.
 */
public class Caixa extends ObjetoInteragivel {
    public Shape caixa = null;
    public Pane container;
    public ArrayList<Node> conteudo_caixa = new ArrayList();
    
    //public DescricaoStatus descricao;
    
    //VER PRA ARREDONDAR OS CANTOS DE UMA CAIXA QUADRADA E ATE QUEM SABE ELIMINAR O CONSTRUTOR DO CIRCLE

    private void ContructorHelper(Shape forma, double grossura_borda, Paint cor_fundo, Paint cor_borda){
        forma.setStrokeWidth(grossura_borda);

        caixa = forma;
        setBackgroundColor(cor_fundo);
        setStrokeColor(cor_borda);

        container = new Pane();
        container.setTranslateY(-grossura_borda/2);
        setUpInteractiveObject();//E SE O CARA NAO QUIZER?
        getChildren().addAll(caixa, container);
    }
    
    /**
     * Cria uma caixa quadrada apenas para servir de modelo.
     * @param grossura_borda Groassura da borda da caixa.
     * @param cor_fundo Cor do fundo da caixa.
     * @param cor_borda Cor da borda da caixa.
     */
    public Caixa(double grossura_borda, Paint cor_fundo, Paint cor_borda) {
        Shape forma = new Rectangle(50, 50);
        ContructorHelper(forma, grossura_borda, cor_fundo, cor_borda);
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
        Shape forma = new Rectangle(tamanhoX, tamanhoY);
        ContructorHelper(forma, grossura_borda, cor_fundo, cor_borda);
    }
    
    /**
     * Cria uma caixa redonda.
     * @param raio Raio da caixa.
     * @param grossura_borda Grossura da borda da caixa.
     * @param cor_fundo Cor do fundo da caixa.
     * @param cor_borda Cor da borda da caixa.
     */
    public Caixa(double raio, double grossura_borda, Paint cor_fundo, Paint cor_borda) {
        Shape forma = new Circle(raio);
        ContructorHelper(forma, grossura_borda, cor_fundo, cor_borda);
    }
    
    /**
     * Cria uma caixa com forma personalizada.
     * @param grossura_borda Grossura da borda da caixa.
     * @param cor_fundo Cor do fundo da caixa.
     * @param cor_borda Cor da borda da caixa.
     */
    public Caixa(Shape forma, double grossura_borda, Paint cor_fundo, Paint cor_borda) {
        ContructorHelper(forma, grossura_borda, cor_fundo, cor_borda);
    }
    
    public Caixa(Node elemento, Paint cor_fundo, Paint cor_borda, double grossura_borda, boolean retangular){//FALTOU ADICIONAR O ELEMENTO????
        double larguraObjeto = elemento.getBoundsInLocal().getWidth();
        double alturaObjeto = elemento.getBoundsInLocal().getHeight();
        Shape forma;
        if(retangular){
            forma = new Rectangle(larguraObjeto + 10, alturaObjeto + 10);
        }else{
            forma = new Circle((larguraObjeto > alturaObjeto) ? larguraObjeto+10 : alturaObjeto+10);
        }
        ContructorHelper(forma, grossura_borda, cor_fundo, cor_borda);
    }
    
    /**
     * @return A altura da caixa em pixels.
     */
    public double getAltura_caixa(){
        return caixa.getBoundsInLocal().getHeight();
    }
    
    /**
     * @return A largura da caixa em pixels.
     */
    public double getLargura_caixa(){
        return caixa.getBoundsInLocal().getWidth();
    }
    
    /**
     * Método para mudar a cor da borda da caixa.
     * @param cor Nova cor.
     */
    public void setStrokeColor(Paint cor) {
        caixa.setStroke(cor);
    }
    
    public void setStrokeWidth(double grossura){
        caixa.setStrokeWidth(grossura);
    }
    
    /**
     * Método para mudar a cor do fundo da caixa.
     * @param cor Nova cor.
     */
    public void setBackgroundColor(Paint cor) {
        caixa.setFill(cor);
    }
   
    public void resizeBoxWithItsContent(){
        
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
        realocar_conteudos((getLargura_caixa() - ObjetoVisivelManipulador.getLargura(container))/2,
                (getAltura_caixa() - ObjetoVisivelManipulador.getAltura(container))/2);
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
            container.setTranslateX(X);
        if(Y != Double.NaN)
            container.setTranslateY(Y);
    }

    /**
       * Adiciona determinados elementos a caixa (Nodo(s)).
       * @param conteudo Conteúdo a ser adicionado.
    */
    public void adicionar_conteudo(Node... conteudo) {
        for (int i = 0; i < conteudo.length; i++) {
            conteudo_caixa.add(conteudo[i]);
            container.getChildren().add(conteudo[i]);
        }
    }
    
    /**
       * Adiciona uma array de Texto a caixa.
       * @param conteudo Array com os elementos a serem adicionados.
    */
    public void adicionar_conteudo(ArrayList<Node> conteudo) {
        for (int i = 0; i < conteudo.size(); i++) {
            adicionar_conteudo(conteudo.get(i));  
        }
    }
    
    /**
     * Remove um conteúdo da caixa.
     * @param conteudo Conteúdo a ser removido.
     */
    public void remover_conteudo(Node conteudo){
        container.getChildren().clear();
        conteudo_caixa.remove(conteudo);
        container.getChildren().addAll(conteudo_caixa);
    }
    
    /**
     * Remove todos os conteúdos da caixa.
     */
    public void limpar_conteudo_caixa(){
        container.getChildren().clear();
        conteudo_caixa.clear();
        container.getChildren().addAll(conteudo_caixa);
    }
    
    /**
     * Método para alterar o conteúdo de uma caixa.
     * OBS: se o conteudo_velho for == null, a caixa será limpa.
     * @param conteudo_velho O conteúdo velho.
     * @param conteudo_novo O conteúdo novo;
     */
    public void alterar_conteudo(Node conteudo_velho, Node conteudo_novo) {
        realocar_conteudos(0, 0);
        container.getChildren().clear();
        if(conteudo_velho == null){
            conteudo_caixa.clear();
            adicionar_conteudo(conteudo_novo);
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
     * Coloca uma descrição na caixa.
     * @param oquefaz Descrição da caixa.
     */
    //public void setDescricaoCaixa(DescricaoStatus oquefaz){
      //  descricao = oquefaz;
    //}
    
}