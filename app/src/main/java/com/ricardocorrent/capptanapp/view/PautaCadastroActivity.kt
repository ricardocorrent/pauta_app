package com.ricardocorrent.capptanapp.view

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.ricardocorrent.capptanapp.R
import com.ricardocorrent.capptanapp.model.Pauta
import com.ricardocorrent.capptanapp.utils.ArquivoTextoUtils
import com.ricardocorrent.capptanapp.utils.JsonUtils
import kotlinx.android.synthetic.main.activity_pauta_cadastro.*

class PautaCadastroActivity : AppCompatActivity() {

    companion object {
        var USUARIO_NOME = ""
        var USUARIO_EMAIL = ""
        val STATUS_ABERTO = "ABERTO"
        val NOME_DO_ARQUIVO = "lista_de_pautas"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pauta_cadastro)


        //pegar o nome e email do usuario que foi logado
        USUARIO_NOME = intent.getStringExtra("nome")
        USUARIO_EMAIL = intent.getStringExtra("email")

        tvPautaCadastroAutor.text = USUARIO_NOME

        btnPautaCadastroFinalizar.setOnClickListener {
            if (salvarPauta()) {
                finish()
            }
        }
    }

    fun salvarPauta(): Boolean {

        if(validarCampos()){
            var arrayJson = ArquivoTextoUtils().carregarArquivo(NOME_DO_ARQUIVO, this)
            var arrayDePauta = ArrayList<Pauta>()

            if (arrayJson != null) {
                arrayDePauta = JsonUtils().jsonArrayParaArrayDePautas(arrayJson)!!
            }

            arrayDePauta.add(criarPauta())
            arrayJson = JsonUtils().arrayDePautasParaJsonArray(arrayDePauta)
            ArquivoTextoUtils().gravarArquivo(NOME_DO_ARQUIVO, this, arrayJson)
            return true
        }

        return false
    }

    fun validarCampos(): Boolean {

        if (edtPautaCadastroTitulo.text.toString().equals("")) {
            return false
        }
        if (edtPautaCadastroBreveDescricao.text.toString().equals("")) {
            return false
        }
        if (edtPautaCadastroDetalhes.text.toString().equals("")) {
            return false
        }
        return true
    }

    fun criarPauta(): Pauta {
        return Pauta(
            edtPautaCadastroTitulo.text.toString(),
            edtPautaCadastroBreveDescricao.text.toString(),
            edtPautaCadastroDetalhes.text.toString(),
            STATUS_ABERTO,
            USUARIO_NOME
        )
    }



}

