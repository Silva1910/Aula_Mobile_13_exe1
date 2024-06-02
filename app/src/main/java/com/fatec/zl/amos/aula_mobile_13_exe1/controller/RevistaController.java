package com.fatec.zl.amos.aula_mobile_13_exe1.controller;


import com.fatec.zl.amos.aula_mobile_13_exe1.model.Revista;
import com.fatec.zl.amos.aula_mobile_13_exe1.persistence.RevistaDao;

import java.sql.SQLException;
import java.util.List;

public class RevistaController implements IController<Revista> {
    private final RevistaDao mDao;

    public RevistaController(RevistaDao mDao) {
        this.mDao = mDao;
    }

    @Override
    public void inserir(Revista revista) throws SQLException {
        if (mDao.open() == null) {
            mDao.open();
        }
        mDao.inserir(revista);
        mDao.close();
    }

    @Override
    public void modificar(Revista revista) throws SQLException {
        if (mDao.open() == null) {
            mDao.open();
        }
        mDao.atualizar(revista);
        mDao.close();
    }

    @Override
    public void deletar(Revista revista) throws SQLException {
        if (mDao.open() == null) {
            mDao.open();
        }
        mDao.deletar(revista);
        mDao.close();
    }

    @Override
    public Revista buscar(Revista revista) throws SQLException {
        if (mDao.open() == null) {
            mDao.open();
        }
        return mDao.consultar(revista);
    }

    @Override
    public List<Revista> listar() throws SQLException {
        if (mDao.open() == null) {
            mDao.open();
        }
        return mDao.listar();
    }
}
