package librerias.estructurasDeDatos.jerarquicos;

import librerias.estructurasDeDatos.modelos.*;
import librerias.estructurasDeDatos.lineales.*; 
import librerias.excepciones.ElementoNoEncontrado;
import java.util.NoSuchElementException;

/** Clase ABB<E> que, en base a la identificacion AB-Nodo, representa un
 *  Arbol Binario mediante un enlace a su Nodo Raiz. Para poder utilizarla 
 *  como Implementacion eficiente de Cola de Prioridad y Map Ordenado, 
 *  sus caracteristicas son las siguientes: 
 *  1.- El tipo de sus Elementos es E extends Comparable<E>
 *  2.- Como unico ATRIBUTO, protected para la Herencia, 
 *      TIENE UN NodoABB<E> raiz
 *      OJO: talla NO es un atributo de la clase, sino de sus Nodos
 *           Precisamente por ello, esta clase implementa un ABB con Rango 
 *  3.- Tiene un unico constructor, el de ABB vacio, sin parametros
 *  4.- Todos sus metodos publicos son muy simples: basicamente, contienen
 *      una invocacion al metodo homonimo recursivo sobre su Nodo Raiz 
 *      (son metodos guia o lanzadera)
 *      Las caracteristicas de cualquiera de estos metodos recursivos son:
 *      4.1. Su modificador de visibilidad es protected y no private; 
 *      4.2. Tiene, al menos, como PARAMETRO FORMAL el Nodo actual sobre  
 *           el que se aplica, PERO NO ES ESTATICO; al invocar su ejecucion  
 *           desde el metodo publico, en su llamada mas alta, el valor del 
 *           Nodo actual es siempre this.raiz
 *      4.3. En general, devuelven el Nodo del ABB resultado de su aplicacion;
 *           Por ejemplo: recuperar(e) devuelve el Nodo de actual que contiene
 *           a e, el elemento a recuperar; insertar(e) devuelve el Nodo Raiz
 *           del ABB que se obtiene al insertar el elemento e en el Nodo actual
 *           
 * @param <E>, tipo de los elementos del ABB, Comparable por definicion 
 *
 **/

public class ABB<E extends Comparable<E>> {
    
    // para representar al AB subyacente, un ABB TIENE UN
    protected NodoABB<E> raiz;
 
    // METODOS CON LA MISMA EFICIENCIA QUE EN UN AB *****
    
    /** crea un ABB vacio, con 0 elementos */
    @SuppressWarnings("unchecked")
    public ABB() { this.raiz = null; }
    
    /** comprueba si un ABB esta vacio */
    public boolean esVacio() { return this.raiz == null; }
    
    /** devuelve la altura de un ABB, -1 si esta vacio **/
    public int altura() { return altura(this.raiz); }
    // devuelve la altura del Nodo actual (Recorrido en Post-Orden 
    // con caso base Nodo vacio explicito)
    protected int altura(NodoABB<E> actual) {
        if (actual == null) { return -1; }
        else { return Math.max(altura(actual.izq), altura(actual.der)) + 1; }
    }
       
    // SII ABB CON RANGO: METODO MAS EFICIENTE QUE EN UN AB *****
    
    /** devuelve el tamanyo de un ABB, o numero de Nodos que lo componen */
    public int talla() { 
        // el tamanyo de un ABB es el de su Nodo Raiz
        return talla(this.raiz); 
    }
    // devuelve el tamanyo del Nodo actual de un ABB 
    protected int talla(NodoABB<E> actual) {
        if (actual == null) { return 0; }
        else { return actual.talla; }
    }
      
    // SII ABB EQUILIBRADO: METODOS MAS EFICIENTES QUE EN UN AB  *****
    
    /** devuelve la primera aparicion en Pre-Orden de e en un ABB, 
     *  null si e no es un Dato del ABB */
    public E recuperar(E e) {
        NodoABB<E> res = recuperar(e, this.raiz);
        if (res == null) { return null; }
        else { return res.dato; }
    }
    // devuelve el primer Nodo en Pre-Orden de actual que contiene a e,
    // o null si no existe tal Nodo (caso base Implicito actual == null)
    protected NodoABB<E> recuperar(E e, NodoABB<E> actual) {
        NodoABB<E> res = actual;
        if (actual != null) {
            int resC = actual.dato.compareTo(e);
            if (resC > 0)      { res = recuperar(e, actual.izq); }
            else if (resC < 0) { res = recuperar(e, actual.der); }
            // else NO hacer nada porque res se ha inicializado a actual
        }
        return res; 
    }
    
