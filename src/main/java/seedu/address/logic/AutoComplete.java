package seedu.address.logic;

import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;

import java.util.List;

public class AutoComplete {

    private final String[] BASECOMMANDS = { "add", "edit", "select", "delete", "clear",
        "backup", "find", "list", "history", "exit", "help", "undo", "redo"
    };
    private String[] autoCompleteList;
    public List<ReadOnlyPerson> lastShownList;

    public AutoComplete(Model model) {
        autoCompleteList = BASECOMMANDS;
        lastShownList = model.getFilteredPersonList();
    }

    public void updateAutoCompleteList(String userInput) {
    }

    public String[] getAutoCompleteList() {
        return autoCompleteList;
    }
}
