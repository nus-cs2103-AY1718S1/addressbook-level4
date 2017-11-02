package seedu.room.logic;

import java.util.ArrayList;

import seedu.room.model.Model;
import seedu.room.model.person.ReadOnlyPerson;


/**
 * AutoComplete class integrated into LogicManager to keep track of current set
 * of autocomplete suggestions
 */
public class AutoComplete {

    private final String[] baseCommands = { "add", "edit", "select", "delete", "clear",
        "backup", "find", "list", "history", "exit", "help", "undo", "redo"
    };
    private ArrayList<String> personsStringArray;
    private String[] autoCompleteList;
    private Model model;

    public AutoComplete(Model model) {
        this.model = model;
        autoCompleteList = baseCommands;
        personsStringArray = new ArrayList<String>();
        this.updatePersonsArray();
    }

    /**
     * Updates AutoComplete suggestions according to user typed input
     * @param userInput
     */
    public void updateAutoCompleteList(String userInput) {
        switch (userInput) {
        case "":
            this.resetAutocompleteList();
            break;
        case "find":
            this.autoCompleteList = getConcatPersonsArray("find");
            break;
        case "edit":
            this.autoCompleteList = getConcatPersonsArray("edit");
            break;
        case "delete":
            this.autoCompleteList = getConcatPersonsArray("delete");
            break;
        default:
            return;
        }
    }

    // Concatenate Persons to suggestions when command typed
    private String[] getConcatPersonsArray(String command) {
        String[] newAutoCompleteList = new String[personsStringArray.size()];
        for (int i = 0; i < personsStringArray.size(); i++) {
            if (command.equals("find")) {
                newAutoCompleteList[i] = command + " " + personsStringArray.get(i);
            } else {
                newAutoCompleteList[i] = command + " " + (i + 1);
            }
        }
        return newAutoCompleteList;
    }

    /**
     * Reset autocomplete suggestions to base commands
     */
    public void resetAutocompleteList() {
        this.autoCompleteList = baseCommands;
    }

    /**
     * Update array of persons suggestions when list modified
     */
    public void updatePersonsArray() {
        personsStringArray.clear();
        for (ReadOnlyPerson p: model.getFilteredPersonList()) {
            personsStringArray.add(p.getName().toString());
        }
    }

    /**
     * Getter for auto-complete list suggestions
     */
    // Update array of persons suggestions when list modified
    public String[] getAutoCompleteList() {
        return autoCompleteList;
    }
}
