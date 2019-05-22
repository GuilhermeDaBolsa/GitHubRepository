package Biblioteca.BasicObjects;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class CenaVisivel extends Pane{
    public ObjectEventsHandler events_handler = new ObjectEventsHandler(this);
    
    public double yGetWidth(){
        return VisibleObjectHandler.getWidth(this);
    }
    
    public double yGetHeight(){
        return VisibleObjectHandler.getHeight(this);
    }
    
    public void ySetTranslateX(double pivo, double X){
        VisibleObjectHandler.setTranslateX(this, pivo, X);
    }
    
    public void ySetTranslateY(double pivo, double Y){
        VisibleObjectHandler.setTranslateY(this, pivo, Y);
    }
    
    public DoubleProperty getPosicaoXListener(){
        return VisibleObjectHandler.getPosicaoXListener(this);
    }
    
    public DoubleProperty getPosicaoYListener(){
        return VisibleObjectHandler.getPosicaoYListener(this);
    }
    
    public void bindPosicaoX(DoubleProperty posicao, double eixo){
        VisibleObjectHandler.bindPosicaoX(this, posicao, eixo);
    }
    
    public void bindPosicaoY(Node nodo, DoubleProperty posicao, double eixo){
        VisibleObjectHandler.bindPosicaoY(this, posicao, eixo);
    }
    
    public ReadOnlyDoubleProperty getBindLargura(){
        return this.widthProperty();
    }
    
    public ReadOnlyDoubleProperty getBindAltura(){
        return this.heightProperty();
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
