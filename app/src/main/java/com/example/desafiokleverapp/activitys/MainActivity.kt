package com.example.desafiokleverapp.activitys

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafiokleverapp.adapter.MoedaAdapter
import com.example.desafiokleverapp.database.DaoMoeda
import com.example.desafiokleverapp.databinding.ActivityMainBinding
import com.example.desafiokleverapp.model.Moeda
import kotlinx.coroutines.internal.artificialFrame

class MainActivity : AppCompatActivity()
{
    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var listaMoedas = emptyList<Moeda>()
    private var moedaAdapter: MoedaAdapter ? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.fabAdicionarMoeda.setOnClickListener{
            // intent para chamar tela de adicionar moeda
            val intent =  Intent(this, AdicionarMoedaActivity:: class.java)
            startActivity(intent)
        }


        //Monta o RecyclerView
        moedaAdapter = MoedaAdapter(
            { id -> confirmarExclusao(id) },
            {moeda -> editar(moeda)  }
        )
        binding.rvMoedas.adapter = moedaAdapter
        binding.rvMoedas.layoutManager = LinearLayoutManager (this)

    }

    private fun editar(moeda: Moeda)
    {
        val intent = Intent(this, AdicionarMoedaActivity::class.java)
        intent.putExtra("moeda", moeda)
        startActivity(intent)
    }

    private fun confirmarExclusao(id: Int)
    {
        //cria um Dialog
        val alertaExclusao = AlertDialog.Builder (this)

        //configura o dialog
        alertaExclusao.setTitle("Confirmar exclusão")
        alertaExclusao.setMessage("Deseja mesmo excluir esta moeda ?")

        //configura o botão sim
        alertaExclusao.setPositiveButton("Sim") { _, _ ->

            val moedaDAO = DaoMoeda(this)

            //Verifica o sucesso do metodo de remover
            if (moedaDAO.remover(id))
            {
                //atualiza a lista novamente para mostrar alteração
                atualizarListaMoedas()

                //mostra a mensagem de sucesso
                Toast.makeText(this, "Sucesso ao remover moeda", Toast.LENGTH_SHORT).show()
            }
            else
            {
                //mostra a mensagem de erro
                Toast.makeText(this, "Erro ao remover moeda", Toast.LENGTH_SHORT).show()
            }

        }

        //configura o botão não
        alertaExclusao.setNegativeButton("Não") { _, _ ->
        }

        //mostra o dialog na tela
        alertaExclusao.create().show()
    }

    override fun onStart()
    {
        super.onStart()

        //atualiza lista
        atualizarListaMoedas()



    }

    private fun atualizarListaMoedas()
    {
        //instancia a lista e recupera os valores
        val daoMoeda = DaoMoeda(this)
        listaMoedas = daoMoeda.listar()

        //carrega a lista
        moedaAdapter?.adicionarLista( listaMoedas)

        if(listaMoedas.count() > 0)
        {
            binding.rvMoedas.visibility = View.VISIBLE
            binding.txtSemFavoritos.visibility = View.GONE
            binding.imgSemFavoritos.visibility = View.GONE
        }
        else
        {
            binding.rvMoedas.visibility = View.GONE
            binding.txtSemFavoritos.visibility = View.VISIBLE
            binding.imgSemFavoritos.visibility = View.VISIBLE
        }
    }
}