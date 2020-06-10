package librerias.estructurasDeDatos.grafos;

import librerias.estructurasDeDatos.modelos.*;
import librerias.estructurasDeDatos.lineales.*;
import librerias.estructurasDeDatos.jerarquicos.*;

public class Tramo {
    private int pueblo1, pueblo2;
    private String fecha;
    
    public Tramo(int p1, int p2, String f) {
        pueblo1 = p1;
        pueblo2 = p2;
        fecha = f;    
    }
    
    public int getPueblo1() {return pueblo1;}
    
    public int getPueblo2() {return pueblo2;}
    
    public String getFecha() {return fecha;}
    
    public static String sePuede(int x, int y, ListaConPI<Tramo> l) {
        UFSet ufs = new ForestUFSet(200);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            Tramo t = l.recuperar();
            int clase1 = ufs.find(t.getPueblo1());
            int clase2 = ufs.find(t.getPueblo2());
            if (clase1 != clase2) { 
                ufs.union(clase1, clase2); 
            }
            if (ufs.find(x) == ufs.find(y)) {
                return t.getFecha(); 
            }
        }
        return null;
    }
}