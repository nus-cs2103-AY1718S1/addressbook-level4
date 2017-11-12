package seedu.address.ui;

import java.io.File;
import java.net.MalformedURLException;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.ProfilePicturesFolder;
import seedu.address.commons.events.ui.MissingDisplayPictureEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author jaivigneshvenugopal
/**
 * Displays profile picture of each debtor
 */
public class DebtorProfilePicture extends UiPart<Region> {
    public static final String FXML = "DebtorProfilePicture.fxml";
    public static final String DEFAULT_PROFILEPIC_PATH = "/images/profilePics/unknown.jpg";
    public static final String JPG_EXTENSION = ".jpg";

    @FXML
    private ImageView profilePic = new ImageView();
    @FXML
    private AnchorPane profilePicPlaceHolder;

    public DebtorProfilePicture(ReadOnlyPerson person) {
        super(FXML);
        String imageName = person.getName().toString().replaceAll("\\s+", "");
        String imagePath = DEFAULT_PROFILEPIC_PATH;
        Image image = new Image(getClass().getResource(imagePath).toExternalForm());

        if (person.hasDisplayPicture()) {

            imagePath = ProfilePicturesFolder.getPath() + imageName + JPG_EXTENSION;
            File imageFile = new File(imagePath);

            if (!imageFile.exists()) {
                person.setHasDisplayPicture(false);
                raise(new MissingDisplayPictureEvent(person));
            } else {
                try {
                    image = new Image(imageFile.toURI().toURL().toExternalForm());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }

        profilePic.setImage(image);
        profilePic.setFitWidth(450);
        profilePic.setFitHeight(450);
        profilePicPlaceHolder.setTopAnchor(this.getImageView(), 20.0);
        profilePicPlaceHolder.setRightAnchor(this.getImageView(), 50.0);
        registerAsAnEventHandler(this);
    }

    public ImageView getImageView() {
        return profilePic;
    }

}
