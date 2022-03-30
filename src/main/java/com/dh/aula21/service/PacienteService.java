package com.dh.aula21.service;

import com.dh.aula21.repository.IDao;
import com.dh.aula21.model.Paciente;

import java.util.List;
import java.util.Optional;

public class PacienteService {

    private IDao<Paciente> pacienteIDao;

    public PacienteService(IDao<Paciente> pacienteIDao) {
        this.pacienteIDao = pacienteIDao;
    }

    public Paciente salvar(Paciente paciente){
        pacienteIDao.salvar(paciente);
        return paciente;
    }

    public void excluir(Integer id){
        pacienteIDao.excluir(id);
    }

    public List<Paciente> buscarTodos(){
        return pacienteIDao.buscarTodos();
    }

    public Optional<Paciente> getById(Integer id){ return pacienteIDao.getById(id); }

    public Paciente atualizar(Paciente paciente){
        pacienteIDao.atualizar(paciente);
        return paciente;
    }
}
