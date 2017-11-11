package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.InsuranceClickedEvent;
import seedu.address.commons.events.ui.InsurancePanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.SwitchToProfilePanelRequestEvent;
import seedu.address.commons.util.AppUtil.PanelChoice;
import seedu.address.model.insurance.ReadOnlyInsurance;

/**
 * The Insurance Panel of the App.
 */
public class InsuranceListPanel extends UiPart<Region> {

    //@@author OscarWang114
    private static final String FXML = "InsuranceListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private ListView<InsuranceCard> insuranceListView;

    public InsuranceListPanel() {
        super(FXML);
        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    public InsuranceListPanel(ObservableList<ReadOnlyInsurance> insuranceList) {
        super(FXML);
        setConnections(insuranceList);
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
        ObservableList<InsuranceCard> mappedList = EasyBind.map(
                insuranceList, (insurance) -> new InsuranceCard(insurance, insuranceList.indexOf(insurance) + 1));
        insuranceListView.setItems(mappedList);
        insuranceListView.setCellFactory(listView -> new InsuranceListPanel.InsuranceListViewCell());
        setEventHandlerForSelectionChangeEvent();

    }
    //@@author

    //@@author RSJunior37
    private void setEventHandlerForSelectionChangeEvent() {
        insuranceListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in insurance list panel changed to : '" + newValue + "'");
                        raise(new InsurancePanelSelectionChangedEvent(newValue));
                    }
                });
    }
    //@@author

    //@@author Juxarius
    @Subscribe
    private void handleSwitchToProfilePanelRequestEvent(SwitchToProfilePanelRequestEvent event) {
        insuranceListView.getSelectionModel().clearSelection();
    }

    /**
     * Scrolls to the {@code InsuranceCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            insuranceListView.scrollTo(index);
            insuranceListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        if (event.panelChoice == PanelChoice.INSURANCE) {
            logger.info(LogsCenter.getEventHandlingLogMessage(event));
            scrollTo(event.targetIndex);
        }
    }

    @Subscribe
    private void handleInsuranceClickedEvent(InsuranceClickedEvent event) {
        ObservableList<InsuranceCard> insurances = insuranceListView.getItems();
        for (int i = 0; i < insurances.size(); i++) {
            if (insurances.get(i).getInsurance().getId().equals(event.getInsurance().getId())) {
                insuranceListView.scrollTo(i);
                insuranceListView.getSelectionModel().select(i);
                break;
            }
        }
    }
    // weird phenomenon that a filteredList does not contain elements in the original list and cannot be used
    // in the select command
    //@@author

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code InsuranceCard}.
     */
    class InsuranceListViewCell extends ListCell<InsuranceCard> {
        @Override
        protected void updateItem(InsuranceCard insurance, boolean empty) {
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
