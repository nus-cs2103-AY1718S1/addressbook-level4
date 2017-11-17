# Jacob
###### \seedu\address\ui\AddWindow.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Controller for a help page
 */
public class AddWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(AddWindow.class);
    private static final String ICON = "/images/add_icon.png";
    private static final String FXML = "AddWindow.fxml";
    private static final String TITLE = "Add";

    private final Logic logic;

    @FXML
    private TextField nameField;
    @FXML
    private TextField tagsField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField birthdayField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField scoreField;
    @FXML
    private Button add;

    private final Stage dialogStage;

    public AddWindow(Logic inlogic) {
        super(FXML);
        logic = inlogic;
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaxHeight(500);
        dialogStage.setMaxWidth(800);
        FxViewUtil.setStageIcon(dialogStage, ICON);
    }

    /**
     * Shows the edit window.
     * @throws IllegalStateException
     */
    public void show() {
        logger.fine("Showing edit window to modify person.");
        dialogStage.showAndWait();
    }

    /**
     * handles button event given to it by the fxml doc that it is set as controller for by the constructor in UiPart
     * @param buttonEvent
     */
    @FXML
    private void handleAddSubmitAction(ActionEvent buttonEvent) {
        try {
            //first the command
            String commandText = "add ";
            //then its arguments
            if (nameField.getText().length() != 0) {
                commandText = commandText + " n/" + nameField.getText();
            }
            if (phoneField.getText().length() != 0) {
                commandText = commandText + " p/" + phoneField.getText();
            }
            if (emailField.getText().length() != 0) {
                commandText = commandText + " e/" + emailField.getText();
            }
            if (birthdayField.getText().length() != 0) {
                commandText = commandText + " b/" + birthdayField.getText();
            }
            if (addressField.getText().length() != 0) {
                commandText = commandText + " a/" + addressField.getText();
            }
            String tagsText = tagsField.getText();
            if (tagsText.length() != 0) {
                String tags = tagsText;
                int lastIndex = 0;
                for (int tagsIndex = 0; tagsIndex < tags.length(); tagsIndex++) {
                    String s = "";
                    if ((s + tags.charAt(tagsIndex)).equals(" ")) {
                        commandText = commandText + " t/" + tagsText.substring(lastIndex, tagsIndex);
                        lastIndex = tagsIndex;
                    }
                }
                commandText = commandText + " t/" + tagsText.substring(lastIndex, tagsText.length());
            }
            if (scoreField.getText().length() != 0) {
                commandText = commandText + " s/" + scoreField.getText();
            }
            CommandResult commandResult = logic.execute(commandText);
            //Stage stage = (Stage) edit.getScene().getWindow();
            //stage.close(); //TODO: Get the window to close on editing.
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
            final Node source = (Node) buttonEvent.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        } catch (CommandException | ParseException e) {
            // handle command failure
            logger.info("Add call failed");
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }
}
```
###### \seedu\address\ui\CommandBox.java
``` java
    /**
     * handles button events given to it by the fxml doc that it is set as controller for by the constructor in UiPart
     * @param buttonEvent
     */
    @FXML
    private void handleAddButtonAction(ActionEvent buttonEvent) {
```
###### \seedu\address\ui\CommandBox.java
``` java
            AddWindow addWindow = new AddWindow(logic);
            addWindow.show();
        }
        logger.info("Result: " + commandResult.feedbackToUser);
        raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
    }
    /**
     * handles button events given to it by the fxml doc that it is set as controller for by the constructor in UiPart
     * @param buttonEvent
     */
    @FXML
    private void handleUndoButtonAction(ActionEvent buttonEvent) {
        try {
            CommandResult commandResult = logic.execute("undo");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
        } catch (CommandException | ParseException e) {
            // handle command failure
            logger.info("Delete call failed on index undo");
            raise(new NewResultAvailableEvent(e.getMessage()));
        }

    }

    /**
     * handles button events given to it by the fxml doc that it is set as controller for by the constructor in UiPart
     * @param buttonEvent
     */
    @FXML
    private void handleRedoButtonAction(ActionEvent buttonEvent) {
        try {
            CommandResult commandResult = logic.execute("redo");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
        } catch (CommandException | ParseException e) {
            // handle command failure
            logger.info("Delete call failed on index redo");
            raise(new NewResultAvailableEvent(e.getMessage()));
        }

    }
