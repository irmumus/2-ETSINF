package librerias.estructurasDeDatos.modelos;

import librerias.excepciones.*;

public interface ListaConPIPlus<E> extends ListaConPI<E> {
    public boolean eliminarUltimo(E e);
    
    public boolean eliminarPrimero(E e);
    
    public boolean contiene(E e);
    
    public boolean eliminar(E e);
    
    public boolean eliminarTodos(E e);
    
    public void concatenar(ListaConPI<E> e);
}
