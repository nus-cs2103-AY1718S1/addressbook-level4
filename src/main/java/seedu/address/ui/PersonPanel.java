//@@author sebtsh
package seedu.address.ui;

import java.io.File;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The main panel of the app that displays all the details of a person in the address book.
 */
public class PersonPanel extends UiPart<Region> {
    private static final String FXML = "PersonPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private ReadOnlyPerson storedPerson;
    private Logic logic;

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


    public PersonPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        registerAsAnEventHandler(this);
    }

    //@@author a0107442n
    /**
     * Register the image import button for click event.
     */

    private void registerImageSelectionButton(Index index) {
        //Set onClickListener for the image import button
        photoSelectionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
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

                //Update photo field in addressbook through an edit command
                try {
                    logger.fine("Person Panel register button for index "
                            + index.getZeroBased());
                    CommandResult commandResult = logic.execute("edit " + index.getZeroBased() + " ph/"
                            + file.toURI().toString());
                    raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));
                } catch (CommandException | ParseException e) {
                    raise(new NewResultAvailableEvent(e.getMessage(), true));
                }
            }
        });
    }
    //@@author sebtsh

    /**
     * Shows the details of the person selected. Called by the handlePersonPanelSelectionChangedEvent event listener,
     * as well as the handleAddressBookChanged event listener.
     * @param person
     */
    private void showPersonDetails(ReadOnlyPerson person) {
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
        //@@author sebtsh
    }

    /**
     * Calls showPersonDetails when a person is selected in the person panel, causing their details to be displayed
     * in the main window.
     * @param event
     */
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        Index index = event.getNewSelection().index;
        ReadOnlyPerson person = event.getNewSelection().person;
        logger.fine("Person Panel: stored index becomes " + index
                .getZeroBased());

        registerImageSelectionButton(index);
        logger.fine(LogsCenter.getEventHandlingLogMessage
                (event) + " for index " + index.getZeroBased());
        showPersonDetails(person);
    }

    /**
     * Calls showPersonDetails when the address book is changed. This results in any edits to the currently displayed
     * person being refreshed immediately, instead of the user having to click away and click back to see the changes.
     * @param event
     */
    @Subscribe
    private void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.fine(LogsCenter.getEventHandlingLogMessage(event));
        for (ReadOnlyPerson dataPerson : event.data.getPersonList()) {
            if (storedPerson != null && storedPerson.getName().equals(dataPerson.getName())) {
                showPersonDetails(dataPerson);
                storedPerson = dataPerson;
                break;
            }
        }
    }
}
