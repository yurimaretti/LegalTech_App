package br.com.fiap.legaltech.model

import com.google.gson.annotations.SerializedName

data class PrestadorModel(
    @SerializedName("cpfPrestadorId") val cpfPrestadorId: String = "",
    @SerializedName("nomePrestador") val nomePrestador: String = "",
    @SerializedName("emailPrestador") val emailPrestador: String = "",
    @SerializedName("telefonePrestador") val telefonePrestador: String = "",
    @SerializedName("cidadePrestador") val cidadePrestador: String = "",
    @SerializedName("ufPrestador") val ufPrestador: String = "",
    @SerializedName("registroProfissional") val registroProfissional: String = "",
    @SerializedName("valorHora") val valorHora: String = "",
    @SerializedName("especialidade") val especialidade: String = ""
)
