package ejemplos.tema3;

import librerias.estructurasDeDatos.modelos.*; 
import librerias.estructurasDeDatos.deDispersion.*;
import librerias.excepciones.*;
import java.util.Date;

public class ModuloAutorizacion {
    //private Map<Usuario, Date> m;
    private TablaHash<Usuario, Date> m;
    public ModuloAutorizacion(int n) {
        m = new TablaHash<Usuario, Date>(n);
    }
    
    public Date fechaAcceso(String nombre, String pwd) {
        Usuario usuario = new Usuario(nombre, pwd);
        return m.recuperar(usuario);
    }
    
    public void registrarUsuario(String nombre, String pwd) 
        throws UsuarioExistente {
        Usuario aRegistrar = new Usuario(nombre, pwd);
        Date fecha = new Date();
        m.insertar(aRegistrar, fecha);
    }
    
    public boolean estaAutorizado(String nombre, String pwd) {
        boolean res = false;
        Usuario auto = new Usuario(nombre, pwd);
        m.claves().inicio();
        while(!m.claves().esFin()){
            if(m.claves().recuperar().equals(auto)) {
                res = true;
            }
        }
        return res;
    }
}