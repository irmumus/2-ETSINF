package ejemplos.tema3;

public class Matricula {   
    private int numeros; 
    private String letras;  

    public Matricula(int n, String l) { numeros = n; letras = l; }   

    public int getNumeros() { return numeros; }   

    public String getLetras() { return letras; }   

    public String toString() { return "Matricula " + numeros + " " + letras; }
    
    public boolean equals (Object o) {
        return o instanceof Matricula
                && letras.equals(((Matricula) o).letras)
                && numeros == ((Matricula) o).numeros;
    }
    
    public int hashCode() {
        return (letras + numeros).hashCode();
    }

}