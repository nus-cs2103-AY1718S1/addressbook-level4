package seedu.address.ui;

import java.io.IOException;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.QrEvent;
import seedu.address.logic.commands.LoggingCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author blaqkrow
/**
 * The UI component that is for generating a QR code which calls a contact on a smartphone.
 */
public class QrButton extends UiPart<Region> {
    public static final String MESSAGE_FAIL = "Please select someone";
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "QrButton.fxml";
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private LoggingCommand loggingCommand;
    private BrowserPanel bp;
    private ReadOnlyPerson person;

    public QrButton(BrowserPanel bp) {
        super(FXML);
        loggingCommand = new LoggingCommand();
        this.bp = bp;
        registerAsAnEventHandler(this);
    }
    /**
     * Handles the QR button pressed event.
     */
    @FXML
    private void handleQrButtonPressed() throws CommandException, ParseException, IOException {
        if (person != null) {
            bp.loadQrCode(person);
            logger.info("QR Code displayed");
        } else {
            logger.info(MESSAGE_FAIL);
        }
    }
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.person = event.getNewSelection().person;
    }
    @Subscribe
    private void clickButton(QrEvent event) {
        bp.loadQrCode((ReadOnlyPerson) event.getPerson());
    }
}
