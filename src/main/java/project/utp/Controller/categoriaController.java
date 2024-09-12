package project.utp.Controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.utp.modelo.Categoria;
import project.utp.service.categoriaService;

import java.nio.file.LinkOption;
import java.util.List;

@RestController
@RequestMapping("/categoria")
public class categoriaController {

    @Autowired
    private  categoriaService categoriacontroller;

    public categoriaController(categoriaService categoriacontroller) {
        this.categoriacontroller = categoriacontroller;
    }
    @GetMapping("/listar")
    public List<Categoria> mostarCategoria(){return categoriacontroller.listar();}

    @GetMapping("/buscarId/{id_categoria}")
    public Categoria buscarId(@PathVariable long id_categoria){return categoriacontroller.buscarId(id_categoria);}

    @PostMapping
    public int agregarCategoria(@RequestBody Categoria categoria){return categoriacontroller.agregar(categoria);}

    @DeleteMapping("/{id_categoria}")
    public int eliminarCategoria (@PathVariable long id_categoria){return categoriacontroller.eliminar(id_categoria);}
}
