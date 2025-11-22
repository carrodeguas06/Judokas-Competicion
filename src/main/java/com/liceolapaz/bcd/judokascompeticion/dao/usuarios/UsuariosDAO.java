package com.liceolapaz.bcd.judokascompeticion.dao.usuarios;

import com.liceolapaz.bcd.judokascompeticion.pojo.Usuario;

import java.util.List;

public interface UsuariosDAO {
    List<Usuario> obtenerUsuarios();
    Usuario obtenerUsuario(int id);
}
