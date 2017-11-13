//@@author viviantan95
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHOTO;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.Photo;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Adds a photo to a person in the address book.
 */
public class AddPhotoCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addphoto";
    public static final String COMMAND_ALIAS = "ap";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a photo to a person in the address book using "
            + "the index of the person in the latest listing.\n"
            + "Parameters: INDEX (must be a positive integer) u/[PHOTO_URL]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_PHOTO
            + "https://www.facebook.com/AlexYeoh/photo1.jpg\n";

    public static final String MESSAGE_ADDPHOTO_SUCCESS = "Added photo of Person: %1$s";
    public static final String MESSAGE_ADDPHOTO_UNSUCCESS = "Please enter a valid photo URL.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Index index;
    private final Photo photo;

    public AddPhotoCommand(Index index, Photo photo) {
        requireNonNull(index);
        requireNonNull(photo);

        this.index = index;
        this.photo = photo;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> latestList = model.getFilteredPersonList();
        if (index.getZeroBased() >= latestList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToAddPhoto = latestList.get(index.getZeroBased());
        Person addedPhotoPerson = new Person(personToAddPhoto);
        addedPhotoPerson.setPhoto(photo);

        try {
            model.updatePerson(personToAddPhoto, addedPhotoPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing.");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADDPHOTO_SUCCESS, personToAddPhoto));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPhotoCommand // instanceof handles nulls
                && this.index.equals(((AddPhotoCommand) other).index)
                && this.photo.equals(((AddPhotoCommand) other).photo));
    }
}
