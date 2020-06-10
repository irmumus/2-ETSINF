package librerias.estructurasDeDatos.lineales;

import librerias.estructurasDeDatos.modelos.*;

// Un ArrayColaPlus ES UN ArrayCola que implementa ColaPlus

public class ArrayColaPlus<E> extends ArrayCola<E> 
                                implements ColaPlus<E> {
    /** obtiene la talla de una Cola **/
    public int talla() {
      /*//return super.talla;
      ArrayColaPlus<E> aux = new ArrayColaPlus<E>();
      int res = 0;
      while(!esVacia()){
          aux.encolar(this.desencolar());
          res++;
      }
      this.elArray=aux.elArray;
      return res;*/
      return super.talla();
    }
}