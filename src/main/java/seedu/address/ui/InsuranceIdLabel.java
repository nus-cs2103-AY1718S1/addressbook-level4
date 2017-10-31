package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.InsurancePanelSelectionChangedEvent;
import seedu.address.model.insurance.ReadOnlyInsurance;

/**
 * To be used in ProfilePanel ListView, displaying list of associated insurance
 */
public class InsuranceIdLabel extends UiPart<Region> {

    private static final String FXML = "InsuranceIdLabel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());


    @FXML
    private Label insuranceId;

    //@@author RSJunior37
    public InsuranceIdLabel(ReadOnlyInsurance insurance) {
        super(FXML);
        insuranceId.textProperty().bind(insurance.signingDateStringProperty());
        insuranceId.setOnMouseClicked(e -> raise(new InsurancePanelSelectionChangedEvent(insurance)));


    }
}
