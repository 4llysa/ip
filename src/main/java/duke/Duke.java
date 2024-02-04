package duke;

import duke.exception.AllyException;
import duke.parser.Parser;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Todo;
import duke.ui.UI;

import java.util.Scanner;

/**
 * Chatbot to help users manage their tasks.
 * Supports Todos, Deadline and Event tasks.
 */
public class Duke {

    protected static TaskList lst = new TaskList();
    protected static Storage storage = new Storage();

    /**
     * Main Function that controls the ChatBot
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UI ui = new UI(sc);
        ui.showWelcome();
        lst = storage.loadTasks();
        while (ui.hasNextCommand()) {
            try {
                String s = ui.readCommand();
                Parser.Command command = Parser.parseCommand(s);
                String taskDetail = Parser.parseTaskDetail(s);
                switch (command) {
                    case LIST:
                        lst.displayList();
                        break;
                    case BYE:
                        ui.showGoodbye();
                        Storage.saveTasks();
                        return;
                    case MARK:
                        lst.markComplete(Integer.parseInt(taskDetail.trim()));
                        break;
                    case UNMARK:
                        lst.unmarkComplete(Integer.parseInt(taskDetail.trim()));
                        break;
                    case TODO:
                        lst.addToList(new Todo(taskDetail));
                        break;
                    case DEADLINE:
                        lst.addToList(new Deadline(taskDetail));
                        break;
                    case EVENT:
                        lst.addToList(new Event(taskDetail));
                        break;
                    case DELETE:
                        lst.deleteTask(Integer.parseInt(taskDetail.trim()));
                        break;
                    case UNKNOWN:
                        throw new AllyException();
                }
            } catch (AllyException e) {
                ui.showError();
            }
        }
    }


}