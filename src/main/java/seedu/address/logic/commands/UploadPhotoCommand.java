package seedu.address.logic.commands;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.PhotoChangeEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Email;
import seedu.address.model.person.ReadOnlyPerson;

//@@author JasmineSee

/**
 * Uploads image file to specified person.
 */
public class UploadPhotoCommand extends Command {
    public static final String COMMAND_WORD = "photo";
    public static final String COMMAND_ALIAS = "p";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Uploads image to the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) or "
            + "INDEX (must be a positive integer) and image file path\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "OR: " + COMMAND_WORD + " 1 " + "C:\\Users\\Pictures\\photo.jpg";

    public static final String MESSAGE_UPLOAD_IMAGE_SUCCESS = "Uploaded image to Person: %1$s";
    public static final String MESSAGE_UPLOAD_IMAGE_FALURE = "Image file is not valid. Try again!";
    private final Index targetIndex;
    private final String filePath;
    private final FileChooser fileChooser = new FileChooser();
    private Stage stage;

    public UploadPhotoCommand(Index targetIndex, String filePath) {
        this.targetIndex = targetIndex;
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        ReadOnlyPerson personToUploadImage = lastShownList.get(targetIndex.getZeroBased());
        File imageFile;

        if (filePath.equals("")) {
            imageFile = handleFileChooser();
        } else {
            imageFile = new File(filePath);
        }

        if (isValidImageFile(imageFile)) {
            saveFile(imageFile, personToUploadImage.getEmail());
            EventsCenter.getInstance().post(new PhotoChangeEvent());
        } else {
            throw new CommandException(String.format(MESSAGE_UPLOAD_IMAGE_FALURE));
        }

        LoggingCommand loggingCommand = new LoggingCommand();
        loggingCommand.keepLog("", "Uploaded photo to " + targetIndex.getOneBased());
        return new CommandResult(String.format(MESSAGE_UPLOAD_IMAGE_SUCCESS, personToUploadImage));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UploadPhotoCommand // instanceof handles nulls
                && this.targetIndex.equals(((UploadPhotoCommand) other).targetIndex)); // state check
    }

    /**
     * Opens fileChooser to select image file
     */
    public File handleFileChooser() {
        File file = fileChooser.showOpenDialog(stage);
        return file;
    }

    /**
     * Checks if file given is an valid image file.
     */
    private boolean isValidImageFile(File file) {
        boolean isValid = true;
        try {
            BufferedImage image = ImageIO.read(file);
            if (image == null) {  //file is not an image file
                isValid = false;
            }
        } catch (IOException ex) { //file could not be opened
            isValid = false;
        }
        return isValid;
    }

    /**
     * Reads and saves image file into project directory folder "photos".
     */
    private void saveFile(File file, Email email) {

        File path = new File("photos/" + email.toString() + ".png");

        try {
            path.mkdirs();
            path.createNewFile();
            BufferedImage image;
            image = ImageIO.read(file);
            ImageIO.write(image, "png", path);

        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(UploadPhotoCommand.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
