package com.ricardocorrent.capptanapp.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ricardocorrent.capptanapp.R
import kotlinx.android.synthetic.main.activity_usuario_detalhes.*

class UsuarioDetalhesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario_detalhes)

        tvUsuarioDetalhesNome.text = intent.getStringExtra("nome")
        tvUsuarioDetalhesEmail.text = intent.getStringExtra("email")
    }
}
