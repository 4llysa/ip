package duke.task;

import java.io.Serializable;

public class Task implements Serializable {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public Task() {
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done duke.task with X
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    public void markComplete() {
        this.isDone = true;
    }

    public void unmarkComplete() {
        this.isDone = false;
    }
}
