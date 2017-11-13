package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

//@@author RSJunior37
/**
 * A handler for the {@code InsuranceProfilePanel} of the UI.
 */
public class InsuranceProfilePanelHandle extends NodeHandle<Node> {

    public static final String INSURNACE_PROFILE_PANEL_ID = "#insuranceProfilePanel";
    private static final String OWNER_FIELD_ID = "#owner";
    private static final String INSURED_FIELD_ID = "#insured";
    private static final String BENEFICIARY_FIELD_ID = "#beneficiary";
    private static final String CONTRACT_NAME_ID = "#contractFileName";
    private static final String PREMIUM_FIELD_ID = "#premium";
    private static final String SIGNING_DATE_ID = "#signingDate";
    private static final String EXPIRY_DATE_ID = "#expiryDate";

    private final Label ownerLabel;
    private final Label insuredLabel;
    private final Label beneficiaryLabel;
    private final Label contractFileNameLabel;
    private final Label premiumLabel;
    private final Label signingDateLabel;
    private final Label expiryDateLabel;

    public InsuranceProfilePanelHandle(Node profilePanelNode) {
        super(profilePanelNode);

        this.ownerLabel = getChildNode(OWNER_FIELD_ID);
        this.insuredLabel = getChildNode(INSURED_FIELD_ID);
        this.beneficiaryLabel = getChildNode(BENEFICIARY_FIELD_ID);
        this.contractFileNameLabel = getChildNode(CONTRACT_NAME_ID);
        this.premiumLabel = getChildNode(PREMIUM_FIELD_ID);
        this.signingDateLabel = getChildNode(SIGNING_DATE_ID);
        this.expiryDateLabel = getChildNode(EXPIRY_DATE_ID);

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

    public String getContractPathId() {
        return contractFileNameLabel.getText();
    }

    public String getPremium() {
        return premiumLabel.getText();
    }

    public String getSigningDateId() {
        return signingDateLabel.getText();
    }

    public String getExpiryDateId() {
        return expiryDateLabel.getText();
    }
}
