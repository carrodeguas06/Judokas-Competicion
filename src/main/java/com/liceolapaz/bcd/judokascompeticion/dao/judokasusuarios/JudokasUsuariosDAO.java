package com.liceolapaz.bcd.judokascompeticion.dao.judokasusuarios;

import pojos.Judoka;
import pojos.User;

import java.util.List;

public interface JudokasUsuariosDAO {
    List<Judoka> obtenerJudokas(User user);
    void insertarJudokas(User user,Judoka judoka);
    void eliminarJudokas(User user,Judoka judoka);
    long cantidadJudokas(User user);
}
