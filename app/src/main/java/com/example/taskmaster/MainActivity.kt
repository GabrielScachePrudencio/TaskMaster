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


import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskmaster.ui.theme.TaskMasterTheme
import com.example.taskmaster.pages.HomeScreen
import com.example.taskmaster.pages.TelaAdicionarTarefaScreen

// APAGAMOS OS OBJETOS @SERIALIZABLE DAQUI DE CIMA

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskMasterTheme {
                val navController = rememberNavController()

                // Usamos a string simples "home" como ponto de partida
                NavHost(
                    navController = navController,
                    startDestination = "home"
                ){
                    // Rota como String comum
                    composable("home") {
                        HomeScreen(
                            onNavegarParaAdicionar = {
                                navController.navigate("adicionar_tarefa")
                            }
                        )
                    }

                    // Rota como String comum
                    composable("adicionar_tarefa") {
                        TelaAdicionarTarefaScreen(
                            onVoltar = {
                                navController.popBackStack()
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