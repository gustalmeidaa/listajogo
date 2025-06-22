package com.example.listajogos
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context:Context, factory:SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context, DATABASE_NAME, factory,DATABASE_VERSION) {
    companion object{
        //Nome do banco e versão do banco
        private val DATABASE_NAME = "CADASTRO_DE_JOGOS"
        private val DATABASE_VERSION = 1
        //Nome da tabela e colunas da tabela
        val TABLE_NAME = "jogos"
        val ID_COL = "id"
        val NAME_COL = "nome"
        val PLAT_COL = "plataforma"
    }
    //Metodo para criação do banco
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COL + " TEXT," +
                PLAT_COL + " TEXT" + ")")
        if (db != null) {
            db.execSQL(query)
        }
    }
    //Metodo que apaga a tabela se ja existir
    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }
    fun addName(name: String, age: String){
        val values = ContentValues()
        values.put(NAME_COL, name)
        values.put(PLAT_COL, age)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun getName():Cursor?{
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
    }

    fun deleteName(nome: String) {
        val db = this.writableDatabase
        db.delete("jogos", "nome=?", arrayOf(nome))
        db.close()
    }

    fun updateName(nomeAntigo: String, novoNome: String, novaPlataforma: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nome", novoNome)
            put("plataforma", novaPlataforma)
        }
        db.update("jogos", values, "nome=?", arrayOf(nomeAntigo))
        db.close()
    }

}