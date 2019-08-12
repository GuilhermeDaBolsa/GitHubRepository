package Biblioteca.BasicObjects;

import Biblioteca.InteractiveObjects.Runnables;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Class created to 
 */
public class YobjectEventsHandler {
    public Node object;
    
    private Runnables<MouseEvent> onMouseEntered;
    private Runnables<MouseEvent> onMouseExited;
    private Runnables<MouseEvent> onMousePressed;
    private Runnables<MouseEvent> onMouseReleased;
    private Runnables<MouseEvent> onMouseDragged;
    private Runnables<MouseEvent> actionCleaner;
    //KEYBORD RUNNABLE || TODO
    
    public boolean is_focused;
    public boolean is_selected;

    public YobjectEventsHandler(Node objeto) {
        this.object = objeto;
        onMouseReleased = new Runnables();
        onMousePressed = new Runnables();
        onMouseEntered = new Runnables();
        onMouseExited = new Runnables();
        onMouseDragged = new Runnables();
        actionCleaner = new Runnables();
        //KEYBORD RUNNABLE || TODO
        
        is_focused = false;
        is_selected = false;
    }
    
    public Runnables<MouseEvent> onMouseEntered(){
        if(onMouseEntered.size() == 0){//activates this action
            object.setOnMouseEntered((event) -> {
                onMouseEntered.run(event);
            });
        }
        
        return onMouseEntered;
    }
    
    public Runnables<MouseEvent> onMouseExited(){
        if(onMouseExited.size() == 0){//activates this action
            object.setOnMouseExited((event) -> {
                onMouseExited.run(event);
            });
        }
        
        return onMouseExited;
    }
    
    public Runnables<MouseEvent> onMousePressed(){
        if(onMousePressed.size() == 0){//activates this action
            object.setOnMousePressed((event) -> {
                onMousePressed.run(event);
            });
        }
        
        return onMousePressed;
    }
    
    public Runnables<MouseEvent> onMouseReleased(){
        if(onMouseReleased.size() == 0){//activates this action
            object.setOnMouseReleased((event) -> {
                onMouseReleased.run(event);
            });
        }
        
        return onMouseReleased;
    }
    
    public Runnables<MouseEvent> onMouseDragged(){
        if(onMouseDragged.size() == 0){//activates this action
            object.setOnMouseDragged((event) -> {
                onMouseDragged.run(event);
            });
        }
        
        return onMouseDragged;
    }
    
    public Runnables<MouseEvent> actionCleaner(){
        return actionCleaner;
    }
    
    public void setOnKeyEMouseAction(EventHandler<? super MouseEvent> value) {
        //this.ActionPressed.addRunnable(value); //FAZER A CLASSE EVENTHANDLES PRA ESSES CASO AQUI QUE USA EVENTOS DE MOUSE E TECLADO
        object.setOnMousePressed(value);
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