    /** actualiza el Dato e de un ABB: si e no esta en el ABB lo inserta  
     *  y si esta lo sobrescribe */
    public void insertar(E e) { 
        this.raiz = insertar(e, this.raiz); 
    }
    // devuelve el Nodo que resulta de actualizar el Dato e de actual
    protected NodoABB<E> insertar(E e, NodoABB<E> actual) {
        NodoABB<E> res = actual; 
        if (actual != null) {    
            int resC = actual.dato.compareTo(e);
            if (resC > 0)      { res.izq = insertar(e, actual.izq); }
            else if (resC < 0) { res.der = insertar(e, actual.der); }
            else               { res.dato = e; }
            /** Actualizacion (a-posteriori) de la talla de los Nodos en el 
            //  Camino de Insercion: solo la insercion de un nuevo Nodo en
            //  actual incrementa en 1 su talla, pues si e ya estaba en el 
            //  Nodo actual (resC == 0) su talla no se vera modificada 
            //  (las tallas de sus Hijos seran las mismas que antes de la 
            //  insercion)
            */
            res.talla = 1 + talla(res.izq) + talla(res.der);
        }
        else { res = new NodoABB<E>(e); } /** RECUERDA: al crearlo, res.talla = 1 */
        return res; 
    }
    
    /** elimina el Dato e de un ABB */
    public void eliminar(E e) { 
        this.raiz = eliminar(e, this.raiz); 
    }
    // devuelve el Nodo que resulta de eliminar el Dato e de actual, null
    // si e no es un elemento de actual
    protected NodoABB<E> eliminar(E e, NodoABB<E> actual) {
        NodoABB<E> res = actual; 
        if (actual != null) {
            int resC = actual.dato.compareTo(e);
            if (resC > 0)      { res.izq = eliminar(e, actual.izq); }
            else if (resC < 0) { res.der = eliminar(e, actual.der); }
            else { // si resC == 0 eliminar el Nodo actual, pues contiene a e:
                /** CASO 1: actual es una Hoja o solo tiene un Hijo, por lo 
                //  que se puede devolver directamente como resultado la nueva
                //  Raiz del Nodo actual: respectivamente, un Nodo vacio (null),
                //  de talla 0, o el Nodo Raiz del unico Hijo de actual, de  
                //  talla 1 menos que la de actual 
                //  IMPORTANTE: no hace falta actualizar la talla del nodo  
                //  resultado; tras devolverlo se actualizara la de su padre
                //  MEJOR CASO de eliminar: e es el Dato del Nodo Raiz (actual)
                //  de un ABB que solo tiene un Hijo (de cualquier tamanyo) 
                */
                if (actual.izq == null) { return actual.der; } 
                else if (actual.der == null) { return actual.izq; }
                else { 
                /** CASO 2: actual tiene 2 Hijos, por lo que borrarlo equivale
                //  a cambiar su dato por el minimo de su Hijo Derecho Y eliminar
                //  dicho minimo
                //  PEOR CASO de eliminar: e es el Dato del Nodo Raiz (actual) de
                //  un ABB que tiene 2 Hijos 
                */
                    res.dato = recuperarMin(actual.der).dato; 
                    /** IMPORTANTE: al eliminarMin se actualiza 
                    //  la talla de actual.der
                    */
                    res.der = eliminarMin(actual.der); 
                } 
            }
            /** Actualizacion (a-posteriori) de la talla de los Nodos en el 
            //  Camino de Borrado: solo el borrado de un Nodo de actual
            //  decrementa en 1 la talla de su Hijo Derecho o Izquierdo
            */
            res.talla = 1 + talla(res.izq) + talla(res.der);
        }
        return res; 
    }
     
    /** SII !esVacio(): devuelve el minimo de un ABB */
    public E recuperarMin() { 
        return recuperarMin(raiz).dato; 
    }
    
    // SII actual != null: devuelve el Nodo que contiene el minimo de actual
    // (Recorrido Pre-Orden del Hijo Izquierdo de actual, caso base implicito)
    protected NodoABB<E> recuperarMin(NodoABB<E> actual) {
        NodoABB<E> res = actual;
        if (actual.izq != null)  { res = recuperarMin(actual.izq); }
        return res;
    }
    
    public E recuperarMax() { 
        return recuperarMax(raiz).dato; 
    }
    
    protected NodoABB<E> recuperarMax(NodoABB<E> actual) {
        NodoABB<E> res = actual;
        if (actual.der != null)  { res = recuperarMax(actual.der); }
        return res;
    }
    
