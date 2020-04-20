package librerias.estructurasDeDatos.lineales;
import librerias.estructurasDeDatos.modelos.*;


/**
 * Write a description of class LEGListaConPIOrdenada here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LEGListaConPIOrdenada<E extends Comparable> 
        extends LEGListaConPI<E> implements ListaConPI<E>{
    
    public void insertar(E e) { 
        //metodos heredados de LEGListaConPI
        inicio();
        while(!esFin() && recuperar().compareTo(e)<0){
            siguiente();
        }
        super.insertar(e);
    }
}
