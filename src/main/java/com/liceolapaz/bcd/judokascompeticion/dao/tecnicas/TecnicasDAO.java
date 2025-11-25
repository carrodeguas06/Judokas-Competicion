package com.liceolapaz.bcd.judokascompeticion.dao.tecnicas;

import pojos.Tecnique;

import java.util.List;

public interface TecnicasDAO {
    List <String> getNombreTecnicas();
    Tecnique getTecnica(String Nombre);
}
