package com.example.taskmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskmaster.pages.HomeScreen
import com.example.taskmaster.pages.TelaAdicionarTarefaScreen
import com.example.taskmaster.pages.TelaDetalheTarefaScreen // Importe a nova tela
import com.example.taskmaster.ui.theme.TaskMasterTheme
import com.example.taskmaster.viewmodel.TarefaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskMasterTheme {
                val navController = rememberNavController()

                // 1. Instanciamos o ViewModel aqui. Ele será compartilhado por todas as telas abaixo.
                val tarefaViewModel: TarefaViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    // Rota da Tela Inicial
                    composable("home") {
                        HomeScreen(
                            viewModel = tarefaViewModel, // Passe o viewModel para a HomeScreen listar as tarefas
                            onNavegarParaAdicionar = {
                                navController.navigate("adicionar_tarefa")
                            },
                            onNavegarParaDetalhes = { tarefa ->
                                // Avisa o ViewModel qual tarefa foi clicada
                                tarefaViewModel.selecionarTarefa(tarefa)
                                // Navega para a tela de detalhes
                                navController.navigate("detalhes_tarefa")
                            }
                        )
                    }

                    // Rota para Adicionar Tarefa
                    composable("adicionar_tarefa") {
                        TelaAdicionarTarefaScreen(
                            onVoltar = {
                                // Ao voltar, recarregamos a lista caso uma tarefa nova tenha sido inserida
                                tarefaViewModel.carregarTarefas()
                                navController.popBackStack()
                            }
                        )
                    }

                    // 2. NOVA ROTA: Detalhes e Edição da Tarefa
                    composable("detalhes_tarefa") {
                        TelaDetalheTarefaScreen(
                            viewModel = tarefaViewModel,
                            onVoltar = {
                                // Ao voltar da edição, atualiza a lista da HomeScreen
                                tarefaViewModel.carregarTarefas()
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
                viewModel = viewModel(),
                onNavegarParaAdicionar = {},
                onNavegarParaDetalhes = {}
            )
        }
    }
}