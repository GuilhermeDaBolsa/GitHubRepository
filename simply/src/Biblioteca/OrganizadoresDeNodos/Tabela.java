package Biblioteca.OrganizadoresDeNodos;

import Biblioteca.BasicObjects.CenaVisivel;
import Biblioteca.BasicObjects.Formas.Linha;
import java.util.ArrayList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

//FAZER OS ITENS CABEÇALHOS (QUE CATEGORIZAM AQUELA LINHA OU COLUNA, EX: DIA)
//ALEM DO MARGIN (QUE TEM Q VIRA PADDING, TEM Q POR A DISTANCIA ENTRE CELULAS, TA!!!!?!!!!

public class Tabela extends CenaVisivel{
    public ArrayList<Node> elementos = new ArrayList();
    private ArrayList<Point2D> posicoes = new ArrayList();
    
    private double min_marginX;
    private double min_marginY;
    private double larguraCelula = -1;
    private double alturaCelula = -1;
    
    public boolean mostrarLinhasX;
    public boolean mostrarLinhasY;
    
    /**
     * n_colunas e n_linhas é o número de colunas e linhas que devem aparecer (caso for menor 1 irão aparecer o número necessário para todos os objetos).
     */
    public int n_linhas = -1;
    public int n_colunas = -1;
    
    private Caixa caixaCelulasPadrao;
    
    private double grossuraLinhaX = 1;
    private Paint corLinhaX = Color.CORNFLOWERBLUE.darker().desaturate();
    private double grossuraLinhaY = 1;
    private Paint corLinhaY = Color.CORNFLOWERBLUE.darker().desaturate();

    //A TABELA VEM COM TAMANHO AUTOMATICO, CONFORME O MAIOR ELEMENTO AS CELULAS SE REAJUSTAM
    //FAZER UM METODO PRO CARA POR NA MÃO O TAMANHO DAS CELULAS?
    
    /**
     * Sets some atributes for the table.
     * @param min_marginX The minimum distance that the biggest object must have
     * from the lines in the X axis (the others objects will have it too as they are smaller).
     * @param min_marginY The minimum distance that the biggest object must have
     * from the lines in the Y axis (the others objects will have it too as they are smaller).
     * @param mostrarLinhasX Show line divisions in the X axis.
     * @param mostrarLinhasY Show line divisions in the Y axis.
     */
    public Tabela(double min_marginX, double min_marginY, 
            boolean mostrarLinhasX, boolean mostrarLinhasY){
        this.min_marginX = min_marginX;
        this.min_marginY = min_marginY;
        this.mostrarLinhasX = mostrarLinhasX;
        this.mostrarLinhasY = mostrarLinhasY;
        caixaCelulasPadrao = new Caixa(Color.BEIGE, 0, Color.BLACK);
    }
    
    /**
     * Muda a aparencia das linhas que separam as linhas (linhas em X).
     * @param grossura Grossura da linha.
     * @param cor Cor da linha.
     */
    public void setModeloLinhaX(Double grossura, Paint cor){
        if(grossura != null)
            this.grossuraLinhaX = grossura;
        if(cor != null)
            this.corLinhaX = cor;
        
        atualizar();
    }
    
    /**
     * Muda a aparencia das linhas que separam as colunas (linhas em Y).
     * @param grossura Grossura da linha.
     * @param cor Cor da linha.
     */
    public void setModeloLinhaY(Double grossura, Paint cor){
        if(grossura != null)
            this.grossuraLinhaY = grossura;
        if(cor != null)
            this.corLinhaY = cor;
        
        atualizar();
    }
    
    public void setCaixaCelulas(Caixa caixa){//LIMITAR O CARA AQUI QUE NEM LIMITO NAS LINHAS
        caixaCelulasPadrao = new Caixa(caixa);
    }
    
    public Caixa getCaixaCelulas(){
        return caixaCelulasPadrao;
    }
    
