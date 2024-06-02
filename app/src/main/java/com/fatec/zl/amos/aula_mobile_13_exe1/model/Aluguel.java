package com.fatec.zl.amos.aula_mobile_13_exe1.model;

public class Aluguel {
    private Aluno aluno;
    private Exemplar exemplar;
    private String tipoExemplar;
    private String dataRetirada;
    private String dataDevolucao;

    public Aluno getAluno() {
        return aluno;
    }

    public String getTipoExemplar() {
        return tipoExemplar;
    }

    public void setTipoExemplar(String tipoExemplar) {
        this.tipoExemplar = tipoExemplar;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    public String getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(String dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    @Override
    public String toString() {
        return "Aluguel{" +
                "aluno=" + aluno +
                ", exemplar=" + exemplar +
                ", dataRetirada='" + dataRetirada + '\'' +
                ", dataDevolucao='" + dataDevolucao + '\'' +
                '}';
    }
}
