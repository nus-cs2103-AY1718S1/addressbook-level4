package seedu.address.ui;

import java.util.logging.Logger;

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
 * The Info Panel of the App.
 */
public class InfoPanel extends UiPart<Region> {

    private static final String FXML = "InfoPanel.fxml";

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
    private void loadPersonInfo(ReadOnlyPerson person) {
        phoneField.setText("HP: ");
        addressField.setText("Address: ");
        emailField.setText("Email: ");
        postalCodeField.setText("S");
        debtField.setText("Debt: $");
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
        //person.tagProperty().addListener((observable, oldValue, newValue) -> {
        tags.getChildren().clear();
        initTags(person);
        //});
    }

    /**
     * Initializes and styles tags belonging to each person. Each unique tag has a unique color.
     * @param person
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-font-size:" + "24px");
            tags.getChildren().add(tagLabel);
        });
        logger.finest("All tags for " + person.getName().toString() + " initialized");
    }

    /**
     * Sets all info fields to not display anything.
     */
    private void loadDefaultPage() {
        for (Node node : pane.getChildren()) {
            if(node instanceof TextFlow) {
                for (Node subNode : ((TextFlow) node).getChildren()) {
                    if(subNode instanceof Text) {
                        ((Text) subNode).setText("");
                    }
                    if(subNode instanceof Label) {
                        ((Label) subNode).setText("");
                    }
                }
            }
            if(node instanceof Text) {
                ((Text) node).setText("");
            }
            if(node instanceof Label) {
                ((Label) node).setText("");
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
                && email.getText().equals(infoPanel.email.getText());
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonInfo(event.getNewSelection().person);
    }
}
