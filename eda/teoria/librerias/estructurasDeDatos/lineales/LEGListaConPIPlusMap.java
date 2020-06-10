package librerias.estructurasDeDatos.lineales;

import librerias.estructurasDeDatos.deDispersion.TablaHash;
import librerias.estructurasDeDatos.modelos.ListaConPI;
import librerias.estructurasDeDatos.modelos.ListaConPIPlusMap;
import librerias.estructurasDeDatos.modelos.Map;
/**
 * Write a description of class LEGListaConPIPlusMAp here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LEGListaConPIPlusMap<E> extends LEGListaConPI<E>
    implements ListaConPIPlusMap<E>{
    /** elimina los elementos repetidos de una ListaConPI,
       * dejando únicamente su primera aparición */
	
    @Override
    public void eliminarRepetidos() {
    	Map<E,E> map = new TablaHash<E,E>(this.talla);
    	this.inicio();
    	while(!this.esFin()) {
    	    E e = this.recuperar();
    	    E f = map.recuperar(e);
    	    if(f == null) {
    	        map.insertar(e,e);
    	        this.siguiente();
    	    }
    	}
    }

    /** elimina los elementos de una ListaConPI
     *  que están en otra **/
    @Override
    public void diferencia(ListaConPI otra) {
    	Map<E, E> map = new TablaHash<E,E>(otra.talla());
    	
    	for(otra.inicio(); !otra.esFin(); otra.siguiente()) {
    	    E e = (E) otra.recuperar();
    	    map.insertar(e, e);
    	}
    	for(this.inicio();!this.esFin();this.siguiente()) {
    	    E e = this.recuperar();
    	    E f = map.recuperar(e);
    	    if(f!=null) this.eliminar();	
    	}
    	
    }
}
