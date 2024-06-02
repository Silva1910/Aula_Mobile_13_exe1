package com.fatec.zl.amos.aula_mobile_13_exe1.controller;



import com.fatec.zl.amos.aula_mobile_13_exe1.model.Livro;
import com.fatec.zl.amos.aula_mobile_13_exe1.persistence.LivroDao;

import java.sql.SQLException;
import java.util.List;

public class LivroController implements IController<Livro> {
    private final LivroDao mDao;

    public LivroController(LivroDao mDao) {
        this.mDao = mDao;
    }

    @Override
    public void inserir(Livro livro) throws SQLException {
        if (mDao.open() == null) {
            mDao.open();
        }
        mDao.inserir(livro);
        mDao.close();
    }

    @Override
    public void modificar(Livro livro) throws SQLException {
        if (mDao.open() == null) {
            mDao.open();
        }
        mDao.atualizar(livro);
        mDao.close();
    }

    @Override
    public void deletar(Livro livro) throws SQLException {
        if (mDao.open() == null) {
            mDao.open();
        }
        mDao.deletar(livro);
        mDao.close();
    }

    @Override
    public Livro buscar(Livro livro) throws SQLException {
        if (mDao.open() == null) {
            mDao.open();
        }
        return mDao.consultar(livro);
    }

    @Override
    public List<Livro> listar() throws SQLException {
        if (mDao.open() == null) {
            mDao.open();
        }
        return mDao.listar();
    }
}
