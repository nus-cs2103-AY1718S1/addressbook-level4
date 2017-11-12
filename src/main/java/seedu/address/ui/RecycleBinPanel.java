package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.ReadOnlyPerson;
//@@author Pengyuz
/**
 * Panel containing the list of persons in bin.
 */
public class RecycleBinPanel extends UiPart<Region> {
    private static final String FXML = "RecycleBinPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RecycleBinPanel.class);

    private TabPane tabPane;

    @FXML
    private ListView<RecycleBinCard> personListView;


    public RecycleBinPanel(ObservableList<ReadOnlyPerson> personList, TabPane tab) {
        super(FXML);
        this.tabPane = tab;
        setConnections(personList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyPerson> personList) {
        ObservableList<RecycleBinCard> mappedList = EasyBind.map(
                personList, (person) -> new RecycleBinCard(person, personList.indexOf(person) + 1));
        personListView.setItems(mappedList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<RecycleBinCard> {

        @Override
        protected void updateItem(RecycleBinCard person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(person.getRoot());
            }
        }
    }
}



