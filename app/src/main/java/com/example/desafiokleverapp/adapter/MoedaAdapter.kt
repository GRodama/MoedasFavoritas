package com.example.desafiokleverapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiokleverapp.databinding.ItemMoedaBinding
import com.example.desafiokleverapp.model.Moeda

class MoedaAdapter(
    val onClickExcluir: (Int) -> Unit,
    val onClickEditar: (Moeda) -> Unit

) : RecyclerView.Adapter<MoedaAdapter.MoedaViewHolder>() {

    private var listaMoedas: List<Moeda> = listOf()

    //metodo para add a lista
    fun adicionarLista (lista: List <Moeda>)
    {
        this.listaMoedas = lista

        //notifica que dados mudaram
        notifyDataSetChanged()
    }

    inner class MoedaViewHolder(itemBinding: ItemMoedaBinding)
        : RecyclerView.ViewHolder(itemBinding.root)
    {

        private val binding: ItemMoedaBinding

        init
        {
            binding = itemBinding
        }

        fun bind(moeda: Moeda)
        {
            //adiciona os dados nos campos
            binding.textDescricao.text = moeda.descricao
            binding.textData.text = moeda.dataCadastro

            //configura o click do botão excluir
            binding.btnExcluir.setOnClickListener{
               onClickExcluir (moeda.idMoeda)
            }

            binding.btnEditar.setOnClickListener{
                onClickEditar(moeda)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoedaViewHolder
    {
        val itemMoedaBinding = ItemMoedaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MoedaViewHolder(itemMoedaBinding)
    }

    override fun onBindViewHolder(holder: MoedaViewHolder, position: Int)
    {
        val moeda = listaMoedas[position]
        holder.bind(moeda)
    }

    override fun getItemCount(): Int
    {
        return listaMoedas.size
    }

}