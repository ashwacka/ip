package wacka;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Parses the input given by the user
 * Checks for valid and invalid inputs, throws exception
 */

public class Parser {
    public static Command parse(String input) throws Wacka.WackaException {
        String[] words = input.split(" ");
        String command = words[0].toLowerCase();

        switch (command) {
        case "mark":
            return parseMark(words);
        case "unmark":
            return parseUnmark(words);
        case "list":
            return new Command(CommandType.LIST, -1, null, null, null);
        case "find":
            return parseFind(input);
        case "todo":
            return parseTodo(input);
        case "deadline":
            return parseDeadline(input);
        case "event":
            return parseEvent(input);
        case "delete":
            return parseDelete(words);
        case "bye":
            return new Command(CommandType.BYE, -1, null, null, null);
        default:
            throw new Wacka.WackaException("Oops! I do not know what to do with this :(");
        }
    }

    private static Command parseIndexCommand(String[] words, CommandType type) throws Wacka.WackaException {
        if (words.length < 2) {
            throw new Wacka.WackaException("Ohno! Please specify a task number!");
        }
        try {
            int index = Integer.parseInt(words[1]) - 1;
            return new Command(type, index, null, null, null);
        } catch (NumberFormatException e) {
            throw new Wacka.WackaException("Ohno! Please provide a valid task number!");
        }
    }

    private static Command parseMark(String[] words) throws Wacka.WackaException {
        return parseIndexCommand(words, CommandType.MARK);
    }

    private static Command parseUnmark(String[] words) throws Wacka.WackaException {
        return parseIndexCommand(words, CommandType.UNMARK);
    }

    private static Command parseDelete(String[] words) throws Wacka.WackaException {
        return parseIndexCommand(words, CommandType.DELETE);
    }

    private static Command parseFind(String input) throws Wacka.WackaException {
        if (input.length() < 6) {
            throw new Wacka.WackaException("Ohno! Please specify a keyword to search for!");
        }
        String keyword = input.substring(5).trim();
        if (keyword.isEmpty()) {
            throw new Wacka.WackaException("Ohno! Please specify a keyword to search for!");
        }
        return new Command(CommandType.FIND, -1, keyword, null, null);
    }

    private static Command parseTodo(String input) throws Wacka.WackaException {
        String description = input.length() < 5 ? "" : input.substring(5).trim();
        if (description.isEmpty()) {
            throw new Wacka.WackaException("Ohno! The description of a todo cannot be empty!");
        }
        return new Command(CommandType.TODO, -1, description, null, null);
    }

    private static Command parseDeadline(String input) throws Wacka.WackaException {
        String rest = input.length() < 9 ? "" : input.substring(9).trim();
        String[] parts = rest.split(" /by ");
        if (parts.length != 2) {
            throw new Wacka.WackaException("Ohno! The deadline cannot be empty!");
        }
        String description = parts[0].trim();
        String byString = parts[1].trim();
        if (description.isEmpty()) {
            throw new Wacka.WackaException("Ohno! Please describe the deadline!");
        }
        if (byString.isEmpty()) {
            throw new Wacka.WackaException("Ohno! When is this due?");
        }
        try {
            LocalDate byDate = LocalDate.parse(byString);
            return new Command(CommandType.DEADLINE, -1, description, byDate, null);
        } catch (DateTimeParseException e) {
            throw new Wacka.WackaException("Ohno! Please use yyyy-mm-dd format for dates (e.g., 2019-12-02)");
        }
    }

    private static Command parseEvent(String input) throws Wacka.WackaException {
        String eventRest = input.length() < 6 ? "" : input.substring(6).trim();
        String[] eventParts = eventRest.split(" /from ");
        if (eventParts.length != 2) {
            throw new Wacka.WackaException("Ohno! The event command format is incorrect. Use: event <description> /from <start> /to <end>");
        }
        String description = eventParts[0].trim();
        String[] timeParts = eventParts[1].split(" /to ");
        if (timeParts.length != 2) {
            throw new Wacka.WackaException("Ohno! The event command format is incorrect. Use: event <description> /from <start> /to <end>");
        }
        String fromString = timeParts[0].trim();
        String toString = timeParts[1].trim();
        if (description.isEmpty()) {
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
            return new Command(CommandType.EVENT, -1, description, fromDate, toDate);
        } catch (DateTimeParseException e) {
            throw new Wacka.WackaException("Ohno! Please use yyyy-mm-dd format for dates (e.g., 2019-12-02)");
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
        MARK, UNMARK, LIST, FIND, TODO, DEADLINE, EVENT, DELETE, BYE
    }
}
