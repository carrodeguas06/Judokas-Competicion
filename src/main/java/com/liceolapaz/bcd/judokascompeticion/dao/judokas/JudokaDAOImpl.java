package com.liceolapaz.bcd.judokascompeticion.dao.judokas;

import com.liceolapaz.bcd.judokascompeticion.database.DatabaseConnection;
import jakarta.persistence.Query;
import org.hibernate.Session;
import pojos.Judoka;

import java.util.List;
import java.util.ArrayList;

public class JudokaDAOImpl implements JudokasDAO {
    @Override
    public List<Judoka> obtenerJudokas() {
        List<Judoka> judokas = new ArrayList<>();
        Session session = DatabaseConnection.getSessionFactory().openSession();

        try {
            Query query = session.createQuery("FROM Judoka j", Judoka.class);
            judokas = query.getResultList();
        } catch (Exception e) {
            System.out.println("No se pueden encontrar judokas");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return judokas;
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
        Session session = DatabaseConnection.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            Query query = session.createQuery("DELETE from Judoka j where id = :id");
            query.setParameter("id", judoka.getId());
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println("No se pudo eliminar el judoka");
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