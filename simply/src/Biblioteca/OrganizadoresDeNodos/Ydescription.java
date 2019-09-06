package Biblioteca.OrganizadoresDeNodos;

import Biblioteca.BasicObjects.YvisibleScene;
import Biblioteca.BasicObjects.Formas.yText;
import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 * Class is under contruction and it is not meant to be used.
 * (IDEA: use YBOX and YTEXT to create just a description, so it must be more especialized on positioning text and others...)
*/
public class Ydescription extends YvisibleScene{
    private YBox caixa;
    private yText titulo;
    private ArrayList<yText> descricao = new ArrayList();
    
    //private static String fonte = "Imagens_jogo/Penumbra-HalfSerif-Std_35114.ttf";
    private static double tamanho_fonte_titulo = 18;
    private static double tamanho_fonte_descricao = 13;

    public Ydescription(double largura_caixa, double altura_caixa, String titulo, String... descricao) {
        this.titulo = new yText(titulo, this.titulo.carregar_fonte("fodase", tamanho_fonte_titulo), null);
        for (int i = 0; i < descricao.length; i++) {
           this.descricao.add(new yText(descricao[i], this.descricao.get(i).carregar_fonte("mds",tamanho_fonte_descricao), null)); 
        }
        
                
        /*imagem = new YBox(largura_exterior/3, altura_exterior/5, 3, null, null, false);
        imagem.adicionar_conteudo(conteudo, false);*/
        caixa = new YBox(largura_caixa, altura_caixa, Color.ALICEBLUE, 6, Color.CADETBLUE);
        caixa.yAddContent(this.titulo);
        for (int i = 0; i < descricao.length; i++) {
            caixa.yAddContent(this.descricao.get(i));
        }
        
        posicionar_conteudos();
        //caixa.ySetBoxSizeWithItsContent(true, true);
        
        getChildren().addAll(caixa);
    }
    
    private void posicionar_conteudos(){
        titulo.ySetTranslateX((caixa.yGetBoxWidth()- titulo.yGetWidth())/2, 0);
        titulo.ySetTranslateY(6, 0);
        double posicionamento = titulo.getTranslateY();
        /*imagem.setTranslateX((largura_exterior - ((Rectangle) imagem.caixa).getWidth())/2);
        imagem.setTranslateY(titulo.getTranslateY()+titulo.altura_texto_total);*/
        for (int i = 0; i < descricao.size(); i++) {
            descricao.get(i).ySetTranslateX((caixa.yGetBoxWidth() - descricao.get(i).yGetWidth())/2, 0);
            descricao.get(i).ySetTranslateY(posicionamento + 6/*imagem.getTranslateY()+((Rectangle) imagem.caixa).getHeight()+*/, 0);
            posicionamento = descricao.get(i).getTranslateY();
        }
    }
    
}