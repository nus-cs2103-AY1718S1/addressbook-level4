package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;


/**
 * Controller for a help page
 */
public class CalendarWindow extends UiPart<Region> {

    public static final String CALENDAR_URL = "https://www.timeanddate.com/calendar/";
    public static final String URL = "https://www.youtube.com/watch?v=r5yaoMjaAmE";
    private static final Logger logger = LogsCenter.getLogger(CalendarWindow.class);
    private static final String ICON = "/images/calender.png";
    private static final String FXML = "Calendar.fxml";
    private static final String TITLE = "Calendar";

    @FXML
    private WebView browser;


    private final Stage dialogStage;

    public CalendarWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(true);
        FxViewUtil.setStageIcon(dialogStage, ICON);
        //String calendarUrl = getClass().getResource(FXML_FILE_FOLDER+ FXML).toString();
        browser.getEngine().load(CALENDAR_URL);
    }

    /**
     * Shows the calendar window.
     *
     * @throws IllegalStateException <ul>
     *                               <li>
     *                               if this method is called on a thread other than the JavaFX Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
     */
    public void show() {
        logger.fine("Displaying calendar on browser.");
        dialogStage.showAndWait();
    }
}

