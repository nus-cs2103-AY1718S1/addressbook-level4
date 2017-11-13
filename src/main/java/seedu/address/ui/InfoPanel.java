package seedu.address.ui;

import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ChangeInternalListEvent;
import seedu.address.commons.events.ui.DeselectionEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.Logic;
import seedu.address.model.person.ReadOnlyPerson;

//@@author khooroko
/**
 * The Info Panel of the App that displays full information of a {@code Person}.
 */
public class InfoPanel extends UiPart<Region> {

    private static final String FXML = "InfoPanel.fxml";
    private static final String MESSAGE_INFO_ADDRESS_FIELD = "Address: ";
    private static final String MESSAGE_INFO_HANDPHONE_FIELD = "HP: ";
    private static final String MESSAGE_INFO_HOME_PHONE_FIELD = "Home: ";
    private static final String MESSAGE_INFO_OFFICE_PHONE_FIELD = "Office: ";
    private static final String MESSAGE_INFO_EMAIL_FIELD = "Email: ";
    private static final String MESSAGE_INFO_POSTAL_CODE_FIELD = "Postal Code: ";
    private static final String MESSAGE_INFO_CLUSTER_FIELD = "General Location: ";
    private static final String MESSAGE_INFO_DEBT_FIELD = "Current Debt ($): ";
    private static final String MESSAGE_INFO_TOTAL_DEBT_FIELD = "Total Debt ($): ";
    private static final String MESSAGE_INFO_INTEREST_FIELD = "Interest (%): ";
    private static final String MESSAGE_INFO_DATE_BORROW_FIELD = "Date Borrowed: ";
    private static final String MESSAGE_INFO_DEADLINE_FIELD = "Deadline: ";
    private static final String MESSAGE_INFO_DATE_REPAID_FIELD = "Date Repaid: ";
    private static final String MESSAGE_INFO_NEARBY_PERSON_FIELD = "All contacts in this area: ";
    private static final String MESSAGE_INFO_DEBT_REPAYMENT_FIELD = "Debt repayment progress: ";

    private Logic logic;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private NearbyPersonListPanel nearbyPersonListPanel;
    private DebtRepaymentProgressBar debtRepaymentProgressBar;
    private DebtorProfilePicture debtorProfilePicture;

    @FXML
    private Pane pane;
    @FXML
    private ScrollPane personInfoPanel;
    @FXML
    private SplitPane splitPane;
    @FXML
    private VBox content;
    @FXML
    private Label name;
    @FXML
    private Text handphoneField;
    @FXML
    private Label handphone;
    @FXML
    private Text homePhoneField;
    @FXML
    private Label homePhone;
    @FXML
    private Text officePhoneField;
    @FXML
    private Label officePhone;
    @FXML
    private Text addressField;
    @FXML
    private Label address;
    @FXML
    private Text postalCodeField;
    @FXML
    private Label postalCode;
    @FXML
    private Text clusterField;
    @FXML
    private Label cluster;
    @FXML
    private Text emailField;
    @FXML
    private Label email;
    @FXML
    private Text debtField;
    @FXML
    private Label debt;
    @FXML
    private Text totalDebtField;
    @FXML
    private Label totalDebt;
    @FXML
    private Text interestField;
    @FXML
    private Label interest;
    @FXML
    private Text dateBorrowField;
    @FXML
    private Label dateBorrow;
    @FXML
    private Text deadlineField;
    @FXML
    private Label deadline;
    @FXML
    private Text dateRepaidField;
    @FXML
    private Label dateRepaid;
    @FXML
    private Text nearbyPersonField;
    @FXML
    private FlowPane tags;
    @FXML
    private StackPane nearbyPersonListPanelPlaceholder;
    @FXML
    private StackPane progressBarPlaceholder;
    @FXML
    private GridPane personInfoTable;
    @FXML
    private AnchorPane profilePicPlaceholder;
    @FXML
    private Text debtRepaymentField;

    public InfoPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Loads the full info of the person
     * @param person the selected person to display the full info of.
     */
    public void loadPersonInfo(ReadOnlyPerson person) {
        handphoneField.setText(MESSAGE_INFO_HANDPHONE_FIELD);
        homePhoneField.setText(MESSAGE_INFO_HOME_PHONE_FIELD);
        officePhoneField.setText(MESSAGE_INFO_OFFICE_PHONE_FIELD);
        addressField.setText(MESSAGE_INFO_ADDRESS_FIELD);
        emailField.setText(MESSAGE_INFO_EMAIL_FIELD);
        postalCodeField.setText(MESSAGE_INFO_POSTAL_CODE_FIELD);
        clusterField.setText(MESSAGE_INFO_CLUSTER_FIELD);
        debtField.setText(MESSAGE_INFO_DEBT_FIELD);
        totalDebtField.setText(MESSAGE_INFO_TOTAL_DEBT_FIELD);
        interestField.setText(MESSAGE_INFO_INTEREST_FIELD);
        dateBorrowField.setText(MESSAGE_INFO_DATE_BORROW_FIELD);
        deadlineField.setText(MESSAGE_INFO_DEADLINE_FIELD);
        dateRepaidField.setText(MESSAGE_INFO_DATE_REPAID_FIELD);
        nearbyPersonField.setText(MESSAGE_INFO_NEARBY_PERSON_FIELD);
        debtRepaymentField.setText(MESSAGE_INFO_DEBT_REPAYMENT_FIELD);
        bindListeners(person);
        if (splitPane.lookup(".split-pane-divider") != null) {
            splitPane.lookup(".split-pane-divider").setMouseTransparent(true);
        }
    }

