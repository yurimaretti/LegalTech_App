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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.legaltech.R
import br.com.fiap.legaltech.components.Botao
import br.com.fiap.legaltech.components.Header
import br.com.fiap.legaltech.components.InputBox
import br.com.fiap.legaltech.model.PrestadorModel
import br.com.fiap.legaltech.model.ServicoModel
import br.com.fiap.legaltech.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun AgendamentoScreen(navController: NavController, nome: String, cpf: String) {
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

            var listaProfissionais by remember {
                mutableStateOf(listOf<PrestadorModel>())
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
                        val call = apiService.getPrestador()

                        call.enqueue(object : Callback<List<PrestadorModel>> {
                            override fun onResponse(call: Call<List<PrestadorModel>>, response: Response<List<PrestadorModel>>) {
                                listaProfissionais = response.body()!!
                            }
                            override fun onFailure(call: Call<List<PrestadorModel>>, t: Throwable) {
                                Toast.makeText(context, "Por favor teste novamente.", Toast.LENGTH_LONG).show();
                                Log.e("TAG", "Erro na chamada à API: ${t.message}")
                            }
                        })
                    },
                    texto = "Consultar Profissionais para Agendamento",
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Botao(
                    onClick = {
                        navController.navigate("agendamentosRealizados/${nome}/${cpf}")
                    },
                    texto = "Consultar Agendamentos realizados",
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn() {
                    items(listaProfissionais) {
                        CardProfissional(it, cpf)
                    }
                }
            }
        }
    }
}

@Composable
fun CardProfissional(profissional: PrestadorModel, cpfUsuario: String, ) {

    var descricao by remember {
        mutableStateOf("")
    }
    var obs by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val apiService = RetrofitInstance.apiService

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
                text = profissional.nomePrestador,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.preto_fundo),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(text = "Especialidade: ${profissional.especialidade}")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(text = "E-mail: ${profissional.emailPrestador}")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(text = "Telefone: ${profissional.telefonePrestador}")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(text = "Cidade: ${profissional.cidadePrestador}")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(text = "Registro prof. : ${profissional.registroProfissional}")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(text = "Valor da hora: ${profissional.valorHora}")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            InputBox(
                label = "Descrição",
                placeholder = "Descreva sua necessidade",
                value = descricao,
                kayboardType = KeyboardType.Text,
                modifier = Modifier.fillMaxWidth(),
                updateValue = { descricao = it },
                visualTransformation = VisualTransformation.None
            )
            Spacer(modifier = Modifier.height(16.dp))
            InputBox(
                label = "Observação",
                placeholder = "Algo a mais que deseja comentar?",
                value = obs,
                kayboardType = KeyboardType.Text,
                modifier = Modifier.fillMaxWidth(),
                updateValue = { obs = it },
                visualTransformation = VisualTransformation.None
            )
            Spacer(modifier = Modifier.height(16.dp))
            Botao(
                onClick = {

                    val servico = ServicoModel("0", descricao, obs, "0", profissional.cpfPrestadorId, cpfUsuario)

                    val call = apiService.incluirServico(servico)

                    call.enqueue(object : Callback<ServicoModel> {
                        override fun onResponse(call: Call<ServicoModel>, response: Response<ServicoModel>) {
                            if (response.isSuccessful) {
                                Toast.makeText(context, "Serviço adicionado!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Ops, algo deu errado... Pode tentar de novo?", Toast.LENGTH_LONG).show();
                            }
                        }

                        override fun onFailure(call: Call<ServicoModel>, t: Throwable) {
                            Log.e("TAG", "Erro na chamada à API: ${t.message}")
                        }
                    })

                },
                texto = "Agendar",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}