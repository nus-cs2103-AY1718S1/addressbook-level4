package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;

//@@author kengying
/**
 * Lists all persons/groups/tags in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";
    public static final String SEARCH_TERM = "all";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + SEARCH_TERM + " : List all persons\n"
            + "Example: " + COMMAND_WORD +  " " + SEARCH_TERM + "\n"
            + ListTagCommand.MESSAGE_USAGE + "\n"
            + ListGroupCommand.MESSAGE_USAGE;

    private Predicate<ReadOnlyPerson> predicate = PREDICATE_SHOW_ALL_PERSONS;

    public ListCommand() {
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && this.predicate.equals(((ListCommand) other).predicate)); // state check
    }
}