    /** SII !esVacio(): elimina y devuelve el minimo de un ABB */
    public E eliminarMin() {
        E res = recuperarMin(); //Lo coge
        this.raiz = eliminarMin(raiz);//Lo elimina
        return res; 
    }
    
    // SII actual != null: devuelve el Nodo resultante de eliminar 
    //el minimo de actual (Recorrido PreOrden del Hijo Izq 
    //de actual, caso base implicito)
    protected NodoABB<E> eliminarMin(NodoABB<E> actual) {
        NodoABB<E> res = actual;
        if (actual.izq != null)  {
            res.izq = eliminarMin(actual.izq);
            res.talla--; // o res.talla = 1 + talla(res.izq) + talla(res.der);
        }
        else { res = actual.der; }
        return res;
    }
  
    /** SII !esVacio(): elimina y devuelve el máximo de un ABB */
    public E eliminarMax() {
        E res = recuperarMax();
        this.raiz = eliminarMax(raiz);
        return res; 
    }
    // SII actual != null: devuelve el Nodo resultante de eliminar el 
    //maximo de actual (Recorrido PreOrden del Hijo Izq de actual,
    //caso base implicito)
    protected NodoABB<E> eliminarMax(NodoABB<E> actual) {
        NodoABB<E> res = actual;
        if (actual.izq != null)  {
            res.izq = eliminarMax(actual.izq);
            res.talla--; // o res.talla = 1 + talla(res.izq) + talla(res.der);
        }
        else { res = actual.der; }
        return res;
    }
    /** SII !esVacio(): devuelve el sucesor de e en un ABB, null si no esta */
    public E sucesor(E e) {
        NodoABB<E> res = sucesor(e, this.raiz);
        if (res == null) { return null; }
        else { return res.dato; }
    }
    // SII actual != null: devuelve el Nodo de actual que contiene al sucesor  
    // de e, null si no esta
    protected NodoABB<E> sucesor(E e, NodoABB<E> actual) {
        NodoABB<E> res = null; 
        if (actual != null) {    
            int resC = actual.dato.compareTo(e);
            if (resC > 0) { 
                res = sucesor(e, actual.izq); // va a la izq 
                // vuelve de la izq, donde siempre esta el sucesor
                if (res == null) { res = actual; } // actualiza sucesor
            }
            else { 
                res = sucesor(e, actual.der); // va a la der 
                // vuelve de la der, luego el sucesor no varia
            }
        }
        return res; 
    }
 
    /** SII !esVacio() AND 1 <= k <= talla():  
     *  devuelve el k-esimo minimo de un ABB */
    public E seleccionar(int k) { 
        return seleccionar(k, this.raiz).dato; 
    }
    // SII actual != null AND 1 <= k <= talla(actual): devuelve el Nodo de  
    // actual que contiene su k-esimo minimo (Busqueda con garantia de exito)
    protected NodoABB<E> seleccionar(int k, NodoABB<E> actual) {
        int tallaI = talla(actual.izq);
        if  (k == tallaI + 1) { return actual; }
        else if (k <= tallaI) { return seleccionar(k, actual.izq); }
        else { return seleccionar(k - tallaI - 1, actual.der); }
    }

    /** metodo recuperar, version iterativa */
    public E recuperarI(E e) {
        NodoABB<E> aux = this.raiz; 
        while (aux != null) { 
            int resC = aux.dato.compareTo(e);   
            if (resC == 0)     { return aux.dato; }
            else if (resC > 0) { aux = aux.izq;   }
            else               { aux = aux.der;   }
        }
        return null; 
    }

    /** metodo eliminarMin, version iterativa */
    // SII actual != null
    protected NodoABB<E> eliminarMinI(NodoABB<E> actual) {
        NodoABB<E> aux = actual;
        NodoABB<E> padreAux = null;
        while (aux.izq != null) {
            aux.talla--; 
            padreAux = aux; 
            aux = aux.izq;    
        }
        if (padreAux == null) { actual = actual.der; } // minimo en Raiz!!
        else                  { padreAux.izq = aux.der; }
        return actual;
    }
    
    /** metodo eliminarMin, segunda version iterativa */
    // SII actual != null: devuelve el Nodo actual tras eliminar su minimo,
    // copiando el dato que este contiene en nodoMin (paso por referencia)
    protected NodoABB<E> eliminarMin(NodoABB<E> actual, NodoABB<E> nodoMin) {
        NodoABB<E> aux = actual;
        NodoABB<E> padreAux = null;
        while (aux.izq != null) {
            aux.talla--; 
            padreAux = aux; 
            aux = aux.izq;    
        }
        nodoMin.dato = aux.dato;
        if (padreAux == null) { actual = actual.der; }    
        else                  { padreAux.izq = aux.der; }
        return actual;
    }
    // SII !esVacio() 
    public E eliminarMin2() { 
        NodoABB<E> nodoMin = new NodoABB<E>(null);
        this.raiz = eliminarMin(this.raiz, nodoMin);
        return nodoMin.dato;
    }

