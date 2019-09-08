package SimplY.Lists;

import java.util.ArrayList;

/**
 * Created to be a simple map that does just the necessary.
 * Works with index, the keys index is the same to the object related to that key.
 * @param <Key> Type of the keys.
 * @param <Content> Type of the objects.
 */
public class YSimpleMap<Key, Content> {
    private ArrayList<Key> keys;
    private ArrayList<Content> objects;

    /**
     * Instantiate an empty map.
     */
    public YSimpleMap() {
        keys = new ArrayList();
        objects = new ArrayList();
    }
    
    /**
     * Adds a new object to the map.
     * @param key Object's key (gives a "name" to the object)
     * @param object The object to be stored.
     */
    public void add(Key key, Content object){
        int exist = keys.indexOf(key);
        if(exist != -1){
            keys.set(exist, key);
            objects.set(exist, object);
        }else{
            keys.add(key);
            objects.add(object);
        }
    }
    
    /**
     * Adds a new object to the map with a index.
     * @param index The desired position in the map.
     * @param key Object's key (gives a "name" to the object)
     * @param object The object to be stored.
     */
    public void add(int index, Key key, Content object){
        int exist = keys.indexOf(key);
        if(exist != -1){
            keys.remove(key);
            objects.remove(object);
        }
        
        keys.add(index, key);
        objects.add(index, object);
    }
    
    /**
     * Remove an object (and it's key) by its key.
     * @param key The object's key.
     */
    public void remove(Key key){
        remove(keys.indexOf(key));
    }
    
    /**
     * Remove an object and it's key by index.
     * @param index The position of the key and the object.
     */
    public void remove(int index){
        if(index > keys.size()-1)
            index = keys.size()-1;
        
        if(index >= 0){
            keys.remove(index);
            objects.remove(index);
        }
    }
    
    /**
     * @return The number of elements this map contains.
     */
    public int size(){
        return keys.size();
    }
    
    /**
     * Clear the map, deleting all keys and objects.
     */
    public void clear(){
        keys.clear();
        objects.clear();
    }
    
    /**
     * @param index Location of the key in the array.
     * @return A key that is located in the index.
     */
    public Key getKey(int index){
        return keys.get(index);
    }
    
    /**
     * Gets an object by its key.
     * @param key Object's key.
     * @return The object.
     */
    public Content get(Key key){
        return get(keys.indexOf(key));
    }
    
    /**
     * Gets an object by index.
     * @param index The position of the object.
     * @return The object.
     */
    public Content get(int index){
        if(index > keys.size()-1)
            index = keys.size()-1;
        else if(index < 0)
            index = 0;
        
        return (Content) objects.get(index);
    }
}