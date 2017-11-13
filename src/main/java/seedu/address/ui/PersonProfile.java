//@@author zenghou
package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.PictureSize;
import facebook4j.auth.AccessToken;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/** Contains the profile panel of a person */
public class PersonProfile extends UiPart<Region> {

    private static final String FXML = "PersonProfile.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private final Facebook facebook = new FacebookFactory().getInstance();

    private final String appId = "383233665430563";
    private final String appSecret = "c127fe47ff977d7da7be5f38b4662ba5";
    private final String appToken = "383233665430563|lAaXz9VGaRFJNVLTI9iTKKl1pWk";
    private final String defaultProfilePicture = "images/fail.png";

    @FXML
    private Label profile;

    @FXML
    private Label name;

    @FXML
    private Label birthday;

    @FXML
    private Label remark;

    @FXML
    private ImageView profilePicture;

    @FXML
    private FlowPane tags;

    public PersonProfile() {
        super(FXML);
        registerAsAnEventHandler(this);
        facebook.setOAuthAppId(appId, appSecret);
        facebook.setOAuthAccessToken(new AccessToken(appToken, null));
    }

    /**
     * Sets the individual UI elements to the respective {@code Person} properties
     * so the profile correctly reflects the selected person.
     */
    private void initPersonProfile(ReadOnlyPerson person) {
        profile.setText("PROFILE");
        name.setText(person.getName().toString());
        birthday.setText(person.getBirthday().toString());
        remark.setText(person.getRemark().toString());
        tags.getChildren().clear(); // clear existing tags
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Retrieves a profile picture from Facebook {@see Facebook#getPictureURL}and sets that image as the contact image
     * for the Person's profile. A default image would be assigned if no picture is retrieved from Facebook.
     */
    private void setProfilePicture(ReadOnlyPerson person) {
        Image profilePic;
        try {
            profilePic = new Image(facebook.getPictureURL(person.getFacebook().value, PictureSize.large).toString());
        } catch (FacebookException fbe) {
            profilePic = new Image(defaultProfilePicture);
            fbe.printStackTrace();
        }
        profilePicture.setImage(profilePic);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ReadOnlyPerson selectedPerson = event.getNewSelection().person;
        initPersonProfile(selectedPerson);
        setProfilePicture(selectedPerson);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonProfile)) {
            return false;
        }

        // state check
        PersonProfile profile = (PersonProfile) other;
        return name.getText().equals(profile.name.getText())
                && remark.getText().equals(profile.remark.getText());
    }
}
