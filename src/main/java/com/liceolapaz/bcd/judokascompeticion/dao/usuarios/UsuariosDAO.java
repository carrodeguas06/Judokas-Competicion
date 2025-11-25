package com.liceolapaz.bcd.judokascompeticion.dao.usuarios;

import pojos.User;

import java.util.List;

public interface UsuariosDAO {
    List<User> obtenerUsuarios();
    User crearUsuario(User user);
    User editarUsuario(User user);
    List<User> buscarUsuario(String string);
    void setAdmin(User user);
}
