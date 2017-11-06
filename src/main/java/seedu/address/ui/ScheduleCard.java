//@@author 17navasaw
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Name;
import seedu.address.model.schedule.Schedule;

/**
 * An UI component that displays information of a {@code Schedule}.
 */
public class ScheduleCard extends UiPart<Region> {
    private static final String FXML = "ScheduleCard.fxml";

    public final Schedule schedule;
    private final Logger logger = LogsCenter.getLogger(ScheduleCard.class);

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private HBox cardPane;
    @FXML
    private Label activity;
    @FXML
    private Label number;
    @FXML
    private Label date;
    @FXML
    private VBox personNames;

    @FXML
    private Label dateHeader;
    @FXML
    private Label personsInvolvedHeader;

    public ScheduleCard(Schedule schedule, int displayedIndex) {
        super(FXML);
        this.schedule = schedule;
        number.setText(displayedIndex + ". ");
        setHeaderStyles();
        initPersonNames(schedule);
        bindListeners(schedule);
    }

    /**
     * Sets the styles for headings in the ScheduleCard.
     */
    private void setHeaderStyles() {
        dateHeader.getStyleClass().remove("label");
        dateHeader.getStyleClass().add("headers");
        personsInvolvedHeader.getStyleClass().remove("label");
        personsInvolvedHeader.getStyleClass().add("headers");
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Schedule} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(Schedule schedule) {
        logger.fine("Binding listeners...");

        activity.textProperty().bind(Bindings.convert(schedule.getActivityProperty()));
        date.textProperty().bind(Bindings.convert(schedule.getScheduleDateProperty()));
        schedule.getPersonInvolvedNamesProperty().addListener((observable, oldValue, newValue) -> {
            personNames.getChildren().clear();
            initPersonNames(schedule);
        });
    }

    /**
     * Sets person names involved in the scheduling to the respective UI labels upon startup.
     */
    private void initPersonNames(Schedule schedule) {
        for (Name name: schedule.getPersonInvolvedNames()) {
            logger.info(name.fullName);
        }
        schedule.getPersonInvolvedNames().forEach(personName -> {
            Label personNameLabel = new Label(personName.fullName);
            personNames.getChildren().add(personNameLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ScheduleCard)) {
            return false;
        }

        // state check
        ScheduleCard card = (ScheduleCard) other;
        return number.getText().equals(card.number.getText())
                && schedule.equals(card.schedule);
    }
}
