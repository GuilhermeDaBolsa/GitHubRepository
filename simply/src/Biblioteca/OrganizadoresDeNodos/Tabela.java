package Biblioteca.OrganizadoresDeNodos;

import Biblioteca.BasicObjects.CenaVisivel;
import Biblioteca.BasicObjects.Formas.Linha;
import java.util.ArrayList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeType;

//FAZER OS ITENS CABEÇALHOS (QUE CATEGORIZAM AQUELA LINHA OU COLUNA, EX: DIA)

public class Tabela extends CenaVisivel{
    public ArrayList<Node> elementos = new ArrayList();
    private ArrayList<Point2D> posicoes = new ArrayList();
    
    public double min_paddingX;
    public double min_paddingY;
    public double cells_X_distance = 0;
    public double cells_Y_distance = 0;
    public double cells_width = -1;
    public double cells_height = -1;
    
    public boolean show_horizontal_lines;
    public boolean show_vertical_lines;
    
    /**
     * n_colunas e n_linhas é o número de colunas e linhas que devem aparecer (caso for menor 1 irão aparecer o número necessário para todos os objetos).
     */
    public int n_linhas = -1;
    public int n_colunas = -1;
    
    private double cell_stroke_width;
    private Paint cell_stroke_color;
    private Paint cell_background_color;
    
    private double lineX_stroke_width;
    private Paint lineX_stroke_color;
    
    private double lineY_stroke_width;
    private Paint lineY_stroke_color;

    //A TABELA VEM COM TAMANHO AUTOMATICO, CONFORME O MAIOR ELEMENTO AS CELULAS SE REAJUSTAM
    //FAZER UM METODO PRO CARA POR NA MÃO O TAMANHO DAS CELULAS?
    
    /*
    set first line objects as header objects (cabeçalho)
    set first colum objects as header objects (cabeçalho)
    setCellsSize(double width, double height)
    setTableSize(ainda nao sei direito como essa vai funcionar)
    setResponsibleTable(widthpropriety, heightpropriety)
    setResponsibleLinesAndColumns(widthpropriety, heightpropriety)
    
    junta essa tabela com a tabela menu
    
    */
    
    /**
     * Sets some atributes for the table.
     * @param min_paddingX The minimum distance that the biggest object must have
     * from its cell in the X axis (the others objects will have it too as they are smaller).
     * @param min_paddingY The minimum distance that the biggest object must have
     * from its cell in the Y axis (the others objects will have it too as they are smaller).
     * @param cells_X_distance
     * @param cells_Y_distance
     * @param show_horizontal_lines Show line divisions in the X axis.
     * @param show_vertical_lines Show line divisions in the Y axis.
     */
    public Tabela(double min_paddingX, double min_paddingY, double cells_X_distance,
            double cells_Y_distance, boolean show_horizontal_lines, boolean show_vertical_lines){
        this.min_paddingX = min_paddingX;
        this.min_paddingY = min_paddingY;
        this.cells_X_distance = cells_X_distance;
        this.cells_Y_distance = cells_Y_distance;
        this.show_horizontal_lines = show_horizontal_lines;
        this.show_vertical_lines = show_vertical_lines;
        
        ySetCellConfig(0.0, Color.BLACK, Color.TRANSPARENT);
        ySetXlines(1.0, Color.CORNFLOWERBLUE.darker().desaturate());
        ySetYlines(1.0, Color.CORNFLOWERBLUE.darker().desaturate());
    }
    
    /**
     * Muda a aparencia das linhas que separam as linhas (linhas em X).
     * @param grossura Grossura da linha.
     * @param cor Cor da linha.
     */
    public void ySetXlines(Double grossura, Paint cor){
        if(grossura != null)
            this.lineX_stroke_width = grossura;
        if(cor != null)
            this.lineX_stroke_color = cor;
    }
    
    /**
     * Muda a aparencia das linhas que separam as colunas (linhas em Y).
     * @param grossura Grossura da linha.
     * @param cor Cor da linha.
     */
    public void ySetYlines(Double grossura, Paint cor){
        if(grossura != null)
            this.lineY_stroke_width = grossura;
        if(cor != null)
            this.lineY_stroke_color = cor;
    }
    
