package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.QrSaveEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Selects a person to Generate QRCode identified using it's last displayed index from the address book.
 */
//@@author danielweide
public class QrSaveContactCommand extends Command {

    public static final String COMMAND_WORD = "qrsave";
    public static final String COMMAND_ALIAS = "qrs";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Select Person based on Index to generate QR Code for Contact Saving\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Generated Contact Saving Qr for Selected Person: %1$s";

    private final Index targetIndex;

    public QrSaveContactCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        int indexOfPersonInList = 0;
        indexOfPersonInList = targetIndex.getOneBased() - 1;
        EventsCenter.getInstance().post(new QrSaveEvent(lastShownList.get(indexOfPersonInList)));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QrSaveContactCommand // instanceof handles nulls
                && this.targetIndex.equals(((QrSaveContactCommand) other).targetIndex)); // state check
    }

}
