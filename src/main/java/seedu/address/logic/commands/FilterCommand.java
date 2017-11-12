package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.ListObserver;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.PersonContainsTagPredicate;

//@@author jelneo
/**
 * Filters contacts by tags in master list.
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": filters the address book by tag(s)\n"
            + "Parameters: [ TAG ]...\n"
            + "Example: " + COMMAND_WORD + " friendly tricky";
    public static final String MESSAGE_FILTER_ACKNOWLEDGEMENT = "Showing all contacts with the tag(s): %1$s\n%2$s ";

    private final List<String> tags;

    public FilterCommand(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        model.deselectPerson();
        ListObserver.updateCurrentFilteredList(new PersonContainsTagPredicate(tags));

        String allTagKeywords = tags.toString();
        return new CommandResult(String.format(MESSAGE_FILTER_ACKNOWLEDGEMENT, allTagKeywords,
                getMessageForPersonListShownSummary(ListObserver.getCurrentFilteredList().size())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && this.tags.equals(((FilterCommand) other).tags));
    }
}
