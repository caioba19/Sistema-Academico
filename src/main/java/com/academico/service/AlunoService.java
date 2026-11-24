package com.academico.service;

import com.academico.dto.Dtos.*;
import com.academico.entity.Aluno;
import com.academico.entity.Disciplina;
import com.academico.exception.RecursoNaoEncontradoException;
import com.academico.repository.AlunoRepository;
import com.academico.repository.DisciplinaRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AlunoService {

    private final AlunoRepository repository;
    private final DisciplinaRepository disciplinaRepository;

    public AlunoService(AlunoRepository repository, DisciplinaRepository disciplinaRepository) {
        this.repository = repository;
        this.disciplinaRepository = disciplinaRepository;
    }

    public AlunoResponse criar(AlunoRequest request) {
        Aluno aluno = new Aluno();
        aluno.setNome(request.nome());
        aluno.setEmail(request.email());
        return toResponse(repository.save(aluno));
    }

    @Transactional(readOnly = true)
    public List<AlunoResponse> listar() {
        return repository.findAll().stream().map(AlunoService::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public AlunoResponse buscar(Long id) {
        return toResponse(obter(id));
    }

    public AlunoResponse atualizar(Long id, AlunoRequest request) {
        Aluno aluno = obter(id);
        aluno.setNome(request.nome());
        aluno.setEmail(request.email());
        return toResponse(repository.save(aluno));
    }

    public void remover(Long id) {
        Aluno aluno = obter(id);
        new ArrayList<>(aluno.getDisciplinas()).forEach(aluno::desmatricular);
        repository.delete(aluno);
    }

    public AlunoResponse matricular(Long alunoId, Long disciplinaId) {
        Aluno aluno = obter(alunoId);
        aluno.matricular(obterDisciplina(disciplinaId));
        return toResponse(repository.save(aluno));
    }

    public AlunoResponse desmatricular(Long alunoId, Long disciplinaId) {
        Aluno aluno = obter(alunoId);
        aluno.desmatricular(obterDisciplina(disciplinaId));
        return toResponse(repository.save(aluno));
    }

    @Transactional(readOnly = true)
    public List<DisciplinaResumo> listarDisciplinas(Long alunoId) {
        return toResponse(obter(alunoId)).disciplinas();
    }

    private Aluno obter(Long id) {
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Aluno", id));
    }

    private Disciplina obterDisciplina(Long id) {
        return disciplinaRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Disciplina", id));
    }

    private static AlunoResponse toResponse(Aluno a) {
        List<DisciplinaResumo> disciplinas = a.getDisciplinas().stream()
                .map(d -> new DisciplinaResumo(d.getId(), d.getNome()))
                .toList();
        return new AlunoResponse(a.getId(), a.getNome(), a.getEmail(), disciplinas);
    }
}
