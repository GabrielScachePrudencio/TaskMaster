package com.example.taskmaster.repository

import android.R.bool
import com.example.taskmaster.model.Tarefa
import java.time.LocalDate

object TarefaRepository {
    private val tarefas = mutableListOf<Tarefa>(
        Tarefa(
            id = 1,
            titulo = "Estudar Jetpack Compose",
            descricao = "Praticar a criação de telas e LazyColumn",
            dataCadastro = LocalDate.now(),
            dataInicio = null,
            dataFinal = null
        ),
        Tarefa(
            id = 2,
            titulo = "Ajustar o TaskMaster",
            descricao = "Corrigir os erros de compilação do projeto",
            dataCadastro = LocalDate.now(),
            dataInicio = null,
            dataFinal = null
        )
    )
    fun listar() : List<Tarefa>{
        return tarefas
    }

    fun add(tarefa: Tarefa) : Boolean {
        if(tarefa != null){
            tarefas.add(tarefa)
            return true
        } else {
            return false
        }
    }

    fun deletar(id: Int) : Boolean{
        val tarefa: Tarefa? = findById(id)

        if(tarefa != null){
            tarefas.remove(tarefa)
            return true
        }

        return false
    }

    fun findById(id: Int) : Tarefa?{
        return tarefas.find { it.id == id }
    }

    fun atualizar(tarefaAtualizada: Tarefa) {
        val indice = tarefas.indexOfFirst { it.id == tarefaAtualizada.id }

        if (indice != -1) {
            tarefas[indice] = tarefaAtualizada
        }
    }

}