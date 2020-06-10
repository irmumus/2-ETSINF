package librerias.estructurasDeDatos.modelos;

/** Clase UFSet: representa las clases -subconjuntos- que define
 *  una relación de equivalencia en un conjunto de cardinal N
 *  (a) Los elementos del conjunto se representan mediante enteros 
 *      en el intervalo [0, N-1], pues su tipo concreto 
 *      NO interviene en la definición de las clases
 *  (b) Los elementos de una clase se representan mediante 
 *      uno SOLO de sus miembros, cualquiera de ellos, 
 *      pues todos son equivalentes
 *  (c) Las clases se definen dinámicamente a partir de las N triviales,
 *      cada una formada por un elemento del conjunto
 */ 
public interface UFSet {
    
    /** Devuelve el identificador de la clase de equivalencia
     *  -subconjunto- al que pertenece el elemento i */
    int find(int i);
    
    /** PRECONDICION: claseI != claseJ 
     *                AND claseI = find(i) AND claseJ = find(j)
     * Une las clases de equivalencia –subconjuntos-
     * con identificadores claseI y claseJ */
    void union(int claseI, int claseJ);
    
}