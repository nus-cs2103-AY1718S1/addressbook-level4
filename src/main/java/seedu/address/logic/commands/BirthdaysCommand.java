package seedu.address.logic.commands;

import seedu.address.model.person.CheckIfBirthday;

/**
 * Lists all persons with a birthday today
 */
public class BirthdaysCommand extends Command {
    public static final String COMMAND_WORD = "birthdays";
    public static final String COMMAND_ALIAS = "bd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays dialog box with names of contacts "
            + "who have their birthdays today.\n"
            + "Parameters: KEYWORD \n"
            + "Example: " + COMMAND_WORD;

    private CheckIfBirthday check = new CheckIfBirthday();

    public BirthdaysCommand (){}

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(check);
        return new CommandResult(getBirthdayMessageSummary(model.getFilteredPersonList().size()));
    }

}
