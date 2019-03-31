package Biblioteca.InteractiveObjects;

import Biblioteca.BasicObjects.ObjetoVisivel;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ObjetoInteragivel extends ObjetoVisivel{//VER SE A HITBOX NAO FICA QUADRADONA PCAUSA DO PANE E VER SE DA PRA RESOLVER........
    public Runnables actionRelease = new Runnables();
    public Runnables actionPressed = new Runnables(); //TROCAR PROS HANDLEBLES??
    public Runnables focus = new Runnables();
    public Runnables outFocus = new Runnables();
    public Runnables actionCleaner = new Runnables();
    public boolean is_focused = false;
    public boolean is_selected = false;
    
    
    public void setUpInteractiveObject(){
        objetoVisivel.setOnMouseEntered((event) -> {
            focus.run();
        });
        objetoVisivel.setOnMouseExited((event) -> {
            outFocus.run();
        });
        objetoVisivel.setOnMousePressed((event) -> {
            actionPressed.run();
        });
        objetoVisivel.setOnMouseReleased((event) -> {
            actionRelease.run();
        });
    }
    
    public void setOnKeyEMouseAction(EventHandler<? super MouseEvent> value) {
        //this.ActionPressed.addRunnable(value); //FAZER A CLASSE EVENTHANDLES PRA ESSES CASO AQUI QUE USA EVENTOS DE MOUSE E TECLADO
        objetoVisivel.setOnMousePressed(value);
    }
    
    public void setOnKeyEMouseActionRelease(EventHandler<? super MouseEvent> value, Runnable acao){
        
    }
    
    public void setOnKeyEMouseEnter(EventHandler<? super MouseEvent> value, Runnable acao){
        
    }
    
    public void setOnKeyEMouseExit(EventHandler<? super MouseEvent> value, Runnable acao){
        
    }
    
    public void setKeyActionCleaner(EventHandler<? super KeyEvent> value, Runnable acao){
        
    }
}
