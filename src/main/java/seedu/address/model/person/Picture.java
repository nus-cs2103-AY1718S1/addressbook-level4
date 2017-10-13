package seedu.address.model.person;

/**
 * Represents the url of the picture of the person in the address book.
 */
public class Picture {

    public static final int PIC_WIDTH = 100;
    public static final int PIC_HEIGHT = 100;

    public static final String BASE_URL = System.getProperty("user.dir")
            + "/src/main/resources/contact_images/";

    public static final String PLACEHOLDER_IMAGE = "placeholder_person.png";

    private String pictureUrl;

    public Picture() {
        this.pictureUrl = BASE_URL + PLACEHOLDER_IMAGE;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = BASE_URL + pictureUrl;
    }

}
