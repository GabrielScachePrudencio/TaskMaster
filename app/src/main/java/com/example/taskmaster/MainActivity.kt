package com.example.taskmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.taskmaster.ui.theme.TaskMasterTheme
import com.example.taskmaster.viewmodel.TarefaViewModel
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskmaster.pages.HomeScreen
import com.example.taskmaster.pages.TelaAdicionarTarefaScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
object AdicionarTarefaRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskMasterTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = HomeRoute
                ){
                    composable<HomeRoute> {
                        HomeScreen(
                            onNavegarParaAdicionar = {
                                navController.navigate(AdicionarTarefaRoute)
                            }
                        )
                    }

                    composable<AdicionarTarefaRoute> {
                    TelaAdicionarTarefaScreen (
                        onVoltar = {
                            navController.popBackStack() // Volta para a tela anterior
                        }
                    )
                }
                }

            }
        }
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun HomeScreenPreview() {
        TaskMasterTheme {
            HomeScreen(
                onNavegarParaAdicionar = {}
            )
        }
    }
}




