package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * A handler for the {@code PersonDetailsPanel} of the UI.
 */
public class PersonDetailsPanelHandle extends NodeHandle<Node> {

    public static final String NAME_ID = "#nameLabel";
    public static final String PHONE_ID = "#phoneLabel";
    public static final String EMAIL_ID = "#emailLabel";
    public static final String ADDRESS_ID = "#addressLabel";

    public final Label nameLb;
    public final Label phoneLb;
    public final Label emailLb;
    public final Label addressLb;

    public PersonDetailsPanelHandle(Node cardNode) {
        super(cardNode);
        this.nameLb = getChildNode(NAME_ID);
        this.phoneLb = getChildNode(PHONE_ID);
        this.emailLb = getChildNode(EMAIL_ID);
        this.addressLb = getChildNode(ADDRESS_ID);
    }

    public String getNameId() {
        return nameLb.getText();
    }

    public String getPhoneId() {
        return phoneLb.getText();
    }

    public String getEmailId() {
        return emailLb.getText();
    }

    public String getAddressId() {
        return addressLb.getText();
    }
}
