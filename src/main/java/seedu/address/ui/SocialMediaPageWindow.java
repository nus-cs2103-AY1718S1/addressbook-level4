package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class SocialMediaPageWindow extends UiPart<Region> {

    private static final String FXML = "SocialMediaPageWindow.fxml";

    @FXML
    private WebView socialMediaView;

    public SocialMediaPageWindow(String s) {
        super(FXML);

    }

    public WebView getSocialMediaView() {
        return socialMediaView;
    }

}
