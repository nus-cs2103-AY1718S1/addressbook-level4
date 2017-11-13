package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.person.ReadOnlyPerson;

//@@author liliwei25
/**
 * Shows the map in a pop-up browser
 */
public class MapWindow extends UiPart<Region> {

    public static final String TITLE = "Map";
    public static final String SPACE = " ";
    public static final String PLUS = "+";

    private static final String GOOGLE_MAPS_URL_PREFIX = "https://www.google.com.sg/maps?safe=off&q=";
    private static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";
    private static final Logger logger = LogsCenter.getLogger(MapWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "MapWindow.fxml";
    private static final String MESSAGE_SHOW_MAP = "Showing map window for selected person.";

    @FXML
    private WebView map;

    private final Stage dialogStage;

    public MapWindow(ReadOnlyPerson person) {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = setupStage(scene);
        displayMap(person);
    }

    /**
     * Setup stage for browser pop-up
     */
    private Stage setupStage(Scene scene) {
        Stage dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(false);
        FxViewUtil.setStageIcon(dialogStage, ICON);
        return dialogStage;
    }

    /**
     * Displays map of selected person on pop-up browser
     *
     * @param person Selected person to map
     */
    private void displayMap(ReadOnlyPerson person) {
        String mapUrl = GOOGLE_MAPS_URL_PREFIX + person.getAddress().getMapableAddress().replaceAll(SPACE, PLUS)
                + GOOGLE_SEARCH_URL_SUFFIX;
        map.getEngine().load(mapUrl);
    }

    /**
     * Shows the map window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine(MESSAGE_SHOW_MAP);
        dialogStage.show();
    }
}
