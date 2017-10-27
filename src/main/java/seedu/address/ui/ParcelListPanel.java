package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.JumpToTabRequestEvent;
import seedu.address.commons.events.ui.ParcelPanelSelectionChangedEvent;
import seedu.address.model.parcel.ReadOnlyParcel;

/**
 * Panel containing the list of parcels.
 */
public class ParcelListPanel extends UiPart<Region> {
    private static final String FXML = "ParcelListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ParcelListPanel.class);

    @FXML
    private ListView<ParcelCard> allUncompletedParcelListView;

    @FXML
    private ListView<ParcelCard> allCompletedParcelListView;

    @FXML
    private TabPane tabPanePlaceholder;

    public static final Index INDEX_FIRST_TAB = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_TAB = Index.fromOneBased(2);

    public ParcelListPanel(ObservableList<ReadOnlyParcel> uncompletedParcels,
                           ObservableList<ReadOnlyParcel> completedParcels) {
        super(FXML);
        setConnections(uncompletedParcels, completedParcels);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyParcel> uncompletedParcels,
                                ObservableList<ReadOnlyParcel> completedParcels) {
        ObservableList<ParcelCard> mappedList = EasyBind.map(
                uncompletedParcels, (parcel) -> new ParcelCard(parcel,
                        uncompletedParcels.indexOf(parcel) + 1));
        allUncompletedParcelListView.setItems(mappedList);
        allUncompletedParcelListView.setCellFactory(listView -> new ParcelListViewCell());

        mappedList = EasyBind.map(completedParcels, (parcel) -> new ParcelCard(parcel,
                        completedParcels.indexOf(parcel) + 1));
        allCompletedParcelListView.setItems(mappedList);
        allCompletedParcelListView.setCellFactory(listView -> new ParcelListViewCell());

        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        allUncompletedParcelListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in parcel list panel changed to : '" + newValue + "'");
                        raise(new ParcelPanelSelectionChangedEvent(newValue));
                    }
                });

        allCompletedParcelListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in parcel list panel changed to : '" + newValue + "'");
                        raise(new ParcelPanelSelectionChangedEvent(newValue));
                    }
                });

        tabPanePlaceholder.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(newValue.getText().equals("All Parcels")) {
                        logger.fine("Tab in parcel list panel changed to : '" + newValue.getText() + "'");
                        handleTabSelection(INDEX_FIRST_TAB);
                    } else {
                        handleTabSelection(INDEX_SECOND_TAB);
                    }
                }
        );
    }

    /**
     * Scrolls to the {@code ParcelCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            allUncompletedParcelListView.scrollTo(index);
            allUncompletedParcelListView.getSelectionModel().clearAndSelect(index);
        });

        Platform.runLater(() -> {
            allCompletedParcelListView.scrollTo(index);
            allCompletedParcelListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    @FXML @Subscribe
    private void handleJumpToTabEvent(JumpToTabRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        tabPanePlaceholder.getSelectionModel().select(event.targetIndex);
    }

    public void handleTabSelection(Index selectedIndex) {
        EventsCenter.getInstance().post(new JumpToTabRequestEvent(selectedIndex));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ParcelCard}.
     */
    class ParcelListViewCell extends ListCell<ParcelCard> {

        @Override
        protected void updateItem(ParcelCard parcel, boolean empty) {
            super.updateItem(parcel, empty);

            if (empty || parcel == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(parcel.getRoot());
            }
        }
    }

}
