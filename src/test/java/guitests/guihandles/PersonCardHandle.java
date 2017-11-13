package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String FAVORITE_FIELD_ID = "#favorite";
    private static final String BIRTHDAY_FIELD_ID = "#birthday";
    private static final String PROFILEPAGE_FIELD_ID = "#profile";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label addressLabel;
    private final Label profileLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label birthdayLabel;
    private final List<Label> tagLabels;

    private final ImageView favoriteLabel;

    public PersonCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.profileLabel = getChildNode(PROFILEPAGE_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.birthdayLabel = getChildNode(BIRTHDAY_FIELD_ID);
        this.favoriteLabel = getChildNode(FAVORITE_FIELD_ID);
        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    //@@author quangtdn
    public String getProfilePage() {
        return profileLabel.getText();
    }
    //@@author

    public String getPhone() {
        return phoneLabel.getText();
    }

    public boolean getFavorite() {
        return (favoriteLabel != null);
    }

    public String getEmail() {
        return emailLabel.getText();
    }
    //@@author yanji1221
    public String getBirthday() {
        return birthdayLabel.getText();
    }
    //@@author
    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}
