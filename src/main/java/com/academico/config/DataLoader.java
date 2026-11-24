package com.academico.config;

import com.academico.entity.*;
import com.academico.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProfessorRepository professorRepository;
    private final CursoRepository cursoRepository;
    private final AlunoRepository alunoRepository;

    public DataLoader(ProfessorRepository professorRepository, CursoRepository cursoRepository, AlunoRepository alunoRepository) {
        this.professorRepository = professorRepository;
        this.cursoRepository = cursoRepository;
        this.alunoRepository = alunoRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Professor professor = new Professor();
        professor.setNome("Celso Barreto");
        professor.setEmail("celso@instituicao.edu.br");
        PerfilProfessor perfil = new PerfilProfessor();
        perfil.setEspecializacao("Desenvolvimento de Software");
        perfil.setTelefone("(71) 99999-0000");
        perfil.setCurriculo("Docente com experiência em desenvolvimento web e back-end.");
        perfil.setProfessor(professor);
        professor.setPerfil(perfil);
        professorRepository.save(professor);

        Curso curso = new Curso();
        curso.setNome("Desenvolvimento de Sistemas");
        Disciplina java = disciplina("Java");
        Disciplina banco = disciplina("Banco de Dados");
        Disciplina web = disciplina("Desenvolvimento Web");
        Disciplina engenharia = disciplina("Engenharia de Software");
        curso.adicionarDisciplina(java);
        curso.adicionarDisciplina(banco);
        curso.adicionarDisciplina(web);
        curso.adicionarDisciplina(engenharia);
        cursoRepository.save(curso);

        Aluno joao = aluno("João", "joao@email.com");
        joao.matricular(java);
        joao.matricular(banco);
        Aluno maria = aluno("Maria", "maria@email.com");
        maria.matricular(java);
        maria.matricular(web);
        Aluno pedro = aluno("Pedro", "pedro@email.com");
        pedro.matricular(banco);
        pedro.matricular(java);
        alunoRepository.save(joao);
        alunoRepository.save(maria);
        alunoRepository.save(pedro);
    }

    private Disciplina disciplina(String nome) {
        Disciplina d = new Disciplina();
        d.setNome(nome);
        return d;
    }

    private Aluno aluno(String nome, String email) {
        Aluno a = new Aluno();
        a.setNome(nome);
        a.setEmail(email);
        return a;
    }
}
