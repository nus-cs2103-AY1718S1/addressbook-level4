package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * A handler for the {@code InsuranceProfile} of the UI.
 */
public class InsuranceListPanelHandle extends NodeHandle<Node> {

    public static final String INSURANCE_LIST_VIEW_ID = "#insuranceListView";
    /*
    private static final String OWNER_FIELD_ID = "#owner";
    private static final String INSURED_FIELD_ID = "#insured";
    private static final String BENEFICIARY_FIELD_ID = "#beneficiary";
    private static final String PREMIUM_FIELD_ID = "#premium";
    //TODO: Complete the other fields

    private final Label ownerLabel;
    private final Label insuredLabel;
    private final Label beneficiaryLabel;
    private final Label premiumLabel;

    public InsuranceListPanelHandle(Node profilePanelNode) {
        super(profilePanelNode);

        this.ownerLabel = getChildNode(OWNER_FIELD_ID);
        this.insuredLabel = getChildNode(INSURED_FIELD_ID);
        this.beneficiaryLabel = getChildNode(BENEFICIARY_FIELD_ID);
        this.premiumLabel = getChildNode(PREMIUM_FIELD_ID);

    }

    public String getOwner() {
        return ownerLabel.getText();
    }

    public String getInsured() {
        return insuredLabel.getText();
    }

    public String getBeneficiary() {
        return beneficiaryLabel.getText();
    }

    public String getPremium() {
        return premiumLabel.getText();
    }
    */

    public InsuranceListPanelHandle(Node profilePanelNode) {
        super(profilePanelNode);
    }
}
