package com.liceolapaz.bcd.judokascompeticion.dao.judokasusuarios;

import com.liceolapaz.bcd.judokascompeticion.database.DatabaseConnection;
import com.liceolapaz.bcd.judokascompeticion.navigation.SessionManager;
import jakarta.persistence.Query;
import org.hibernate.Session;
import pojos.User;

public class JudokasUsuariosDAOImpl implements JudokasUsuariosDAO{
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
