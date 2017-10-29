package seedu.address.model.person;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Represents a Photo in the address book.
 */

public class Photo {

    public static final String URL_VALIDATION = "The filepath URL does not exist.";
    private static final String DEFAULT_PHOTOURL = "";
    private static final String DEFAULT_FILEPATH = "";
    private String filepath;
    private String url;
    public Photo(String filepath) throws IllegalArgumentException {
        //this is to setup the default photo for contacts after it is added.
        if (filepath.equals(DEFAULT_FILEPATH)) {
            this.filepath = DEFAULT_PHOTOURL;
            this.url = DEFAULT_FILEPATH;
        } else {
            File file = new File(filepath);
            if (isValidFilePath(file)) {
                this.filepath = filepath;
            } else {
                 throw new IllegalArgumentException(URL_VALIDATION);
            }
        }
    }

    public boolean isValidFilePath(File file) {
        return file.exists();
    }
    //the filepath of the image
    public String getFilePath() {
        return filepath;
    }
    //url of the image that is parsed into Image class
    public String getUrl() {
        return this.url;
    }
    /** It is guaranteed that the new filepath exists inside the resources folder */
    public void resetFilePath(String filepath) { this.filepath = filepath;}
    
}
