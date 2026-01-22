import java.util.Scanner;

public class Wacka {
    public static void main(String[] args) {
        String divider = "______________________________________";
        System.out.println(divider);
        System.out.println("Hello I'm Wacka!");
        System.out.println("What can I do for you?");
        System.out.println(divider);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println(divider);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(divider);
                break;
            } else {
                System.out.println(divider);
                System.out.println(input);
                System.out.println(divider);
            }
        }
        scanner.close();
    }
}
