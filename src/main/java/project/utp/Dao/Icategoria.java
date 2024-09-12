package project.utp.Dao;

import project.utp.modelo.Categoria;

import java.util.List;

public interface Icategoria {

    public List<Categoria> listar();
    Categoria buscarId (long id_categoria);
    public int agregar(Categoria objeto);
    int eliminar (long id_categoria);
}
