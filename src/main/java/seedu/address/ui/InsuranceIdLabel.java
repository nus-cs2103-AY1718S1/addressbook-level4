package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.InsuranceClickedEvent;
import seedu.address.model.insurance.ReadOnlyInsurance;

//@@author RSJunior37
/**
 * To be used in ProfilePanel ListView, displaying list of associated insurance
 */
public class InsuranceIdLabel extends UiPart<Region> {

    private static final String FXML = "InsuranceIdLabel.fxml";
    private static final Double GOLD_INSURANCE_PREMIUM = 2500.0;
    private static final Double SILVER_INSURANCE_PREMIUM = 1500.0;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private Label insuranceId;

    public InsuranceIdLabel(ReadOnlyInsurance insurance) {
        super(FXML);
        insuranceId.textProperty().bind(Bindings.convert(insurance.insuranceNameProperty()));
        setPremiumLevel(insurance.getPremium().toDouble());
        insuranceId.setOnMouseClicked(e -> raise(new InsuranceClickedEvent(insurance)));
    }

    //@@author Juxarius
    private void setPremiumLevel(Double premium) {
        insuranceId.getStyleClass().clear();
        if (premium >= GOLD_INSURANCE_PREMIUM) {
            insuranceId.getStyleClass().add("gold-insurance-header");
        } else if (premium >= SILVER_INSURANCE_PREMIUM) {
            insuranceId.getStyleClass().add("silver-insurance-header");
        } else {
            insuranceId.getStyleClass().add("normal-insurance-header");
        }
    }
    //@@author
}
