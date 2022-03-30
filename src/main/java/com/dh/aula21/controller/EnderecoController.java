package com.dh.aula21.controller;

import com.dh.aula21.model.Endereco;
import com.dh.aula21.repository.impl.EnderecoDaoH2;
import com.dh.aula21.service.EnderecoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/enderecos")
@RestController
public class EnderecoController {

    private EnderecoService enderecosService = new EnderecoService(new EnderecoDaoH2());

    @PostMapping
    public ResponseEntity<Endereco> save(@RequestBody Endereco endereco){
        return ResponseEntity.ok(enderecosService.salvar(endereco));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> findById(@PathVariable Integer id){
        return ResponseEntity.ok(enderecosService.getById(id).get());
    }

    @GetMapping
    public ResponseEntity<List<Endereco>> findAll(){
        return ResponseEntity.ok(enderecosService.buscarTodos());
    }

    @PutMapping
    public ResponseEntity<Endereco> update(@RequestBody Endereco endereco){
        ResponseEntity<Endereco> response = null;
        if(endereco.getId()!=null && enderecosService.getById(endereco.getId()).isPresent()){
            response = ResponseEntity.ok(enderecosService.atualizar(endereco));
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        ResponseEntity<String> response = null;
        if(enderecosService.getById(id).isPresent()){
            enderecosService.excluir(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Endereco excluido");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;

    }
}
