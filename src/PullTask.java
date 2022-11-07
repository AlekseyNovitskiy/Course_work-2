import java.time.LocalDate;
import java.util.*;

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

    public Collection<Task> getTasks(LocalDate dateTasks) {
        Set<Task> setTasks = new HashSet<>();
        for (Task currentTask: this.pullTask.values()) {
            if (!currentTask.getDateCreate().isAfter(dateTasks)) {  // Смотрим только на задачи с датой создания до
                if(currentTask.getRepeat().equals(TypeRepeat.DAILY.getName()))
                {
                    setTasks.add(currentTask);
                }
                else
                {
                    if(checkDateIn(currentTask.getDateCreate(), dateTasks, currentTask.getRepeat()))
                    {
                        setTasks.add(currentTask);
                    }
                }
            }
        }
        return setTasks;
    }

    public boolean checkDateIn(LocalDate dateTask, LocalDate dateOut, String typeTaskRepeat)
    {
        switch (typeTaskRepeat) {
            case "однократная":
                if(dateTask.equals(dateOut)) {
                    return true;
                }
                else
                {
                    return false;
                }
            case "еженедельная":
                while (dateTask.isBefore(dateOut))
                {
                    dateTask = dateTask.plusDays(7);
                }
                if(dateTask.equals(dateOut)) {
                    return true;
                }
                else
                {
                    return false;
                }
            case "ежемесячная":
                while (dateTask.isBefore(dateOut))
                {
                    dateTask = dateTask.plusMonths(1);
                }
                if(dateTask.equals(dateOut)) {
                    return true;
                }
                else
                {
                    return false;
                }
            case "ежегодная":
                while (dateTask.isBefore(dateOut))
                {
                    dateTask = dateTask.plusYears(1);
                }
                if(dateTask.equals(dateOut)) {
                    return true;
                }
                else
                {
                    return false;
                }
            default:
                return false;
        }

    }

    @Override
    public String toString() {
        return "Список из " + this.pullTask.size() + " задач:\n" + pullTask;
    }
}
