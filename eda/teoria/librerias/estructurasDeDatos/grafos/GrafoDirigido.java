package librerias.estructurasDeDatos.grafos;

import librerias.estructurasDeDatos.modelos.ListaConPI; 
import librerias.estructurasDeDatos.lineales.LEGListaConPI;

/** Clase GrafoDirigido: 
*  implementacion de un grafo Dirigido (Ponderado o no) 
*  mediante Listas de Adyacencia.
*/

public class GrafoDirigido extends Grafo { 

    protected int numV, numA;
    protected ListaConPI<Adyacente>[] elArray;
    
    /** Construye un grafo Dirigido vacio con numVertices. 
     *  @param numVertices, Numero de vertices del grafo vacio
     */
    @SuppressWarnings("unchecked")
    public GrafoDirigido(int numVertices) {
        numV = numVertices; 
        numA = 0;
        elArray = new ListaConPI[numVertices]; 
        for (int i = 0; i < numV; i++) {
            elArray[i] = new LEGListaConPI<Adyacente>();
        }
    }
    
    /** Devuelve el numero de vertices de un grafo. 
     *  @return int, Numero de vertices de un grafo
     */
    public int numVertices() { return numV; }
     
    /** Devuelve el numero de aristas de un grafo.
     *  @return int, Numero de aristas de un grafo
     */
    public int numAristas() { return numA; }
    
    /** Comprueba si la arista (i,j) esta en un grafo.
     *  @param i, Vertice origen
     *  @param j, Vertice destino
     *  @return boolean, true si (i,j) esta y false 
     *  en caso contrario
     */
    public boolean existeArista(int i, int j) {
       ListaConPI<Adyacente> l = elArray[i]; 
       for (l.inicio(); !l.esFin(); l.siguiente())
            if (l.recuperar().getDestino() == j) return true;
       return false;   
    }
    
    /** Devuelve el peso de la arista (i,j) de un grafo, 
     *  0 si dicha arista no esta en el grafo.
     *  @return double, Peso de la arista (i,j), 0 si no existe.
     */
    public double pesoArista(int i, int j) {
        ListaConPI<Adyacente> l = elArray[i];
        for (l.inicio(); !l.esFin(); l.siguiente())
            if (l.recuperar().getDestino() == j) 
                return l.recuperar().getPeso();
       return 0.0;
    }
    
    /** Si no esta, inserta la arista (i, j) en un 
     *  grafo no Ponderado 
     *  (al principio de la Lista de adyacentes a i).
     *  @param i, Vertice origen
     *  @param j, Vertice destino
     */    
    public void insertarArista(int i, int j) {
        insertarArista(i, j, 1);
        /*if (!existeArista(i, j)) { 
            elArray[i].insertar(new Adyacente(j, 1)); 
            numA++; 
        }*/
    }
    
    /** Si no está, inserta la arista (i, j) de peso p 
     *  en un grafo Ponderado 
     *  (al principio de la Lista de adyacentes a i).
     *  @param i, Vertice origen
     *  @param j, Vertice destino
     *  @param p, Peso de (i, j)
     */ 
    public void insertarArista(int i, int j, double p) {
        if (!existeArista(i, j)) { 
            elArray[i].insertar(new Adyacente(j, p)); 
            numA++; 
        }
    }
        
    /** Devuelve una Lista Con PI que contiene los adyacentes 
     * al vertice i de un grafo.
     *  @param i, Vertice del que se obtienen los adyacentes
     *  @return ListaConPI, con los vertices adyacentes a i
     */
    public ListaConPI<Adyacente> adyacentesDe(int i) { 
        return elArray[i]; 
    }
    
    /**EJEMPLO 1: GRADO DE SALIDA DE UN VÉRTICE*/
    public int gradoSalida(int i) {
        return elArray[i].talla();
    }
    
    /**EJEMPLO 2: GRADO DE SALIDA DE UN GRAFO 
     * (VÉRTICE CON MAYOR GRADO)
     */
    public int gradoSalida() {
        int gradoMax = gradoSalida(0); //Cojo como base este 
        for(int i = 1; i<numV; i++){
            //Grado a comparar ccon gradoMax
            int grado = gradoSalida(i); 
            if(grado>gradoMax) { //Comparo grados
                gradoMax = grado;
            }
        }
        return gradoMax;
    }
    
