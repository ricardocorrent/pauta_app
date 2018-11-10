package com.ricardocorrent.capptanapp.model

import android.content.Context
import com.ricardocorrent.capptanapp.utils.ArquivoTextoUtils
import com.ricardocorrent.capptanapp.utils.JsonUtils

class Pauta {

    companion object {
        val NOME_DO_ARQUIVO = "lista_de_pautas"
    }

    var titulo: String? = null
    var descricao: String? = null
    var detalhes: String? = null
    var status: String? = null
//    var usuario: Usuario? = null
    var autor: String? = null

    constructor(titulo: String?, descricao: String?, detalhes: String?, status: String?, usuario: Usuario?) {
        this.titulo = titulo
        this.descricao = descricao
        this.detalhes = detalhes
        this.status = status
//        this.usuario = usuario
    }

    constructor(titulo: String?, descricao: String?, detalhes: String?, status: String?, nome: String?) {
        this.titulo = titulo
        this.descricao = descricao
        this.detalhes = detalhes
        this.status = status
        this.autor = nome
    }

}