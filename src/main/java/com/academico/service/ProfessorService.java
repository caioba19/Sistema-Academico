package com.academico.service;

import com.academico.dto.Dtos.*;
import com.academico.entity.PerfilProfessor;
import com.academico.entity.Professor;
import com.academico.exception.RecursoNaoEncontradoException;
import com.academico.repository.ProfessorRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProfessorService {

    private final ProfessorRepository repository;

    public ProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }

    public ProfessorResponse criar(ProfessorRequest request) {
        Professor professor = new Professor();
        aplicar(professor, request);
        return toResponse(repository.save(professor));
    }

    @Transactional(readOnly = true)
    public List<ProfessorResponse> listar() {
        return repository.findAll().stream().map(ProfessorService::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ProfessorResponse buscar(Long id) {
        return toResponse(obter(id));
    }

    public ProfessorResponse atualizar(Long id, ProfessorRequest request) {
        Professor professor = obter(id);
        aplicar(professor, request);
        return toResponse(repository.save(professor));
    }

    public void remover(Long id) {
        repository.delete(obter(id));
    }

    private Professor obter(Long id) {
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Professor", id));
    }

    private void aplicar(Professor professor, ProfessorRequest request) {
        professor.setNome(request.nome());
        professor.setEmail(request.email());
        if (request.perfil() != null) {
            PerfilProfessor perfil = professor.getPerfil() != null ? professor.getPerfil() : new PerfilProfessor();
            perfil.setEspecializacao(request.perfil().especializacao());
            perfil.setTelefone(request.perfil().telefone());
            perfil.setCurriculo(request.perfil().curriculo());
            perfil.setProfessor(professor);
            professor.setPerfil(perfil);
        }
    }

    private static ProfessorResponse toResponse(Professor p) {
        PerfilProfessor pf = p.getPerfil();
        PerfilResponse perfil = pf == null ? null
                : new PerfilResponse(pf.getId(), pf.getEspecializacao(), pf.getTelefone(), pf.getCurriculo());
        return new ProfessorResponse(p.getId(), p.getNome(), p.getEmail(), perfil);
    }
}
