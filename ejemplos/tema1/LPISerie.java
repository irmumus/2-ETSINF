package ejemplos.tema1;

import librerias.estructurasDeDatos.lineales.*;
import librerias.estructurasDeDatos.modelos.*;
/**
 * Write a description of class LPISerie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LPISerie <E extends Comparable <E>> 
        implements Serie<E>{
    private ListaConPI<E> s;
    
    public LPISerie(){ s = new LEGListaConPI<E>();}
    
    public E minimo(){
       if(s.esVacia()){ return null;}
       else{ s.inicio();
           return s.recuperar();
        }
    }
    
    public void insertar(E e) {
        buscar(e);
        s.insertar(e);
    }
    
    private void buscar(E e){
        s.inicio();
        while(!s.esFin() && s.recuperar().compareTo(e)<0){
            s.siguiente();
        }
    }
    
    public boolean eliminar(E e){
        buscar(e);
        if(s.esFin()) return false;
        else if(s.recuperar().compareTo(e)>0){ return false;}
        else{ s.eliminar();
            return true;
        }
    }
    
    public E siguiente(E e){
        buscar(e);
        if(s.esFin()){ return null;}
        else if(s.recuperar().compareTo(e)>0){ return null;}
        else{
            s.siguiente();
            if(s.esFin()){ return null;}
            return s.recuperar();
        }
    }
}
