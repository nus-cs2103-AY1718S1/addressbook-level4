//@@author qihao27
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a todolist count in the person card.
 */
public class TodoCountHandle extends NodeHandle<Node> {
    private static final String TODO_FIELD_ID = "#totalTodo";

    private final Label todoLabel;

    public TodoCountHandle(Node todoNode) {
        super(todoNode);

        this.todoLabel = getChildNode(TODO_FIELD_ID);
    }

    public String getTodoCount() {
        return todoLabel.getText();
    }
}
