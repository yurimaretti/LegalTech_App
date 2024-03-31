package br.com.fiap.legaltech.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.legaltech.R
import br.com.fiap.legaltech.components.Botao
import br.com.fiap.legaltech.components.Header
import br.com.fiap.legaltech.model.ServicoModel
import br.com.fiap.legaltech.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun AgendamentoRealizadosScreen(navController: NavController, nome: String, cpf: String) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Header()

            //APRESENTAÇÃO

            val context = LocalContext.current
            val apiService = RetrofitInstance.apiService

            var listaServicos by remember {
                mutableStateOf(listOf<ServicoModel>())
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Bem vindo $nome!",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.preto_fundo),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Botao(
                    onClick = {
                        val call = apiService.getServico()

                        call.enqueue(object : Callback<List<ServicoModel>> {
                            override fun onResponse(call: Call<List<ServicoModel>>, response: Response<List<ServicoModel>>) {
                                listaServicos = response.body()!!
                            }
                            override fun onFailure(call: Call<List<ServicoModel>>, t: Throwable) {
                                Toast.makeText(context, "Por favor teste novamente.", Toast.LENGTH_LONG).show();
                                Log.e("TAG", "Erro na chamada à API: ${t.message}")
                            }
                        })
                    },
                    texto = "Consultar Agendamentos realizados",
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn() {
                    items(listaServicos) {
                        CardAgendamentosRealizados(it)
                    }
                }
            }
        }
    }
}

@Composable
fun CardAgendamentosRealizados(servico: ServicoModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults
            .cardColors(colorResource(id = R.color.cor_card)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(
                vertical = 16.dp,
                horizontal = 32.dp
            )
        ) {
            Text(
                text = "AGENDAMENTOS CONCLUÍDOS",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.preto_fundo),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Column {

                    Text(text = "CPF do Profissional agendado: ${servico.cpfPrestadorId}", modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "CPF do Usuário que agendou: ${servico.cpfUsuario}", modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Descrição do serviço solicitado: ${servico.descricao}", modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Demais observações: ${servico.observacao}", modifier = Modifier.fillMaxWidth())

            }
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}