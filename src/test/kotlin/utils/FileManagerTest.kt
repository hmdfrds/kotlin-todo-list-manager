package utils

import kotlinx.datetime.LocalDateTime
import org.example.todolist.models.TodoItem
import org.example.todolist.utils.FileManager
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FileManagerTest {
    private val testFile = File("todos.json")

    @BeforeTest
    fun setUp() {
        if (testFile.exists()) {
            testFile.delete()
        }
    }

    @AfterTest
    fun tearDown() {
        if (testFile.exists()) {
            testFile.delete()
        }
    }

    @Test
    fun testSaveAndLoadTodos() {
        val todos = mutableListOf(
            TodoItem(
                id = 1,
                title = "Test Todo",
                description = "Test Description",
                isComplete = false,
                createdAt = LocalDateTime.parse("2025-03-19T07:38")
            )
        )

        FileManager.saveTodos(todos)

        val loadedTodos = FileManager.loadTodos()
        assertEquals(1, loadedTodos.size)
        assertEquals("Test Todo", loadedTodos[0].title)
    }

}