package com.ricardocorrent.capptanapp.utils

import android.content.Context
import android.widget.Toast
import org.json.JSONArray
import java.io.FileInputStream
import java.io.FileOutputStream

class ArquivoTextoUtils{
    fun carregarArquivo(nome: String, context: Context): JSONArray?{
        try {
            val arquivo : FileInputStream = context.openFileInput(nome)
            var str = StringBuilder()
            str.setLength(0)

            var linha = arquivo.bufferedReader().readLine()

            while (linha != null){
                str.append(linha).append("\n")
                linha = arquivo.bufferedReader().readLine()
            }

            return if(str.length > 0) JSONArray(str.toString()) else null
        }catch (e: Exception){
            e.printStackTrace()
            return null
        }
    }

    fun gravarArquivo(nome: String, context: Context, jsonArray: JSONArray?){
        try {
            val arquivo : FileOutputStream = context.openFileOutput(nome, Context.MODE_PRIVATE)
            if (jsonArray == null){
                arquivo.write("".toByteArray())
                arquivo.close()
                return
            }

            val jsonArrayString = jsonArray.toString()
            arquivo.write(jsonArrayString.toByteArray())
            arquivo.close()
            Toast.makeText(context, "Salvo", Toast.LENGTH_LONG).show()
        }catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(context, "Erro ao salvar", Toast.LENGTH_LONG).show()
        }
    }
}