package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

//@@author limcel
/**
 * Provides a handle to a person card in the person list panel.
 */
public class ExtendedPersonCardHandle extends NodeHandle<Node> {
    public static final String EXTENDED_PERSON_CARD_ID = "#extendedPersonCardPlaceholder";
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String FORMCLASS_FIELD_ID = "#formClass";
    private static final String GRADES_FIELD_ID = "#grades";
    private static final String POSTALCODE_FIELD_ID = "#postalCode";
    private static final String REMARK_FIELD_ID = "#remark";


    private final Label idLabel;
    private final Label nameLabel;
    private final Label addressLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label formClassLabel;
    private final Label gradesLabel;
    private final Label postalCodeLabel;
    private final Label remarkLabel;


    public ExtendedPersonCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.formClassLabel = getChildNode(FORMCLASS_FIELD_ID);
        this.gradesLabel = getChildNode(GRADES_FIELD_ID);
        this.postalCodeLabel = getChildNode(POSTALCODE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.remarkLabel = getChildNode(REMARK_FIELD_ID);
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

    public String getFormclass() {
        return formClassLabel.getText();
    }

    public String getGrades() {
        return gradesLabel.getText();
    }

    public String getPostalCode() {
        return postalCodeLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getRemark() {
        return remarkLabel.getText();
    }
}
