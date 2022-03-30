package com.dh.aula21.service;

import com.dh.aula21.model.Consulta;
import com.dh.aula21.repository.IDao;

import java.util.List;
import java.util.Optional;

public class ConsultaService {

    private IDao<Consulta> consultaIDao;

    public ConsultaService(IDao<Consulta> consultaIDao) {
        this.consultaIDao = consultaIDao;
    }

    public Consulta salvar(Consulta consulta){
        consultaIDao.salvar(consulta);
        return consulta;
    }

    public void excluir(Integer id){
        consultaIDao.excluir(id);
    }

    public List<Consulta> buscarTodos(){
        return consultaIDao.buscarTodos();
    }

    public Optional<Consulta> getById(Integer id){ return consultaIDao.getById(id); }

    public Consulta atualizar(Consulta consulta){
        consultaIDao.atualizar(consulta);
        return consulta;
    }
}
