package com.liceolapaz.bcd.judokascompeticion;

import com.liceolapaz.bcd.judokascompeticion.pojo.Usuario;

public class SessionManager {
    private static SessionManager instance;
    private Usuario usuario;

    private SessionManager(){}

    public static SessionManager getInstance()
    {
        if (instance == null)
        {
            instance = new SessionManager();
        }
        return instance;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario)
    {
        this.usuario = usuario;
    }

    public void clearSession()
    {
        this.usuario = null;
    }
}
