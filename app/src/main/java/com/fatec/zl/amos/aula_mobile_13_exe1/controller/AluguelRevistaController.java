package com.fatec.zl.amos.aula_mobile_13_exe1.controller;


import         com.fatec.zl.amos.aula_mobile_13_exe1.model.Aluguel;
import com.fatec.zl.amos.aula_mobile_13_exe1.persistence.AluguelDao;

import java.sql.SQLException;
import java.util.List;

public class AluguelRevistaController implements IController<Aluguel> {
    private final AluguelDao mDao;

    public AluguelRevistaController(AluguelDao mDao) {
        this.mDao = mDao;
    }

    @Override
    public void inserir(Aluguel aluguel) throws SQLException {
        if (mDao.open() == null) {
            mDao.open();
        }
        mDao.inserir(aluguel);
        mDao.close();
    }

    @Override
    public void modificar(Aluguel aluguel) throws SQLException {
        if (mDao.open() == null) {
            mDao.open();
        }
        mDao.atualizar(aluguel);
        mDao.close();
    }

    @Override
    public void deletar(Aluguel aluguel) throws SQLException {
        if (mDao.open() == null) {
            mDao.open();
        }
        mDao.deletar(aluguel);
        mDao.close();
    }

    @Override
    public Aluguel buscar(Aluguel aluguel) throws SQLException {
        if (mDao.open() == null) {
            mDao.open();
        }
        return mDao.consultar(aluguel);
    }

    @Override
    public List<Aluguel> listar() throws SQLException {
        if (mDao.open() == null) {
            mDao.open();
        }
        return mDao.listar();
    }
}
