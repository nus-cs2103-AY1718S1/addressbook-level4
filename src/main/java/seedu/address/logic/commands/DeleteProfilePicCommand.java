package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ProfilePic;
import seedu.address.model.person.ReadOnlyPerson;

//@@author soonwj
/**
 * Deletes the profile picture of a person identified using it's last displayed index from the address book.
 */
public class DeleteProfilePicCommand extends Command {
    public static final String COMMAND_WORD = "deleteProfilePic";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the profile picture of the person identified by the index number used in "
            + "the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    public static final String MESSAGE_DELETE_PROFILE_PIC_SUCCESS = "Deleted profile picture of Person: %1$s";

    private final Index targetIndex;

    public DeleteProfilePicCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson profilePicToDelete = lastShownList.get(targetIndex.getZeroBased());

        UpdateProfilePicCommand updateToDefault = new UpdateProfilePicCommand(targetIndex, new ProfilePic());
        updateToDefault.setData(model, history, undoRedoStack);
        updateToDefault.execute();

        return new CommandResult(String.format(MESSAGE_DELETE_PROFILE_PIC_SUCCESS, profilePicToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteProfilePicCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteProfilePicCommand) other).targetIndex)); // state check
    }
}
