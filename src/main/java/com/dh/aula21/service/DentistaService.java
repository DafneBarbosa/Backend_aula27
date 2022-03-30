package com.dh.aula21.service;

import com.dh.aula21.repository.IDao;
import com.dh.aula21.model.Dentista;

import java.util.List;
import java.util.Optional;

public class DentistaService {

    private IDao<Dentista> dentistaIDao;

    public DentistaService(IDao<Dentista> dentistaIDao) {
        this.dentistaIDao = dentistaIDao;
    }

    public Dentista salvar(Dentista dentista){
        dentistaIDao.salvar(dentista);
        return dentista;
    }

    public void excluir(Integer id){
        dentistaIDao.excluir(id);
    }

    public List<Dentista> buscarTodos(){
        return dentistaIDao.buscarTodos();
    }

    public Optional<Dentista> getById(Integer id){ return dentistaIDao.getById(id); }

    public Dentista atualizar(Dentista dentista){
        dentistaIDao.atualizar(dentista);
        return dentista;
    }

}
