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
    private String backupFilePath;
    private boolean isBackUp = false;
    public Photo(String filepath) throws IllegalArgumentException {
        //this is to setup the default photo for contacts after it is added.
        if (filepath.equals(DEFAULT_FILEPATH)) {
            this.filepath = DEFAULT_PHOTOURL;
            this.url = DEFAULT_FILEPATH;
        } else {
            File file = new File(filepath);
            //this.filepath = filepath;
            if (isValidFilePath(file)) {
                this.filepath = filepath;
               /* try {
                    String localUrl = file.toURI().toURL().toString();
                    this.url = localUrl;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }*/
            } else {
                 throw new IllegalArgumentException(URL_VALIDATION);
            }
        }
        //isBackUp = false;
    }

    public boolean isValidFilePath(File file) {
        return file.exists();
    }
    
    public boolean isInStorage() {
        File targetFile = new File(filepath);
        if(targetFile!=null) {
            return true;
        } else {
            return false;
        }
    } 
    //the filepath of the image
    public String getFilePath() {
        return filepath;
    }
    //url of the image that is parsed into Image class
    public String getUrl() {
        return this.url;
    }
    
    public void resetFilePath(String filepath) {
        this.filepath = filepath; 
    }
    
}
