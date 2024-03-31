package br.com.fiap.legaltech.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.legaltech.R
import br.com.fiap.legaltech.components.Botao
import br.com.fiap.legaltech.components.BotaoOutline
import br.com.fiap.legaltech.components.Header
import br.com.fiap.legaltech.components.InputBox
import br.com.fiap.legaltech.model.UsuarioModel
import br.com.fiap.legaltech.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {

            //HEADER

            Header()

            //FORMULÁRIO LOGIN

            val context = LocalContext.current

            val apiService = RetrofitInstance.apiService

            var cpf by remember {
                mutableStateOf("")
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Card(
                    modifier = Modifier
                        .offset(y = (-20).dp)
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
                            text = "LOGIN",
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.preto_fundo),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        InputBox(
                            label = "CPF",
                            placeholder = "Digite seu CPF",
                            value = cpf,
                            kayboardType = KeyboardType.Number,
                            modifier = Modifier.fillMaxWidth(),
                            updateValue = { cpf = it },
                            visualTransformation = VisualTransformation.None
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Botao(
                                onClick = {

                                    val call = apiService.getUsuarioPorCpf(cpf)

                                    call.enqueue(object : Callback<UsuarioModel> {
                                        override fun onResponse(call: Call<UsuarioModel>, response: Response<UsuarioModel>) {
                                            if (response.isSuccessful) {
                                                val data = response.body()
                                                if (!data!!.cpfUsuario.isEmpty()){
                                                    navController.navigate("agendamento/${data.nomeUsuario}/${data.cpfUsuario}")
                                                }
                                            } else {
                                                Toast.makeText(context, "Não encontramos seu CPF, por favor se cadastre clicando no botão abaixo.", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        override fun onFailure(call: Call<UsuarioModel>, t: Throwable) {
                                            Toast.makeText(context, "Por favor, preencha seu CPF.", Toast.LENGTH_LONG).show();
                                            Log.e("TAG", "Erro na chamada à API: ${t.message}")
                                        }
                                    })
                                },
                                texto = "Login",
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                //FORMULÁRIO CADASTRO

                Card(
                    modifier = Modifier
                        .offset(y = (-20).dp)
                        .fillMaxWidth()
                        .padding(top = 8.dp),
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
                        Text(text = "Não é Cadastrado?")
                        Spacer(modifier = Modifier.height(16.dp))
                        BotaoOutline(
                            onClick = {
                                navController.navigate("cadCliente")
                            },
                            texto = "Cadastro Cliente",
                            modifier = Modifier.fillMaxWidth()
                        )
                        BotaoOutline(
                            onClick = {
                                navController.navigate("cadProfissional")
                            },
                            texto = "Cadastro Profissional",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}