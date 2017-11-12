package seedu.room.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.room.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.File;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

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
 * Allows the addition of an image to a resident currently in the resident book
 */
public class AddImageCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addImage";
    public static final String COMMAND_ALIAS = "ai";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an image to the resident identified "
            + "by the index number used in the last resident listing. "
            + "Existing Image will be replaced by the new image.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "url/[ Image Url ]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "url//Users/username/Downloads/person-placeholder.jpg";
    public static final String MESSAGE_VALID_IMAGE_FORMATS = "Allowed formats: JPG/JPEG/PNG/BMP";

    public static final String MESSAGE_ADD_IMAGE_SUCCESS = "Successfully changed image for Resident: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This resident already exists in the resident book.";

    private final Index index;
    private final String newImageUrl;

    /**
     *
     * @param index of the person in the list whose image is to be updated
     * @param newImageUrl url to the new replacing image
     */
    public AddImageCommand(Index index, String newImageUrl) {
        requireNonNull(index);

        this.index = index;
        this.newImageUrl = newImageUrl;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (!(new File(newImageUrl).exists())) {
            throw new CommandException(Messages.MESSAGE_INVALID_IMAGE_URL);
        }

        ReadOnlyPerson resident = lastShownList.get(index.getZeroBased());
        Person editedPerson = editResidentImage(resident);
        createResidentImage(editedPerson);

        try {
            model.updatePerson(resident, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target resident cannot be missing");
        }
        model.updateFilteredPersonListPicture(PREDICATE_SHOW_ALL_PERSONS, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_IMAGE_SUCCESS, editedPerson.getName()));
    }

    /**
     * @param resident to edit
     * @return Resident with updated Picture url
     */
    public Person editResidentImage(ReadOnlyPerson resident) {
        Name name = resident.getName();
        Phone phone = resident.getPhone();
        Email email = resident.getEmail();
        Room room = resident.getRoom();
        Timestamp timestamp = resident.getTimestamp();
        Set<Tag> tags = resident.getTags();

        Person editedPerson =  new Person(name, phone, email, room, timestamp, tags);
        if (checkJarResourcePath(resident)) {
            editedPerson.getPicture().setJarResourcePath();
        }

        editedPerson.getPicture().setPictureUrl(name.toString() + phone.toString() + ".jpg");
        return editedPerson;
    }

    /**
     * @param resident whose image is to be checked
     * @return true if in production mode (jar file)
     */
    public boolean checkJarResourcePath(ReadOnlyPerson resident) {
        File picture = new File(resident.getPicture().getPictureUrl());
        return (picture.exists()) ? false : true;
    }

    /**
     * @param resident whose attributes would be used to generate image file
     */
    public void createResidentImage(ReadOnlyPerson resident) {
        File picFile = new File(newImageUrl);
        try {
            if (resident.getPicture().checkJarResourcePath()) {
                ImageIO.write(ImageIO.read(picFile), "jpg", new File(resident.getPicture().getJarPictureUrl()));
            } else {
                ImageIO.write(ImageIO.read(picFile), "jpg", new File(resident.getPicture().getPictureUrl()));
            }
        } catch (Exception e) {
            System.out.println("Cannot create Person Image");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddImageCommand)) {
            return false;
        }

        // state check
        AddImageCommand ai = (AddImageCommand) other;
        return index.equals(ai.index) && newImageUrl.equals(ai.newImageUrl);
    }

}
