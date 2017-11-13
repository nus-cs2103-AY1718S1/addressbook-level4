//@@author Jacob Vosburgh
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
//@@author
