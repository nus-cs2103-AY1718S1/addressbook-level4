package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

//@@author JunQuann
/**
 * Provides a handle to the person description panel.
 */
public class PersonDescriptionHandle extends NodeHandle<Node> {
    private static final String DP_FIELD_ID = "#displayPic";
    private static final String NAME_FIELD_ID = "#name";
    private static final String MOBILE_FIELD_ID = "#mobile";
    private static final String BIRTHDAY_FIELD_ID = "#birthday";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String INSTAGRAM_FIELD_ID = "#instagram";
    private static final String TWITTER_FIELD_ID = "#twitter";

    private final Circle displayPicPanel;
    private final Label nameLabel;
    private final Label mobileLabel;
    private final Label birthdayLabel;
    private final Label emailLabel;
    private final Label addressLabel;
    private final Label instagramLabel;
    private final Label twitterLabel;

    public PersonDescriptionHandle(Node personDescriptionPanelNode) {
        super(personDescriptionPanelNode);

        this.displayPicPanel = getChildNode(DP_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.mobileLabel = getChildNode(MOBILE_FIELD_ID);
        this.birthdayLabel = getChildNode(BIRTHDAY_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.instagramLabel = getChildNode(INSTAGRAM_FIELD_ID);
        this.twitterLabel = getChildNode(TWITTER_FIELD_ID);
    }

    public Image getDisplayPic() {
        ImagePattern displayPicImage = (ImagePattern) displayPicPanel.getFill();
        return displayPicImage.getImage();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getMobile() {
        return mobileLabel.getText();
    }

    public String getBirthday() {
        return birthdayLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getInstagram() {
        return instagramLabel.getText();
    }

    public String getTwitter() {
        return twitterLabel.getText();
    }


}
