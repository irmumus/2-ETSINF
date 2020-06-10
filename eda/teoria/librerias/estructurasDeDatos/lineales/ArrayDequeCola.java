package librerias.estructurasDeDatos.lineales;

import librerias.estructurasDeDatos.modelos.*;

/**
 * Write a description of class ArrayDeQueCola here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ArrayDequeCola<E> extends ArrayCola<E>
            implements ColaPlus<E>{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class ArrayDeQueCola
     */
    public ArrayDequeCola()
    {
        // initialise instance variables
        x = 0;
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int sampleMethod(int y)
    {
        // put your code here
        return x + y;
    }
}
