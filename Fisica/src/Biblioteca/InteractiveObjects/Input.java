package Biblioteca.InteractiveObjects;

import java.util.ArrayList;
import java.util.Scanner;


public class Input {
    //BOTH ARRAYS ARE SINCKED IN THEIR INDEXES
    private ArrayList<String> keys = new ArrayList();
    private ArrayList<ArrayList<Runnable>> actions = new ArrayList();
    
    private Thread tredi;

    public Input() {
        tredi = new Thread(() -> {
            Scanner inputReader = new Scanner(System.in);
            while(true) {
                if (inputReader.hasNext()){
                    String input = inputReader.next();
                    triggerActions(input);
                }
            /*if (input.equalsIgnoreCase("Q")){
                break; // stop KeyPressThread
            }*/
            }
        });
    }
    
    public void addInput(String key, Runnable action){
        int index = keyAlreadyExists(key);
        
        if(index > -1){
            //Adds a action
            actions.get(index).add(action);
        }else{
            //Adds a key
            keys.add(key);
            //just initialize
            actions.add(new ArrayList());
            //now to get the index and put the action... call it again
            addInput(key, action);
        }
    }
    
    private void triggerActions(String key){
        int index = keyAlreadyExists(key);
        
        if(index > -1){
            for (int i = 0; i < actions.get(index).size(); i++) {
                actions.get(index).get(i).run();
            }
        }
    }
    
    public int keyAlreadyExists(String key){
        return keys.indexOf(key);
    }
    
    public void startInput(){
        tredi.start();
    }
    
}
