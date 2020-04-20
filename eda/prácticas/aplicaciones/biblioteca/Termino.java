package aplicaciones.biblioteca;

/**
 * Termino: clase de la Clave del Map que representa un termino del 
 * Indice Analitico de una Biblioteca Digital.
 * 
 * Para que sobrescriba eficientemente los metodos equals y hashCode
 * de Object, un Termino TIENE UN valorHash que almacena el resultado 
 * de la PRIMERA invocacion al metodo hashCode de la clase sobre el. 
 * De esta forma, el valor hash asociado a un Termino...
 ** (a) solo se calcula una vez, independientemente del numero de veces 
 **     que el metodo hashCode se aplique sobre el;
 ** (b) se puede usar en el metodo equals de la clase para comprobar la
 **     igualdad de dos terminos SOLO cuando sus valores Hash sean iguales.
 *
 * Ademas, para poder evaluar distintos metodos hashCode, un Termino TIENE 
 * UNA baseHashCode que almacena la base en la que se calcula su valorHash
 * 
 * @author (EDA) 
 * @version (Curso 2019-2020)
 */

public class Termino {
    
    public static final int BASE_TRIVIAL = 1;  
    public static final int BASE_JAVA_LANG_STRING = 31;
    public static final int BASE_MCKENZIE = 4;
    
    protected String termino;
    // por eficiencia: "caching the Hash code" o "Hash cache"
    protected int valorHash;
    // para evaluar distintos metodos hashCode, con distintas bases
    protected int baseHashCode;
    
    /** Crea el Termino asociado a la palabra t y 
     *  le asocia la base a emplear en el metodo hashCode */
    public Termino(String t, int base) { 
        termino = new String(t); 
        baseHashCode = base;
        valorHash = 0;
        
    }
    
    /** Crea el Termino asociado a la palabra t segun el estandar de Java */
    public Termino(String t) { 
        this(t, BASE_JAVA_LANG_STRING); 
    }
    
    /** Devuelve el valor Hash de un (this) Termino de forma eficiente, i.e.
     *  al aplicar este metodo por PRIMERA vez sobre un Termino de longitud n
     *  (this.termino.length()), calcula su valor Hash como sigue: 
     *  this.valorHash =   this.termino.charAt[0] * this.baseHashCode^(n-1)  
     *                   + this.termino.charAt[1] * this.baseHashCode^(n-2) 
     *                   + ...
     *                   + this.termino.charAt[n-1]
     *  Si NO es la primera vez que se aplica el metodo, 
     *  devuelve this.valorHash 
     */
    public int hashCode() { 
        int res = this.valorHash;
        if (res != 0) { return res; }
        int n = this.termino.length();
        // COMPLETADO: calcular de res para this.termino en this.baseHashCode
        for(int i = 0; i < n-2; i++) {
            res = res * this.baseHashCode + this.termino.charAt(i) ;
        }
        //this.valorHash += this.termino.charAt(n-1);
        /* FIN COMPLETADO */
        this.valorHash = res;
        return res;
    }
   
    /** Comprueba si un (this) Termino es igual a otro de forma  
     *  eficiente, i.e. SOLO ejecuta el metodo equals de String 
     *  cuando los valores Hash de uno y otro Termino son iguales
     */
    public boolean equals(Object otro) {
        /* COMPLETADO */
        if(!(otro instanceof Termino)) { return false; }
        Termino t = (Termino) otro;
        return t.termino.equals(this.termino) &&
            t.baseHashCode == this.baseHashCode;
    }
    
    /** Devuelve un String que representa un Termino en cierto formato texto */
    public String toString() { return termino + " (" + valorHash + ")\n"; }
}

