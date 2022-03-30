package com.dh.aula21.controller;

import com.dh.aula21.model.Dentista;
import com.dh.aula21.repository.impl.DentistaDaoH2;
import com.dh.aula21.service.DentistaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/dentistas")
@RestController
public class DentistaController {

    private DentistaService dentistaService = new DentistaService(new DentistaDaoH2());

    @PostMapping
    public ResponseEntity<Dentista> save(@RequestBody Dentista dentista){
       return ResponseEntity.ok(dentistaService.salvar(dentista));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dentista> findById(@PathVariable Integer id){
        return ResponseEntity.ok(dentistaService.getById(id).get());
    }

    @GetMapping
    public ResponseEntity<List<Dentista>> findAll(){
        return ResponseEntity.ok(dentistaService.buscarTodos());
    }

    @PutMapping
    public ResponseEntity<Dentista> update(@RequestBody Dentista dentista){
        ResponseEntity<Dentista> response = null;
        if(dentista.getId()!=null && dentistaService.getById(dentista.getId()).isPresent()){
            response = ResponseEntity.ok(dentistaService.atualizar(dentista));
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        ResponseEntity<String> response = null;
        if(dentistaService.getById(id).isPresent()){
            dentistaService.excluir(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Dentista excluido");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;

    }



}
