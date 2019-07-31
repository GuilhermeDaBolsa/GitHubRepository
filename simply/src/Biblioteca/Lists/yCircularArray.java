package Biblioteca.Lists;

public class yCircularArray<T> {
    public Object[] array;
    
    public yCircularArray(int elements){
        this.array = new Object[elements];
    }
    
    public yCircularArray(T[] array) {
        this.array = array;
    }
    
    public T get(int index){
        return (T) array[get_real_index(index)];
    }
    
    public void set(int index, T object){
        array[get_real_index(index)] = object;
    }
    
    public int get_real_index(int index){
        if(index < 0){
            return array.length + (index % array.length) - 1;
        }
        return index % array.length;
    }
}
