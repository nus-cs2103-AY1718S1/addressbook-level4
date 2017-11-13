package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.ReadOnlyPerson;

//@@author awarenessxz
/**
 * Left Tab Panel containing personListPanel, EmailDraftPanel, and BirthdateTab
 */
public class LeftDisplayPanel extends UiPart<Region> {
    private static final String FXML = "LeftDisplayPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(LeftDisplayPanel.class);

    //Independent UI parts residing in this UI container
    private PersonListPanel personListPanel;
    private PersonListBirthdatePanel birthdayListPanel;
    private MessageDisplay messageDisplay;
    private int tabIndex;

    @FXML
    private TabPane leftDisplayPanel;

    @FXML
    private Tab personListTab;

    @FXML
    private Tab emailDraftTab;

    @FXML
    private Tab birthdateTab;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane messageDraftPanelPlaceholder;

    @FXML
    private StackPane birthdatePanelPlaceholder;

    public LeftDisplayPanel(ObservableList<ReadOnlyPerson> personList,
                            ObservableList<ReadOnlyPerson> personListBirthdate) {
        super(FXML);

        personListPanel = new PersonListPanel(personList);
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        birthdayListPanel = new PersonListBirthdatePanel(personListBirthdate);
        birthdatePanelPlaceholder.getChildren().add(birthdayListPanel.getRoot());

        messageDisplay = new MessageDisplay();
        messageDraftPanelPlaceholder.getChildren().add(messageDisplay.getRoot());

        tabIndex = 0;
    }

    /**
     * Toggles Tabs based on index
     */
    public void toggleTabs(int index) {
        if (index >= 0) {
            tabIndex = index;
        } else {
            tabIndex = (tabIndex + 1) % 3;
        }

        switch (tabIndex) {
        case 0:
            leftDisplayPanel.getSelectionModel().select(personListTab);
            break;
        case 1:
            leftDisplayPanel.getSelectionModel().select(emailDraftTab);
            break;
        case 2:
            leftDisplayPanel.getSelectionModel().select(birthdateTab);
            break;
        default:
            assert false : "This should not happen";
            break;
        }
    }

    /**
     * Scrolls one page down
     */
    public void scrollDown() {
        if (tabIndex == 0) {
            personListPanel.scrollDown();
        } else if (tabIndex == 2) {
            birthdayListPanel.scrollDown();
        }
    }

    /**
     * Scrolls one page up
     */
    public void scrollUp() {
        if (tabIndex == 0) {
            personListPanel.scrollUp();
        } else if (tabIndex == 2) {
            birthdayListPanel.scrollUp();
        }
    }

    public PersonListPanel getPersonListPanel() {
        return this.personListPanel;
    }

}
