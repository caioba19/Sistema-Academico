package com.academico.entity;

import jakarta.persistence.*;

@Entity
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToOne(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private PerfilProfessor perfil;

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public PerfilProfessor getPerfil() { return perfil; }
    public void setPerfil(PerfilProfessor perfil) { this.perfil = perfil; }
}
