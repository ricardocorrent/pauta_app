package com.ricardocorrent.capptanapp.view

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.preference.PreferenceManager
import android.widget.Toast
import com.ricardocorrent.capptanapp.PautaAdapter
import com.ricardocorrent.capptanapp.R
import com.ricardocorrent.capptanapp.model.Pauta
import com.ricardocorrent.capptanapp.utils.ArquivoTextoUtils
import com.ricardocorrent.capptanapp.utils.JsonUtils
import kotlinx.android.synthetic.main.activity_pauta_fechada.*

class PautaFechadaActivity : AppCompatActivity(), PautaAdapter.ItemClickListener {

    companion object {
        var NOME_DO_ARQUIVO = "lista_de_pautas_fechadas"
    }

    var listaDePauta: ArrayList<Pauta>? = null
    var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pauta_fechada)

        listarView()
    }

    override fun onResume() {
        listarView()
        super.onResume()
    }

    fun listarView() {
        var arrayJson = ArquivoTextoUtils().carregarArquivo("lista_de_pautas_fechadas", this)

        if (arrayJson == null || arrayJson.length() == 0) {
            ArquivoTextoUtils().gravarArquivo("lista_de_pautas_fechadas", this, null)
        } else {
            this.listaDePauta = JsonUtils().jsonArrayParaArrayDePautas(arrayJson!!)
            val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            val adapter = PautaAdapter(this, this.listaDePauta!!, this)
            lista_pautas_fechadas_recyclerview.layoutManager = lm
            lista_pautas_fechadas_recyclerview.adapter = adapter
        }

    }

    override fun onItemClick(position: Int) {
        var intent = Intent(this, PautaDetalhesActivity::class.java)
        intent.putExtra("position", position.toString())
        intent.putExtra("NOME_DO_ARQUIVO", NOME_DO_ARQUIVO)
        startActivity(intent)
    }

}
