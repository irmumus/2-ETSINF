package librerias.estructurasDeDatos.grafos;

/** Clase Adyacente: 
 *  representa un vertice adyacente a otro de un grafo y el peso 
 *  de la arista que los une; 
 *  por ello, se puede considerar una representacion
 *  implicita de la arista que une dos vertices de un grafo.
 */

public class Adyacente { 
    
    protected int destino;
    protected double peso;
    
    /** Crea el vertice v adyacente a otro de un grafo  
     *  y el peso de la arista que los une.
     *  @param v, Vertice adyacente a otro
     *  @param peso, de la arista que une a v y a otro del que es adyacente 
     */
     public Adyacente(int v, double peso) { 
         destino = v;  
         this.peso = peso; 
     }

     /** Devuelve el vertice adyacente a otro de un grafo, 
      *  o el vertice destino de la arista que los une. 
      *  @return int, Vertice adyacente a otro 
      */
     public int getDestino() { return this.destino; }

     /** Devuelve el peso de la arista que une 
      *  a un vertice de un grafo a un adyacente a este. 
      *  @return double, 
      *  Peso de la arista que une a un adyacente de un vertice de un grafo, 
      *  1 si el grafo es No Ponderado
      */
     public double getPeso() { return this.peso; }
     
     /** Devuelve un String que representa a un adyacente 
      *  a un vertice de un grafo y al peso de la arista que los une.
      *  @return String, que representa a un adyacente
      */     
     public String toString() { return destino + "(" + peso + ") "; }
}