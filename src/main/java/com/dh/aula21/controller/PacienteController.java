package com.dh.aula21.controller;


import com.dh.aula21.model.Paciente;
import com.dh.aula21.repository.impl.EnderecoDaoH2;
import com.dh.aula21.repository.impl.PacienteDaoH2;
import com.dh.aula21.service.EnderecoService;
import com.dh.aula21.service.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RequestMapping("/pacientes")
@RestController
public class PacienteController {

    private PacienteService pacienteService = new PacienteService(new PacienteDaoH2());
    //private EnderecoService enderecoService = new EnderecoService(new EnderecoDaoH2());

    @PostMapping
    public ResponseEntity<Paciente> save(@RequestBody Paciente paciente){
        return ResponseEntity.ok(pacienteService.salvar(paciente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> findById(@PathVariable Integer id){
        return ResponseEntity.ok(pacienteService.getById(id).get());
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> findAll(){
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @PutMapping
    public ResponseEntity<Paciente> update(@RequestBody Paciente paciente){
        ResponseEntity<Paciente> response = null;
        if(paciente.getId()!=null && pacienteService.getById(paciente.getId()).isPresent()){
            response = ResponseEntity.ok(pacienteService.atualizar(paciente));
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        ResponseEntity<String> response = null;
        //Optional<Paciente> paciente = pacienteService.getById(id);
        //Integer enderecoId = paciente.get().getEndereco().getId();

        if(pacienteService.getById(id).isPresent()){
            pacienteService.excluir(id);
            //enderecoService.excluir(enderecoId);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Paciente excluido");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;

    }
}
