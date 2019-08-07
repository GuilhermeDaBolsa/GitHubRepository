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
    public void addHandleble(String name, EventHandler action){//esse action se perde... da nullpointer, nao sei como o java faz, mas da pra guardar la no objectsevent handler o evento em si, dai os actions daqui referenciam a ele lá.
        handlebles.add(name, action);
    }
    
    public void addHandleble(EventHandler action){
        addHandleble(""+handlebles.size(), action);
    }
    
    public void removeHandleble(String name){
        handlebles.remove(name);
    }
    
    public void removeHandlebles(String sub_name){
        String[] keys = handlebles.keys();
        
        for (int i = 0; i < keys.length; i++) {
            if(sub_name.contains(keys[i]))
                handlebles.remove(i);
        }
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