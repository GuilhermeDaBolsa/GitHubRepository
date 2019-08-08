package Biblioteca.BasicObjects;

import Biblioteca.InteractiveObjects.Runnables;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ObjectEventsHandler {
    public Node objeto;
    
    private Runnables mouseReleased;
    private Runnables mousePressed;
    private Runnables mouseEntered;
    private Runnables mouseExited;
    private Runnables actionCleaner;
    public boolean is_focused;
    public boolean is_selected;

    public ObjectEventsHandler(Node objeto) {
        this.objeto = objeto;
        mouseReleased = new Runnables();
        mousePressed = new Runnables();
        mouseEntered = new Runnables();
        mouseExited = new Runnables();
        actionCleaner = new Runnables();
        is_focused = false;
        is_selected = false;
    }
    
    public void setUpInteractiveObject(){
        objeto.setOnMouseEntered((event) -> {
            mouseEntered.run(event);
        });
        objeto.setOnMouseExited((event) -> {
            mouseExited.run(event);
        });
        objeto.setOnMousePressed((event) -> {
            mousePressed.run(event);
        });
        objeto.setOnMouseReleased((event) -> {
            mouseReleased.run(event);
        });
    }
    
    public Runnables onMouseButtonPressed(){
        return mousePressed;
    }
    
    public Runnables onMouseButtonReleased(){
        return mouseReleased;
    }
    
    public Runnables onMouseEntered(){
        return mouseEntered;
    }
    
    public Runnables onMouseExited(){
        return mouseExited;
    }
    
    public Runnables actionCleaner(){
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