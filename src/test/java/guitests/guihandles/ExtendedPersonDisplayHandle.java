package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

//@@author jacoblipech

/**
 * Provides a handle for the information shown in the Extended Person Display.
 */
public class ExtendedPersonDisplayHandle extends NodeHandle<Node> {

    public static final String EXTENDED_PERSON_VIEW_ID = "#extendedPersonDisplayPlaceholder";

    private static final String NAME_FIELD_ID = "#name";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String BIRTHDAY_FIELD_ID = "#birthday";

    private final Label nameLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label addressLabel;
    private final Label birthdayLabel;

    public ExtendedPersonDisplayHandle(Node cardNode) {
        super(cardNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.birthdayLabel = getChildNode(BIRTHDAY_FIELD_ID);

    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getBirthday() {
        return birthdayLabel.getText();
    }

}
