package librerias.estructurasDeDatos.deDispersion;

import librerias.estructurasDeDatos.modelos.Map;

// para implementar toClaves
import librerias.estructurasDeDatos.modelos.ListaConPI; 
import librerias.estructurasDeDatos.lineales.LEGListaConPI;

/**
 * Implementacion de una TablaHash Enlazada 
 * con Listas con PI y SIN REHASHING
 */

public class TablaHash<C, V> implements Map<C, V> {
    
    // Una Tabla Hash de Entradas de Clave de tipo C y  Valor de tipo V ...
    
    /** El valor (float) del factor de carga de una Tabla Hash 
     *  (valor por defecto en la clase java.util.HashMap) 
     */
    public static final double FACTOR_DE_CARGA = 0.75;
    
    /** TIENE UN array de Listas Con PI de Tipo Generico EntradaHashLPI<C, V>:
     *  - elArray[h] representa una cubeta, 
     *    o lista de colisiones asociadas al indice Hash h
     *  - elArray[h] contiene la referencia a la Lista Con PI 
     *    donde se encuentran todas las 
     *    Entradas cuya Clave tiene un indice Hash h 
     */
    protected ListaConPI<EntradaHash<C,V>>[] elArray;
    
    /** TIENE UNA talla que representa el numero de Entradas 
     *  almacenadas en una Tabla Hash o, si se prefiere, en sus cubetas
     */
    protected int talla; 
    
    /** Devuelve el indice Hash de la Clave c de una Entrada, i.e. 
     *  la cubeta en la que se debe encontrar la Entrada de clave c
     *  *** SIN ESTE METODO NO SE TIENE UNA TABLA HASH, SOLO UN ARRAY ***
     */
    protected int indiceHash(C c) {
        int indiceHash = c.hashCode() % elArray.length;
        if (indiceHash < 0) indiceHash += elArray.length; 
        return indiceHash;
    }    
    
    /** Crea una Tabla Hash vacia, con una capacidad (inicial) maxima  
     *  de tallaMaximaEstimada entradas y factor de carga 0.75
     */
    @SuppressWarnings("unchecked") 
    public TablaHash(int tallaMaximaEstimada) {
        int capacidad = siguientePrimo((int) (tallaMaximaEstimada / FACTOR_DE_CARGA));
        elArray = new LEGListaConPI[capacidad];
        for (int i = 0; i < elArray.length; i++) 
            elArray[i] = new LEGListaConPI<EntradaHash<C,V>>();
        talla = 0;
    }
    
     // Devuelve un numero primo MAYOR o IGUAL a n, i.e. el primo que sigue a n
     protected static final int siguientePrimo(int n) {
         if (n % 2 == 0) n++;
         for ( ; !esPrimo(n); n += 2); 
         return n;
     } 
     
     // Comprueba si n es un numero primo
     protected static final boolean esPrimo(int n) {
         for (int i = 3 ; i * i <= n; i += 2) 
            if (n % i == 0) return false; // n NO es primo
         return true; // n SI es primo
     } 
    
    private ListaConPI<EntradaHash<C, V>> localizar(C c) {
	int pos = indiceHash(c);
	ListaConPI<EntradaHash<C, V>> cubeta = elArray[pos];
	for (cubeta.inicio(); !cubeta.esFin() && 
	  !cubeta.recuperar().clave.equals(c); cubeta.siguiente());
	return cubeta;
    }
    
    /** Devuelve el valor de la entrada con clave c de una Tabla Hash,
     *  o null si no hay una entrada con clave c en la Tabla
     */
    public V recuperar(C c) {
        //int pos = indiceHash(c);
        ListaConPI<EntradaHash<C,V>> cubeta = localizar(c);
        V valor = null;
        if(!cubeta.esFin()){
            valor = cubeta.recuperar().valor;
        }
        return valor;
    }
    
    /** Elimina la entrada con clave c de una Tabla Hash y devuelve
     *  su valor asociado, o null si no hay ninguna entrada con 
     *  clave c en la Tabla
     */
    public V eliminar(C c) {
        ListaConPI<EntradaHash<C,V>> cubeta = localizar(c);
        V valor = null;
        if (!cubeta.esFin()) {
            valor = cubeta.recuperar().valor;
            cubeta.eliminar();
            talla--;
	}
        return valor;
    }
        
