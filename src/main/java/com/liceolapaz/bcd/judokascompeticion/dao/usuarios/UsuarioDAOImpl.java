package com.liceolapaz.bcd.judokascompeticion.dao.usuarios;

import com.liceolapaz.bcd.judokascompeticion.database.DatabaseConnection;
import jakarta.persistence.Query;
import org.hibernate.Session;
import pojos.User;

import java.util.List;

public class UsuarioDAOImpl implements UsuariosDAO {

    @Override
    public List<User> obtenerUsuarios() {
        List<User> usuarios = null;
        Session session = DatabaseConnection.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("from User", User.class);
            usuarios = query.getResultList();
        } catch (Exception e) {
            System.out.println("No se encontro los usuario");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return usuarios;
    }

    @Override
    public User obtenerUsuario(int id) {
        User usuario = null;
        Session session = DatabaseConnection.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("from User where id = :id", User.class);
            query.setParameter("id", id);
            usuario = (User) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("No se encontro el usuario");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return usuario;
    }
}