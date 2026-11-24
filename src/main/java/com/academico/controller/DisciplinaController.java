package com.academico.controller;

import com.academico.dto.Dtos.*;
import com.academico.service.DisciplinaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

    private final DisciplinaService service;

    public DisciplinaController(DisciplinaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DisciplinaResponse criar(@Valid @RequestBody DisciplinaRequest request) {
        return service.criar(request);
    }

    @GetMapping
    public List<DisciplinaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public DisciplinaResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public DisciplinaResponse atualizar(@PathVariable Long id, @Valid @RequestBody DisciplinaRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }

    @GetMapping("/{id}/alunos")
    public List<AlunoResumo> listarAlunos(@PathVariable Long id) {
        return service.listarAlunos(id);
    }
}
