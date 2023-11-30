package com.example.desafiokleverapp.database

import com.example.desafiokleverapp.model.Moeda

interface IMoedaDAO
{
    fun salvar ( moeda: Moeda): Boolean
    fun atualizar ( moeda: Moeda): Boolean
    fun remover ( idMoeda: Int): Boolean
    fun listar(): List <Moeda>
}