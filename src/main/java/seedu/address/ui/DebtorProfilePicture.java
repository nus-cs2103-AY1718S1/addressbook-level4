package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;

public class DebtorProfilePicture extends UiPart<Region> {
    private static final String FXML = "DebtorProfilePicture.fxml";

    @FXML
    private ImageView profilePic = new ImageView();

    public DebtorProfilePicture(ReadOnlyPerson person) {
        super(FXML);
        String imageName = person.getName().toString().replaceAll("\\s+", "");
        String imagePath = "test.jpg";
        Image image = new Image("file:../docs/images/jaivigneshvenugopal.jpg");
        profilePic.setImage(image);
        profilePic.setFitWidth(300);
        profilePic.setFitHeight(300);
        registerAsAnEventHandler(this);
    }

    public ImageView getImageView() {
        return profilePic;
    }
}
