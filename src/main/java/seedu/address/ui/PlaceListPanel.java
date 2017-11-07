package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.PlacePanelSelectionChangedEvent;
import seedu.address.model.place.ReadOnlyPlace;

/**
 * Panel containing the list of places.
 */
public class PlaceListPanel extends UiPart<Region> {
    private static final String FXML = "PlaceListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PlaceListPanel.class);

    @FXML
    private ListView<PlaceCard> placeListView;

    public PlaceListPanel(ObservableList<ReadOnlyPlace> placeList) {
        super(FXML);
        setConnections(placeList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyPlace> placeList) {
        ObservableList<PlaceCard> mappedList = EasyBind.map(
                placeList, (place) -> new PlaceCard(place, placeList.indexOf(place) + 1));
        placeListView.setItems(mappedList);
        placeListView.setCellFactory(listView -> new PlaceListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        placeListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in place list panel changed to : '" + newValue + "'");
                        raise(new PlacePanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code PlaceCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            placeListView.scrollTo(index);
            placeListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PlaceCard}.
     */
    class PlaceListViewCell extends ListCell<PlaceCard> {

        @Override
        protected void updateItem(PlaceCard place, boolean empty) {
            super.updateItem(place, empty);

            if (empty || place == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(place.getRoot());
            }
        }
    }

}
