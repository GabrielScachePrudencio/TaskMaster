package com.example.taskmaster.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskmaster.model.enuns.Prioridade
import com.example.taskmaster.model.enuns.StatusTarefa
import com.example.taskmaster.viewmodel.TarefaViewModel
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaDetalheTarefaScreen(
    viewModel: TarefaViewModel,
    onVoltar: () -> Unit
) {
    val tarefa = viewModel.tarefaSelecionada
    val isEdicao = viewModel.modoEdicao
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    if (tarefa == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            Text("Nenhuma tarefa encontrada.")
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEdicao) "Editar Tarefa" else "Detalhes da Tarefa") },
                navigationIcon = {
                    IconButton(onClick = onVoltar) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (isEdicao) {
                            viewModel.salvarEdicao()
                        } else {
                            viewModel.alternarModoEdicao()
                        }
                    }) {
                        Icon(
                            imageVector = if (isEdicao) Icons.Default.Check else Icons.Default.Edit,
                            contentDescription = if (isEdicao) "Salvar" else "Editar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()) // Permite rolar a tela se a descrição for longa
        ) {

            // --- CAMPO TÍTULO ---
            OutlinedTextField(
                value = tarefa.titulo,
                onValueChange = { viewModel.atualizarCamposTexto(it, tarefa.descricao) },
                label = { Text("Título") },
                enabled = isEdicao,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- CAMPO DESCRIÇÃO ---
            OutlinedTextField(
                value = tarefa.descricao,
                onValueChange = { viewModel.atualizarCamposTexto(tarefa.titulo, it) },
                label = { Text("Descrição") },
                enabled = isEdicao,
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))

            // --- SELEÇÃO DE STATUS ---
            Text("Status:", style = MaterialTheme.typography.titleMedium)
            if (isEdicao) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    StatusTarefa.entries.forEach { status ->
                        FilterChip(
                            selected = tarefa.statusTarefa == status,
                            onClick = { viewModel.trocarStatus(tarefa.id, status) },
                            label = { Text(status.name) }
                        )
                    }
                }
            } else {
                SuggestionChip(onClick = {}, label = { Text(tarefa.statusTarefa.name) })
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- SELEÇÃO DE PRIORIDADE ---
            Text("Prioridade:", style = MaterialTheme.typography.titleMedium)
            if (isEdicao) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Prioridade.entries.forEach { prioridade ->
                        FilterChip(
                            selected = tarefa.prioridade == prioridade,
                            onClick = { viewModel.trocarPrioridade(tarefa.id, prioridade) },
                            label = { Text(prioridade.name) }
                        )
                    }
                }
            } else {
                SuggestionChip(onClick = {}, label = { Text(tarefa.prioridade.name) })
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))

            // --- EXIBIÇÃO DE DATAS ---
            Text(
                text = "Data de Cadastro: ${tarefa.dataCadastro.format(dateFormatter)}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            tarefa.dataInicio?.let {
                Text(
                    text = "Data de Início: ${it.format(dateFormatter)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            tarefa.dataFinal?.let {
                Text(
                    text = "Prazo Final: ${it.format(dateFormatter)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}