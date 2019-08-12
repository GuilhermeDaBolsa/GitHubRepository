package Biblioteca.BasicObjects;

import Biblioteca.InteractiveObjects.Runnables;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Class created to 
 */
public class ObjectEventsHandler {
    public Node objeto;
    
    private Runnables<MouseEvent> yOnMouseEntered;
    private Runnables<MouseEvent> yOnMouseExited;
    private Runnables<MouseEvent> yOnMousePressed;
    private Runnables<MouseEvent> yOnMouseReleased;
    private Runnables<MouseEvent> yOnMouseDragged;
    private Runnables<MouseEvent> yActionCleaner;
    //KEYBORD RUNNABLE || TODO
    
    public boolean is_focused;
    public boolean is_selected;

    public ObjectEventsHandler(Node objeto) {
        this.objeto = objeto;
        yOnMouseReleased = new Runnables();
        yOnMousePressed = new Runnables();
        yOnMouseEntered = new Runnables();
        yOnMouseExited = new Runnables();
        yOnMouseDragged = new Runnables();
        yActionCleaner = new Runnables();
        //KEYBORD RUNNABLE || TODO
        
        is_focused = false;
        is_selected = false;
    }
    
    public Runnables<MouseEvent> yOnMouseEntered(){
        if(yOnMouseEntered.size() == 0){//activates this action
            objeto.setOnMouseEntered((event) -> {
                yOnMouseEntered.run(event);
            });
        }
        
        return yOnMouseEntered;
    }
    
    public Runnables<MouseEvent> yOnMouseExited(){
        if(yOnMouseExited.size() == 0){//activates this action
            objeto.setOnMouseExited((event) -> {
                yOnMouseExited.run(event);
            });
        }
        
        return yOnMouseExited;
    }
    
    public Runnables<MouseEvent> yOnMousePressed(){
        if(yOnMousePressed.size() == 0){//activates this action
            objeto.setOnMousePressed((event) -> {
                yOnMousePressed.run(event);
            });
        }
        
        return yOnMousePressed;
    }
    
    public Runnables<MouseEvent> yOnMouseReleased(){
        if(yOnMouseReleased.size() == 0){//activates this action
            objeto.setOnMouseReleased((event) -> {
                yOnMouseReleased.run(event);
            });
        }
        
        return yOnMouseReleased;
    }
    
    public Runnables<MouseEvent> yOnMouseDragged(){
        if(yOnMouseDragged.size() == 0){//activates this action
            objeto.setOnMouseDragged((event) -> {
                yOnMouseDragged.run(event);
            });
        }
        
        return yOnMouseDragged;
    }
    
    public Runnables<MouseEvent> yActionCleaner(){
        return yActionCleaner;
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