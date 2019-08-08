package Biblioteca.InteractiveObjects;

import Biblioteca.Lists.ySimpleMap;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * Class created to store events by names.
 * @param <EventType> The type of the event handled by this runnable.
 */
public class Runnables<EventType extends Event> {
    /**
     * A map that holds the actions (objects) and its names (keys).
     */
    private ySimpleMap<String, EventHandler> actions;

    /**
     * Creates a clean map of actions, Runnable.
     */
    public Runnables() {
        actions = new ySimpleMap();
    }
    
    /**
     * Adds an action that handles a event (or not) with a name.
     * @param name The name of the action.
     * @param action The action to be stored.
     */
    public void addHandleble(String name, EventHandler<? super EventType> action){
        actions.add(name, action);
    }
    
    /**
     * Adds an action with a generic name (the name will be the position where it was placed in the map).
     * @param action The action to be stored.
     */
    public void addHandleble(EventHandler<? super EventType> action){
        addHandleble(""+actions.size(), action);
    }
    
    /**
     * Remove an action by its name.
     * @param name The name of the action to be removed.
     */
    public void removeHandleble(String name){
        actions.remove(name);
    }
    
    /**
     * Remove ALL actions whoses names contains a sub name.
     * @param sub_name Part of the name of the action to be removed.
     */
    public void removeHandlebles(String sub_name){
        String[] keys = actions.keys();
        
        for (int i = 0; i < keys.length; i++) {
            if(sub_name.contains(keys[i]))
                actions.remove(i);
        }
    }
    
    /**
     * Clear all actions and its names.
     */
    public void clear(){
        actions.clear();
    }
    
    /**
     * Run all actions stored with an event (can be null if there is no action that uses the event).
     * @param event Event that fired the run.
     */
    public void run(EventType event){
        for (int i = 0; i < actions.size(); i++) {
            actions.get(i).handle(event);
        }
    }
    
    /**
     * Runs an expecific action by its name with an event (can be null if the action dont uses the event).
     * @param name
     * @param event 
     */
    public void runEspecifc(String name, EventType event){
        actions.get(name).handle(event);
    }
}