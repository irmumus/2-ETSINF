package librerias.estructurasDeDatos.grafos;

import librerias.estructurasDeDatos.modelos.ListaConPI;
import librerias.estructurasDeDatos.modelos.Cola;
import librerias.estructurasDeDatos.modelos.ColaPrioridad;
import librerias.estructurasDeDatos.modelos.UFSet;
import librerias.estructurasDeDatos.lineales.LEGListaConPI;
import librerias.estructurasDeDatos.lineales.ArrayCola;
import librerias.estructurasDeDatos.jerarquicos.ForestUFSet;
import librerias.estructurasDeDatos.jerarquicos.MonticuloBinario;

/** Clase abstracta Grafo: 
 *  Base de la jerarquia Grafo, que define el comportamiento 
 *  de un grafo. No es una interfaz porque incluye el codigo 
 *  de aquellas operaciones de un grafo que son independientes 
 *  tanto de su tipo como de su implementacion.
 */

public abstract class Grafo {
    //ATRIBUTOS
    protected int[] visitados;
    protected int ordenVisita;
    protected Cola<Integer> q;
    protected double[] distanciaMin;
    protected int[] caminoMin;
    protected static final double INFINITO = Double.POSITIVE_INFINITY;

    /** Devuelve el numero de vertices de un grafo.
     *  @return int, numero de vertices de un grafo
     */
    public abstract int numVertices();
    
    /** Devuelve el numero de aristas de un grafo.
     *  @return int, numero de aristas de un grafo
     */
    public abstract int numAristas();
    
    /** Comprueba si la arista (i,j) esta en un grafo.
     *  @param i, Vertice origen
     *  @param j, Vertice destino
     *  @return boolean, true si (i,j) esta y false en caso 
     *  contrario
     */
    public abstract boolean existeArista(int i, int j);
    
    /** Devuelve el peso de la arista (i,j) de un grafo, 
     *  0 si dicha arista no esta en el grafo.
     *  @return double, Peso de la arista (i,j), 0 si no existe.
     */
    public abstract double pesoArista(int i, int j);
    
    /** Si no esta, inserta la arista (i, j) en un grafo 
     *  no Ponderado.
     *  @param i, Vertice origen
     *  @param j, Vertice destino
     */
    public abstract void insertarArista(int i, int j);
    
    /** Si no esta, inserta la arista (i, j) de peso p en
     *  un grafo Ponderado.
     *  @param i, Vertice origen
     *  @param j, Vertice destino
     *  @param p, Peso de la arista (i,j)
     */
    public abstract void insertarArista(int i, int j, double p);

    /** Devuelve una Lista Con PI que contiene los adyacentes
     *  al vertice i de un grafo.
     *  @param i, Vertice del que se obtienen los adyacentes
     *  @return ListaConPI, con los vertices adyacentes a i
     */
    public abstract ListaConPI<Adyacente> adyacentesDe(int i);
       
    /** Devuelve un String con cada uno de los vertices 
     *  de un grafo y sus adyacentes, en orden de insercion 
     *  @return String, que representa a un grafo
     */        
    public String toString() {
        String res = "" ;  
        for (int i = 0 ; i < numVertices(); i++) {
            res += "Vertice: " + i;
            ListaConPI<Adyacente> l = adyacentesDe(i);
            res += (l.esVacia()) ? " sin adyacentes " :  " con adyacentes: ";
            for (l.inicio(); !l.esFin(); l.siguiente()) {
                res +=  l.recuperar() + " ";  
            }
            res += "\n";  
        }
        return res;      
    }  
     
    /**EJERCICIO 3: DEVUELVE UN ARRAY CUYOS ELEMENTOS SON
     * LOS VÉRTICES DEL GRAFO EN EL ORDEN QUE FINALIZA SU DFS
     */
    public int[] finDelDFS() {
        int[] res = new int[numVertices()]; 
        ordenVisita = 0;
        visitados = new int[numVertices()];
        for (int v = 0; v < numVertices(); v++) {
            if (visitados[v] == 0) { finDelDFS(v, res); }
        }
        return res;
    }
    
