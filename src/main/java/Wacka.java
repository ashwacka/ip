import java.util.Scanner;
import java.util.*;

public class Wacka {
    public static void main(String[] args) {
        String divider = "______________________________________";
        System.out.println(divider);
        System.out.println("Hello I'm Wacka!");
        System.out.println("What can I do for you?");
        System.out.println(divider);

        Scanner scanner = new Scanner(System.in);
        String[] arr = new String[100];
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
            if (!input.equals("list")) {
                arr[i] = input;
                i++;
                System.out.println(divider);
                System.out.println("added: " + input);
                System.out.println(divider);
            } else if (input.equals("list")) {
                System.out.println(divider);
                for (int j = 0; j < i; j++) {
                    System.out.println(j + 1 + ". " + arr[j]);
                }
                System.out.println(divider);
            }

        }
        scanner.close();
    }
}
