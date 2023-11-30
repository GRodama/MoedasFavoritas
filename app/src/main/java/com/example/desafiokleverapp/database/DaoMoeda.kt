package com.example.desafiokleverapp.database

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.desafiokleverapp.model.Moeda

class DaoMoeda (context: Context) : IMoedaDAO
{
    private val escrita = DatabaseHelper (context).writableDatabase
    private val leitura = DatabaseHelper (context).readableDatabase


    //usado para salvar o tipo de moeda
    override fun salvar(moeda: Moeda): Boolean
    {
        val conteudos = ContentValues()
        conteudos.put ("${DatabaseHelper.DESCRICAO}", moeda.descricao)

        try
        {
            escrita.insert(DatabaseHelper.TABELA_MOEDAS, null, conteudos)
            Log.i("info_db", "Sucesso ao salvar a moeda")
        }
        catch (e: Exception)
        {
            e.printStackTrace()
            Log.i("info_db", "Erro ao salvar moeda")
            return false
        }
        return true
    }

    override fun atualizar(moeda: Moeda): Boolean
    {
        val args = arrayOf(moeda.idMoeda.toString())
        val conteudo = ContentValues()

        //insere a nova descricao
        conteudo.put("${DatabaseHelper.DESCRICAO}", moeda.descricao)
        try
        {
            escrita.update(
                DatabaseHelper.TABELA_MOEDAS,
                conteudo,
                "${DatabaseHelper.ID_MOEDA} = ?",
                args
            )
            Log.i("info_db", "Sucesso ao atualizar moeda")
        }
        catch (e: Exception)
        {
            e.printStackTrace()
            Log.i("info_db", "Erro ao atualizar moeda")
            return false
        }

        return true
    }

    override fun remover(idMoeda: Int): Boolean
    {
        val args = arrayOf(idMoeda.toString())
        try
        {
            escrita.delete(
                DatabaseHelper.TABELA_MOEDAS,
                "${DatabaseHelper.ID_MOEDA} = ?",
                args
            )
            Log.i("info_db", "Sucesso ao remover moeda")
        }
        catch (e: Exception)
        {
            e.printStackTrace()
            Log.i("info_db", "Erro ao remover moeda")
            return false
        }

        return true
    }

    override fun listar(): List<Moeda>
    {
        //VARIAVEIS
        val listaMoedas = mutableListOf<Moeda>()

        //selecao no banco de dados
        val sql = "SELECT ${DatabaseHelper.ID_MOEDA}, " +
                "${DatabaseHelper.DESCRICAO}, " +
                "  strftime('%d/%m/%Y', ${DatabaseHelper.DATA_CADASTRO}) , ${DatabaseHelper.DATA_CADASTRO} " +
                " FROM ${DatabaseHelper.TABELA_MOEDAS}"

        //recupera no banco de dados
        val cursor = leitura.rawQuery(sql, null)

        //captura os indices de cada coluna
        val indiceId = cursor.getColumnIndex(DatabaseHelper.ID_MOEDA)
        val indiceDescricao = cursor.getColumnIndex(DatabaseHelper.DESCRICAO)
        val indiceData = cursor.getColumnIndex(DatabaseHelper.DATA_CADASTRO)

        //percorre todos o cursos
        while (cursor.moveToNext())
        {
            val idMoeda = cursor.getInt(indiceId)
            val descricao = cursor.getString(indiceDescricao)
            val data = cursor.getString(indiceData)

            //adiciona uma moeda
            listaMoedas.add(
                Moeda(idMoeda, descricao, data)
            )
        }

       //retorna a lista
       return listaMoedas
    }
}