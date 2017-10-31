package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.person.Person;

//@@author leonchowwenhao
/**
 * Selects an event identified using it's last displayed index from the address book
 * and displays the emails of every person that has joined.
 */
public class DisplayEmailsCommand extends Command {

    public static final String COMMAND_WORD = "displayEmails";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the event identified by the index number used in the last event listing "
            + "and displays the emails of every person that has joined.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;

    public DisplayEmailsCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyEvent> lastShownList = model.getFilteredEventList();
        String temp = "";
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        if (!lastShownList.get(targetIndex.getZeroBased()).getParticipants().isEmpty()) {
            for (Person person : lastShownList.get(targetIndex.getZeroBased()).getParticipants()) {
                temp += person.getEmail().value + ", ";
            }
        } else {
            temp = "No one has joined this event.";
        }

        return new CommandResult(temp.trim());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplayEmailsCommand // instanceof handles nulls
                && this.targetIndex.equals(((DisplayEmailsCommand) other).targetIndex)); // state check
    }
}
