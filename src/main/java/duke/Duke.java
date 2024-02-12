package duke;

import duke.exception.AllyException;
import duke.parser.Parser;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Todo;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;


/**
 * Chatbot to help users manage their tasks.
 * Supports Todos, Deadline and Event tasks.
 */
public class Duke {

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;
    private Image user = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image duke = new Image(this.getClass().getResourceAsStream("/images/duke.png"));
    private MainWindow mainWindow;

    protected static TaskList lst = new TaskList();
    protected static Storage storage = new Storage();

    public Duke() {
        this.lst = storage.loadTasks();
    }

    protected void linkMainWindow(MainWindow mw) {
        this.mainWindow = mw;
        String logo = "        _  _        \n  __ _ | || | _   _ \n / _` || || || | | |\n| (_| || || || |_| |\n \\__,_||_||_| \\__, |\n              |___/ \n";
        mw.addDukeMessage("Hello from" + "\n" + logo + "\n" + "How can I help you today?");
    }

    protected String getResponse(String s) {
            try {
                Parser.Command command = Parser.parseCommand(s);
                String taskDetail = Parser.parseTaskDetail(s);
                switch (command) {
                    case LIST:
                        return lst.getTasks();
                    case BYE:
                        Storage.saveTasks();
                        mainWindow.addDukeMessage("Bye. Hope to see you again soon!");
                        System.exit(0);
                    case MARK:
                        return lst.markComplete(Integer.parseInt(taskDetail.trim()));
                    case UNMARK:
                        return lst.unmarkComplete(Integer.parseInt(taskDetail.trim()));
                    case TODO:
                        return lst.addToList(new Todo(taskDetail));
                    case DEADLINE:
                        return lst.addToList(new Deadline(taskDetail));

                    case EVENT:
                        return lst.addToList(new Event(taskDetail));

                    case DELETE:
                        return lst.deleteTask(Integer.parseInt(taskDetail.trim()));

                    case FIND:
                        assert lst != null;
                        Finder finder = new Finder(lst);
                        return finder.find(taskDetail);

                    case UNKNOWN:
                        throw new AllyException();
                }
            } catch (AllyException e) {
            }
        return "don't understand u bodoh";
    }
}