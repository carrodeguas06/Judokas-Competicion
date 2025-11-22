package com.liceolapaz.bcd.judokascompeticion.dao.judokas;

import com.liceolapaz.bcd.judokascompeticion.database.DatabaseConnection;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pojos.Judoka;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JudokaDAOImpl implements JudokasDAO{
    @Override
    public List<Judoka> obtenerJudokas() {
        List<Judoka> judokas = new ArrayList<>();

        Session session = DatabaseConnection.getSessionFactory().openSession();
            Query query = session.createQuery("FROM Judoka j", Judoka.class);
            judokas = query.getResultList();

        return judokas;
    }

    @Override
    public void anhadirJudoka(Judoka judoka) {
        Session session = DatabaseConnection.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.persist(judoka);
        session.getTransaction().commit();
    }

    @Override
    public void eliminarJudoka(Judoka judoka) {
        Session session = DatabaseConnection.getSessionFactory().openSession();
        Query query = session.createQuery("DELETE from Judoka j where id = :id",  Judoka.class);
        query.setParameter("id", judoka.getId());
        query.executeUpdate();
    }

    @Override
    public void editarJudoka(Judoka judoka) {
        Session session = DatabaseConnection.getSessionFactory().openSession();
        Query query = session.createQuery("UPDATE Judoka j SET j.name = :name, j.lastName = :lastName, j.belt = :belt, j.country = :country, j.especialTecnique = :special_tecnique WHERE j.id = :id", Judoka.class);
        query.setParameter("name", judoka.getName());
        query.setParameter("lastName", judoka.getLastName());
        query.setParameter("belt", judoka.getBelt());
        query.setParameter("country", judoka.getCountry());
        query.setParameter("special_tecnique", judoka.getEspecialTecnique());
        query.setParameter("id", judoka.getId());
        query.executeUpdate();
    }

    @Override
    public Judoka obtenerJudoka(int id) {
        Judoka judoka = null;
        Session session = DatabaseConnection.getSessionFactory().openSession();
        Query query = session.createQuery("FROM Judoka j WHERE j.id = :id", Judoka.class);
        query.setParameter("id", id);
        judoka = (Judoka) query.getSingleResult();

        return judoka;
    }
}
