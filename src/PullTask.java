import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PullTask {
    private Map<Integer,Task> pullTask= new HashMap<>();

    public PullTask()
    {
    }

    public PullTask(Task newTask)
    {
        this.pullTask.put(newTask.getId(), newTask);
    }
    public PullTask(Task... newTasks)
    {
        for (Task newTask: newTasks) {
            this.add(newTask);
        }
    }
    public void add(Task newTask)
    {
        if (this.pullTask == null)
        {
            this.pullTask.put(newTask.getId(), newTask);
            System.out.println("Задача успешно добавлена");
            return;
        }
        if(this.pullTask.containsValue(newTask))
        {
            throw new IllegalArgumentException("Такая задача уже существует");
        }
        else
        {
            this.pullTask.put(newTask.getId(), newTask);
            System.out.println("Задача успешно добавлена");
        }
    }

    public void deleteTask(int deletedNum)
    {
        if (this.pullTask == null)
        {
            throw new NullPointerException("Список задач пуст, удалять нечего");
        }
        if(!this.pullTask.containsKey(deletedNum))
        {
            throw new IllegalArgumentException("Такой задачи нет в списке задач");
        }
        else {
            this.pullTask.remove(deletedNum);
            System.out.println("Задача успешно удалена");
        }
    }

    public Map<Integer, Task> getPullTask() {
        return pullTask;
    }

    public Collection<Task> getTasks() {
        return this.pullTask.values();
    }

    @Override
    public String toString() {
        return "Список из " + this.pullTask.size() + " задач:\n" + pullTask;
    }
}
