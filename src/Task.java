import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Task {

    public static int generator = 1;
    private int id;
    private String heading;
    private String description;
    private String type;
    private String repeat;
    private LocalDate dateCreate;// Создал для удобства можно обойтись и без этого поля
    private LocalDateTime dateTimeCreate;

    public Task(String heading, String description, String type, String repeat)
    {
        if (heading.isBlank() || description.isBlank() || type.isBlank() || repeat.isBlank()) {
            throw new IllegalArgumentException("Некорректно заполнено одно или несколько полей");
        }
        this.id = generator;
        generator++;
        this.heading = heading;
        this.description = description;
        this.type = TypeTask.valueOf(type).getName();
        this.repeat = TypeRepeat.valueOf(repeat).getName();
        this.dateTimeCreate = LocalDateTime.now();
        this.dateCreate = this.dateTimeCreate.toLocalDate();
    }

    public LocalDateTime getNextDate(String repeat)
    {
        if (repeat.isBlank())
        {
            throw new IllegalArgumentException("Указана некорректная повторяемость");
        }
        switch (repeat)
        {
            case "однократная":
                return this.dateTimeCreate;
            case "ежедневная":
                return this.dateTimeCreate.plusDays(1);
            case "еженедельная":
                return this.dateTimeCreate.plusDays(7);
            case "ежемесячнвя":
                return this.dateTimeCreate.plusMonths(1);
            case  "ежегодная":
                return this.dateTimeCreate.plusYears(1);
            default:
                return null;
        }
    }

    public int getId() {
        return id;
    }

    public String getHeading() {
        return heading;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getRepeat() {
        return repeat;
    }

    public LocalDate getDateCreate() {
        return dateCreate;
    }

    @Override
    public String toString() {
        return "Задача: " +
                "Идентификатор:" + id +
                "\n Заголовок:\n" + heading +
                "\n Описание:\n" + description +
                "\n Тип задачи: " + type +
                ". Повторяемость: " + repeat +
                ".\n Дата создания: " + dateCreate +
                ". Полная дата и время создания: " + dateTimeCreate + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return Objects.equals(heading, task.heading) && Objects.equals(description, task.description) && Objects.equals(type, task.type) && Objects.equals(repeat, task.repeat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(heading, description, type, repeat);
    }
}
