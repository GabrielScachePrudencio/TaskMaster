package com.example.taskmaster.viewmodel

import android.view.View
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.taskmaster.model.Tarefa
import com.example.taskmaster.repository.TarefaRepository

class TarefaViewModel : ViewModel() {
    val tarefas = mutableStateListOf<Tarefa>()

    init {
        carregarTarefas()
    }

    fun carregarTarefas(){
        tarefas.clear()
        tarefas.addAll(TarefaRepository.listar())
    }
}