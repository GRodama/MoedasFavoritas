package com.example.desafiokleverapp.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.desafiokleverapp.database.DaoMoeda
import com.example.desafiokleverapp.databinding.ActivityAdicionarMoedaBinding
import com.example.desafiokleverapp.databinding.ActivityMainBinding
import com.example.desafiokleverapp.model.Moeda

class AdicionarMoedaActivity : AppCompatActivity()
{
    private val binding by lazy{
        ActivityAdicionarMoedaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Recuperar moeda
        var moeda: Moeda? = null
        val bundle = intent.extras

        if (bundle != null)
        {
            moeda = bundle.getSerializable("moeda") as Moeda

            //seta a moeda passada como texto
            binding.editMoeda.setText(moeda.descricao)

            binding.textBox.setText("Detalhes Moeda")
        }

        binding.btnSalvar.setOnClickListener{

            //se o textBox nao esta vazio salvamos o tipo escrito
            if(binding.editMoeda.text.isNotEmpty())
            {
                //verificamos se a moeda existe para ou editarmos ou salvarmos
                if(moeda != null)
                {
                    //editamos a moeda
                    editarMoeda(moeda)
                }
                else
                {
                    //Salvamos a moeda
                    salvarMoeda()
                }

            }
            //se não mostramos o aviso para preencher campo
            else
            {
                Toast.makeText(this, "Preencha com uma moeda", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun editarMoeda(moeda: Moeda)
    {
        //pega o novo valor da descricao para atualizarmos
        val descricao = binding.editMoeda.text.toString()
        val moedaAtualizada = Moeda(moeda.idMoeda, descricao, "default")

        val daoMoeda = DaoMoeda (this)

        if( daoMoeda.atualizar(moedaAtualizada))
        {
            //mostra mensagem com sucesso
            Toast.makeText(this, "Moeda atualizada com sucesso", Toast.LENGTH_SHORT).show()

            //finaliza a activity
            finish()
        }
    }

    private fun salvarMoeda() {
        val descricao = binding.editMoeda.text.toString()
        val moeda = Moeda(-1, descricao, "default")
        val daoMoeda = DaoMoeda(this)

        // se foi possivel salvar
        if (daoMoeda.salvar(moeda)) {
            //mostra mensagem com sucesso
            Toast.makeText(this, "Moeda cadastrada com sucesso", Toast.LENGTH_SHORT).show()

            //finaliza a activity
            finish()
        }
    }

}