    /** devuelve un String con los Datos de un ABB en Post-Orden */
    public String toStringPostOrden() {
        StringBuilder res = new StringBuilder().append("[");
        if (raiz != null) { toStringPostOrden(raiz, res); }
        return res.append("]").toString();
    }
    // SII actual != null: actualiza res con los Datos del Nodo actual  
    // en Post-Orden (Recorrido Post-Orden con caso base Nodo Hoja implicito)
    protected void toStringPostOrden(NodoABB<E> actual, StringBuilder res) {
        if (actual.izq != null) {
            toStringPostOrden(actual.izq, res); 
            res.append(", ");
        }
        if (actual.der != null) {
            toStringPostOrden(actual.der, res);
            res.append(", ");
        }
        res.append(actual.dato.toString());
    }
    
    /** devuelve un String con los Datos de un ABB en Pre-Orden */
    public String toStringPreOrden() { 
        StringBuilder res = new StringBuilder().append("[");
        if (raiz != null) { toStringPreOrden(raiz, res); }
        return res.append("]").toString();
    } 
    // SII actual != null: actualiza res con los Datos del Nodo actual  
    // en Pre-Orden (Recorrido Pre-Orden con caso base Nodo Hoja implicito)
    protected void toStringPreOrden(NodoABB<E> actual, StringBuilder res) {
        res.append(actual.dato.toString()); 
        if (actual.izq != null) {
            res.append(", ");
            toStringPreOrden(actual.izq, res); 
        }
        if (actual.der != null) {
            res.append(", ");
            toStringPreOrden(actual.der, res);
        }
    }
    
    /** devuelve un String con los Datos de un ABB en In-Orden */
    public String toStringInOrden() {  
        StringBuilder res = new StringBuilder().append("[");
        if (raiz != null) { toStringInOrden(raiz, res); }
        return res.append("]").toString();
    }
    // SII actual != null: actualiza res con los Datos del Nodo actual  
    // en In-Orden (Recorrido In-Orden con caso base Nodo Hoja implicito)
    protected void toStringInOrden(NodoABB<E> actual, StringBuilder res) {
        if (actual.izq != null) {
            toStringInOrden(actual.izq, res); 
            res.append(", ");
        }
        res.append(actual.dato.toString()); 
        if (actual.der != null) {
            res.append(", ");
            toStringInOrden(actual.der, res);
        }
    }
    
    /** devuelve un String con los Datos de un ABB ordenados Por Niveles */
    //  Recorrido ITERATIVO de la Cola que contiene, en cada iteracion, 
    //  los Nodos del ABB que aun quedan por visitar
    public String toStringPorNiveles() {
        if (this.raiz == null) { return "[]"; }
        StringBuilder res = new StringBuilder().append("[");
        Cola<NodoABB<E>> q = new ArrayCola<NodoABB<E>>();
        q.encolar(this.raiz);
        while (!q.esVacia()) {
            NodoABB<E> actual = q.desencolar();
            res.append(actual.dato.toString());
            // Por eficiencia, cada dato de res, ultimo incluido,  
            // se separa del siguiente con ", "
            res.append(", ");
            //Actualizacion de la Cola de Nodos a visitar
            if (actual.izq != null) { q.encolar(actual.izq); }
            if (actual.der != null) { q.encolar(actual.der); }
        }
        // Por eficiencia, para borrar el ultimo ", " 
        // de res se resta 2 a su longitud actual
        res.setLength(res.length() - 2);
        return res.append("]").toString();
    }    
    
    /**EJERCICIO5: devuelve el nivel en el que se encuentra la primera 
     * aparicion en Pre-Orden de e en un ABB equilibrado o -1
     */
    public int enQueNivel(E e){
        if(raiz==null){
            return -1;
        }
        return enQueNivel(e, raiz, 0);
    }
    
    public int enQueNivel(E e, NodoABB<E> actual, int nivAct){
        int res = -1;
        if(actual.dato.equals(e)){ res = nivAct;}
        else if (actual.izq != null) {
            res = enQueNivel(e, actual.izq, nivAct+1);
        } else if(res==-1 && actual.der!=null){
            res = enQueNivel(e, actual.der, nivAct+1);
        }
        return res;
    }
    
