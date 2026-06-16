package com.example.taskmaster.model.enuns

import kotlinx.serialization.Serializable


@Serializable
enum class StatusTarefa {
    CONCLUIDO,
    PENDENTE,
    AGUARDANDO

}