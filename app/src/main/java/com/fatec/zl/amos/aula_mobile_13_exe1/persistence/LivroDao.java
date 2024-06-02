package com.fatec.zl.amos.aula_mobile_13_exe1.persistence;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.fatec.zl.amos.aula_mobile_13_exe1.model.Livro;

import java.util.ArrayList;
import java.util.List;

public class LivroDao implements ICRUDDao<Livro> {

    private final Context context;
    private GenericoDao gDao;
    private SQLiteDatabase database;

    public LivroDao(Context context) {
        this.context = context;
    }

    public LivroDao open() throws SQLException {
        gDao = new GenericoDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    public void close() {
        gDao.close();
    }

    @Override
    public void inserir(Livro livro) throws SQLException {
        ContentValues contentValuesExemplar = new ContentValues();
        contentValuesExemplar.put("codigo", livro.getCodigo());
        contentValuesExemplar.put("nome", livro.getNome());
        contentValuesExemplar.put("qtd_paginas", livro.getQtdPaginas());

        long idExemplar = database.insert("exemplar", null, contentValuesExemplar);

        if (idExemplar != -1) {
            ContentValues contentValuesLivro = new ContentValues();
            contentValuesLivro.put("codigo", livro.getCodigo());
            contentValuesLivro.put("edicao", livro.getEdicao());
            contentValuesLivro.put("ISBN", livro.getISBN());

            database.insert("livro", null, contentValuesLivro);
        } else {
            throw new SQLException("Falha ao inserir na tabela exemplar.");
        }
    }

    @Override
    public int atualizar(Livro livro) throws SQLException {
        ContentValues contentValuesExemplar = new ContentValues();
        contentValuesExemplar.put("nome", livro.getNome());
        contentValuesExemplar.put("qtd_paginas", livro.getQtdPaginas());

        int rowsExemplar = database.update("exemplar", contentValuesExemplar, "codigo = ?", new String[]{String.valueOf(livro.getCodigo())});

        if (rowsExemplar > 0) {
            ContentValues contentValuesLivro = new ContentValues();
            contentValuesLivro.put("edicao", livro.getEdicao());
            contentValuesLivro.put("ISBN", livro.getISBN());

            return database.update("livro", contentValuesLivro, "codigo = ?", new String[]{String.valueOf(livro.getCodigo())});
        }

        return 0;
    }


    @Override
    public void deletar(Livro livro) throws SQLException {
        database.delete("livro", "codigo = ?", new String[]{String.valueOf(livro.getCodigo())});
        database.delete("exemplar", "codigo = ?", new String[]{String.valueOf(livro.getCodigo())});
    }


    @SuppressLint("Range")
    @Override
    public Livro consultar(Livro livro) throws SQLException {
        String sql = "SELECT exemplar.codigo, exemplar.nome, exemplar.qtd_paginas, livro.edicao, livro.ISBN " +
                "FROM exemplar " +
                "JOIN livro ON exemplar.codigo = livro.codigo " +
                "WHERE exemplar.codigo = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(livro.getCodigo())});

        if (cursor != null && cursor.moveToFirst()) {
            livro.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            livro.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            livro.setQtdPaginas(cursor.getInt(cursor.getColumnIndex("qtd_paginas")));
            livro.setEdicao(cursor.getInt(cursor.getColumnIndex("edicao")));
            livro.setISBN(cursor.getString(cursor.getColumnIndex("ISBN")));
            cursor.close();
        } else {
            livro = null; // Defina o livro como null se não encontrar resultados
        }
        return livro;
    }



    @SuppressLint("Range")
    @Override
    public List<Livro> listar() throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT exemplar.codigo, exemplar.nome, exemplar.qtd_paginas, livro.edicao, livro.ISBN " +
                "FROM exemplar " +
                "JOIN livro ON exemplar.codigo = livro.codigo";
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Livro livro = new Livro();
                livro.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                livro.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                livro.setQtdPaginas(cursor.getInt(cursor.getColumnIndex("qtd_paginas")));
                livro.setEdicao(cursor.getInt(cursor.getColumnIndex("edicao")));
                livro.setISBN(cursor.getString(cursor.getColumnIndex("ISBN")));
                livros.add(livro);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return livros;
    }


    private static ContentValues getContentValues(Livro livro) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("codigo", livro.getCodigo());
        contentValues.put("nome", livro.getNome());
        contentValues.put("qtd_paginas", livro.getQtdPaginas());
        contentValues.put("edicao", livro.getEdicao());
        contentValues.put("ISBN", livro.getISBN());
        return contentValues;
    }
}
