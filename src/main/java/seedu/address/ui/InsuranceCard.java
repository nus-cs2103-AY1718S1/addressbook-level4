package seedu.address.ui;

import java.io.File;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.InsurancePanelSelectionChangedEvent;
import seedu.address.commons.events.ui.PersonNameClickedEvent;
import seedu.address.commons.events.ui.SwitchToInsurancePanelRequestEvent;
import seedu.address.model.insurance.ReadOnlyInsurance;

//@@author OscarWang114
/**
 * An UI component that displays information of a {@code LifeInsurance}.
 */
public class InsuranceCard extends UiPart<Region> {


    private static final String FXML = "InsuranceCard.fxml";
    private static final Double GOLD_INSURANCE_PREMIUM = 2500.0;
    private static final Double SILVER_INSURANCE_PREMIUM = 1500.0;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private File insuranceFile;
    private ReadOnlyInsurance insurance;

    @FXML
    private Label index;
    @FXML
    private Label owner;
    @FXML
    private Label insured;
    @FXML
    private Label beneficiary;
    @FXML
    private Label premium;
    @FXML
    private Label insuranceName;

    public InsuranceCard() {
        super(FXML);
        enableNameToProfileLink(insurance);
        registerAsAnEventHandler(this);

    }
    //@@author

    //@@author RSJunior37

    public InsuranceCard(ReadOnlyInsurance insurance, int displayIndex) {
        super(FXML);
        this.insurance = insurance;
        index.setText(displayIndex + ".");
        enableNameToProfileLink(insurance);

        bindListeners(insurance);
        setPremiumLevel(insurance.getPremium().toDouble());
    }

    public ReadOnlyInsurance getInsurance() {
        return insurance;
    }


    /**
     * Listen for click event on person names to be displayed as profile
     * @param insurance
     */
    private void enableNameToProfileLink(ReadOnlyInsurance insurance) {
        owner.setOnMouseClicked(e -> raise(new PersonNameClickedEvent(insurance.getOwner())));
        insured.setOnMouseClicked(e -> raise(new PersonNameClickedEvent(insurance.getInsured())));
        beneficiary.setOnMouseClicked(e -> raise(new PersonNameClickedEvent(insurance.getBeneficiary())));
    }

    /**
     * Binds the individual UI elements to observe their respective {@code ReadOnlyInsurance} properties
     * so that they will be notified of any changes.
     * @param insurance
     */
    private void bindListeners(ReadOnlyInsurance insurance) {
        insuranceName.textProperty().bind(Bindings.convert(insurance.insuranceNameProperty()));
        owner.textProperty().bind(Bindings.convert(insurance.getOwner().nameProperty()));
        insured.textProperty().bind(Bindings.convert(insurance.getInsured().nameProperty()));
        beneficiary.textProperty().bind(Bindings.convert(insurance.getBeneficiary().nameProperty()));
        premium.textProperty().bind(Bindings.convert(insurance.premiumProperty()));
    }

    //@@author Juxarius
    private void setPremiumLevel(Double premium) {
        if (premium >= GOLD_INSURANCE_PREMIUM) {
            insuranceName.getStyleClass().add("gold-insurance-header");
            index.getStyleClass().add("gold-insurance-header");
        } else if (premium >= SILVER_INSURANCE_PREMIUM) {
            insuranceName.getStyleClass().add("silver-insurance-header");
            index.getStyleClass().add("silver-insurance-header");
        } else {
            insuranceName.getStyleClass().add("normal-insurance-header");
            index.getStyleClass().add("normal-insurance-header");
        }
    }
    //@@author


    //@@author RSJunior37
    @Subscribe
    private void handleInsurancePanelSelectionChangedEvent(InsurancePanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        insurance = event.getInsurance();
        enableNameToProfileLink(insurance);
        bindListeners(insurance);
        raise(new SwitchToInsurancePanelRequestEvent());
    }
    //@@author
}
