// @@author tanchc
package seedu.address.ui;

import java.util.HashMap;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.address.model.task.ReadOnlyTask;


/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    private static final double GAP = 8;

    private static String[] colors = { "red", "blue", "orange", "brown", "green", "black", "grey", "yellow", "pink" };

    private static HashMap<String, String> moduleColors = new HashMap<String, String>();
    private static Random random = new Random();
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyTask task;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label taskname;
    @FXML
    private Label idtask;
    @FXML
    private Label date;
    @FXML
    private Label startTime;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        this.task = task;
        idtask.setText(displayedIndex + ". ");
        bindListeners(task);
    }

    private static int getRandom() {
        int randNum = random.nextInt(colors.length);
        return randNum;
    }

    private static String getColorForModule(String modValue, int randNum) {
        if (!moduleColors.containsKey(modValue)) {
            moduleColors.put(modValue, colors[randNum]);
        }

        return moduleColors.get(modValue);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Task} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyTask task) {
        taskname.textProperty().bind(Bindings.convert(task.appointmentProperty()));
        date.textProperty().bind(Bindings.convert(task.dateProperty()));
        startTime.textProperty().bind(Bindings.convert(task.startTimeProperty()));
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
        return idtask.getText().equals(card.idtask.getText())
                && task.equals(card.task);
    }
}
