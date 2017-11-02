package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
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
import seedu.address.commons.events.ui.SwitchToProfilePanelRequestEvent;
import seedu.address.model.insurance.ReadOnlyInsurance;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Profile Panel of the App.
 */
public class ProfilePanel extends UiPart<Region> {

    public static final String DEFAULT_MESSAGE = "Ain't Nobody here but us chickens!";
    public static final String INSURANCE_LIST_HEADER = "List of Insurance Contracts Involved: ";
    public static final String NO_INSURANCE_MESSAGE = "This person is not related to any Insurance Contracts";
    public static final String PERSON_DOES_NOT_EXIST_MESSAGE = "This person does not exist in Lisa.";

    private static final String FXML = "ProfilePanel.fxml";

    public final ObservableList<InsuranceIdLabel> insurance = FXCollections.observableArrayList();

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane profilePanel;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label dob;
    @FXML
    private Label gender;
    @FXML
    private Label email;
    @FXML
    private Label insuranceHeader;
    @FXML
    private ListView<InsuranceIdLabel> insuranceListView;

    public ProfilePanel() {
        super(FXML);
        scrollPane.setFitToWidth(true);
        profilePanel.prefWidthProperty().bind(scrollPane.widthProperty());
        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Updates selected person
     * @param person
     */
    private void loadPersonPage(ReadOnlyPerson person) {
        bindListeners(person);
        if (person.getLifeInsuranceIds().isEmpty()) {
            insuranceHeader.setText(NO_INSURANCE_MESSAGE);
        } else {
            insuranceHeader.setText(INSURANCE_LIST_HEADER);
        }
        ObservableList<ReadOnlyInsurance> insuranceList = person.getLifeInsurances().asObservableList();
        for (ReadOnlyInsurance i : insuranceList) {
            insurance.add(new InsuranceIdLabel(i));
        }
        insuranceListView.setItems(insurance);
        insuranceListView.setCellFactory(insuranceListView -> new ProfilePanel.InsuranceIdListViewCell());


    }

    //@@author OscarWang114
    private void loadPersonPage(StringProperty name) {
        this.name.textProperty().bind(Bindings.convert(name));
        this.address.textProperty().unbind();
        this.address.setText(PERSON_DOES_NOT_EXIST_MESSAGE);
    }
    //@@author

    //@@author RSJunior37
    /**
     * Load default page with empty fields and default message
     */
    private void loadDefaultPage() {
        name.setText(DEFAULT_MESSAGE);
        phone.setText(null);
        address.setText(null);
        dob.setText(null);
        gender.setText(null);
        email.setText(null);
        insuranceHeader.setText(null);
    }
    /**
     * To be called everytime a new person is selected and bind all information for real-time update
     * @param person
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        dob.textProperty().bind(Bindings.convert(person.dobProperty()));
        gender.textProperty().bind(Bindings.convert(person.genderProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code InsuranceIdLabel}.
     */
    class InsuranceIdListViewCell extends ListCell<InsuranceIdLabel> {
        @Override
        protected void updateItem(InsuranceIdLabel insurance, boolean empty) {
            super.updateItem(insurance, empty);

            if (empty || insurance == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(insurance.getRoot());
            }
        }
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        insurance.clear();
        loadPersonPage(event.getNewSelection().person);
        raise(new SwitchToProfilePanelRequestEvent());
    }

    //@@author

    //@@author OscarWang114
    @Subscribe
    private void handlePersonNameClickedEvent(PersonNameClickedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        insurance.clear();
        ReadOnlyPerson person = event.getPerson().orElse(null);
        if (person == null) {
            loadPersonPage(event.getPersonName());
        } else {
            loadPersonPage(event.getPerson().get());
        }
        raise(new SwitchToProfilePanelRequestEvent());
    }
    //@@author
}
