package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.avatar.Avatar;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

//@@author nadhira15
/**
 * Adds tag(s) to a person in the address book.
 */
public class AddTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtag";
    public static final String COMMAND_ALIAS = "at";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add tag(s) to a person in the address book. "
            + "Parameters: INDEX (must be a positive integer) "
            + "TAG [MORE_TAGS] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "neighbours owesMoney";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Tag: %2$s";
    public static final String MESSAGE_ADD_TAG_SUCCESS = "Tag(s) added!";
    public static final String MESSAGE_DUPLICATE_TAG = "This tag(s) already exists for this person.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person is already in the address book.";

    private final Index index;
    private final Set<Tag> addedTag;

    /**
     * @param index of the person in the filtered person list to add tag
     * @param addedTag tag of the person
     */

    public AddTagCommand(Index index, Set<Tag> addedTag) {
        requireNonNull(index);
        requireNonNull(addedTag);

        this.index = index;
        this.addedTag = addedTag;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, addedTag);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
        return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, editedPerson));
    }
    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             Set<Tag> addedTag) throws CommandException {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Birthday birthday = personToEdit.getBirthday();
        Avatar avatar = personToEdit.getAvatar();

        Set<Tag> initialTags = personToEdit.getTags();
        Set<Tag> updatedTags = new HashSet<>();

        try {
            updatedTags = getUpdatedTags(initialTags, addedTag);
        } catch (DuplicateTagException e) {
            throw new CommandException(e.getMessage());
        }

        return new Person(name, phone, email, address, birthday, avatar, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        //short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTagCommand)) {
            return false;
        }

        // state check
        AddTagCommand e = (AddTagCommand) other;
        return index.equals(e.index)
                && addedTag.equals(e.addedTag);
    }

    public static HashSet<Tag> getUpdatedTags(Set<Tag> initialTag, Set<Tag> addedTag) throws DuplicateTagException {
        HashSet<Tag> updatedTags = new HashSet<>(initialTag);
        for (Tag toAdd : addedTag) {
            requireNonNull(toAdd);
            if (initialTag.contains(toAdd)) {
                throw new DuplicateTagException();
            }
            updatedTags.add(toAdd);
        }
        return updatedTags;
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTagException extends DuplicateDataException {
        protected DuplicateTagException() {
            super(MESSAGE_DUPLICATE_TAG);
        }

        public String getMessage() {
            return super.getMessage();
        }
    }
}
