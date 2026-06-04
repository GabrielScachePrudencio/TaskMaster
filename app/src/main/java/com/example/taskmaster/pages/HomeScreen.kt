package com.example.taskmaster.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskmaster.viewmodel.TarefaViewModel

@Composable
fun HomeScreen(
    onNavegarParaAdicionar: () -> Unit,
    viewModel: TarefaViewModel = viewModel()
) {
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {onNavegarParaAdicionar()}
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar Tarefa")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)
        ) {
            items(viewModel.tarefas){
                tarefa ->
                Text(
                    text = tarefa.titulo,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }

    }
}