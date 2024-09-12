package project.utp.Dao;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project.utp.modelo.Categoria;
import project.utp.modelo.Producto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@Repository
public class productoDao implements Iproducto{

    @Autowired
    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<Producto> listar() {
        String sql="SELECT * FROM producto WHERE estado=1";
        return jdbcTemplate.query(sql,new productoMapper());
    }

    @Override
    public Producto buscarId(long id_producto) {
        String sql="SELECT * FROM producto WHERE id_producto=? AND estado=1";
        return jdbcTemplate.queryForObject(sql,new Object[]{id_producto},new productoMapper());
    }

    @Override
    public List<Producto> buscarporCategoria(Categoria categoria) {
        return null;
    }


    @Override
    public boolean agregar (Producto objeto) {
        String sql = "INSERT INTO producto (nombre, descripcion, precio, imagen_url, categoria_id) VALUES (?, ?, ?, ?, ?)";
        int agregar=jdbcTemplate.update(sql, objeto.getNombre(),objeto.getDescripcion(), objeto.getPrecio(), objeto.getImagen_url(), objeto.getCategoria().getId_categoria());
        if(agregar>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean eliminar(long id_producto) {
        String sql ="UPDATE producto SET estado=0 WHERE id_producto=? AND estado =1";
        int eliminar=jdbcTemplate.update(sql,id_producto);
        if (eliminar>0){
            return true;
        }
        return false;
    }


    private static final class productoMapper implements RowMapper<Producto>{

        @Override
        public Producto mapRow(ResultSet rs, int rowNum) throws SQLException {
            Producto producto = new Producto();
            producto.setId_producto(rs.getLong("id_producto"));
            producto.setNombre(rs.getString("nombre"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setPrecio(rs.getDouble("precio"));
            producto.setImagen_url(rs.getString("imagen_url"));
            producto.setEstado(rs.getBoolean("estado"));

            Categoria categoria = new Categoria();
            categoria.setId_categoria(rs.getLong("categoria_id"));
           // categoria.setNombre(rs.getString("categoria_nombre"));
            producto.setCategoria(categoria);
            return producto;

        }
    }

}
