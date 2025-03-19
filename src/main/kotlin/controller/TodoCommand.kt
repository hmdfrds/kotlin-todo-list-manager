package org.example.todolist.controller

sealed class TodoCommand {
    data class AddTodo(val title: String, val description: String?) : TodoCommand()
    data object ListTodos : TodoCommand()
    data class MarkTodoCompleted(val id: Int) : TodoCommand()
    data class RemoveTodo(val id: Int) : TodoCommand()
    data object Exit : TodoCommand()
}