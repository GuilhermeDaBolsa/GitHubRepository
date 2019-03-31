package Biblioteca.OrganizadoresDeNodos;

import Biblioteca.BasicObjects.Formas.Texto;
import Biblioteca.InteractiveObjects.ObjetoInteragivel;
import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 * Classe para criar uma descrição de algum item.
*/
public class Descricao extends ObjetoInteragivel{
    private Caixa caixa;
    private Texto titulo;
    private ArrayList<Texto> descricao = new ArrayList();
    
    //private static String fonte = "Imagens_jogo/Penumbra-HalfSerif-Std_35114.ttf";
    private static double tamanho_fonte_titulo = 18;
    private static double tamanho_fonte_descricao = 13;

    public Descricao(double largura_caixa, double altura_caixa, String titulo, String... descricao) {
        this.titulo = new Texto(titulo, Texto.carregar_fonte("fodase",tamanho_fonte_titulo), null);
        for (int i = 0; i < descricao.length; i++) {
           this.descricao.add(new Texto(descricao[i], Texto.carregar_fonte("mds",tamanho_fonte_descricao),null)); 
        }
        
                
        /*imagem = new Caixa(largura_exterior/3, altura_exterior/5, 3, null, null, false);
        imagem.adicionar_conteudo(conteudo, false);*/
        caixa = new Caixa(largura_caixa, altura_caixa, 6, Color.ALICEBLUE, Color.CADETBLUE);
        caixa.adicionar_conteudo(this.titulo);
        for (int i = 0; i < descricao.length; i++) {
            caixa.adicionar_conteudo(this.descricao.get(i));
        }
        
        posicionar_conteudos();
        //caixa.resizeBoxWithItsContent(true, true);
        
        getChildren().addAll(caixa);
    }
    
    private void posicionar_conteudos(){
        titulo.setTranslateX((caixa.getLargura_caixa() - titulo.getLargura())/2);
        titulo.setTranslateY(6);
        double posicionamento = titulo.getTranslateY();
        /*imagem.setTranslateX((largura_exterior - ((Rectangle) imagem.caixa).getWidth())/2);
        imagem.setTranslateY(titulo.getTranslateY()+titulo.altura_texto_total);*/
        for (int i = 0; i < descricao.size(); i++) {
            descricao.get(i).setTranslateX((caixa.getLargura_caixa() - descricao.get(i).getLargura())/2);
            descricao.get(i).setTranslateY(posicionamento + descricao.get(i).getAltura() + 6/*imagem.getTranslateY()+((Rectangle) imagem.caixa).getHeight()+*/);
            posicionamento = descricao.get(i).getTranslateY();
        }
    }
    
}