package com.example.desafiokleverapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, NOME_BANCO_DADOS, null, VERSAO_BANCO_DADOS)
{

    companion object
    {
        private const val VERSAO_BANCO_DADOS = 1
        private const val NOME_BANCO_DADOS = "listaMoedas.db"
        const val TABELA_MOEDAS = "moedas"

        //Colunas tabela moedas
        const val ID_MOEDA = "id_moeda"
        const val DESCRICAO = "descricao"
        const val DATA_CADASTRO = "data_cadastro"

        const val ATIVO = "ativo"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val sqlTabela = "CREATE TABLE IF NOT EXISTS $TABELA_MOEDAS (" +
                "$ID_MOEDA INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "$DESCRICAO VARCHAR(350) NOT NULL," +
                "$DATA_CADASTRO DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ");"
        try
        {
            db?.execSQL( sqlTabela )
            Log.i("info_db", "Sucesso ao criar a tabela v: $VERSAO_BANCO_DADOS")
        }
        catch (e: Exception)
        {
            e.printStackTrace()
            Log.i("info_db", "erro ao criar tabela ${e.message}")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
    }
}