package com.academico.controller;

import com.academico.dto.Dtos.*;
import com.academico.service.AlunoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlunoResponse criar(@Valid @RequestBody AlunoRequest request) {
        return service.criar(request);
    }

    @GetMapping
    public List<AlunoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public AlunoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public AlunoResponse atualizar(@PathVariable Long id, @Valid @RequestBody AlunoRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }

    @GetMapping("/{id}/disciplinas")
    public List<DisciplinaResumo> listarDisciplinas(@PathVariable Long id) {
        return service.listarDisciplinas(id);
    }

    @PostMapping("/{alunoId}/disciplinas/{disciplinaId}")
    public AlunoResponse matricular(@PathVariable Long alunoId, @PathVariable Long disciplinaId) {
        return service.matricular(alunoId, disciplinaId);
    }

    @DeleteMapping("/{alunoId}/disciplinas/{disciplinaId}")
    public AlunoResponse desmatricular(@PathVariable Long alunoId, @PathVariable Long disciplinaId) {
        return service.desmatricular(alunoId, disciplinaId);
    }
}
