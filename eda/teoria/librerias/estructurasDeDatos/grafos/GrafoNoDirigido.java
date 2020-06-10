package librerias.estructurasDeDatos.grafos;
import librerias.estructurasDeDatos.modelos.ListaConPI; 
import librerias.estructurasDeDatos.lineales.LEGListaConPI;

/** Clase GrafoNoDirigido: 
 *  implementacion de un grafo No Dirigido (Ponderado o no) 
 *  mediante Listas de Adyacencia:
 *  un grafo No Dirigido ES UN Grafo Dirigido tal que 
 *  si la Arista (i, j) esta en la Lista de Adyacencia de i 
 *  entonces tambien esta la Arista (j, i) en la Lista de 
 *  Adyacencia de j
 */

public class GrafoNoDirigido extends GrafoDirigido {
    //ATRIBUTOS
    protected int aristasTree;
    
    /** Construye un grafo No Dirigido vacio con numVertices. 
     *  @param numVertices, Numero de vertices del grafo vacio
     */
    public GrafoNoDirigido(int numVertices) { super(numVertices); }
    
    /** Si no esta, inserta la arista (i, j) 
     *  en un grafo No Dirigido y No Ponderado; 
     *  por tanto, tambien inserta la arista (j, i).
     *  @param i, Vertice origen
     *  @param j, Vertice destino
     */ 
    public void insertarArista(int i, int j) {
        insertarArista(i, j, 1);
        /*if (!existeArista(i, j)) { 
            elArray[i].insertar(new Adyacente(j, 1)); 
            elArray[j].insertar(new Adyacente(i, 1));
            numA++; 
        }*/
    }
    
    /** Si no esta, inserta la arista (i, j) de peso p 
     *  en un grafo No Dirigido y Ponderado; 
     *  por tanto, tambien inserta la arista (j, i) de peso p.
     *  @param i, Vertice origen
     *  @param j, Vertice destino
     *  @param p, Peso de (i, j)
     */ 
    public void insertarArista(int i, int j, int p) {
        if (!existeArista(i, j)) { 
            elArray[i].insertar(new Adyacente(j, p)); 
            elArray[j].insertar(new Adyacente(i, p));
            numA++; 
        }
    }
    
    /**EJEMPLO 1: GRADO DE SALIDA DE UN VÉRTICE*/
    public int gradoSalida(int i) {
        return elArray[i].talla();
    }
    
    /**EJEMPLO 2: GRADO DE SALIDA DE UN GRAFO (VÉRTICE CON 
     * MAYOR GRADO)*/
    public int gradoSalida() {
        int gradoMax = gradoSalida(0); //Cojo como base este 
        for(int i = 1; i<numV; i++){
            int grado = gradoSalida(i); //Grado a comparar ccon gradoMax
            if(grado>gradoMax) { //Comparo grados
                gradoMax = grado;
            }
        }
        return gradoMax;
    }
    
    /**EJEMPLO 3: GRADO DE ENTRADA DE UN VÉRTICE*/
     public int gradoEntrada(int i) {
         return gradoSalida(i);
    }
    
    /**EJEMPLO 4: GRADO DE ENTRADA DE UN GRAFO*/
    public int gradoEntrada(){
        //Crear e inicializar array de contadores
        int[] gradoEntrada = new int[numV];
        for(int i = 0; i<numV; i++){
            //Actualizar el contador del grado de Entrada de 
            //cada vértice de la lista adyacentesDe(i)
            ListaConPI<Adyacente> l = elArray[i];
            for(l.inicio(); !l.esFin();l.siguiente()){
                gradoEntrada[l.recuperar().getDestino()]++;
            }
        }
        int gradoMax = gradoEntrada[0];
        for(int i = 1; i < numV; i++){
            int grado = gradoEntrada[i];
            if(grado>gradoMax) { gradoMax = grado;}
        }
        return gradoMax;
    }
    
