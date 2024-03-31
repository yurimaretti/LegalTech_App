package br.com.fiap.legaltech.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.legaltech.R
import br.com.fiap.legaltech.components.Botao
import br.com.fiap.legaltech.components.BotaoOutline
import br.com.fiap.legaltech.components.Header
import br.com.fiap.legaltech.components.InputBox
import br.com.fiap.legaltech.enum.EstadosBrasileiros
import br.com.fiap.legaltech.model.PrestadorModel
import br.com.fiap.legaltech.model.UsuarioModel
import br.com.fiap.legaltech.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadProfScreen(navController: NavController) {
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

            //FORMULÁRIO CADASTRO

            val context = LocalContext.current

            var nome by remember {
                mutableStateOf("")
            }
            var email by remember {
                mutableStateOf("")
            }
            var telefone by remember {
                mutableStateOf("")
            }
            var cpf by remember {
                mutableStateOf("")
            }
            var estado by remember {
                mutableStateOf("")
            }
            var cidade by remember {
                mutableStateOf("")
            }
            var registro by remember {
                mutableStateOf("")
            }
            var especialidade by remember {
                mutableStateOf("")
            }
            var valorHora by remember {
                mutableStateOf("")
            }
            var isExpanded by remember {
                mutableStateOf(false)
            }
            var lista = EstadosBrasileiros.values()

            val apiService = RetrofitInstance.apiService
            val prestador = PrestadorModel(cpf, nome, email, telefone, cidade, estado, registro, valorHora, especialidade)

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
                            text = "CADASTRO PROFISSIONAL",
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.preto_fundo),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        InputBox(
                            label = "Nome",
                            placeholder = "Digite seu Nome",
                            value = nome,
                            kayboardType = KeyboardType.Text,
                            modifier = Modifier.fillMaxWidth(),
                            updateValue = { nome = it },
                            visualTransformation = VisualTransformation.None
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        InputBox(
                            label = "Email",
                            placeholder = "Digite seu E-mail",
                            value = email,
                            kayboardType = KeyboardType.Email,
                            modifier = Modifier.fillMaxWidth(),
                            updateValue = { email = it },
                            visualTransformation = VisualTransformation.None
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        InputBox(
                            label = "CPF",
                            placeholder = "Digite seu CPF",
                            value = cpf,
                            kayboardType = KeyboardType.Number,
                            modifier = Modifier.fillMaxWidth(),
                            updateValue = { cpf = it },
                            visualTransformation = VisualTransformation.None
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        InputBox(
                            label = "Telefone",
                            placeholder = "Digite seu Telefone",
                            value = telefone,
                            kayboardType = KeyboardType.Text,
                            modifier = Modifier.fillMaxWidth(),
                            updateValue = { telefone = it },
                            visualTransformation = VisualTransformation.None
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Estado")
                        Spacer(modifier = Modifier.height(16.dp))
                        ExposedDropdownMenuBox(
                            expanded = isExpanded,
                            onExpandedChange = {
                                isExpanded = it
                            }
                        ) {
                            OutlinedTextField(
                                value = estado,
                                onValueChange = {},
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults
                                        .TrailingIcon(expanded = isExpanded)
                                },
                                modifier = Modifier.menuAnchor(),
                                readOnly = true,
                                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                shape = RoundedCornerShape(16.dp)
                            )
                            ExposedDropdownMenu(
                                expanded = isExpanded,
                                onDismissRequest = { isExpanded = !isExpanded }
                            ) {
                                lista.forEach{
                                    DropdownMenuItem(
                                        text = {
                                            Text(text = it.toString())
                                        },
                                        onClick = {
                                            estado = it.toString()
                                            isExpanded = !isExpanded
                                        }
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        InputBox(
                            label = "Cidade",
                            placeholder = "Digite Cidade em que exerce",
                            value = cidade,
                            kayboardType = KeyboardType.Text,
                            modifier = Modifier.fillMaxWidth(),
                            updateValue = { cidade = it },
                            visualTransformation = VisualTransformation.None
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        InputBox(
                            label = "Registro Profissional",
                            placeholder = "Digite seu Registro",
                            value = registro,
                            kayboardType = KeyboardType.Text,
                            modifier = Modifier.fillMaxWidth(),
                            updateValue = { registro = it },
                            visualTransformation = VisualTransformation.None
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        InputBox(
                            label = "Especialidade",
                            placeholder = "Digite sua Especialidade",
                            value = especialidade,
                            kayboardType = KeyboardType.Text,
                            modifier = Modifier.fillMaxWidth(),
                            updateValue = { especialidade = it },
                            visualTransformation = VisualTransformation.None
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        InputBox(
                            label = "Valor da Hora",
                            placeholder = "Digite o Valor",
                            value = valorHora,
                            kayboardType = KeyboardType.Decimal,
                            modifier = Modifier.fillMaxWidth(),
                            updateValue = { valorHora = it },
                            visualTransformation = VisualTransformation.None
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Botao(
                                onClick = {

                                    val call = apiService.incluirPrestador(prestador)

                                    call.enqueue(object : Callback<PrestadorModel> {
                                        override fun onResponse(call: Call<PrestadorModel>, response: Response<PrestadorModel>) {
                                            if (response.isSuccessful) {
                                                Toast.makeText(context, "Prestador adicionado!", Toast.LENGTH_LONG).show();
                                                navController.navigate("login")
                                            } else {
                                                Toast.makeText(context, "Ops, algo deu errado... Pode tentar de novo?", Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        override fun onFailure(call: Call<PrestadorModel>, t: Throwable) {
                                            Log.e("TAG", "Erro na chamada à API: ${t.message}")
                                        }
                                    })

                                },
                                texto = "Cadastrar",
                                modifier = Modifier.width(130.dp)
                            )
                            BotaoOutline(
                                onClick = { navController.navigate("login") },
                                texto = "Cancelar",
                                modifier = Modifier.width(130.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CadProfScreenPreview() {
    CadProfScreen(rememberNavController())
}