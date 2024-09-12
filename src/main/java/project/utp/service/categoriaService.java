package project.utp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import project.utp.Dao.categoriaDao;
import project.utp.modelo.Categoria;
import project.utp.modelo.Producto;

import java.util.List;

@Service
public class categoriaService {

    @Autowired
    private categoriaDao categoriaservice;

    public List<Categoria> listar(){return categoriaservice.listar();}

    public Categoria buscarId( long id_categoria){return categoriaservice.buscarId(id_categoria);}

    public int agregar(Categoria categoria){return categoriaservice.agregar(categoria);}

    public int eliminar(long id_categoria){return categoriaservice.eliminar(id_categoria);}
}
