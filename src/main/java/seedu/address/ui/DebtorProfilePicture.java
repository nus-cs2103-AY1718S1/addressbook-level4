package seedu.address.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.ProfilePicturesFolder;
import seedu.address.model.person.ReadOnlyPerson;

//@@author jaivigneshvenugopal
/**
 * Displays profile picture of each debtor
 */
public class DebtorProfilePicture extends UiPart<Region> {
    private static final String FXML = "DebtorProfilePicture.fxml";

    @FXML
    private ImageView profilePic = new ImageView();

    public DebtorProfilePicture(ReadOnlyPerson person) {
        super(FXML);
        String imageName = person.getName().toString().replaceAll("\\s+", "");
        String imagePath = ProfilePicturesFolder.getPath() + imageName + ".jpg";

        File file = new File(imagePath);

        if (!file.exists()) {
            file = new File("src/main/resources/images/unknown.jpg");
        }


        Image image = null;

        try {
            image = new Image(file.toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ImageIO.write(bufferedImage, "jpg", new File("src/main/resources/images/test.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        profilePic.setImage(image);
        profilePic.setFitWidth(300);
        profilePic.setFitHeight(300);
        registerAsAnEventHandler(this);
    }

    public ImageView getImageView() {
        return profilePic;
    }
}
