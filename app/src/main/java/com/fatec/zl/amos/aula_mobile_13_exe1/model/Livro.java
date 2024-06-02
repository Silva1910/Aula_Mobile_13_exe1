package com.fatec.zl.amos.aula_mobile_13_exe1.model;

public class Livro extends Exemplar{

    private int Edicao;
    private String ISBN;

    public int getEdicao() {
        return Edicao;
    }

    public void setEdicao(int edicao) {
        Edicao = edicao;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "Edicao=" + Edicao +
                ", ISBN='" + ISBN + '\'' +
                ", codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", qtdPaginas=" + qtdPaginas +
                '}';
    }
}
