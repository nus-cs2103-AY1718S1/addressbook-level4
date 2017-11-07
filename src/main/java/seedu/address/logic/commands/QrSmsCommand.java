package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.QrSmsEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Selects a person to Generate QRCode identified using it's last displayed index from the address book.
 */
//@@author danielweide
public class QrSmsCommand extends Command {

    public static final String COMMAND_WORD = "qrsms";
    public static final String COMMAND_ALIAS = "qs";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Select Person based on Index to generate QR Code for SMS\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Generated SMS Qr for Selected Person: %1$s";

    private final Index targetIndex;

    public QrSmsCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        int indexOfPersonInList;
        indexOfPersonInList = targetIndex.getOneBased() - 1;
        EventsCenter.getInstance().post(new QrSmsEvent(lastShownList.get(indexOfPersonInList)));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QrSmsCommand // instanceof handles nulls
                && this.targetIndex.equals(((QrSmsCommand) other).targetIndex)); // state check
    }

}
