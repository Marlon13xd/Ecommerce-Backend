package project.utp.Dao;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project.utp.modelo.Categoria;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@Repository
public class categoriaDao implements Icategoria{

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Categoria> listar() {
        String sql="SELECT * FROM categoria WHERE estado=1";
        return jdbcTemplate.query(sql, new categoriaMapper());
    }

    @Override
    public Categoria buscarId(long id_categoria) {
        String sql="SELECT * FROM categoria WHERE id_categoria=? AND estado= 1";
        return jdbcTemplate.queryForObject(sql,new Object[]{id_categoria}, new categoriaMapper());
    }

    @Override
    public int agregar(Categoria objeto) {
        String sql="INSERT INTO categoria (nombre) VALUES (?)";
        return jdbcTemplate.update(sql,objeto.getNombre());
    }

    @Override
    public int eliminar(long id_categoria) {
        String sql="UPDATE categoria SET estado=0 WHERE id_categoria=? AND estado=1";
        return jdbcTemplate.update(sql,id_categoria);
    }


    private final static class categoriaMapper implements RowMapper<Categoria>{

        @Override
        public Categoria mapRow(ResultSet rs, int rowNum) throws SQLException {
            Categoria categoria = new Categoria();
            categoria.setId_categoria(rs.getLong("id_categoria"));
            categoria.setNombre(rs.getString("nombre"));
            categoria.setEstado(rs.getBoolean("estado"));
            categoria.setDescripcion(rs.getString("descripcion"));
            return categoria;
        }
    }
}
