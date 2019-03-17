package Biblioteca.InteractiveObjects;

import Biblioteca.BasicObjects.VisibleObject;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public abstract class InteractiveObject extends VisibleObject{
    public Runnables actionRelease = new Runnables();
    public Runnables actionPressed = new Runnables(); //TROCAR PROS HANDLEBLES??
    public Runnables focus = new Runnables();
    public Runnables outFocus = new Runnables();
    public Runnables actionCleaner = new Runnables();
    public boolean is_focused = false;
    public boolean is_selected = false;
    
    
    public void setUpInteractiveObject(){
        this.setOnMouseEntered((event) -> {
            focus.run();
        });
        this.setOnMouseExited((event) -> {
            outFocus.run();
        });
        this.setOnMousePressed((event) -> {
            actionPressed.run();
        });
        this.setOnMouseReleased((event) -> {
            actionRelease.run();
        });
    }
    
    public void setOnKeyEMouseAction(EventHandler<? super MouseEvent> value) {
        //this.ActionPressed.addRunnable(value); //FAZER A CLASSE EVENTHANDLES PRA ESSES CASO AQUI QUE USA EVENTOS DE MOUSE E TECLADO
        this.setOnMousePressed(value);
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
