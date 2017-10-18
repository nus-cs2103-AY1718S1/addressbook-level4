package seedu.address.logic;

import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;

import java.util.ArrayList;

public class AutoComplete {

    private final String[] BASECOMMANDS = { "add", "edit", "select", "delete", "clear",
        "backup", "find", "list", "history", "exit", "help", "undo", "redo"
    };
    private ArrayList<String> personsStringArray;
    private String[] autoCompleteList;
    Model model;

    public AutoComplete(Model model) {
        this.model = model;
        autoCompleteList = BASECOMMANDS;
        personsStringArray = new ArrayList<String>();
        this.updatePersonsArray();
    }

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
        }
    }

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

    public void resetAutocompleteList() {
        this.autoCompleteList = BASECOMMANDS;
    }

    public void updatePersonsArray() {
        personsStringArray.clear();
        for (ReadOnlyPerson p: model.getFilteredPersonList()) {
            personsStringArray.add(p.getName().toString());
        }
    }

    public String[] getAutoCompleteList() {
        return autoCompleteList;
    }
}
