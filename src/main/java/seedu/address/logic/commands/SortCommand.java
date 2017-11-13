//@@author siri99
package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
* Sorts all persons in the address book by indicated format.
*/
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_WORD_ALIAS = "so";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the address book in selected format.\n"
            + "Example: " + COMMAND_WORD + " : default sorts in alphabhetical order \n"
            + "Example: " + COMMAND_WORD + " name : sorts in alphabhetical order \n"
            + "Example: " + COMMAND_WORD + " birthday or " + COMMAND_WORD + " b : sorts in ascending order"
            + " of birthdays \n"
            + "Example: " + COMMAND_WORD + " score : sorts in descending order of scores";

    public static final String MESSAGE_SUCCESS_NAME = "Sorted successfully by Name, listing all persons below";

    public static final String MESSAGE_SUCCESS_BIRTHDAY = "Sorted successfully by Birthdays, listing all persons below";

    //@@author Henning
    public static final String MESSAGE_SUCCESS_SCORE = "Sorted successfully by Group Scores, listing all persons below";
    //@@author Henning
    private String sortFilter;

    public SortCommand (String sortFilter) {
        //Null sort Filter refers to default sort command i.e. sort by name
        this.sortFilter = sortFilter;
    }

    @Override
    public CommandResult execute() throws CommandException {
        switch (sortFilter) {
        case "name":
        default:
            model.sortFilteredPersonListName();
            return new CommandResult(MESSAGE_SUCCESS_NAME);

        case "birthday":
        case "b":
            model.sortFilteredPersonListBirthday();
            return new CommandResult(MESSAGE_SUCCESS_BIRTHDAY);

        //author Henning
        case "score":
        case "s":
            model.sortFilteredPersonListScore();
            return new CommandResult(MESSAGE_SUCCESS_SCORE);
            //author Henning
        }

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // if not the same object, return false
        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand s = (SortCommand) other;
        return sortFilter.equals(s.sortFilter);
    }
}
//@@author siri99
