package seedu.address.ui;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.insurance.ReadOnlyInsurance;
import seedu.address.model.person.ReadOnlyPerson;

//@@author OscarWang114
/**
 * The Insurance Panel of the App.
 */
public class InsuranceListPanel extends UiPart<Region> {

    private static final String FXML = "InsuranceListPanel.fxml";

    @FXML
    private ListView<InsuranceProfile> insuranceListView;

    public InsuranceListPanel() {
        super(FXML);
        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    public InsuranceListPanel(ReadOnlyPerson person) {
        super(FXML);
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
     * Custom {@code ListCell} that displays the graphics of a {@code InsuranceProfile}.
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
