package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import seedu.address.ui.InsuranceProfile;

/**
 * A handler for the {@code InsuranceProfile} of the UI.
 */
public class InsuranceListPanelHandle extends NodeHandle<Node> {

    public static final String INSURANCE_LIST_VIEW_ID = "#insuranceListView";

    public InsuranceListPanelHandle(ListView<InsuranceProfile> insuranceListPanelNode) {
        super(insuranceListPanelNode);
    }
}
