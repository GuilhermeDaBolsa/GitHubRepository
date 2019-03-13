package ObjetosBase.logicaEcore;

import javafx.scene.layout.Pane;

public abstract class VisibleObject extends Pane{ //TODO O OBJETO VISIVEL VAI EXTENDER PANE...ISSO NAO DEIXA MAIS PESADO???????
    

    public double getAlturaNodo(){
        return this.getBoundsInLocal().getHeight();
    }
    
    public double getLarguraNodo(){
        return this.getBoundsInLocal().getWidth();
    }
    
    /**
     * Método para mudar o tamanho total do nodo.
     * (OBS: Caso o parametro for Double.NaN, não será mudado o tamanho do nodo no respectivo eixo do parametro).
     * @param escalaX Fator de escala da largura.
     * @param escalaY Fator de escala da altura
     */
    public void mudarTamanhoNodo(double escalaX, double escalaY){
        if(escalaX != Double.NaN)
            this.setScaleX(escalaX);
        if(escalaY != Double.NaN)
            this.setScaleY(escalaY);
    }
    
    public void mudarTamanhoNodosInterior(double escalaX, double escalaY){
            for (int i = 0; i < this.getChildren().size(); i++) {
                if(escalaX != Double.NaN)
                    this.getChildren().get(i).setScaleX(escalaX);
                if(escalaY != Double.NaN)
                    this.getChildren().get(i).setScaleX(escalaX);
            }
            
    }
    
    //setEffect (VER SE JA NAO TEM PCAUSA DO PANE)
    //bind posicao/ tamanho 
}
