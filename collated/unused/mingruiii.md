# mingruiii
###### /ClearCommand.java
``` java
    private ConfirmationDialog clearConfirmationDialog;
```
###### /ClearCommand.java
``` java
        Platform.runLater(() -> {
            clearConfirmationDialog = new ConfirmationDialog(ClearCommand.COMMAND_WORD,
                    ClearCommand.CONFIRMATION_MESSAGE);
            if (clearConfirmationDialog.goAhead()) {
                model.resetData(new Rolodex());
                EventsCenter.getInstance().post(new ClearPersonDetailPanelRequestEvent());
            }
        });
```
###### /ClearCommandSystemTest.java
``` java
    /**
     * Performs the same verification as {@code assertCommandSuccess(String, String, Model)} except that enter key is
     * pressed to select OK on the confirmation alert
     */
    private void assertCommandSuccessAfterPressingEnter(String command, String expectedResultMessage,
                                                        Model expectedModel) {
        executeCommand(command);
        guiRobot.push(KeyCode.ENTER);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }
```
###### /ConfirmationDialog.java
``` java
package seedu.address.logic;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Creates a confirmation dialog, to be used before executing delete, clear and exit commands.
 */
public class ConfirmationDialog {
    private Alert alert;
    private Optional<ButtonType> clickedButton;

    public ConfirmationDialog(String headerText, String contentText) {
        alert = new Alert(Alert.AlertType.CONFIRMATION, contentText, ButtonType.YES, ButtonType.CANCEL);
        alert.getDialogPane().getStylesheets().add("view/LightTheme.css");
        alert.setTitle("Rolodex");
        alert.setHeaderText(headerText);
    }

    /**
     * show the alert message and execute corresponding results when user clicks yes or cancel or close the dialog
     */
    public boolean goAhead() {
        clickedButton = alert.showAndWait();
        return clickedButton.isPresent() && clickedButton.get() == ButtonType.YES;
    }
}
```
###### /DeleteCommand.java
``` java
    private ConfirmationDialog deleteConfirmationDialog;
```
###### /DeleteCommand.java
``` java
        Platform.runLater(() -> {
            deleteConfirmationDialog = new ConfirmationDialog(COMMAND_WORD, CONFIRMATION_MESSAGE);
            if (deleteConfirmationDialog.goAhead()) {
                try {
                    model.deletePerson(personToDelete);
                } catch (PersonNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
```
###### /DeleteCommandSystemTest.java
``` java
    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)}, except that enter
     * key is pressed to select OK on the confirmation alert
     */
    private void assertCommandSuccessAfterPressingEnter(String command, Model expectedModel,
                                                        String expectedResultMessage, Index expectedSelectedCardIndex) {
        executeCommand(command);
        guiRobot.push(KeyCode.ENTER);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }
```
###### /ExitCommand.java
``` java
    private ConfirmationDialog exitConfirmationDialog;
```
###### /ExitCommand.java
``` java
        Platform.runLater(() -> {
            exitConfirmationDialog = new ConfirmationDialog(COMMAND_WORD, CONFIRMATION_MESSAGE);
            if (exitConfirmationDialog.goAhead()) {
                EventsCenter.getInstance().post(new ExitAppRequestEvent());
            }
        });
```
###### /UiManager.java
``` java
        primaryStage.setOnCloseRequest(event -> {
            //raise(new ExitAppRequestEvent());
            Optional<ButtonType> clickedButton = showAlertDialogAndGetResult(Alert.AlertType.CONFIRMATION,
                    "Rolodex", ExitCommand.COMMAND_WORD, ExitCommand.CONFIRMATION_MESSAGE);
            if (clickedButton.get() == ButtonType.OK) {
                raise(new ExitAppRequestEvent());
            } else {
                event.consume();
            }
        });
```
###### /UiManager.java
``` java
    @Override
    public Optional<ButtonType> showAlertDialogAndGetResult(AlertType type, String title, String headerText,
                                                             String contentText) {
        return showAlertDialog(mainWindow.getPrimaryStage(), type, title, headerText, contentText).showAndWait();
    }
```