    /**EJEMPLO 3: GRADO DE ENTRADA DE UN VÉRTICE*/
    public int gradoEntrada(int i) {
        int grado = 0;
        for(int j = 0; j<numV; j++) {
            if(existeArista(j,i)) {grado++;}
        }
        return grado;
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
        
    /**EJERCICIO 1A: DEVUELVE EL GRADO DEL GRAFO (VER DIBUJO)*/
    public int grado(){
        int[] gradoV = getArrayGrados();
        return maximo(gradoV);
    }
    
    protected int[] getArrayGrados(){
        int[] grados = new int[numV];
        for(int i = 0; i<numV; i++){
            ListaConPI<Adyacente> l = elArray[i];
            grados[i] += l.talla();
            for(l.inicio(); !l.esFin(); l.siguiente()){
                grados[l.recuperar().getDestino()]++;
            }
        }
        return grados;
    }
    
    protected int maximo(int[] v){
        int max = v[0];
        for(int i = 1; i<v.length; i++){
            if(v[i]>max){
                max = v[i];
            }
        }
        return max;
    }
    
    /**EJERCICIO 1B: ARISTA DE MAYOR PESO, PESO MÁXIMO 
     * DE LAS ARISTAS DE UN GRAFO
     */
    public double aristaMayorPeso(){
        double res = -1;
        for(int i = 0; i<numV; i++){
            ListaConPI<Adyacente> l = elArray[i]; 
            //l obtiene la lista de adyacentes de elArray[i]
            for(l.inicio(); !l.esFin(); l.siguiente()){
                Adyacente a = l.recuperar();
                if(a.getPeso()>res) {
                    res = a.getPeso();
                }
            }
        }
        return res;
    }
    
    /**EJERCICIO 1C: DEVOLVER SI EL GRAFO ES REGULAR, ES DECIR,  
     * TODOS LOS VÉRTICES TIENEN EL MISMO GRADO
     */
    public boolean esRegular(){
        //Paso 1: Calcular el grado vértices eficientemente
        int[] gradoV = getArrayGrados();
        //Paso 2: comprobar si es regular. Buscamos el 1er 
        //vértice cuyo grado sea distinto a los demás
        int gradoV0 = gradoV[0];
        for(int i = 1; i<numV; i++){
            if(gradoV[i] != gradoV0){ return false;}
        }
        return true;
    }
    
    /**EJERCICIO 2A: DEVOLVER EL PRIER VÉRTICE CON 
     * GRADO DE ENTRADA |V|-1
     * SI NO EXISTE DEVOLVER -1
     */
    public int getVerticeReceptivo() {
        //Paso 1: Cálculo del grado de entrada de cada vértice
        int[] gradoEntrada = getArrayGradosEntrada();
        //Paso 2: Buscar vértice i 
        //tal que gradoEntrada[i] = numV - 1
        for (int i = 0; i < numV; i++){
            if (gradoEntrada[i] == numV - 1) {return i;}
        }
        return -1;
    }
    
    //Se parece a getArrayGrados, solo cambia lo comentado
    protected int[] getArrayGradosEntrada(){
        int[] grados = new int[numV];
        for(int i = 0; i<numV; i++){
            ListaConPI<Adyacente> l = elArray[i];
            //grados[i] += l.talla();
            for(l.inicio(); !l.esFin(); l.siguiente()){
                grados[l.recuperar().getDestino()]++;
            }
        }
        return grados;
    }
    
    /**EJERCICIO 2B: COMPROBAR SI EL VÉRTICE DE UN GRADO ES 
     * SUMIDERO
     * SUMIDERO -> VÉRTICE CON GRADO DE ENTRADA > 0  Y 
     * GRADO DE SALIDA = 0
     */
    public boolean esSumidero(int i) { 
        return gradoEntrada(i)>0 && gradoSalida(i)==0;
    }
    
    /**EJERCICIO 2C: DEVOLVER EL PRIMER SUMIDERO UNIVERSAL O -1
     * SUMIDERO UNIVERSAL -> VERTICE CON GRADO 
     * DE ENTRADA = |V|-1 Y GRADO SALIDA = 0
     */
    public int getSumideroU() {
        int[] gradoEntrada = getArrayGradosEntrada();
        for (int i = 0; i < numV; i++){
            if (elArray[i].talla() == 0
                    && gradoEntrada[i] == numV - 1) {return i;}
        }
        return -1;
    }
    
    /**EJERCICIO 2D: DEVOLVER LA 1ª FUENTE UNIVERSAL O -1
     * FUENTE UNIVERSAL -> VERTICE CON GRADO DE ENTRADA = 0
     * Y GRADO DE SALIDA = |V|-1
     */
    public int getFuenteU() {
        int[] gradoEntrada = getArrayGradosEntrada();
        for(int i = 0; i<numV;i++){
            if(elArray[i].talla()==numV-1 && gradoEntrada[i]==0){
                return i;
            }
        }
        return -1;
    }
    
    /**EJERCICIO 2E: DECIR SI UN GRAFO ES O NO COMPLETO
     * GRAFO COMPLETO->GRAFO SIMPLE DONDE CADA PAR DE VÉRTICES
     * DISTINTOS ESTÁN CONECTADOS POR UNA ÚNICA ARISTA
     */
    public boolean esCompleto(){
        return numA == numV*(numV-1);
    }
}