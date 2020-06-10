package ejemplos.tema1;


/**
 * Write a description of class Comida here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Comida<E extends Comparable <E>>{
    private double calorias;
    private int minutos;
    
    public Comida(double c, int m){
        calorias = c;
        minutos = m;
    }
    
    public int compareTo(Comida o){
        if(this.calorias == o.calorias){
            if(this.minutos<o.minutos) { return -1;}
            else if(this.minutos > o.minutos){ return 1;}
            return 0;
        } else{
            if(this.calorias<o.calorias){return -1;}
            else{ return 1;}
        }
    }
}
