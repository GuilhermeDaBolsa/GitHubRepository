package SimplY.Interactions;

import SimplY.Lists.YSimpleMap;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * Class created to store runnables and it's event by name.
 * @param <EventType> The type of the event handled by this runnable.
 */
public class YHandlebles<EventType extends Event> {
    /**
     * A map that holds the actions (objects) and its names (keys).
     */
    private YSimpleMap<String, EventHandler> actions;
    
    /**
     * Number of the event beeing executed.
     */
    private int current_event_execution;

    /**
     * Creates a clean map of actions, Runnable.
     */
    public YHandlebles() {
        actions = new YSimpleMap();
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
     * Adds an action that handles a event (or not) with a name and a priority of execution (0 super important, size - 1, nothing important).
     * @param name The name of the action.
     * @param action The action to be stored.
     */
    public void addHandleble(int priority, String name, EventHandler<? super EventType> action){
        actions.add(priority, name, action);
    }
    
    /**
     * Copies a content of a YHandlebles to this object, puting the name + its runnable number in the array of runnables.
     * @param name Name of the events.
     * @param handlebles The events.
     */
    public void addHandlebles(String name, YHandlebles handlebles){
        for (int i = 0; i < handlebles.size(); i++) {
            actions.add(name+i, (EventHandler) handlebles.actions.get(i));
        }
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
    
    /**
     * Stop the execution of the events when the current event ends executing.
     */
    public void stopEventPropagation(){
        current_event_execution = actions.size();
    }
    
    /**
     * @return All the events names.
     */
    public String[] getActionsName(){
        String s[] = new String[actions.size()];
        for (int i = 0; i < s.length; i++) {
            s[i] = actions.getKey(i);
        }
        return s;
    }
}