    /**
     * Monta a tabela, calculando tamanho, posição e número de linhas e as posições dos objetos.
     */
    public void atualizar(){
        this.getChildren().clear();
        
        //calcula tudo q precisa
        Point2D farestsObjects = farestsPositionsInTable();
        int Ncolunas = (int) (n_colunas > 0 ? n_colunas : farestsObjects.getX()+1);
        int Nlinhas = (int) (n_linhas > 0 ? n_linhas : farestsObjects.getY()+1);
        
        Point2D sizeOfBiggestElements = biggestSizeInTable();
        double larguraCelulas = (larguraCelula >= 0 ? larguraCelula : sizeOfBiggestElements.getX()) + min_marginX * 2;
        double alturaCelulas = (alturaCelula >= 0 ? alturaCelula : sizeOfBiggestElements.getY()) + min_marginY * 2;
        
        double larguraLinhas = Ncolunas * larguraCelulas;
        double alturaLinhas = Nlinhas * alturaCelulas;
        
        caixaCelulasPadrao.ySetStroke(8.0, Color.ROYALBLUE, StrokeType.OUTSIDE, true);
        caixaCelulasPadrao.resizeBox(larguraCelulas, alturaCelulas, true, true, true);
        
        //posiciona os elementos VER PRA ADICIONAR CAIXAS NOS LUGAR VAZIO
        for (int i = 0; i < elementos.size(); i++) {
            Caixa caixa = new Caixa(caixaCelulasPadrao);
            caixa.add(elementos.get(i));
            
            double posicaoX = posicoes.get(i).getX();
            double posicaoY = posicoes.get(i).getY(); 
            
            if(posicaoX > Ncolunas || posicaoY > Nlinhas || posicaoX < 0 || posicaoY < 0)
                continue;
            
            double translateInX = posicaoX * larguraCelulas;
            double translateInY = posicaoY * alturaCelulas;
            caixa.setTranslateX(translateInX);
            caixa.setTranslateY(translateInY);
            
            caixa.alinhar_conteudos_centro();
            this.getChildren().add(caixa);
        }
        
        //faz as linhas em X
        if(mostrarLinhasX){
            for (int i = 1; i < Nlinhas; i++) {
                Linha linhaX = new Linha(0, 0, larguraLinhas, 0, grossuraLinhaX, corLinhaX);
                linhaX.ySetTranslateY((min_marginY + alturaCelulas)*i, 0);
                this.getChildren().add(linhaX);
            }
        }
        
        //faz as linhas em Y
        if(mostrarLinhasY){
            for (int i = 1; i < Ncolunas; i++) {
                Linha linhaY = new Linha(0, 0, 0, alturaLinhas, grossuraLinhaY, corLinhaY);
                linhaY.ySetTranslateX((min_marginX + larguraCelulas)*i, 0);
                this.getChildren().add(linhaY);
            }
        }
    }
    
    /**
     * Encontra a maior largura e altura entre todas de todos os elementos.
     * @return Uma tupla Point2D cujo X é a maior largura e Y a maior altura.
     */
    private Point2D biggestSizeInTable(){
        double width = 0;
        double height = 0;
        Bounds element;
        for (int i = 0; i < elementos.size(); i++) {
            element = elementos.get(i).getBoundsInLocal();
            if(element.getWidth() > width)
                width = element.getWidth();
            if(element.getHeight() > height)
                height = element.getHeight();
        }
        return new Point2D(width, height);
    }
    
    private Point2D farestsPositionsInTable(){
        int X = 0;
        int Y = 0;
        
        if(!posicoes.isEmpty()){
            X = (int) posicoes.get(0).getX();
            Y = (int) posicoes.get(0).getY();

            for (int i = 1; i < posicoes.size(); i++) {
                if(posicoes.get(i).getX() > X){
                    X = (int) posicoes.get(i).getX();
                }
                if(posicoes.get(i).getY() > Y){
                    Y = (int) posicoes.get(i).getY();
                }
            }
        }
        return new Point2D(X, Y);
    }
    
    /**
     * Adiciona um objeto à tabela. (se ja existir um elemento naquele lugar ver o que fazer, por enquanto substitui)
     * @param object É o objeto a ser adicionado.
     * @param positionInTableX Posição X na tabela.
     * @param positionInTableY Posição Y na tabela.
     */
    public void add(Node object, int positionInTableX, int positionInTableY){
        boolean elementInSamePosition = false;
        
        for (int i = 0; i < posicoes.size(); i++) {
            if(positionInTableX == posicoes.get(i).getX() && positionInTableY == posicoes.get(i).getY()){
                elementos.set(i, object);
                elementInSamePosition = true;
                break;
            }
        }
        if(!elementInSamePosition){
            elementos.add(object);
            posicoes.add(new Point2D(positionInTableX, positionInTableY));
        }
        atualizar();
    }
    
    public void remove(Node object){
        int index = elementos.indexOf(object);
        elementos.remove(index);
        posicoes.remove(index);
        atualizar();
    }
}