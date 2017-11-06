//@@author limyongsong
package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Panel containing the todolist of a person.
 */
public class RemarkPanel extends UiPart<Region> {
    private static final String FXML = "RemarkPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RemarkPanel.class);

    private final StringProperty displayed = new SimpleStringProperty("---Remarks---\n");

    @FXML
    private TextArea remarkPanel;

    private ReadOnlyPerson tempPerson;

    public RemarkPanel() {
        super(FXML);
        remarkPanel.textProperty().bind(displayed);

        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        tempPerson = event.getNewSelection().person;
        String printedString = "Remarks regarding " + String.valueOf(event.getNewSelection().person.getName()) + ":\n"
                + "---------------------------------------------------\n";
        for (int i = 0; i < event.getNewSelection().person.getRemark().size(); i++) {
            printedString = printedString.concat(i + 1 + "). " //Shows a list of remark numbered from 1 to size()
                    + event.getNewSelection().person.getRemark().get(i).value + "\n");
        }
        final String finalString = printedString;
        Platform.runLater(() -> displayed.setValue(finalString));
    }


    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ObservableList<ReadOnlyPerson> personList = event.data.getPersonList();
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getName().fullName.equals(tempPerson.getName().fullName)) {
                String printedString = "Remarks regarding " + String.valueOf(personList.get(i).getName()) + ":\n"
                        + "---------------------------------------------------\n";
                for (int j = 0; j < personList.get(i).getRemark().size(); j++) {
                    printedString = printedString.concat(j + 1 + "). "
                            + personList.get(i).getRemark().get(j).value + "\n");
                }
                final String finalString = printedString;
                Platform.runLater(() -> displayed.setValue(finalString));
                break;
            }
        }
    }
}
