package com.ricardocorrent.capptanapp.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ricardocorrent.capptanapp.R
import com.ricardocorrent.capptanapp.model.Usuario
import com.ricardocorrent.capptanapp.utils.ArquivoTextoUtils
import com.ricardocorrent.capptanapp.utils.JsonUtils
import kotlinx.android.synthetic.main.activity_usuario_cadastro.*

class UsuarioCadastroActivity : AppCompatActivity() {

    companion object {
        val NOME_DO_ARQUIVO = "lista_de_usuarios"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario_cadastro)

        btnCadastroUsuarioFinalizar.setOnClickListener {
            if (salvarUsuario()) {
                finish()
            }
        }
    }

    fun salvarUsuario(): Boolean {
        if (validarUsuario()) {

            var arrayJson = ArquivoTextoUtils().carregarArquivo(NOME_DO_ARQUIVO,this)
            var arrayDeUsuario = ArrayList<Usuario>()

            if (arrayJson != null) {
                arrayDeUsuario = JsonUtils().jsonArrayParaArrayDeUsuarios(arrayJson)!!
            }

            arrayDeUsuario.add(criarUsuario())
            arrayJson = JsonUtils().arrayDeUsuariosParaJsonArray(arrayDeUsuario)
            ArquivoTextoUtils().gravarArquivo(NOME_DO_ARQUIVO, this, arrayJson)
            return true
        } else {
            return false
        }
    }

    fun criarUsuario(): Usuario {
        return Usuario(
            edtUsuarioCadastroNome.text.toString(),
            edtUsuarioCadastroEmail.text.toString(),
            edtUsuarioCadastroSenha.text.toString()
        )
    }

    fun validarUsuario(): Boolean {
        try {
            if (edtUsuarioCadastroNome.text.toString().equals("")) {
                Toast.makeText(this, "Informe Nome", Toast.LENGTH_SHORT).show()
                return false
            }
            if (edtUsuarioCadastroEmail.text.toString().equals("")) {
                Toast.makeText(this, "Informe Email", Toast.LENGTH_SHORT).show()
                return false
            }
            if (edtUsuarioCadastroSenha.text.toString().equals("")) {
                Toast.makeText(this, "Informe Senha", Toast.LENGTH_SHORT).show()
                return false
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}
