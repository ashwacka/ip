package wacka;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
    }

    @Test
    public void testAddTask() {
        // Test adding tasks to the list
        Wacka.Task todo = new Wacka.Todo("Test todo");
        Wacka.Task deadline = new Wacka.Deadline("Test deadline", LocalDate.parse("2024-12-31"));
        
        assertEquals(0, taskList.getCount());
        
        taskList.addTask(todo);
        assertEquals(1, taskList.getCount());
        
        taskList.addTask(deadline);
        assertEquals(2, taskList.getCount());
        
        Wacka.Task[] tasks = taskList.getTasks();
        assertEquals("Test todo", tasks[0].getDescription());
        assertEquals("Test deadline", tasks[1].getDescription());
    }

    @Test
    public void testDeleteTask() throws Wacka.WackaException {
        // Test deleting a task from the list
        Wacka.Task todo1 = new Wacka.Todo("Task 1");
        Wacka.Task todo2 = new Wacka.Todo("Task 2");
        Wacka.Task todo3 = new Wacka.Todo("Task 3");
        
        taskList.addTask(todo1);
        taskList.addTask(todo2);
        taskList.addTask(todo3);
        assertEquals(3, taskList.getCount());
        
        //test the deletion of middle task
        Wacka.Task deleted = taskList.deleteTask(1);
        assertEquals("Task 2", deleted.getDescription());
        assertEquals(2, taskList.getCount());
        
        // check that the tasks have been shifted correctly
        Wacka.Task[] tasks = taskList.getTasks();
        assertEquals("Task 1", tasks[0].getDescription());
        assertEquals("Task 3", tasks[1].getDescription());
        
        // Test deleting with invalid index
        assertThrows(Wacka.WackaException.class, () -> taskList.deleteTask(5));
        assertThrows(Wacka.WackaException.class, () -> taskList.deleteTask(-1));
    }

    @Test
    public void testMarkAndUnmarkTask() throws Wacka.WackaException {
        // Test marking and unmarking tasks
        Wacka.Task todo = new Wacka.Todo("Test task");
        taskList.addTask(todo);
        
        // Initially not done
        assertFalse(todo.getIsDone());
        
        // Mark as done
        taskList.markTask(0);
        assertTrue(todo.getIsDone());
        
        // Unmark
        taskList.unmarkTask(0);
        assertFalse(todo.getIsDone());
        
        // Test with invalid index
        assertThrows(Wacka.WackaException.class, () -> taskList.markTask(10));
        assertThrows(Wacka.WackaException.class, () -> taskList.unmarkTask(-1));
    }
}
