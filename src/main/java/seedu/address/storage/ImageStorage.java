package seedu.address.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;

import javafx.scene.image.Image;

import seedu.address.commons.util.AppUtil;
import seedu.address.commons.util.FileUtil;

/**
 * A class to store and retrieve images in the data folder
 * Currently used for for avatars
 */
//@@author nicholaschuayunzhi
public class ImageStorage {

    public static final String PNG = "png";
    public static final String JPG = "jpg";
    public static final String AVATAR_STORAGE_PATH = "data/images/avatars/";
    public static final String DEFAULT_RESOURCE_PATH = "/images/avatars/default.png";

    /**
     * Looks for image in {@code AVATAR_STORAGE_PATH} based on imageName
     * @param imageName
     * @return image if it exists or default image if image does not exist
     */
    public static Image getAvatar(String imageName) {
        Image image;

        try {

            String fullPath = AVATAR_STORAGE_PATH + imageName;
            image = new Image(new FileInputStream(new File(fullPath)));

        } catch (FileNotFoundException e) {
            image = AppUtil.getImage(DEFAULT_RESOURCE_PATH);
        }

        return image;
    }

    /**
     * Saves image to {@code AVATAR_STORAGE_PATH}
     * @param imageFilePath file path of image to be saved
     * @param name name of image to be saved as
     * @return true if avatar is successfully saved and false if avatar is not saved
     */
    public static boolean saveAvatar(String imageFilePath, String name) {

        String format = getFormat(imageFilePath);

        try {
            File imageFile = new File(imageFilePath);
            File imageFileToWrite = new File(AVATAR_STORAGE_PATH + name);
            FileUtil.createIfMissing(imageFileToWrite);
            ImageIO.write(ImageIO.read(imageFile), format, imageFileToWrite);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * parses file path and returns format
     * @return image format of {@param imageFilePath}
     * assumes filePath either ends with .png or .jpg (see #isJpgOrPng)
     */
    public static String getFormat(String imageFilePath) {
        String format = PNG;

        if (isJpg(imageFilePath)) {
            format = JPG;
        }
        return format;
    }

    /**
     * @return true if filePath is a path to a .jpg or .png
     */
    public static boolean isJpgOrPng(String filePath) {
        return isJpg(filePath) || isPng(filePath);
    }

    /**
     * parses {@code filePath} and checks if its a path to .jpg or .png
     * and checks if the file exists and is not a directory
     * @return true if above criteria is met
     */
    public static boolean isValidImagePath(String filePath) {

        if (!isJpgOrPng(filePath)) {
            return false;
        }

        File f = new File(filePath);
        if (!f.exists() || f.isDirectory()) {
            return false;
        }

        return true;
    }

    private static boolean isJpg(String filePath) {
        return filePath.toLowerCase().endsWith(".jpg");
    }

    private static boolean isPng(String filePath) {
        return filePath.toLowerCase().endsWith(".png");
    }

}
