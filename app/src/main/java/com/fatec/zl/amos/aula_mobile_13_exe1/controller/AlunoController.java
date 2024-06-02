package com.fatec.zl.amos.aula_mobile_13_exe1.controller;



import com.fatec.zl.amos.aula_mobile_13_exe1.model.Aluno;
import com.fatec.zl.amos.aula_mobile_13_exe1.persistence.AlunoDao;

import java.sql.SQLException;
import java.util.List;

public class AlunoController implements IController<Aluno> {
    private final AlunoDao mDao;

    public AlunoController(AlunoDao mDao) {
        this.mDao = mDao;
    }

    @Override
    public void inserir(Aluno aluno) throws SQLException {
        if (mDao.open() == null) {
            mDao.open();
        }
        mDao.inserir(aluno);
        mDao.close();
    }

    @Override
    public void modificar(Aluno aluno) throws SQLException {
        if (mDao.open() == null) {
            mDao.open();
        }
        mDao.atualizar(aluno);
        mDao.close();
    }

    @Override
    public void deletar(Aluno aluno) throws SQLException {
        if (mDao.open() == null) {
            mDao.open();
        }
        mDao.deletar(aluno);
        mDao.close();
    }

    @Override
    public Aluno buscar(Aluno aluno) throws SQLException {
        if (mDao.open() == null) {
            mDao.open();
        }
        return mDao.consultar(aluno);
    }

    @Override
    public List<Aluno> listar() throws SQLException {
        if (mDao.open() == null) {
            mDao.open();
        }
        return mDao.listar();
    }
}
