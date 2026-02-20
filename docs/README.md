# Wacka — User Guide

**Welcome to Wacka!** Wacka is your own personal chatbot that helps you manage tasks. Chat in natural commands to add todos, deadlines, events, and recurring tasks, then list, mark, or find them.

---

## Quick start

**Run the app (GUI)**  
- Ensure you have Java 17 or above installed.
- Download the latest ```wacka.jar``` from the Releases page
- Copy the file to the folder you want to use as your Wacka home folder. 
- Open a terminal, cd into that folder, and run:
```java -jar wacka.jar```

The Wacka chat window opens. Type in the box at the bottom and press **Send** (or Enter).

---

## Commands

| Command | Example |
|--------|---------|
| **list** | `list` — show all tasks |
| **todo** | `todo read book` — add a todo |
| **deadline** | `deadline submit report /by 2026-03-01` — add a deadline (use `yyyy-mm-dd`) |
| **event** | `event meeting /from 2026-02-20 /to 2026-02-21` — add an event |
| **recur** | `recur Weekly sync /every WEEKLY` — add a recurring task (DAILY, WEEKLY, MONTHLY, YEARLY). Optional: `/from 2026-02-20` |
| **mark** | `mark 1` — mark task #1 as done (recurring tasks are replaced by the next occurrence) |
| **unmark** | `unmark 1` — mark task #1 as not done |
| **delete** | `delete 2` — remove task #2 |
| **find** | `find book` — list tasks whose description contains “book” |
| **bye** | `bye` — close the chat |

---

## Tips

- **Dates** Use `yyyy-mm-dd` (e.g. `2026-02-20`).
- **Recurring tasks** When you mark one done, it is replaced by the next occurrence (e.g. next week) in the same list position.
- **Errors** Invalid commands or missing details show in a red-highlighted message; fix the command and try again.

---

## Where is my data?

Tasks are saved in `data/wacka.txt` in the project folder. The file is updated when you add, mark, unmark, or delete tasks.

---

## Requirements

- **JDK 17**
- For GUI: JavaFX (included via Gradle)

