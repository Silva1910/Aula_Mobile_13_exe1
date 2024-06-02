package com.fatec.zl.amos.aula_mobile_13_exe1.persistence;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GenericoDao extends SQLiteOpenHelper {

    private static final String DATABASE = "BIBLI";
    private static final int DATABASE_VER = 1;

    private static final String CREATE_TABLE_ALUNO =
            "CREATE TABLE Aluno ( " +
                    "RA INTEGER PRIMARY KEY NOT NULL, " +
                    "nome VARCHAR(55) NOT NULL, " +
                    "email VARCHAR(55) NOT NULL);";

    private static final String CREATE_TABLE_EXEMPLAR = "CREATE TABLE exemplar (" +
            "codigo INTEGER PRIMARY KEY, " +
            "nome TEXT, " +
            "qtd_paginas INTEGER" +
            ");";

    private static final String CREATE_TABLE_LIVRO = "CREATE TABLE livro (" +
            "codigo INTEGER PRIMARY KEY, " +
            "edicao INTEGER, " +
            "ISBN TEXT, " +
            "FOREIGN KEY(codigo) REFERENCES exemplar(codigo) ON DELETE CASCADE" +
            ");";

    private static final String CREATE_TABLE_REVISTA = "CREATE TABLE revista (" +
            "codigo INTEGER PRIMARY KEY, " +
            "ISSN TEXT, " +
            "FOREIGN KEY(codigo) REFERENCES exemplar(codigo) ON DELETE CASCADE" +
            ");";

    private static final String CREATE_TABLE_ALUGUEL = "CREATE TABLE aluguel ( " +
            "alunoRA INTEGER NOT NULL, " +
            "exemplarCodigo INTEGER NOT NULL, " +
            "tipoExemplar TEXT NOT NULL, " +  // Coluna para diferenciar livro e revista
            "dataRetirada VARCHAR(55) NOT NULL, " +
            "dataDevolucao VARCHAR(55) NOT NULL, " +
            "PRIMARY KEY (alunoRA, exemplarCodigo, tipoExemplar), " +
            "FOREIGN KEY (alunoRA) REFERENCES Aluno(RA), " +
            "FOREIGN KEY (exemplarCodigo) REFERENCES exemplar(codigo));";

    public GenericoDao(Context context) {
        super(context, DATABASE, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_ALUNO);
        sqLiteDatabase.execSQL(CREATE_TABLE_EXEMPLAR);
        sqLiteDatabase.execSQL(CREATE_TABLE_LIVRO);
        sqLiteDatabase.execSQL(CREATE_TABLE_REVISTA);
        sqLiteDatabase.execSQL(CREATE_TABLE_ALUGUEL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS aluguel");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS revista");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS livro");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS exemplar");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Aluno");
            onCreate(sqLiteDatabase);
        }
    }
}
