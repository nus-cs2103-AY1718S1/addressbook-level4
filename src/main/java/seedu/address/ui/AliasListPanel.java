package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.alias.ReadOnlyAliasToken;

//@@author Alim95
/**
 * Panel containing the list of Alias.
 */
public class AliasListPanel extends UiPart<Region> {
    private static final String FXML = "AliasListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AliasListPanel.class);

    @FXML
    private ListView<AliasCard> aliasListView;

    public AliasListPanel(ObservableList<ReadOnlyAliasToken> aliasList) {
        super(FXML);
        setConnections(aliasList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyAliasToken> aliasList) {
        ObservableList<AliasCard> mappedList = EasyBind.map(
                aliasList, (alias) -> new AliasCard(alias, aliasList.indexOf(alias) + 1));
        aliasListView.setItems(mappedList);
        aliasListView.setCellFactory(listView -> new AliasListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code AliasCard}.
     */
    class AliasListViewCell extends ListCell<AliasCard> {

        @Override
        protected void updateItem(AliasCard alias, boolean empty) {
            super.updateItem(alias, empty);

            if (empty || alias == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(alias.getRoot());
            }
        }
    }

}
