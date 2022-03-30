package com.dh.aula21.controller;

import com.dh.aula21.model.Usuario;
import com.dh.aula21.repository.impl.UsuarioDaoH2;
import com.dh.aula21.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/usuarios")
@RestController
public class UsuarioController {

    private UsuarioService usuarioService = new UsuarioService(new UsuarioDaoH2());

    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.salvar(usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Integer id){
        return ResponseEntity.ok(usuarioService.getById(id).get());
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll(){
        return ResponseEntity.ok(usuarioService.buscarTodos());
    }

    @PutMapping
    public ResponseEntity<Usuario> update(@RequestBody Usuario usuario){
        ResponseEntity<Usuario> response = null;
        if(usuario.getId()!=null && usuarioService.getById(usuario.getId()).isPresent()){
            response = ResponseEntity.ok(usuarioService.atualizar(usuario));
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        ResponseEntity<String> response = null;
        if(usuarioService.getById(id).isPresent()){
            usuarioService.excluir(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuario excluido");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;

    }


}
