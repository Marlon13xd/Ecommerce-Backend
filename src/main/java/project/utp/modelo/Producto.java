package project.utp.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Producto {

    @Id
    private long id_producto;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String imagen_url;


    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    private boolean estado;

}

