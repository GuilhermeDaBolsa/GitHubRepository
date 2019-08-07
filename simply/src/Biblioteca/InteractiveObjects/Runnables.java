package Biblioteca.InteractiveObjects;

import Biblioteca.Lists.ySimpleMap;

//JUNTAR ESSE E O HANDABLES EM UMA CLASSE SÓ QUE RECEBE O TIPO DEPOIS

public class Runnables {
    private ySimpleMap<String, Runnable> runnables = new ySimpleMap();

    public Runnables() {}
  
    /**
     * Empurra os outros elementos pra frente
     * @param action 
     */
    public void addRunnable(String name, Runnable action){
        runnables.add(name, action);
    }
    
    public void addRunnable(Runnable action){
        addRunnable(""+runnables.size(), action);
    }
    
    public void removeRunnable(String name){
        runnables.remove(name);
    }
  
    public void clear(){
        runnables.clear();
    }
    
    public void run(){
        for (int i = 0; i < runnables.size(); i++) {
            runnables.get(i).run();
        }
    }
    
    public void runEspecifc(String name){
        runnables.get(name).run();
    }
}