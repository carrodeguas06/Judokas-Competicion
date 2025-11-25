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

    @Override
    public User crearUsuario(User user) {
        Session session = DatabaseConnection.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.persist(user);
        session.getTransaction().commit();
        return null;
    }

    @Override
    public User editarUsuario(User user) {
        Session session = DatabaseConnection.getSessionFactory().openSession();
        Query query = session.createQuery("update User u set u.name = :name, u.lastName = :lastName, u.nickname = :nickName , u.admin = :admin, u.password = :pass where u.id = :id", User.class);
        query.setParameter("name", user.getName());
        query.setParameter("lastName", user.getLastName());
        query.setParameter("nickName", user.getNickname());
        query.setParameter("admin", user.getAdmin());
        query.setParameter("pass", user.getPassword());
        query.setParameter("id", user.getId());
        query.executeUpdate();
        return null;
    }

    @Override
    public List<User> buscarUsuario(String string) {
        Session session = DatabaseConnection.getSessionFactory().openSession();
        Query query = session.createQuery("from User where name LIKE :name or lastName LIKE :name", User.class);
        query.setParameter("name", "%"+string+"%");

        return query.getResultList();
    }

    @Override
    public void setAdmin(User user) {
        Session session = DatabaseConnection.getSessionFactory().openSession();
        session.getTransaction().begin();
        Query query = session.createQuery("update User u set u.admin = :admin where u.id = :id");

        query.setParameter("admin", user.getAdmin());
        query.setParameter("id", user.getId());

        query.executeUpdate();
        session.getTransaction().commit();
    }

    public UsuarioDAOImpl() {
    }
}