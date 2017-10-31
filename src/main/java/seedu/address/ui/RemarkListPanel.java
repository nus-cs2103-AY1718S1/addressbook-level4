package seedu.address.ui;

import java.util.ArrayList;
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
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Panel containing the todolist of a person.
 */
public class RemarkListPanel extends UiPart<Region> {
    private static final String FXML = "RemarkListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RemarkListPanel.class);
    private final Integer firstIndexOfArray = 0;

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private TextArea remarkListPanel;

    private int totalPersonsWithRemarks;
    private ArrayList<String> personListWithRemarks;
    private ObservableList<ReadOnlyPerson> personObservableList;

    public RemarkListPanel(ObservableList<ReadOnlyPerson> personObservableList) {
        super(FXML);
        remarkListPanel.textProperty().bind(displayed);
        this.personObservableList = personObservableList;
        setTotalPersonsWithRemarks();

        registerAsAnEventHandler(this);
    }

    /**
     * Displays the total number of persons on list on the TextArea of the RemarkListPanel.fxml
     */
    private void setTotalPersonsWithRemarks() {
        totalPersonsWithRemarks = 0;
        personListWithRemarks = new ArrayList<>();
        for (int i = 0; i < personObservableList.size(); i++) {
            if (!personObservableList.get(i).getRemark().isEmpty()
                    && !personObservableList.get(i).getRemark().get(firstIndexOfArray).value.equals("")) {
                totalPersonsWithRemarks++;
                personListWithRemarks.add(personObservableList.get(i).getName().fullName);
            }
        }
        String printedString = totalPersonsWithRemarks + " person(s) with pending remarks: \n";
        for (int i = 0; i < personListWithRemarks.size(); i++) {
            printedString = printedString.concat(personListWithRemarks.get(i) + "\n");
        }
        final String finalString = printedString;
        Platform.runLater(() -> displayed.setValue(finalString));
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        personObservableList = event.data.getPersonList();
        setTotalPersonsWithRemarks();
    }
}
