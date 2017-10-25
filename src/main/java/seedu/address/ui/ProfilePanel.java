package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Profile Panel of the App.
 */
public class ProfilePanel extends UiPart<Region> {

    public static final String DEFAULT_MESSAGE = "Insurance profile";
    private static final String FXML = "ProfilePanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private ReadOnlyPerson person;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane profilePanel;
    @FXML
    private Label name;
    @FXML
    private Label owner;
    @FXML
    private Label insured;
    @FXML
    private Label beneficiary;
    @FXML
    private Label premium;
    @FXML
    private Label contractPath;
    @FXML
    private Label signingDate;
    @FXML
    private Label expiryDate;

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
        this.person = person;
        bindListeners(person);
    }

    /**
     * Load default page with empty fields and default message
     */
    private void loadDefaultPage() {
        name.setText(DEFAULT_MESSAGE);
        owner.setText("");
        insured.setText("");
        beneficiary.setText("");
        premium.setText("");
        contractPath.setText("");
        signingDate.setText("");
        expiryDate.setText("");
    }

    /**
     * To be called everytime a new person is selected and bind all information for real-time update
     * @param person
     */
    private void bindListeners(ReadOnlyPerson person) {
        if (person.getLifeInsurance() == null) {
            owner.textProperty().unbind();
            insured.textProperty().unbind();
            beneficiary.textProperty().unbind();
            premium.textProperty().unbind();
            contractPath.textProperty().unbind();
            signingDate.textProperty().unbind();
            expiryDate.textProperty().unbind();

            owner.setText("");
            insured.setText("");
            beneficiary.setText("");
            premium.setText("");
            contractPath.setText("");
            signingDate.setText("");
            expiryDate.setText("");
        } else {
            owner.textProperty().bind(Bindings.convert(person.getLifeInsurance().getOwner().nameProperty()));
            insured.textProperty().bind(Bindings.convert(person.getLifeInsurance().getInsured().nameProperty()));
            beneficiary.textProperty().bind(Bindings.convert(person.getLifeInsurance()
                    .getBeneficiary().nameProperty()));
            premium.textProperty().bind(Bindings.convert(person.getLifeInsurance().premiumProperty()));
            /*
            contractPath.textProperty().bind(Bindings.convert(person.getLifeInsurance().contractPathProperty()));
            signingDate.textProperty().bind(Bindings.convert(person.getLifeInsurance().signingDateProperty()));
            expiryDate.textProperty().bind(Bindings.convert(person.getLifeInsurance().expiryDateProperty()));
            */
        }
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }
}
