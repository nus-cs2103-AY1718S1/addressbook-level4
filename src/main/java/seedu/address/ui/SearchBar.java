package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Search bar on the GUI to filter contacts
 */
public class SearchBar extends UiPart<Region> {

    private static final String FXML = "SearchBar.FXML";

    private final Logger logger = LogsCenter.getLogger(SearchBar.class);
    private Logic logic;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField tagsField;

    public SearchBar(Logic logic) {
        super(FXML);
        this.logic = logic;

        nameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                onSearchbarChanged();
            }
        });

        phoneField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                onSearchbarChanged();
            }
        });

        addressField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                onSearchbarChanged();
            }
        });

        emailField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                onSearchbarChanged();
            }
        });

        tagsField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                onSearchbarChanged();
            }
        });
    }

    /**
     * Fires a new FindContainsCommand everytime search bar changes
     */
    @FXML
    private void onSearchbarChanged() {
        String commandString = "find_contain ";

        if (!nameField.getText().equals("")) {
            commandString += "n/" + nameField.getText() + " ";
        }

        if (!phoneField.getText().equals("")) {
            commandString += "p/" + phoneField.getText() + " ";
        }

        if (!emailField.getText().equals("")) {
            commandString += "e/" + emailField.getText() + " ";
        }

        if (!addressField.getText().equals("")) {
            commandString += "a/" + addressField.getText() + " ";
        }

        if (!tagsField.getText().equals("")) {
            commandString += "r/" + tagsField.getText() + " ";
        }

        if (commandString.equals("find_contain ")) {
            try {
                CommandResult commandResult = logic.execute("list");
                logger.info("Result: " + commandResult.feedbackToUser);
                raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
            } catch (ParseException e) {
                //wont happen
            } catch (CommandException e) {
                //wont happen
            }
        } else {
            try {
                CommandResult commandResult = logic.execute(commandString);
                logger.info("Result: " + commandResult.feedbackToUser);
                raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
            } catch (ParseException e) {
                //wont happen
            } catch (CommandException e) {
                //wont happen
            }
        }
    }
}
