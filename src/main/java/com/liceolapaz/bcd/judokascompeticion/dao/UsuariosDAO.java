package com.liceolapaz.bcd.judokascompeticion.dao;

import com.liceolapaz.bcd.judokascompeticion.Usuario;

import java.util.List;

public interface UsuariosDAO {
    List<Usuario> obtenerUsuarios();
    Usuario obtenerUsuario(int id);
}
