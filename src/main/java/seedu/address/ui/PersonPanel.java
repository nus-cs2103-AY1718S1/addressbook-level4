//@@author A0155754X
package seedu.address.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.google.common.eventbus.Subscribe;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The main panel of the app that displays all the details of a person in the address book.
 */
public class PersonPanel extends UiPart<Region> {
    private static final String FXML = "PersonPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private ReadOnlyPerson storedPerson;

    @FXML
    private Rectangle defaultScreen;

    @FXML
    private Label nameLabel;

    @FXML
    private ImageView photo;

    @FXML
    private Button photoSelectionButton;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label companyLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label priorityLabel;

    @FXML
    private Label noteLabel;

    @FXML
    private FlowPane tagsPane;

    @FXML
    private ImageView imageView;


    public PersonPanel() {
        super(FXML);
        registerImageSelectionButton();
        registerAsAnEventHandler(this);
    }

    //@@author a0107442n
    /**
     * Register the image import button for click event.
     */

    private void registerImageSelectionButton() {
        //Set onClickListener for the image import button
        photoSelectionButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new
                EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                FileChooser fileChooser = new FileChooser();

                //Set extension filter
                FileChooser.ExtensionFilter extFilterJpg = new FileChooser
                        .ExtensionFilter("JPG files (*.jpg)", "*.JPG");
                FileChooser.ExtensionFilter extFilterJpeg = new FileChooser
                        .ExtensionFilter("JPEG files (*.jpeg)", "*.JPEG");
                FileChooser.ExtensionFilter extFilterPng = new FileChooser
                        .ExtensionFilter ("PNG files (*.png)", "*.PNG");
                fileChooser.getExtensionFilters().addAll(extFilterJpg,
                        extFilterJpeg, extFilterPng);

                //Show open file dialog
                File file = fileChooser.showOpenDialog(((Node) t.getTarget())
                        .getScene().getWindow());

                try {
                    EditCommand.EditPersonDescriptor descriptor = new
                    EditCommand.EditPersonDescriptor();
                    descriptor.setPhoto(person.getPhoto());
                    EditCommand command= new EditCommand(index, descriptor);
                    //BufferedImage bufferedImage = ImageIO.read(file);
                    //Image image = SwingFXUtils.toFXImage(bufferedImage,
                    // null);
                    //photo.setImage(image);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    //@@author

    /**
     * Shows the details of the person selected. Called by the handlePersonPanelSelectionChangedEvent event listener,
     * as well as the handleAddressBookChanged event listener.
     * @param person
     */
    private void showPersonDetails(Index index, ReadOnlyPerson person) {
        defaultScreen.setOpacity(0);

        nameLabel.setText(person.getName().toString());
        phoneLabel.setText(person.getPhone().toString());
        emailLabel.setText(person.getEmail().toString());
        addressLabel.setText(person.getAddress().toString());
        companyLabel.setText(person.getCompany().toString());
        positionLabel.setText(person.getPosition().toString());
        priorityLabel.setText(person.getPriority().toString());
        statusLabel.setText(person.getStatus().toString());
        noteLabel.setText(person.getNote().toString());
        tagsPane.getChildren().removeAll(tagsPane.getChildren());
        person.getTags().forEach(tag -> tagsPane.getChildren().add(new Label(tag.tagName)));

        //@@author a0107442n
        //Load the photo of the contact
        String imagePath = person.getPhoto().toString();
        Image image = new Image(new File(imagePath).toURI().toString());
        photo.setImage(image);

        //@@author
        storedPerson = person;
    }

    /**
     * Calls showPersonDetails when a person is selected in the person panel, causing their details to be displayed
     * in the main window.
     * @param event
     */
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showPersonDetails(event.getNewSelection().index, event.getNewSelection()
                .person);
    }

    /**
     * Calls showPersonDetails when the address book is changed. This results in any edits to the currently displayed
     * person being refreshed immediately, instead of the user having to click away and click back to see the changes.
     * @param event
     */
    @Subscribe
    private void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        for (ReadOnlyPerson dataPerson : event.data.getPersonList()) {
            if (storedPerson.getName().equals(dataPerson.getName())) {
                showPersonDetails(null, dataPerson);
                storedPerson = dataPerson;
                break;
            }
        }
    }
}
