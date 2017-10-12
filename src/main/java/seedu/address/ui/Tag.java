package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import javax.print.attribute.standard.NumberUp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Tag of a Person
 */
public class Tag extends UiPart<Label> {

    private static final String FXML = "Tag.fxml";
    private final Logger logger = LogsCenter.getLogger(Tag.class);

    private Logic logic;
    private String value;
    @FXML
    private Label tag;

    public Tag(String tagValue) {
        super(FXML);
        tag.setText(tagValue);
        value = tagValue;
        if (MainWindow.getInstance() != null) {
            logic = MainWindow.getInstance().getLogic();
        }
    }

    /**
     * onMouseClicked, a search for tag will be executed
     */
    @FXML
    private void handleClick(MouseEvent event) {
        try {
            CommandResult commandResult =
                    logic.execute(FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_TAG_STRING + value);
            // process result of the command
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException | ParseException e) {
            // handle command failure
            logger.info("Invalid tag find: " + value);
            raise(new NewResultAvailableEvent(e.getMessage()));
        } catch (NullPointerException ne) {
            logger.info("Address Book Logic not initialised");
            assert false;
        }
    }

    public String getText() {
        return value;
    }

    public Label getLabel() {
        return tag;
    }

}
