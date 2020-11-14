package cl.inacap.carelesscovid.dao;

import java.util.List;

import cl.inacap.carelesscovid.dto.Paciente;

public interface PacientesDAO {
    Paciente save(Paciente p);
    List<Paciente> getAll();
}
