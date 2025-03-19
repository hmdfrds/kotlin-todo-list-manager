package org.example.todolist.ui

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.todolist.models.TodoItem
import org.example.todolist.utils.FileManager
import kotlin.system.exitProcess

object UIManager {
    private fun clearConsole() {
        print("\u001B[H\u001B[2J")
        System.out.flush()
    }

    fun displayMainMenu() {
        while (true) {
            clearConsole()
            println("-------------------------------------")
            println("       Kotlin Todo List Manager")
            println("-------------------------------------")
            println("1. Add New Todo")
            println("2. List All Todos")
            println("3. Mark Todo as Completed")
            println("4. Remove Todo")
            println("5. Exit")
            print("Enter your choice: ")
            when (readlnOrNull()?.trim()) {
                "1" -> addTodoScreen()
                "2" -> listTodoScreen()
                "3" -> markTodoAsCompletedScreen()
                "4" -> removeTodoScreen()
                "5" -> {
                    if (exitConfirmationScreen()) {
                        println("Exiting application. Goodbye!")
                        exitProcess(0)
                    }
                }
            }
        }
    }

    private fun addTodoScreen() {
        clearConsole()
        println("-------------------------------------")
        println("           Add New Todo")
        println("-------------------------------------")
        print("Enter the title of the Todo: ")
        val title = readlnOrNull()?.trim()
        if (title.isNullOrEmpty()) {
            println("\u001B[31mTitle cannot be empty. \u001B[0m")
            println("Press Enter to return to the main menu...")
            readlnOrNull()
            return
        }
        print("Enter the description (optional): ")
        val description = readlnOrNull()?.trim()
        val todos = FileManager.loadTodos()
        val newId = if (todos.isEmpty()) 1 else todos.maxOf { it.id } + 1
        val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val newTodo = TodoItem(newId, title, description, false, currentTime)
        todos.add(newTodo)
        FileManager.saveTodos(todos)
        println("Todo added successfully!")
        println("Press Enter to return to the main menu...")
        readlnOrNull()
    }

    private fun listTodoScreen() {
        clearConsole()
        println("-------------------------------------")
        println("            Todo List")
        println("-------------------------------------")
        val todos = FileManager.loadTodos()
        if (todos.isEmpty()) {
            println("No todos available.")
        } else {
            println("ID   | Title                 | Status     | Created At")
            println("-------------------------------------------------------")
            todos.forEach { todo ->
                val status = if (todo.isComplete) "Completed" else "Pending"
                val createdAtStr = todo.createdAt.toString().substring(0, 16)
                println(String.format("%4d | $-20s | %-10s | %s", todo.id, todo.title, status, createdAtStr))
            }
            println("-------------------------------------------------------")
        }
        println("Press Enter to return to the main menu...")
        readlnOrNull()
    }

    private fun markTodoAsCompletedScreen() {
        clearConsole()
        println("-------------------------------------")
        println("     Mark Todo as Completed")
        println("-------------------------------------")
        print("Enter the ID of the Todo to mark as completed: ")
        val input = readlnOrNull()?.trim()
        val id = input?.toIntOrNull()
        if (id == null) {
            println("\u001B[31Invalid ID. Please enter a numeric value. \u001B[0m")
            println("Press Enter to return to the main menu...")
            readlnOrNull()
            return
        }
        val todos = FileManager.loadTodos()
        val todo = todos.find { it.id == id }
        if (todo == null) {
            println("\u001B[31Todo with ID $id not found. \u001B[0m")
        } else {
            todo.isComplete = true
            FileManager.saveTodos(todos)
            println("Todo marked as completed!")
        }
        println("Press Enter to return to the main menu...")
        readlnOrNull()
    }

    private fun removeTodoScreen() {
        clearConsole()
        println("-------------------------------------")
        println("            Remove Todo")
        println("-------------------------------------")
        print("Enter the ID of the Todo to remove: ")
        val input = readlnOrNull()?.trim()
        val id = input?.toIntOrNull()
        if (id == null) {
            println("\u001B[31mInvalid ID. Please enter a numeric value. \u001B[0m")
            println("Press Enter to return to the main menu...")
            readlnOrNull()
            return
        }
        val todos = FileManager.loadTodos()
        val todo = todos.find { it.id == id }
        if (todo == null) {
            println("\u001B[31Todo with ID $id not found. \u001B[0m")
        } else {
            todos.remove(todo)
            FileManager.saveTodos(todos)
            println("Todo removed successfully!")
        }
        println("Press Enter to return to the main menu...")
        readlnOrNull()
    }

    private fun exitConfirmationScreen(): Boolean {
        clearConsole()
        println("-------------------------------------")
        println("          Exit Application")
        println("-------------------------------------")
        print("Are you sure you want to exit? (y/n): ")
        return when (readlnOrNull()?.trim()?.lowercase()) {
            "y" -> true
            else -> false
        }
    }
}