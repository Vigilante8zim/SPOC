package com.example.felipe.spoc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Felipe on 08/06/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "usuarios.db";
    private static final String NOME_TABLE = "usuarios";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_SENHA = "senha";
    private static final String COLUMN_NOME = "nome";
    SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table usuarios (id integer primary key not null," +
            " email text not null, senha text not null, nome text not null);";


    public DatabaseHelper(Context context){
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    public String lugar(Context cont){
        String massa;
        massa = cont.getDatabasePath("usuarios.db").toString();
        return massa;
    }

    public void limpaTudo(){


        db = this.getWritableDatabase();
        db.delete(NOME_TABLE,null,null);


    }

    public void insertUsuario(Usuario u){
        db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();

        String query = "select * from "+NOME_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        int cont = cursor.getCount();

        valores.put(COLUMN_ID, cont);
        valores.put(COLUMN_EMAIL, u.getEmail());
        valores.put(COLUMN_SENHA, u.getSenha());
        valores.put(COLUMN_NOME, u.getNome());

        db.insert(NOME_TABLE, null, valores);
        db.close();
    }

    public String retornaNome(String email){
        db = this.getReadableDatabase();
        String query = "select nome from "+NOME_TABLE+" where email = '"+email+"'";
        Cursor cursor = db.rawQuery(query, null);
        String a = "ndeu";
        if (cursor.moveToFirst()) {
            a = cursor.getString(0);
        }
        return a;
    }

    public String searchPass(String email){
        db = this.getReadableDatabase();
        String query = "select email, senha from "+NOME_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        String a,b;
        b = "Não encontrado";
        if (cursor.moveToFirst()){

            do{
                a = cursor.getString(0);
                if (a.equals(email)){
                    b = cursor.getString(1);
                    break;
                }

            }while(cursor.moveToNext());

        }
    return b;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+NOME_TABLE;
        db.execSQL(query);
        this.onCreate(db);
    }
}
