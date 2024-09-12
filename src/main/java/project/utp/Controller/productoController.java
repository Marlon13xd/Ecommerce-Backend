package project.utp.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.utp.modelo.Producto;
import project.utp.service.productoService;

import java.util.List;

@RestController
@RequestMapping("/producto")
@CrossOrigin("*")
public class productoController {

    @Autowired
    private productoService productocontroller;

    public productoController(productoService productocontroller) {
        this.productocontroller = productocontroller;
    }

    @GetMapping("/listar")
    public List<Producto> mostrarProducto(){return productocontroller.listar();}

    @GetMapping("/{id_producto}")
    public Producto buscarId(@PathVariable long id_producto){return productocontroller.buscarId(id_producto);}

    @PostMapping
    public ResponseEntity<String> guardarProducto(@RequestBody Producto producto){
        productocontroller.agregar(producto);
        return ResponseEntity.ok().body("´Producto registrado exitosamente");
    }

    @DeleteMapping("/{id_producto}")
    public ResponseEntity<String> eliminarProducto(@PathVariable long id_producto){
    productocontroller.eliminar(id_producto);
    return ResponseEntity.ok().body("Producto desahabilitado exitosamente");
     }
}
