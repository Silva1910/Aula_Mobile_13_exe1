package com.fatec.zl.amos.aula_mobile_13_exe1.model;

public class Aluno {

private int RA;
private String nome;
private String email;

    public Aluno() {
        super();
    }

    public int getRA() {
        return RA;
    }

    public void setRA(int RA) {
        this.RA = RA;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "RA=" + RA +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}