package com.fatec.zl.amos.aula_mobile_13_exe1.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fatec.zl.amos.aula_mobile_13_exe1.model.Revista;

import java.util.ArrayList;
import java.util.List;

public class RevistaDao implements ICRUDDao<Revista> {

    private final Context context;
    private GenericoDao gDao;
    private SQLiteDatabase database;

    public RevistaDao(Context context) {
        this.context = context;
    }

    public RevistaDao open() throws SQLException {
        gDao = new GenericoDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    public void close() {
        gDao.close();
    }
    @Override
    public void inserir(Revista revista) throws SQLException {
        // Adicione logs para verificar os valores
        if (revista == null) {
            throw new SQLException("Revista é nula.");
        }

        Log.d("RevistaDao", "Inserindo revista: " + revista.getCodigo() + ", " + revista.getNome() + ", " + revista.getQtdPaginas() + ", " + revista.getISSN());

        if (revista.getCodigo() == 0) {
            throw new SQLException("Código inválido.");
        }
        if (revista.getNome() == null) {
            throw new SQLException("Nome inválido.");
        }
        if (revista.getQtdPaginas() <= 0) {
            throw new SQLException("Quantidade de páginas inválida.");
        }
        if (revista.getISSN() == null) {
            throw new SQLException("ISSN inválido.");
        }

        ContentValues contentValuesExemplar = new ContentValues();
        contentValuesExemplar.put("codigo", revista.getCodigo());
        contentValuesExemplar.put("nome", revista.getNome());
        contentValuesExemplar.put("qtd_paginas", revista.getQtdPaginas());

        try {
            long idExemplar = database.insert("exemplar", null, contentValuesExemplar);

            if (idExemplar != -1) {
                ContentValues contentValuesRevista = new ContentValues();
                contentValuesRevista.put("codigo", revista.getCodigo());
                contentValuesRevista.put("ISSN", revista.getISSN());

                long idRevista = database.insert("revista", null, contentValuesRevista);
                if (idRevista == -1) {
                    throw new SQLException("Falha ao inserir na tabela revista.");
                }
            } else {
                throw new SQLException("Falha ao inserir na tabela exemplar.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Erro ao inserir revista: " + e.getMessage());
        }
    }

    @Override
    public int atualizar(Revista revista) throws SQLException {
        ContentValues contentValuesExemplar = new ContentValues();
        contentValuesExemplar.put("nome", revista.getNome());
        contentValuesExemplar.put("qtd_paginas", revista.getQtdPaginas());

        int rowsExemplar = database.update("exemplar", contentValuesExemplar, "codigo = ?", new String[]{String.valueOf(revista.getCodigo())});

        if (rowsExemplar > 0) {
            ContentValues contentValuesRevista = new ContentValues();
            contentValuesRevista.put("ISSN", revista.getISSN());

            return database.update("revista", contentValuesRevista, "codigo = ?", new String[]{String.valueOf(revista.getCodigo())});
        }

        return 0;
    }

    @Override
    public void deletar(Revista revista) throws SQLException {
        int rowsRevista = database.delete("revista", "codigo = ?", new String[]{String.valueOf(revista.getCodigo())});
        if (rowsRevista > 0) {
            database.delete("exemplar", "codigo = ?", new String[]{String.valueOf(revista.getCodigo())});
        } else {
            throw new SQLException("Falha ao excluir da tabela revista.");
        }
    }

    @SuppressLint("Range")
    @Override
    public Revista consultar(Revista revista) throws SQLException {
        String sql = "SELECT exemplar.codigo, exemplar.nome, exemplar.qtd_paginas, revista.ISSN " +
                "FROM exemplar " +
                "JOIN revista ON exemplar.codigo = revista.codigo " +
                "WHERE exemplar.codigo = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(revista.getCodigo())});

        if (cursor != null && cursor.moveToFirst()) {
            revista.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            revista.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            revista.setQtdPaginas(cursor.getInt(cursor.getColumnIndex("qtd_paginas")));
            revista.setISSN(cursor.getString(cursor.getColumnIndex("ISSN")));
        }

        if (cursor != null) {
            cursor.close();
        }
        return revista;
    }

    @SuppressLint("Range")
    @Override
    public List<Revista> listar() throws SQLException {
        List<Revista> revistas = new ArrayList<>();
        String sql = "SELECT exemplar.codigo, exemplar.nome, exemplar.qtd_paginas, revista.ISSN " +
                "FROM exemplar " +
                "JOIN revista ON exemplar.codigo = revista.codigo";
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Revista revista = new Revista();
                revista.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                revista.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                revista.setQtdPaginas(cursor.getInt(cursor.getColumnIndex("qtd_paginas")));
                revista.setISSN(cursor.getString(cursor.getColumnIndex("ISSN")));
                revistas.add(revista);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return revistas;
    }
}
