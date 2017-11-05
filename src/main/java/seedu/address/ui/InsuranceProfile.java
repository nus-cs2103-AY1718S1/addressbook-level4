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


/**
 * The Profile Panel of the App.
 */
public class InsuranceProfile extends UiPart<Region> {

    //@@author OscarWang114
    private static final String FXML = "InsuranceProfile.fxml";
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

    public InsuranceProfile() {
        super(FXML);
        enableNameToProfileLink(insurance);
        registerAsAnEventHandler(this);

    }
    //@@author

    //@@author RSJunior37

    public InsuranceProfile(ReadOnlyInsurance insurance, int displayIndex) {
        super(FXML);
        this.insurance = insurance;
        index.setText(displayIndex + ".");

        // initializeContractFile(insurance);

        enableNameToProfileLink(insurance);

        bindListeners(insurance);
        setPremiumLevel(insurance.getPremium());
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
     * Checks if pdf file exist in project, if not add click event on contract field to add file with filechooser
     * Then add click event on contract field to open up the file
     * @param insurance
     */
    /*
    private void initializeContractFile(ReadOnlyInsurance insurance) {
        insuranceFile =  new File(PDFFOLDERPATH + insurance.getContractPath());
        if (isFileExists(insuranceFile)) {
            activateLinkToInsuranceFile();
        } else {
            contractPath.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    FileChooser.ExtensionFilter extFilter =
                            new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
                    FileChooser chooser = new FileChooser();
                    chooser.getExtensionFilters().add(extFilter);
                    File openedFile = chooser.showOpenDialog(null);
                    activateLinkToInsuranceFile();

                    if (isFileExists(openedFile)) {
                        try {
                            Files.copy(openedFile.toPath(), insuranceFile.toPath());
                        } catch (IOException ex) {
                            logger.info("Unable to open at path: " + openedFile.getAbsolutePath());
                        }
                    }
                }
            });

        }
    }*/

    /**
     *  Enable the link to open contract pdf file and adjusting the text hover highlight
     */
    /*private void activateLinkToInsuranceFile() {
        contractPath.getStyleClass().add("particular-link");
        contractPath.setOnMouseClicked(event -> {
            try {
                Desktop.getDesktop().open(insuranceFile);
            } catch (IOException ee) {
                logger.info("File do not exist: " + PDFFOLDERPATH + insurance.getContractPath());
            }
        });
    }*/
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
        premium.textProperty().bind(Bindings.convert(insurance.premiumStringProperty()));
    }

    //@@author Juxarius
    private void setPremiumLevel(Double premium) {
        if (premium > 500.0) {
            insuranceName.getStyleClass().add("gold-insurance-header");
            index.getStyleClass().add("gold-insurance-header");
        } else if (premium > 100.0) {
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

        //initializeContractFile(insurance);
        enableNameToProfileLink(insurance);
        bindListeners(insurance);
        index.setText(null);
        raise(new SwitchToInsurancePanelRequestEvent());
    }
    //@@author
}
