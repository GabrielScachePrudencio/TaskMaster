package com.example.taskmaster.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskmaster.model.enuns.Prioridade
import com.example.taskmaster.model.enuns.StatusTarefa
import com.example.taskmaster.viewmodel.TarefaViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
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

    // Controla qual DatePicker está aberto: "inicio", "final" ou null
    var datePickerAlvo by remember { mutableStateOf<String?>(null) }

    if (tarefa == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Nenhuma tarefa encontrada.")
        }
        return
    }

    // DatePickerDialog reutilizável
    if (datePickerAlvo != null) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = when (datePickerAlvo) {
                "inicio" -> tarefa.dataInicio
                    ?.atStartOfDay(ZoneId.of("UTC"))
                    ?.toInstant()
                    ?.toEpochMilli()
                "final" -> tarefa.dataFinal
                    ?.atStartOfDay(ZoneId.of("UTC"))
                    ?.toInstant()
                    ?.toEpochMilli()
                else -> null
            }
        )

        DatePickerDialog(
            onDismissRequest = { datePickerAlvo = null },
            confirmButton = {
                TextButton(onClick = {
                    val millis = datePickerState.selectedDateMillis
                    if (millis != null) {   
                        val dataSelecionada = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.of("UTC"))
                            .toLocalDate()
                        when (datePickerAlvo) {
                            "inicio" -> viewModel.atualizarDataInicio(tarefa.id, dataSelecionada)
                            "final" -> viewModel.atualizarDataFinal(tarefa.id, dataSelecionada)
                        }
                    }
                    datePickerAlvo = null
                }) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = { datePickerAlvo = null }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
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
                        if (isEdicao) viewModel.salvarEdicao()
                        else viewModel.alternarModoEdicao()
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
                .verticalScroll(rememberScrollState())
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
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

            // --- DATA DE CADASTRO (somente leitura) ---
            Text(
                text = "Data de Cadastro: ${tarefa.dataCadastro.format(dateFormatter)}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- DATA DE INÍCIO ---
            CampoData(
                label = "Data de Início",
                data = tarefa.dataInicio,
                formatter = dateFormatter,
                isEdicao = isEdicao,
                onSelecionarData = { datePickerAlvo = "inicio" },
                onLimparData = { viewModel.atualizarDataInicio(tarefa.id, null) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // --- DATA FINAL ---
            CampoData(
                label = "Prazo Final",
                data = tarefa.dataFinal,
                formatter = dateFormatter,
                isEdicao = isEdicao,
                onSelecionarData = { datePickerAlvo = "final" },
                onLimparData = { viewModel.atualizarDataFinal(tarefa.id, null) }
            )
        }
    }
}

@Composable
private fun CampoData(
    label: String,
    data: LocalDate?,
    formatter: DateTimeFormatter,
    isEdicao: Boolean,
    onSelecionarData: () -> Unit,
    onLimparData: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "$label:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(6.dp))

        if (isEdicao) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onSelecionarData,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = data?.format(formatter) ?: "Selecionar data"
                    )
                }

                // Botão para limpar a data, só aparece se já tiver uma data definida
                if (data != null) {
                    TextButton(onClick = onLimparData) {
                        Text("Limpar", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        } else {
            if (data != null) {
                SuggestionChip(
                    onClick = {},
                    label = { Text(data.format(formatter)) }
                )
            } else {
                Text(
                    text = "Não definida",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}