package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BrowserPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ChangeThemeEvent;
import seedu.address.commons.events.ui.JumpToBrowserListRequestEvent;
import seedu.address.commons.events.ui.ShowBrowserEvent;
import seedu.address.commons.events.ui.ShowMeetingEvent;

//@@author fongwz
/**
 * Panel containing the list of settings
 */
public class SettingsSelector extends UiPart<Region> {

    private static final String FXML = "SettingsSelector.fxml";
    private final Logger logger = LogsCenter.getLogger(SettingsSelector.class);

    private ObservableList<BrowserSelectorCard> browserItems;
    private ObservableList<ThemeSelectorCard> themeItems;

    @FXML
    private ListView<BrowserSelectorCard> browserSelectorList;

    @FXML
    private ListView<ThemeSelectorCard> themeSelectorList;

    @FXML
    private Label browserSelectorTitle;

    @FXML
    private Label themeSelectorTitle;

    public SettingsSelector() {
        super(FXML);
        setConnections();
        registerAsAnEventHandler(this);
        browserSelectorTitle.textProperty().setValue("Display Mode :");
        browserSelectorTitle.getStyleClass().add("label-bright-underline");
        themeSelectorTitle.textProperty().setValue("Theme :");
        themeSelectorTitle.getStyleClass().add("label-bright-underline");
    }

    private void setConnections() {
        //Setting connections for browser list
        ObservableList<String> browserStringItems = FXCollections.observableArrayList(
                "linkedin", "google", "meeting", "maps"
        );
        browserItems = EasyBind.map(
                browserStringItems, (item) -> new BrowserSelectorCard(item));
        browserSelectorList.setItems(browserItems);
        browserSelectorList.setCellFactory(listView -> new BrowserListViewCell());

        //Setting connections for theme list
        ObservableList<String> themeStringItems = FXCollections.observableArrayList(
                "blue", "dark", "light"
        );
        themeItems = EasyBind.map(
                themeStringItems, (item) -> new ThemeSelectorCard(item));
        themeSelectorList.setItems(themeItems);
        themeSelectorList.setCellFactory(listView -> new SettingsSelector.ThemeListViewCell());

        setEventHandlerSelectionChange();
    }

    private void setEventHandlerSelectionChange() {
        browserSelectorList.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in browser list panel changed to : '" + newValue + "'");
                        if (newValue.getImageString().equals("meeting")) {
                            raise(new ShowMeetingEvent());
                        } else {
                            raise(new ShowBrowserEvent());
                            raise(new BrowserPanelSelectionChangedEvent(newValue.getImageString()));
                        }
                    }
                });
    }

    /**
     * Raise an event to select the browser list items
     */
    private void selectBrowser(String browserSelection) {
        for (int i = 0; i <= browserSelectorList.getItems().size(); i++) {
            if (browserSelectorList.getItems().get(i).getImageString().equals(browserSelection)) {
                browserSelectorList.getSelectionModel().clearAndSelect(i);
                raise(new BrowserPanelSelectionChangedEvent(browserSelection));
                return;
            }
        }
    }

    /**
     * Selects the theme on the theme ListView
     * @param theme
     */
    public void selectTheme(String theme) {
        for (int i = 0; i < themeSelectorList.getItems().size(); i++) {
            if (themeSelectorList.getItems().get(i).getThemeName().equals(theme)) {
                themeSelectorList.getSelectionModel().clearAndSelect(i);
            }
        }
    }

    @Subscribe
    private void handleJumpToBrowserListRequestEvent(JumpToBrowserListRequestEvent event) {
        selectBrowser(event.browserItem);
    }

    @Subscribe
    private void handleChangeThemeEvent(ChangeThemeEvent event) {
        selectTheme(event.theme);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code BrowserSelectorCard}.
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

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ThemeSelectorCard}.
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

    /**
     * Returns the browser List View for test cases
     */
    public ListView<BrowserSelectorCard> getBrowserSelectorList() {
        return browserSelectorList;
    }

    /**
     * Returns the observable list of browser items for test cases
     */
    public ObservableList<BrowserSelectorCard> getBrowserItems() {
        return browserItems;
    }
}
