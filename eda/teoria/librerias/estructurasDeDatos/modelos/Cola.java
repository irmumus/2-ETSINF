package librerias.estructurasDeDatos.modelos;
//import librerias.estructurasDeDatos.lineales.*;

public interface Cola<E> {
    // metodos Modificadores del estado de una Cola:
    /** inserta el Elemento e en una Cola, o lo situa en su final 
     */
    void encolar(E e);
    
    /** SII !esVacia(): 
     * obtiene y elimina de una Cola el Elemento que ocupa su principio 
     */
    E desencolar();
    
    // metodos Consultores del estado de una Cola
    /** SII !esVacia(): 
     * obtiene el Elemento que ocupa el principio de una Cola,
     * el primero en orden de insercion 
     */
    E primero();
    
    /** comprueba si una Cola esta vacia 
     */
    boolean esVacia();
    public interface ColaPlus<E> extends Cola<E> { int talla(); }

}