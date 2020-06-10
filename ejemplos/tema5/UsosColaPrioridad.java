package ejemplos.tema5;

import librerias.estructurasDeDatos.modelos.ColaPrioridad;
import librerias.estructurasDeDatos.jerarquicos.MonticuloBinario;
import librerias.estructurasDeDatos.modelos.ListaConPI;
import librerias.estructurasDeDatos.lineales.LEGListaConPI;

/**
 * class UsosColaPrioridad.
 * 
 * @author FTG 
 * @version 1.0
 */

public class UsosColaPrioridad {
    
    /** Problema 1: ORDENAR UN ARRAY E[] V
     *  Disenyar un metodo estatico e iterativo cPSort 
     *  que, con la ayuda de una Cola de Prioridad, 
     *  ordene un array v de elementos Comparable. 
     */
    public static <E extends Comparable<E>> void cPSort(E[] v) {
        ColaPrioridad<E> cP = new MonticuloBinario<E> (v.length+1);
        for(int i = 0; i < v.length; i++) {
            cP.insertar(v[i]);
        }
        for(int i = 0; i<v.length; i++) {
            v[i] = cP.eliminarMin();
        }
    }
    
    /** Problema 2: MEZCLAR 2 COLAS Y QUE SE ORDENEN
     *  Disenyar un metodo estatico, generico e iterativo cPFusionar 
     *  que devuelva una ListaConPI con los datos de 2 Colas de Prioridad dadas, 
     *  cP1 y cP2, ordenados ascendentemente. 
     *  El metodo no puede usar ninguna EDA auxiliar para calcular su resultado 
     *  y, ademas, cP1 y cP2 deben quedar vacias al concluir su ejecucion.
     */
    public static <E extends Comparable<E>> ListaConPI<E> cPFusionar(ColaPrioridad<E> cP1, ColaPrioridad<E> cP2){
        ListaConPI res = new LEGListaConPI<E> ();
        while(!cP1.esVacia() && !cP2.esVacia()) { //mientras las dos no estén vacías
            if(cP1.recuperarMin().compareTo(cP2.recuperarMin())<0){ 
                //cP1 es menor que cP2
                res.insertar(cP1.eliminarMin());
            }else{ //min de cP2 < min de cP1
                res.insertar(cP2.eliminarMin());
            }
        }
        //Si acaba el while es porque cP1 y/o cP2 han acabado
        while(!cP1.esVacia()) { //Si a cP1 le quedan valores
            res.insertar(cP1.eliminarMin());
        }
        while(!cP2.esVacia()){ //Si a cP2 le quedan valores
            res.insertar(cP2.eliminarMin());
        }
        return res;
    }
    
    /** Problema 3: COMPROBAR SI LA DIFERENNCIA ENTRE 2 VALORES CONSECUTIVOS SE AJUSTA A EPSILON (NO LO SOBREPASA, ES MENOR)
     *  Disenyar un metodo estatico e iterativo cPEsLineal 
     *  que determine si un conjunto de valores reales se ajusta (aprox.) 
     *  a una funcion lineal creciente usando el siguiente algoritmo: 
     *  comprobar si la diferencia entre todo par de valores consecutivos, 
     *  en orden ascendente, esta acotada por un epsilon dado. 
     */
    public static boolean cPEsLineal(ColaPrioridad<Double> cP, double epsilon) {
        //La cola está ordenada
        double a = cP.eliminarMin(); //Obtengo el mínimo(el primero, porque está ordenada)
        while(!cP.esVacia()){
            double b = cP.eliminarMin(); //obtengo el mínimo de la cola (en el primer caso será igual a a)
            if(b-a>epsilon){ return false;}
        }
        return true;
    }
    
    /** Problema 4: DEVOLVER LOS MEJORES K ELEMENTOS DEL ARRAY (LOS PRIMEROS K ELEMENTOS, PORQUE ES COLA DE PRIORIDAD)
     *  Disenyar un metodo estatico, generico e iterativo cPTopK 
     *  que, dado un array de datos v y un entero k, 
     *  devuelva una Cola de Prioridad con los k mejores (Top K) datos de v. 
     *  El metodo debe tener un coste O(X log k), siendo X la longitud de v.
     */
    public static <E extends Comparable<E>> ColaPrioridad<E> cPTopK(E[] v, int k) {
        ColaPrioridad<E> cP = new MonticuloBinario<E>(k+1);
        for(int i = 0; i<v.length; i++){
            cP.insertar(v[i]);
            if(i>=k-1){cP.eliminarMin();} //Elimina el más pequeño, o sea, el peor valorado
        }
        return cP;
    }
    
}