    public void ySetCellConfig(Double stroke_width, Paint stroke_color, Paint background_color){
        if(stroke_width != null)
            this.cell_stroke_width = stroke_width;
        if(stroke_color != null)
            this.cell_stroke_color = stroke_color;
        if(background_color != null)
            this.cell_background_color = background_color;
    }
    
    /**
     * Monta a tabela, calculando tamanho, posição e número de linhas e as posições dos objetos.
     */
    public void yRefresh(){
        this.getChildren().clear();
        
        //calcula tudo q precisa
        Point2D farestsObjects = yFarestsPositionsInTable();
        n_colunas = (int) (n_colunas > 0 ? n_colunas : farestsObjects.getX()+1);
        n_linhas = (int) (n_linhas > 0 ? n_linhas : farestsObjects.getY()+1);
        
        Point2D sizeOfBiggestElements = yBiggestSizeInTable();
        cells_width = (cells_width >= 0 ? cells_width : sizeOfBiggestElements.getX()) + min_paddingX * 2;
        cells_height = (cells_height >= 0 ? cells_height : sizeOfBiggestElements.getY()) + min_paddingY * 2;
        
        double larguraLinhas = n_colunas * (cells_width + cells_X_distance) - cells_X_distance;
        double alturaLinhas = n_linhas * (cells_height + cells_Y_distance) - cells_Y_distance;
        
        //posiciona os elementos
        for (int i = 0; i < n_linhas; i++) {
            for (int j = 0; j < n_colunas; j++) {
                int posicaoX = j;
                int posicaoY = i;
                if(posicaoX > n_colunas || posicaoY > n_linhas || posicaoX < 0 || posicaoY < 0)
                    continue;
                
                YBox caixa= new YBox(cells_width, cells_height, cell_background_color, cell_stroke_width, cell_stroke_color);
                caixa.ySetStroke(null, null, StrokeType.INSIDE, true);
                
                int index = posicoes.indexOf(new Point2D(posicaoX, posicaoY));
                if(index != -1){
                    caixa.yAddContent(elementos.get(index));
                }
                
                double translateInX = posicaoX * (cells_width + cells_X_distance);
                double translateInY = posicaoY * (cells_height + cells_Y_distance);
                caixa.ySetTranslateX(translateInX, 0);
                caixa.ySetTranslateY(translateInY, 0);
                caixa.yAlignContents(0.5, 0.5, 0.5, 0.5);
                this.getChildren().add(caixa);
            }
        }
        
        //faz as linhas em X
        if(show_horizontal_lines){
            for (int i = 1; i < n_linhas; i++) {
                Linha linhaX = new Linha(larguraLinhas, 0, lineX_stroke_width, lineX_stroke_color);
                linhaX.ySetTranslateY((cells_height + cells_X_distance) * i - cells_X_distance/2, 0.5);
                this.getChildren().add(linhaX);
            }
        }
        
        //faz as linhas em Y
        if(show_vertical_lines){
            for (int i = 1; i < n_colunas; i++) {
                Linha linhaY = new Linha(0, alturaLinhas, lineY_stroke_width, lineY_stroke_color);
                linhaY.ySetTranslateX((cells_width + cells_Y_distance) * i - cells_Y_distance/2, 0.5);
                this.getChildren().add(linhaY);
            }
        }
    }
    
    /**
     * Encontra a maior largura e altura entre todas de todos os elementos.
     * @return Uma tupla Point2D cujo X é a maior largura e Y a maior altura.
     */
    private Point2D yBiggestSizeInTable(){
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
    
    private Point2D yFarestsPositionsInTable(){
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
    public void yAdd(Node object, int positionInTableX, int positionInTableY){
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
    }
    
    public void yRemove(Node object){
        int index = elementos.indexOf(object);
        elementos.remove(index);
        posicoes.remove(index);
    }
}