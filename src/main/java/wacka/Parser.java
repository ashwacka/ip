package wacka;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {
    public static Command parse(String input) throws Wacka.WackaException {
        String[] words = input.split(" ");
        String command = words[0].toLowerCase();

        switch (command) {
            case "mark":
                if (words.length < 2) {
                    throw new Wacka.WackaException("Ohno! Please specify a task number!");
                }
                try {
                    int markIndex = Integer.parseInt(words[1]) - 1;
                    return new Command(CommandType.MARK, markIndex, null, null, null);
                } catch (NumberFormatException e) {
                    throw new Wacka.WackaException("Ohno! Please provide a valid task number!");
                }

            case "unmark":
                if (words.length < 2) {
                    throw new Wacka.WackaException("Ohno! Please specify a task number!");
                }
                try {
                    int unmarkIndex = Integer.parseInt(words[1]) - 1;
                    return new Command(CommandType.UNMARK, unmarkIndex, null, null, null);
                } catch (NumberFormatException e) {
                    throw new Wacka.WackaException("Ohno! Please provide a valid task number!");
                }

            case "list":
                return new Command(CommandType.LIST, -1, null, null, null);

            case "todo":
                String todoDescription = input.substring(5).trim();
                if (todoDescription.isEmpty()) {
                    throw new Wacka.WackaException("Ohno! The description of a todo cannot be empty!");
                }
                return new Command(CommandType.TODO, -1, todoDescription, null, null);

            case "deadline":
                String rest = input.substring(9).trim();
                String[] parts = rest.split(" /by ");
                if (parts.length != 2) {
                    throw new Wacka.WackaException("Ohno! The deadline cannot be empty!");
                }
                String deadlineDescription = parts[0].trim();
                String byString = parts[1].trim();
                if (deadlineDescription.isEmpty()) {
                    throw new Wacka.WackaException("Ohno! Please describe the deadline!");
                }
                if (byString.isEmpty()) {
                    throw new Wacka.WackaException("Ohno! When is this due?");
                }
                try {
                    LocalDate byDate = LocalDate.parse(byString);
                    return new Command(CommandType.DEADLINE, -1, deadlineDescription, byDate, null);
                } catch (DateTimeParseException e) {
                    throw new Wacka.WackaException("Ohno! Please use yyyy-mm-dd format for dates (e.g., 2019-12-02)");
                }

            case "event":
                String eventRest = input.substring(6).trim();
                String[] eventParts = eventRest.split(" /from ");
                if (eventParts.length != 2) {
                    throw new Wacka.WackaException("Ohno! The event command format is incorrect. Use: event <description> /from <start> /to <end>");
                }
                String eventDescription = eventParts[0].trim();
                String[] timeParts = eventParts[1].split(" /to ");
                if (timeParts.length != 2) {
                    throw new Wacka.WackaException("Ohno! The event command format is incorrect. Use: event <description> /from <start> /to <end>");
                }
                String fromString = timeParts[0].trim();
                String toString = timeParts[1].trim();
                if (eventDescription.isEmpty()) {
                    throw new Wacka.WackaException("Ohno! The description of an event cannot be empty!");
                }
                if (fromString.isEmpty()) {
                    throw new Wacka.WackaException("Ohno! When does the event start?");
                }
                if (toString.isEmpty()) {
                    throw new Wacka.WackaException("Ohno! When does the event end?");
                }
                try {
                    LocalDate fromDate = LocalDate.parse(fromString);
                    LocalDate toDate = LocalDate.parse(toString);
                    return new Command(CommandType.EVENT, -1, eventDescription, fromDate, toDate);
                } catch (DateTimeParseException e) {
                    throw new Wacka.WackaException("Ohno! Please use yyyy-mm-dd format for dates (e.g., 2019-12-02)");
                }

            case "delete":
                if (words.length < 2) {
                    throw new Wacka.WackaException("Ohno! Please specify a task number!");
                }
                try {
                    int deleteIndex = Integer.parseInt(words[1]) - 1;
                    return new Command(CommandType.DELETE, deleteIndex, null, null, null);
                } catch (NumberFormatException e) {
                    throw new Wacka.WackaException("Ohno! Please provide a valid task number!");
                }

            case "bye":
                return new Command(CommandType.BYE, -1, null, null, null);

            default:
                throw new Wacka.WackaException("Oops! I do not know what to do with this :(");
        }
    }

    public static class Command {
        public CommandType type;
        public int index;
        public String description;
        public LocalDate date;
        public LocalDate toDate;

        public Command(CommandType type, int index, String description, LocalDate date, LocalDate toDate) {
            this.type = type;
            this.index = index;
            this.description = description;
            this.date = date;
            this.toDate = toDate;
        }
    }

    public enum CommandType {
        MARK, UNMARK, LIST, TODO, DEADLINE, EVENT, DELETE, BYE
    }
}
