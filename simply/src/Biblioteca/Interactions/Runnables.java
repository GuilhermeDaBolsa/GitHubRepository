package Biblioteca.Interactions;

import Biblioteca.Lists.ySimpleMap;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * Class created to store runnables and it's event by name.
 * @param <EventType> The type of the event handled by this runnable.
 */
public class Runnables<EventType extends Event> {
    /**
     * A map that holds the actions (objects) and its names (keys).
     */
    private ySimpleMap<String, EventHandler> actions;
    
    private int current_event_execution;

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
    
    public void addHandleble(int priority, String name, EventHandler<? super EventType> action){
        actions.add(priority, name, action);
    }
    
    public void addHandlebles(String name, Runnables handlebles){
        for (int i = 0; i < handlebles.size(); i++) {
            actions.add(name+i, (EventHandler) handlebles.actions.get(i));
        }
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
        for (int i = 0; i < actions.size(); i++) {
            if(actions.getKey(i).contains(sub_name))
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
     * @return How many actions are stored.
     */
    public int size(){
        return actions.size();
    }
    
    /**
     * Run all actions stored with an event (can be null if there is no action that uses the event).
     * @param event Event that fired the run.
     */
    public void run(EventType event){
        current_event_execution = 0;
        for (; current_event_execution < actions.size(); current_event_execution++) {
            actions.get(current_event_execution).handle(event);
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
    
    public void stopEventPropagation(){
        current_event_execution = actions.size();
    }
    
    public String[] getActionsName(){
        String s[] = new String[actions.size()];
        for (int i = 0; i < s.length; i++) {
            s[i] = actions.getKey(i);
        }
        return s;
    }
}