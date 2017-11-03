package seedu.address.model.person;
/**
 *add the url of the picture of the person in the adress book
 **///@@author wei renkai
public class Picture {

    public static final int PIC_WIDTH = 100;
    public static final int PIC_HEIGHT = 100;

    public static final String BASE_URL = System.getProperty("user.dir") + "/src/main/resources/contact_images/";

    public static final String PLACEHOLDER_IMAG = "test1.png";

    private String pictureUrl;

    public Picture() {
        this.pictureUrl = BASE_URL + PLACEHOLDER_IMAG;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = BASE_URL + pictureUrl;
    }
}
