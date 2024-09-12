package project.utp.Dao;

import project.utp.modelo.Categoria;
import project.utp.modelo.Producto;

import java.util.List;

public interface Iproducto {
    public List<Producto> listar();
    Producto buscarId(long id_producto);
    List<Producto> buscarporCategoria(Categoria categoria);
    boolean agregar (Producto objeto);
    boolean eliminar (long id_producto);

}
