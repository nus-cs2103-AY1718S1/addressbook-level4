package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.task.ReadOnlyTask;

import java.util.HashMap;
import java.util.Random;

/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskCard.fxml";
    private static String[] colours = { "red", "orange", "cyan", "green", "blue", "purple", "pink", "grey", "black" };
    private static HashMap<String, String> tagColours = new HashMap<String, String>();
    private static Random random = new Random();

    public final ReadOnlyTask task;

    @FXML
    private Label id;

    @FXML
    private Label description;

    @FXML
    private Label startDate;

    @FXML
    private Label deadline;

    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText("  " + displayedIndex + ". ");
        initTags(task);
        bindListeners(task);
    }

    private static String getColourForTag(String tagValue) {
        if (!tagColours.containsKey(tagValue)) {
            tagColours.put(tagValue, colours[random.nextInt(colours.length)]);
        }

        return tagColours.get(tagValue);
    }


    /**
     * Binds the individual UI elements to observe their respective {@code Task} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyTask task) {
        description.textProperty().bind(Bindings.convert(task.descriptionProperty()));
        startDate.textProperty().bind(Bindings.convert(task.startDateProperty()));
        deadline.textProperty().bind(Bindings.convert(task.deadlineProperty()));
    }


    /**
     * Set the colour of label for the same tag
     * @param task
     */
    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColourForTag(tag.tagName));
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
