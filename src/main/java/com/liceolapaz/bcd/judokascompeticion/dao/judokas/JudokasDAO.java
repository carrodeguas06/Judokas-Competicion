package com.liceolapaz.bcd.judokascompeticion.dao.judokas;

import com.liceolapaz.bcd.judokascompeticion.pojo.Judoka;

import java.util.List;

public interface JudokasDAO {
    List<Judoka> obtenerJudokas();
    void anhadirJudoka(Judoka judoka);
    void eliminarJudoka(Judoka judoka);
    void editarJudoka(Judoka judoka);
    Judoka obtenerJudoka(int id);
}
