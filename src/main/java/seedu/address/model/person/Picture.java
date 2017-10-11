package seedu.address.model.person;

public class Picture {

    // Placeholder image url
    public static final String PLACEHOLDER_IMAGE = System.getProperty("user.dir") +
            "/src/main/resources/contact_images/placeholder_person.jpg";

    private String pictureUrl;

    public Picture() {
        this.pictureUrl = PLACEHOLDER_IMAGE;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
