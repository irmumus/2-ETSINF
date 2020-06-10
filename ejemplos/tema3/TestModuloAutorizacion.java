package ejemplos.tema3;
import librerias.excepciones.*;
import java.util.*;

public class TestModuloAutorizacion {

    public static void main(String[] args) throws Exception {
        ModuloAutorizacion mA = new ModuloAutorizacion(10);
        Scanner tec = new Scanner(System.in).useLocale(Locale.US);
        String usuario;
        String contra;
        System.out.print("\nNombre de usuario: ");
        usuario = tec.nextLine();
        System.out.print("\nContraseña: ");
        contra = tec.nextLine();
        try{
            mA.registrarUsuario(usuario,contra);
        } catch (UsuarioExistente e){
            System.out.println("Usuario " + usuario + " ya existente.");
        }
        //    mA.registrarUsuario( ...
        
        Thread.sleep(8000);
        if(mA.estaAutorizado(usuario, contra)){
            System.out.println("Usuario autorizado");
        } else {
            System.out.println("Usuario NO autorizado");
        }
        
        System.out.println("Fecha de acceso: " + 
            mA.fechaAcceso(usuario, contra));
        //System.out.println(mA.fechaAcceso( ...
    }
}