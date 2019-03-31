package Biblioteca.BasicObjects;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class ObjetoVisivel extends Pane{
    protected Node objetoVisivel = this;
    
    public double getLargura(){
        return ObjetoVisivelManipulador.getLargura(this);
    }
    
    public double getAltura(){
        return ObjetoVisivelManipulador.getAltura(this);
    }
    
    public void setPosicaoX(double pivo, double X){
        ObjetoVisivelManipulador.setPosicaoX(this, pivo, X);
    }
    
    public void setPosicaoY(double pivo, double Y){
        ObjetoVisivelManipulador.setPosicaoY(this, pivo, Y);
    }
    
            //setEffect (VER SE JA NAO TEM PCAUSA DO PANE)
    //bind posicao/ tamanho 
}