package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.MessageAlignmentFormatter.FORMAT_ALIGNMENT_TO_EXAMPLE;
import static seedu.address.commons.core.MessageAlignmentFormatter.FORMAT_ALIGNMENT_TO_PARAMETERS;
import static seedu.address.commons.util.FileUtil.REGEX_VALID_IMAGE;
import static seedu.address.commons.util.FileUtil.copyFile;
import static seedu.address.commons.util.FileUtil.createIfMissing;
import static seedu.address.commons.util.FileUtil.getFileExtension;
import static seedu.address.commons.util.FileUtil.haveSameContent;
import static seedu.address.commons.util.FileUtil.isFileExists;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHOTO;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.PersonSelectedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.photo.PhotoPath;
import seedu.address.model.photo.exceptions.DuplicatePhotoPathException;

//@@author April0616
/**
 * Edits the photo path of the specified person.
 */
public class PhotoCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "photo";
    public static final String COMMAND_ALIAS = "ph";

    public static final String MESSAGE_USAGE = "| " + COMMAND_WORD + " |"
            + ": Adds a photo to the person identified by the index number used in the last person listing"
            + "by specifying the path of the photo.\n"
            + "The valid photo extensions are 'jpg', 'jpeg', 'png', 'gif' or 'bmp'.\n"
            + "If the path field is empty, the old photo path is removed for the person.\n"
            + "Parameters: INDEX " + PREFIX_PHOTO + "[PHOTO PATH] \n"
            + FORMAT_ALIGNMENT_TO_PARAMETERS + "(INDEX must be a positive integer)\n"
            + "Example: (add photo)  " + COMMAND_WORD + " 1 " + PREFIX_PHOTO
            + "C:\\Users\\User\\Desktop\\photo.jpg\n"
            + FORMAT_ALIGNMENT_TO_EXAMPLE + "(delete photo) " + COMMAND_WORD + " 2 " + PREFIX_PHOTO + "\n";
    public static final String MESSAGE_ADD_PHOTO_SUCCESS =
            "Successfully saved photo and added the photo path to Person: %1$s";
    public static final String MESSAGE_DELETE_PHOTO_SUCCESS = "Removed photo path from Person: %1$s";
    public static final String MESSAGE_NO_PHOTO_TO_DELETE = "No photo path to remove from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSONS = "This person already exists in the address book.";
    public static final String MESSAGE_LOCAL_PHOTOPATH_CONSTRAINTS =
            "Photo Path should be the absolute path of a valid file in your PC. It should be a string started "
                    + "with the name of your disk, "
                    + "followed by several groups of backslash and string, like \"c:\\desktop\\happy.jpg\","
                    + "and the file should exist.\n"
                    + "The valid photo extensions are 'jpg', 'jpeg', 'png', 'gif' or 'bmp'.\n";

    public static final String REGEX_LOCAL_PHOTOPATH_VALIDATION =
            "([a-zA-Z]:)?(\\\\[a-zA-Z0-9_.-]+)+\\\\?" + REGEX_VALID_IMAGE;

    public static final String PATH_FILE_SAVED_PARENT_DIRECTORY = "src/main/resources/images/contactPhotos/";
    public static final String PATH_DEFAULT_PHOTO = "src/main/resources/images/defaultPhoto.jpg";

    private Index targetIndex;
    private String localPhotoPath;
    private PhotoPath photoPath;

    /**
     * Initializes the PhotoCommand and decides its behaviour.
     * @param targetIndex of the person in the list to edit the photo path
     * @param localPhotoPath path of the photo store in the computer
     */
    public PhotoCommand(Index targetIndex, String localPhotoPath) throws IllegalValueException, FileNotFoundException {
        requireNonNull(targetIndex);
        requireNonNull(localPhotoPath);

        String trimmedPhotoPath = localPhotoPath.trim();

        if (isEmptyPhotoPath(trimmedPhotoPath)) {
            setDeletePhotoCommandParameters(targetIndex);
        } else if (isValidLocalPhotoPath(trimmedPhotoPath)) {
            setEditPhotoCommandParametersAndFile(targetIndex, trimmedPhotoPath);
        } else {
            throw new IllegalValueException(MESSAGE_LOCAL_PHOTOPATH_CONSTRAINTS);
        }
    }
    private void setEditPhotoCommandParametersAndFile(Index targetIndex, String photoPath)
            throws FileNotFoundException, IllegalValueException {
        // copy the photo
        String savePath = copyLocalPhotoToApp(photoPath);

        // set up parameters
        this.targetIndex = targetIndex;
        localPhotoPath = photoPath;
        this.photoPath = new PhotoPath(savePath);
    }

    /**
     * Copies the photo specified in the local photo path to the app.
     * The copied photo is saved in the default folder in the app.
     * @param localPhotoPath of the photo
     * @return the photo path string of the copied photo
     * @throws FileNotFoundException if the photo is not found in the user's computer
     */
    private String copyLocalPhotoToApp(String localPhotoPath) throws FileNotFoundException {
        File targetFile = new File(localPhotoPath);
        String extension = getFileExtension(localPhotoPath);
        String savePath = PATH_FILE_SAVED_PARENT_DIRECTORY + getSavedFileName(extension);

        if (!isFileExists(targetFile)) {
            throw new FileNotFoundException(MESSAGE_LOCAL_PHOTOPATH_CONSTRAINTS);
        }

        createAppPhotoFile(savePath);

        try {
            copyFile(localPhotoPath, savePath);
        } catch (IOException e) {
            assert false : "Cannot copy the file!";
        }

        return savePath;
    }


    private void setDeletePhotoCommandParameters(Index targetIndex) throws IllegalValueException {
        this.targetIndex = targetIndex;
        localPhotoPath = "";
        photoPath = new PhotoPath("");
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToPhoto = lastShownList.get(targetIndex.getZeroBased());
        Person photoedPerson = createPhotoedPerson(personToPhoto, photoPath);

        try {
            model.addPhotoPath(photoPath);
        } catch (DuplicatePhotoPathException e) {
            assert false : "Duplicated photo path!";
        }

        try {
            model.updatePerson(personToPhoto, photoedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSONS);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        EventsCenter.getInstance().post(new PersonSelectedEvent(photoedPerson, targetIndex.getZeroBased()));
        return new CommandResult(generateSuccessMsg(personToPhoto));
    }

    /**
     * Returns true if the given string is an empty photo path.
     */
    public static boolean isEmptyPhotoPath(String photoPathString) {
        return photoPathString.equals("");
    }

    /**
     * Returns true if the given string is a valid local photo path.
     */
    public static boolean isValidLocalPhotoPath(String photoPathString) {
        return photoPathString.matches(REGEX_LOCAL_PHOTOPATH_VALIDATION);
    }

    /**
     * Generates the unique name of the copied photo.
     * The name is generated by combining the time {@code num} this method executes and
     * its original {@code fileExtension}.
     * @return the name of the copied photo.
     */
    private String getSavedFileName(String fileExtension) {
        Date date = new Date();
        Long num = date.getTime();
        return num.toString() + fileExtension;
    }


    /**
     * Creates and returns a {@code Person} with the details of {@code personToPhoto}.
     */
    private static Person createPhotoedPerson(ReadOnlyPerson personToPhoto,
                                              PhotoPath photoPath) {
        assert personToPhoto != null;

        Person photoPerson = new Person(personToPhoto.getName(), personToPhoto.getGender(),
                personToPhoto.getMatricNo(), personToPhoto.getPhone(),
                personToPhoto.getEmail(), personToPhoto.getAddress(), personToPhoto.getTimetable(),
                personToPhoto.getRemark(), photoPath, personToPhoto.getTags(), personToPhoto.getBirthday());

        return photoPerson;
    }

    /**
     * Creates an empty app photo file if it doesn't exist.
     * @param path of the app photo
     * @throws IOException if the file or directory cannot be created.
     */
    public void createAppPhotoFile(String path) {
        File photoFile = new File(path);
        try {
            createIfMissing(photoFile);
        } catch (IOException e) {
            assert false : "The file or directory cannot be created.";
        }
    }

    /**
     * Generates the successful message accordingly.
     * @param personToPhoto
     * @return the successful message for adding photo if the photo path string is not empty.
     */
    private String generateSuccessMsg(ReadOnlyPerson personToPhoto) {
        if (photoPath.toString().equals("")) {
            if (personToPhoto.getPhotoPath().value.equals("")) {
                return String.format(MESSAGE_NO_PHOTO_TO_DELETE, personToPhoto);
            } else {
                return String.format(MESSAGE_DELETE_PHOTO_SUCCESS, personToPhoto);
            }
        } else {
            return String.format(MESSAGE_ADD_PHOTO_SUCCESS, personToPhoto);
        }
    }

    /**
     * Gets the local photo path of the file.
     * @return the string of local photo path
     */
    public String getLocalPhotoPath() {
        return this.localPhotoPath;
    }

    /**
     * Gets the photo path of the file stored in app.
     * @return the string of app photo path
     */
    public String getAppPhotoPath() {
        return this.photoPath.value;
    }

    /**
     * Gets the index of the person.
     * @return the target index of the person
     */
    public Index getIndex() {
        return this.targetIndex;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhotoCommand)) {
            return false;
        }

        // state check
        PhotoCommand ph = (PhotoCommand) other;
        return targetIndex.equals(ph.targetIndex)
                && haveSameContent(ph.photoPath.value, ((PhotoCommand) other).photoPath.value);
    }
}

