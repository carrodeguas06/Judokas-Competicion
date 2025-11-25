package com.liceolapaz.bcd.judokascompeticion.dao.judokas;

import pojos.Judoka;

import java.util.List;

public interface JudokasDAO {
    List<Judoka> obtenerJudokas();
    void anhadirJudoka(Judoka judoka);
    void eliminarJudoka(Judoka judoka);
    void editarJudoka(Judoka judoka);
    List<Judoka> obtenerJudokas(String nombre);
}
