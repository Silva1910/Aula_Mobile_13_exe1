package com.fatec.zl.amos.aula_mobile_13_exe1.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.fatec.zl.amos.aula_mobile_13_exe1.model.Aluguel;

import java.util.ArrayList;
import java.util.List;

public class AluguelDao implements ICRUDDao<Aluguel> {

    private final Context context;
    private GenericoDao gDao;
    private SQLiteDatabase database;

    public AluguelDao(Context context) {
        this.context = context;
    }

    public AluguelDao open() throws SQLException {
        gDao = new GenericoDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    public void close() {
        gDao.close();
    }

    @Override
    public void inserir(Aluguel aluguel) throws SQLException {
        ContentValues contentValues = getContentValues(aluguel);
        database.insert("aluguel", null, contentValues);
    }

    @Override
    public int atualizar(Aluguel aluguel) throws SQLException {
        ContentValues contentValues = getContentValues(aluguel);
        return database.update("aluguel", contentValues, "alunoRA = ? AND exemplarCodigo = ? AND tipoExemplar = ?",
                new String[]{String.valueOf(aluguel.getAluno().getRA()),
                        String.valueOf(aluguel.getExemplar().getCodigo()),
                        aluguel.getTipoExemplar()});
    }

    @Override
    public void deletar(Aluguel aluguel) throws SQLException {
        database.delete("aluguel", "alunoRA = ? AND exemplarCodigo = ? AND tipoExemplar = ?",
                new String[]{String.valueOf(aluguel.getAluno().getRA()),
                        String.valueOf(aluguel.getExemplar().getCodigo()),
                        aluguel.getTipoExemplar()});
    }

    @SuppressLint("Range")
    @Override
    public Aluguel consultar(Aluguel aluguel) throws SQLException {
        String sql = "SELECT * FROM aluguel WHERE alunoRA = ? AND exemplarCodigo = ? AND tipoExemplar = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{
                String.valueOf(aluguel.getAluno().getRA()),
                String.valueOf(aluguel.getExemplar().getCodigo()),
                aluguel.getTipoExemplar()});

        if (cursor != null && cursor.moveToFirst()) {
            aluguel.getAluno().setRA(cursor.getInt(cursor.getColumnIndex("alunoRA")));
            aluguel.getExemplar().setCodigo(cursor.getInt(cursor.getColumnIndex("exemplarCodigo")));
            aluguel.setTipoExemplar(cursor.getString(cursor.getColumnIndex("tipoExemplar")));
            aluguel.setDataRetirada(cursor.getString(cursor.getColumnIndex("dataRetirada")));
            aluguel.setDataDevolucao(cursor.getString(cursor.getColumnIndex("dataDevolucao")));
        }

        if (cursor != null) {
            cursor.close();
        }
        return aluguel;
    }

    @SuppressLint("Range")
    @Override
    public List<Aluguel> listar() throws SQLException {
        List<Aluguel> alugueis = new ArrayList<>();
        String sql = "SELECT * FROM aluguel";
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Aluguel aluguel = new Aluguel();
                aluguel.getAluno().setRA(cursor.getInt(cursor.getColumnIndex("alunoRA")));
                aluguel.getExemplar().setCodigo(cursor.getInt(cursor.getColumnIndex("exemplarCodigo")));
                aluguel.setTipoExemplar(cursor.getString(cursor.getColumnIndex("tipoExemplar")));
                aluguel.setDataRetirada(cursor.getString(cursor.getColumnIndex("dataRetirada")));
                aluguel.setDataDevolucao(cursor.getString(cursor.getColumnIndex("dataDevolucao")));
                alugueis.add(aluguel);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        return alugueis;
    }

    private static ContentValues getContentValues(Aluguel aluguel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("alunoRA", aluguel.getAluno().getRA());
        contentValues.put("exemplarCodigo", aluguel.getExemplar().getCodigo());
        contentValues.put("tipoExemplar", aluguel.getTipoExemplar());
        contentValues.put("dataRetirada", aluguel.getDataRetirada());
        contentValues.put("dataDevolucao", aluguel.getDataDevolucao());
        return contentValues;
    }
}
