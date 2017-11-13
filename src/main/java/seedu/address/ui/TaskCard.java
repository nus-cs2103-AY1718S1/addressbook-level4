package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.ReadOnlyTask;

//@@author Esilocke
/**
 * A UI component that displays the information of a {@code Task}
 * */
public class TaskCard  extends UiPart<Region> {

    public static final int DEFAULT_NAME_SIZE = 15;
    public static final int DEFAULT_ATTRIBUTE_SIZE = 10;
    public static final int FONT_SIZE_EXTENDER = 5;
    public static final int DEFAULT_FONT_SIZE_MULTIPLIER = 0;

    private static final String FXML = "TaskListCard.fxml";
    private static int nameSize = DEFAULT_NAME_SIZE;
    private static int attributeSize = DEFAULT_ATTRIBUTE_SIZE;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyTask task;

    @FXML
    private HBox taskCardPane;
    @FXML
    private Label taskName;
    @FXML
    private Label id;
    @FXML
    private Label state;
    @FXML
    private Label description;
    @FXML
    private Label deadline;
    @FXML
    private Label priority;
    @FXML
    private Label assignCount;
    @FXML
    private Label taskAddress;

    private int fontSizeMultipler;

    public TaskCard(ReadOnlyTask task, int displayedIndex, int fontSizeMultiplier) {
        super(FXML);
        this.task = task;
        this.fontSizeMultipler = fontSizeMultiplier;
        id.setText(displayedIndex + ". ");
        bindListeners(task);
        updateAttributeSizes();
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Task} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyTask task) {
        taskName.textProperty().bind(Bindings.convert(task.taskNameProperty()));
        description.textProperty().bind(Bindings.convert(task.descriptionProperty()));
        deadline.textProperty().bind(Bindings.convert(task.deadlineProperty()));
        priority.textProperty().bind(Bindings.convert(task.priorityProperty()));
        assignCount.textProperty().bind(Bindings.convert(task.assigneeProperty()));
        taskAddress.textProperty().bind(Bindings.convert(task.taskAddressProperty()));
        state.textProperty().bind(Bindings.convert(task.stateProperty()));
    }

    //@@author charlesgoh
    /**
     * Set default size for all attributes
     */
    public void updateAttributeSizes() {
        nameSize = DEFAULT_NAME_SIZE + (fontSizeMultipler * FONT_SIZE_EXTENDER);
        attributeSize = DEFAULT_ATTRIBUTE_SIZE + (fontSizeMultipler * FONT_SIZE_EXTENDER);

        // Set styles using set name and attribute sizes
        taskName.setStyle("-fx-font-size: " + Integer.toString(nameSize));
        id.setStyle("-fx-font-size: " + Integer.toString(nameSize));
        description.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
        deadline.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
        priority.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
        taskAddress.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
        assignCount.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
        state.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
    }
    //@@author charlesgoh

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

    public Label getTaskName() {
        return taskName;
    }

    public Label getId() {
        return id;
    }

    public Label getDescription() {
        return description;
    }

    public Label getDeadline() {
        return deadline;
    }

    public Label getPriority() {
        return priority;
    }

    public Label getTaskAddress() {
        return taskAddress;
    }

    public void setFontSizeMultiplier(int fontSizeMultipler) {
        this.fontSizeMultipler = fontSizeMultipler;
    }

    public int getFontSizeMultiplier() {
        return this.fontSizeMultipler;
    }
}
