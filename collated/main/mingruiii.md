# mingruiii
###### /java/seedu/address/logic/commands/ClearCommand.java
``` java
    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        Platform.runLater(() -> {
            clearConfirmationDialog = new ConfirmationDialog(ClearCommand.COMMAND_WORD,
                    ClearCommand.CONFIRMATION_MESSAGE);
            if (clearConfirmationDialog.goAhead()) {
                model.resetData(new Rolodex());
                EventsCenter.getInstance().post(new ClearPersonDetailPanelRequestEvent());
            }
        });
        return new CommandResult(MESSAGE_SUCCESS);

    }
}
```
###### /java/seedu/address/logic/commands/DeleteCommand.java
``` java
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getLatestPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToDelete = lastShownList.get(targetIndex.getZeroBased());

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
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/ExitCommand.java
``` java
    @Override
    public CommandResult execute() {
        Platform.runLater(() -> {
            exitConfirmationDialog = new ConfirmationDialog(COMMAND_WORD, CONFIRMATION_MESSAGE);
            if (exitConfirmationDialog.goAhead()) {
                EventsCenter.getInstance().post(new ExitAppRequestEvent());
            }
        });
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
```
###### /java/seedu/address/logic/ConfirmationDialog.java
``` java
package seedu.address.logic;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * create a confirmation dialog, to be used for delete, clear and exit commands
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
###### /java/seedu/address/ui/CommandBox.java
``` java
    public boolean isFocused() {
        return commandTextField.isFocused();
    }

```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * press control key
     */
    public void pressCtrl() {
        pause = new PauseTransition();
        pause.setDuration(Duration.millis(MILLISECONDS_PAUSE_BEFORE_PRESSING_CTRL));
        pause.setOnFinished(event -> {
            robot.push(KeyCode.CONTROL);
        });
        pause.play();
    }

```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * process input as user is typing
     */
    public void processInput() {
        input = commandTextField.getText();
        updateKeyboardIconAndStyle();
        autoSelectFirstField();
        if (Arrays.asList(AddCommand.LIST_OF_FIELDS).contains(selectedText)) {
            updateSelection();
        }
    }

    /**
     * if the command is add, and the next field is selected from pressing tab key, update the field selection
     */
    private void updateSelection() {
        commandTextField.selectRange(anchorPosition, anchorPosition + selectedText.length());
        selectedText = "";
    }

```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * if the input matches the command format, automatically selects the first field that the user need to key in
     */
    private void autoSelectFirstField() {
        setFocus();
        switch (input) {
        case AddCommand.FORMAT:
            commandTextField.selectRange(START_OF_FIRST_FIELD, END_OF_FIRST_FIELD);
            break;
        case EditCommand.FORMAT:
        case FindCommand.FORMAT:
        case SelectCommand.FORMAT:
        case DeleteCommand.FORMAT:
            int indexOfFirstSpace = input.indexOf(" ");
            commandTextField.selectRange(indexOfFirstSpace + 1, input.length());
            break;
        default:
        }
    }

    private boolean isAutoCompleteCommand(String command) {
        return setOfAutoCompleteCommands.contains(command);
    }

    private boolean isAddCommandFormat(String input) {
        return input.startsWith("add")
                && input.contains("n/") && input.contains("p/") && input.contains("e/") && input.contains("a/");
    }

    private void changeSelectionToNextField() {
        commandTextField.selectNextWord();
        anchorPosition = commandTextField.getAnchor();
        selectedText = commandTextField.getSelectedText().trim();
    }

    /**
     * if the current input is a valid command, auto complete the full format
     * in the case of add command, if the user is trying to navigate to the next field, auto select the next field
     */
    private void autocomplete() {
        input = commandTextField.getText().trim().toLowerCase();
        if (isAutoCompleteCommand(input)) {
            displayFullFormat(input);
        } else if (isAddCommandFormat(input)) {
            int positionOfNameField = input.indexOf("n/");
            int positionOfPhoneField = input.indexOf("p/");
            int positionOfEmailField = input.indexOf("e/");
            int positionOfAddressField = input.indexOf("a/");
            int currentPosition = commandTextField.getCaretPosition();
            if (currentPosition > positionOfNameField && currentPosition < positionOfPhoneField) {
                commandTextField.positionCaret(positionOfPhoneField + 2);
                changeSelectionToNextField();
            } else if (currentPosition > positionOfPhoneField && currentPosition < positionOfEmailField) {
                commandTextField.positionCaret(positionOfEmailField + 2);
                changeSelectionToNextField();
            } else if (currentPosition > positionOfEmailField && currentPosition < positionOfAddressField) {
                commandTextField.positionCaret(positionOfAddressField + 2);
                changeSelectionToNextField();
            }
        }
    }

    /**
     * if the command input is a valid command that requires additional field(s), display the full
     * format in the textfield
     * @param command input by the user
     */
    private void displayFullFormat(String command) {
        switch (command) {
        case "add":
        case "a":
            replaceText(AddCommand.FORMAT);
            break;
        case "edit":
        case "e":
            replaceText(EditCommand.FORMAT);
            break;
        case "find":
        case "f":
        case "search":
            replaceText(FindCommand.FORMAT);
            break;
        case "select":
        case "s":
            replaceText(SelectCommand.FORMAT);
            break;
        case "delete":
        case "d":
            replaceText(DeleteCommand.FORMAT);
            break;
        default:
        }
    }

```
###### /java/seedu/address/ui/KeyListener.java
``` java
    /**
     * display the full command format for commands that require multiple fields
     */
    private void displayCommandFormat(String command) {
        commandBox.replaceText(command);
        commandBox.pressCtrl();
    }

```
###### /java/seedu/address/ui/MainWindow.java
``` java
    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        primaryStage.fireEvent(new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

```
###### /java/seedu/address/ui/Ui.java
``` java
    Optional<ButtonType> showAlertDialogAndGetResult(Alert.AlertType type, String title, String headerText,
                                                     String contentText);

}
```
###### /java/seedu/address/ui/UiManager.java
``` java
    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");
        primaryStage.setTitle(config.getAppTitle());

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = new MainWindow(primaryStage, config, prefs, logic);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();
            mainWindow.setKeyListeners();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }

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
    }

```
###### /java/seedu/address/ui/UiManager.java
``` java
    @Override
    public Optional<ButtonType> showAlertDialogAndGetResult(AlertType type, String title, String headerText,
                                                             String contentText) {
        return showAlertDialog(mainWindow.getPrimaryStage(), type, title, headerText, contentText).showAndWait();
    }

```
