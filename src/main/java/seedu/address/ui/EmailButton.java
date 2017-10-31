package seedu.address.ui;

import java.io.IOException;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
//@@author blaqkrow
/**
 * The UI component that is responsible for emailing the selected person.
 */
public class EmailButton extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "EmailButton.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private String selectedEmail  = "";

    @FXML
    private Button emailButton;
    public EmailButton() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     * Handles the Email button pressed event.
     */
    @FXML
    private void handleEmailButtonPressed() throws CommandException, ParseException, IOException {
        OpenEmailClient emailClient = new OpenEmailClient(this.selectedEmail);
        emailClient.sendMail();
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        this.selectedEmail = event.getNewSelection().person.emailProperty().getValue().toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }



}
