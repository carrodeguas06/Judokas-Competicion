package com.liceolapaz.bcd.judokascompeticion.navigation;

import pojos.User;

public class SessionManager {
    private static SessionManager instance;
    private User usuario;

    private SessionManager(){}

    public static SessionManager getInstance()
    {
        if (instance == null)
        {
            instance = new SessionManager();
        }
        return instance;
    }
    public User getUsuario() {
        return usuario;
    }
    public void setUsuario(User usuario)
    {
        this.usuario = usuario;
    }

    public void clearSession()
    {
        this.usuario = null;
    }
}
