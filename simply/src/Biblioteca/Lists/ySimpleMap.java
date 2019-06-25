package Biblioteca.Lists;

import java.util.ArrayList;

public class ySimpleMap<Key, Object> {
    ArrayList<Key> keys = new ArrayList();
    ArrayList<Object> objects = new ArrayList();

    public ySimpleMap() {
        keys.clear();
        objects.clear();
    }
    
    public void add(Key key, Object object){
        keys.add(key);
        objects.add(object);
    }
    
    public void remove(Key key){
        int index = keys.indexOf(key);
        keys.remove(index);
        objects.remove(index);
    }
}
