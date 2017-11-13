package seedu.room.logic;

import java.util.ArrayList;

import seedu.room.model.Model;
import seedu.room.model.person.ReadOnlyPerson;

//@@author shitian007
/**
 * AutoComplete class integrated into {@code Logic} to keep track of current set
 * of autocomplete suggestions according to user input
 */
public class AutoComplete {

    public static final String[] BASE_COMMANDS = { "add", "addEvent", "addImage", "backup", "edit", "select", "delete",
        "deleteByTag", "deleteEvent", "deleteImage", "deleteTag", "clear", "find", "list", "highlight", "history",
        "import", "exit", "help", "undo", "redo", "sort", "swaproom", "switch", "prev", "next"
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
     * @param userInput determines suggestions
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

    /**
     * @param command typed in by the user
     * @return String array of suggestions with the index/name of the list of the displayed residents appended
     */
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
     * Reset {@code autoCompleteList} list to base commands
     */
    public void resetAutocompleteList() {
        this.autoCompleteList = BASE_COMMANDS;
    }

    /**
     * Update {@code personStringArray} when list in {@code Model} model modified
     */
    public void updatePersonsArray() {
        personsStringArray.clear();
        for (ReadOnlyPerson resident: model.getFilteredPersonList()) {
            personsStringArray.add(resident.getName().toString());
        }
    }

    /**
     * @return Last updated array of suggestions
     */
    public String[] getAutoCompleteList() {
        return autoCompleteList;
    }
}
