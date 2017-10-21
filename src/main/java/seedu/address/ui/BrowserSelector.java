package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

public class BrowserSelector extends UiPart<Region> {

    private static final String FXML = "BrowserSelector.fxml";
    private final Logger logger = LogsCenter.getLogger(BrowserSelector.class);


    @FXML
    private ListView<BrowserSelectorCard> browserSelectorList;

    public BrowserSelector() {
        super(FXML);
        setConnections();
        setEventHandler();
    }

    private void setConnections() {
        ObservableList<String> items = FXCollections.observableArrayList(
                "linkedin","facebook","meeting"
        );

        ObservableList<BrowserSelectorCard> mappedList = EasyBind.map(
                items, (item) -> new BrowserSelectorCard(item));

        browserSelectorList.setItems(mappedList);
        browserSelectorList.setCellFactory(listView -> new BrowserListViewCell());

        /** old code
        browserSelectorList.setItems(items);
        browserSelectorList.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (name.equals("linkedin")) {
                        imageView.setImage(new Image("/images/linkedin.png"));
                    } else if (name.equals("facebook")) {
                        imageView.setImage(new Image("/images/facebook.png"));
                    } else if (name.equals("meeting")) {
                        imageView.setImage(new Image("/images/meeting.png"));
                    }
                    imageView.setFitWidth(32.0);
                    imageView.setFitHeight(32.0);
                    setGraphic(imageView);
                }
            }
        }); */
    }

    private void setEventHandler() {
        browserSelectorList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in person list panel changed to : '" + newValue + "'");
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class BrowserListViewCell extends ListCell<BrowserSelectorCard> {

        @Override
        protected void updateItem(BrowserSelectorCard item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(item.getRoot());
            }
        }
    }
}
