package wacka;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UiTest {

    @Test
    public void testPrintWelcome() {
        //tests that the welcome is printed with no errors
        Ui ui = new Ui();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        
        try {
            System.setOut(new PrintStream(outputStream));
            ui.printWelcome();
            String output = outputStream.toString();
            
            assertTrue(output.contains("Hello! I'm Wacka"));
            assertTrue(output.contains("What can I do for you?"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testShowTaskAdded() {
        // Test that showTaskAdded displays correct information
        Ui ui = new Ui();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        
        try {
            System.setOut(new PrintStream(outputStream));
            Wacka.Task task = new Wacka.Todo("Test task");
            ui.showTaskAdded(task, 1);
            String output = outputStream.toString();
            
            assertTrue(output.contains("Got it! I've added this task:"));
            assertTrue(output.contains("Test task"));
            assertTrue(output.contains("Now you have 1 tasks in the list."));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testShowError() {
        //tests that showError displays the error message correctly
        Ui ui = new Ui();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        
        try {
            System.setOut(new PrintStream(outputStream));
            String errorMessage = "Test error message";
            ui.showError(errorMessage);
            String output = outputStream.toString();
            
            assertTrue(output.contains(errorMessage));
        } finally {
            System.setOut(originalOut);
        }
    }
}
