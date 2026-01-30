public class Ui {
    private static final String DIVIDER = "____________________________________________________________";

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
        System.out.println(DIVIDER);
        System.out.println("Here are the tasks in your list:");
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
}
