package ObjetosBase.logicaEcore.actionsEinteractive;

import java.util.ArrayList;
import javafx.event.EventHandler;


//JUNTAR ESSE E O RUNNABLES EM UMA CLASSE SÓ QUE RECEBE O TIPO DEPOIS


public class Handlebles {
    private ArrayList<EventHandler> handlebles = new ArrayList();

    public Handlebles() {
    }
    
    public Handlebles(EventHandler action) {
        addHandleble(action);
    }
    
    public void addHandleble(EventHandler action){
        handlebles.add(action);
    }
    
    public void removeHandleble(int index){
        handlebles.remove(index);
    }
    
    //duvido que funcione
    public void removeHandleble(EventHandler action){
        handlebles.remove(action);
    }
    
    public void clear(){
        handlebles.clear();
    }
    
    public void run(){
        for (int i = 0; i < handlebles.size(); i++) {
            handlebles.get(i).handle(null);
        }
    }
    
    public void runEspecifc(int index){
        handlebles.get(index).handle(null);
    }
    
    //duvido que funcione
    public void runEspecifc(Runnable action){
        handlebles.get(handlebles.indexOf(action)).handle(null);
    }
}
