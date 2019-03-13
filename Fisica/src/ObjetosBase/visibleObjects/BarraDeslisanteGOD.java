package ObjetosBase.visibleObjects;

import ObjetosBase.logicaEcore.actionsEinteractive.InteractiveObject;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class BarraDeslisanteGOD extends InteractiveObject {

    private Rectangle fundo;
    private Rectangle path;
    private Caixa bolinha;
    private Texto mostra_valor;
    private Runnable acao;
    
    public double altura;
    public double largura;
    
    public double min;
    public double max;
    public double start_number;
    public double incremento; //its inactiveted! samerda atrapalha pacas
    
    private double deltaX;
    private double deltaY;
    
    private boolean is_pressed = false;

    public BarraDeslisanteGOD(double altura, double largura, double min, double max, double start_number, double incremento, Runnable action) {
        this.altura = altura;
        this.largura = largura;
        this.min = min;
        this.max = max;
        this.start_number = start_number;
        this.incremento = incremento;
        this.acao = action;
        
        fundo = new Rectangle(largura, altura,Color.GRAY);
        path = new Rectangle(largura, altura, Color.DARKCYAN);
        mostra_valor = new Texto(""+start_number, Font.font((largura>altura)? altura:largura), null);
        if (altura > largura) {
            criar_tudo_e_a_bolinha(largura, false, action);
        } else {
            criar_tudo_e_a_bolinha(altura, true, action);
        }
        
        this.getChildren().addAll(fundo, path, mostra_valor, bolinha);
    }

    private void criar_tudo_e_a_bolinha(double tamanho, boolean eixoX, Runnable action) {
        bolinha = new Caixa(tamanho, tamanho, 0, Color.BLACK, Color.TRANSPARENT);
        

        if(eixoX){
            fundo.setScaleX((largura+bolinha.getAltura_caixa())/largura);
            path.setScaleX(fundo.getScaleX());
            
            path.widthProperty().bind(bolinha.layoutXProperty());
            bolinha.setLayoutX((largura/(max-min))*(start_number - min) - bolinha.getAltura_caixa()/2);
            
            mostra_valor.setTranslateY(20);
            mostra_valor.translateXProperty().bind(bolinha.layoutXProperty());
            mostra_valor.nodoTexto.textProperty().bind(
                Bindings.format("%.1f",(bolinha.layoutXProperty().add(bolinha.getAltura_caixa()/2)).multiply((max-min)/largura).add(min))
            );
        }else{
            fundo.setScaleY((altura+bolinha.getLargura_caixa())/altura);
            path.setScaleY(fundo.getScaleY());
            
            bolinha.setLayoutY((altura/(max-min))*(start_number - min) - bolinha.getLargura_caixa()/2);
            path.heightProperty().bind(bolinha.layoutYProperty());
            
            mostra_valor.translateXProperty().bind(mostra_valor.widthProperty().multiply(-1).add(-4));
            mostra_valor.translateYProperty().bind(bolinha.layoutYProperty().add(mostra_valor.getAlturaTexto()/1.5));
            mostra_valor.nodoTexto.textProperty().bind(
                Bindings.format("%.1f",(bolinha.layoutYProperty().add(bolinha.getLargura_caixa()/2)).multiply((max-min)/altura).add(min))
            );
        }
        
        bolinha.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                deltaX = bolinha.getLayoutX() - mouseEvent.getSceneX();
                deltaY = bolinha.getLayoutY() - mouseEvent.getSceneY();
                is_pressed = true;
            }
        });
        bolinha.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double posicaoX = mouseEvent.getSceneX() + deltaX;
                double posicaoY = mouseEvent.getSceneY() + deltaY;
                
                if(eixoX){
                    if(posicaoX < limiteEsquerda()){
                        posicaoX = limiteEsquerda();
                    }else if(posicaoX > limiteDireita()){
                            posicaoX = limiteDireita();
                    }
                    bolinha.setLayoutX(posicaoX);
                }else{
                    if(posicaoY < limiteCima()){
                        posicaoY = limiteCima();
                    }else if(posicaoY > limiteBaixo()){
                            posicaoY = limiteBaixo();
                    }
                    bolinha.setLayoutY(posicaoY);
                }
                if(action != null)
                    action.run();
            }
        });
        
        bolinha.setOnMouseReleased((event) -> {
            is_pressed = false;
        });
    }
    
    public boolean is_pressed(){
        return is_pressed;
    }
    
    public double getValor(){
        return bolinha.layoutYProperty().add(bolinha.getLargura_caixa()/2).multiply((max-min)/altura).add(min).doubleValue();
    }
    
    public double getPosicao(){
        return bolinha.layoutYProperty().doubleValue();
    }
    
    public void bind_objeto(DoubleProperty objeto){
        objeto.bind((bolinha.layoutYProperty().add(bolinha.getLargura_caixa()/2)).multiply((max-min)/altura).add(min));
    }
    
    public void setAction(Runnable action){
        this.acao = action;
    }
    
    
    public void aumentar_valor(double valor){
        double posicao = getPosicao() + valor;

        if(posicao < limiteCima()){
            posicao = limiteCima();
        }else if(posicao > limiteBaixo()){
            posicao = limiteBaixo();
        }
        bolinha.setLayoutY(posicao);
    }
    
    private double limiteEsquerda(){
        return -bolinha.getAltura_caixa()/2;
    }
    
    private double limiteDireita(){
        return largura-bolinha.getAltura_caixa()/2;
    }
    
    private double limiteCima(){
        return -bolinha.getLargura_caixa()/2;
    }
    
    private double limiteBaixo(){
        return altura-bolinha.getLargura_caixa()/2;
    }

}