    /**EJERCICIO6: devolver el numero de elementos de un Nodo de 
     * un ABB equilibrdo que son mayores que uno dado e
     */
    protected int contarMayoresQue(E e, NodoABB<E> actual){
        int res = 0;
        if(actual!=null){
            if(actual.dato.compareTo(e)>0) { res+=1;}
            res += contarMayoresQue(e, actual.izq);
            res += contarMayoresQue(e, actual.der);
        }
        return res;
    }
    
    /**EJERCICIO7: devolver una lista con PI con los datos del ABB
     * ordenados ascendentemente (In-Orden)
     */
    public ListaConPI toListaConPI(){
        ListaConPI<E> l = new LEGListaConPI<E>();
        if(raiz!=null) { toListaConPI(raiz, l);}
        return l;
    }
    
    protected void toListaConPI(NodoABB<E> actual, ListaConPI<E> l){
        if(actual.izq!=null){//voy por la izquierda
            toListaConPI(actual.izq,l);
        }
        l.insertar(actual.dato);
        if(actual.der!=null){
            toListaConPI(actual.der,l);
        }
    }
    
    /**EJERCICIO 9: Devolver un String con los datos del 
    * nivel k de un ABB
    */
    public String datosEnNivel(int k){
       return "[" + datosEnNivel(k, this.raiz, 0) + "]";
    }
    
    protected String datosEnNivel(int k, NodoABB<E> actual, int nivAct){
        String res = "";
        if(actual!=null){
            if(k==nivAct) { 
                res = actual.dato.toString();
            } else {
                res = datosEnNivel(k,actual.izq,nivAct+1) + "," +
                    datosEnNivel(k, actual.der, nivAct+1);
            }
        }
        return res;
    }
    
    /**EJERCICIO10:Devuelve la altura de un ABB si es equilibrado
     * o lance NoSuchElementException tan pronto encuentre un7
     * nodo que incumple la condición de equilibrio
     */
    public int alturaDeEquilibrado() throws NoSuchElementException{
        return alturaDeEquilibrado(this.raiz);
    }
    
    protected int alturaDeEquilibrado(NodoABB<E> actual){
        if(actual==null) { return -1;}
        else{
            int alturaIzq = alturaDeEquilibrado(actual.izq);
            int alturaDer = alturaDeEquilibrado(actual.der);
            int dif = Math.abs(alturaIzq-alturaDer);
            if(dif>1){ 
                throw new NoSuchElementException("No es equilibrado");
            }
            return 1+ Math.max(alturaIzq, alturaDer);
        }
    }
        
    protected NodoABB<E> predecesor(E e, NodoABB<E> actual) {
	NodoABB<E> res = null;
	if (actual != null) {
	    int resC = actual.dato.compareTo(e);
        	if (resC < 0) {
        		res = predecesor(e, actual.der);
        		if (res == null)
        			res = actual;
        	} else {
        		res = predecesor(e, actual.izq);
                }
	}
	return res;
    }

    public E predecesor(E e) {
    	NodoABB<E> res = predecesor(e, this.raiz);
    	if (res == null)
    		return null;
    	else
    		return res.dato;
    }
    
    /**EJERCICIO11: Dado un dato e devolver el dato en el nodo padre
     * del que contiene a e o null si no está en el ABB o no
     * tiene padre
     */
    public E padreDe(E e){
        if(raiz!=null && raiz.dato.compareTo(e)==0){ return null;}
        return padreDe(raiz, e);
    }
    
    protected E padreDe(NodoABB<E> n, E e){
        if(n==null){ return null;}
        if(n.izq!=null && n.izq.dato.compareTo(e)==0){
            return n.dato;
        }
        if(n.der!=null && n.der.dato.compareTo(e)==0){
            return n.dato;
        }
        int resC = n.dato.compareTo(e);
        if(resC<0) { return padreDe(n.der, e);}
        else{ return padreDe(n.izq, e);}
    }
    
    public E hermanoDe(E e) {
        if (raiz != null && raiz.dato.compareTo(e) == 0) return null;
            return hermanoDe(raiz, e);
    }
    protected E hermanoDe(NodoABB<E> n, E e) {
        if (n == null) { return null; }
        
        if (n.izq != null && n.izq.dato.compareTo(e) == 0) {
            if (n.der != null) { return n.der.dato; }
            else { return null; }
        }
        
        if (n.der != null && n.der.dato.compareTo(e) == 0) {
            if (n.izq != null) { return n.izq.dato; }
            else { return null; }
        }
        int resC = n.dato.compareTo(e);
        if (resC < 0) { return hermanoDe(n.der, e); }
        else { return hermanoDe(n.izq, e); }
    }
    
    
}  