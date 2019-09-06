package Biblioteca.Interactions;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * Class created to handle with events that occured in objects.
 */
public class YEventsHandler {
    /**
     * The object where the events occur.
     */
    public Node object;
    
    private Yhandlebles<MouseEvent> onMouseEntered;
    private Yhandlebles<MouseEvent> onMouseExited;
    private Yhandlebles<MouseEvent> onMousePressed;
    private Yhandlebles<MouseEvent> onMouseReleased;
    private Yhandlebles<MouseEvent> onMouseClicked;
    private Yhandlebles<MouseEvent> onMouseDragged;
    private Yhandlebles<MouseEvent> actionCleaner;
    //KEYBORD RUNNABLE || TODO
    
    public boolean is_focused;
    public boolean is_selected;

    /**
     * Instantiates the handlebles.
     * @param objeto 
     */
    public YEventsHandler(Node objeto) {
        this.object = objeto;
        onMouseClicked = new Yhandlebles();
        onMouseReleased = new Yhandlebles();
        onMousePressed = new Yhandlebles();
        onMouseEntered = new Yhandlebles();
        onMouseExited = new Yhandlebles();
        onMouseDragged = new Yhandlebles();
        actionCleaner = new Yhandlebles();
        //KEYBORD RUNNABLE || TODO
        
        is_focused = false;
        is_selected = false;
    }
    
    /**
     * @return The handler to mouse entered events.
     */
    public Yhandlebles<MouseEvent> onMouseEntered(){
        if(onMouseEntered.size() == 0){//activates this action
            object.setOnMouseEntered((event) -> {
                onMouseEntered.run(event);
            });
        }
        
        return onMouseEntered;
    }
    
    /**
     * @return The handler to mouse exited events.
     */
    public Yhandlebles<MouseEvent> onMouseExited(){
        if(onMouseExited.size() == 0){//activates this action
            object.setOnMouseExited((event) -> {
                onMouseExited.run(event);
            });
        }
        
        return onMouseExited;
    }
    
    /**
     * @return The handler to mouse pressed events.
     */
    public Yhandlebles<MouseEvent> onMousePressed(){
        if(onMousePressed.size() == 0){//activates this action
            object.setOnMousePressed((event) -> {
                onMousePressed.run(event);
            });
        }
        
        return onMousePressed;
    }
    
    /**
     * @return The handler to mouse released events.
     */
    public Yhandlebles<MouseEvent> onMouseReleased(){
        if(onMouseReleased.size() == 0){//activates this action
            object.setOnMouseReleased((event) -> {
                onMouseReleased.run(event);
            });
        }
        
        return onMouseReleased;
    }
    
    /**
     * @return The handler to mouse clicked (pressed and released) events.
     */
    public Yhandlebles<MouseEvent> onMouseClicked(){
        if(onMouseClicked.size() == 0){//activates this action
            object.setOnMouseClicked((event) -> {
                onMouseClicked.run(event);
            });
        }
        
        return onMouseClicked;
    }
    
    /**
     * @return The handler to mouse dragged events.
     */
    public Yhandlebles<MouseEvent> onMouseDragged(){
        if(onMouseDragged.size() == 0){//activates this action
            object.setOnMouseDragged((event) -> {
                onMouseDragged.run(event);
            });
        }
        
        return onMouseDragged;
    }
    
    /**
     * @return The handler to cleaner events (meant to clean something when it is deactivated or something like it).
     */
    public Yhandlebles<MouseEvent> actionCleaner(){
        return actionCleaner;
    }
    
    /**
     * Remove an event from all kinds of events (pressed, release, clicked......) by the events name.
     * @param name 
     */
    public void removeFromAll(String name){
        onMouseEntered.removeHandleble(name);
        onMouseExited.removeHandleble(name);
        onMousePressed.removeHandleble(name);
        onMouseReleased.removeHandleble(name);
        onMouseDragged.removeHandleble(name);
        onMouseClicked.removeHandleble(name);
        actionCleaner.removeHandleble(name);
    }
    
    /**
     * Clear all events.
     */
    public void clearEvents(){
        onMouseEntered.clear();
        onMouseExited.clear();
        onMousePressed.clear();
        onMouseReleased.clear();
        onMouseDragged.clear();
    }
    
    /*
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
    }*/
}