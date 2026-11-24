package com.academico.service;

import com.academico.dto.Dtos.*;
import com.academico.entity.Curso;
import com.academico.entity.Disciplina;
import com.academico.exception.RecursoNaoEncontradoException;
import com.academico.repository.CursoRepository;
import com.academico.repository.DisciplinaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DisciplinaService {

    private final DisciplinaRepository repository;
    private final CursoRepository cursoRepository;

    public DisciplinaService(DisciplinaRepository repository, CursoRepository cursoRepository) {
        this.repository = repository;
        this.cursoRepository = cursoRepository;
    }

    public DisciplinaResponse criar(DisciplinaRequest request) {
        Curso curso = obterCurso(request.cursoId());
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(request.nome());
        curso.adicionarDisciplina(disciplina);
        return toResponse(repository.save(disciplina));
    }

    @Transactional(readOnly = true)
    public List<DisciplinaResponse> listar() {
        return repository.findAll().stream().map(DisciplinaService::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public DisciplinaResponse buscar(Long id) {
        return toResponse(obter(id));
    }

    @Transactional(readOnly = true)
    public List<AlunoResumo> listarAlunos(Long id) {
        return obter(id).getAlunos().stream().map(a -> new AlunoResumo(a.getId(), a.getNome())).toList();
    }

    public DisciplinaResponse atualizar(Long id, DisciplinaRequest request) {
        Disciplina disciplina = obter(id);
        disciplina.setNome(request.nome());
        Curso novoCurso = obterCurso(request.cursoId());
        if (!novoCurso.getId().equals(disciplina.getCurso().getId())) {
            disciplina.getCurso().getDisciplinas().remove(disciplina);
            novoCurso.adicionarDisciplina(disciplina);
        }
        return toResponse(repository.save(disciplina));
    }

    public void remover(Long id) {
        Disciplina disciplina = obter(id);
        disciplina.getAlunos().forEach(a -> a.getDisciplinas().remove(disciplina));
        disciplina.getCurso().getDisciplinas().remove(disciplina);
        repository.delete(disciplina);
    }

    private Disciplina obter(Long id) {
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Disciplina", id));
    }

    private Curso obterCurso(Long id) {
        return cursoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Curso", id));
    }

    private static DisciplinaResponse toResponse(Disciplina d) {
        return new DisciplinaResponse(d.getId(), d.getNome(), d.getCurso().getId(), d.getCurso().getNome());
    }
}
