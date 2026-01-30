public class TaskList {
    private Wacka.Task[] tasks;
    private int count;

    public TaskList() {
        this.tasks = new Wacka.Task[100];
        this.count = 0;
    }

    public TaskList(Wacka.Task[] tasks) {
        this.tasks = new Wacka.Task[100];
        this.count = 0;
        if (tasks != null) {
            for (Wacka.Task task : tasks) {
                if (task != null && this.count < 100) {
                    this.tasks[this.count] = task;
                    this.count++;
                }
            }
        }
    }

    public void addTask(Wacka.Task task) {
        if (count < tasks.length) {
            tasks[count] = task;
            count++;
        }
    }

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
        
        return deletedTask;
    }

    public Wacka.Task getTask(int index) throws Wacka.WackaException {
        if (index < 0 || index >= count) {
            throw new Wacka.WackaException("Ohno! Invalid task index!");
        }
        return tasks[index];
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
        return result;
    }

    public int getCount() {
        return count;
    }
}
