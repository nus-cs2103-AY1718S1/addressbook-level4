package seedu.address.ui;

import static seedu.address.commons.util.FileUtil.isFileExists;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.InsurancePanelSelectionChangedEvent;
import seedu.address.commons.events.ui.PersonNameClickedEvent;
import seedu.address.commons.events.ui.SwitchToInsurancePanelRequestEvent;
import seedu.address.model.insurance.ReadOnlyInsurance;

//@@author Juxarius

/**
 * Profile panel for insurance when the respective insurance is selected
 */
public class InsuranceProfilePanel extends UiPart<Region> {
    private static final String FXML = "InsuranceProfilePanel.fxml";
    private static final String PDF_FOLDER_PATH = "data/";
    private static final String PDF_EXTENSION = ".pdf";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private File insuranceFile;
    private ReadOnlyInsurance insurance;

    @FXML
    private ScrollPane insuranceScrollPane;
    @FXML
    private AnchorPane insuranceProfilePanel;
    @FXML
    private Label insuranceName;
    @FXML
    private Label owner;
    @FXML
    private Label insured;
    @FXML
    private Label beneficiary;
    @FXML
    private Label premium;
    @FXML
    private Label signingDate;
    @FXML
    private Label expiryDate;
    @FXML
    private Label contractFileName;

    public InsuranceProfilePanel() {
        super(FXML);
        insuranceScrollPane.setFitToWidth(true);
        insuranceProfilePanel.prefWidthProperty().bind(insuranceScrollPane.widthProperty());
        insuranceProfilePanel.prefHeightProperty().bind(insuranceScrollPane.heightProperty());
        setAllToNull();
        registerAsAnEventHandler(this);
    }

    //@@author RSJunior37

    public InsuranceProfilePanel(ReadOnlyInsurance insurance) {
        super(FXML);
        this.insurance = insurance;

        initializeContractFile(insurance);
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

    private void setAllToNull() {
        owner.setText(null);
        insured.setText(null);
        beneficiary.setText(null);
        contractFileName.setText(null);
        premium.setText(null);
        signingDate.setText(null);
        expiryDate.setText(null);
    }

    /**
     * Checks if pdf file exist in project, if not add click event on contract field to add file with filechooser
     * Then add click event on contract field to open up the file
     * @param insurance
     */
    private void initializeContractFile(ReadOnlyInsurance insurance) {
        insuranceFile =  new File(PDF_FOLDER_PATH + insurance.getContractFileName()
                + (insurance.getContractFileName().toString().endsWith(PDF_EXTENSION) ? "" : PDF_EXTENSION));

        if (isFileExists(insuranceFile)) {
            activateLinkToInsuranceFile();
        } else {
            contractFileName.getStyleClass().clear();
            contractFileName.getStyleClass().add("missing-file");
            contractFileName.setOnMouseClicked(event -> {
                FileChooser.ExtensionFilter extensionFilter =
                        new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
                FileChooser chooser = new FileChooser();
                chooser.getExtensionFilters().add(extensionFilter);
                File openedFile = chooser.showOpenDialog(null);

                if (isFileExists(openedFile)) {
                    try {
                        Files.copy(openedFile.toPath(), insuranceFile.toPath());
                        if (isFileExists(insuranceFile)) {
                            activateLinkToInsuranceFile();
                        }
                    } catch (IOException ex) {
                        logger.info("Unable to open file at path: " + openedFile.getAbsolutePath());
                    }
                }
            });
        }
    }

    /**
     *  Enable the link to open contract pdf file and adjusting the text hover highlight
     */
    private void activateLinkToInsuranceFile() {
        contractFileName.getStyleClass().clear();
        contractFileName.getStyleClass().add("valid-file");
        contractFileName.setOnMouseClicked(event -> {
            try {
                Desktop.getDesktop().open(insuranceFile);
            } catch (IOException ee) {
                logger.info("File do not exist: " + PDF_FOLDER_PATH + insurance.getContractFileName());
            }
        });
    }
    //@@author

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
        contractFileName.textProperty().bind(Bindings.convert(insurance.contractFileNameProperty()));
        premium.textProperty().bind(Bindings.convert(insurance.premiumProperty()));
        signingDate.textProperty().bind(Bindings.convert(insurance.signingDateStringProperty()));
        expiryDate.textProperty().bind(Bindings.convert(insurance.expiryDateStringProperty()));
    }

    //@@author Juxarius
    private void setPremiumLevel(Double premium) {
        insuranceName.getStyleClass().clear();
        insuranceName.getStyleClass().add("insurance-profile-header");
        if (premium > 500.0) {
            insuranceName.getStyleClass().add("gold-insurance-header");
        } else if (premium > 100.0) {
            insuranceName.getStyleClass().add("silver-insurance-header");
        } else {
            insuranceName.getStyleClass().add("normal-insurance-header");
        }
    }

    @Subscribe
    private void handleSwitchToInsurancePanelRequestEvent(InsurancePanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        enableNameToProfileLink(event.getInsurance());
        initializeContractFile(event.getInsurance());
        bindListeners(event.getInsurance());
        setPremiumLevel(event.getInsurance().getPremium().toDouble());
        raise(new SwitchToInsurancePanelRequestEvent());
    }
    //@@author

}
