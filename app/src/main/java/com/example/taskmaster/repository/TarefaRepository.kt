package com.example.taskmaster.repository

import com.example.taskmaster.model.Tarefa
import com.example.taskmaster.model.enuns.Prioridade
import com.example.taskmaster.model.enuns.StatusTarefa
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.time.LocalDate


class TarefaRepository(
    private val storage: TarefaStorage
) {


    val tarefas: Flow<List<Tarefa>> =
        storage.tarefas


    suspend fun inicializar() {

        val lista =
            storage.tarefas.first()


        if (lista.isEmpty()) {

            storage.salvar(

                listOf(

                    Tarefa(
                        1,
                        "Estudar Jetpack Compose",
                        "Praticar criação de telas",
                        dataCadastro = LocalDate.now(),
                        dataInicio = null,
                        dataFinal = null
                    ),


                    Tarefa(
                        2,
                        "Ajustar TaskMaster",
                        "Corrigir erros do projeto",
                        StatusTarefa.AGUARDANDO,
                        Prioridade.IMPORTANTE,
                        LocalDate.now(),
                        LocalDate.now(),
                        null
                    ),


                    Tarefa(
                        3,
                        "Trabalho da faculdade",
                        "Finalizar projeto",
                        StatusTarefa.PENDENTE,
                        Prioridade.LEMBRETE,
                        LocalDate.now(),
                        LocalDate.now(),
                        LocalDate.now().plusDays(7)
                    )

                )

            )

        }

    }


    suspend fun adicionar(
        tarefa: Tarefa
    ) {

        val lista =
            storage.tarefas.first()


        storage.salvar(
            lista + tarefa
        )

    }


    suspend fun atualizar(
        tarefa: Tarefa
    ) {

        val lista =
            storage.tarefas.first()


        storage.salvar(

            lista.map {

                if (it.id == tarefa.id)
                    tarefa
                else
                    it

            }

        )

    }


    suspend fun deletar(
        id: Int
    ) {

        val lista =
            storage.tarefas.first()


        storage.salvar(

            lista.filter {
                it.id != id
            }

        )

    }


    suspend fun proximoId(): Int {

        val lista =
            storage.tarefas.first()


        return (lista.maxOfOrNull {
            it.id
        } ?: 0) + 1

    }

}