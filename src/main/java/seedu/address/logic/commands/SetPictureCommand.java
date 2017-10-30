package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Favourite;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfPic;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Sets the profile picture for an indexed person.
 */
public class SetPictureCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "ppset";
    public static final String COMMAND_ALT = "pps";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the profile picture of person in given index with picture at given file path.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " fp/FILEPATH";

    public static final String MESSAGE_SET_PICTURE_PERSON_SUCCESS = "New profile picture for %1$s has been set";
    public static final String MESSAGE_INVALID_FILE = "File at given file path was not .jpg or .png";
    public static final String MESSAGE_FILE_NOT_EXIST = "File does not exist at given file path";

    private final Index targetIndex;
    private final String filePath;

    public SetPictureCommand(Index index, ProfPic filePath) {
        requireNonNull(index);
        requireNonNull(filePath);
        this.targetIndex = index;
        this.filePath = filePath.getPath();
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        final File file = new File(filePath);
        String fileType;

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        try {
            fileType = Files.probeContentType(file.toPath());
            if (!(fileType.equals("image/jpeg") || fileType.equals("image/png"))) { // jpeg or png?
                throw new CommandException(MESSAGE_INVALID_FILE);
            }
        } catch (IOException ioException) {
            throw new CommandException(MESSAGE_FILE_NOT_EXIST);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(targetIndex.getZeroBased());

        // copy picture to resource/image folder and name copied file as PERSON_NAME.png
        Path dest = new File("resources/images" + personToEdit.getName().toString() + ".png").toPath();

        try {
            Files.copy(file.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioException) {
            // ???
        }

        Person editedPerson = setPicturePerson(personToEdit);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) { // If duplicate it just means current pic is not default
            return new CommandResult(String.format(MESSAGE_SET_PICTURE_PERSON_SUCCESS, editedPerson));
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SET_PICTURE_PERSON_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the the Favourite attribute set to true.
     */
    private static Person setPicturePerson(ReadOnlyPerson personToEdit) {
        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        ProfPic updatedProfPic = new ProfPic(updatedName + "png");
        Favourite updatedFavourite = personToEdit.getFavourite();
        Set<Tag> updatedTags = personToEdit.getTags();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedFavourite,
                updatedProfPic, updatedTags);
    }
}
