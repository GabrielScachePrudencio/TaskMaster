package com.example.taskmaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskmaster.repository.TarefaRepository


class TarefaViewModelFactory(
    private val repository: TarefaRepository
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {


        if (
            modelClass.isAssignableFrom(
                TarefaViewModel::class.java
            )
        ) {


            @Suppress("UNCHECKED_CAST")
            return TarefaViewModel(repository) as T


        }


        throw IllegalArgumentException(
            "ViewModel desconhecido"
        )


    }


}