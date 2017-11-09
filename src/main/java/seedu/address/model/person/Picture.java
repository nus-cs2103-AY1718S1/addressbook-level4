package seedu.address.model.person;
/**
 *add the url of the picture of the person in the adress book
 **///@@author renkai91
public class Picture {

    public static final int PIC_WIDTH = 100;
    public static final int PIC_HEIGHT = 100;

    public static final String BASE_URL = System.getProperty("user.dir") + "/data/contact_images/";

    public static final String PLACEHOLDER_IMAGE = System.getProperty("user.dir") + "/src/main/resources/test1.png";

    public static final String BASE_JAR_URL = System.getProperty("user.dir");

    public static final String PLACEHOLDER_JAR_URL = "/images/test1.png";

    private String pictureUrl;
    private String jarPictureUrl;
    private boolean jarResourcePath;

    public Picture() {
        this.pictureUrl = PLACEHOLDER_IMAGE;
        this.jarPictureUrl = PLACEHOLDER_JAR_URL;
        this.jarResourcePath = false;

    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getJarPictureUrl() {
        return jarPictureUrl;
    }
    public void setJarResourcePath() {
        this.jarResourcePath = true;
    }
    public boolean checkJarResourcePath() {
        return this.jarResourcePath;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = BASE_URL + pictureUrl;
        this.jarPictureUrl = BASE_JAR_URL + pictureUrl;
    }
}
//@@author
