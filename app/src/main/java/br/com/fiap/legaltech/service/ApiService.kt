package br.com.fiap.legaltech.service

import br.com.fiap.legaltech.model.PrestadorModel
import br.com.fiap.legaltech.model.ServicoModel
import br.com.fiap.legaltech.model.UsuarioModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    //BASE URL - http://localhost:5000/api/Usuario/
    //EXEMPLO - http://localhost:5000/api/Usuario/1234

    @GET("/api/Usuario/{cpfUsuario}")
    fun getUsuarioPorCpf(@Path("cpfUsuario") cpfUsuario: String): Call<UsuarioModel>

    @GET("/api/Prestador")
    fun getPrestador(): Call<List<PrestadorModel>>

    @POST("/api/Usuario")
    fun incluirUsuario(@Body dados: UsuarioModel): Call<UsuarioModel>

    @POST("/api/Prestador")
    fun incluirPrestador(@Body dados: PrestadorModel): Call<PrestadorModel>

    @GET("/api/Servico")
    fun getServico(): Call<List<ServicoModel>>

    @POST("/api/Servico")
    fun incluirServico(@Body dados: ServicoModel): Call<ServicoModel>
}