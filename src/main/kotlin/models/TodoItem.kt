package org.example.todolist.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class TodoItem(
    val id: Int,
    val title: String,
    val description: String? = null,
    val isComplete: Boolean = false,
    val createdAt: LocalDateTime,
)