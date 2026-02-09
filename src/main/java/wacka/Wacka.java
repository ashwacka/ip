package wacka;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Wacka {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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

    public Wacka(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (WackaException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the program based on the input commands given
     */
    public void run() {
        ui.printWelcome();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String input = scanner.nextLine();

            try {
                Parser.Command command = Parser.parse(input);

                switch (command.type) {
                    case BYE:
                        ui.printGoodbye();
                        scanner.close();
                        return;

                    case LIST:
                        ui.showTaskList(tasks.getTasks(), tasks.getCount());
                        break;

                    case FIND:
                        Wacka.Task[] matchingTasks = tasks.findTasks(command.description);
                        ui.showMatchingTasks(matchingTasks, matchingTasks.length);
                        break;

                    case MARK:
                        tasks.markTask(command.index);
                        try {
                            storage.save(tasks.getTasks());
                        } catch (WackaException e) {
                            ui.showError("Error saving tasks");
                        }
                        Task markedTask = tasks.getTask(command.index);
                        ui.showMarkedTask(markedTask.getStatus(), markedTask.getDescription());
                        break;

                    case UNMARK:
                        tasks.unmarkTask(command.index);
                        try {
                            storage.save(tasks.getTasks());
                        } catch (WackaException e) {
                            ui.showError("Error saving tasks");
                        }
                        Task unmarkedTask = tasks.getTask(command.index);
                        ui.showUnmarkedTask(unmarkedTask.getStatus(), unmarkedTask.getDescription());
                        break;

                    case TODO:
                        Task todo = new Todo(command.description);
                        tasks.addTask(todo);
                        try {
                            storage.save(tasks.getTasks());
                        } catch (WackaException e) {
                            ui.showError("Error saving tasks");
                        }
                        ui.showTaskAdded(todo, tasks.getCount());
                        break;

                    case DEADLINE:
                        Task deadline = new Deadline(command.description, command.date);
                        tasks.addTask(deadline);
                        try {
                            storage.save(tasks.getTasks());
                        } catch (WackaException e) {
                            ui.showError("Error saving tasks");
                        }
                        ui.showTaskAdded(deadline, tasks.getCount());
                        break;

                    case EVENT:
                        Task event = new Event(command.description, command.date, command.toDate);
                        tasks.addTask(event);
                        try {
                            storage.save(tasks.getTasks());
                        } catch (WackaException e) {
                            ui.showError("Error saving tasks");
                        }
                        ui.showTaskAdded(event, tasks.getCount());
                        break;

                    case DELETE:
                        Task deletedTask = tasks.deleteTask(command.index);
                        try {
                            storage.save(tasks.getTasks());
                        } catch (WackaException e) {
                            ui.showError("Error saving tasks");
                        }
                        ui.showTaskDeleted(deletedTask, tasks.getCount());
                        break;
                }
            } catch (WackaException e) {
                ui.showError(e.getMessage());
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        String filePath = "data" + java.io.File.separator + "wacka.txt";
        new Wacka(filePath).run();
    }
}
