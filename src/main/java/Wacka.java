import java.util.Scanner;
import java.util.*;

public class Wacka {

    public static class Task {
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

        @Override
        public String toString() {
            return getStatus() + " " + getDescription();
        }
    }

    public static void main(String[] args) {
        String divider = "______________________________________";
        System.out.println(divider);
        System.out.println("Hello I'm Wacka!");
        System.out.println("What can I do for you?");
        System.out.println(divider);

        Scanner scanner = new Scanner(System.in);
        Task[] arr = new Task[100];
        int i = 0;
        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            //base case, terminating factor
            if (input.equals("bye")) {
                System.out.println(divider);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(divider);
                break;
            }

            String[] words = input.split(" ");
            if (words[0].equals("mark")) {
                String[] parts = input.split(" ");
                int idx = Integer.parseInt(parts[1]) - 1;
                arr[idx].markAsDone();
                System.out.println(divider);
                System.out.println("Good Job! I have marked this task as completed ⭐️");
                System.out.println(arr[idx].getStatus() + " " + arr[idx].getDescription());
                System.out.println(divider);
            } else if (words[0].equals("unmark")) {
                String[] parts = input.split(" ");
                int idx = Integer.parseInt(parts[1]) - 1;
                arr[idx].unMark();
                System.out.println(divider);
                System.out.println("Okay! I have marked this task as incomplete️");
                System.out.println(arr[idx].getStatus() + " " + arr[idx].getDescription());
                System.out.println(divider);
            } else if (words[0].equals("list")) {
                System.out.println(divider);
                for (int j = 0; j < i; j++) {
                    System.out.println(j + 1 + ". " + arr[j]);
                }
                System.out.println(divider);
            } else if (!words[0].equals("list")) {
                arr[i] = new Task(input);
                i++;
                System.out.println(divider);
                System.out.println("added: " + input);
                System.out.println(divider);

            }
        }
        scanner.close();
    }
}
