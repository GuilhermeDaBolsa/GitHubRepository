package Biblioteca.Lists;

import java.util.ArrayList;

public class ySimpleMap<Key, Content> {
    private ArrayList<Key> keys = new ArrayList();
    private ArrayList<Content> objects = new ArrayList();

    public ySimpleMap() {
        keys.clear();
        objects.clear();
    }
    
    public Key[] keys(){
        return (Key[]) keys.toArray();
    }
    
    public Content[] objects(){
        return (Content[]) objects.toArray();
    }
    
    public void add(Key key, Content object){
        keys.add(key);
        objects.add(object);
    }
    
    public void remove(Key key){
        remove(keys.indexOf(key));
    }
    
    public void remove(int index){
        if(index > keys.size()-1)
            index = keys.size()-1;
        else if(index < 0)
            index = 0;
        
        keys.remove(index);
        objects.remove(index);
    }
    
    public int size(){
        return keys.size();
    }
    
    public void clear(){
        keys.clear();
        objects.clear();
    }
    
    public Content get(Key key){
        return get(keys.indexOf(key));
    }
    
    public Content get(int index){
        if(index > keys.size()-1)
            index = keys.size()-1;
        else if(index < 0)
            index = 0;
        
        return objects.get(index);
    }
}