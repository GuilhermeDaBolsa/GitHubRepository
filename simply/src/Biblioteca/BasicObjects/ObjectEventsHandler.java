package Biblioteca.BasicObjects;

import Biblioteca.InteractiveObjects.Handlebles;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ObjectEventsHandler {
    public Node objeto;
    
    private Handlebles actionRelease = new Handlebles();
    private Handlebles actionPressed = new Handlebles();
    private Handlebles focus = new Handlebles();
    private Handlebles outFocus = new Handlebles();
    private Handlebles actionCleaner = new Handlebles();
    public boolean is_focused = false;
    public boolean is_selected = false;

    public ObjectEventsHandler(Node objeto) {
        this.objeto = objeto;
    }
    
    public void setUpInteractiveObject(){
        objeto.setOnMouseEntered((event) -> {
            focus.run();
        });
        objeto.setOnMouseExited((event) -> {
            outFocus.run();
        });
        objeto.setOnMousePressed((event) -> {
            actionPressed.run();
        });
        objeto.setOnMouseReleased((event) -> {
            actionRelease.run();
        });
    }
    
    public Handlebles onMouseButtonPressed(){
        return actionPressed;
    }
    
    public Handlebles onMouseButtonReleased(){
        return actionRelease;
    }
    
    public Handlebles onMouseEntered(){
        return focus;
    }
    
    public Handlebles onMouseExited(){
        return outFocus;
    }
    
    public Handlebles actionCleaner(){
        return actionCleaner;
    }
    
    public void setOnKeyEMouseAction(EventHandler<? super MouseEvent> value) {
        //this.ActionPressed.addRunnable(value); //FAZER A CLASSE EVENTHANDLES PRA ESSES CASO AQUI QUE USA EVENTOS DE MOUSE E TECLADO
        objeto.setOnMousePressed(value);
    }
    
    public void setOnKeyEMouseActionRelease(EventHandler<? super MouseEvent> value, Runnable acao){
        
    }
    
    public void setOnKeyEMouseEnter(EventHandler<? super MouseEvent> value, Runnable acao){
        
    }
    
    public void setOnKeyEMouseExit(EventHandler<? super MouseEvent> value, Runnable acao){
        
    }
    
    public void setKeyActionCleaner(EventHandler<? super KeyEvent> value, Runnable acao){
        
    }
    
    public void moveOnClickAndDrag(){
        //FAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAZZZZZZZZZZZZZZZZZZZZZZZZzz
    }
}
