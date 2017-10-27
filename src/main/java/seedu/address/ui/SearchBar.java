package seedu.address.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.logging.Logger;

/**
 * Search bar on the GUI to filter contacts
 */
public class SearchBar extends UiPart<Region> {

    private static final String FXML = "SearchBar.FXML";

    private final Logger logger = LogsCenter.getLogger(SearchBar.class);
    private Logic logic;

    @FXML
    private CheckBox nameCheckBox;

    @FXML
    private CheckBox phoneCheckBox;

    @FXML
    private CheckBox emailCheckBox;

    @FXML
    private CheckBox addressCheckBox;

    @FXML
    private CheckBox tagsCheckBox;

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

    @FXML
    private void onSearchbarChanged() {
        String commandString = "find ";

        if (nameCheckBox.isSelected() && !nameField.getText().equals("")) {
            commandString += "n/" + nameField.getText() + " ";
        }

        if (phoneCheckBox.isSelected() && !phoneField.getText().equals("")) {
            commandString += "p/" + phoneField.getText() + " ";
        }

        if (emailCheckBox.isSelected() && !emailField.getText().equals("")) {
            commandString += "e/" + emailField.getText() + " ";
        }

        if (addressCheckBox.isSelected() && !addressField.getText().equals("")) {
            commandString += "a/" + addressField.getText() + " ";
        }

        if (tagsCheckBox.isSelected() && !tagsField.getText().equals("")) {
            commandString += "t/" + tagsField.getText() + " ";
        }

        if (commandString.equals("find ")) {
            return;
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
