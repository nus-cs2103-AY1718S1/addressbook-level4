package seedu.address.ui;

import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author khooroko
/**
 * The Info Panel of the App that displays full information of a {@code Person}.
 */
public class InfoPanel extends UiPart<Region> {

    private static final String FXML = "InfoPanel.fxml";
    private static final String MESSAGE_INFO_ADDRESS_FIELD = "Address: ";
    private static final String MESSAGE_INFO_PHONE_FIELD = "HP: ";
    private static final String MESSAGE_INFO_EMAIL_FIELD = "Email: ";
    private static final String MESSAGE_INFO_POSTAL_CODE_FIELD = "S";
    private static final String MESSAGE_INFO_DEBT_FIELD = "Debt: $";
    private static final String MESSAGE_INFO_DATE_BORROW_FIELD = "Date borrowed: ";
    private static final String MESSAGE_INFO_DEAD_LINE_FIELD = "Dead Line: ";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private Pane pane;
    @FXML
    private Label name;
    @FXML
    private Text phoneField;
    @FXML
    private Label phone;
    @FXML
    private Text addressField;
    @FXML
    private Label address;
    @FXML
    private Text postalCodeField;
    @FXML
    private Label postalCode;
    @FXML
    private Text emailField;
    @FXML
    private Label email;
    @FXML
    private Text debtField;
    @FXML
    private Label debt;
    @FXML
    private Text dateBorrowField;
    @FXML
    private Label dateBorrow;
    @FXML
    private Text deadLineField;
    @FXML
    private Label deadLine;
    @FXML
    private FlowPane tags;

    public InfoPanel() {
        super(FXML);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Loads the full info of the person
     * @param person the selected person to display the full info of.
     */
    public void loadPersonInfo(ReadOnlyPerson person) {
        phoneField.setText(MESSAGE_INFO_PHONE_FIELD);
        addressField.setText(MESSAGE_INFO_ADDRESS_FIELD);
        emailField.setText(MESSAGE_INFO_EMAIL_FIELD);
        postalCodeField.setText(MESSAGE_INFO_POSTAL_CODE_FIELD);
        debtField.setText(MESSAGE_INFO_DEBT_FIELD);
        dateBorrowField.setText(MESSAGE_INFO_DATE_BORROW_FIELD);
        deadLineField.setText(MESSAGE_INFO_DEAD_LINE_FIELD);
        bindListeners(person);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        postalCode.textProperty().bind(Bindings.convert(person.postalCodeProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        debt.textProperty().bind(Bindings.convert(person.debtProperty()));
        dateBorrow.textProperty().bind(Bindings.convert(person.dateBorrowProperty()));
        deadLine.textProperty().bind(Bindings.convert(person.dateBorrowProperty()));
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
            tagLabel.setStyle("-fx-font-size:" + "15px");
            tags.getChildren().add(tagLabel);
        });
        logger.finest("All tags for " + person.getName().toString() + " initialized in info");
    }

    /**
     * Sets all info fields to not display anything.
     */
    private void loadDefaultPage() {
        Label label;
        Text text;
        for (Node node: pane.getChildren()) {
            if (node instanceof Label) {
                label = (Label) node;
                label.setText("");
            } else if (node instanceof TextFlow) {
                for (Node subNode: ((TextFlow) node).getChildren()) {
                    if (subNode instanceof Text) {
                        text = (Text) subNode;
                        text.setText("");
                    }
                    if (subNode instanceof Label) {
                        label = (Label) subNode;
                        label.setText("");
                    }
                }
            }
        }
    }

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
                && phone.getText().equals(infoPanel.phone.getText())
                && address.getText().equals(infoPanel.address.getText())
                && postalCode.getText().equals(infoPanel.postalCode.getText())
                && debt.getText().equals(infoPanel.debt.getText())
                && email.getText().equals(infoPanel.email.getText())
                && deadLine.getText().equals(infoPanel.deadLine.getText())
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
    }
}
