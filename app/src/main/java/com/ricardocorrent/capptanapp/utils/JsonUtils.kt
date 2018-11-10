package com.ricardocorrent.capptanapp.utils

import com.ricardocorrent.capptanapp.model.Pauta
import com.ricardocorrent.capptanapp.model.Usuario
import org.json.JSONArray
import org.json.JSONObject
import kotlin.Exception

class JsonUtils {

    fun usuarioParaJson(usuario: Usuario): JSONObject? {

        var jsonUsuario = JSONObject()

        try {
            jsonUsuario.put("nome", usuario.nome)
            jsonUsuario.put("email", usuario.email)
            jsonUsuario.put("senha", usuario.senha)
            return jsonUsuario
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun pautaParaJson(pauta: Pauta): JSONObject? {
        var jsonPauta = JSONObject()
        try {
            jsonPauta.put("titulo", pauta.titulo)
            jsonPauta.put("descricao", pauta.descricao)
            jsonPauta.put("detalhes", pauta.detalhes)
            jsonPauta.put("status", pauta.status)
            jsonPauta.put("autor", pauta.autor)

            return jsonPauta
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun jsonParaUsuario(json: JSONObject): Usuario? {
        try {
            return Usuario(
                json.getString("nome"),
                json.getString("email"),
                json.getString("senha")
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun jsonParaPauta(json: JSONObject): Pauta? {
        try {

            return Pauta(
                json.getString("titulo"),
                json.getString("descricao"),
                json.getString("detalhes"),
                json.getString("status"),
                json.getString("autor")
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun arrayDeUsuariosParaJsonArray(arrayUsuario: ArrayList<Usuario>): JSONArray? {
        try {
            var jsonArray = JSONArray()

            for (u in arrayUsuario) {
                var usuarioJson = JSONObject()
                usuarioJson.put("nome", u.nome)
                usuarioJson.put("email", u.email)
                usuarioJson.put("senha", u.senha)

                jsonArray.put(usuarioJson)

            }
            return jsonArray
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun arrayDePautasParaJsonArray(arrayPauta: ArrayList<Pauta>): JSONArray? {
        try {
            var jsonArray = JSONArray()

            for (p in arrayPauta) {
                var pautaJson = JSONObject()
                pautaJson.put("titulo", p.titulo)
                pautaJson.put("descricao", p.descricao)
                pautaJson.put("detalhes", p.detalhes)
                pautaJson.put("status", p.status)
                pautaJson.put("autor", p.autor)

                jsonArray.put(pautaJson)

            }
            return jsonArray
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun jsonArrayParaArrayDeUsuarios(arrayJson: JSONArray): ArrayList<Usuario>? {
        try {
            var listaDeUsuario = ArrayList<Usuario>()
            for (i in 0..(arrayJson.length() - 1)) {
                var usuarioJson = arrayJson.getJSONObject(i)
                var usuario = Usuario(
                    usuarioJson.getString("nome"),
                    usuarioJson.getString("email"),
                    usuarioJson.getString("senha")
                )
                listaDeUsuario.add(usuario)
            }
            return listaDeUsuario
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun jsonArrayParaArrayDePautas(arrayJson: JSONArray): ArrayList<Pauta>? {
        try {
            var listaDePauta = ArrayList<Pauta>()
            for (i in 0..(arrayJson.length() - 1)) {
                var pautaJson = arrayJson.getJSONObject(i)
                var pauta = Pauta(
                    pautaJson.getString("titulo"),
                    pautaJson.getString("descricao"),
                    pautaJson.getString("detalhes"),
                    pautaJson.getString("status"),
                    pautaJson.getString("autor")
                )
                listaDePauta.add(pauta)
            }
            return listaDePauta
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun jsonArrayParaArrayDePautasPorStatus(arrayJson: JSONArray, status: String): ArrayList<Pauta>? {
        try {
            var listaDePauta = ArrayList<Pauta>()
            for (i in 0..(arrayJson.length() - 1)) {
                var pautaJson = arrayJson.getJSONObject(i)
                if (pautaJson.getString("status").equals(status)) {
                    var pauta = Pauta(
                        pautaJson.getString("titulo"),
                        pautaJson.getString("descricao"),
                        pautaJson.getString("detalhes"),
                        pautaJson.getString("status"),
                        pautaJson.getString("autor")
                    )
                    listaDePauta.add(pauta)
                }
            }
            return listaDePauta
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}