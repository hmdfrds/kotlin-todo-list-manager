package org.example.todolist.ui

import org.example.todolist.controller.TodoCommand
import org.example.todolist.controller.TodoController
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

                else -> {
                    println("\u001B[31mInvalid option. Please try again. \u001B[0m")
                    Thread.sleep(1500)
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
        val result = TodoController.processCommand(TodoCommand.AddTodo(title, description))
        println(result)
        println("Press Enter to return to the main menu...")
        readlnOrNull()
    }

    private fun listTodoScreen() {
        clearConsole()
        println("-------------------------------------")
        println("            Todo List")
        println("-------------------------------------")
        val result = TodoController.processCommand(TodoCommand.ListTodos)
        println(result)
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
        val result = TodoController.processCommand(TodoCommand.MarkTodoCompleted(id))
        println(result)
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
        val result = TodoController.processCommand(TodoCommand.RemoveTodo(id))
        println(result)
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