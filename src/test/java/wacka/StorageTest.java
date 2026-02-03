package wacka;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    public void testLoadFromNonExistentFile() throws Wacka.WackaException {
        // Test loading from a file that doesn't exist should return empty array
        String filePath = tempDir.resolve("nonexistent.txt").toString();
        Storage storage = new Storage(filePath);
        
        Wacka.Task[] tasks = storage.load();
        
        assertNotNull(tasks);
        assertEquals(0, tasks.length);
    }

    @Test
    public void testSaveAndLoadTasks() throws Wacka.WackaException, IOException {
        // Test saving tasks to file and loading them back
        String filePath = tempDir.resolve("test.txt").toString();
        Storage storage = new Storage(filePath);
        
        // Create test tasks
        Wacka.Task todo = new Wacka.Todo("Test todo");
        Wacka.Task deadline = new Wacka.Deadline("Test deadline", LocalDate.parse("2024-12-31"));
        Wacka.Task[] tasksToSave = {todo, deadline};
        
        // Save tasks
        storage.save(tasksToSave);
        
        // Load tasks back
        Wacka.Task[] loadedTasks = storage.load();
        
        // Verify loaded tasks
        assertEquals(2, loadedTasks.length);
        assertEquals("Test todo", loadedTasks[0].getDescription());
        assertEquals("Test deadline", loadedTasks[1].getDescription());
        assertTrue(loadedTasks[1] instanceof Wacka.Deadline);
    }

    @Test
    public void testLoadTasksWithCorruptedLine() throws IOException, Wacka.WackaException {
        // Test that corrupted lines are skipped when loading
        String filePath = tempDir.resolve("corrupted.txt").toString();
        File file = new File(filePath);
        
        // Write valid and invalid lines
        FileWriter writer = new FileWriter(file);
        writer.write("T | 0 | Valid todo\n");
        writer.write("Invalid format line\n");
        writer.write("D | 1 | Valid deadline | 2024-12-31\n");
        writer.close();
        
        Storage storage = new Storage(filePath);
        Wacka.Task[] tasks = storage.load();
        
        // Should load 2 valid tasks, skipping the corrupted line
        assertEquals(2, tasks.length);
        assertEquals("Valid todo", tasks[0].getDescription());
        assertEquals("Valid deadline", tasks[1].getDescription());
        assertTrue(tasks[1].getIsDone()); // Should be marked as done
    }
}
