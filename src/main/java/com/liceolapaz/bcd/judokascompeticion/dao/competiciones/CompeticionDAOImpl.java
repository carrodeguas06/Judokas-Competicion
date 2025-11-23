package com.liceolapaz.bcd.judokascompeticion.dao.competiciones;

import com.liceolapaz.bcd.judokascompeticion.database.DatabaseConnection;
import jakarta.persistence.Query;
import org.hibernate.Session;
import pojos.Competition;

import java.util.List;

public class CompeticionDAOImpl implements CompeticionesDAO{
    @Override
    public void anhadirCompeticion(Competition competition) {
        Session session = DatabaseConnection.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.persist(competition);
        session.getTransaction().commit();
    }

    @Override
    public void eliminarCompeticion(Competition competition) {
        Session session = DatabaseConnection.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.remove(competition);
        session.getTransaction().commit();
    }

    @Override
    public void editarCompeticion(Competition competition) {
        Session session = DatabaseConnection.getSessionFactory().openSession();
        Query query = session.createQuery("update Competition c set c.name = :name, c.city= :city, c.country = :country, c.temp = :temp, c.type = :type where id = :id");
        query.setParameter("name", competition.getName());
        query.setParameter("city", competition.getCity());
        query.setParameter("country", competition.getCountry());
        query.setParameter("temp", competition.getTemp());
        query.setParameter("type", competition.getType());
        query.setParameter("id", competition.getId());
        query.executeUpdate();
    }

    @Override
    public List<Competition> obtenerCompeticiones() {
        Session session = DatabaseConnection.getSessionFactory().openSession();
        Query query = session.createQuery("from Competition c", Competition.class);
        return query.getResultList();
    }

    @Override
    public List<Competition> obtenerCompeticion(String nombre) {
        Session session = DatabaseConnection.getSessionFactory().openSession();
        Query query = session.createQuery("from Competition c where c.name = :nombre", Competition.class);
        query.setParameter("nombre", "%"+ nombre + "%");
        return query.getResultList();
    }

    public CompeticionDAOImpl() {
    }
}
