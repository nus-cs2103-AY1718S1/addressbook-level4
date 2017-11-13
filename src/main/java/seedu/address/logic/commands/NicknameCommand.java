package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Nickname;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author chuaweiwen
/**
 * Sets the nickname of an existing person in the address book.
 */
public class NicknameCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "nickname";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the nickname to the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[NICKNAME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "Adam";

    public static final String MESSAGE_SET_NICKNAME_SUCCESS = "Nickname successfully set to Person: %1$s";
    public static final String MESSAGE_REMOVE_NICKNAME_SUCCESS = "Nickname successfully removed from Person: %1$s";
    public static final String MESSAGE_UNCHANGED_NICKNAME = "Nickname remains unchanged for Person: %1$s";

    private final Index index;
    private final Nickname nickname;

    /**
     * @param index of the person in the filtered person list to edit
     * @param nickname details to edit the person with
     */
    public NicknameCommand(Index index, Nickname nickname) {
        requireNonNull(index);
        requireNonNull(nickname);

        this.index = index;
        this.nickname = nickname;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = (Person) lastShownList.get(index.getZeroBased());
        Nickname previousNickname;

        try {
            previousNickname = personToEdit.getNickname();
        } catch (NullPointerException npe) {
            throw new AssertionError("Nickname cannot be null");
        }

        personToEdit.setNickname(nickname);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        try {
            model.updatePerson(personToEdit, personToEdit);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("The target person cannot be duplicated");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        if (nickname.equals(previousNickname)) {
            return new CommandResult(String.format(MESSAGE_UNCHANGED_NICKNAME, personToEdit.getAsText()));
        } else if (nickname.value.equals("")) {
            return new CommandResult(String.format(MESSAGE_REMOVE_NICKNAME_SUCCESS, personToEdit.getAsText()));
        } else {
            return new CommandResult(String.format(MESSAGE_SET_NICKNAME_SUCCESS, personToEdit.getAsText()));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NicknameCommand)) {
            return false;
        }

        // state check
        NicknameCommand e = (NicknameCommand) other;
        return index.equals(e.index) && nickname.equals(e.nickname);
    }
}
//@@author
