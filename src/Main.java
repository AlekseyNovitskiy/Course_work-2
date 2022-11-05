import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParseException {

        PullTask dailyPlanner = new PullTask();
        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                printMenu();
                System.out.print("Выберите пункт меню: ");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            dailyPlanner.add(inputTask(scanner));
                            break;
                        case 2:
                            deleteTask(scanner, dailyPlanner);
                            break;
                        case 3:
                            getTasksToDate(scanner, dailyPlanner);
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка!");
                }
            }
        }
    }
    private static Task inputTask(Scanner scanner) {
        scanner.nextLine();
        System.out.print("Введите название задачи: \n");
        String taskName = scanner.nextLine();
        System.out.print("Введите описание задачи: \n");
        String taskDescription = scanner.nextLine();
        System.out.println("Выберете тип задачи:");
        String[] allTypeTask = TypeTask.PERSONAL.getListType();
        for (int j = 0; j < allTypeTask.length; j++) {
            System.out.println((j+1) + " " + allTypeTask[j]);
        }
        boolean flag = true;
        String taskType = "";
        while (flag) {
            if(scanner.hasNextInt()) {
                switch (scanner.nextInt()) {
                    case 1:
                        taskType = "PERSONAL";
                        flag = false;
                        break;
                    case 2:
                        taskType = "WORKING";
                        flag = false;
                        break;
                    default:
                        System.out.println("Введено некорректное значение повторите ввод:");
                }
            }
            else{
                scanner.next();
                System.out.println("Введено некорректное значение повторите ввод:");
            }
        }
        System.out.println("Выберете тип повторяемости:");
        String[] allTypeRepeat = TypeRepeat.ANNUAL.getListRepeat();
        for (int j = 0; j < allTypeRepeat.length; j++) {
            System.out.println((j+1) + " " + allTypeRepeat[j]);
        }
        flag = true;
        String taskReapeat = "";
        while (flag) {
            if(scanner.hasNextInt()) {
                switch (scanner.nextInt()) {
                    case 1:
                        taskReapeat = "ONE_TIME";
                        flag = false;
                        break;
                    case 2:
                        taskReapeat = "DAILY";
                        flag = false;
                        break;
                    case 3:
                        taskReapeat = "WEEKLY";
                        flag = false;
                        break;
                    case 4:
                        taskReapeat = "MONTHLY";
                        flag = false;
                        break;
                    case 5:
                        taskReapeat = "ANNUAL";
                        flag = false;
                        break;
                    default:
                        System.out.println("Введено некорректное значение повторите ввод:");
                }
            }
            else{
                scanner.next();
                System.out.println("Введено некорректное значение повторите ввод:");
            }
        }
        Task newTask  = new Task(taskName, taskDescription, taskType, taskReapeat);
        //System.out.println("Создана задача.\n" + newTask);
        return newTask;
    }

    private static void deleteTask(Scanner scanner, PullTask newPull) {
        scanner.nextLine();
        boolean flag = true;
        while (flag) {
            System.out.print("Выберите пункт: \n");
            printDeleteMenu();
            if (scanner.hasNextInt()) {
                switch (scanner.nextInt()) {
                    case 1:
                        System.out.print("Введите ID: \n");
                        scanner.nextLine();
                        if (scanner.hasNextInt()) {
                            newPull.deleteTask(scanner.nextInt());
                        } else {
                            scanner.next();
                            System.out.println("Введено некорректное значение повторите ввод:");
                        }
                        flag = false;
                        break;
                    case 2:
                        System.out.println(newPull);
                        break;
                    case 0:
                        flag = false;
                        break;
                    default:
                        System.out.println("Введено некорректное значение повторите ввод:");
                }
            } else {
                scanner.next();
                System.out.println("Введено некорректное значение повторите ввод:");
            }
        }
    }

    private static void getTasksToDate(Scanner scanner, PullTask newPull) throws ParseException {
        scanner.nextLine();
        boolean flag = true;
        LocalDate dateTasks;
        while (flag) {
            System.out.print("Введите дату в формате YYYY-MM-DD: \n");
            String dateForTasks = scanner.nextLine();
            try {
                dateTasks = LocalDate.parse(dateForTasks);
                PullTask datePullTasks = new PullTask();
                for (Task currentTask: newPull.getTasks()) {
                    if (!currentTask.getDateCreate().isAfter(dateTasks)) {  // Смотрим только на задачи с датой создания до
                        if(currentTask.getRepeat().equals(TypeRepeat.DAILY.getName()))
                        {
                            System.out.println(currentTask);
                        }
                        else
                        {
                            if(checkDateIn(currentTask.getDateCreate(), dateTasks, currentTask.getRepeat()))
                            {
                                System.out.println(currentTask);
                            }
                        }
                    }
                }
                flag = false;
            } catch (DateTimeParseException ex) {
                System.out.println("Введено некорректное значение");
            }
        }
        System.out.println();

    }

    private static void printMenu() {
        System.out.println("1. Добавить задачу\n2. Удалить задачу\n3. Получить задачу на указанный день\n0. Выход");
    }
    private static void printDeleteMenu() {
        System.out.println("1. Удалить задачу по ID\n2. Вывести все задачи\n0. Обратно в меню");
    }

    public static boolean checkDateIn(LocalDate dateTask, LocalDate dateOut, String typeTaskRepeat)
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

}
