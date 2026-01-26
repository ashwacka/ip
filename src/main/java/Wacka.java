import java.util.Scanner;
import java.util.*;

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

        public abstract String getType();

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
    }

    public static class Deadline extends Task {
        private String by;

        public Deadline(String description, String by) {
            super(description);
            this.by = by;
        }

        @Override
        public String getType() {
            return "D";
        }

        @Override
        public String toString() {
            return "[" + getType() + "]" + getStatus() + " " + getDescription() + " (by: " + by + ")";
        }
    }

    public static class Event extends Task {
        private String from;
        private String to;

        public Event(String description, String from, String to) {
            super(description);
            this.from = from;
            this.to = to;
        }

        @Override
        public String getType() {
            return "E";
        }

        @Override
        public String toString() {
            return "[" + getType() + "]" + getStatus() + " " + getDescription() + " (from: " + from + " to: " + to + ")";
        }
    }

    public static void main(String[] args) {
        String divider = "____________________________________________________________";
        System.out.println("Hello! I'm Wacka");
        System.out.println("What can I do for you?");
        System.out.println(divider);

        Scanner scanner = new Scanner(System.in);
        Task[] arr = new Task[100];
        int i = 0;
        
        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            //base case, terminating factor
            if (input.equals("bye")) {
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
                    System.out.println(divider);
                    System.out.println("Good Job! I have marked this task as completed ⭐️");
                    System.out.println(arr[idx].getStatus() + " " + arr[idx].getDescription());
                    System.out.println(divider);

                //unmarking a previously marked task
                } else if (words[0].equals("unmark")) {
                    String[] parts = input.split(" ");
                    int idx = Integer.parseInt(parts[1]) - 1;
                    arr[idx].unMark();
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
                    String by = parts[1].trim();
                    if (description.isEmpty()) {
                        throw new WackaException("Ohno! Please describe the deadline!");
                    }
                    if (by.isEmpty()) {
                        throw new WackaException("Ohno! When is this due?");
                    }
                    arr[i] = new Deadline(description, by);
                    i++;
                    System.out.println(divider);
                    System.out.println("Got it! I've added this task:");
                    System.out.println("  " + arr[i - 1]);
                    System.out.println("Now you have " + i + " tasks in the list.");
                    System.out.println(divider);

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
                    String from = timeParts[0].trim();
                    String to = timeParts[1].trim();
                    if (description.isEmpty()) {
                        throw new WackaException("Ohno! The description of an event cannot be empty!");
                    }
                    if (from.isEmpty()) {
                        throw new WackaException("Ohno! When does the event start?");
                    }
                    if (to.isEmpty()) {
                        throw new WackaException("Ohno! When does the event end?");
                    }
                    arr[i] = new Event(description, from, to);
                    i++;
                    System.out.println(divider);
                    System.out.println("Got it! I've added this task:");
                    System.out.println("  " + arr[i - 1]);
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
