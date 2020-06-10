package ejemplos.tema2;

/**
 * class EjerciciosRL.
 * 
 * @author FTG
 * @version 1.0
 */

public class EjerciciosRL {
 
    /** Ejercicio1: Sea v un array de enteros que se ajustan 
     *  al perfil de una curva continua y monotona creciente, 
     *  tal que: v[0] < 0 && v[v.length-1] > 0
     *  Existe una unica posición k de v, 0 <= k < v.length-1, 
     *  tal que: entre v[k] y v[k+1] la funcion vale 0, 
     *  i.e. tal que: v[k] <= 0 && v[k+1] > 0 
     *  
     *  Diseñar el mejor (coste logaritmico) metodo recursivo 
     *  que calcule k
     */
    public static int puntoCruce(int[] v) {
        return puntoCruce(v, 0, v.length - 1);
    }
    
    private static int puntoCruce(int[] v, int i, int j) {
        /** Busqueda con garantia de exito del indice k tal que: 
         *  (v[k] <= 0) && (v[k + 1] > 0)
         *  Se divide el problema en 2 subproblemas
         */
        int m = (i + j) / 2;
        /** Para que m sea el punto de cruce (m = k), 
         * como minimo: v[m] <= 0 */
        if (v[m] <= 0) {
            /** Si, ademas, v[m + 1] > 0, entonces 
             * m es el punto de cruce */
            if (v[m + 1] > 0) return m; 
            /** Sino, el punto de cruce NO es m 
             *  y, ademas, solo puede estar a partir de m + 1
             *  i.e. en el subArray v[m + 1, j]
             */
            else return puntoCruce(v, m + 1, j); 
        }
        /** Si v[m] > 0, el punto de cruce NO es m 
         *  y, ademas, solo puede estar antes de m
         *  i.e. en el subArray v[i, m - 1]
         */
        else return puntoCruce(v, i, m - 1); 
    }
    
    
    /** Ejercicio2: Sea v un array de enteros positivos 
     * que se ajustan al perfil de una curva concava, 
     *  i.e. existe una unica posición k de v, 
     *  0 <= k <v.length, tal que:
     *  para todo j : 0 <= j < k       : v[j] > v[j+1]  &&  
     *  para todo j : k < j < v.length : v[j-1] < v[j]
     *  
     *  Diseñar el mejor (coste logaritmico) metodo 
     *  recursivo que calcule k
     */
    public static int minimoCC(int[] v) {
        return minimoCC(v, 0, v.length - 1);
    }
    
    private static int minimoCC(int[] v, int i, int j) {
        int m = (i + j) / 2;
        if (m == 0 || m == v.length - 1
            || (v[m] < v[m - 1] && v[m] < v[m + 1])) 
            return m; 
        else {
            if (v[m - 1] < v[m]) return minimoCC(v, i, m - 1); 
            else                 return minimoCC(v, m + 1, j); 
        }
    }
    
    /** Ejercicio3: Busqueda de la componente de un 
     * array con valor igual a posicion. 
     * 
     *  Diseñar un metodo recursivo que, con el menor 
     *  coste posible, 
     *  determine si un array v de enteros, 
     *  ordenado ascendentemente y sin elementos repetidos, 
     *  contiene alguna componente cuyo valor es igual a 
     *  la posicion que ocupa. 
     *  Si existe tal componente el metodo devuelve su 
     *  posicion, y sino -1.    
     */
    public static int valYposIguales(int[] v) {
        return valYposIguales(v, 0, v.length - 1);
    }
    
    public static int valYposIguales(int[] v, int i, int j) {
        if (i > j) return -1;
        int m = (i + j) / 2;
        if (v[m] == m) return m;
        if (v[m] < m) return valYposIguales(v, m + 1, j);
        return valYposIguales(v, i, m - 1);
    }
    
    /** Ejercicio4: Busqueda de dos String en posiciones 
     * consecutivas de un array.
     * 
     *  Diseñar un método recursivo que, con el menor coste 
     *  posible,compruebe si dos String x e y (tal que x es
     *  menor estricto que y) 
     *  ocupan posiciones consecutivas en un array de String v, 
     *  ordenado ascendentemente y sin elementos repetidos. 
     */
    public static boolean vecinas(String[] v, String x, String y){
        return vecinas(v, x, y, 0, v.length - 1);
    }
    
    public static boolean vecinas(String[] v, String x, 
            String y,int i, int j) {
        if (i >= j) return false;
        int m = (i + j) / 2;
        int cx = v[m].compareTo(x);
        if (cx == 0) {
            int cy = v[m + 1].compareTo(y);
            return cy == 0;
        }
        if (cx < 0) return vecinas(v, x, y, m + 1, j);
        return vecinas(v, x, y, i, m - 1);
    }   
    
    /**Ejercicio 8: Buscar el primer elemento par del array
     * Supuestamente los primeros son impares y el resto
     * son pares, con lo que buscar la pos del 1º par
     */
    public int primerPar(int[] v){
        for(int i = 0; i < v.length-1; i++){
            if(v[i]%2==0) { return i;}
        }
        return -1;
    }
}