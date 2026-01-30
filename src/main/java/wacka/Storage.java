package wacka;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public Wacka.Task[] load() throws Wacka.WackaException {
        Wacka.Task[] tasks = new Wacka.Task[100];
        int count = 0;

        try {
            File file = new File(filePath);
            
            if (!file.exists()) {
                return new Wacka.Task[0];
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null && count < tasks.length) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                try {
                    tasks[count] = parseTaskFromFile(line);
                    count++;
                } catch (Wacka.WackaException e) {
                    // Skip corrupted lines
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new Wacka.WackaException("Error loading tasks from file");
        }

        Wacka.Task[] result = new Wacka.Task[count];
        System.arraycopy(tasks, 0, result, 0, count);
        return result;
    }

    public void save(Wacka.Task[] tasks) throws Wacka.WackaException {
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();

            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            FileWriter writer = new FileWriter(file);
            for (Wacka.Task task : tasks) {
                if (task != null) {
                    writer.write(task.toFileFormat() + System.lineSeparator());
                }
            }
            writer.close();
        } catch (IOException e) {
            throw new Wacka.WackaException("Error saving tasks to file");
        }
    }

    private Wacka.Task parseTaskFromFile(String line) throws Wacka.WackaException {
        try {
            String[] parts = line.split(" \\| ");
            if (parts.length < 3) {
                throw new Wacka.WackaException("Invalid task format in file");
            }

            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];

            Wacka.Task task;
            switch (type) {
                case "T":
                    task = new Wacka.Todo(description);
                    break;
                case "D":
                    if (parts.length < 4) {
                        throw new Wacka.WackaException("Invalid deadline format in file");
                    }
                    try {
                        LocalDate byDate = LocalDate.parse(parts[3].trim());
                        task = new Wacka.Deadline(description, byDate);
                    } catch (DateTimeParseException e) {
                        throw new Wacka.WackaException("Invalid date format in file: " + parts[3]);
                    }
                    break;
                case "E":
                    if (parts.length < 5) {
                        throw new Wacka.WackaException("Invalid event format in file");
                    }
                    try {
                        LocalDate fromDate = LocalDate.parse(parts[3].trim());
                        LocalDate toDate = LocalDate.parse(parts[4].trim());
                        task = new Wacka.Event(description, fromDate, toDate);
                    } catch (DateTimeParseException e) {
                        throw new Wacka.WackaException("Invalid date format in file");
                    }
                    break;
                default:
                    throw new Wacka.WackaException("Unknown task type in file: " + type);
            }

            if (isDone) {
                task.markAsDone();
            }
            return task;
        } catch (Exception e) {
            throw new Wacka.WackaException("Error parsing task from file: " + e.getMessage());
        }
    }
}
