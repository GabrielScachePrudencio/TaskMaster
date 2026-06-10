package com.example.taskmaster.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.taskmaster.model.Tarefa
import com.example.taskmaster.model.enuns.Prioridade
import com.example.taskmaster.model.enuns.StatusTarefa
import com.example.taskmaster.repository.TarefaRepository
import java.time.LocalDate

class TarefaViewModel : ViewModel() {
    val tarefas = mutableStateListOf<Tarefa>()

    // Guarda a tarefa que está sendo exibida ou editada na tela de detalhes
    var tarefaSelecionada by mutableStateOf<Tarefa?>(null)
        private set

    // Controla se a tela está em modo de leitura (false) ou edição (true)
    var modoEdicao by mutableStateOf(false)
        private set

    init {
        carregarTarefas()
    }

    fun carregarTarefas() {
        tarefas.clear()
        tarefas.addAll(TarefaRepository.listar())
    }

    // Chame esta função ao clicar em uma tarefa na HomeScreen
    fun selecionarTarefa(tarefa: Tarefa) {
        tarefaSelecionada = tarefa
        modoEdicao = false
    }

    fun alternarModoEdicao() {
        modoEdicao = !modoEdicao
    }

    // Atualiza os campos de texto em tempo real enquanto o usuário digita
    fun atualizarCamposTexto(novoTitulo: String, novaDescricao: String) {
        tarefaSelecionada = tarefaSelecionada?.copy(
            titulo = novoTitulo,
            descricao = novaDescricao
        )
    }

    // Salva as alterações de texto e enums no repositório local e na lista da UI
    fun salvarEdicao() {
        tarefaSelecionada?.let { tarefaAtualizada ->
            val indice = tarefas.indexOfFirst { it.id == tarefaAtualizada.id }
            if (indice != -1) {
                tarefas[indice] = tarefaAtualizada
                TarefaRepository.atualizar(tarefaAtualizada)
            }
            modoEdicao = false
        }
    }

    fun trocarStatus(idAtividade: Int, statusNovo: StatusTarefa) {
        val indice = tarefas.indexOfFirst { it.id == idAtividade }
        if (indice != -1) {
            val tarefaAtualizada = tarefas[indice].copy(statusTarefa = statusNovo)
            tarefas[indice] = tarefaAtualizada
            TarefaRepository.atualizar(tarefaAtualizada)

            if (tarefaSelecionada?.id == idAtividade) {
                tarefaSelecionada = tarefaAtualizada
            }
        }
    }

    fun trocarPrioridade(idAtividade: Int, prioridadeNova: Prioridade) {
        val indice = tarefas.indexOfFirst { it.id == idAtividade }
        if (indice != -1) {
            val tarefaAtualizada = tarefas[indice].copy(prioridade = prioridadeNova)
            tarefas[indice] = tarefaAtualizada
            TarefaRepository.atualizar(tarefaAtualizada)

            if (tarefaSelecionada?.id == idAtividade) {
                tarefaSelecionada = tarefaAtualizada
            }
        }
    }


    fun atualizarDataInicio(idAtividade: Int, novaData: LocalDate?) {
        val indice = tarefas.indexOfFirst { it.id == idAtividade }
        if (indice != -1) {
            val tarefaAtualizada = tarefas[indice].copy(dataInicio = novaData)
            tarefas[indice] = tarefaAtualizada
            TarefaRepository.atualizar(tarefaAtualizada)
            if (tarefaSelecionada?.id == idAtividade) {
                tarefaSelecionada = tarefaAtualizada
            }
        }
    }

    fun atualizarDataFinal(idAtividade: Int, novaData: LocalDate?) {
        val indice = tarefas.indexOfFirst { it.id == idAtividade }
        if (indice != -1) {
            val tarefaAtualizada = tarefas[indice].copy(dataFinal = novaData)
            tarefas[indice] = tarefaAtualizada
            TarefaRepository.atualizar(tarefaAtualizada)
            if (tarefaSelecionada?.id == idAtividade) {
                tarefaSelecionada = tarefaAtualizada
            }
        }
    }


}