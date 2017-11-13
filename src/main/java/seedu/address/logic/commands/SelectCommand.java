package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";
    public static final String COMMAND_ALIAS = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the person identified by the index number used in the last person listing.\n"
            + "Alias: " + COMMAND_ALIAS + "\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Person: %1$s";

    private final Index targetIndex;
    private String socialType = null;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    //@@author sarahnzx
    public SelectCommand(Index targetIndex, String socialType) {
        this.targetIndex = targetIndex;
        this.socialType = socialType;
    }
    //@@author

    //@@author marvinchin
    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson selectedPerson = lastShownList.get(targetIndex.getZeroBased());
        try {
            model.selectPerson(selectedPerson);
            // index of person might have shifted because of the select operation
            // so we need to find the new index
            Index newIndex = model.getPersonIndex(selectedPerson);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(newIndex, socialType));
        } catch (PersonNotFoundException e) {
            assert false : "The selected person should be in the last shown list";
        }

        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()));
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && this.targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
