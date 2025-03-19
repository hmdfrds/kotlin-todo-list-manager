package org.example.todolist.utils

import kotlinx.serialization.json.Json
import org.example.todolist.models.TodoItem
import java.io.File

object FileManager {
    private const val FILE_NAME = "todos.json"

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    fun loadTodos(): MutableList<TodoItem> {
        val file = File(FILE_NAME)
        return if (file.exists()) {
            try {
                val content = file.readText()
                if (content.isBlank()) mutableListOf() else json.decodeFromString(content)
            } catch (e: Exception) {
                println("\u001B[31mError loading todos: ${e.message}. Starting with an empty list.\u001B[0m")
                mutableListOf()
            }
        } else {
            println("\u001B[31mFile not found - a new file will be created upon saving.\u001B[0m")
            mutableListOf()
        }

    }

    fun saveTodos(todos: List<TodoItem>) {
        try {
            val file = File(FILE_NAME)
            file.writeText(json.encodeToString(todos))
        } catch (e: Exception) {
            println("\u001B[31mError saving todos: ${e.message}\u001B[0m")
        }
    }
}
