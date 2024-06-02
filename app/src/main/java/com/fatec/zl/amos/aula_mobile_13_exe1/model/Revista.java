package com.fatec.zl.amos.aula_mobile_13_exe1.model;

public class Revista extends Exemplar{

    private String ISSN;

    public String getISSN() {
        return ISSN;
    }

    public void setISSN(String ISSN) {
        this.ISSN = ISSN;
    }

    @Override
    public String toString() {
        return "Revista{" +
                "ISSN='" + ISSN + '\'' +
                ", codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", qtdPaginas=" + qtdPaginas +
                '}';
    }
}
