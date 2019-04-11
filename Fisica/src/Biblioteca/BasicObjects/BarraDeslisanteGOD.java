package Biblioteca.BasicObjects;

import Biblioteca.BasicObjects.Formas.Texto;
import Biblioteca.InteractiveObjects.ObjetoInteragivel;
import Biblioteca.OrganizadoresDeNodos.Caixa;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class BarraDeslisanteGOD extends ObjetoInteragivel {
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
    
    public boolean is_pressed = false;

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
            mostra_valor.texto.textProperty().bind(
                Bindings.format("%.1f",(bolinha.layoutXProperty().add(bolinha.getAltura_caixa()/2)).multiply((max-min)/largura).add(min))
            );
        }else{
            fundo.setScaleY((altura+bolinha.getLargura_caixa())/altura);
            path.setScaleY(fundo.getScaleY());
            
            bolinha.setLayoutY((altura/(max-min))*(start_number - min) - bolinha.getLargura_caixa()/2);
            path.heightProperty().bind(bolinha.layoutYProperty());
            
            mostra_valor.translateXProperty().bind(mostra_valor.widthProperty().multiply(-1).add(-4));
            mostra_valor.translateYProperty().bind(bolinha.layoutYProperty().subtract(mostra_valor.getAltura()/2 - 2));
            mostra_valor.texto.textProperty().bind(
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
    
    public double getValor(){
        return bolinha.layoutYProperty().add(bolinha.getLargura_caixa()/2).multiply((max-min)/altura).add(min).doubleValue();
    }
    
    public double getPosicao(){
        return bolinha.layoutYProperty().doubleValue();
    }
    
    public void bind_valor(DoubleProperty objeto){
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

    public Rectangle getFundo() {
        return fundo;
    }

    public void setFundo(Rectangle fundo) {
        this.fundo = fundo;
    }

    public Rectangle getPath() {
        return path;
    }

    public void setPath(Rectangle path) {
        this.path = path;
    }

    public Caixa getBolinha() {
        return bolinha;
    }

    public void setBolinha(Caixa bolinha) {
        this.bolinha = bolinha;
    }

    public Texto getMostra_valor() {
        return mostra_valor;
    }

    public void setMostra_valor(Texto mostra_valor) {
        this.mostra_valor = mostra_valor;
    }

    public Runnable getAcao() {
        return acao;
    }

    public void setAcao(Runnable acao) {
        this.acao = acao;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getLargura() {
        return largura;
    }

    public void setLargura(double largura) {
        this.largura = largura;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getStart_number() {
        return start_number;
    }

    public void setStart_number(double start_number) {
        this.start_number = start_number;
    }

    public double getIncremento() {
        return incremento;
    }

    public void setIncremento(double incremento) {
        this.incremento = incremento;
    }

    public double getDeltaX() {
        return deltaX;
    }

    public void setDeltaX(double deltaX) {
        this.deltaX = deltaX;
    }

    public double getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(double deltaY) {
        this.deltaY = deltaY;
    }

    public boolean isIs_pressed() {
        return is_pressed;
    }

    public void setIs_pressed(boolean is_pressed) {
        this.is_pressed = is_pressed;
    }

    
}
