package seedu.address.model.person;

import java.io.File;
import java.net.MalformedURLException;

import javafx.scene.image.Image;

/**
 * Represents a Photo in the address book.
 */

public class Photo {

    public static final String URL_VALIDATION = "The filepath URL does not exist.";
    private static final String DEFAULT_PHOTOURL = "";
    private String filepath;
    public Photo(String filepath) throws IllegalArgumentException {
        //this is to setup the default photo for contacts after it is added.
        if (filepath.equals("")) {
            this.filepath = DEFAULT_PHOTOURL;
            //this.image = new Image(getClass().getResource(this.filepath).toExternalForm());
        } else {
            File file = new File(filepath);
            if (isValidFilePath(file)) {
                try {
                    String localUrl = file.toURI().toURL().toString();
                     this.filepath = localUrl;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else {
                throw new IllegalArgumentException(URL_VALIDATION);
            }
        }
    }

    public static boolean isValidFilePath(File file) {
        return file.exists();
    }

    public String getFilePath() {
        return filepath;
    }

   // public Image getImage() {
     //   return image;
    //}

}
