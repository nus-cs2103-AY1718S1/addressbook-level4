//@@author keloysiusmak
package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;

/**
 * Panel containing the list of commands, and their command words and aliases
 */
class ViewAliasListPanel extends UiPart<Region> {
    private static final String FXML = "ViewAliasListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ViewAliasListPanel.class);

    @javafx.fxml.FXML
    private ListView<ViewAliasCard> viewAliasListView;

    public ViewAliasListPanel(ArrayList<ArrayList<String>> commandList, Logic logic) {
        super(FXML);
        setCommands(commandList, logic);
    }

    private void setCommands(ArrayList<ArrayList<String>> commandList, Logic logic) {

        ArrayList<ViewAliasCard> mappedList = new ArrayList<ViewAliasCard>();
        for (int i = 0; i < commandList.size(); i++) {
            ArrayList<String> command = commandList.get(i);
            ViewAliasCard v = new ViewAliasCard(command.get(0), command.get(1), logic);
            mappedList.add(v);
        }
        ObservableList<ViewAliasCard> convertedList = FXCollections.observableArrayList(mappedList);
        viewAliasListView.setItems(convertedList);
        viewAliasListView.setCellFactory(listView -> new ViewAliasListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class ViewAliasListViewCell extends ListCell<ViewAliasCard> {
        @Override
        protected void updateItem(ViewAliasCard command, boolean empty) {
            super.updateItem(command, empty);

            if (empty || command == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(command.getRoot());
            }
        }
    }
}
