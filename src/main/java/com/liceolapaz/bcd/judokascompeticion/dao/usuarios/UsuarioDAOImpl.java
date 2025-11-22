package com.liceolapaz.bcd.judokascompeticion.dao.usuarios;

import com.liceolapaz.bcd.judokascompeticion.database.DatabaseConnection;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pojos.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuariosDAO {

    @Override
    public List<User> obtenerUsuarios() {
        List<User> usuarios = new ArrayList<>();
        Session session = DatabaseConnection.getSessionFactory().openSession();
        Query query = session.createQuery("from User", User.class);
        usuarios = query.getResultList();
        return usuarios;
    }

    @Override
    public User obtenerUsuario(int id) {
        User usuario = null;
        Session session = DatabaseConnection.getSessionFactory().openSession();
        Query query = session.createQuery("from User where id = :id", User.class);
        query.setParameter("id", id);
        usuario = (User) query.getSingleResult();
        return usuario;
    }
}
