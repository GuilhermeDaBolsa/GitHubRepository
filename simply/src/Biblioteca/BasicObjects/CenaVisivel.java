package Biblioteca.BasicObjects;

import Biblioteca.Interactions.YEventsHandler;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.Pane;

public class CenaVisivel extends Pane implements yAbstractInterface{
    private YEventsHandler yEvents_Handler = new YEventsHandler(this);
    
    @Override
    public YEventsHandler yGetEventsHandler(){
        return yEvents_Handler;
    }
    
    @Override
    public double yGetWidth(){
        return VisibleObjectHandler.getWidth(this);
    }
    
    @Override
    public double yGetHeight(){
        return VisibleObjectHandler.getHeight(this);
    }
    
    @Override
    public double yGetTranslateX(double pivo){
        return VisibleObjectHandler.yGetTranslateX(this, pivo);
    }
    
    @Override
    public double yGetTranslateY(double pivo){
        return VisibleObjectHandler.yGetTranslateY(this, pivo);
    }
    
    @Override
    public void ySetTranslateX(double X, double pivo){
        VisibleObjectHandler.setTranslateX(this, X, pivo);
    }
    
    @Override
    public void ySetTranslateY(double Y, double pivo){
        VisibleObjectHandler.setTranslateY(this, Y, pivo);
    }
    
    public void bindPosicaoX(DoubleBinding posicao, double eixo){
        VisibleObjectHandler.bindPosicaoX(this, posicao, eixo);
    }
    
    public void bindPosicaoY(DoubleBinding posicao, double eixo){
        VisibleObjectHandler.bindPosicaoY(this, posicao, eixo);
    }
    
    /**
     * Faz com que o objeto desapareça e não interaja com nada. Porém ele 
     * continua com suas propriedades, apenas se tornará invisivel e inalvejavel.
     */
    public void desativar(){
        VisibleObjectHandler.desativar(this);
    }
    
    /**
     * Faz com que o objeto apareça e interaja com a cena. Tornando-o visivel e alvejavel.
     */
    public void ativar(){
        VisibleObjectHandler.ativar(this);
    }
    
    /**
     * Caso o objeto esteja desativado, será ativado e vice-versa.
     */
    public void switchAtivarDesativar(){
        VisibleObjectHandler.switchAtivarDesativar(this);
    }
}
