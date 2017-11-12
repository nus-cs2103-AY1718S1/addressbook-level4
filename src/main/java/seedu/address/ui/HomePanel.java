package seedu.address.ui;

import java.io.InputStream;
import java.util.Random;
import java.util.logging.Logger;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.UserPrefs;

//@@author nahtanojmil
/**
 * Displays the home page on startup on when called upon
 */
public class HomePanel extends UiPart<Region> {

    private static final String FXML = "HomePanel.fxml";
    private InputStream is = this.getClass().getResourceAsStream("/images/Wallpaper/img9.jpg");
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private ImageView homePage;

    @FXML
    private Label quotesLabel;

    private UserPrefs pref;

    private String[] quotes = {
        "What we want is to see the child in pursuit of knowledge, and not knowledge in pursuit of the child.",
        "Good teaching is one-fourth preparation and three-fourths theatre.",
        "A teacher is a compass that activates the magnets of curiosity, knowledge, and wisdom in the pupils.",
        "I cannot teach anybody anything, I can only make them think.",
        "What sculpture is to a block of marble, education is to a human soul.",
        "Education breeds confidence. Confidence breeds hope. Hope breeds peace.",
        "Teach the children so it will not be necessary to teach the adults.",
        "Tell me and I forget. Teach me and I remember. Involve me and I learn.",
        "Education is not preparation for life; education is life itself.",
        "A mind when stretched by a new idea never regains its original dimensions.",
        "Education is not filling of a pail but the lighting of a fire.",
        "The best way to predict your future is to create it."
    };

    public HomePanel(UserPrefs preferences) {
        super(FXML);
        this.pref = preferences;
    }

    public HomePanel getHomePanel() {
        return this;
    }

    /**
     * Changes both images and quotes when home command is parsed
     */
    public void refreshPage() {
        Random random = new Random();
        Image image = new Image(is);
        homePage.setImage(image);
        homePage.setFitWidth(1650);
        quotesLabel.setMinWidth(500);
        quotesLabel.setStyle("-fx-font: 30 system;");
        quotesLabel.setText(quotes[random.nextInt(quotes.length)]);
    }

}
