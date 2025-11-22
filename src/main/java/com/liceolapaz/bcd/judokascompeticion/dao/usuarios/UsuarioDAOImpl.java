package com.liceolapaz.bcd.judokascompeticion.dao.usuarios;

import com.liceolapaz.bcd.judokascompeticion.pojo.Usuario;
import com.liceolapaz.bcd.judokascompeticion.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuariosDAO {

    @Override
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                ) {
            while (rs.next())
            {
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("last_name"),
                        rs.getString("nickname"),
                        rs.getString("password")
                );
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }

    @Override
    public Usuario obtenerUsuario(int id) {
        return null;
    }
}
