package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonNameClickedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.insurance.ReadOnlyInsurance;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Profile Panel of the App.
 */
public class InsuranceListPanel extends UiPart<Region> {

    private static final String FXML = "InsuranceListPanel.fxml";
    public static final String INSURANCE_LIST_PANEL_ID = "#insuranceListPanel";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private ListView<InsuranceProfile> insuranceListView;

    public InsuranceListPanel() {
        super(FXML);
        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    public InsuranceListPanel(ReadOnlyPerson person) {
        super(FXML);
        //if(person.getLifeInsurances)
        setConnections(person.getLifeInsurances().asObservableList());
        registerAsAnEventHandler(this);
    }
    /**
     * Load default page with nothing
     */
    private void loadDefaultPage() {

    }

    /**
     * To be called everytime a new person is selected and bind the list of insurance of that person
     * @param insuranceList
     */
    public void setConnections(ObservableList<ReadOnlyInsurance> insuranceList) {
        ObservableList<InsuranceProfile> mappedList = EasyBind.map(
                insuranceList, (insurance) -> new InsuranceProfile(insurance, insuranceList.indexOf(insurance) + 1));
        insuranceListView.setItems(mappedList);
        insuranceListView.setCellFactory(listView -> new InsuranceListPanel.InsuranceListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class InsuranceListViewCell extends ListCell<InsuranceProfile> {

        @Override
        protected void updateItem(InsuranceProfile insurance, boolean empty) {
            super.updateItem(insurance, empty);

            if (empty || insurance == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(insurance.getRoot());
            }
        }
    }
}
