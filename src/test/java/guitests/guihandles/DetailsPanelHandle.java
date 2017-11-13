//@@author tshradheya
package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * Provides a handle to a person details in the panel.
 */
public class DetailsPanelHandle extends NodeHandle<Node> {
    private static final String NAME_FIELD_ID = "#detailsName";
    private static final String ADDRESS_FIELD_ID = "#detailsAddress";
    private static final String PHONE_FIELD_ID = "#detailsPhone";
    private static final String EMAIL_FIELD_ID = "#detailsEmail";
    private static final String NICKNAME_FIELD_ID = "#detailsNickname";
    private static final String BIRTHDAY_FIELD_ID = "#detailsBirthday";
    private static final String DISPLAY_PICTURE_FIELD_ID = "#detailsDisplayPicture";
    private static final String TAGS_FIELD_ID = "#detailsTag";

    private final Label nameLabel;
    private final Label addressLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label nicknameLabel;
    private final Label birthdayLabel;
    private final ImageView displayPictureImageView;
    private final List<Label> tagLabels;

    public DetailsPanelHandle(Node cardNode) {
        super(cardNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.nicknameLabel = getChildNode(NICKNAME_FIELD_ID);
        this.birthdayLabel = getChildNode(BIRTHDAY_FIELD_ID);
        this.displayPictureImageView = getChildNode(DISPLAY_PICTURE_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getNickname() {
        return nicknameLabel.getText();
    }

    public String getBirthday() {
        return birthdayLabel.getText();
    }

    public Image getDisplayPictureImageView() {
        return displayPictureImageView.getImage();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}
