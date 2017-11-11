package seedu.address.logic.commands.person;

import seedu.address.model.person.CheckBirthdays;

/**
 * Lists all persons in Bluebird whose birthday is on the current day with respect to the user.
 */

public class ShowBirthdaysCommand {

    public static final String COMMAND_WORD = "showbirthdays";

    public static final String MESSAGE_SUCCESS = "List all persons in Bluebird whose birthdays are today.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all the persons whose birthdays are the today.\n"
            + "Parameters: KEYWORD\n"
            + "Example for showing birthdays: " + COMMAND_WORD;

    private CheckBirthdays checker = new CheckBirthdays();

    public ShowBirthdaysCommand(){

    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(checker);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