    /** Inserta la entrada (c, v)  a una Tabla Hash y devuelve  
     *  el antiguo valor asociado a c, o null si no hay ninguna 
     *  entrada con clave c en la Tabla
     */
    // NO HACE REHASHING. En la practica 3 se modificara este metodo
    // de forma que el rehashing se efectua cuando tras insertar una 
    // nueva entrada en la correspondiente cubeta (lista enlazada 
    // directa) de elArray, e incrementar la talla, ...
    // factorDeCarga() > FACTOR_DE_CARGA.
    // Ello equivale, básicamente, a que la talla actual 
    // supere la tallaMaximaEstimada.
    public V insertar(C c, V v) {
        ListaConPI<EntradaHash<C,V>> cubeta = localizar(c);
        V antiguoValor = null;
        if (cubeta.esFin()) { // si no esta, insercion efectiva de la Entrada (c, v)
            cubeta.insertar(new EntradaHash<C, V>(c, v));
            talla++;
        } else { // si ya esta, actualizar (el valor de la) Entrada y retornar el antiguo
            antiguoValor = cubeta.recuperar().valor;
            cubeta.recuperar().valor = v;
	}
        return antiguoValor;
    }
     
    /** Comprueba si una Tabla Hash esta vacia, i.e. si tiene 0 entradas.
     */
    public boolean esVacio() { 
        return talla == 0; 
    }
    
    /** Devuelve la talla, o numero de entradas, de una Tabla Hash.
     */
    public int talla() { 
        return talla; 
    } 

    /** Devuelve una ListaConPI con las talla() claves de una Tabla Hash
     */
    public ListaConPI<C> claves() {
        ListaConPI<C> l = new LEGListaConPI<C>();
        for(ListaConPI<EntradaHash<C, V>> cubeta : elArray){
            for(cubeta.inicio();!cubeta.esFin();cubeta.siguiente()){
                l.insertar(cubeta.recuperar().clave);
            }
        }
        return l;
    }
    
    /** Devuelve el factor de carga actual de una Tabla Hash, i.e. la longitud
     *  media de sus cubetas
     */
    // RECUERDA: este metodo tiene 
    // un coste INDEPENDIENTE de la talla del problema
    // NO hace falta calcular con un bucle la longitud media de las cubetas!!!
    public final double factorDeCarga() {
        return 0; /*CAMBIAR / COMPLETAR*/
    }
    
    /**Ejercicio 6: Añade un metodo que dada una entrada de clave
     * c devuelva el numero de colisiones que provoca su localizacion
     */
    public int numeroColisiones(C c) {
        return elArray[indiceHash(c)].talla();
    }
    
    /**Ejercicio 7: Diseña un metodo que dado un determinado valor
     * devuelva una ListaConPI con todas las claves que 
     * tienen asociado dicho valor
     */
    public ListaConPI<C> clavesConValor(V v) {
	ListaConPI<C> deClaves = new LEGListaConPI<C>();
	for (int i = 0; i < elArray.length; i++) {
	    for (elArray[i].inicio(); !elArray[i].esFin(); elArray[i].siguiente()) {
		if (elArray[i].recuperar().valor.equals(v)) {
		    deClaves.insertar(elArray[i].recuperar().clave);
		}
            }
	}
	return deClaves;
    }
    
    public static <E> E modaDe(E[] v) {
	E moda = null;
	int frecModa = 0;
	Map<E, Integer> map = new TablaHash<E, Integer>(v.length);
	for (int i = 0; i < v.length; i++) {
            Integer frec = map.recuperar(v[i]);
            if (frec != null) {
                frec++;
            } else {
        	frec = 1;
            }
            map.insertar(v[i], frec);
            if (frec > frecModa) {
        	frecModa = frec;
        	moda = v[i];
            }
	}
	return moda;
    }
    
    /*******************************
     * SOLO PARA EJEMPLOS DE TEORIA
     *******************************/
    /** Devuelve un String con las Entradas de una Tabla Hash 
     */
    // RECUERDA: se usa la clase StringBuilder porque es mas eficiente
    public final String toString() {
        StringBuilder res = new StringBuilder();
        /*COMPLETAR*/
        return res.toString(); 
    }
}