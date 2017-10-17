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

    public AutoComplete(Model model) {
        autoCompleteList = BASECOMMANDS;
        personsStringArray = new ArrayList<String>();
        for (ReadOnlyPerson p: model.getFilteredPersonList()) {
            personsStringArray.add(p.getName().toString());
        }
    }

    public void updateAutoCompleteList(String userInput) {
        switch (userInput) {
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
                ;
        }
    }

    private String[] getConcatPersonsArray(String command) {
        String[] newAutoCompleteList = new String[personsStringArray.size()];
        for (int i = 0; i < personsStringArray.size(); i++) {
            newAutoCompleteList[i] = command + " " + personsStringArray.get(i);
        }
        return newAutoCompleteList;
    }

    }

    public String[] getAutoCompleteList() {
        return autoCompleteList;
    }
}
