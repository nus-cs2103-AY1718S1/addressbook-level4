package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.ui.InsuranceCard;

/**
 * A handler for the {@code InsuranceListPanel} of the UI.
 */
public class InsuranceListPanelHandle extends NodeHandle<Node> {

    public static final String INSURANCE_LIST_VIEW_ID = "#insuranceListView";

    public InsuranceListPanelHandle(ListView<InsuranceCard> insuranceListPanelNode) {
        super(insuranceListPanelNode);
    }
}
