# Sistema Acadêmico - API REST

Spring Boot 3 + Spring Data JPA + H2 (padrão) ou MySQL. Requer Java 17+ e Maven.

## Rodar

    mvn spring-boot:run

API em http://localhost:8080
Console H2 em http://localhost:8080/h2-console (jdbc:h2:mem:academico, user sa, sem senha)

MySQL:

    mvn spring-boot:run -Dspring-boot.run.profiles=mysql

## Relacionamentos

| Relação | Mapeamento |
|---|---|
| Professor ↔ PerfilProfessor | @OneToOne (FK única professor_id em PerfilProfessor) |
| Curso → Disciplina | @OneToMany(mappedBy) / @ManyToOne(optional = false) |
| Aluno ↔ Disciplina | @ManyToMany com tabela intermediária matricula (aluno_id, disciplina_id) |

## Endpoints

| Método | Rota |
|---|---|
| POST, GET, GET/{id}, PUT/{id}, DELETE/{id} | /professores, /cursos, /disciplinas, /alunos |
| GET | /disciplinas/{id}/alunos |
| GET | /alunos/{id}/disciplinas |
| POST | /alunos/{alunoId}/disciplinas/{disciplinaId} (matricular) |
| DELETE | /alunos/{alunoId}/disciplinas/{disciplinaId} (desmatricular) |

Exemplos prontos em `requests.http`. Dados iniciais (João, Maria, Pedro) são carregados na subida.
