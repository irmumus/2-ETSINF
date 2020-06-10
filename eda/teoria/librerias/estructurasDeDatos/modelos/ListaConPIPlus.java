package librerias.estructurasDeDatos.modelos;

import librerias.excepciones.*;

public interface ListaConPIPlus<E> extends ListaConPI<E> {
    public boolean eliminarUltimo(E e);
}