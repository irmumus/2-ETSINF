package librerias.estructurasDeDatos.lineales;


/**
 * Write a description of class LEGColaOrdenada here.
 * 
 * @author irmumus
 * @version 2020
 */
public class LEGColaOrdenada<E extends Comparable<E>> extends LEGCola<E>{
    public void encolar(E element) {
        if (esVacia()){ 
            first = last = new NodoLEG<>(element);
        }else {
            for (NodoLEG<E> n = first; n != null; n = n.siguiente){
                if(element.compareTo(n.dato)>0){
                    n.siguiente=n;
                    n.dato=element;
                }
            }
        }
    }
}
