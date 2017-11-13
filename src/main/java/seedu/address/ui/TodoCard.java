//@@author Hailinx
package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.TodoItem;

/**
 * An UI component that displays information of a {@code TodoItem}.
 */
public class TodoCard extends UiPart<Region> {
    private static final String FXML = "TodoCard.fxml";

    @FXML
    private Label id;
    @FXML
    private Label time;
    @FXML
    private Label task;

    public TodoCard(TodoItem item, int displayedIndex) {
        super(FXML);
        id.setText(displayedIndex + ". ");
        bindListeners(item);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code List<TodoItem>} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(TodoItem item) {
        time.textProperty().bind(Bindings.convert(new SimpleObjectProperty<>(item.getTimeString())));
        task.textProperty().bind(Bindings.convert(new SimpleObjectProperty<>(item.task)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TodoCard)) {
            return false;
        }

        // state check
        TodoCard card = (TodoCard) other;
        return id.getText().equals(card.id.getText())
                && time.getText().equals(card.time.getText())
                && task.getText().equals(card.task.getText());
    }
}
