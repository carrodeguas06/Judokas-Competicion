package com.liceolapaz.bcd.judokascompeticion.dao.judokasusuarios;

import com.liceolapaz.bcd.judokascompeticion.database.DatabaseConnection;
import jakarta.persistence.Query;
import org.hibernate.Session;
import pojos.Judoka;
import pojos.JudokaUser;
import pojos.User;

import java.util.List;

public class JudokasUsuariosDAOImpl implements JudokasUsuariosDAO{
    @Override
    public List<Judoka> obtenerJudokas(User user) {
        Session session = DatabaseConnection.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT ju.idjudoka FROM JudokaUser ju WHERE ju.idusers.id = :userId", Judoka.class);

        query.setParameter("userId", user.getId());
        return query.getResultList();
    }

    @Override
    public void insertarJudokas(User user, Judoka judoka) {
        JudokaUser asociacion = new pojos.JudokaUser();
        asociacion.setIdusers(user);
        asociacion.setIdjudoka(judoka);

        Session session = DatabaseConnection.getSessionFactory().openSession();

        session.beginTransaction();
        session.persist(asociacion);
        session.getTransaction().commit();
    }

    @Override
    public void eliminarJudokas(User user, Judoka judoka) {
        Session session = DatabaseConnection.getSessionFactory().openSession();
        Query query = session.createQuery("FROM JudokaUser ju WHERE ju.idusers = :userId AND ju.idjudoka = :judokaId", pojos.JudokaUser.class);
        query.setParameter("userId", user);
        query.setParameter("judokaId", judoka);
    }

    @Override
    public long cantidadJudokas(User user) {

        Session session = DatabaseConnection.getSessionFactory().openSession();
        Long count = 0L;

        String hql = "SELECT count(j) FROM JudokaUser j WHERE j.idusers.id = :userId";

        Query query = session.createQuery(hql, Long.class);

        query.setParameter("userId", user.getId());

        count = (Long) query.getSingleResult();

        return count;
    }
}
