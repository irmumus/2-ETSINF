package ejemplos.tema4;

import librerias.estructurasDeDatos.modelos.MapOrdenado;
import librerias.estructurasDeDatos.modelos.EntradaMap;
import librerias.estructurasDeDatos.modelos.ListaConPI; 
import librerias.estructurasDeDatos.lineales.LEGListaConPI; 
import librerias.estructurasDeDatos.jerarquicos.ABBMapOrdenado;
import java.util.*;

/**
 * class UsosMapOrdenado.
 * 
 * @author FTG 
 * @version 1.0
 */

public class UsosMapOrdenado {
    
    /** Disenyar un metodo estatico, generico e iterativo entradas 
     *  que devuelva una ListaConPI con las Entradas de un Map m 
     *  ordenadas ascendentemente. 
     */
    public static <C extends Comparable<C>, V> 
    ListaConPI<EntradaMap<C, V>> entradas(MapOrdenado<C, V> m) 
    {
        ListaConPI<EntradaMap<C, V>> l = new LEGListaConPI<EntradaMap<C, V>>();
        
        /** Obtener primera Entrada del Map Ordenado por claves,
         *  i.e. la Entrada de clave minima del Map 
         */
        C claveMin = m.recuperarMin();
        V valorMin = m.recuperar(claveMin);
        EntradaMap<C,V> e = new EntradaMap<C,V>(claveMin,valorMin);
        
        
        /** Insertar primer elemento de la lista de Entradas 
         *  ordenada ascendentemente por clave 
         */
        l.insertar(e);
        
        /** Para obtener siguientes Entradas de la lista resultado,
         *  recorrer el Map Ordenado por claves 
         */
        for (m.claves().inicio(); !m.claves().esFin(); m.claves().siguiente()) {
            /** Obtener siguiente Entrada del Map Ordenado por claves,
             *  i.e. el sucesor de la Entrada e 
             */
            e = m.sucesorEntrada(claveMin);
            claveMin = m.sucesor(claveMin);
            
            /** Insertar siguiente elemento de la lista de Entradas
             *  ordenada ascendentemente por clave 
             */
            l.insertar(e);
        }
        return l;
    }
    
    /** Diseñar un metodo estatico, generico e iterativo mapSort 
     *  que, con la ayuda de un MapOrdenado, 
     *  ordene los elementos (Comparable) de un array v.  
     */
    public static <C extends Comparable<C>> void mapSort(C[] v) {
        //Me creo el MAP a ordenar
        MapOrdenado<C, C> m = new ABBMapOrdenado<C, C> ();
        //Inserto los elementos de v en m
        int i = 0;
        while(i <= v.length-1){
            m.insertar(v[i],v[i]);
            i++;
        }
        //Ahora lo ordeno
        C aInsertar = m.recuperarMin();
        for(i=0; i<v.length; i++){
            v[i]=aInsertar;
            aInsertar = m.sucesor(aInsertar);
        }
    }
    
    /**Disenyar un metodo estatico iterativo corregirTexto que,
     * dados un texto y un MapOrdenado m de las palabras correctas de
     * un lenguaje dado, devuelva una ListaConPI con las palabras
     * incorrectas del texto en orden alfab�tico. Usar un MapOrdenado
     * como EDA auxiliar
     */
    public static ListaConPI<String> 
            corregirTexto(MapOrdenado<String, String> m, String[] s){
        ListaConPI<String> l =  new LEGListaConPI<String>();
        MapOrdenado<String, Integer> e = new ABBMapOrdenado<String, Integer>();
        for(int i = 0; i < s.length; i++){
            String b = m.recuperar(s[i]);
            if(s==null) { e.insertar(s[i],i);}
        }
        EntradaMap<String, Integer> d = e.recuperarEntradaMin();
        while(e!=null){
            l.insertar(d.toString());
            d = e.sucesorEntrada(d.getClave());
        }
        return l;
    }
    
    /** Diseñar un metodo estatico, generico e iterativo hayDosQueSuman 
     *  que, dados un array v de enteros y un entero k, 
     *  determine si existen en v dos numeros cuya suma sea k. 
     *  Usar un Map Ordenado como EDA auxiliar.
     */
    public static boolean hayDosQueSuman(Integer[] v, int k) {
        MapOrdenado<Integer, Integer> m = new ABBMapOrdenado<Integer, Integer>();
        for(int i = 0; i<v.length; i++){
            m.insertar(v[i], i);
        }
        Integer min = m.recuperarMin();
        Integer max = m.recuperarMax();
        boolean esta = false;
        for(int i = 0; i<v.length-1 && !esta; i++){
            if(min+max==k){ esta=true;}
            else if(min+max<k) {
                min = m.sucesor(min);
            } else { max = m.predecesor(max);}
        }
        return esta;
    }
}
