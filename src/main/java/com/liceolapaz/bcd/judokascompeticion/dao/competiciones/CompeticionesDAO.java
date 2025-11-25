package com.liceolapaz.bcd.judokascompeticion.dao.competiciones;

import pojos.Competition;

import java.util.List;

public interface CompeticionesDAO {
    void anhadirCompeticion(Competition competition);
    void eliminarCompeticion(Competition competition);
    void editarCompeticion(Competition competition);
    List<Competition> obtenerCompeticiones();
    List<Competition> obtenerCompeticion(String nombre);
}
