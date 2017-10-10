package seedu.address.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.control.TextArea;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;

public class CommandBoxHelper extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBoxHelper.fxml";

    private StringProperty helperText = new SimpleStringProperty("");

    private Character firstChar;
    private String commandString;

    @FXML
    private TextArea commandBoxHelper;


    public CommandBoxHelper(){
        super(FXML);
        commandBoxHelper.textProperty().bind(helperText);
        commandBoxHelper.setStyle("-fx-text-fill: lime");
    }

    /**
     * Command to display text within the CommandBoxHelper given user input in the CLI.
     */
    public void listHelp(TextField commandText){

        helperText.setValue("");
        try {
            commandString = commandText.getText();
            firstChar = commandText.getText().charAt(0);
        } catch (Exception e) {

        }

        //TO-DO create a short form and long form for commands
        //TO-DO fill in all other commands
        //TO-DO fix for words of the same letter
        switch (firstChar) {
            case 'a':
                if (checkSubset(AddCommand.COMMAND_WORD, commandString)) {
                    helperText.setValue(AddCommand.COMMAND_WORD + "\n");
                } else {
                    helperText.setValue("");
                }
                break;
            case 'c':
                if (checkSubset(ClearCommand.COMMAND_WORD, commandString)) {
                    helperText.setValue(ClearCommand.COMMAND_WORD + "\n");
                } else {
                    helperText.setValue("");
                }
                break;
            case 'd':
                if (checkSubset(DeleteCommand.COMMAND_WORD, commandString)) {
                    helperText.setValue(DeleteCommand.COMMAND_WORD + "\n");
                } else {
                    helperText.setValue("");
                }
                break;
            case 'e':
                if (checkSubset(EditCommand.COMMAND_WORD, commandString)) {
                    helperText.setValue(helperText.getValue() + EditCommand.COMMAND_WORD + "\n");
                }
                if (checkSubset(ExitCommand.COMMAND_WORD, commandString)) {
                    helperText.setValue(helperText.getValue() + ExitCommand.COMMAND_WORD + "\n");
                }
                if (!checkSubset(ExitCommand.COMMAND_WORD, commandString) &&
                        !checkSubset(EditCommand.COMMAND_WORD, commandString)) {
                    helperText.setValue("");
                }
                break;
            case 'f':
                if (checkSubset(FindCommand.COMMAND_WORD, commandString)) {
                    helperText.setValue(FindCommand.COMMAND_WORD + "\n");
                } else {
                    helperText.setValue("");
                }
                break;
            case 'h':
                if (checkSubset(HelpCommand.COMMAND_WORD, commandString)) {
                    helperText.setValue(helperText.getValue() + HelpCommand.COMMAND_WORD + "\n");
                }
                if (checkSubset(HistoryCommand.COMMAND_WORD, commandString)) {
                    helperText.setValue(helperText.getValue() + HistoryCommand.COMMAND_WORD + "\n");
                }
                if (!checkSubset(HelpCommand.COMMAND_WORD, commandString) &&
                        !checkSubset(HistoryCommand.COMMAND_WORD, commandString)) {
                    helperText.setValue("");
                }
                break;
            case 'l':
                if (checkSubset(ListCommand.COMMAND_WORD, commandString)) {
                    helperText.setValue(ListCommand.COMMAND_WORD + "\n");
                } else {
                    helperText.setValue("");
                }
                break;
            case 'r':
                if (checkSubset(RedoCommand.COMMAND_WORD, commandString)) {
                    helperText.setValue(RedoCommand.COMMAND_WORD + "\n");
                } else {
                    helperText.setValue("");
                }
                break;
            case 's':
                if (checkSubset(SelectCommand.COMMAND_WORD, commandString)) {
                    helperText.setValue(SelectCommand.COMMAND_WORD + "\n");
                } else {
                    helperText.setValue(SelectCommand.COMMAND_WORD);
                }
                break;
            case 'u':
                if (checkSubset(UndoCommand.COMMAND_WORD, commandString)) {
                    helperText.setValue(UndoCommand.COMMAND_WORD + "\n");
                } else {
                    helperText.setValue("");
                }
                break;
            default:
                helperText.setValue("No commands matching your words");
                break;
        }
    }

    /**
     * Checks if commandFieldString is a subset of commandWord and if their first letter matches
     */
    private boolean checkSubset(String commandWord , String commandFieldString){
        try {
            if (commandWord.contains(commandFieldString)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) { return false; }
    }
}
