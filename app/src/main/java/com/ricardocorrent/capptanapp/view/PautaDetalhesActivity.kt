package com.ricardocorrent.capptanapp.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ricardocorrent.capptanapp.R
import com.ricardocorrent.capptanapp.model.Pauta
import com.ricardocorrent.capptanapp.utils.ArquivoTextoUtils
import com.ricardocorrent.capptanapp.utils.JsonUtils
import kotlinx.android.synthetic.main.activity_pauta_detalhes.*

class PautaDetalhesActivity : AppCompatActivity() {

    companion object {
        var POSITION = Int.MIN_VALUE
        val STATUS_ABERTO = "ABERTO"
        val STATUS_FECHADO = "FECHADO"
        var NOME_DO_ARQUIVO = ""
    }

    var arrayDePauta = ArrayList<Pauta>()
    var pauta: Pauta? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pauta_detalhes)

        POSITION = intent.getStringExtra("position").toInt()
        NOME_DO_ARQUIVO = intent.getStringExtra("NOME_DO_ARQUIVO")

        atualizarArrayDePautas()



        btnPautaDetalhesFinalizar.setOnClickListener {
            if (finalizarPauta()) {
                finish()
            }
        }

        btnPautaDetalhesReabrir.setOnClickListener {
            if (reabrirPauta()) {
                finish()
            }
        }
    }

    override fun onResume() {
        atualizarArrayDePautas()
        super.onResume()
    }

    fun atualizarArrayDePautas(){
        var arrayJson = ArquivoTextoUtils().carregarArquivo(NOME_DO_ARQUIVO, this)
        this.arrayDePauta = JsonUtils().jsonArrayParaArrayDePautas(arrayJson!!)!!

        this.pauta = buscarPauta(POSITION)

        edtPautaDetalhesDetalhes.text = this.pauta!!.detalhes
        tvPautaDetalhesAutor.text = this.pauta!!.autor

        if (this.pauta!!.status.equals("ABERTO")) {
            exibirAberto()
        } else {
            exibirFechado()
        }
    }

    fun exibirAberto() {
        btnPautaDetalhesFinalizar.visibility = View.VISIBLE
        btnPautaDetalhesReabrir.visibility = View.INVISIBLE
    }

    fun exibirFechado() {
        btnPautaDetalhesFinalizar.visibility = View.INVISIBLE
        btnPautaDetalhesReabrir.visibility = View.VISIBLE
    }

    fun buscarPauta(position: Int): Pauta {

        return this.arrayDePauta[position]

    }

    fun finalizarPauta(): Boolean {
        this.arrayDePauta.remove(this.pauta)

        var arrayJsonAberto = JsonUtils().arrayDePautasParaJsonArray(this.arrayDePauta)
        ArquivoTextoUtils().gravarArquivo("lista_de_pautas", this, arrayJsonAberto)

        var arrayJson = ArquivoTextoUtils().carregarArquivo("lista_de_pautas_fechadas", this)
        var arrayDePautaFechada = ArrayList<Pauta>()

        if(arrayJson != null){
            arrayDePautaFechada = JsonUtils().jsonArrayParaArrayDePautas(arrayJson)!!
        }

        this.pauta!!.status = STATUS_FECHADO
        arrayDePautaFechada!!.add(this.pauta!!)
        arrayJson = JsonUtils().arrayDePautasParaJsonArray(arrayDePautaFechada)
        ArquivoTextoUtils().gravarArquivo("lista_de_pautas_fechadas", this, arrayJson)
        return true
    }

    fun reabrirPauta(): Boolean {
        this.arrayDePauta.remove(this.pauta)
        this.pauta!!.status = STATUS_ABERTO

        var arrayJsonFechado = JsonUtils().arrayDePautasParaJsonArray(this.arrayDePauta)
        ArquivoTextoUtils().gravarArquivo("lista_de_pautas_fechadas", this, arrayJsonFechado)

        var arrayJson = ArquivoTextoUtils().carregarArquivo("lista_de_pautas", this)
        var arrayDePautaAberta = ArrayList<Pauta>()

        if(arrayJson != null){
            arrayDePautaAberta = JsonUtils().jsonArrayParaArrayDePautas(arrayJson)!!
        }
        arrayDePautaAberta!!.add(this.pauta!!)
        arrayJson = JsonUtils().arrayDePautasParaJsonArray(arrayDePautaAberta)
        ArquivoTextoUtils().gravarArquivo("lista_de_pautas", this, arrayJson)
        return true
    }
}
