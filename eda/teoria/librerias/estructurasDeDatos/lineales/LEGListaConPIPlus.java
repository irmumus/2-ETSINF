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
        NodoLEG<E> ultimo = null;
        inicio();
        while (!esFin()) {
            if (recuperar().equals(e)) ultimo = ant;
            siguiente();
        }
        if (ultimo == null) return false;
        else {
            if (ultimo.siguiente == ult) ult = ultimo;
            ultimo.siguiente = ultimo.siguiente.siguiente;
            talla--;
            return true;
        }
    }
    
    public boolean eliminarPrimero(E e) {
        inicio();
        while (!esFin()) {
            if (recuperar().equals(e)) {
                eliminar();
                return true;
            }
            siguiente();
        }
        return false;
     }
    
     public boolean eliminarTodos(E e) {
        inicio();
        boolean b = false;
        while (!esFin()) {
            if (recuperar().equals(e)) {
                eliminar();
                b = true;
            }
            else siguiente();
        }
        return b;
    }
    
    public void concatenar(ListaConPI<E> e){
        this.fin();
        e.inicio();
        while(!e.esFin()){
            this.insertar(e.recuperar());
            e.siguiente();
        }    
    }
}
