package Biblioteca.BasicObjects.Formas;

public class yCircularArray<T> {
    public T[] array;
    
    public yCircularArray(T[] array) {
        this.array = array;
    }
    
    public T get(int index){
        if(index < 0){
            index = array.length + (index % array.length);
        }else{
            index = index % array.length;
        }
        return array[index];
    }
}
