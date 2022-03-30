package com.dh.aula21.controller;

import com.dh.aula21.model.Consulta;
import com.dh.aula21.repository.impl.ConsultaDaoH2;
import com.dh.aula21.service.ConsultaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/consultas")
@RestController
public class ConsultaController {

    private ConsultaService consultaService = new ConsultaService(new ConsultaDaoH2());

    @PostMapping
    public ResponseEntity<Consulta> save(@RequestBody Consulta consulta){
        return ResponseEntity.ok(consultaService.salvar(consulta));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> findById(@PathVariable Integer id){
        return ResponseEntity.ok(consultaService.getById(id).get());
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> findAll(){
        return ResponseEntity.ok(consultaService.buscarTodos());
    }

    @PutMapping
    public ResponseEntity<Consulta> update(@RequestBody Consulta consulta){
        ResponseEntity<Consulta> response = null;

        if(consulta.getId()!=null && consultaService.getById(consulta.getId()).isPresent()){
            response = ResponseEntity.ok(consultaService.atualizar(consulta));
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        ResponseEntity<String> response = null;
        if(consultaService.getById(id).isPresent()){
            consultaService.excluir(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Consulta excluida");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;

    }
}
