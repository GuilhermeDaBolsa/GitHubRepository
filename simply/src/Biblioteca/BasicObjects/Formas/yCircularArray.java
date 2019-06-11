package Biblioteca.BasicObjects.Formas;

public class yCircularArray<T> {
    public T[] array;
    
    public yCircularArray(T[] array) {
        this.array = array;
    }
    
    public T get(int index){
        return array[get_real_index(index)];
    }
    
    public void set(int index, T object){
        array[get_real_index(index)] = object;
    }
    
    public int get_real_index(int index){
        if(index < 0){
            return array.length + (index % array.length);
        }
        return index % array.length;
    }
}
