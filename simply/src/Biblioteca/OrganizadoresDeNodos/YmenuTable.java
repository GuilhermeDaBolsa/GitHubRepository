package Biblioteca.OrganizadoresDeNodos;

import Biblioteca.BasicObjects.YvisibleScene;
import Biblioteca.Interactions.Yhandlebles;
import java.util.ArrayList;

/**
 * Creates a table where it's elements follow a pattern positioning.
 * This table have a limit action, that is modifieble. This action is executed when the countDownLimit reaches 0.
 */
public class YmenuTable extends YvisibleScene{
    public ArrayList<YBox> elementos = new ArrayList();
    private double positionPaternX;
    private double positionPaternY;
    private double spaceBetweenElementsX;
    private double spaceBetweenElementsY;
    
    private double countDownX;
    private double countDownY;
    private int countDownLimit;
    public Yhandlebles acaoLimite;
    
    private boolean patternXisBoxBounds;
    private boolean patternYisBoxBounds;
    
    //espacoEntreElementosX É o espaço entre os elementos (em X), para eles não ficarem grudados um no outro.
    //espacoEntreElementosY É o espaço entre os elementos (em Y), para eles não ficarem grudados um no outro.
    
    //E MUDAR O POSITION PATTERN PRA TIPO PODE SER 1px e SE O ESPAÇO ENTRE ELEMENTOS FOR MAIOR VAI O ESPAÇO ENTRE ELEMENTOS, EU ACHO
    
    /**
     * Creates a table with it configuration.
     * @see #ySetPositionPatern(java.lang.Double, java.lang.Double) 
     * @see #ySetSpaceBetweenElements(java.lang.Double, java.lang.Double) 
     * @see #ySetPatternAsBoxBounds(java.lang.Boolean, java.lang.Boolean) 
     * @see #ySetNewCountDown(java.lang.Integer, java.lang.Double, java.lang.Double) 
     */
    public YmenuTable(double positionPaternX, double positionPaternY, double espacoElementosX, double espacoElementosY,//TIRAR OS POSITION PATERN E TENTAR PEGA ELES NA MÃO?
            int contDownLimit, double spaceInCountDownX, double spaceInCountDownY, boolean patternXasBounds, boolean patternYasBounds) {
        ySetNewCountDown(contDownLimit, spaceInCountDownX, spaceInCountDownY);
        ySetPositionPatern(positionPaternX, positionPaternY);
        ySetSpaceBetweenElements(espacoElementosX, espacoElementosY);
        ySetPatternAsBoxBounds(patternXasBounds, patternYasBounds);
    }

    /**
     * Adds a new element to the table.
     * @param elemento The new element to be added.
     */
    public void yAddElement(YBox elemento){
        elementos.add(elemento);
        yRefresh();
    }
    
    /**
     * Adds several elements to the table.
     * @param elements The elements to be added.
     */
    public void yAddElements(YBox... elements){
        for (YBox elemento1 : elements) {
            yAddElement(elemento1);
        }
    }
    
    /**
     * Remove an element from the table by it's index.
     * @param index Index of the element
     */
    public void yRemove(int index){
        elementos.remove(index);//VER SE QUANDO REMOVE AQUI ELE REMOVE DO PANE, ACHO Q NAO :)
        yRefresh();
    }
    
    /**
     * Remove an element from the table.
     * @param element The element to be removed.
     */
    public void yRemove(YBox element){
        int index = elementos.indexOf(element);
        if(index != -1)
            yRemove(index);
    }
    
    /**
     * Refreshes the table
     */
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
     * Set position pattern between elements.
     * @param positionPaternX Position pattern in X.
     * @param positionPaternY Position pattern in Y.
     */
    public void ySetPositionPatern(Double positionPaternX, Double positionPaternY){
        if(positionPaternX != null)
            this.positionPaternX = positionPaternX;
        if(positionPaternY != null)
            this.positionPaternY = positionPaternY;
    }
    
    /**
     * Set space between elemtens.
     * @param spaceBetweenElementsX Space in X.
     * @param spaceBetweenElementsY Space in Y.
     */
    public void ySetSpaceBetweenElements(Double spaceBetweenElementsX, Double spaceBetweenElementsY){
        if(spaceBetweenElementsX != null)
            this.spaceBetweenElementsX = spaceBetweenElementsX;
        if(spaceBetweenElementsY != null)
            this.spaceBetweenElementsY = spaceBetweenElementsY;
    }
    
    /**
     * Count down is the number of elements that can still be added without alerting the limit and activating
     * the actionsLimit action.
     * @param limit New count down.
     * @param distanceX If you want to move the limit cell to an expecific place in X inform it here.
     * @param distanceY If you want to move the limit cell to an expecific place in Y inform it here. 
     */
    public void ySetNewCountDown(Integer limit, Double distanceX, Double distanceY){
        if(limit != null)
            countDownLimit = limit;
        if(distanceX != null)
            countDownX = distanceX;
        if(distanceY != null)
            countDownY = distanceY;
    }
    
    /**
     * If you want the position patter to be the box size inform true in the paramaters.
     * @param X Box size pattern in X (box's width)
     * @param Y Box size pattern in Y (box's height)
     */
    public void ySetPatternAsBoxBounds(Boolean X, Boolean Y){
        if(X != null)
            patternXisBoxBounds = X;
        if(Y != null)
            patternYisBoxBounds = Y;
    }
    
    /*public void bindEspacoEntreElementos(/*BINDE DO CARAIO){
        
    }*/
}