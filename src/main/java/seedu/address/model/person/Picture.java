package seedu.address.model.person;

public class Picture {

    // Placeholder image url
    public static final String PLACEHOLDER_IMAGE = "placeholder_person.png";
    public static final String BASE_URL = System.getProperty("user.dir") +
            "/src/main/resources/contact_images/";

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
