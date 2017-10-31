package seedu.address.ui;

import static seedu.address.commons.util.FileUtil.isFileExists;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonNameClickedEvent;
import seedu.address.model.insurance.ReadOnlyInsurance;

//@@author OscarWang114
/**
 * The Profile Panel of the App.
 */
public class InsuranceProfile extends UiPart<Region> {

    private static final String FXML = "InsuranceProfile.fxml";
    private static final String PDFFOLDERPATH = "data/";

    private final Logger logger = LogsCenter.getLogger(this.getClass());


    private ReadOnlyInsurance insurance;
    private File insuranceFile;

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
    private Label contractPath;
    @FXML
    private Label signingDate;
    @FXML
    private Label expiryDate;

    public InsuranceProfile(ReadOnlyInsurance insurance, int displayIndex) {
        super(FXML);
        this.insurance = insurance;
        index.setText(displayIndex + ".");

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
        owner.setOnMouseClicked(e -> raise(new PersonNameClickedEvent(insurance.getOwner())));
        insured.setOnMouseClicked(e -> raise(new PersonNameClickedEvent(insurance.getInsured())));
        beneficiary.setOnMouseClicked(e ->
                raise(new PersonNameClickedEvent(insurance.getBeneficiary())));

        bindListeners(insurance);
        registerAsAnEventHandler(this);
    }

    /**
     *  Enable the link to open contract pdf file and adjusting the text hover highlight
     */
    private void activateLinkToInsuranceFile() {
        contractPath.getStyleClass().add("particular-link");
        contractPath.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Desktop.getDesktop().open(insuranceFile);
                    // HostServices hostServices = getRoot().getHostservices();
                    // hostServices.showDocument(file.getAbsolutePath());
                } catch (IOException ee) {
                    logger.info("File do not exist: " + PDFFOLDERPATH + insurance.getContractPath());
                }
            }
        });
    }

    /**
     * Binds the individual UI elements to observe their respective {@code ReadOnlyInsurance} properties
     * so that they will be notified of any changes.
     * @param insurance
     */
    private void bindListeners(ReadOnlyInsurance insurance) {
        owner.textProperty().bind(Bindings.convert(insurance.getOwner().nameProperty()));
        insured.textProperty().bind(Bindings.convert(insurance.getInsured().nameProperty()));
        beneficiary.textProperty().bind(Bindings.convert(insurance.getBeneficiary().nameProperty()));
        premium.textProperty().bind(Bindings.convert(insurance.premiumStringProperty()));
        contractPath.textProperty().bind(Bindings.convert(insurance.contractPathProperty()));
        signingDate.textProperty().bind(Bindings.convert(insurance.signingDateStringProperty()));
        expiryDate.textProperty().bind(Bindings.convert(insurance.expiryDateStringProperty()));
    }
}