    /**EJERCICIO 2A: DEVOLVER EL PRIER VÉRTICE CON GRADO DE 
     * ENTRADA |V|-1 Y SI NO EXISTE DEVOLVER -1
     */
    public int getVerticeReceptivo() {
        // Como grados de salida y entrada de un vértice 
        //son iguales
        for (int i = 0; i < numV; i++){
            if (elArray[i].talla() == numV - 1) {return i;}
        }
        return -1;
    } 
    
    /**EJERCICIO 2B: COMPROBAR SI EL VÉRTICE DE UN GRADO 
     * ES SUMIDERO
     * SUMIDERO -> VÉRTICE CON GRADO DE ENTRADA > 0  Y
     * GRADO DE SALIDA = 0
     */
    public boolean esSumidero(int i) { return false;}
    
    /**EJERCICIO 2C: DEVOLVER EL PRIMER SUMIDERO UNIVERSAL O -1
     * SUMIDERO UNIVERSAL -> VERTICE CON GRADO 
     * DE ENTRADA = |V|-1 Y GRADO SALIDA = 0
     */
    public int getSumideroU() {
        return -1;
    }
    
    /**EJERCICIO 2D: DEVOLVER LA 1ª FUENTE UNIVERSAL O -1
     * FUENTE UNIVERSAL -> VERTICE CON GRADO DE ENTRADA = 0
     * Y GRADO DE SALIDA = |V|-1
     */
    public int getFuenteU() {
        return -1;
    }
    
    /**EJERCICIO 2E: DECIR SI UN GRAFO ES O NO COMPLETO
     * GRAFO COMPLETO->GRAFO SIMPLE DONDE CADA PAR DE VÉRTICES
     * DISTINTOS ESTÁN CONECTADOS POR UNA ÚNICA ARISTA
     */
    public boolean esCompleto(){
        return 2* numA == numV*(numV-1);
    }
    
    /**EJERCICIO 4: COMPROBAR SI UN GRAFO ES CONEXO*/
    public boolean esConexo() {
        visitados = new int[numVertices()];
        ordenVisita = 0;
        recorridoDFS(0);
        return ordenVisita == numVertices();
        // Con mayor coste efectivo:
        // for (int v = 1; v < numVertices(); v++) {
        // if (visitados[v] == 0) { return false; }
        // }
        // return true;
    } 
    
    protected void recorridoDFS(int v) {
        visitados[v] = 1;
        ordenVisita++;
        ListaConPI<Adyacente> l = adyacentesDe(v);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int w = l.recuperar().getDestino();
            if (visitados[w] == 0) { recorridoDFS(w); }
        }
    }
    
    public String toStringCC() {
        String res = ""; visitados = new int[numVertices()];
        int nCC = 0;
        for (int v = 0; v < numVertices(); v++) {
            if (visitados[v] == 0) {
                nCC++; res += "[" + toStringCC(v) + "]\n";
            }
        }
        return "Hay " + nCC + " Componentes Conexas y son:\n" + res;
    }
    protected String toStringCC(int v) {
        String res = "" + v; visitados[v] = 1;
        ListaConPI<Adyacente> l = adyacentesDe(v);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int w = l.recuperar().getDestino();
            if (visitados[w] == 0) { res += " " + toStringCC(w); }
        }
        return res;
    }
    
    public String[] spanningTree() {
        visitados = new int[numVertices()];
        String[] sTree = new String[numVertices() - 1];
        aristasTree = 0;
        spanningTree(0, sTree);
        if (aristasTree != numVertices() - 1) { return null; }
        return sTree;
    }
    
    protected void spanningTree(int v, String[] sTree) {
        visitados[v] = 1;
        ListaConPI<Adyacente> l = adyacentesDe(v);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int w = l.recuperar().getDestino();
            if (visitados[w] == 0) {
                sTree[aristasTree++] = "(" + v + ", " + w + ")";
                spanningTree(w, sTree);
            }
        }
    }
}