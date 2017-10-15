package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

//@@author khooroko
/**
 * A handler for the {@code InfoPanel} of the UI.
 */
public class InfoPanelHandle extends NodeHandle<Node> {
    public static final String INFO_PANEL_ID = "#infoPanelPlaceholder";

    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String POSTAL_CODE_FIELD_ID = "#postalCode";
    private static final String CLUSTER_FIELD_ID = "#cluster";
    private static final String DEBT_FIELD_ID = "#debt";
    private static final String DATE_BORROW_FIELD_ID = "#dateBorrow";
    private static final String DATE_REPAID_FIELD_ID = "#dateRepaid";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String ADDRESS_FIELD_FIELD_ID = "#addressField";
    private static final String PHONE_FIELD_FIELD_ID = "#phoneField";
    private static final String EMAIL_FIELD_FIELD_ID = "#emailField";
    private static final String POSTAL_CODE_FIELD_FIELD_ID = "#postalCodeField";
    private static final String CLUSTER_FIELD_FIELD_ID = "#clusterField";
    private static final String DEBT_FIELD_FIELD_ID = "#debtField";
    private static final String DATE_BORROW_FIELD_FIELD_ID = "#dateBorrowField";
    private static final String DATE_REPAID_FIELD_FIELD_ID = "#dateRepaidField";

    private final Label nameLabel;
    private final Label addressLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label postalCodeLabel;
    private final Label clusterLabel;
    private final Label debtLabel;
    private final Label dateBorrowLabel;
    private final Label dateRepaidLabel;
    private final List<Label> tagLabels;
    private final Text addressText;
    private final Text phoneText;
    private final Text emailText;
    private final Text postalCodeText;
    private final Text clusterText;
    private final Text debtText;
    private final Text dateBorrowText;
    private final Text dateRepaidText;

    private String lastRememberedName;
    private String lastRememberedAddress;
    private String lastRememberedPhone;
    private String lastRememberedEmail;
    private String lastRememberedPostalCode;
    private String lastRememberedCluster;
    private String lastRememberedDebt;
    private String lastRememberedDateBorrow;
    private String lastRememberedDateRepaid;
    private List<String> lastRememberedTags;

    public InfoPanelHandle(Node infoPanelNode) {
        super(infoPanelNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.postalCodeLabel = getChildNode(POSTAL_CODE_FIELD_ID);
        this.clusterLabel = getChildNode(CLUSTER_FIELD_ID);
        this.debtLabel = getChildNode(DEBT_FIELD_ID);
        this.dateBorrowLabel = getChildNode(DATE_BORROW_FIELD_ID);
        this.dateRepaidLabel = getChildNode(DATE_REPAID_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());

        this.addressText = getChildNode(ADDRESS_FIELD_FIELD_ID);
        this.phoneText = getChildNode(PHONE_FIELD_FIELD_ID);
        this.emailText = getChildNode(EMAIL_FIELD_FIELD_ID);
        this.postalCodeText = getChildNode(POSTAL_CODE_FIELD_FIELD_ID);
        this.clusterText = getChildNode(CLUSTER_FIELD_FIELD_ID);
        this.debtText = getChildNode(DEBT_FIELD_FIELD_ID);
        this.dateBorrowText = getChildNode(DATE_BORROW_FIELD_FIELD_ID);
        this.dateRepaidText = getChildNode(DATE_REPAID_FIELD_FIELD_ID);
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

    public String getDebt() {
        return debtLabel.getText();
    }

    public String getDateBorrow() {
        return dateBorrowLabel.getText();
    }

    public String getDateRepaid() {
        return dateRepaidLabel.getText();
    }

    public String getPostalCode() {
        return  postalCodeLabel.getText();
    }

    public String getCluster() {
        return clusterLabel.getText();
    }

    public String getAddressField() {
        return addressText.getText();
    }

    public String getPhoneField() {
        return phoneText.getText();
    }

    public String getEmailField() {
        return emailText.getText();
    }

    public String getPostalCodeField() {
        return postalCodeText.getText();
    }

    public String getClusterField() {
        return clusterText.getText();
    }

    public String getDebtField() {
        return debtText.getText();
    }

    public String getDateBorrowField () {
        return dateBorrowText.getText();
    }

    public String getDateRepaidField () {
        return dateRepaidText.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Remember the details of the person that was last selected
     */
    public void rememberSelectedPersonDetails() {
        lastRememberedAddress = getAddress();
        lastRememberedDebt = getDebt();
        lastRememberedEmail = getEmail();
        lastRememberedName = getName();
        lastRememberedPhone = getPhone();
        lastRememberedPostalCode = getPostalCode();
        lastRememberedCluster = getCluster();
        lastRememberedTags = getTags();
        lastRememberedDateBorrow = getDateBorrow();
        lastRememberedDateRepaid = getDateRepaid();
    }

    /**
     * Returns true if the selected {@code Person} is different from the value remembered by the most recent
     * {@code rememberSelectedPersonDetails()} call.
     */
    public boolean isSelectedPersonChanged() {
        return !getName().equals(lastRememberedName)
                || !getAddress().equals(lastRememberedAddress)
                || !getPhone().equals(lastRememberedPhone)
                || !getDebt().equals(lastRememberedDebt)
                || !getEmail().equals(lastRememberedEmail)
                || !getPostalCode().equals(lastRememberedPostalCode)
                || !getCluster().equals(lastRememberedCluster)
                || !getDateBorrow().equals(lastRememberedDateBorrow)
                || !getDateRepaid().equals(lastRememberedDateRepaid)
                || !getTags().equals(lastRememberedTags);
    }

}
