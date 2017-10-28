package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowPersonListViewEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose specified field contain "
            + "any of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [PREFIX KEYWORD]... (Keywords must not contain whitespaces)\n"
            + "PREFIX: \n"
            + "Name - " + PREFIX_NAME + "\n"
            + "Phone - " + PREFIX_PHONE + "\n"
            + "Address - " + PREFIX_ADDRESS + "\n"
            + "Email - " + PREFIX_EMAIL + "\n"
            + "Tag - " + PREFIX_TAG + "\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "alice " + PREFIX_TAG + "friends "
            + PREFIX_ADDRESS + "Serangoon";

    private final Predicate<ReadOnlyPerson> predicate;

    public FindCommand(Predicate<ReadOnlyPerson> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowPersonListViewEvent());
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
