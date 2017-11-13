//@@author tshradheya
package seedu.address.ui;

import java.io.File;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays information of a Popular Contact
 */
public class PopularContactCard extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(PersonCard.class);
    private static final String FXML = "PopularContactCard.fxml";
    private static final Integer IMAGE_WIDTH = 70;
    private static final Integer IMAGE_HEIGHT = 70;
    private static final String DIRECTORY_SAVING_PATH = "pictures/";
    private static final String DEFAULT_IMAGE_PATH = "/images/defaulddp.png";
    private static final String EMPTY_STRING = "";
    private static final String IMAGE_EXTENSION = ".png";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyPerson person;

    @FXML
    private VBox popularContactPane;
    @FXML
    private Label popularContactName;
    @FXML
    private Circle popularContactDisplayPicture;
    @FXML
    private Label rank;

    public PopularContactCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        rank.setText("#" + displayedIndex + " ");
        bindListeners(person);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        popularContactName.textProperty().bind(Bindings.convert(person.nameProperty()));
        assignImage(person);
    }

    /**
     * Assigns URL to the image depending on the path
     */
    private void assignImage(ReadOnlyPerson person) {

        if (!person.getDisplayPicture().getPath().equals(EMPTY_STRING)) {

            Image image = new Image("file:" + DIRECTORY_SAVING_PATH + person.getDisplayPicture().getPath()
                    + IMAGE_EXTENSION, IMAGE_WIDTH, IMAGE_HEIGHT, false, false);

            // To take care of image deleted manually
            File file = new File(DIRECTORY_SAVING_PATH + person.getDisplayPicture().getPath() + IMAGE_EXTENSION);

            //Defensive programming
            if (!file.exists()) {
                logger.info("Corrupted image. Loading default image now");

                image = new Image(MainApp.class.getResourceAsStream(DEFAULT_IMAGE_PATH),
                        IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
            }

            popularContactDisplayPicture.setFill(new ImagePattern(image));

        } else {
            Image image = new Image(MainApp.class.getResourceAsStream(DEFAULT_IMAGE_PATH),
                    IMAGE_WIDTH, IMAGE_HEIGHT, false, false);

            popularContactDisplayPicture.setFill(new ImagePattern(image));
        }
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PopularContactCard card = (PopularContactCard) other;
        return rank.getText().equals(card.rank.getText())
                && person.equals(card.person);
    }
}
