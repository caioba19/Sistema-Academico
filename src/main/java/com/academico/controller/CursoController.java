package com.academico.controller;

import com.academico.dto.Dtos.*;
import com.academico.service.CursoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CursoResponse criar(@Valid @RequestBody CursoRequest request) {
        return service.criar(request);
    }

    @GetMapping
    public List<CursoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public CursoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public CursoResponse atualizar(@PathVariable Long id, @Valid @RequestBody CursoRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }
}
