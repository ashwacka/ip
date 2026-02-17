package wacka;

public class Ui {
    private static final String DIVIDER = "____________________________________________________________";

    /**
     * Prints the default welcome
     */
    public void printWelcome() {
        System.out.println("Hello! I'm Wacka");
        System.out.println("What can I do for you?");
        System.out.println(DIVIDER);
    }

    public void printGoodbye() {
        System.out.println(DIVIDER);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(DIVIDER);
    }

    public void showError(String message) {
        System.out.println(DIVIDER);
        System.out.println(message);
        System.out.println(DIVIDER);
    }

    public void showMarkedTask(String status, String description) {
        System.out.println(DIVIDER);
        System.out.println("Good Job! I have marked this task as completed ⭐️");
        System.out.println(status + " " + description);
        System.out.println(DIVIDER);
    }

    public void showUnmarkedTask(String status, String description) {
        System.out.println(DIVIDER);
        System.out.println("Okay! I have marked this task as incomplete️");
        System.out.println(status + " " + description);
        System.out.println(DIVIDER);
    }

    public void showTaskList(Wacka.Task[] tasks, int count) {
        assert tasks != null : "tasks array must not be null";
        assert count >= 0 && count <= tasks.length : "count must be valid for tasks array";
        System.out.println(DIVIDER);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + "." + tasks[i]);
        }
        System.out.println(DIVIDER);
    }

    public void showMatchingTasks(Wacka.Task[] tasks, int count) {
        boolean isEmpty = (tasks == null || tasks.length == 0 || count <= 0);
        if (isEmpty) {
            System.out.println(DIVIDER);
            System.out.println("No matching tasks found.");
            System.out.println(DIVIDER);
            return;
        }
        System.out.println(DIVIDER);
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + "." + tasks[i]);
        }
        System.out.println(DIVIDER);
    }

    public void showTaskAdded(Wacka.Task task, int totalTasks) {
        System.out.println(DIVIDER);
        System.out.println("Got it! I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        System.out.println(DIVIDER);
    }

    public void showTaskDeleted(Wacka.Task task, int totalTasks) {
        System.out.println(DIVIDER);
        System.out.println("Okay! I've deleted this task: ");
        System.out.println(task.toString());
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        System.out.println(DIVIDER);
    }

    public void showLoadingError() {
        System.out.println("Error loading tasks from file. Creating new empty list.");
    }

    // GUI-friendly methods that return formatted strings instead of printing

    public String getWelcomeMessage() {
        return "Hello! I'm Wacka\nWhat can I do for you?";
    }

    public String getGoodbyeMessage() {
        return "Bye. Hope to see you again soon!";
    }

    public String getErrorMessage(String message) {
        return message;
    }

    public String getMarkedTaskMessage(String status, String description) {
        return "Good Job! I have marked this task as completed ⭐️\n" + status + " " + description;
    }

    public String getUnmarkedTaskMessage(String status, String description) {
        return "Okay! I have marked this task as incomplete\n" + status + " " + description;
    }

    public String getTaskListMessage(Wacka.Task[] tasks, int count) {
        assert tasks != null : "tasks array must not be null";
        assert count >= 0 && count <= tasks.length : "count must be valid for tasks array";
        if (count == 0) {
            return "Your task list is empty!";
        }
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < count; i++) {
            sb.append((i + 1)).append(".").append(tasks[i]).append("\n");
        }
        return sb.toString().trim();
    }

    public String getMatchingTasksMessage(Wacka.Task[] tasks, int count) {
        boolean isEmpty = (tasks == null || tasks.length == 0 || count <= 0);
        if (isEmpty) {
            return "No matching tasks found.";
        }
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < count; i++) {
            sb.append((i + 1)).append(".").append(tasks[i]).append("\n");
        }
        return sb.toString().trim();
    }

    public String getTaskAddedMessage(Wacka.Task task, int totalTasks) {
        return "Got it! I've added this task:\n  " + task + "\nNow you have " + totalTasks + " tasks in the list.";
    }

    public String getTaskDeletedMessage(Wacka.Task task, int totalTasks) {
        return "Okay! I've deleted this task:\n" + task.toString() + "\nNow you have " + totalTasks + " tasks in the list.";
    }
}
