package seedu.address.ui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;

//@@author jaivigneshvenugopal
/**
 * Displays profile picture of each debtor
 */
public class DebtorProfilePicture extends UiPart<Region> {
    public static final String FXML = "DebtorProfilePicture.fxml";
    public static final String DEFAULT_INTERNAL_PROFILEPIC_FOLDER_PATH = "/images/profilePics/";
    public static final String DEFAULT_PROFILEPIC_PATH = "/images/profilePics/unknown.jpg";
    public static final String JPG_EXTENSION = ".jpg";

    @FXML
    private ImageView profilePic = new ImageView();

    public DebtorProfilePicture(ReadOnlyPerson person) {
        super(FXML);
        String imageName = person.getName().toString().replaceAll("\\s+", "");
        String imagePath = DEFAULT_PROFILEPIC_PATH;

        if (person.hasDisplayPicture()) {
            imagePath =  DEFAULT_INTERNAL_PROFILEPIC_FOLDER_PATH + imageName + JPG_EXTENSION;
        }

        Image image = new Image(getClass().getResource(imagePath).toExternalForm());

        profilePic.setImage(image);
        profilePic.setFitWidth(200);
        profilePic.setFitHeight(200);
        registerAsAnEventHandler(this);
    }

    public ImageView getImageView() {
        return profilePic;
    }

}
