package com.example.taskmaster.model.enuns

import kotlinx.serialization.Serializable


@Serializable
enum class Prioridade {
    IMPORTANTE,
    NORMAL,
    LEMBRETE

}