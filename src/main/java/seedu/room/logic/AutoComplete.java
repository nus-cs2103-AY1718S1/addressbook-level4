package seedu.room.logic;

import java.util.ArrayList;

import seedu.room.model.Model;
import seedu.room.model.person.ReadOnlyPerson;

//@@author shitian007
/**
 * AutoComplete class integrated into LogicManager to keep track of current set
 * of autocomplete suggestions
 */
public class AutoComplete {

    public static final String[] BASE_COMMANDS = { "add", "addEvent", "addImage", "backup", "edit", "select", "delete",
        "deleteByTag", "deleteEvent", "deleteImage", "deleteTag", "clear", "find", "list", "highlight", "history",
        "import", "exit", "help", "undo", "redo", "sort", "swaproom"
    };
    private ArrayList<String> personsStringArray;
    private String[] autoCompleteList;
    private Model model;

    public AutoComplete(Model model) {
        this.model = model;
        autoCompleteList = BASE_COMMANDS;
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
            this.autoCompleteList = getConcatResidentsArray("find");
            break;
        case "edit":
            this.autoCompleteList = getConcatResidentsArray("edit");
            break;
        case "delete":
            this.autoCompleteList = getConcatResidentsArray("delete");
            break;
        case "select":
            this.autoCompleteList = getConcatResidentsArray("select");
            break;
        case "addImage":
            this.autoCompleteList = getConcatResidentsArray("addImage");
            break;
        case "deleteImage":
            this.autoCompleteList = getConcatResidentsArray("deleteImage");
            break;
        default:
            return;
        }
    }

    // Concatenate Persons to suggestions when command typed
    private String[] getConcatResidentsArray(String command) {
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
        this.autoCompleteList = BASE_COMMANDS;
    }

    /**
     * Update array of persons suggestions when list modified
     */
    public void updatePersonsArray() {
        personsStringArray.clear();
        for (ReadOnlyPerson resident: model.getFilteredPersonList()) {
            personsStringArray.add(resident.getName().toString());
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
