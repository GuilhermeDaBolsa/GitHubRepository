package Biblioteca.BasicObjects;

import Biblioteca.InteractiveObjects.Handlebles;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ObjectEventsHandler {
    public Node objeto;
    
    private Handlebles mouseReleased;
    private Handlebles mousePressed;
    private Handlebles mouseEntered;
    private Handlebles mouseExited;
    private Handlebles actionCleaner;
    public boolean is_focused;
    public boolean is_selected;

    public ObjectEventsHandler(Node objeto) {
        this.objeto = objeto;
        mouseReleased = new Handlebles();
        mousePressed = new Handlebles();
        mouseEntered = new Handlebles();
        mouseExited = new Handlebles();
        actionCleaner = new Handlebles();
        is_focused = false;
        is_selected = false;
    }
    
    public void setUpInteractiveObject(){
        objeto.setOnMouseEntered((event) -> {
            mouseEntered.run();
        });
        objeto.setOnMouseExited((event) -> {
            mouseExited.run();
        });
        objeto.setOnMousePressed((event) -> {
            mousePressed.run();
        });
        objeto.setOnMouseReleased((event) -> {
            mouseReleased.run();
        });
    }
    
    public Handlebles onMouseButtonPressed(){
        return mousePressed;
    }
    
    public Handlebles onMouseButtonReleased(){
        return mouseReleased;
    }
    
    public Handlebles onMouseEntered(){
        return mouseEntered;
    }
    
    public Handlebles onMouseExited(){
        return mouseExited;
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