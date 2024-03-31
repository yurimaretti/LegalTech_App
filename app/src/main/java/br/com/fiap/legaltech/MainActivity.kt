package br.com.fiap.legaltech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
//import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.fiap.legaltech.ui.theme.LegalTechTheme
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.fiap.legaltech.screens.AgendamentoRealizadosScreen
import br.com.fiap.legaltech.screens.AgendamentoScreen
import br.com.fiap.legaltech.screens.CadClienteScreen
import br.com.fiap.legaltech.screens.CadProfScreen
import br.com.fiap.legaltech.screens.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            LegalTechTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Start,
                                animationSpec = tween(1000)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.End,
                                animationSpec = tween(1000)
                            )
                        }
                    ){
                        composable(route = "login") { LoginScreen(navController) }
                        composable(route = "cadCliente") { CadClienteScreen(navController) }
                        composable(route = "cadProfissional") { CadProfScreen(navController) }
                        composable(
                            route = "agendamento/{nome}/{cpf}",
                            arguments = listOf(
                                navArgument("nome"){
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val nome: String? = it.arguments?.getString("nome")
                            val cpf: String? = it.arguments?.getString("cpf")
                            AgendamentoScreen(navController, nome!!, cpf!!)
                        }
                        composable(
                            route = "agendamentosRealizados/{nome}/{cpf}",
                            arguments = listOf(
                                navArgument("nome"){
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val nome: String? = it.arguments?.getString("nome")
                            val cpf: String? = it.arguments?.getString("cpf")
                            AgendamentoRealizadosScreen(navController, nome!!, cpf!!)
                        }
                    }
                }
            }
        }
    }
}