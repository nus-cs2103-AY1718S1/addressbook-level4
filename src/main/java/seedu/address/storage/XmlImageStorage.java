package seedu.address.storage;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.FileUtil.createDirs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

//@@author liliwei25
/**
 * Creates folder to store all images saved by user
 */
public class XmlImageStorage {

    private static final String PNG = ".png";
    private static final String PARENT_DIR = "profiles/";
    private static final String UNDERSCORE = "_";

    /**
     * Save selected image to image folder
     *
     * @return The file path of the saved image
     * @throws IOException when image copy fails
     */
    public String saveImage(File image, String name) throws IOException {
        requireNonNull(image);
        requireNonNull(name);

        File filePath = new File(PARENT_DIR);
        createDirs(filePath);
        // Create picture file to include time in case there are multiple Person with same name
        File newImage = new File(PARENT_DIR.concat(name).concat(UNDERSCORE)
                .concat(Long.toString(LocalDateTime.now().toEpochSecond(ZoneOffset.MAX))).concat(PNG));
        Files.copy(image.toPath(), newImage.toPath(), REPLACE_EXISTING);

        return newImage.getPath();
    }

    /**
     * Deletes image from directory
     *
     * @param location File path of image
     * @throws IOException If image is not found or delete failed
     */
    public void removeImage(String location) throws IOException {
        Files.delete(new File(location).toPath());
    }
}
