package librerias.estructurasDeDatos.jerarquicos; 
import  librerias.estructurasDeDatos.modelos.ListaConPI; 
import  librerias.estructurasDeDatos.modelos.ColaPrioridad; 
    
public class MonticuloBinario<E extends Comparable<E>> 
    implements ColaPrioridad<E> {
        
    protected static final int C_P_D = 11; 
    protected E[] elArray; 
    protected int talla;
  
    /** crea un Heap vacio */
    public MonticuloBinario() { this(C_P_D); }
    
    /** crea una Cola de Prioridad (CP) vacia con capacidad inicial n */
    @SuppressWarnings("unchecked")
    public MonticuloBinario(int n) { 
        elArray = (E[]) new Comparable[n];
        talla = 0;
    }
  
    /** comprueba si un Heap es vacio en Theta(1) */
    public boolean esVacia() { return talla == 0; }
      
    /** devuelve el minimo de un Heap en Theta(1) */
    public E recuperarMin() { return elArray[1]; }

    /** inserta e en un Heap */
    public void insertar(E e) {
        if (talla == elArray.length - 1) duplicarArray();
        // PASO 1. Buscar la posicion de insercion ordenada de e
        // (a) Preservar la Propiedad Estructural
        int posIns = ++talla; 
        
        // (b) Preservar la Propiedad de Orden: reflotar 
        posIns = reflotar(e, posIns); 
        /*
        while (posIns > 1 && e.compareTo(elArray[posIns / 2]) < 0) { 
            elArray[posIns] = elArray[posIns / 2]; 
            posIns = posIns / 2;
        }
        */
        // PASO 2. Insertar e en su posicion de insercion ordenada
        elArray[posIns] = e;
    }
    
    protected int reflotar(E e, int posIns) {
        while (posIns > 1 && e.compareTo(elArray[posIns / 2]) < 0) { 
            elArray[posIns] = elArray[posIns / 2]; 
            posIns = posIns / 2;
        }
        return posIns;
    }

    @SuppressWarnings("unchecked")
    protected void duplicarArray() {
        E[] nuevo = (E[]) new Comparable[elArray.length * 2];
        System.arraycopy(elArray, 1, nuevo, 1, talla);
        elArray = nuevo;
    }  
  
    /** recupera y elimina el minimo de un Heap */
    public E eliminarMin() {
        E elMinimo = elArray[1];
        // PASO 1. Borrar el minimo del Heap
        // (a) Preservar la Propiedad Estructural: 
        //     borrar Por Niveles el minimo
        elArray[1] = elArray[talla--];
        // (b) Preservar la Propiedad de Orden:
        //     buscar la posicion de insercion ordenada de elArray[1] 
        // PASO 2. Insertar elArray[1] en su posicion ordenada
        hundir(1); 
        return elMinimo;
    }
  
    protected void hundir(int pos) {
        int posActual = pos; 
        E aHundir = elArray[posActual]; 
        int hijo = posActual * 2; 
        boolean esHeap = false; 
        while (hijo <= talla && !esHeap) {
            if (hijo < talla && 
                elArray[hijo + 1].compareTo(elArray[hijo]) < 0) {
                hijo++;
            }
            if (elArray[hijo].compareTo(aHundir) < 0) {
                elArray[posActual] = elArray[hijo];
                posActual = hijo;  
                hijo = posActual * 2; 
            } 
            else { esHeap = true; }
        }
        elArray[posActual] = aHundir;
    }

    /** obtiene un String con los datos de una CP ordenados Por Niveles 
     *  y con el formato que se usa en el estandar de Java (entre corchetes
     *  cuadrados y separando cada elemento del anterior mediante una coma 
     *  seguida de un espacio en blanco); si la CP esta vacia el String 
     *  resultado es []
     */
    public String toString() { 
      // NOTA: se usa la clase StringBuilder, en lugar de String, 
      // por motivos de eficiencia
        StringBuilder res = new StringBuilder();
        if (talla == 0) return res.append("[]").toString();
        int i = 1;
        res.append("[");
        while (i < talla) res.append(elArray[i++] + ", ");
        res.append(elArray[i].toString() + "]"); 
        return res.toString();
    }
    
    /** devuelve el numero de hojas de un Heap en Theta(1) */
    public int contarHojas() { 
        return talla - (talla / 2);
    }
    
    /** devuelve el maximo de un Heap tras talla/2 compareTo */
    public E recuperarMax() { 
        int pos = talla / 2 + 1;
        E max = elArray[pos];
        while (pos <= talla) {
            if (elArray[pos].compareTo(max) > 0) {
                max = elArray[pos];
            } 
            pos++;
        }
        return max;
    }
    
    public void introducir(E e) {
        if (talla == elArray.length - 1) { duplicarArray(); }
        elArray[++talla] = e;
    }
    
    public void arreglar() { arreglar(1); }
    
    protected void arreglar(int i) {
        if  (i <= talla / 2) { //si no es una Hoja
            if (2 * i <= talla) { arreglar(2 * i); }
            if (2 * i + 1 <= talla) { arreglar(2 * i + 1); } 
            hundir(i); 
        }
    }
    
    /*  Restablece la propiedad de orden de un Heap */ 
    //  hunde Por-Niveles y Descendente los nodos Internos 
    //  de elArray, pues las Hojas ya son Heaps
    public void arreglarIterativo() {
        for (int i = talla / 2; i > 0; i--) {
            hundir(i);
        }
    } 
    
    /**PROBLEMA 5: COMPROBAR SI EXISTEN ELEMENTOS MENORES QUE E EN EL MONTÍCULO*/
    public boolean hayMenoresQue(E e){
        if(talla==0) { return false;}
        return elArray[1].compareTo(e)<0; //elArray[1] es la raíz del montículo = el más peque
    }
    
    /**PROBLEMA 6: COMPROBAR SI EXIXTEN ELEMENTOS MAYORES QUE E EN EL MONTÍCULO*/
    public boolean hayMayoresQue(E e){
        int pos1Hoja = talla/2 +1; //Las hojas son los nodos más bajos del montículo
        for(int i = pos1Hoja; i<=talla; i++){
            if(elArray[i].compareTo(e)>0){
                return true;
            }
        }
        return false;
    }
    
    /**PROBLEMA 7: COMPROBAR SI E ESTÁ EN EL MONTÍCULO (RECURSIÓN)*/
    public boolean estaEn(E e){
        return estaEn(e, 1);
    }
    
    protected boolean estaEn(E e, int i){
        if(i>talla || elArray[i].compareTo(e)>0){ return false;}
        if(elArray[i].compareTo(e)==0) { return true;}
        return estaEn(e, 2*i) || estaEn(e, 2*i+1); //2*i = hijo izquierdo de i; 2*i+1 = hijo derecho que i (elijo recorrido)
    }
    
    /**PROBLEMA 8; BORRAR LAS HOJAS DEL MONTÍCULO DEL RANGO [X,Y] Y ORDENAR*/
    public void borrarHojasEnRango(E x, E y){
        int n = talla;
        for(int i = n; i >n/2; i--){ //bucle descendente
            if(elArray[i].compareTo(x)>=0 && elArray[i].compareTo(y)<=0){ //si está entre x e y
                if(i<talla) { //si no es el último ya 
                    elArray[i] = elArray[talla]; //pongo el último en la posición i
                }
                talla--;
            }
        }
        arreglar(); //se ordena
    }
    
    /**PROBLEMA 9: DEVOLVER Y ELIMINAR EL ELEMENTO K*/
    public E eliminar(int k){
        E aEliminar = elArray[k]; //obtengo el elemento de k
        elArray[k] = elArray[talla]; //Pongo el último elemento la posición k del montículo
        talla--; //decremento la talla
        hundir(k); //arreglo el montículo ordenando k(hundo, porque lo he subido y hay que bajarlo) 
        return aEliminar;
    }
    
    /**PROBLEMA 10: DEVOLVER EL NÚMERO DE ELEMENTOS IGUALES AL MÍNIMO, ÉSTE INCLUIDO (RECURSIÓN)*/
    public int igualesAlMinimo(){
        return igualesAlMinimo(1);
    }
    
    protected int igualesAlMinimo(int i){
        //Si la posición i sobrepasa la talla o si el elemento
        //del array en la posición i es más grande que el de la posición 1
        if(i>talla || elArray[i].compareTo(elArray[1])>0){ return 0; } 
        //Si no pasa eso, es menor, sumo 1 y recursión con el hijo izquierdo y con el derecho
        return 1 + igualesAlMinimo(2*i) + igualesAlMinimo(2*i+1); 
    }
    
    /**PROBLEMA 11: DEVOLVER TRUE SI LOS ELEMENTOS DEL ARRAY V CUMPLEN LA PROPIEDAD DE ORDENACIÓN*/
    public <E extends Comparable<E>> boolean esHeap(E[] v){
        for(int i = 1; i<talla/2; i++){
            if(v[i].compareTo(v[2*i])>0 || v[i].compareTo(v[2*i+1])>0) { 
                //si el padre es mayor que el hijo izquierdo o el derecho
                return false;
            }
        }
        return true;
    }
    
    /**VARIANTE DE esHeap: CONTAR EL NÚMERO DE ELEMENTOS QUE INCUMPLEN LA PROPIEDAD DE ORDEN*/
    public static <E extends Comparable<E>> int esHeapContar(E[] v){
        int res = 0;
        for(int i = 1; i < v.length; i++){
            if(v[i].compareTo(v[(i-1)/2])<0){ res++;}
        }
        return res;
    }
  
    
    /**PROBLEMA 12: DEVOLVER EL NÚMERO DE ELEMENTOS MENORES QUE E (RECURSIÓN)*/
    public int menoresQue(E e){
        return menoresQue(e, 1);
    }
    
    protected int menoresQue(E e, int i){
        if(i>talla || elArray[i].compareTo(e)>= 0) { //si i sobrepasa la talla o i es mayor que e 
            return 0;
        }
        return 1 + menoresQue(e, i*2) + menoresQue(e, 2*i+1);
    }
    
    /**PROBLEMA 13: ELIMINAR LA PRIMERA HOJA DEL MONTÍCULO*/
    public E eliminar1Hoja(){
        int k = talla/2+1; //Obtengo la primera hoja
        return eliminar(k); //PROBLEMA 9
    }
    
    /**PROBLEMA 15: ORDENAR ASCENDENTEMENTE UN ARRAY V USANDO UN MONTÍCULO*/
    public static <E extends Comparable<E>> void heapSort(E[] v){
        for(int i = v.length/2; i>=0; i--) { //Empiezo desde mitad para atrás (hacia el principio)
            hundirMax(v, i, v.length);
        }
        for(int i = v.length-1; i>0; i--){
            intercambiar(v,0,i);
            hundirMax(v,0,i);
        }
    }
    private static <E extends Comparable <E>> void intercambiar(E[] v, int x, int y){
        E elX = v[x];
        v[x] = v[y];
        v[y] = elX;
    }
    
    private static <E extends Comparable <E>> void hundirMax(E[] v, int hueco, int fin){
        int hijo = hueco*2+1; //Este es el hijo derecho
        E aux = v[hueco];
        boolean esHeap = false;
        while(hijo<fin && !esHeap){ //Mientras que hijo no pase el final y esHeap sea false
            if( hijo!=fin-1 && v[hijo+1].compareTo(v[hijo])>0) { hijo++;}
            if(v[hijo].compareTo(aux)>0){ //si hijo>aux
                v[hueco] = v[hijo];
                hueco = hijo;
                hijo = hueco*2+1;
            } else { esHeap = true;}
        }
        v[hueco] = aux;
    }
    
    /**EXTRA: DEVOLVER EL NÚMERO DE NODOS CUYO HIJO IZQUIERDO ES MAYOR QUE EL DERECHO*/
    public int mayorIzqQueDer(){
        int res=0;
        for(int i = 1; i <= talla/2; i++){
            if(2*i+1<=talla && elArray[2*i].compareTo(elArray[2*i+1])>0) { res++;}
        }
        return res;
    }
}