package seedu.room.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.room.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.room.commons.core.Messages;
import seedu.room.commons.core.index.Index;
import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.model.person.Email;
import seedu.room.model.person.Name;
import seedu.room.model.person.Person;
import seedu.room.model.person.Phone;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.person.Room;
import seedu.room.model.person.Timestamp;
import seedu.room.model.person.exceptions.DuplicatePersonException;
import seedu.room.model.person.exceptions.PersonNotFoundException;
import seedu.room.model.tag.Tag;

//@@author shitian007
/**
 * Allows deletion of an image for a specified person
 */
public class DeleteImageCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deleteImage";
    public static final String COMMAND_ALIAS = "di";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an image to the person identified "
            + "by the index number used in the last person listing. "
            + "Existing Image will be reset to placeholder image.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 3 ";

    public static final String MESSAGE_RESET_IMAGE_SUCCESS = "Successfully deleted image for Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the resident book.";

    private final Index index;

    /**
     * @param index of the person in the list whose image is to be deleted
     */
    public DeleteImageCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson person = lastShownList.get(index.getZeroBased());
        Person editedPerson = resetPersonImage(person);

        try {
            model.updatePerson(person, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonListPicture(PREDICATE_SHOW_ALL_PERSONS, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_RESET_IMAGE_SUCCESS, editedPerson.getName()));
    }

    /**
     * @param person whose image is to be reset
     * @return Person object with picture url reset
     */
    public Person resetPersonImage(ReadOnlyPerson person) {
        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Room room = person.getRoom();
        Timestamp timestamp = person.getTimestamp();
        Set<Tag> tags = person.getTags();

        Person editedPerson =  new Person(name, phone, email, room, timestamp, tags);
        return editedPerson;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            System.out.println("this");
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteImageCommand)) {
            System.out.println("that");
            return false;
        }

        // state check
        DeleteImageCommand ai = (DeleteImageCommand) other;
        return index.equals(ai.index);
    }
}
