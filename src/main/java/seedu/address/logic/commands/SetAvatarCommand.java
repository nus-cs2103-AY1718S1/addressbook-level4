package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVATAR;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.storage.util.ProcessImageUtil;

//@@author karrui
/**
 * Changes the avatar of an existing person in the address book
 */
public class SetAvatarCommand extends Command {

    public static final String COMMAND_WORD = "setavatar";
    public static final String COMMAND_ALIAS = "sa";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the avatar of the person identified by the index number used in the last person listing."
            + "The image will be sourced from the path URL parameter provided.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_AVATAR + "[URL]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_AVATAR + "http://www.examplepictures.com/image.jpg";

    public static final String MESSAGE_SET_AVATAR_SUCCESS = "Set Avatar for Person: %1$s";
    public static final String MESSAGE_REMOVE_AVATAR_SUCCESS = "Removed Avatar for Person: %1$s";

    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    public static final String MESSAGE_URL_INVALID = "The URL is invalid / is not an image."
            + " Avatar has been set to the default avatar";

    private final Index targetIndex;
    private final Avatar avatar;

    public SetAvatarCommand(Index index, Avatar avatar) {
        requireNonNull(index);
        requireNonNull(avatar);

        this.targetIndex = index;
        this.avatar = avatar;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToSetAvatar = lastShownList.get(targetIndex.getZeroBased());
        String personToSetAvatarPath = personToSetAvatar.getAvatar().path;
        Person editedPerson;

        if ("".equals(avatar.path) && !"".equals(personToSetAvatarPath)) { // delete image from storage
            ProcessImageUtil.removeImageFromStorage(personToSetAvatarPath);
        } else if (!"".equals(personToSetAvatarPath)) {   // has a previously set avatar, remove first before processing
            ProcessImageUtil.removeImageFromStorage(personToSetAvatarPath);
        }

        if (personToSetAvatar.isHomepageManuallySet()) {
            editedPerson = new Person(personToSetAvatar.getName(), personToSetAvatar.getPhone(),
                    personToSetAvatar.getEmail(), personToSetAvatar.getAddress(), personToSetAvatar.getRemark(),
                    avatar, personToSetAvatar.getTags(), personToSetAvatar.getHomepage());
        } else {
            editedPerson = new Person(personToSetAvatar.getName(), personToSetAvatar.getPhone(),
                    personToSetAvatar.getEmail(), personToSetAvatar.getAddress(), personToSetAvatar.getRemark(),
                    avatar, personToSetAvatar.getTags());
        }

        try {
            model.updatePerson(personToSetAvatar, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }


        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Success message if remark added or deleted
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!avatar.path.isEmpty()) {
            return String.format(MESSAGE_SET_AVATAR_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_REMOVE_AVATAR_SUCCESS, personToEdit);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetAvatarCommand)) {
            return false;
        }

        // state check
        SetAvatarCommand e = (SetAvatarCommand) other;
        return targetIndex.equals(e.targetIndex)
                && avatar.equals(e.avatar);
    }
}
