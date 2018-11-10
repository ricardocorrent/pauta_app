package com.ricardocorrent.capptanapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.ricardocorrent.capptanapp.model.Pauta
import com.ricardocorrent.capptanapp.utils.ArquivoTextoUtils
import com.ricardocorrent.capptanapp.utils.JsonUtils
import com.ricardocorrent.capptanapp.view.PautaCadastroActivity
import com.ricardocorrent.capptanapp.view.PautaDetalhesActivity
import com.ricardocorrent.capptanapp.view.PautaFechadaActivity
import com.ricardocorrent.capptanapp.view.UsuarioDetalhesActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), PautaAdapter.ItemClickListener {

    companion object {
        var USUARIO_NOME = ""
        var USUARIO_EMAIL = ""
        val STATUS_ABERTO = "ABERTO"
        val STATUS_FECHADO = "FECHADO"
        val NOME_DO_ARQUIVO = "lista_de_pautas"
    }

    var listaDePauta: ArrayList<Pauta>? = null
    var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //pegar o email do usuario que foi logado
        USUARIO_NOME = intent.getStringExtra("nome")
        USUARIO_EMAIL = intent.getStringExtra("email")

        listarView()
    }

    override fun onResume() {
        listarView()
        super.onResume()
    }

    override fun onRestart() {
        listarView()
        super.onRestart()
    }

    fun listarView() {
        var arrayJson = ArquivoTextoUtils().carregarArquivo(NOME_DO_ARQUIVO, this)
        if (arrayJson == null || arrayJson.length() == 0) {
            ArquivoTextoUtils().gravarArquivo(NOME_DO_ARQUIVO, this, null)
        } else {
            this.listaDePauta = JsonUtils().jsonArrayParaArrayDePautas(arrayJson!!)
            val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            val adapter = PautaAdapter(this, this.listaDePauta!!, this)
            lista_pautas_abertas_recyclerview.layoutManager = lm
            lista_pautas_abertas_recyclerview.adapter = adapter
        }
    }

    override fun onItemClick(position: Int) {
        val itemClicado = listaDePauta!![position]
        var intent = Intent(this, PautaDetalhesActivity::class.java)
        intent.putExtra("position", position.toString())
        intent.putExtra("NOME_DO_ARQUIVO", NOME_DO_ARQUIVO)
        startActivity(intent)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_logout) {
            toast = Toast.makeText(this, "Logout", Toast.LENGTH_SHORT)
            toast?.show()
            finish()
            return true
        }
        if (item.itemId == R.id.menu_incluir_pauta) {
            incluirPauta()
            return true
        }
        if (item.itemId == R.id.menu_perfil) {
            exibirPerfil()
            return true
        }
        if (item.itemId == R.id.menu_pautas_fechadas) {
            exibirPautasFechadas()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun incluirPauta() {
        val intent = Intent(this, PautaCadastroActivity::class.java)
        intent.putExtra("nome", USUARIO_NOME)
        intent.putExtra("email", USUARIO_EMAIL)
        startActivity(intent)
    }

    fun exibirPerfil() {
        val intent = Intent(this, UsuarioDetalhesActivity::class.java)
        intent.putExtra("nome", USUARIO_NOME)
        intent.putExtra("email", USUARIO_EMAIL)
        startActivity(intent)
    }

    fun exibirPautasFechadas() {
        val intent = Intent(this, PautaFechadaActivity::class.java)
        intent.putExtra("nome", USUARIO_NOME)
        intent.putExtra("email", USUARIO_EMAIL)
        startActivity(intent)
    }
}
