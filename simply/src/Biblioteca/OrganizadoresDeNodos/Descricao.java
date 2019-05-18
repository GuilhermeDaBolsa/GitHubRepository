package Biblioteca.OrganizadoresDeNodos;

import Biblioteca.BasicObjects.CenaVisivel;
import Biblioteca.BasicObjects.Formas.Texto;
import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 * Classe para criar uma descrição de algum item.
*/
public class Descricao extends CenaVisivel{
    private Caixa caixa;
    private Texto titulo;
    private ArrayList<Texto> descricao = new ArrayList();
    
    //private static String fonte = "Imagens_jogo/Penumbra-HalfSerif-Std_35114.ttf";
    private static double tamanho_fonte_titulo = 18;
    private static double tamanho_fonte_descricao = 13;

    public Descricao(double largura_caixa, double altura_caixa, String titulo, String... descricao) {
        this.titulo = new Texto(titulo, Texto.carregar_fonte("fodase",tamanho_fonte_titulo), null);
        for (int i = 0; i < descricao.length; i++) {
           this.descricao.add(new Texto(descricao[i], Texto.carregar_fonte("mds",tamanho_fonte_descricao), null)); 
        }
        
                
        /*imagem = new Caixa(largura_exterior/3, altura_exterior/5, 3, null, null, false);
        imagem.adicionar_conteudo(conteudo, false);*/
        caixa = new Caixa(largura_caixa, altura_caixa, Color.ALICEBLUE, 6, Color.CADETBLUE);
        caixa.add(this.titulo);
        for (int i = 0; i < descricao.length; i++) {
            caixa.add(this.descricao.get(i));
        }
        
        posicionar_conteudos();
        //caixa.resizeBoxWithItsContent(true, true);
        
        getChildren().addAll(caixa);
    }
    
    private void posicionar_conteudos(){
        titulo.ySetTranslateX((caixa.getLarguraCaixa()- titulo.yGetWidth())/2, 0);
        titulo.ySetTranslateY(6, 0);
        double posicionamento = titulo.getTranslateY();
        /*imagem.setTranslateX((largura_exterior - ((Rectangle) imagem.caixa).getWidth())/2);
        imagem.setTranslateY(titulo.getTranslateY()+titulo.altura_texto_total);*/
        for (int i = 0; i < descricao.size(); i++) {
            descricao.get(i).ySetTranslateX((caixa.getLarguraCaixa() - descricao.get(i).yGetWidth())/2, 0);
            descricao.get(i).ySetTranslateY(posicionamento + 6/*imagem.getTranslateY()+((Rectangle) imagem.caixa).getHeight()+*/, 0);
            posicionamento = descricao.get(i).getTranslateY();
        }
    }
    
}