package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import seedu.address.commons.util.FileUtil;

//@@author JunQuann
/**
 */
public class ImageFileStorage {

    private String dirPath;

    public ImageFileStorage(String dirPath) {
        this.dirPath = dirPath;
    }

    public String getImageFilePath(String imageName) {
        return dirPath + imageName + ".png";
    }

    /**
     * Copy the image {@code currentImagePath} to the designated ImageFileStorage
     * folder with image name {@code imageName}
     */
    public void copyImage(String currentImagePath, String imageName) throws IOException {
        File currentImage = new File(currentImagePath);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(currentImage));
        createImageDir();
        String newImagePath = getImageFilePath(imageName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newImagePath));

        int data;

        while ((data = bis.read()) != -1) {
            bos.write(data);
        }

        bis.close();
        bos.close();
    }

    /**
     * Create the image file storage directory if it does not exists.
     */
    public void createImageDir() throws IOException {
        requireNonNull(dirPath);
        FileUtil.createDirs(new File(dirPath));
    }
}

