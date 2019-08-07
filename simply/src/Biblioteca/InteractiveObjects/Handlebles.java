package Biblioteca.InteractiveObjects;

import Biblioteca.Lists.ySimpleMap;
import javafx.event.EventHandler;

//JUNTAR ESSE E O RUNNABLES EM UMA CLASSE SÓ QUE RECEBE O TIPO DEPOIS

public class Handlebles {
    private ySimpleMap<String, EventHandler> handlebles = new ySimpleMap();

    public Handlebles() {}
    
    /**
     * Empurra os outros elementos pra frente
     * @param action 
     */
    public void addHandleble(String name, EventHandler action){
        handlebles.add(name, action);
    }
    
    public void addHandleble(EventHandler action){
        handlebles.add(""+handlebles.size(), action);
    }
    
    public void removeHandleble(String name){
        handlebles.remove(name);
    }
    
    public void clear(){
        handlebles.clear();
    }
    
    public void run(){
        for (int i = 0; i < handlebles.size(); i++) {
            handlebles.get(i).handle(null);
        }
    }
    
    public void runEspecifc(String name){
        handlebles.get(name).handle(null);
    }
}