package seedu.address.ui;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

public class ThemeSelector extends UiPart<Region>{

    private static final String FXML = "ThemeSelector.fxml";

    @FXML
    private ListView<ThemeSelectorCard> themeSelectorList;

    @FXML
    private Label themeSelectorLabel;

    public ThemeSelector() {
        super(FXML);
        themeSelectorLabel.textProperty().setValue("Theme :");
        themeSelectorLabel.getStyleClass().add("label-bright-underline");
        setConnections();
    }

    private void setConnections() {
        ObservableList<String> items = FXCollections.observableArrayList(
                "blue","dark","light"
        );

        ObservableList<ThemeSelectorCard> mappedList = EasyBind.map(
                items, (item) -> new ThemeSelectorCard(item));

        themeSelectorList.setItems(mappedList);
        themeSelectorList.setCellFactory(listView -> new ThemeSelector.ThemeListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class ThemeListViewCell extends ListCell<ThemeSelectorCard> {

        @Override
        protected void updateItem(ThemeSelectorCard item, boolean empty) {
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
