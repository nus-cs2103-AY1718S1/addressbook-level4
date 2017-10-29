package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.task.ReadOnlyTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskCard.fxml";
    private static final int GREEN_RANGE = 7;
    private static final int YELLOW_RANGE = 3;

    public final ReadOnlyTask task;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label id;

    @FXML
    private Label description;

    @FXML
    private Label startDate;

    @FXML
    private Label deadline;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText("  " + displayedIndex + ". ");
        bindListeners(task);
        setColour();
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
     * Change colour of taskcard according to urgency of the task
     */
    private void setColour() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String taskDate = "";
        try {
            Date deadline = ParserUtil.parseDate(task.getDeadline().date);
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            taskDate = dateFormat.format(deadline);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        LocalDate deaddate = LocalDate.parse(taskDate, formatter);
        int range = deaddate.getDayOfYear() - date.getDayOfYear();
        String bkgndColour;
        if (range >= GREEN_RANGE) {
            bkgndColour = "#00c300";
        } else if (range >= YELLOW_RANGE) {
            bkgndColour = "#d1d14f";
        } else if (range == 0){
            bkgndColour = "#ff444d";
        } else {
            bkgndColour = "#878787";
        }
        gridPane.setStyle("-fx-background-color: " + bkgndColour + ";"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-height: 2;"
                + "-fx-border-color: black;");
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
