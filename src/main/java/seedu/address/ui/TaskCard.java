package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import seedu.address.model.task.ReadOnlyTask;

//@@author deep4k
/**
 * UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    private static final String OVERDUE_PREFIX = "Overdue\n";
    private static final String COMPLETED_PREFIX = "Completed on ";
    private static final String TASK_TIME_PATTERN = "HH:mm EEE, dd MMM";
    private static final String COMPLETED_TIME_PATTERN = "EEE, dd MMM";
    private static final String START_TIME_PREFIX = "from ";
    private static final String END_TIME_PREFIX = " to ";
    private static final String DEADLINE_PREFIX = "by ";
    private static final String EMPTY_PREFIX = "";
    private static final String OVERDUE_STYLE = "-fx-background-color: rgba(244, 67, 54, 0.8)";
    private static final String UPCOMING_STYLE = "-fx-background-color: rgba(170,181,46,0.8)";
    private static final String OTHER_STYLE = "-fx-background-color: rgba(7,38,255,0.51)";
    private static final Color NAME_COLOR_DARK = Color.web("#3a3d42");
    private static final Color TIME_COLOR_DARK = Color.web("#4172c1");
    private static final Color NAME_COLOR_LIGHT = Color.web("#ffffff");
    private static final Color TIME_COLOR_LIGHT = Color.web("#fff59d");

    private final ReadOnlyTask task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label header;
    @FXML
    private Label id;
    @FXML
    private VBox taskVbox;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        initTimeStatus();
        bindListeners(task);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Task} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyTask task) {
        header.textProperty().bind(Bindings.convert(task.headerProperty()));
    }

    /**
     * Initializes and binds the values that are not implemented with ObjectProperty
     */
    @FXML
    public void initTimeStatus() {

        Label time = new Label();
        time.setId("time");

        if (task.isOverdue()) {
            cardPane.setStyle(OVERDUE_STYLE);
            header.setTextFill(NAME_COLOR_LIGHT);
            time.setTextFill(TIME_COLOR_LIGHT);
            id.setTextFill(NAME_COLOR_LIGHT);
        } else if (task.isUpcoming()) {
            cardPane.setStyle(UPCOMING_STYLE);
            header.setTextFill(NAME_COLOR_DARK);
            time.setTextFill(TIME_COLOR_DARK);
        } else {
            cardPane.setStyle(OTHER_STYLE);
            header.setTextFill(NAME_COLOR_DARK);
            time.setTextFill(TIME_COLOR_DARK);
        }

        StringBuilder timeDescription = new StringBuilder();
        timeDescription.append(formatTaskTime(task));

        if (task.isCompleted()) {
            timeDescription.append(formatUpdatedTime(task));
        }

        time.setText(timeDescription.toString());
        time.setMaxHeight(Control.USE_COMPUTED_SIZE);
        time.setWrapText(true);

        if (task.hasTime() || task.isCompleted()) {
            taskVbox.getChildren().add(time);
            taskVbox.setAlignment(Pos.CENTER_LEFT);
            time.setAlignment(Pos.CENTER_LEFT);
            time.setFont(Font.font("Segoe UI Semibold", FontPosture.ITALIC, 11));
        }

    }

    /**
     * Formats LocalTime to String based on the pattern provided
     *
     * @param dateTimePattern String format for date
     * @param prefix          Start-time , end-time , deadline
     * @return required time depending on prefix, as String
     */
    private String formatTime(String dateTimePattern, String prefix, Optional<LocalDateTime> dateTime) {

        StringBuilder sb = new StringBuilder();
        DateTimeFormatter format = DateTimeFormatter.ofPattern(dateTimePattern);
        sb.append(prefix).append(dateTime.get().format(format));

        return sb.toString();
    }

    /**
     * Extracts LocalDateTime from provided ReadOnlyTask and returns it as String
     *
     * @param task : ReadOnlyTask provided
     * @return required time as String
     */
    private String formatTaskTime(ReadOnlyTask task) {

        StringBuilder timeStringBuilder = new StringBuilder();

        if (task.isOverdue()) {
            timeStringBuilder.append(OVERDUE_PREFIX);
        }

        if (task.isEvent()) {
            String startTime = formatTime(TASK_TIME_PATTERN, START_TIME_PREFIX, task.getStartDateTime());
            String endTime = formatTime(TASK_TIME_PATTERN, END_TIME_PREFIX, task.getEndDateTime());
            timeStringBuilder.append(startTime);
            timeStringBuilder.append(endTime);
        } else if (task.hasDeadline()) {
            String deadline = formatTime(TASK_TIME_PATTERN, DEADLINE_PREFIX, task.getEndDateTime());
            timeStringBuilder.append(deadline);
        }

        return timeStringBuilder.toString();
    }

    /**
     * Updated LocalDateTime from provided ReadOnlyTask and returns it as String
     *
     * @param task : ReadOnlyTask provided
     * @return updated time as String
     */
    private String formatUpdatedTime(ReadOnlyTask task) {
        StringBuilder timeStringBuilder = new StringBuilder();
        if (task.hasTime()) {
            timeStringBuilder.append("\n");
        }
        timeStringBuilder.append(COMPLETED_PREFIX);
        timeStringBuilder.append(formatTime(COMPLETED_TIME_PATTERN, EMPTY_PREFIX,
                Optional.ofNullable(task.getLastUpdatedTime())));
        return timeStringBuilder.toString();
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

