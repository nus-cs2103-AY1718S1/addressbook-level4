package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.EventContainsKeywordPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Selects a person identified using it's last displayed index from the address book
 * and displays all events the person has joined.
 */
public class SelectJoinedEventsCommand extends Command {

    public static final String COMMAND_WORD = "selectE";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the person identified by the index number used in the last person listing "
            + "and displays all events the person has joined.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final List<Index> indexList;

    public SelectJoinedEventsCommand(List<Index> indexList) {
        this.indexList = indexList;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        String temp = "";

        for(Index targetIndex: indexList) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            temp += (lastShownList.get(targetIndex.getZeroBased()).getName().fullName) + " ";
        }

        String[] nameKeywords = (temp.trim()).split("\\s+");

        EventContainsKeywordPredicate predicate = new EventContainsKeywordPredicate(Arrays.asList(nameKeywords));
        model.updateFilteredEventList(predicate);
        return new CommandResult(getMessageForEventListShownSummary(model.getFilteredEventList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectJoinedEventsCommand // instanceof handles nulls
                && this.indexList.equals(((SelectJoinedEventsCommand) other).indexList)); // state check
    }
}
