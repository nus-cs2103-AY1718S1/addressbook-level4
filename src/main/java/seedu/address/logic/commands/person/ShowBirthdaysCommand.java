package seedu.address.logic.commands.person;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.person.CheckBirthdays;

//@@author hymss
/**
 * Lists all persons in Bluebird whose birthday is on the current day with respect to the user.
 */

public class ShowBirthdaysCommand extends Command {

    public static final String COMMAND_WORD = "showbirthdays";

    public static final String MESSAGE_SUCCESS = "Chirp! Here are the birthdays for today.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all persons whose birthdays are today.\n"
            + "Parameters: KEYWORD\n"
            + "Example for showing birthdays: " + COMMAND_WORD;

    private CheckBirthdays checker = new CheckBirthdays();

    public ShowBirthdaysCommand() {

    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(checker);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
