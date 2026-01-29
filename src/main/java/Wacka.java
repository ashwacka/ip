import java.util.Scanner;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Wacka {

    public static class WackaException extends Exception {
        public WackaException(String message) {
            super(message);
        }
    }

    public static abstract class Task {
        protected String description;
        protected boolean isDone;

        public Task(String description) {
            this.description = description;
            this.isDone = false;
        }

        public String getStatus() {
            if (isDone) {
                return "[X]";
            } else {
                return "[ ]";
            }
        }

        public String getDescription() {
            return description;
        }

        public void markAsDone() {
            isDone = true;
        }

        public void unMark() {
            isDone = false;
        }

        public boolean getIsDone() {
            return isDone;
        }

        public abstract String getType();
        
        // convert task to given format, e.g. T | 1 | read book
        public abstract String toFileFormat();

        @Override
        public String toString() {
            return "[" + getType() + "]" + getStatus() + " " + getDescription();
        }
    }

    public static class Todo extends Task {
        public Todo(String description) {
            super(description);
        }

        @Override
        public String getType() {
            return "T";
        }

        @Override
        public String toFileFormat() {
            return "T | " + (isDone ? "1" : "0") + " | " + description;
        }
    }

    public static class Deadline extends Task {
        private LocalDate by;

        public Deadline(String description, LocalDate by) {
            super(description);
            this.by = by;
        }

        @Override
        public String getType() {
            return "D";
        }

        public LocalDate getBy() {
            return by;
        }

        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
            return "[" + getType() + "]" + getStatus() + " " + getDescription() + " (by: " + by.format(formatter) + ")";
        }

        @Override
        public String toFileFormat() {
            return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by.toString();
        }
    }

    public static class Event extends Task {
        private LocalDate from;
        private LocalDate to;

        public Event(String description, LocalDate from, LocalDate to) {
            super(description);
            this.from = from;
            this.to = to;
        }

        @Override
        public String getType() {
            return "E";
        }

        public LocalDate getFrom() {
            return from;
        }

        public LocalDate getTo() {
            return to;
        }

        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
            return "[" + getType() + "]" + getStatus() + " " + getDescription() + " (from: " + from.format(formatter) + " to: " + to.format(formatter) + ")";
        }

        @Override
        public String toFileFormat() {
            return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from.toString() + " | " + to.toString();
        }
    }

    //convert task from file to proper list format
    private static Task parseTaskFromFile(String line) throws WackaException {
        try {
            String[] parts = line.split(" \\| ");
            if (parts.length < 3) {
                throw new WackaException("Invalid task format in file");
            }

            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];

            Task task;
            switch (type) {
                case "T":
                    task = new Todo(description);
                    break;
                case "D":
                    if (parts.length < 4) {
                        throw new WackaException("Invalid deadline format in file");
                    }
                    try {
                        LocalDate byDate = LocalDate.parse(parts[3].trim());
                        task = new Deadline(description, byDate);
                    } catch (DateTimeParseException e) {
                        throw new WackaException("Invalid date format in file: " + parts[3]);
                    }
                    break;
                case "E":
                    if (parts.length < 5) {
                        throw new WackaException("Invalid event format in file");
                    }
                    try {
                        LocalDate fromDate = LocalDate.parse(parts[3].trim());
                        LocalDate toDate = LocalDate.parse(parts[4].trim());
                        task = new Event(description, fromDate, toDate);
                    } catch (DateTimeParseException e) {
                        throw new WackaException("Invalid date format in file");
                    }
                    break;
                default:
                    throw new WackaException("Unknown task type in file: " + type);
            }

            if (isDone) {
                task.markAsDone();
            }
            return task;
        } catch (Exception e) {
            throw new WackaException("Error parsing task from file: " + e.getMessage());
        }
    }

    // Save tasks to file
    private static void saveTasks(Task[] tasks, int count, String filePath) {
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();

            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < count; i++) {
                writer.write(tasks[i].toFileFormat() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
        }
    }

    // Load tasks from file
    private static int loadTasks(Task[] tasks, String filePath) {
        try {
            File file = new File(filePath);
            
            // If file doesn't exist, return 0
            if (!file.exists()) {
                return 0;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            int count = 0;

            while ((line = reader.readLine()) != null && count < tasks.length) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                try {
                    tasks[count] = parseTaskFromFile(line);
                    count++;
                } catch (WackaException e) {
                }
            }
            reader.close();
            return count;
        } catch (IOException e) {
            return 0;
        }
    }

    public static void main(String[] args) {
        String divider = "____________________________________________________________";
        System.out.println("Hello! I'm Wacka");
        System.out.println("What can I do for you?");
        System.out.println(divider);

        String filePath = "data" + File.separator + "wacka.txt";
        
        Scanner scanner = new Scanner(System.in);
        Task[] arr = new Task[100];
        int i = loadTasks(arr, filePath);
        
        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            //base case, terminating factor
            if (input.equals("bye")) {
                System.out.println(divider);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(divider);
                break;
            }

            try {
                String[] words = input.split(" ");
                //marking as complete
                if (words[0].equals("mark")) {
                    String[] parts = input.split(" ");
                    int idx = Integer.parseInt(parts[1]) - 1;
                    arr[idx].markAsDone();
                    saveTasks(arr, i, filePath);
                    System.out.println(divider);
                    System.out.println("Good Job! I have marked this task as completed ⭐️");
                    System.out.println(arr[idx].getStatus() + " " + arr[idx].getDescription());
                    System.out.println(divider);

                //unmarking a previously marked task
                } else if (words[0].equals("unmark")) {
                    String[] parts = input.split(" ");
                    int idx = Integer.parseInt(parts[1]) - 1;
                    arr[idx].unMark();
                    saveTasks(arr, i, filePath);
                    System.out.println(divider);
                    System.out.println("Okay! I have marked this task as incomplete️");
                    System.out.println(arr[idx].getStatus() + " " + arr[idx].getDescription());
                    System.out.println(divider);

                //display list
                } else if (words[0].equals("list")) {
                    System.out.println(divider);
                    System.out.println("Here are the tasks in your list:");
                    for (int j = 0; j < i; j++) {
                        System.out.println((j + 1) + "." + arr[j]);
                    }
                    System.out.println(divider);

                //filter based on the type of task
                } else if (words[0].equals("todo")) {
                    String description = input.substring(5).trim();
                    if (description.isEmpty()) {
                        throw new WackaException("Ohno! The description of a todo cannot be empty!");
                    }
                    arr[i] = new Todo(description);
                    i++;
                    saveTasks(arr, i, filePath);
                    System.out.println(divider);
                    System.out.println("Got it! I've added this task:");
                    System.out.println("  " + arr[i - 1]);
                    System.out.println("Now you have " + i + " tasks in the list.");
                    System.out.println(divider);

                } else if (words[0].equals("deadline")) {
                    String rest = input.substring(9).trim();
                    String[] parts = rest.split(" /by ");
                    if (parts.length != 2) {
                        throw new WackaException("Ohno! The deadline cannot be empty!");
                    }
                    String description = parts[0].trim();
                    String byString = parts[1].trim();
                    if (description.isEmpty()) {
                        throw new WackaException("Ohno! Please describe the deadline!");
                    }
                    if (byString.isEmpty()) {
                        throw new WackaException("Ohno! When is this due?");
                    }
                    try {
                        LocalDate byDate = LocalDate.parse(byString);
                        arr[i] = new Deadline(description, byDate);
                        i++;
                        saveTasks(arr, i, filePath);
                        System.out.println(divider);
                        System.out.println("Got it! I've added this task:");
                        System.out.println("  " + arr[i - 1]);
                        System.out.println("Now you have " + i + " tasks in the list.");
                        System.out.println(divider);
                    } catch (DateTimeParseException e) {
                        throw new WackaException("Ohno! Please use yyyy-mm-dd format for dates (e.g., 2019-12-02)");
                    }

                } else if (words[0].equals("event")) {
                    String rest = input.substring(6).trim();
                    String[] parts = rest.split(" /from ");
                    if (parts.length != 2) {
                        throw new WackaException("Ohno! The event command format is incorrect. Use: event <description> /from <start> /to <end>");
                    }
                    String description = parts[0].trim();
                    String[] timeParts = parts[1].split(" /to ");
                    if (timeParts.length != 2) {
                        throw new WackaException("Ohno! The event command format is incorrect. Use: event <description> /from <start> /to <end>");
                    }
                    String fromString = timeParts[0].trim();
                    String toString = timeParts[1].trim();
                    if (description.isEmpty()) {
                        throw new WackaException("Ohno! The description of an event cannot be empty!");
                    }
                    if (fromString.isEmpty()) {
                        throw new WackaException("Ohno! When does the event start?");
                    }
                    if (toString.isEmpty()) {
                        throw new WackaException("Ohno! When does the event end?");
                    }
                    try {
                        LocalDate fromDate = LocalDate.parse(fromString);
                        LocalDate toDate = LocalDate.parse(toString);
                        arr[i] = new Event(description, fromDate, toDate);
                        i++;
                        saveTasks(arr, i, filePath);
                        System.out.println(divider);
                        System.out.println("Got it! I've added this task:");
                        System.out.println("  " + arr[i - 1]);
                        System.out.println("Now you have " + i + " tasks in the list.");
                        System.out.println(divider);
                    } catch (DateTimeParseException e) {
                        throw new WackaException("Ohno! Please use yyyy-mm-dd format for dates (e.g., 2019-12-02)");
                    }
                } else if (words[0].equals("delete")) {
                    int deleteIdx = Integer.parseInt(words[1]) - 1;
                    if (deleteIdx < 0 || deleteIdx >= i) {
                        throw new WackaException("Ohno! There's no task to delete!");
                    }
                    Task deletedTask = arr[deleteIdx];

                    //shift all elements to the left
                    for (int j = deleteIdx; j < i - 1; j++) {
                        arr[j] = arr[j + 1];
                    }
                    arr[i - 1] = null;
                    i--;
                    saveTasks(arr, i, filePath);
                    System.out.println(divider);
                    System.out.println("Okay! I've deleted this task: ");
                    System.out.println(deletedTask.toString());
                    System.out.println("Now you have " + i + " tasks in the list.");
                    System.out.println(divider);
                } else {
                    throw new WackaException("Oops! I do not know what to do with this :(");
                }
            } catch (WackaException e) {
                System.out.println(divider);
                System.out.println(e.getMessage());
                System.out.println(divider);
            }
        }
        scanner.close();
    }
}
