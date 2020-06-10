package librerias.estructurasDeDatos.lineales;
import librerias.estructurasDeDatos.modelos.*;

public class LEGCola<E> implements Cola<E> {
    protected NodoLEG<E> first, last;

    public LEGCola() {
        first = last = null;
    }

    @Override
    public void encolar(E element) {
        if (esVacia()) first = last = new NodoLEG<>(element);
        else {
            last.siguiente = new NodoLEG<>(element, last);
            last = last.siguiente;
        }
    }

    @Override
    public E desencolar() {//throws Exception {
        if (esVacia()) {return null;}
        E e = first.dato;
        first = first.siguiente;
        return e;
    }

    @Override
    public E primero(){// throws Exception {
        if (esVacia()) { return null;}
        return first.dato;
    }

    @Override
    public boolean esVacia() {
        return first == null;
    }

    @Override
    public String toString() {
        String s = "[ ";
        for (NodoLEG<E> n = first; n != null; n = n.siguiente) {
            s += n.dato.toString();
            if (n.siguiente != null) s += ", ";
        }
        return s;
    }
}