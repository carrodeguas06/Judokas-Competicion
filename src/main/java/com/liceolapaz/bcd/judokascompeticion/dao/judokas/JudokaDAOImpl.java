package com.liceolapaz.bcd.judokascompeticion.dao.judokas;

import com.liceolapaz.bcd.judokascompeticion.database.DatabaseConnection;
import jakarta.persistence.Query;
import org.hibernate.Session;
import pojos.Judoka;

import java.util.List;
import java.util.ArrayList;

public class JudokaDAOImpl implements JudokasDAO {
    @Override
    public List obtenerJudokas() {
        Session session = DatabaseConnection.getSessionFactory().openSession();
        Query query = session.createQuery("FROM Judoka j", Judoka.class);
        return query.getResultList();
    }

    @Override
    public Judoka obtenerJudoka(int id) {
        Judoka judoka = null;
        Session session = DatabaseConnection.getSessionFactory().openSession();

        try {
            Query query = session.createQuery("FROM Judoka j WHERE j.id = :id", Judoka.class);
            query.setParameter("id", id);
            judoka = (Judoka) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("No se puede encontrar judoka");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return judoka;
    }

    @Override
    public List obtenerJudokas(String nombre) {
        Session session = DatabaseConnection.getSessionFactory().openSession();
        Query query = session.createQuery("FROM Judoka j WHERE LOWER(j.name) LIKE :search OR LOWER(j.lastName) LIKE :search", Judoka.class);
        query.setParameter("search","%" + nombre.toLowerCase() + "%");

        return query.getResultList();
    }

    @Override
    public void anhadirJudoka(Judoka judoka) {
        Session session = DatabaseConnection.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            session.persist(judoka);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println("No se pudo guardar el judoka");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void eliminarJudoka(Judoka judoka) {
        if (judoka == null || judoka.getId() == null) {
            System.err.println("Error: El objeto Judoka o su ID es nulo.");
            return;
        }

        Session session = DatabaseConnection.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            Integer judokaId = judoka.getId();

            Query query1 = session.createQuery("DELETE FROM ResultadosJudoka r WHERE r.judoka.id= :judokaId");
            query1.setParameter("judokaId", judokaId);
            query1.executeUpdate();


            Query query3 = session.createQuery("DELETE FROM JudokaUser r WHERE r.idjudoka.id = :judokaId");
            query3.setParameter("judokaId", judokaId);
            query3.executeUpdate();


            Query query2 = session.createQuery("DELETE FROM Judoka j WHERE j.id = :judokaId");
            query2.setParameter("judokaId", judokaId);
            query2.executeUpdate();
            session.getTransaction().commit();


        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.err.println("No se pudo eliminar el judoka.");

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void editarJudoka(Judoka judoka) {
        Session session = DatabaseConnection.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            Query query = session.createQuery("UPDATE Judoka j SET j.name = :name, j.lastName = :lastName, j.belt = :belt, j.country = :country, j.especialTecnique = :special_tecnique WHERE j.id = :id");
            query.setParameter("name", judoka.getName());
            query.setParameter("lastName", judoka.getLastName());
            query.setParameter("belt", judoka.getBelt());
            query.setParameter("country", judoka.getCountry());
            query.setParameter("special_tecnique", judoka.getEspecialTecnique());
            query.setParameter("id", judoka.getId());
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}