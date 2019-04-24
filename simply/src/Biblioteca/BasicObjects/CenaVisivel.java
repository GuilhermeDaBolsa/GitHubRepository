package Biblioteca.BasicObjects;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Pane;

public class CenaVisivel extends Pane{
    public ObjectEventsHandler events_handler = new ObjectEventsHandler(this);
    
    public double getLargura(){
        return VisibleObjectHandler.getWidth(this);
    }
    
    public double getAltura(){
        return VisibleObjectHandler.getHeigth(this);
    }
    
    public void setPosicaoX(double pivo, double X){
        VisibleObjectHandler.setTranslateX(this, pivo, X);
    }
    
    public void setPosicaoY(double pivo, double Y){
        VisibleObjectHandler.setTranslateY(this, pivo, Y);
    }
    
    public DoubleProperty getPosicaoXListener(){
        return this.translateXProperty();
    }
    
    public DoubleProperty getPosicaoYListener(){
        return this.translateYProperty();
    }
    
    public ReadOnlyDoubleProperty getBindLargura(){
        return widthProperty();
    }
    
    public ReadOnlyDoubleProperty getBindAltura(){
        return heightProperty();
    }
    
    public void bindPosicaoX(DoubleProperty posicao, double eixo){
        this.translateXProperty().bind(posicao.subtract(eixo*getLargura()));
    }
    
    public void bindPosicaoY(DoubleProperty posicao, double eixo){
        this.translateYProperty().bind(posicao.subtract(eixo*getAltura()));
    }
    
    /**
     * Faz com que o objeto desapareça e não interaja com nada. Porém ele 
     * continua com suas propriedades, apenas se tornará invisivel e inalvejavel.
     */
    public void desativar(){
        this.setVisible(false);
        this.setDisable(true);
    }
    
    /**
     * Faz com que o objeto apareça e interaja com a cena. Tornando-o visivel e alvejavel.
     */
    public void ativar(){
        this.setVisible(true);
        this.setDisable(false);
    }
    
    /**
     * Caso o objeto esteja desativado, será ativado e vice-versa.
     */
    public void switchAtivarDesativar(){
        this.setVisible(!this.isVisible());
        this.setDisable(!this.isDisable());
    }
}
