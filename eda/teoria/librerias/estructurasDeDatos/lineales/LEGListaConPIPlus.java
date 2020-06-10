package librerias.estructurasDeDatos.lineales;

import librerias.estructurasDeDatos.modelos.*;
import librerias.estructurasDeDatos.lineales.*;

/**
 * Write a description of class LEGListaConPIPlus here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LEGListaConPIPlus<E> extends LEGListaConPI<E>
             implements ListaConPIPlus<E>{
    public boolean contiene(E e){
        NodoLEG<E> aux = pri;
        while(aux!=null){
            if(aux.dato==e) {return true;}
            aux = aux.siguiente;
        }
        return false;
    }
    
    public boolean eliminar(E e){
        NodoLEG<E> aux = pri;
        while(aux!=null){
            if(aux.dato==e) {
                eliminar();
                return true;
            }
            aux = aux.siguiente;
        }
        return false;
    }
    
    public boolean eliminarUltimo(E e){
        NodoLEG<E> aux = pri;
        while(aux!=null){
            aux = aux.siguiente;
        }
        eliminar();
        return true;
    }
    
    public void concatenar(ListaConPI<E> e){
        e.inicio();
        while(!e.esFin()){
            this.insertar(e.recuperar());
            e.siguiente();
        }    
    }
}
