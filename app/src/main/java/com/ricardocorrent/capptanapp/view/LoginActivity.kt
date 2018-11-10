package com.ricardocorrent.capptanapp.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ricardocorrent.capptanapp.MainActivity
import com.ricardocorrent.capptanapp.R
import com.ricardocorrent.capptanapp.model.Usuario
import com.ricardocorrent.capptanapp.utils.ArquivoTextoUtils
import com.ricardocorrent.capptanapp.utils.JsonUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {
        var USUARIO_NOME = ""
        var USUARIO_EMAIL = ""
        val NOME_DO_ARQUIVO = "lista_de_usuarios"
    }

    var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (savedInstanceState != null) {

            toast = Toast.makeText(this, "onsaveinstancestate", Toast.LENGTH_SHORT)
            toast?.show()
            if (savedInstanceState.containsKey("loginNome")) {
                USUARIO_NOME = savedInstanceState.getString("loginNome")

            }
            if(savedInstanceState.containsKey("loginEmail")){
                USUARIO_EMAIL = savedInstanceState.getString("loginEmail")
            }
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("nome", USUARIO_NOME)
            intent.putExtra("email", USUARIO_EMAIL)
            startActivity(intent)

        }

        btnLoginCadastro.setOnClickListener {
            var intent = Intent(this, UsuarioCadastroActivity::class.java)
            startActivity(intent)
        }

        btnLoginEntrar.setOnClickListener {
            if (validarDados()) {
                if (login()) {
                    var intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("nome", USUARIO_NOME)
                    intent.putExtra("email", USUARIO_EMAIL)
                    startActivity(intent)
                }
            }
        }

        btnLoginRecuperarSenha.setOnClickListener {
            startActivity(Intent(this, RecuperarSenhaActivity::class.java))
        }
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString("loginNome", USUARIO_NOME)
        outState?.putString("loginEmail", USUARIO_EMAIL)
    }

    fun validarDados(): Boolean {
        if (edtLoginEmail.text.toString().equals("")) {
            toast = Toast.makeText(this, "Operação NEGADA", Toast.LENGTH_SHORT)
            toast?.show()
            return false
        }
        if (edtLoginSenha.text.toString().equals("")) {
            toast = Toast.makeText(this, "Operação NEGADA", Toast.LENGTH_SHORT)
            toast?.show()
            return false
        }

        return true
    }

    fun login(): Boolean {

        if (existeDados() != null) {
            var listaDeUsuario = existeDados()
            for (u in listaDeUsuario!!) {
                var usuarioExiste: Boolean = true
                if (!u.email.equals(edtLoginEmail.text.toString())) {
                    usuarioExiste = false
                }
                if (!u.senha.equals(edtLoginSenha.text.toString())) {
                    usuarioExiste = false
                }

                if(usuarioExiste){
                    USUARIO_NOME = u.nome!!
                    USUARIO_EMAIL = u.email!!
                    return true
                }
            }
        }
        toast = Toast.makeText(this, "Operação NEGADA", Toast.LENGTH_SHORT)
        toast?.show()
        return false

    }

    fun existeDados(): ArrayList<Usuario>? {
        val arrayJson = ArquivoTextoUtils().carregarArquivo(NOME_DO_ARQUIVO,this)
        if (arrayJson == null || arrayJson.length() == 0) {
//            ArquivoTextoUtils().gravarArquivo(this, null)
            toast = Toast.makeText(this, "Sem DADOS", Toast.LENGTH_SHORT)
            toast?.show()
            return null
        } else {
            return JsonUtils().jsonArrayParaArrayDeUsuarios(arrayJson!!)
        }
    }
}
