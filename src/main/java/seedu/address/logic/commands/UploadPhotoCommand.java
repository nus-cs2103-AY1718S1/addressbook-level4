package seedu.address.logic.commands;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.PhotoChangeEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Email;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Uploads image file to specified person.
 */
public class UploadPhotoCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "photo";
    public static final String COMMAND_ALIAS = "p";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Uploads image to the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) or INDEX (must be a positive integer) and image file path\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "OR: " + COMMAND_WORD + " 1 " + "C:\\Users\\Pictures\\photo.jpg";

    public static final String MESSAGE_UPLOAD_IMAGE_SUCCESS = "Uploaded image to Person: %1$s";
    public static final String MESSAGE_UPLOAD_IMAGE_FALURE = "Image file is not valid. Try again!";
    private final Index targetIndex;
    private final String filePath;
    private final FileChooser fileChooser = new FileChooser();
    private Stage stage;
    private ImageView imageView = new ImageView();
    private HashMap<Email, String> photoList;

    public UploadPhotoCommand(Index targetIndex, String filePath) {
        this.targetIndex = targetIndex;
        this.filePath = filePath;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        ReadOnlyPerson personToUploadImage = lastShownList.get(targetIndex.getZeroBased());
        File imageFile;
        //  Photo targetPhoto = lastShownList.get(targetIndex.getZeroBased()).getPhoto();

        if (filePath.equals("")) {
            imageFile = handleFileChooser();
        } else {
            imageFile = new File(filePath);
        }

        if (isValidImageFile(imageFile)) {
            imageFile = saveFile(imageFile, personToUploadImage.getEmail());
            EventsCenter.getInstance().post(new PhotoChangeEvent());
        } else {
            return new CommandResult(String.format(MESSAGE_UPLOAD_IMAGE_FALURE));
        }

        // ReadOnlyPerson editedPerson = lastShownList.get(targetIndex.getZeroBased());
        // editedPerson.getPhoto().setPath(imageFile.getPath());
        // photoList.put(personToUploadImage.getEmail(), imageFile.getPath());
        // EventsCenter.getInstance().registerHandler(handler);
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
            Image image = ImageIO.read(file);
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
    private File saveFile(File file, Email email) {

        File path = new File("src/main/photos/" + email.toString() + ".png");

        try {
            path.mkdirs();
            path.createNewFile();
            //     System.out.println(path.getPath());
            //  BufferedImage image = new BufferedImage(100, 100, 1);
            BufferedImage image;
            image = ImageIO.read(file);
            ImageIO.write(image, "png", path);

        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(UploadPhotoCommand.class.getName()).log(Level.SEVERE, null, e);
        }
        return path;
    }

}
