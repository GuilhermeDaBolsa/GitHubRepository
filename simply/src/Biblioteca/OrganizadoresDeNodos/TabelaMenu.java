package Biblioteca.OrganizadoresDeNodos;

import Biblioteca.BasicObjects.CenaVisivel;
import Biblioteca.Interactions.Runnables;
import java.util.ArrayList;

public class TabelaMenu extends CenaVisivel{
    public ArrayList<YBox> elementos = new ArrayList();
    private double positionPaternX;
    private double positionPaternY;
    private double spaceBetweenElementsX;
    private double spaceBetweenElementsY;
    
    private double countDownX;
    private double countDownY;
    private int countDownLimit;
    public Runnables acaoLimite;
    
    private boolean patternXisBoxBounds;
    private boolean patternYisBoxBounds;
    
    //espacoEntreElementosX É o espaço entre os elementos (em X), para eles não ficarem grudados um no outro.
    //espacoEntreElementosY É o espaço entre os elementos (em Y), para eles não ficarem grudados um no outro.
    
    //E MUDAR O POSITION PATTERN PRA TIPO PODE SER 1px e SE O ESPAÇO ENTRE ELEMENTOS FOR MAIOR VAI O ESPAÇO ENTRE ELEMENTOS, EU ACHO
    
    /**
     * Faz na mão, independente de outros objetos.
     * @param positionPaternX É a medida padrão de distância entre os elementos (eixo X).
     * @param positionPaternY É a medida padrão de distância entre os elementos (eixo Y).
     * @param contDownLimit When contDownLimit reaches 0, acaoLimite will be executed.
     */
    public TabelaMenu(double positionPaternX, double positionPaternY, double espacoElementosX, double espacoElementosY,//TIRAR OS POSITION PATERN E TENTAR PEGA ELES NA MÃO?
            int contDownLimit, double spaceInCountDownX, double spaceInCountDownY, boolean patternXasBounds, boolean patternYasBounds) {
        ySetNewCountDown(contDownLimit, spaceInCountDownX, spaceInCountDownY);
        ySetPositionPatern(positionPaternX, positionPaternY);
        ySetSpaceBetweenElements(espacoElementosX, espacoElementosY);
        ySetPatternAsBoxBounds(patternXasBounds, patternYasBounds);
    }

    /**
     * Adiciona um novo elemento em uma nova posição desejada e, apartir daí, será seguido o padrão de posicionamento.
     * @param elemento É o novo elemento.
     */
    public void yAddElement(YBox elemento){
        elementos.add(elemento);
        yRefresh();
    }
    
    /**
     * Adiciona vários elementos seguindo os padrões de posicionamento.
     * @param elemento São os novos elemento.
     */
    public void yAddElements(YBox... elemento){
        for (YBox elemento1 : elemento) {
            yAddElement(elemento1);
        }
    }
    
    public void yRemove(int index){
        elementos.remove(index);//VER SE QUANDO REMOVE AQUI ELE REMOVE DO PANE, ACHO Q NAO :)
        yRefresh();
    }
    
    public void yRemove(YBox element){
        int index = elementos.indexOf(element);
        if(index != -1)
            yRemove(index);
    }
    
    public void yRefresh(){
        getChildren().clear();
        
        if(!elementos.isEmpty()){
            int countDown = countDownLimit;
            double X = 0;
            double Y = 0;
            
            elementos.get(0).ySetTranslateX(0, 0);
            elementos.get(0).ySetTranslateY(0, 0);
            countDown--;
            
            for (int i = 1; i < elementos.size(); i++) {
                YBox element = elementos.get(i);
                
                if(patternXisBoxBounds)
                    X = elementos.get(i-1).yGetTranslateX(1) + spaceBetweenElementsX;
                else
                    X += positionPaternX + spaceBetweenElementsX;
                
                if(patternYisBoxBounds)
                    Y = elementos.get(i-1).yGetTranslateY(1) + spaceBetweenElementsY;
                else
                    Y += positionPaternY + spaceBetweenElementsY;

                if(countDown > -1 && countDown == 0){
                    X = elementos.get(i-1).yGetTranslateX(1) + countDownX;//THIS NEEDS THOGHTS
                    Y = elementos.get(i-1).yGetTranslateY(1) + countDownY;
                    countDown = countDownLimit;
                }

                element.ySetTranslateX(X, 0);
                element.ySetTranslateY(Y, 0);
                countDown--;
            }
        }
        
        getChildren().addAll(elementos);
    }
    
    /**
     * Muda a medida padrão de distância entre os elementos.
     * (OBS: se o valor de algum dos espaços for null, o valor não será alterado)
     * @param positionPaternX É a medida padrão de distância entre os elementos (eixo X).
     * @param positionPaternY É a medida padrão de distância entre os elementos (eixo Y).
     */
    public void ySetPositionPatern(Double positionPaternX, Double positionPaternY){
        if(positionPaternX != null)
            this.positionPaternX = positionPaternX;
        if(positionPaternY != null)
            this.positionPaternY = positionPaternY;
    }
    
    /**
     * Muda o espaço entre os elementos.
     * (OBS: se o valor de algum dos espaços for null, o valor não será alterado)
     * @param spaceBetweenElementsX É o espaço entre os elementos (em X), para eles não ficarem grudados um no outro.
     * @param spaceBetweenElementsY É o espaço entre os elementos (em Y), para eles não ficarem grudados um no outro.
     */
    public void ySetSpaceBetweenElements(Double spaceBetweenElementsX, Double spaceBetweenElementsY){
        if(spaceBetweenElementsX != null)
            this.spaceBetweenElementsX = spaceBetweenElementsX;
        if(spaceBetweenElementsY != null)
            this.spaceBetweenElementsY = spaceBetweenElementsY;
    }
    
    public void ySetNewCountDown(Integer limit, Double distanceX, Double distanceY){
        if(limit != null)
            countDownLimit = limit;
        if(distanceX != null)
            countDownX = distanceX;
        if(distanceY != null)
            countDownY = distanceY;
    }
    
    public void ySetPatternAsBoxBounds(Boolean X, Boolean Y){
        if(X != null)
            patternXisBoxBounds = X;
        if(Y != null)
            patternYisBoxBounds = Y;
    }
    
    /*public void bindEspacoEntreElementos(/*BINDE DO CARAIO){
        
    }*/
}