package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMAGE_URL;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ProfilePic;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;


//@@author soonwj
/**
 * Updates the profile picture of an existing person in the address book.
 */

public class UpdateProfilePicCommand extends Command {
    public static final String COMMAND_WORD = "updateProfilePic";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the profile picture of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing profile picture will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[u/valid URL of image]\n"
            + "Example of image stored locally: " + COMMAND_WORD + " 1 "
            + PREFIX_IMAGE_URL + "file:///C:/Users/Bobby/Images/picture.jpg\n"
            + "Example of image stored on the internet: " + COMMAND_WORD + " 1 "
            + PREFIX_IMAGE_URL
            + "http://asianwiki.com/images/4/45/Sooyoung-p2.jpg\n";

    public static final String MESSAGE_UPDATE_PROFILE_PIC_SUCCESS = "Updated profile pic of Person: %1$s";
    public static final String MESSAGE_NOT_UPDATED = "Please enter a valid image URL.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final ProfilePic profilePic;

    private boolean isOldFileDeleted = true;

    /**
     *
     * @param index of the person in the filtered person list to update profile picture
     * @param profilePic of the image to be used as the profile picture
     */
    public UpdateProfilePicCommand(Index index, ProfilePic profilePic) {
        requireNonNull(index);
        requireNonNull(profilePic);

        this.index = index;
        this.profilePic = profilePic;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToUpdateProfilePic = lastShownList.get(index.getZeroBased());
        Person updatedProfilePicPerson = new Person(personToUpdateProfilePic);
        ProfilePic newProfilePic;

        if (profilePic.toString().compareTo(ProfilePic.DEFAULT_URL) == 0) {
            deleteOldFile(personToUpdateProfilePic);
            newProfilePic = profilePic;
        } else {
            String newFile;
            createNewDirectory();
            newFile = setFileName(personToUpdateProfilePic);
            createNewFile(newFile);
            newProfilePic = downloadImage(newFile);
        }
        updatedProfilePicPerson.setProfilePic(newProfilePic);

        try {
            model.updatePerson(personToUpdateProfilePic, updatedProfilePicPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        if (profilePic.toString().compareTo(ProfilePic.DEFAULT_URL) != 0) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }
        String resultMessage = String.format(MESSAGE_UPDATE_PROFILE_PIC_SUCCESS, personToUpdateProfilePic);
        if (isOldFileDeleted) {
            return new CommandResult(resultMessage);
        } else {
            return new CommandResult(String.join("\n", resultMessage, "Old image not deleted"));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UpdateProfilePicCommand // instanceof handles nulls
                && this.index.equals(((UpdateProfilePicCommand) other).index)
                && this.profilePic.equals(((UpdateProfilePicCommand) other).profilePic)); // state check
    }

    private String urlToPath(String url) {
        return url.substring(url.indexOf("ProfilePics"));
    }

    /**
     * Deletes the old profile picture, if it exists
     * @param personToUpdateProfilePic is the person whose old profile picture is to be deleted
     */
    private void deleteOldFile(ReadOnlyPerson personToUpdateProfilePic) {
        String oldFile = personToUpdateProfilePic.getProfilePic().toString();
        if (oldFile.compareTo(ProfilePic.DEFAULT_URL) != 0) {
            oldFile = urlToPath(oldFile);
            try {
                Files.delete(Paths.get(oldFile));
            } catch (IOException ioe) {
                isOldFileDeleted = false;
            }
        }
    }

    /**
     * Creates a ProfilePics directory if it does not exist
     * @throws CommandException if directory cannot be created
     */
    private void createNewDirectory() throws CommandException {
        if (!Files.isDirectory(Paths.get("ProfilePics"))) {
            try {
                Files.createDirectory(Paths.get("ProfilePics"));
            } catch (IOException ioe) {
                throw new CommandException("ProfilePics directory failed to be created");
            }
        }
    }

    /**
     * Sets the file name of the new profile picture
     * @param personToUpdateProfilePic is the person whose profile picture's file name is to be set
     * @return the file name of teh new profile picture
     */
    private String setFileName(ReadOnlyPerson personToUpdateProfilePic) {
        String newFile;
        if (personToUpdateProfilePic.getProfilePic().toString().compareTo(ProfilePic.DEFAULT_URL) == 0) {
            newFile = "ProfilePics/" + new Date().getTime() + ".png";
        } else {
            newFile = personToUpdateProfilePic.getProfilePic().toString();
            newFile = urlToPath(newFile);
        }
        return newFile;
    }

    /**
     * Creates a new file for the profile picture if such a file does not exist
     * @param newFile is the name of the file to be created
     * @throws CommandException when the file cannot be created
     */
    private void createNewFile(String newFile) throws CommandException {
        if (!Files.exists(Paths.get(newFile))) {
            try {
                Files.createFile(Paths.get(newFile));
            } catch (IOException ioe) {
                throw new CommandException("New file failed to be created");
            }
        }
    }

    /**
     * Downloads the image of the profile picture into the ProfilePics directory
     * @param newFile is the name of the destination file
     * @throws CommandException when the image fails to download
     */
    private ProfilePic downloadImage(String newFile) throws CommandException {
        ProfilePic newProfilePic;
        try {
            URL url = new URL(profilePic.toString());
            InputStream in = url.openStream();
            Files.copy(in, Paths.get(newFile), StandardCopyOption.REPLACE_EXISTING);
            in.close();
            newProfilePic = new ProfilePic("file://" + Paths.get(newFile).toAbsolutePath().toUri().getPath());
        } catch (IOException ioe) {
            throw new CommandException("Image failed to download");
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }
        return newProfilePic;
    }
}
