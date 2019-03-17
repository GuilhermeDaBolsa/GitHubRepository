package Biblioteca.InteractiveObjects;

import java.util.ArrayList;

//JUNTAR ESSE E O HANDABLES EM UMA CLASSE SÓ QUE RECEBE O TIPO DEPOIS

public class Runnables {
    private ArrayList<Runnable> runnables = new ArrayList();

    public Runnables() {
    }
    
    public Runnables(Runnable action) {
        addRunnable(action);
    }
    
    /**
     * Empurra os outros elementos pra frente
     * @param action 
     */
    public void addRunnable(int index, Runnable action){
        runnables.add(index, action);
    }
    
    public void addRunnable(Runnable action){
        runnables.add(action);
    }
    
    public void removeRunnable(int index){
        runnables.remove(index);
    }
    
    //duvido que funcione
    public void removeRunnable(Runnable action){
        runnables.remove(action);
    }
    
    public void clear(){
        runnables.clear();
    }
    
    public void run(){
        for (int i = 0; i < runnables.size(); i++) {
            runnables.get(i).run();
        }
    }
    
    public void runEspecifc(int index){
        runnables.get(index).run();
    }
    
    //duvido que funcione
    public void runEspecifc(Runnable action){
        runnables.get(runnables.indexOf(action)).run();
    }
}
