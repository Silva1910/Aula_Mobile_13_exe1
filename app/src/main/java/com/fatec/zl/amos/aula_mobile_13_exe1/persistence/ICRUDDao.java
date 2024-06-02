package com.fatec.zl.amos.aula_mobile_13_exe1.persistence;



import java.sql.SQLException;
import java.util.List;

public interface ICRUDDao<T> {
    void inserir(T obj) throws SQLException;
    int atualizar(T obj) throws SQLException;
    void deletar(T obj) throws SQLException;
    T consultar(T obj) throws SQLException;
    List<T> listar() throws SQLException;
}