    protected void finDelDFS(int v, int[] res) {
        res[ordenVisita] = v; 
        ordenVisita++; 
        visitados[v] = 1;
        ListaConPI<Adyacente> l = adyacentesDe(v);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int w = l.recuperar().getDestino();
            if (visitados[w] == 0) { finDelDFS(w, res); }
        }
        // PISTA: marcar con 2 el FINAL del DFS de v
        visitados[v] = 2; 
        res[ordenVisita] = v; ordenVisita++;
    }
    
    public int[] toArrayDFS() {
        int[] res = new int[numVertices()];
        ordenVisita = 0;
        visitados = new int[numVertices()];
        for (int v = 0; v < numVertices(); v++) {
            if (visitados[v] == 0) { toArrayDFS(v, res); }
        }
        return res;
    } 
    
    protected void toArrayDFS(int v, int[] res) {
        res[ordenVisita] = v;
        ordenVisita++;
        visitados[v] = 1;
        ListaConPI<Adyacente> l = adyacentesDe(v);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int w = l.recuperar().getDestino();
            if (visitados[w] == 0) { toArrayDFS(w, res); }
        }
    }
    
    public int[] toArrayBFS() {
        int[] res = new int[numVertices()];
        visitados = new int[numVertices()];
        ordenVisita = 0;
        q = new ArrayCola<Integer>();
        for (int v = 0; v < numVertices(); v++) {
            if (visitados[v] == 0) { toArrayBFS(v, res); }
        }
        return res;
    } 
    
    protected void toArrayBFS(int v, int[] res) {
        visitados[v] = 1; res[ordenVisita++] = v;
        q.encolar(v);
        while (!q.esVacia()) {
            int u = q.desencolar();
            ListaConPI<Adyacente> l = adyacentesDe(u);
            for (l.inicio(); !l.esFin(); l.siguiente()) {
                int w = l.recuperar().getDestino();
                if (visitados[w] == 0) {
                    visitados[w] = 1; res[ordenVisita++] = w;
                    q.encolar(w);
                }
            }
        }
    }
    
    public void caminosMinimosSinPesos(int v) {
        caminoMin = new int[numVertices()];
        distanciaMin = new double[numVertices()];
        for (int i = 0; i < numVertices(); i++) {
            distanciaMin[i] = INFINITO;
            caminoMin[i] = -1;
        }
        distanciaMin[v] = 0;
        q = new ArrayCola<Integer>();
        caminosBFS(v);
    }
    
    protected void caminosBFS(int v) {
        q.encolar(v);
        while (!q.esVacia()) {
            int u = q.desencolar();
            ListaConPI<Adyacente> l = adyacentesDe(u);
            for (l.inicio(); !l.esFin(); l.siguiente()) {
                int w = l.recuperar().getDestino();
                if (distanciaMin[w] == INFINITO) {
                    distanciaMin[w] = distanciaMin[u] + 1;
                    caminoMin[w] = u;
                    q.encolar(w);
                }
            }
        }
    }
    
    public ListaConPI<Integer> caminoMinimoSinPesos(int v, int w) {
        caminosMinimosSinPesos(v);
        return decodificarCaminoHasta(w);
    }
    
    protected ListaConPI<Integer> decodificarCaminoHasta(int w) {
        ListaConPI<Integer> res = new LEGListaConPI<Integer>();
        if (distanciaMin[w] != INFINITO) {
            res.insertar(w);
            for (int v = caminoMin[w]; v != -1; v = caminoMin[v]) {
                res.inicio();
                res.insertar(v);
            }
        }
        return res;
    }
    
    public ListaConPI<Integer> caminoMinimo(int v, int w) {
        dijkstra(v);
        return decodificarCaminoHasta(w);
    }
    
    public void dijkstra(int u) {
        distanciaMin = new double[numVertices()];
        caminoMin = new int[numVertices()];
        for (int i = 0; i < numVertices(); i++) {
            distanciaMin[i] = INFINITO;
            caminoMin[i] = -1;
        }
        distanciaMin[u] = 0;
        visitados = new int[numVertices()];
        ColaPrioridad<VerticeCoste> qP;
        qP = new MonticuloBinario<VerticeCoste>();
        qP.insertar(new VerticeCoste(u, 0));
        // mientras haya vértices por explorar
        while (!qP.esVacia()) {
            // siguiente vértice a explorar es el de menor distancia
            int v = qP.eliminarMin().codigo;
            if (visitados[v] == 0) { // evitar repeticiones
                visitados[v] = 1;
                // recorrer vértices adyacentes de v
                ListaConPI<Adyacente> l = adyacentesDe(v);
                for (l.inicio(); !l.esFin(); l.siguiente()) {
                    int w = l.recuperar().destino;
                    double costeW = l.recuperar().peso;
                    // ¿ mejor forma de alcanzar w es a través de v ?
                    if (distanciaMin[w] > distanciaMin[v] + costeW) {
                        distanciaMin[w] = distanciaMin[v] + costeW;
                        caminoMin[w] = v;
                        qP.insertar(
                        new VerticeCoste(w, distanciaMin[w]));
                    }
                }
            }
        }
    }
    
    class VerticeCoste implements Comparable<VerticeCoste> {
        protected int codigo; 
        protected double coste;
        
        public VerticeCoste(int cod, double cost) {
            codigo = cod; coste = cost;
        }
        
        public int compareTo(VerticeCoste v) {
            if (coste < v.coste) { return -1; }
            if (coste > v.coste) { return 1; }
            return 0;
        }
    }
    
    public int[] ordenTopologicoDFS() {
        int[] res = new int[numVertices()]; 
        ordenVisita = 0;
        visitados = new int[numVertices()];
        for (int v = 0; v < numVertices(); v++) {
            if (visitados[v] == 0) { 
                ordenTopologicoDFS(v, res); 
            }
        }
        return res;
    }
        
    protected void ordenTopologicoDFS(int v, int[] res) {
        visitados[v] = 1;
        ListaConPI<Adyacente> l = adyacentesDe(v);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int w = l.recuperar().getDestino();
            if (visitados[w] == 0) { 
                ordenTopologicoDFS(w, res); 
            }
        }
        visitados[v] = 2;
        res[numVertices()- 1 - ordenVisita] = v; 
        ordenVisita++;
    }
    
    // SII el Grafo es un Digrafo ...
    public boolean hayCicloDFS() {
        boolean ciclo = false;
        visitados = new int[numVertices()];
        for (int v = 0; v < numVertices() && !ciclo; v++){
            if (visitados[v] == 0) { 
                ciclo = hayAristaHADFS(v); 
            }
        }
        return ciclo;
    }
    
    protected boolean hayAristaHADFS(int v) {
        boolean aristaHA = false; 
        visitados[v] = 1;
        ListaConPI<Adyacente> l = adyacentesDe(v);
        for (l.inicio(); !l.esFin() && !aristaHA; l.siguiente()) {
            int w = l.recuperar().getDestino();
            if (visitados[w] == 0) { aristaHA = hayAristaHADFS(w); }
            else if (visitados[w] == 1) { aristaHA = true; }
        }
        visitados[v] = 2;
        return aristaHA;
    } 
    
    public int numeroCC() {
        UFSet ufs = new ForestUFSet(numVertices());
        int nCC = numVertices();
        for (int u = 0; u < numVertices(); u++) {
            ListaConPI<Adyacente> l = adyacentesDe(u);
            for (l.inicio(); !l.esFin(); l.siguiente()) {
                int v = l.recuperar().getDestino();
                int claseU = ufs.find(u);
                int claseV = ufs.find(v);
                if (claseU != claseV) {
                    ufs.union(claseU, claseV);
                    nCC--;
                }
            }
        }
        return nCC;
    }
    
    public String verticesCercanos(int v, int n) {
        distanciaMin = new double[numVertices()];
        for (int i = 1; i < numVertices(); i++){
            distanciaMin[i] = INFINITO;
        }
        distanciaMin[v] = 0;
        q = new ArrayCola<Integer>();
        q.encolar(v);
        String res = "[";
        while (!q.esVacia()) {
            int u = q.desencolar();
            if (distanciaMin[u] < n) {
                ListaConPI<Adyacente> l = adyacentesDe(u);
                for (l.inicio(); !l.esFin(); l.siguiente()) {
                    int w = l.recuperar().getDestino();
                    if (distanciaMin[w] == INFINITO) {
                        distanciaMin[w] = distanciaMin[u] + 1;
                        res += "(" + w + ", " + (int)distanciaMin[w] + ") ";
                        q.encolar(w);
                    }
                }
            }
        }
        return res + "]";
    } 
    
    public int maxVerticesCC() {
        int res = 0;
        visitados = new int[numVertices()];
        for (int v = 0; v < numVertices(); v++) {
            if (visitados[v] == 0) {
                int verticesCCv = maxVerticesCC(v);
                if (verticesCCv > res) { res = verticesCCv; }
            }
        }
        return res + 1;
    }
    
    protected int maxVerticesCC(int v) {
        int res = 0;
        visitados[v] = 1;
        ListaConPI<Adyacente> l = adyacentesDe(v);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int w = l.recuperar().getDestino();
            if (visitados[w] == 0) { res += 1 + maxVerticesCC(w); }
        }
        return res;
    }
    
    public int aristasHA() {
        int res = 0; visitados = new int[numVertices()];
        for (int v = 0; v < numVertices(); v++) {
            if (visitados[v] == 0) { res += aristasHA(v); }
        }
        return res;
    }
    
    protected int aristasHA(int v) {
        int res = 0; visitados[v] = 1;
        ListaConPI<Adyacente> l = adyacentesDe(v);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int w = l.recuperar().getDestino();
            if (visitados[w] == 0) { res += aristasHA(w); }
            else if (visitados[w] == 1) { res++; }
        }
        visitados[v] = 2;
        return res;
    }
}