package br.com.fiap.legaltech.model

import com.google.gson.annotations.SerializedName

data class UsuarioModel(
    @SerializedName("cpfUsuario") val cpfUsuario: String,
    @SerializedName("nomeUsuario") val nomeUsuario: String,
    @SerializedName("emailUsuario") val emailUsuario: String,
    @SerializedName("telefoneUsuario") val telefoneUsuario: String,
    @SerializedName("cidadeUsuario") val cidadeUsuario: String,
    @SerializedName("ufUsuario") val ufUsuario: String
)
