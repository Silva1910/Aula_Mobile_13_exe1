package com.fatec.zl.amos.aula_mobile_13_exe1.persistence;



import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.fatec.zl.amos.aula_mobile_13_exe1.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoDao implements ICRUDDao<Aluno> {

    private final Context context;
    private GenericoDao gDao;
    private SQLiteDatabase database;

    public AlunoDao(Context context) {
        this.context = context;
    }

    public AlunoDao open() throws SQLException {
        gDao = new GenericoDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    public void close() {
        gDao.close();
    }

    @Override
    public void inserir(Aluno aluno) throws SQLException {
        ContentValues contentValues = getContentValues(aluno);
        database.insert("Aluno", null, contentValues);
    }

    @Override
    public int atualizar(Aluno aluno) throws SQLException {
        ContentValues contentValues = getContentValues(aluno);
        return database.update("Aluno", contentValues, "RA = ?", new String[]{String.valueOf(aluno.getRA())});
    }

    @Override
    public void deletar(Aluno aluno) throws SQLException {
        database.delete("Aluno", "RA = ?", new String[]{String.valueOf(aluno.getRA())});
    }

    @SuppressLint("Range")
    @Override
    public Aluno consultar(Aluno aluno) throws SQLException {
        String sql = "SELECT RA, NOME, EMAIL FROM Aluno WHERE RA = " + aluno.getRA();
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            aluno.setRA(cursor.getInt(cursor.getColumnIndex("RA")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        }

        if (cursor != null) {
            cursor.close();
        }
        return aluno;
    }

    @SuppressLint("Range")
    @Override
    public List<Aluno> listar() throws SQLException {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM Aluno";
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Aluno aluno = new Aluno();
                aluno.setRA(cursor.getInt(cursor.getColumnIndex("RA")));
                aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                aluno.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                alunos.add(aluno);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        return alunos;
    }

    private static ContentValues getContentValues(Aluno aluno) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("RA", aluno.getRA());
        contentValues.put("nome", aluno.getNome());
        contentValues.put("email", aluno.getEmail());
        return contentValues;
    }
}
