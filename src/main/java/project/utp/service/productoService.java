package project.utp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.utp.Dao.productoDao;
import project.utp.modelo.Producto;

import java.util.List;

@Service
public class productoService {

    @Autowired
    private productoDao productoservice;

    public List<Producto> listar(){return productoservice.listar();}

    public Producto buscarId(long id_producto){return productoservice.buscarId(id_producto);}

    public boolean agregar (Producto producto){return productoservice.agregar(producto);}

    public boolean eliminar (long id_producto){return productoservice.eliminar(id_producto);}
}
