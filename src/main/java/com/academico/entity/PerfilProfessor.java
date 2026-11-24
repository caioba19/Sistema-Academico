package com.academico.entity;

import jakarta.persistence.*;

@Entity
public class PerfilProfessor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String especializacao;

    private String telefone;

    @Column(length = 2000)
    private String curriculo;

    @OneToOne(optional = false)
    @JoinColumn(name = "professor_id", nullable = false, unique = true)
    private Professor professor;

    public Long getId() { return id; }
    public String getEspecializacao() { return especializacao; }
    public void setEspecializacao(String especializacao) { this.especializacao = especializacao; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getCurriculo() { return curriculo; }
    public void setCurriculo(String curriculo) { this.curriculo = curriculo; }
    public Professor getProfessor() { return professor; }
    public void setProfessor(Professor professor) { this.professor = professor; }
}
