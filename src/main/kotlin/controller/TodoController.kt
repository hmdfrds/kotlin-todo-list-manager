package org.example.todolist.controller

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.todolist.models.TodoItem
import org.example.todolist.utils.FileManager

object TodoController {
    fun processCommand(command: TodoCommand): String {
        val todos = FileManager.loadTodos()
        return when (command) {
            is TodoCommand.AddTodo -> {
                if (command.title.isBlank()) {
                    "Title cannot be empty."
                } else {
                    val newId = if (todos.isEmpty()) 1 else todos.maxOf { it.id } + 1
                    val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    val newTodo = TodoItem(newId, command.title, command.description, false, currentTime)
                    todos.add(newTodo)
                    FileManager.saveTodos(todos)
                    "Todo added successfully!"
                }
            }

            TodoCommand.ListTodos -> {
                if (todos.isEmpty()) {
                    "No todos available."
                } else {
                    val builder = StringBuilder()

                    builder.append("ID   | Title                 | Status     | Created At\n")
                    builder.append("-------------------------------------------------------\n")
                    todos.forEach { todo ->
                        val status = if (todo.isComplete) "Completed" else "Pending"
                        val createdAtStr = todo.createdAt.toString().substring(0, 16)
                        builder.append(
                            String.format(
                                "%-4d | %-21s | %-10s | %s\n",
                                todo.id,
                                todo.title,
                                status,
                                createdAtStr
                            )
                        )
                    }
                    builder.append("-------------------------------------------------------")
                    builder.toString()
                }
            }

            is TodoCommand.MarkTodoCompleted -> {
                val todo = todos.find { it.id == command.id }
                if (todo == null) {
                    "Todo with ID ${command.id} not found"
                } else {
                    todo.isComplete = true
                    FileManager.saveTodos(todos)
                    "Todo marked as completed!"
                }
            }

            is TodoCommand.RemoveTodo -> {
                val todo = todos.find { it.id == command.id }
                if (todo == null) {
                    "Todo with ID ${command.id} not found."
                } else {
                    todos.remove(todo)
                    FileManager.saveTodos(todos)
                    "Todo removed successfully!"
                }
            }

            TodoCommand.Exit -> "Exiting application."
        }
    }
}