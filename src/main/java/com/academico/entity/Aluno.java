package com.academico.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany
    @JoinTable(
            name = "matricula",
            joinColumns = @JoinColumn(name = "aluno_id"),
            inverseJoinColumns = @JoinColumn(name = "disciplina_id")
    )
    private Set<Disciplina> disciplinas = new HashSet<>();

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Set<Disciplina> getDisciplinas() { return disciplinas; }

    public void matricular(Disciplina disciplina) {
        disciplinas.add(disciplina);
        disciplina.getAlunos().add(this);
    }

    public void desmatricular(Disciplina disciplina) {
        disciplinas.remove(disciplina);
        disciplina.getAlunos().remove(this);
    }
}
