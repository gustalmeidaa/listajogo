package com.example.listajogos

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "CADASTRO_DE_JOGOS"
        private const val DATABASE_VERSION = 1

        const val TABLE_JOGOS = "jogos"
        const val ID_COL = "id"
        const val NAME_COL = "nome"
        const val PLAT_COL = "plataforma"

        const val TABLE_USUARIOS = "usuarios"
        const val USER_ID = "id"
        const val EMAIL_COL = "email"
        const val SENHA_COL = "senha"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val queryJogos = ("CREATE TABLE $TABLE_JOGOS ("
                + "$ID_COL INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$NAME_COL TEXT, "
                + "$PLAT_COL TEXT)")

        val queryUsuarios = ("CREATE TABLE $TABLE_USUARIOS ("
                + "$USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$EMAIL_COL TEXT UNIQUE, "
                + "$SENHA_COL TEXT)")

        db.execSQL(queryJogos)
        db.execSQL(queryUsuarios)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_JOGOS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIOS")
        onCreate(db)
    }

    // ===================== JOGOS =====================
    fun addGame(nome: String, plataforma: String) {
        val values = ContentValues()
        values.put(NAME_COL, nome)
        values.put(PLAT_COL, plataforma)

        val db = this.writableDatabase
        db.insert(TABLE_JOGOS, null, values)
        db.close()
    }

    fun getGames(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_JOGOS", null)
    }

    fun deleteGame(nome: String) {
        val db = this.writableDatabase
        db.delete(TABLE_JOGOS, "$NAME_COL=?", arrayOf(nome))
        db.close()
    }

    fun updateGame(nomeAntigo: String, novoNome: String, novaPlataforma: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(NAME_COL, novoNome)
            put(PLAT_COL, novaPlataforma)
        }
        db.update(TABLE_JOGOS, values, "$NAME_COL=?", arrayOf(nomeAntigo))
        db.close()
    }

    // ===================== USUÁRIOS =====================
    fun cadastrarUsuario(email: String, senha: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(EMAIL_COL, email)
        values.put(SENHA_COL, senha)

        val resultado = db.insert(TABLE_USUARIOS, null, values)
        db.close()
        return resultado != -1L
    }

    fun verificarLogin(email: String, senha: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USUARIOS WHERE $EMAIL_COL=? AND $SENHA_COL=?"
        val cursor = db.rawQuery(query, arrayOf(email, senha))

        val existe = cursor.count > 0
        cursor.close()
        db.close()

        return existe
    }
}
