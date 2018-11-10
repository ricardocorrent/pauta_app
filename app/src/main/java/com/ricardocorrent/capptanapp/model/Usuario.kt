package com.ricardocorrent.capptanapp.model

class Usuario {
    var nome: String? = null
    var email: String? = null
    var senha: String? = null

    constructor(nome: String?, email: String?, senha: String?) {
        this.nome = nome
        this.email = email
        this.senha = senha
    }

    constructor()
}