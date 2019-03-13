package ObjetosBase.visibleObjects;

import ObjetosBase.logicaEcore.actionsEinteractive.InteractiveObject;
import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 * Classe para criar uma descrição de algum item.
*/
public class Descricao extends InteractiveObject{
    private Caixa caixa;
    private Texto titulo;
    private ArrayList<Texto> descricao = new ArrayList();
    
    private double largura;
    private double altura;
    
    //private static String fonte = "Imagens_jogo/Penumbra-HalfSerif-Std_35114.ttf";
    private static double tamanho_fonte_titulo = 18;
    private static double tamanho_fonte_descricao = 13;

    public Descricao(double largura_caixa, double altura_caixa, String titulo, String... descricao) {
        largura = largura_caixa;
        altura = altura_caixa;
        
        this.titulo = new Texto(titulo, Texto.carregar_fonte("fodase",tamanho_fonte_titulo), null);
        for (int i = 0; i < descricao.length; i++) {
           this.descricao.add(new Texto(descricao[i], Texto.carregar_fonte("mds",tamanho_fonte_descricao),null)); 
        }
        
                
        /*imagem = new Caixa(largura_exterior/3, altura_exterior/5, 3, null, null, false);
        imagem.adicionar_conteudo(conteudo, false);*/
        caixa = new Caixa(largura, altura, 6, Color.ALICEBLUE, Color.CADETBLUE);
        caixa.adicionar_conteudo(this.titulo);
        for (int i = 0; i < descricao.length; i++) {
            caixa.adicionar_conteudo(this.descricao.get(i));
        }
        
        posicionar_conteudos();
        
        
        getChildren().addAll(caixa);
    }
    
    private void posicionar_conteudos(){
        titulo.setTranslateX((largura - titulo.largura_texto_total)/2);
        titulo.setTranslateY(titulo.altura_texto_total+6);
        double posicionamento = titulo.getTranslateY();
        /*imagem.setTranslateX((largura_exterior - ((Rectangle) imagem.caixa).getWidth())/2);
        imagem.setTranslateY(titulo.getTranslateY()+titulo.altura_texto_total);*/
        for (int i = 0; i < descricao.size(); i++) {
            descricao.get(i).setTranslateX((largura - descricao.get(i).largura_texto_total)/2);
            descricao.get(i).setTranslateY(posicionamento + descricao.get(i).altura_texto_total + 6/*imagem.getTranslateY()+((Rectangle) imagem.caixa).getHeight()+*/);
            posicionamento = descricao.get(i).getTranslateY();
        }
    }
    
}