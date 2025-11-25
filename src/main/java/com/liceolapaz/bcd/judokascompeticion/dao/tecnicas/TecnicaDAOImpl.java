package com.liceolapaz.bcd.judokascompeticion.dao.tecnicas;

import com.liceolapaz.bcd.judokascompeticion.database.DatabaseConnection;
import jakarta.persistence.Query;
import org.hibernate.Session;
import pojos.Tecnique;

import java.util.List;

public class TecnicaDAOImpl implements TecnicasDAO{

    public TecnicaDAOImpl() {
    }

    @Override
    public List<String> getNombreTecnicas() {
        Session session = DatabaseConnection.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT t.name from Tecnique t", String.class);
        return query.getResultList();
    }

    @Override
    public Tecnique getTecnica(String Nombre) {
        Session session = DatabaseConnection.getSessionFactory().openSession();
        Query query =  session.createQuery("FROM Tecnique WHERE name = :name", Tecnique.class);
        query.setParameter("name", Nombre);
        return (Tecnique) query.getSingleResult();
    }

}
