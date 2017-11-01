package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TodoItem;

import java.util.List;
import java.util.logging.Logger;

import static seedu.address.model.util.TimeConvertUtil.convertTimeToString;

public class TodoCard extends UiPart<Region> {
    private static final String FXML = "TodoCard.fxml";
    private final Logger logger = LogsCenter.getLogger(TodoCard.class);

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
        String timeStr;
        String startTimeStr = convertTimeToString(item.start);
        if (item.end != null) {
            String endTime = convertTimeToString(item.end);
            timeStr = "From: " + startTimeStr + "   To: " + endTime;
        } else {
            timeStr = "From: " + startTimeStr;
        }

        time.textProperty().bind(Bindings.convert(new SimpleObjectProperty<>(timeStr)));
        task.textProperty().bind(Bindings.convert(new SimpleObjectProperty<>(item.task)));
    }
}
