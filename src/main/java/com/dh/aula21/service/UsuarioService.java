package com.dh.aula21.service;

import com.dh.aula21.repository.IDao;
import com.dh.aula21.model.Usuario;

import java.util.List;
import java.util.Optional;

public class UsuarioService {

    private IDao<Usuario> usuarioIDao;

    public UsuarioService(IDao<Usuario> usuarioIDao) {
        this.usuarioIDao = usuarioIDao;
    }

    public Usuario salvar(Usuario usuario){
        usuarioIDao.salvar(usuario);
        return usuario;
    }

    public void excluir(Integer id){
        usuarioIDao.excluir(id);
    }

    public List<Usuario> buscarTodos(){
        return usuarioIDao.buscarTodos();
    }

    public Optional<Usuario> getById(Integer id){ return usuarioIDao.getById(id); }

    public Usuario atualizar(Usuario usuario){
        usuarioIDao.atualizar(usuario);
        return usuario;
    }
}
