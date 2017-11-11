package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.EventContainsKeywordPredicate;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author leonchowwenhao
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

    public static final String MESSAGE_SELECT_PERSON_JOINED_EVENTS = "For : %1$s";

    private final List<Index> indexList;

    public SelectJoinedEventsCommand(List<Index> indexList) {
        this.indexList = indexList;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        StringBuilder eventNames = new StringBuilder();
        StringBuilder personNames = new StringBuilder();

        for (Index targetIndex: indexList) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personNames.append(lastShownList.get(targetIndex.getZeroBased()).getName()).append(", ");
            for (ReadOnlyEvent event: lastShownList.get(targetIndex.getZeroBased()).getParticipation()) {
                eventNames.append(event.getEventName()).append("[-]");
            }
        }

        String[] eventNameKeywords = (eventNames.toString().trim()).split("\\[-]+");
        EventContainsKeywordPredicate predicate = new EventContainsKeywordPredicate(Arrays.asList(eventNameKeywords));
        model.updateFilteredEventList(predicate);

        personNames.append(getMessageForEventListShownSummary(model.getFilteredEventList().size()));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_JOINED_EVENTS, personNames));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectJoinedEventsCommand // instanceof handles nulls
                && this.indexList.equals(((SelectJoinedEventsCommand) other).indexList)); // state check
    }
}
