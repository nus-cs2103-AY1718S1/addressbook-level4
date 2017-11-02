package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String POSTAL_CODE_FIELD_ID = "#postalCode";
    private static final String DEBT_FIELD_ID = "#debt";
    private static final String INTEREST_FIELD_ID = "#interest";
    private static final String DATE_BORROW_FIELD_ID = "#dateBorrow";
    private static final String DEADLINE_FIELD_ID = "#deadline";
    private static final String DATE_REPAID_FIELD_ID = "#dateRepaid";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label addressLabel;
    private final Label emailLabel;
    private final Label postalCodeLabel;
    private final Label debtLabel;
    private final Label interestLabel;
    private final Label dateBorrowLabel;
    private final Label deadlineLabel;
    private final Label dateRepaidLabel;
    private final List<Label> tagLabels;

    public PersonCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.postalCodeLabel = getChildNode(POSTAL_CODE_FIELD_ID);
        this.debtLabel = getChildNode(DEBT_FIELD_ID);
        this.interestLabel = getChildNode(INTEREST_FIELD_ID);
        this.dateBorrowLabel = getChildNode(DATE_BORROW_FIELD_ID);
        this.deadlineLabel = getChildNode(DEADLINE_FIELD_ID);
        this.dateRepaidLabel = getChildNode(DATE_REPAID_FIELD_ID);

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

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getPostalCode() {
        return postalCodeLabel.getText();
    }

    public String getDebt() {
        return debtLabel.getText();
    }

    public String getInterest() {
        return interestLabel.getText();
    }

    public String getDateBorrow() {
        return dateBorrowLabel.getText();
    }

    public String getDeadline() {
        return deadlineLabel.getText();
    }

    public String getDateRepaid() {
        return dateRepaidLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}
