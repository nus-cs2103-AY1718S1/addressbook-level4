package seedu.address.logic.commands.person;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.ReadOnlyPerson;

//@@author yunpengn
/**
 * Adds an {@link Avatar} to the selected person.
 */
public class AddAvatarCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "avatar";
    public static final String COMMAND_ALIAS = "avr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds avatar to the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) IMAGE_PATH\n"
            + "Example: " + COMMAND_WORD + " 1 something.png";

    public static final String MESSAGE_ADD_AVATAR_SUCCESS = "Added avatar to person: %1$s";

    private final Index targetIndex;
    private final Avatar avatar;

    public AddAvatarCommand(Index targetIndex, Avatar avatar) {
        this.targetIndex = targetIndex;
        this.avatar = avatar;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson person = lastShownList.get(targetIndex.getZeroBased());
        model.setPersonAvatar(person, avatar);

        return new CommandResult(String.format(MESSAGE_ADD_AVATAR_SUCCESS, person));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAvatarCommand // instanceof handles nulls
                && this.targetIndex.equals(((AddAvatarCommand) other).targetIndex)
                && this.avatar.equals(((AddAvatarCommand) other).avatar)); // state check
    }
}
