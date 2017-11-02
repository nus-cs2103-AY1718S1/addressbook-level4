package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * A handler for the {@code ProfilePanel} of the UI.
 */
public class ProfilePanelHandle extends NodeHandle<Node> {

    public static final String PROFILE_ID = "#profilePanel";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String DOB_FIELD_ID = "#dob";
    private static final String GENDER_FIELD_ID = "#gender";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";

    private final Label nameLabel;
    private final Label addressLabel;
    private final Label dobLabel;
    private final Label genderLabel;
    private final Label phoneLabel;
    private final Label emailLabel;

    public ProfilePanelHandle(Node profilePanelNode) {
        super(profilePanelNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.dobLabel = getChildNode(DOB_FIELD_ID);
        this.genderLabel = getChildNode(GENDER_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);

    }
    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getDateOfBirth() {
        return dobLabel.getText();
    }

    public String getGender() {
        return genderLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

}
