package seedu.address.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.task.ReadOnlyTask;

//@@author tby1994
/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskCard.fxml";
    private static final int GREEN_RANGE = 7;
    private static final int YELLOW_RANGE = 3;
    private static final int RED_RANGE = 0;
    private static String[] colours = { "orange", "cyan", "blue", "purple", "pink", "grey", "black" };
    private static HashMap<String, String> tagColours = new HashMap<String, String>();
    private static Random random = new Random();

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

    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText("  " + displayedIndex + ". ");
        initTags(task);
        bindListeners(task);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Task} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyTask task) {
        description.textProperty().bind(Bindings.convert(task.descriptionProperty()));
        startDate.textProperty().bind(Bindings.convert(task.startDateProperty()));
        deadline.textProperty().bind(Bindings.convert(task.deadlineProperty()));
        task.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(task);
        });
        setColour();
    }
    //@@author

    //@@author tpq95
    /**
     * Change colour of taskcard according to urgency of the task
     */
    private void setColour() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String bkgndColour;
        if (!task.getDeadline().isEmpty()) {
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
            if (range >= GREEN_RANGE) {
                bkgndColour = "#6A8A82";
            } else if (range >= YELLOW_RANGE) {
                bkgndColour = "#A37C27";
            } else if (range >= RED_RANGE) {
                bkgndColour = "#A7414A";
            } else {
                bkgndColour = "#878787";
            }
        } else {
            // for task with no deadline
            bkgndColour = "#563838";
        }
        gridPane.setStyle("-fx-background-color: " + bkgndColour + ";"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-height: 2;"
                + "-fx-border-color: black;");
    }
    //@@author

    //@@author tby1994
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

    private static String getColourForTag(String tagValue) {
        if (!tagColours.containsKey(tagValue)) {
            tagColours.put(tagValue, colours[random.nextInt(colours.length)]);
        }

        return tagColours.get(tagValue);
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
