package seedu.address.logic.commands;
import seedu.address.model.*;
import seedu.address.model.person.NameContainsPhonePredicate;

import static seedu.address.logic.commands.Command.getMessageForPersonListShownSummary;


/**
 * Created by nguyenminhquang on 10/10/17.
 */
public class FindPhoneCommand extends Command {
    public static final String COMMAND_WORD = "phone";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the persons whose phone numbers "
            + "appear in the list of specified numbers and displays those persons as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " 12345678";

    private final NameContainsPhonePredicate predicate;

    public FindPhoneCommand(NameContainsPhonePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindPhoneCommand) other).predicate)); // state check
    }
}
