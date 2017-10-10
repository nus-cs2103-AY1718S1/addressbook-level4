package seedu.address.ui;

import static seedu.address.logic.parser.ParserUtil.parseCommandAndArguments;
import static seedu.address.logic.parser.ParserUtil.parseIconCode;

import java.util.Optional;
import java.util.logging.Logger;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CommandInputChangedEvent;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Panel that displays the additional details of a Person
 */
public class CommandBoxIcon extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    private static final String FXML = "CommandBoxIcon.fxml";

    private Ikon iconCode = null;

    @FXML
    private FontIcon icon;

    public CommandBoxIcon() {
        super(FXML);
        icon.setVisible(false);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleCommandInputChangedEvent(CommandInputChangedEvent event) {
        String userInput = event.currentInput;

        String[] command;
        try {
            command = parseCommandAndArguments(userInput);
        } catch (ParseException e) {
            icon.setVisible(false);
            return;
        }

        String commandWord = command[0];

        Optional<Ikon> iconCode = parseIconCode(commandWord);

        if (!iconCode.isPresent()) {
            icon.setVisible(false);
            return;
        }

        icon.iconCodeProperty().set(iconCode.get());
        icon.setVisible(true);
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "CommandBoxIcon updated to " + iconCode.get().getDescription()));
    }

}
