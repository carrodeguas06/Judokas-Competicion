package com.liceolapaz.bcd.judokascompeticion.database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DatabaseConnection {

    // Variable estática, pero empieza siendo NULL
    private static SessionFactory sessionFactory;

    // Constructor privado para que nadie haga "new DatabaseConnection()"
    private DatabaseConnection() { }

    /**
     * Método estático para obtener la sesión.
     * Crea la conexión SOLO si no existe todavía.
     */
    public static SessionFactory getSessionFactory() {
        // AQUÍ es donde sustituimos el bloque static.
        // Verificamos: ¿Ya existe la fábrica?
        if (sessionFactory == null) {
            try {
                System.out.println("--> Configurando Hibernate por primera vez...");

                // Si es null, la creamos ahora mismo
                sessionFactory = new Configuration().configure().buildSessionFactory();

                System.out.println("--> ¡Conectado!");
            } catch (Throwable ex) {
                System.err.println("--> ERROR al crear la SessionFactory: " + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        // Si ya existía, simplemente la devolvemos
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}