package Biblioteca.BasicObjects;

import Biblioteca.InteractiveObjects.Runnables;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ObjectEventsHandler {
    public Node objeto;
    
    public Runnables<MouseEvent> yOnMouseReleased;
    public Runnables<MouseEvent> yOnMousePressed;
    public Runnables<MouseEvent> yOnMouseEntered;
    public Runnables<MouseEvent> yOnMouseExited;
    public Runnables<MouseEvent> yActionCleaner;
    public boolean is_focused;
    public boolean is_selected;

    public ObjectEventsHandler(Node objeto) {
        this.objeto = objeto;
        yOnMouseReleased = new Runnables();
        yOnMousePressed = new Runnables();
        yOnMouseEntered = new Runnables();
        yOnMouseExited = new Runnables();
        yActionCleaner = new Runnables();
        //KEYBORD RUNNABLE || TODO
        
        is_focused = false;
        is_selected = false;
    }
    
    public void setUpInteractiveObject(){
        objeto.setOnMouseEntered((event) -> {
            yOnMouseEntered.run(event);
        });
        objeto.setOnMouseExited((event) -> {
            yOnMouseExited.run(event);
        });
        objeto.setOnMousePressed((event) -> {
            yOnMousePressed.run(event);
        });
        objeto.setOnMouseReleased((event) -> {
            yOnMouseReleased.run(event);
        });
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