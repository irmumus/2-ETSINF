package ejemplos.tema1;


/**
 * Write a description of interface Serie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public interface Serie<E extends Comparable<E>>{
    void insertar(E e);
    
    E minimo();
    
    boolean eliminar(E e);
    
    E siguiente(E e);
}
