package Biblioteca.OrganizadoresDeNodos;

import Biblioteca.InteractiveObjects.ObjetoInteragivel;
import Biblioteca.BasicObjects.Formas.Linha;
import java.util.ArrayList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Tabela extends ObjetoInteragivel{
    private ArrayList<Caixa> elementos = new ArrayList();
    private ArrayList<Point2D> posicoes = new ArrayList();
    
    private int minPositionX = 1;
    private int minPositionY = 1;
    private int PosElementoMaisADireita = 0;
    private int PosElementoMaisABaixo = 0;
    
    private double espacoEntreElementosX;
    private double espacoEntreElementosY;
    private double larguraCelula = -1;
    private double alturaCelula = -1;
    
    public boolean mostrarLinhasX;
    public boolean mostrarLinhasY;
    public boolean mostrarBorda;
    
    private Caixa caixaContorno = new Caixa(2, Color.WHITE, Color.BLACK);
    private Caixa caixaCelulasPadrao = new Caixa(1, Color.RED, Color.TRANSPARENT);
    private double grossuraLinhaX = 1;
    private Paint corLinhaX = Color.CORNFLOWERBLUE.darker().desaturate();
    private double grossuraLinhaY = 1;
    private Paint corLinhaY = Color.CORNFLOWERBLUE.darker().desaturate();

    
    //A TABELA VEM COM TAMANHO AUTOMATICO, CONFORME O MAIOR ELEMENTO AS CELULAS SE REAJUSTAM
    //FAZER UM METODO PRO CARA POR NA MÃO O TAMANHO DAS CELULAS
    
    
    //FAZER AS BORDAS DOS OBJETOS EM CAIXAS
    
    public Tabela(double espacoEntreElementosX, double espacoEntreElementosY, 
            boolean mostrarLinhasX, boolean mostrarLinhasY, boolean mostrarBordaTabela){
        this.espacoEntreElementosX = espacoEntreElementosX;
        this.espacoEntreElementosY = espacoEntreElementosY;
        this.mostrarLinhasX = mostrarLinhasX;
        this.mostrarLinhasY = mostrarLinhasY;
        this.mostrarBorda = mostrarBordaTabela;
    }
    
    /**
     * Muda a aparencia das linhas que separam as linhas (linhas em X).
     * @param grossura Grossura da linha.
     * @param cor Cor da linha.
     */
    public void setModeloLinhaX(double grossura, Paint cor){
        if(grossura != Double.NaN)
            this.grossuraLinhaX = grossura;
        if(cor != null)
            this.corLinhaX = cor;
    }
    
    /**
     * Muda a aparencia das linhas que separam as colunas (linhas em Y).
     * @param grossura Grossura da linha.
     * @param cor Cor da linha.
     */
    public void setModeloLinhaY(double grossura, Paint cor){
        if(grossura != Double.NaN)
            this.grossuraLinhaY = grossura;
        if(cor != null)
            this.corLinhaY = cor;
    }
    
    /**
     * Altera o fundo e a borda externa da tabela.
     * @param grossuraBorda Grossura da borda.
     * @param corBorda Cor da borda.
     * @param corFundo Cor do fundo da tabela.
     */
    public void setModeloFundoEBorda(double grossuraBorda, Paint corBorda, Paint corFundo){
        if(corFundo != null)
            caixaContorno.setBackgroundColor(corFundo);
        if(corBorda != null)
            caixaContorno.setStrokeColor(corBorda);
        if(grossuraBorda != Double.NaN)
            caixaContorno.setStrokeWidth(grossuraBorda);
    }
    
    /**
     * Monta a tabela, calculando tamanho, posição e número de linhas e as posições dos objetos.
     * @param colunas É o número de colunas que devem aparecer (caso for menor 1 irão aparecer o número necessário para todos os objetos).
     * @param linhas É o número de colunas que devem aparecer (caso for menor 1 irão aparecer o número necessário para todos os objetos).
     */
    public void montarTabela(int colunas, int linhas){
        this.getChildren().clear();
        
        //calcula tudo q precisa
        int Ncolunas = colunas > 0 ? colunas : PosElementoMaisADireita;
        int Nlinhas = linhas > 0 ? linhas : PosElementoMaisABaixo;
        Point2D sizeOfBiggestElements = biggestSizeInTable();
        double larguraCelulas = larguraCelula >= 0 ? larguraCelula : sizeOfBiggestElements.getX();
        double alturaCelulas = alturaCelula >= 0 ? alturaCelula : sizeOfBiggestElements.getY();
        double larguraLinhas = Ncolunas*(larguraCelulas + espacoEntreElementosX);
        double alturaLinhas = Nlinhas*(alturaCelulas + espacoEntreElementosY);
        
        //faz a borda da tabela
        if(mostrarBorda){
            ((Rectangle) caixaContorno.caixa).setWidth(larguraLinhas); //VER COMO REDIMENCIONAR UMA SHAPE DIREITINHO PLSS E SEM SCALE :/
            ((Rectangle) caixaContorno.caixa).setHeight(alturaLinhas);
            this.getChildren().add(caixaContorno);
        }else{
            //ADICIONA UMA COPIA DA CAIXACONTORNO MAS SEM A BORDA :)
        }
        
        //posiciona os elementos VER PRA ADICIONAR CAIXAS NOS LUGAR VAZIO
        for (int i = 0; i < elementos.size(); i++) {
            Caixa caixa = elementos.get(i);
            double posicaoX = posicoes.get(i).getX();
            double posicaoY = posicoes.get(i).getY(); 
            if(posicaoX > Ncolunas || posicaoY > Nlinhas || posicaoX < minPositionX || posicaoY < minPositionY)
                continue;
            double translateInX = (posicaoX - 1)*(larguraCelulas + espacoEntreElementosX) + espacoEntreElementosX/2;
            double translateInY = (posicaoY - 1)*(alturaCelulas + espacoEntreElementosY) + espacoEntreElementosY/2;
            ((Rectangle) caixa.caixa).setWidth(larguraCelulas);
            ((Rectangle) caixa.caixa).setHeight(alturaCelulas);
            caixa.setTranslateX(translateInX);
            caixa.setTranslateY(translateInY);
            caixa.alinhar_conteudos_centro();
            this.getChildren().add(caixa);
        }
        
        //faz as linhas em X
        if(mostrarLinhasX){
            for (int i = 1; i <= Nlinhas; i++) {
                Linha linhaX = new Linha(0, 0, larguraLinhas, 0, grossuraLinhaX, corLinhaX);
                linhaX.setTranslateY((espacoEntreElementosY + alturaCelulas)*i);
                this.getChildren().add(linhaX);
            }
        }
        
        //faz as linhas em Y
        if(mostrarLinhasY){
            for (int i = 1; i <= Ncolunas; i++) {
                Linha linhaY = new Linha(0, 0, 0, alturaLinhas, grossuraLinhaY, corLinhaY);
                linhaY.setTranslateX((espacoEntreElementosX + larguraCelulas)*i);
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
                elementos.get(i).alterar_conteudo(null, object);
                elementInSamePosition = true;
                break;
            }
        }
        
        if(!elementInSamePosition){
            Caixa container = new Caixa(0, Color.WHITE, Color.BLACK);
            container.adicionar_conteudo(object);
            elementos.add(container);
            posicoes.add(new Point2D(positionInTableX, positionInTableY));
            if(positionInTableX > PosElementoMaisADireita){
                PosElementoMaisADireita = positionInTableX;
            }
            if(positionInTableY > PosElementoMaisABaixo){
                PosElementoMaisABaixo = positionInTableY;
            }
        }
    }
}