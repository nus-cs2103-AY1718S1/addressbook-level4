package seedu.address.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.ReadOnlyTask;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    private static ArrayList<String> colors = new ArrayList<String>(
            Arrays.asList("Tomato", "Orange", "DodgerBlue", "MediumSeaGreen", "SlateBlue", "Violet", "Maroon"));
    private static HashMap<String, String> tagColors = new HashMap<String, String>();

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyTask task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label startDateTime;
    @FXML
    private Label endDateTime;
    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        initTags(task);
        bindListeners(task);
    }

    private static String getTagColor(String tagName) {
        if (!tagColors.containsKey(tagName)) {
            String color = colors.get(0);
            tagColors.put(tagName, color);
            colors.remove(0);
            colors.add(color);
        }

        return tagColors.get(tagName);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Task} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyTask task) {
        name.textProperty().bind(Bindings.convert(task.nameProperty()));
        description.textProperty().bind(Bindings.convert(task.descriptionProperty()));
        startDateTime.textProperty().bind(Bindings.convert(task.startTimeProperty()));
        endDateTime.textProperty().bind(Bindings.convert(task.endTimeProperty()));
        task.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(task);
        });
    }

    /**
     * initialize tags and assign them with a color. tags haven't be met before will be given a new color from the list.
     * @param task
     */
    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getTagColor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
