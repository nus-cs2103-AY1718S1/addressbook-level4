package seedu.address.model.person;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;

//@@author wenzongteo
/**
 * Represents a Person's display picture in the address book
 * Guarantees: immutable; is valid as declared in (@Link #isValidImage(String))
 */
public class Photo {
    public static final String MESSAGE_PHOTO_CONSTRAINTS =
            "Person's photo should be in .jpg or .jpeg and preferred to be of 340px x 453px dimension. If the "
                    + "photo is on the local system, please provide the absolute file path. If the photo is from the "
                    + "internet, ensure that the link starts with http or https and ends with .jpg or .jpeg";
    public static final String MESSAGE_PHOTO_NOT_FOUND = "Error! Photo does not exist!";
    public static final String MESSAGE_LINK_ERROR = "Error! URL given is invalid!";

    /**
     * Can contain multiple words but must end with .jpg or .jpeg
     */
    public static final String PHOTO_VALIDATION_REGEX = "([^\\s]+[\\s\\w]*(\\.(?i)(jpg|jpeg|))$)";
    /**
     * Can contain multiple words or symbols but must start with either http or https and end with either .jpg or .jpeg
     */
    public static final String URL_REGEX =
            "((^(https?)(://))[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]*(\\.(?i)(jpg|jpeg))$)";
    public static final String DEFAULT_PHOTO = "data/images/default.jpeg";
    public static final String DOWNLOAD_LOCATION = "data/download.jpg";
    public static final String UNFILLED = "-";
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public final String value;
    private final String hash;

    /**
     * Validates given photo.
     * @throws IllegalValueException if given photo string is invalid or file is not found.
     */
    public Photo(String photo) throws IllegalValueException {
        photo = photo.trim();

        if (photo.equals(UNFILLED)) {
            logger.info("photo fill is left unfilled, using default photo as contact's photo.");
            photo = DEFAULT_PHOTO;
        }

        if (!isValidPhoto(photo)) {
            throw new IllegalValueException(MESSAGE_PHOTO_CONSTRAINTS);
        } else {
            this.value = getPhoto(photo);
            File image = new File(this.value);
            this.hash = generateHash(image);
        }
    }

    /**
     * Photo entered by the app that does not require validation as Image should already exist.
     * If Photo entered by the app does not exist, default image will used instead.
     * @param photo path to the image stored.
     * @param num used for overloading constructor.
     */
    public Photo(String photo, int num) {
        photo = photo.trim();

        File image = new File(photo);
        this.value = photo;

        if (!FileUtil.isFileExists(image)) {
            logger.info("contact's photo cannot be found. Using default photo instead.");
            copyDefaultPhoto(photo);
        }
        this.hash = generateHash(image);
    }

    /**
     * Check if photo source given is from the internet or local system and call the relevant methods.
     * Return the path for the photo that is obtained from the relevant methods.
     * @param photo photo path specified by the user.
     * @return photo path of the photo that is obtained from the relevant methods.
     * @throws IllegalValueException if the photo is not found.
     */
    private String getPhoto(String photo) throws IllegalValueException {
        if (photo.matches(URL_REGEX)) {
            logger.info("Attempting to download photo from the internet");
            return getPhotoFromInternet(photo);
        } else {
            return getPhotoFromLocal(photo);
        }
    }

    /**
     * Check if the file exists in the local system. Return IllegalValueException if the image is not found.
     * @param photo photo path specified by the user.
     * @return photo path specified by the user if the photo exists.
     * @throws IllegalValueException if the photo is not found on the local system
     */
    private String getPhotoFromLocal(String photo) throws IllegalValueException {
        File image = new File(photo);
        if (!FileUtil.isFileExists(image)) {
            throw new IllegalValueException(MESSAGE_PHOTO_NOT_FOUND);
        } else {
            return photo;
        }
    }

    /**
     * Copy the default photo for contact to serve as the contact's photo.
     * @param destPath File path of the contact's photo.
     */
    public void copyDefaultPhoto(String destPath) {
        try {
            Files.copy(Paths.get(DEFAULT_PHOTO), Paths.get(destPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new AssertionError("Photo should already exist!");
        }
    }

    /**
     *  @return the generated hash of the image.
     *  @throws IOException if the file does not exist.
     *  @throws NoSuchAlgorithmException if the algorithm does not exist.
     */
    public String generateHash(File photo) {
        try {
            MessageDigest hashing = MessageDigest.getInstance("MD5");
            return new String(hashing.digest(Files.readAllBytes(photo.toPath())));
        } catch (Exception e) {
            throw new AssertionError("Impossible");
        }
    }

    /**
     * @return true if a given string is a valid person photo.
     */
    public static boolean isValidPhoto(String test) {
        if (test.matches(URL_REGEX)) {
            return true;
        } else if (test.matches(PHOTO_VALIDATION_REGEX)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the hash of the current image.
     */
    public String getHash() {
        return hash;
    }

    /**
     * Downloads photo from the path given by the user and store it locally. Returns the local file path of the image.
     * @param photo URL link given by the user
     * @return the file path of the downloaded image in the local system.
     * @throws IllegalValueException if errors are faced when visiting the link or downloading the file.
     */
    private String getPhotoFromInternet(String photo) throws IllegalValueException {
        try {
            URL url = new URL(photo);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(DOWNLOAD_LOCATION);
            byte[] buffer = new byte[4096];

            int length = 0;

            while ((length = is.read(buffer)) != -1) {
                os.write(buffer, 0, length);
            }

            is.close();
            os.close();

            logger.info("Photo downloaded to " + DOWNLOAD_LOCATION);
            return DOWNLOAD_LOCATION;
        } catch (IOException e) {
            throw new IllegalValueException(MESSAGE_LINK_ERROR);
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Photo // instanceof handles nulls
                && this.value.equals(((Photo) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
