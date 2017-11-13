package seedu.address.ui;

import static seedu.address.model.font.FontSize.getAssociateFxFontSizeString;

import java.util.Random;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.address.commons.events.ui.ChangeFontSizeEvent;
import seedu.address.model.font.FontSize;
import seedu.address.model.reminder.ReadOnlyReminder;
//@@author RonakLakhotia
//import javax.swing.text.html.ImageView;


/**
 * An UI component that displays information of a {@code Reminder}.
 */
public class ReminderCard extends UiPart<Region> {

    private static final String FXML = "ReminderListCard.fxml";
    private static Random random = new Random();

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     */

    public final ReadOnlyReminder reminder;

    @FXML
    private HBox cardPane;
    @FXML
    private Label about;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label date;



    public ReminderCard(ReadOnlyReminder reminder, int displayedIndex) {
        super(FXML);
        this.reminder = reminder;
        id.setText(displayedIndex + ". ");
        bindListeners(reminder);
        registerAsAnEventHandler(this);
        String currentFontSize = FontSize.getCurrentFontSizeLabel();
        setFontSize(currentFontSize);
    }


    /**
     * Binds the individual UI elements to observe their respective {@code Reminder} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyReminder reminder) {
        about.textProperty().bind(Bindings.convert(reminder.detailsProperty()));
        priority.textProperty().bind(Bindings.convert(reminder.priorityProperty()));
        date.textProperty().bind(Bindings.convert(reminder.dueDateProperty()));

    }



    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReminderCard)) {
            return false;
        }

        // state check
        ReminderCard card = (ReminderCard) other;
        return id.getText().equals(card.id.getText())
                && reminder.equals(card.reminder);
    }
    //@@author

    @Subscribe
    private void handleChangeFontSizeEvent(ChangeFontSizeEvent event) {
        setFontSize(event.getFontSize());
        //setFontSizeForAllImages(event.getFontSize());
    }

    private void setFontSize(String newFontSize) {
        assert (FontSize.isValidFontSize(newFontSize));

        String fxFormatFontSize = getAssociateFxFontSizeString(newFontSize);
        setFontSizeForAllAttributesExceptTag(fxFormatFontSize);
    }


    private void setFontSizeForAllAttributesExceptTag(String fontSize) {
        about.setStyle(fontSize);
        id.setStyle(fontSize);
        priority.setStyle(fontSize);
        date.setStyle(fontSize);
    }


}
