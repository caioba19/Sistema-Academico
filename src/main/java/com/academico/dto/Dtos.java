package com.academico.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public final class Dtos {

    private Dtos() {}

    public record PerfilRequest(String especializacao, String telefone, String curriculo) {}

    public record PerfilResponse(Long id, String especializacao, String telefone, String curriculo) {}

    public record ProfessorRequest(
            @NotBlank String nome,
            @NotBlank @Email String email,
            @Valid PerfilRequest perfil) {}

    public record ProfessorResponse(Long id, String nome, String email, PerfilResponse perfil) {}

    public record CursoRequest(@NotBlank String nome) {}

    public record DisciplinaResumo(Long id, String nome) {}

    public record CursoResponse(Long id, String nome, List<DisciplinaResumo> disciplinas) {}

    public record DisciplinaRequest(@NotBlank String nome, @NotNull Long cursoId) {}

    public record DisciplinaResponse(Long id, String nome, Long cursoId, String cursoNome) {}

    public record AlunoRequest(@NotBlank String nome, @NotBlank @Email String email) {}

    public record AlunoResumo(Long id, String nome) {}

    public record AlunoResponse(Long id, String nome, String email, List<DisciplinaResumo> disciplinas) {}
}
