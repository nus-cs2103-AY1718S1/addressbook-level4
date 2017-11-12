package seedu.address.ui;

import java.time.Clock;
import java.util.Date;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.CommandModeChangedEvent;
import seedu.address.model.Model;

//@@author tby1994
/**
 * A ui for the command mode status bar that is displayed at the footer of the application.
 */
public class CommandModeStatusBarFooter extends UiPart<Region> {

    private static final String FXML = "CommandModeStatusBarFooter.fxml";
    private static final Logger logger = LogsCenter.getLogger(CommandModeStatusBarFooter.class);

    private static Clock clock = Clock.systemDefaultZone();

    @FXML
    private Label commandMode;

    public CommandModeStatusBarFooter(Model model) {
        super(FXML);
        commandMode.textProperty().bind(model.getCommandMode().getCommandModeProperty());
        registerAsAnEventHandler(this);
    }

    /**
     * Handles the command mode changed event
     */
    @Subscribe
    private void handleCommandModeChangedEvent(CommandModeChangedEvent event) {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Setting last updated status to " + lastUpdated));
    }
}
