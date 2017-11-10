package seedu.address.ui;

import java.util.logging.Logger;

import javafx.scene.control.TabPane;
import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.ReadOnlyPerson;

//@@author Pengyuz
/**
 * Panel containing the list of persons.
 */
public class RecycleBinPanel extends UiPart<Region> {
    private static final String FXML = "RecycleBinPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RecycleBinPanel.class);

    private TabPane tabPane;

    @FXML
    private ListView<PersonCard> personListView;


    public RecycleBinPanel(ObservableList<ReadOnlyPerson> personList, TabPane tab) {
        super(FXML);
        this.tabPane = tab;
        setConnections(personList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyPerson> personList) {
        ObservableList<PersonCard> mappedList = EasyBind.map(
                personList, (person) -> new PersonCard(person, personList.indexOf(person) + 1));
        personListView.setItems(mappedList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<PersonCard> {

        @Override
        protected void updateItem(PersonCard person, boolean empty) {
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



