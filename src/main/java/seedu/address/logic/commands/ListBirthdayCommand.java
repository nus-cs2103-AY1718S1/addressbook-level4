package seedu.address.logic.commands;

import seedu.address.model.person.BirthdayChecker;

/**
 * Lists all persons in Contags whose birthday is the current day to the user.
 */
public class ListBirthdayCommand extends Command {

    public static final String COMMAND_WORD = "listbirthday";

    public static final String MESSAGE_SUCCESS = "Listed all contacts whose birthday is today.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all the persons whose birthday is the current day.\n"
            + "Parameters: KEYWORD\n"
            + "Example for birthday list: " + COMMAND_WORD;

    private BirthdayChecker checker = new BirthdayChecker();

    public ListBirthdayCommand (){

    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(checker);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
