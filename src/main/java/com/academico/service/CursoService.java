package com.academico.service;

import com.academico.dto.Dtos.*;
import com.academico.entity.Curso;
import com.academico.exception.RecursoNaoEncontradoException;
import com.academico.repository.CursoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CursoService {

    private final CursoRepository repository;

    public CursoService(CursoRepository repository) {
        this.repository = repository;
    }

    public CursoResponse criar(CursoRequest request) {
        Curso curso = new Curso();
        curso.setNome(request.nome());
        return toResponse(repository.save(curso));
    }

    @Transactional(readOnly = true)
    public List<CursoResponse> listar() {
        return repository.findAll().stream().map(CursoService::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public CursoResponse buscar(Long id) {
        return toResponse(obter(id));
    }

    public CursoResponse atualizar(Long id, CursoRequest request) {
        Curso curso = obter(id);
        curso.setNome(request.nome());
        return toResponse(repository.save(curso));
    }

    public void remover(Long id) {
        Curso curso = obter(id);
        curso.getDisciplinas().forEach(d -> d.getAlunos().forEach(a -> a.getDisciplinas().remove(d)));
        repository.delete(curso);
    }

    private Curso obter(Long id) {
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Curso", id));
    }

    private static CursoResponse toResponse(Curso c) {
        List<DisciplinaResumo> disciplinas = c.getDisciplinas().stream()
                .map(d -> new DisciplinaResumo(d.getId(), d.getNome()))
                .toList();
        return new CursoResponse(c.getId(), c.getNome(), disciplinas);
    }
}
