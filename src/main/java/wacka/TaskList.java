package wacka;

/**
 * Class for handling all actions related to a Task in the List
 */

public class TaskList {
    private Wacka.Task[] tasks;
    private int count;

    public TaskList() {
        this.tasks = new Wacka.Task[100];
        this.count = 0;
        assert this.count >= 0 && this.count <= this.tasks.length : "count must be in valid range after init";
    }

    public TaskList(Wacka.Task[] tasks) {
        this.tasks = new Wacka.Task[100];
        this.count = 0;
        if (tasks == null) {
            return;
        }
        for (Wacka.Task task : tasks) {
            if (task == null || this.count >= 100) {
                continue;
            }
            this.tasks[this.count] = task;
            this.count++;
        }
    }

    public void addTask(Wacka.Task task) {
        assert task != null : "task to add must not be null";
        if (count < tasks.length) {
            tasks[count] = task;
            count++;
        }
    }

    /**
     * Deletes specified task from the list
     * Shifts all the tasks after the deleted task to their new position
     */

    public Wacka.Task deleteTask(int index) throws Wacka.WackaException {
        if (index < 0 || index >= count) {
            throw new Wacka.WackaException("Ohno! There's no task to delete!");
        }
        Wacka.Task deletedTask = tasks[index];
        
        // Shift all elements to the left
        for (int j = index; j < count - 1; j++) {
            tasks[j] = tasks[j + 1];
        }
        tasks[count - 1] = null;
        count--;

        assert deletedTask != null : "deleted task must not be null";
        return deletedTask;
    }

    public Wacka.Task getTask(int index) throws Wacka.WackaException {
        if (index < 0 || index >= count) {
            throw new Wacka.WackaException("Ohno! Invalid task index!");
        }
        Wacka.Task task = tasks[index];
        assert task != null : "task at valid index must not be null";
        return task;
    }

    public void markTask(int index) throws Wacka.WackaException {
        if (index < 0 || index >= count) {
            throw new Wacka.WackaException("Ohno! Invalid task index!");
        }
        tasks[index].markAsDone();
    }

    public void unmarkTask(int index) throws Wacka.WackaException {
        if (index < 0 || index >= count) {
            throw new Wacka.WackaException("Ohno! Invalid task index!");
        }
        tasks[index].unMark();
    }

    public Wacka.Task[] getTasks() {
        Wacka.Task[] result = new Wacka.Task[count];
        System.arraycopy(tasks, 0, result, 0, count);
        assert result.length == count : "returned array length must match count";
        return result;
    }

    /**
     * Returns tasks whose description contains the given keyword (case-insensitive).
     */
    public Wacka.Task[] findTasks(String keyword) {
        Wacka.Task[] temp = new Wacka.Task[count];
        int matchCount = 0;
        String lowerKeyword = keyword.toLowerCase();
        for (int i = 0; i < count; i++) {
            if (tasks[i].getDescription().toLowerCase().contains(lowerKeyword)) {
                temp[matchCount] = tasks[i];
                matchCount++;
            }
        }
        Wacka.Task[] result = new Wacka.Task[matchCount];
        System.arraycopy(temp, 0, result, 0, matchCount);
        return result;
    }

    public int getCount() {
        return count;
    }
}
