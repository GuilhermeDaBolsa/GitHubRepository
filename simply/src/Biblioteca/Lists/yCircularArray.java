package Biblioteca.Lists;

import java.util.Arrays;

/**
 * Class created to store elements in a circular way, in other words, the array "never ends".
 * When it tries to go further than the array's limit, it goes all back to the begining of it, cyclicly.
 * @param <T> The type of the object that will be stored.
 */
public class yCircularArray<T> {
    private Object[] array;
    
    /**
     * Intantiates an array of Objects with the given size.
     * @param size The size of the array.
     */
    public yCircularArray(int size){
        this.array = new Object[size];
    }
    
    /**
     * Creates a copy of the given array to become a circular array.
     * @param array The array to be converted.
     */
    public yCircularArray(T[] array) {
        this.array = Arrays.copyOf(array, array.length, Object[].class);
    }
    
    /**
     * @param index The position of the desired object.
     * @return The object located in the index position.
     */
    public T get(int index){
        return (T) array[get_real_index(index)];
    }
    
    /**
     * Sets an object in determined position (independently if there is or not an object there)
     * @param index The position.
     * @param object The object.
     */
    public void set(int index, T object){
        array[get_real_index(index)] = object;
    }
    
    /**
     * @return The size of this array.
     */
    public int lenght(){
        return array.length;
    }
    
    /**
     * @param index The position to be transformed.
     * @return The transformed index (this is what makes the array circular, if the index go higher than the limit, it go back to zero and
     * if it go below zero it goes to the end of the array, in a circular way)
     */
    public int get_real_index(int index){
        if(index < 0){
            return get_real_index(array.length + (index % array.length));
        }
        return index % array.length;
    }
}