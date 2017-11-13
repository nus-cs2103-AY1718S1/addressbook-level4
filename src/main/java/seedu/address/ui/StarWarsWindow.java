package seedu.address.ui;

import java.io.InputStream;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.StarWars;
import seedu.address.commons.util.FxViewUtil;

/**
 * Controller for a star wars page
 */
public class StarWarsWindow extends UiPart<Region> {

    private static Timeline timeline;

    private static final Logger logger = LogsCenter.getLogger(StarWarsWindow.class);
    private static final String ICON = "/images/rolodex_icon_32.png";
    private static final String FXML = "StarWarsWindow.fxml";
    private static final String TITLE = "Star Wars IV";

    @FXML
    private TextArea textArea;

    private final Stage dialogStage;
    private StarWars starWars;

    public StarWarsWindow(StarWars starWarsObject) {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMinWidth(720.0);
        dialogStage.setMinHeight(480.0);
        dialogStage.setOnCloseRequest(event -> {
            StarWars.shutDown();
            timeline.stop();
        });
        dialogStage.setOnHiding(event -> StarWars.shutDown());
        FxViewUtil.setStageIcon(dialogStage, ICON);

        starWars = starWarsObject;
    }

    /**
     * Shows the star wars window.
     */
    public void show() {
        logger.fine("Showing star wars page.");
        dialogStage.show();

        InputStream inputStream = starWars.getIn();

        timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            String nextScene = getNextScene("[H", inputStream);
            if (nextScene != null) {
                textArea.setText(nextScene);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static String getNextScene(String delimeterPattern, InputStream in) {
        try {
            char lastChar = delimeterPattern.charAt(delimeterPattern.length() - 1);
            StringBuffer sb = new StringBuffer();
            int ch = in.read();
            while (ch != -1) {
                sb.append((char) ch);
                if ((char) ch == lastChar && sb.toString().endsWith(delimeterPattern)) {
                    return sb.toString().replace(delimeterPattern, "");
                }
                ch = in.read();
            }
            if (ch == -1) {
                in.close();
                return sb.toString();
            }
        } catch (Exception e) {
            timeline.stop();
            StarWars.shutDown();
        }
        return null;
    }
}
