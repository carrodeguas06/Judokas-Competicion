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
                        rs.getString("password"),
                        rs.getBoolean("admin")
                );
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener usuarios");
        }
        return usuarios;
    }

    @Override
    public Usuario obtenerUsuario(int id) {
        Usuario usuario = null;
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?");

        ) {
            ResultSet rs = stmt.executeQuery();

            usuario = new Usuario(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("last_name"),
                    rs.getString("nickname"),
                    rs.getString("password"),
                    rs.getBoolean("admin")
            );
            } catch (SQLException ex) {
            System.out.println("Error al obtener usuario");;
        }
        return usuario;
    }
}
