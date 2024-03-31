package br.com.fiap.legaltech.model

import com.google.gson.annotations.SerializedName

data class ServicoModel(
    @SerializedName("id") val id: String = "",
    @SerializedName("descricao") val descricao: String = "",
    @SerializedName("observacao") val observacao: String = "",
    @SerializedName("nota") val nota: String = "",
    @SerializedName("cpfPrestadorId") val cpfPrestadorId: String = "",
    @SerializedName("cpfUsuario") val cpfUsuario: String = ""
)
