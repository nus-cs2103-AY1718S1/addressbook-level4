# Jacob
###### \java\seedu\address\ui\AddWindow.java
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
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * handles button events given to it by the fxml doc that it is set as controller for by the constructor in UiPart
     * @param buttonEvent
     */
    @FXML
    private void handleAddButtonAction(ActionEvent buttonEvent) {
```
###### \java\seedu\address\ui\CommandBox.java
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
###### \java\seedu\address\ui\EditWindow.java
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
###### \java\seedu\address\ui\PersonCard.java
``` java
    public PersonCard(ReadOnlyPerson person, int displayedIndex, Logic inlogic) {
        super(FXML);
        this.person = person;
        logic = inlogic;
```
###### \java\seedu\address\ui\PersonCard.java
``` java
        cardNum = displayedIndex;
        id.setText(cardNum + ". ");
        initTags(person);
        bindListeners(person);
    }

    /**
     * Provides a consistent color based on the string of a tag's value
     * ie, the same color will return for every call using 'friend' or any other tag.
     */

    private String mapTagToColor(String tagValue) {
        if (!colorMapping.containsKey(tagValue)) {
            colorMapping.put(tagValue, colors[tagValue.length() % colors.length]);
        }
        return colorMapping.get(tagValue);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
```
###### \java\seedu\address\ui\PersonCard.java
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
###### \java\seedu\address\ui\PersonCard.java
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
###### \java\seedu\address\ui\PersonCard.java
``` java
            EditWindow editWindow = new EditWindow(logic, cardNum);
            editWindow.show();
        }
        logger.info("Result: " + commandResult.feedbackToUser);
        raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
```
###### \resources\view\AddWindow.fxml
``` fxml

<?import javafx.scene.control.TextField?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>

<StackPane fx:id="helpWindowRoot" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
  <VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <TextField fx:id="nameField" prefWidth="200.0" promptText="Name"/>
    <TextField fx:id="tagsField" prefWidth="200.0" promptText="Optional Tags"/>
    <TextField fx:id="phoneField" prefWidth="200.0" promptText="Phone"/>
    <TextField fx:id="birthdayField" prefWidth="200.0" promptText="Optional Birthday"/>
    <TextField fx:id="addressField" prefWidth="200.0" promptText="Address"/>
    <TextField fx:id="emailField" prefWidth="200.0" promptText="Email"/>
    <TextField fx:id="scoreField" prefWidth="200.0" promptText="Optional Group Score"/>
    <Button onAction='#handleAddSubmitAction' id="add" prefWidth="120.0" minWidth="90.0" text="Add"/>
  </VBox>
</StackPane>
```
###### \resources\view\CommandBox.fxml
``` fxml
  <Button onAction='#handleAddButtonAction' id="add" prefWidth="120.0" minWidth="90.0" text="Add"/>
  <Button onAction='#handleUndoButtonAction' id="undo" prefWidth="120.0" minWidth="90.0" text="Undo"/>
  <Button onAction='#handleRedoButtonAction' id="redo" prefWidth="120.0" minWidth="90.0" text="Redo"/>
```
###### \resources\view\EditWindow.fxml
``` fxml

<?import javafx.scene.control.TextField?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>

<StackPane fx:id="helpWindowRoot" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
  <VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <TextField fx:id="nameField" prefWidth="200.0" promptText="Name"/>
    <TextField fx:id="tagsField" prefWidth="200.0" promptText="Tag(s)"/>
    <TextField fx:id="phoneField" prefWidth="200.0" promptText="Phone"/>
    <TextField fx:id="birthdayField" prefWidth="200.0" promptText="Birthday"/>
    <TextField fx:id="addressField" prefWidth="200.0" promptText="Address"/>
    <TextField fx:id="emailField" prefWidth="200.0" promptText="Email"/>
    <TextField fx:id="scoreField" prefWidth="200.0" promptText="Group Score"/>
    <Button onAction='#handleEditSubmitAction' id="edit" prefWidth="120.0" minWidth="90.0" text="Edit"/>
  </VBox>
</StackPane>
```
###### \resources\view\PersonListCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.control.Button?>

<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
  <GridPane HBox.hgrow="ALWAYS">
    <columnConstraints>
      <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
    </columnConstraints>
    <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
      <padding>
        <Insets top="5" right="5" bottom="5" left="15" />
      </padding>
      <HBox spacing="5" alignment="CENTER_LEFT">
        <Label fx:id="id" styleClass="cell_big_label">
          <minWidth>
            <!-- Ensures that the label text is never truncated -->
            <Region fx:constant="USE_PREF_SIZE" />
          </minWidth>
        </Label>
        <Label fx:id="name" text="\$first" styleClass="cell_big_label" />
        <Region prefHeight="10.0" prefWidth="1.0" HBox.hgrow="ALWAYS" />
        <Button onAction='#handleDeleteButtonAction' fx:id="delete" text="X"/>
      </HBox>
      <FlowPane fx:id="tags" />
      <Label fx:id="phone" styleClass="cell_small_label" text="\$phone" />
```
