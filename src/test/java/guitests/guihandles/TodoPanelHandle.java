//@@author Hailinx
package guitests.guihandles;

import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.ui.TodoCard;

/**
 * Provides a handle for {@code TodoPanel} containing the list of {@code TodoCard}.
 */
public class TodoPanelHandle extends NodeHandle<ListView<TodoCard>> {
    public static final String TODO_LIST_VIEW_ID = "#todoCardList";

    public TodoPanelHandle(ListView<TodoCard> todoPanelNode) {
        super(todoPanelNode);
    }

    /**
     * Returns the todoCard handle of a person associated with the {@code index} in the list.
     */
    public TodoCardHandle getTodoCardHandle(int index) {
        return getTodoCardHandle(getRootNode().getItems().get(index));
    }

    /**
     * Returns the {@code TodoCardHandle} of the specified {@code todoCard} in the list.
     */
    public TodoCardHandle getTodoCardHandle(TodoCard todoCard) {
        Optional<TodoCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.equals(todoCard))
                .map(card -> new TodoCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("TodoCard does not exist."));
    }

}
