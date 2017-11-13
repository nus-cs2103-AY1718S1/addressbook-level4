//@@author tshradheya
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISPLAYPICTURE;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.DisplayPicture;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Adds a display picture to an existing person in address book
 */
public class DisplayPictureCommand extends Command {

    public static final String COMMAND_WORD = "displaypic";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds/Updates the profile picture of a person identified "
            + "by the index number used in the last person listing. "
            + "Existing Display picture will be updated by the image referenced in the input path. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DISPLAYPICTURE + "[PATH]\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + "C:\\Users\\Admin\\Desktop\\pic.jpg";

    public static final String MESSAGE_ADD_DISPLAYPICTURE_SUCCESS = "Added Display Picture to Person: %1$s";

    public static final String MESSAGE_DELETE_DISPLAYPICTURE_SUCCESS = "Removed Display Picture from Person: %1$s";

    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    public static final String MESSAGE_IMAGE_PATH_FAIL =
            "This specified path cannot be read. Please check it's validity and try again";
    private static final String EMPTY_STRING = "";

    private Index index;
    private DisplayPicture displayPicture;

    /**
     * @param index of the person in the filtered person list to edit the remark
     * @param displayPicture of the person
     */
    public DisplayPictureCommand(Index index, DisplayPicture displayPicture) {
        requireNonNull(index);
        requireNonNull(displayPicture);

        this.index = index;
        this.displayPicture = displayPicture;
    }

    @Override
    public CommandResult execute() throws CommandException, IOException, URISyntaxException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        //Defensive coding
        if (displayPicture.getPath() == null) {
            assert false : "Should never be null";
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());

        if (displayPicture.getPath().equalsIgnoreCase(EMPTY_STRING)) {
            displayPicture.setPath(EMPTY_STRING);

            Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), personToEdit.getBirthday(), personToEdit.getNickname(),
                    displayPicture, personToEdit.getPopularityCounter(), personToEdit.getTags());

            updatePersonWithDisplayPicturePath(personToEdit, editedPerson);

            return new CommandResult(generateSuccessMessage(editedPerson));
        }


        boolean isExecutedProperly = model.addDisplayPicture(displayPicture.getPath(),
                    personToEdit.getEmail().hashCode());
        if (isExecutedProperly) {
            displayPicture.setPath(Integer.toString(personToEdit.getEmail().hashCode()));
        } else {
            displayPicture.setPath("");
            return new CommandResult(generateFailureMessage());
        }

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getBirthday(), personToEdit.getNickname(),
                displayPicture, personToEdit.getPopularityCounter(), personToEdit.getTags());

        updatePersonWithDisplayPicturePath(personToEdit, editedPerson);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates failure message
     * @return String wih failure message
     */
    private String generateFailureMessage() {
        return MESSAGE_IMAGE_PATH_FAIL;
    }

    /**
     * Generates success message
     * @param personToEdit is checked
     * @return String with success message
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!displayPicture.getPath().isEmpty()) {
            return String.format(MESSAGE_ADD_DISPLAYPICTURE_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_DISPLAYPICTURE_SUCCESS, personToEdit);
        }
    }

    /**
     * Updates person in address book with new displaypicture path
     * @param personToEdit who has to be assigned display picture path
     * @param editedPerson person assigned display picture path
     * @throws CommandException when duplicate person found
     */
    private void updatePersonWithDisplayPicturePath(ReadOnlyPerson personToEdit,
                                                    Person editedPerson) throws CommandException {
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DisplayPictureCommand)) {
            return false;
        }

        // state check
        DisplayPictureCommand e = (DisplayPictureCommand) other;
        return index.equals(e.index)
                && displayPicture.equals(e.displayPicture);
    }
}
