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
public class DisplayCommand extends Command {

    public static final String COMMAND_WORD = "display";
    public static final String PARTICULAR_EMAIL = "email";
    public static final String PARTICULAR_PHONE = "phone";
    public static final String PARTICULAR_ADDRESS = "address";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the event identified by the index number used in the last event listing "
            + "and displays the target particular of every person that has joined.\n"
            + "Parameters: INDEX (must be a positive integer) PARTICULAR (either email, phone, or address)"
            + "Example: " + COMMAND_WORD + " 1 " + PARTICULAR_EMAIL;
    public static final String MESSAGE_NO_PARTICIPANT = "No one has joined this event.";
    public static final String MESSAGE_INVALID_PARTICULAR = "%1$s is not a valid particular. Please type \""
        + PARTICULAR_EMAIL + "\", \"" + PARTICULAR_PHONE + "\", or \"" + PARTICULAR_ADDRESS + "\" instead.";

    private final Index targetIndex;
    private final String particular;

    public DisplayCommand(Index targetIndex, String particular) {
        this.targetIndex = targetIndex;
        this.particular = particular;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyEvent> lastShownList = model.getFilteredEventList();
        String result = "Details: ";
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        if (!lastShownList.get(targetIndex.getZeroBased()).getParticipants().isEmpty()) {
            switch (particular) {

            case PARTICULAR_EMAIL:
                for (Person person : lastShownList.get(targetIndex.getZeroBased()).getParticipants()) {
                    result += person.getEmail().value + "\n\t     ";
                }
                return new CommandResult(result.trim());

            case PARTICULAR_PHONE:
                for (Person person : lastShownList.get(targetIndex.getZeroBased()).getParticipants()) {
                    result += person.getPhone().value + "\n\t     ";
                }
                return new CommandResult(result.trim());

            case PARTICULAR_ADDRESS:
                for (Person person : lastShownList.get(targetIndex.getZeroBased()).getParticipants()) {
                    result += person.getAddress().value + "\n\t     ";
                }
                return new CommandResult(result.trim());

            default:
                return new CommandResult(String.format(MESSAGE_INVALID_PARTICULAR, particular));
            }

        } else {
            return new CommandResult(MESSAGE_NO_PARTICIPANT);
        }


    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplayCommand // instanceof handles nulls
                && this.targetIndex.equals(((DisplayCommand) other).targetIndex)); // state check
    }
}
