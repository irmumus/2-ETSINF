package librerias.estructurasDeDatos.grafos;

import librerias.estructurasDeDatos.jerarquicos.*;
import librerias.estructurasDeDatos.lineales.*;
import librerias.estructurasDeDatos.modelos.*;

public class Voto {
    
    private int votante, votado;
    
    public Voto(int v, int w) { 
        votante = v; 
        votado = w; 
    }
    
    public int getVotante() { return votante; }
    
    public int getVotado() { return votado; }
    
    public static int votosDe(int candidato, ListaConPI<Voto> l) {
        ForestUFSet ufs = new ForestUFSet(l.talla());
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            Voto v = l.recuperar();
            int votado = v.getVotado();
            int votante = v.getVotante();
            int idVotado = ufs.find(votado);
            int idVotante = ufs.find(votante);
            if (idVotado != idVotante) {
                ufs.union(idVotante, idVotado);
            }
        }
        return ufs.cardinal(candidato);
    }
}