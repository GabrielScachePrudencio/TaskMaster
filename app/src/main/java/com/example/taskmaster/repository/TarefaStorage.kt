package com.example.taskmaster.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.taskmaster.model.Tarefa
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


private val Context.dataStore by preferencesDataStore(
    name = "tarefas"
)


class TarefaStorage(
    private val context: Context
) {


    private val json =
        Json {
            ignoreUnknownKeys = true
        }


    private val chaveTarefas =
        stringPreferencesKey(
            "lista_tarefas"
        )



    val tarefas: Flow<List<Tarefa>> =

        context.dataStore.data.map {

            val dados =
                it[chaveTarefas] ?: "[]"


            json.decodeFromString(
                dados
            )

        }



    suspend fun salvar(
        tarefas: List<Tarefa>
    ){

        context.dataStore.edit {

            it[chaveTarefas] =
                json.encodeToString(tarefas)

        }

    }

}