package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

// @@author johnweikangong
/**
 * Provides a handle for {@code PersonDetailsPanel}.
 */
public class PersonDetailsPanelHandle extends NodeHandle<Node> {
    public static final String PERSON_DETAILS_VIEW_ID = "#personDetailsPanel";
    private static final String NAME_ID = "#name";
    private static final String PHONE_ID = "#phone";
    private static final String BIRTHDAY_ID = "#birthday";
    private static final String EMAIL_ID = "#email";
    private static final String ADDRESS_ID = "#address";
    private static final String POSTALCODE_ID = "#postalCode";

    private final Label name;
    private final Label phone;
    private final Label birthday;
    private final Label email;
    private final Label address;
    private final Label postalCode;

    public PersonDetailsPanelHandle(Node personDetailsPanelNode) {
        super(personDetailsPanelNode);

        this.name = getChildNode(NAME_ID);
        this.phone = getChildNode(PHONE_ID);
        this.birthday = getChildNode(BIRTHDAY_ID);
        this.email = getChildNode(EMAIL_ID);
        this.address = getChildNode(ADDRESS_ID);
        this.postalCode = getChildNode(POSTALCODE_ID);
    }

    public PersonDetailsPanelHandle getPersonDetailsPanelHandle() {
        return this;
    }

    public String getName() {
        return name.getText();
    }

    public String getPhone() {
        return phone.getText();
    }

    public String getBirthday() {
        return birthday.getText();
    }

    public String getEmail() {
        return email.getText();
    }

    public String getAddress() {
        return address.getText();
    }

    public String getPostalCode() {
        return postalCode.getText();
    }
}
