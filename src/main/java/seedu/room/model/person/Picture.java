package seedu.room.model.person;

//@@author shitian007
/**
 * Represents the url of the picture of the person in the resident book.
 */
public class Picture {

    public static final int PIC_WIDTH = 100;
    public static final int PIC_HEIGHT = 100;

    public static final String FOLDER_NAME = "contact_images";

    public static final String BASE_URL = System.getProperty("user.dir")
            + "/data/" + FOLDER_NAME + "/";

    public static final String PLACEHOLDER_IMAGE = System.getProperty("user.dir")
            + "/src/main/resources/images/placeholder_person.png";

    public static final String BASE_JAR_URL = System.getProperty("user.dir") + "/";

    public static final String PLACEHOLDER_JAR_URL = "/images/placeholder_person.png";

    private String pictureUrl;
    private String jarPictureUrl;
    private boolean jarResourcePath;

    public Picture() {
        this.pictureUrl = PLACEHOLDER_IMAGE;
        this.jarPictureUrl = PLACEHOLDER_JAR_URL;
        this.jarResourcePath = false;
    }

    /**
     * @return pictureUrl
     */
    public String getPictureUrl() {
        return pictureUrl;
    }

    /**
     * Handles resource path for jar
     */
    public String getJarPictureUrl() {
        return jarPictureUrl;
    }

    /**
     * Sets jar resource path to true
     */
    public void setJarResourcePath() {
        this.jarResourcePath = true;
    }

    /**
     * Checks if image is to be retrieved in jar
     */
    public boolean checkJarResourcePath() {
        return this.jarResourcePath;
    }

    /**
     * Sets name of image which will be appended to contact_images directory
     */
    public void setPictureUrl(String pictureUrl) {
        if (pictureUrl.contains("/")) {
            String[] splitStrings = pictureUrl.split("/");
            String pictureName = splitStrings[splitStrings.length - 1];
            this.pictureUrl = BASE_URL + pictureName;
            this.jarPictureUrl = BASE_JAR_URL + pictureName;
        } else {
            this.pictureUrl = BASE_URL + pictureUrl;
            this.jarPictureUrl = BASE_JAR_URL + pictureUrl;
        }
    }

    /**
     * Resets picture of person to original placeholder
     */
    public void resetPictureUrl() {
        this.pictureUrl = PLACEHOLDER_IMAGE;
        this.jarPictureUrl = PLACEHOLDER_JAR_URL;
    }

}