```
###### \seedu\address\ui\EditWindow.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Controller for a help page
 */
public class EditWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(EditWindow.class);
    private static final String ICON = "/images/edit_icon.png";
    private static final String FXML = "EditWindow.fxml";
    private static final String TITLE = "Edit";

    private final int cardNum;
    private final Logic logic;

    @FXML
    private TextField nameField;
    @FXML
    private TextField tagsField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField birthdayField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField scoreField;
    @FXML
    private Button edit;

    private final Stage dialogStage;

    public EditWindow(Logic inlogic, int card) {
        super(FXML);
        logic = inlogic;
        cardNum = card;
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaxHeight(500);
        dialogStage.setMaxWidth(800);
        FxViewUtil.setStageIcon(dialogStage, ICON);
    }

    /**
     * Shows the edit window.
     * @throws IllegalStateException
     */
    public void show() {
        logger.fine("Showing edit window to modify person.");
        dialogStage.showAndWait();
    }

    /**
     * handles button event given to it by the fxml doc that it is set as controller for by the constructor in UiPart
     * @param buttonEvent
     */
    @FXML
    private void handleEditSubmitAction(ActionEvent buttonEvent) {
        try {
            String commandText = "edit " + cardNum;
            if (nameField.getText().length() != 0) {
                commandText = commandText + " n/" + nameField.getText();
            }
            if (phoneField.getText().length() != 0) {
                commandText = commandText + " p/" + phoneField.getText();
            }
            if (emailField.getText().length() != 0) {
                commandText = commandText + " e/" + emailField.getText();
            }
            if (birthdayField.getText().length() != 0) {
                commandText = commandText + " b/" + birthdayField.getText();
            }
            if (addressField.getText().length() != 0) {
                commandText = commandText + " a/" + addressField.getText();
            }
            String tagsText = tagsField.getText();
            if (tagsText.length() != 0) {
                String tags = tagsText;
                int lastIndex = 0;
                for (int tagsIndex = 0; tagsIndex < tags.length(); tagsIndex++) {
                    String s = "";
                    if ((s + tags.charAt(tagsIndex)).equals(" ")) {
                        commandText = commandText + " t/" + tagsText.substring(lastIndex, tagsIndex);
                        lastIndex = tagsIndex;
                    }
                }
                commandText = commandText + " t/" + tagsText.substring(lastIndex, tagsText.length());
            }
            if (scoreField.getText().length() != 0) {
                commandText = commandText + " s/" + scoreField.getText();
            }
            CommandResult commandResult = logic.execute(commandText);
            //Stage stage = (Stage) edit.getScene().getWindow();
            //stage.close(); //TODO: Get the window to close on editing.
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
            final Node source = (Node) buttonEvent.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        } catch (CommandException | ParseException e) {
            // handle command failure
            logger.info("Edit call failed");
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }
}
```
###### \seedu\address\ui\PersonCard.java
``` java
    public PersonCard(ReadOnlyPerson person, int displayedIndex, Logic inlogic) {
        super(FXML);
        this.person = person;
        logic = inlogic;
```
###### \seedu\address\ui\PersonCard.java
``` java
        cardNum = displayedIndex;
        id.setText(cardNum + ". ");
        initTags(person);
        bindListeners(person);
    }
```
###### \seedu\address\ui\PersonCard.java
``` java
    /**
     * Provides a consistent color based on the first letter of a tag
     * ie, the same color will return for every call using 'friend' or any other tag.
     * This also matches the color scheme used to choose colors in the browser, so tags appear the same in both.
     * @param tagValue
     */

    private String mapTagToColor(String tagValue) {
        if (!colorMapping.containsKey(tagValue)) {
            int x = Character.getNumericValue(tagValue.charAt(0)) + 87; //magic number to match to javascript char->int
            colorMapping.put(tagValue, colors[x % 6]); //colors.length]);
        }
        return colorMapping.get(tagValue);
    }
```
###### \seedu\address\ui\PersonCard.java
``` java
    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     * @param person
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
```
###### \seedu\address\ui\PersonCard.java
``` java

    /**
     * handles button events given to it by the fxml doc that it is set as controller for by the constructor in UiPart
     * @param buttonEvent
     */
    @FXML
    private void handleDeleteButtonAction(ActionEvent buttonEvent) {
        try {
            CommandResult commandResult = new CommandResult("");
            String justIndex = id.getText().substring(0, id.getText().length() - 2);
            String delCommand = "delete " + justIndex;
```
###### \seedu\address\ui\PersonCard.java
``` java
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
        } catch (CommandException | ParseException e) {
            // handle command failure
            logger.info("Delete call failed on index " + id.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    /**
     * handles edit button presses, triggering a new window.
     * @param buttonEvent
     */
    @FXML
    private void handleEditButtonAction(ActionEvent buttonEvent) {
```
###### \seedu\address\ui\PersonCard.java
``` java

```
###### \seedu\address\ui\PersonCard.java
``` java
            EditWindow editWindow = new EditWindow(logic, cardNum);
            editWindow.show();
        }
        logger.info("Result: " + commandResult.feedbackToUser);
        raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
```
###### \seedu\address\ui\PersonCard.java
``` java
    }
}
```
