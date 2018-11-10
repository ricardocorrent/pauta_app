package com.ricardocorrent.capptanapp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ricardocorrent.capptanapp.model.Pauta

class PautaAdapter : RecyclerView.Adapter<PautaAdapter.PautaViewHolder> {

    var listaDePauta = ArrayList<Pauta>()
    var context: Context
    val itemClickListener: ItemClickListener

    constructor(context: Context, listaDePauta: ArrayList<Pauta>, itemClickListener: ItemClickListener) : super() {
        this.listaDePauta = listaDePauta
        this.context = context
        this.itemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PautaViewHolder {
        val pautaItem = LayoutInflater.from(context).inflate(R.layout.item_pauta, parent, false)

        return PautaViewHolder(pautaItem)
    }

    override fun getItemCount(): Int {
        return listaDePauta.size
    }

    override fun onBindViewHolder(holder: PautaViewHolder, position: Int) {
        val pauta = listaDePauta[position]

        holder.tv_titulo!!.text = pauta.titulo
        holder.tv_descricao!!.text = pauta.descricao
    }

    inner class PautaViewHolder : RecyclerView.ViewHolder, View.OnClickListener {

        val tv_titulo: TextView?
        val tv_descricao: TextView?

        constructor(itemView: View?) : super(itemView!!) {
            this.tv_titulo = itemView?.findViewById<TextView>(R.id.tvItemTitulo)
            this.tv_descricao = itemView?.findViewById<TextView>(R.id.tvItemDescricao)

            itemView?.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val itemClicado = adapterPosition
            itemClickListener.onItemClick(itemClicado)
        }

    }

    interface ItemClickListener {
        fun onItemClick(position: Int)
    }
}