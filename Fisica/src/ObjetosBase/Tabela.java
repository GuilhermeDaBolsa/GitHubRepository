package ObjetosBase;

import ObjetosBase.logicaEcore.actionsEinteractive.InteractiveObject;
import ObjetosBase.visibleObjects.Linha;
import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Paint;

public class Tabela extends InteractiveObject{
    private ArrayList<Node> matriz = new ArrayList();
    private ArrayList<Point2D> posicoes = new ArrayList();
    private int PosElementoMaisADireita = 0;
    private int PosElementoMaisABaixo = 0;
    
    private double espacoEntreElementosX;
    private double espacoEntreElementosY;
    private boolean mostrarLinhasX;
    private boolean mostrarLinhasY;
    private Linha modeloLinhaX;
    private Linha modeloLinhaY;
    
    /*FAZER PRA TER UM Nº INICIAL DE CASAS VAZIAS NA TABELA (PRA CASO O PROGRAMADOR QUEIRA MOSTRAR ELA VAZIA) 
    FAZER UM MÉTODO ONDE É INFORMADO O Nºde casas PARA SEREM RENDeRIZADAS, ESCONDENDO ELEMENTOS MAIS A FRENTE DO nº de casas
    OU MOSTRANDO CASAS VAZIAS MAIS A FRENTE DO ULTIMO ELEMENTO
    
    */

//QUAL O TAMANHO DA BOX DE CADA ELEMENTO???????
    
    public Tabela(double espacoEntreElementosX, double espacoEntreElementosY, boolean mostrarLinhasX, boolean mostrarLinhasY){
        this.espacoEntreElementosX = espacoEntreElementosX;
        this.espacoEntreElementosY = espacoEntreElementosY;
        this.mostrarLinhasX = mostrarLinhasX;
        this.mostrarLinhasY = mostrarLinhasY;
    }
    
    public void setModeloLinhaX(double grossura, Paint cor){
        //this.modeloLinhaX = new Linha(grossura, grossura, grossura, Color.CORAL)
    }
    
    public void setModeloLinhaY(Linha modelo){
        //this.modeloLinhaY = new Linha(grossura, grossura, grossura, Color.CORAL)
    }
    
    public void mostrarTabela(int colunas, int linhas){
        
    }
    
    

    
    //se ja existir um elemento naquele lugar ver o que fazer, por enquanto substitui
    public void addObject(Node object, int positionInTableX, int positionInTableY){
        Point2D position = new Point2D(positionInTableX, positionInTableY);
        boolean elementInSamePosition = false;
        
        for (int i = 0; i < posicoes.size(); i++) {
            if(position.getX() == posicoes.get(i).getX() && position.getY() == posicoes.get(i).getY()){
                matriz.set(i, object);
                elementInSamePosition = true;
                break;
            }
        }
        
        if(!elementInSamePosition){
            matriz.add(object);
            posicoes.add(position);
            if(positionInTableX > PosElementoMaisADireita){
                PosElementoMaisADireita = positionInTableX;
            }
            if(positionInTableY > PosElementoMaisABaixo){
                PosElementoMaisABaixo = positionInTableY;
            }
        }
    }
    
   
    
}
