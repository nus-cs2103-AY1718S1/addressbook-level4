package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * A UI component that displays information on the browser display type
 */
public class BrowserSelectorCard extends UiPart<Region> {

    private static final String FXML = "BrowserSelectorCard.fxml";

    private final String imageString;

    @FXML
    private ImageView browserCardImage;

    @FXML
    private Label browserCardText;

    public BrowserSelectorCard(String imageName) {
        super(FXML);
        this.imageString = imageName;
        browserCardText.textProperty().setValue(imageName);
        fillImage(imageName);
    }

    /**
     * Fills the image on the browser card
     */
    private void fillImage(String imageName) {
        if (imageName.equals("linkedin")) {
            browserCardImage.setImage(new Image("/images/linkedin.png"));
        } else if (imageName.equals("facebook")) {
            browserCardImage.setImage(new Image("/images/facebook.png"));
        } else if (imageName.equals("meeting")) {
            browserCardImage.setImage(new Image("/images/meeting.png"));
        }
    }

    public String getImageString() {
        return imageString;
    }
}
