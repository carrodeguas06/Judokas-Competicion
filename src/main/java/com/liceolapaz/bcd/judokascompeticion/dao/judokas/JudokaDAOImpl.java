package com.liceolapaz.bcd.judokascompeticion.dao.judokas;

import com.liceolapaz.bcd.judokascompeticion.database.DatabaseConnection;
import com.liceolapaz.bcd.judokascompeticion.pojo.Judoka;
import com.liceolapaz.bcd.judokascompeticion.pojo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JudokaDAOImpl implements JudokasDAO{
    @Override
    public List<Judoka> obtenerJudokas() {
        List<Judoka> judokas = new ArrayList<>();
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM judokas");
                ResultSet rs = ps.executeQuery()
                ){
            while (rs.next())
            {
                Judoka judoka = new Judoka(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("last_name"),
                        rs.getString("country"),
                        rs.getString("belt"),
                        rs.getInt("special_tecnique")
                );
                judokas.add(judoka);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener judokas");
        }
        return judokas;
    }

    @Override
    public void anhadirJudoka(Judoka judoka) {

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO judokas (id, name, last_name, country, belt, special_tecnique) VALUES (?, ?, ?, ?, ?,?)");
                ) {
            ps.setInt(1,judoka.getId());
            ps.setString(2, judoka.getNombre());
            ps.setString(3, judoka.getApellido());
            ps.setString(4, judoka.getPais());
            ps.setString(5, judoka.getCinturon());
            ps.setInt(6, judoka.getTecnica_especial());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al añadir judoka");
        }

    }

    @Override
    public void eliminarJudoka(Judoka judoka) {
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM judokas WHERE id=?");
                ){
            ps.setInt(1,judoka.getId());
        } catch (SQLException e) {
            System.out.println("Error al eliminar judoka");
        }
    }

    @Override
    public void editarJudoka(Judoka judoka) {
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("UPDATE judokas SET name=?, last_name=?, country=?, belt=?, special_tecnique=? WHERE id=?");
        ){
            ps.setString(1, judoka.getNombre());
            ps.setString(2, judoka.getApellido());
            ps.setString(3, judoka.getPais());
            ps.setString(4, judoka.getCinturon());
            ps.setInt(5, judoka.getTecnica_especial());
            ps.setInt(6,judoka.getId());
        } catch (SQLException e) {
            System.out.println("Error al editar judoka");
        }
    }

    @Override
    public Judoka obtenerJudoka(int id) {
        Judoka judoka = null;
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("select * from judokas where id=?");
        ){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            judoka = new Judoka(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("last_name"),
                    rs.getString("country"),
                    rs.getString("belt"),
                    rs.getInt("special_tecnique")
            );
        } catch (SQLException e) {
            System.out.println("Error al obtener judoka");
        }
        return judoka;
    }
}
