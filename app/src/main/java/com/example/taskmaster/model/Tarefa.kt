package com.example.taskmaster.model

import com.example.taskmaster.model.enuns.Prioridade
import com.example.taskmaster.model.enuns.StatusTarefa
import java.time.LocalDate

data class Tarefa (
    val id: Int,
    var titulo: String,
    var descricao: String,
    var statusTarefa: StatusTarefa = StatusTarefa.PENDENTE,
    var prioridade: Prioridade = Prioridade.NORMAL,
    val dataCadastro: LocalDate,
    var dataInicio: LocalDate?,
    var dataFinal: LocalDate?
) {
}