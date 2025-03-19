package models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json
import org.example.todolist.models.TodoItem
import kotlin.test.Test
import kotlin.test.assertEquals

class TodoItemTest {
    @Test
    fun testTodoItemSerialization() {
        val todo = TodoItem(
            id = 1,
            title = "Buy groceries",
            description = "Milk, eggs, bread",
            isComplete = false,
            createdAt = LocalDateTime.parse("2025-03-19T07:38")
        )
        val jsonString = Json.encodeToString(todo)
        val decodedTodo = Json.decodeFromString<TodoItem>(jsonString)

        assertEquals(todo, decodedTodo)
    }
}