    /**
     * Resets the Nearby Person List Panel
     * @param person the selected person to display the nearby contacts of
     */
    public void resetNearbyPersonListPanel(ReadOnlyPerson person) {
        nearbyPersonListPanel = new NearbyPersonListPanel(logic.getAllPersons(), person);
        nearbyPersonListPanelPlaceholder.getChildren().clear();
        nearbyPersonListPanelPlaceholder.getChildren().add(nearbyPersonListPanel.getRoot());
        personInfoPanel.setVisible(true);
        personInfoPanel.setContent(content);
    }

    //@@author jelneo
    /**
     * Resets the debt repayment progress bar.
     * @param person the person whose person card is selected in the address book
     */
    private void resetDebtRepaymentProgressBar(ReadOnlyPerson person) {
        debtRepaymentProgressBar = new DebtRepaymentProgressBar(person);
        progressBarPlaceholder.getChildren().clear();
        progressBarPlaceholder.getChildren().add(debtRepaymentProgressBar.getRoot());
    }

    //@@author jaivigneshvenugopal
    /**
     * Resets the debtors profile picture to the latest one existing in folder
     * @param person the person whose person card is selected in the address book
     */
    private void resetDebtorProfilePicture(ReadOnlyPerson person) {
        debtorProfilePicture = new DebtorProfilePicture(person);
        profilePicPlaceholder.getChildren().add(debtorProfilePicture.getImageView());
    }

    //@@author khooroko
    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        handphone.textProperty().bind(Bindings.convert(person.handphoneProperty()));
        homePhone.textProperty().bind(Bindings.convert(person.homePhoneProperty()));
        officePhone.textProperty().bind(Bindings.convert(person.officePhoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        postalCode.textProperty().bind(Bindings.convert(person.postalCodeProperty()));
        cluster.textProperty().bind(Bindings.convert(person.clusterProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        debt.textProperty().bind(Bindings.convert(person.debtProperty()));
        totalDebt.textProperty().bind(Bindings.convert(person.totalDebtProperty()));
        interest.textProperty().bind(Bindings.convert(person.interestProperty()));
        dateBorrow.textProperty().bind(Bindings.convert(person.dateBorrowProperty()));
        deadline.textProperty().bind(Bindings.convert(person.deadlineProperty()));
        dateRepaid.textProperty().bind(Bindings.convert(person.dateRepaidProperty()));
        //TODO: fix tag colours. person.tagProperty().addListener((observable, oldValue, newValue) -> {
        tags.getChildren().clear();
        initTags(person);
    }

    /**
     * Initializes and styles tags belonging to each person.
     * @param person must be a valid person.
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-font-size:" + "20px");
            tags.getChildren().add(tagLabel);
        });
        logger.finest("All tags for " + person.getName().toString() + " initialized in info");
    }

    //@@author jelneo
    /**
     * Sets all info fields to not display anything.
     */
    private void loadDefaultPage() {
        personInfoPanel.setVisible(false);
    }

    //@@author khooroko
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InfoPanel)) {
            return false;
        }

        InfoPanel infoPanel = (InfoPanel) other;
        return name.getText().equals(infoPanel.name.getText())
                && handphone.getText().equals(infoPanel.handphone.getText())
                && homePhone.getText().equals(infoPanel.homePhone.getText())
                && officePhone.getText().equals(infoPanel.officePhone.getText())
                && address.getText().equals(infoPanel.address.getText())
                && postalCode.getText().equals(infoPanel.postalCode.getText())
                && cluster.getText().equals(infoPanel.cluster.getText())
                && debt.getText().equals(infoPanel.debt.getText())
                && totalDebt.getText().equals(infoPanel.totalDebt.getText())
                && interest.getText().equals(infoPanel.interest.getText())
                && email.getText().equals(infoPanel.email.getText())
                && deadline.getText().equals(infoPanel.deadline.getText())
                && dateBorrow.getText().equals(infoPanel.dateBorrow.getText())
                && dateRepaid.getText().equals(infoPanel.dateRepaid.getText())
                && tags.getChildrenUnmodifiable()
                        .stream()
                        .map(Label.class::cast)
                        .map(Label::getText)
                        .collect(Collectors.toList())
                        .equals(infoPanel.tags.getChildrenUnmodifiable()
                                .stream()
                                .map(Label.class::cast)
                                .map(Label::getText)
                                .collect(Collectors.toList()));
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonInfo(event.getNewSelection().person);
        logic.updateSelectedPerson(event.getNewSelection().person);
        resetNearbyPersonListPanel(event.getNewSelection().person);
        resetDebtRepaymentProgressBar(event.getNewSelection().person);
        resetDebtorProfilePicture(event.getNewSelection().person);
        personInfoPanel.setVisible(true);
        personInfoPanel.setContent(content);
    }

    @Subscribe
    private void handleChangeInternalListEvent(ChangeInternalListEvent event) {
        unregisterAsAnEventHandler(this);
    }

    @Subscribe
    private void handleDeselectionEvent(DeselectionEvent event) {
        loadDefaultPage();
    }

}
