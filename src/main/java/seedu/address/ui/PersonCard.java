package seedu.address.ui;

import java.util.HashMap;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final double GAP = 8;
    // @@author tanchc
    private static String[] colors = { "red", "blue", "orange", "brown", "green" };

    private static HashMap<String, String> moduleColors = new HashMap<String, String>();
    private static Random random = new Random();
    // @@author
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyPerson person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    // @@author ahmadalkaff
    @FXML
    private FlowPane phones;
    //@@author viviantan95
    @FXML
    private Label birthday;
    //@@author
    @FXML
    private Label address;
    // @@author ahmadalkaff
    @FXML
    private FlowPane emails;
    // @@author
    @FXML
    private ImageView imageView;
    @FXML
    private FlowPane modules;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        // @@author ahmadalkaff
        initPhones(person);
        initEmails(person);
        initModules(person);
        //@@author viviantan95
        initPhoto(person);
        //@@author
        bindListeners(person);
    }
    // @@author tanchc
    private static int getRandom() {
        int randNum = random.nextInt(colors.length);
        return randNum;
    }

    private static String getColorForModule(String moduleValue, int randNum) {
        if (!moduleColors.containsKey(moduleValue)) {
            moduleColors.put(moduleValue, colors[randNum]);
        }

        return moduleColors.get(moduleValue);
    }
    // @@author
    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        // @@author ahmadalkaff
        person.phoneProperty().addListener((observable, oldValue, newValue) -> {
            phones.getChildren().clear();
            initPhones(person);
        });

        //@@author viviantan95
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        //@@author
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        // @@author ahmadalkaff
        person.emailProperty().addListener((observable, oldValue, newValue) -> {
            emails.getChildren().clear();
            initEmails(person);
        });

        //@@author viviantan95
        person.photoProperty().addListener((observable, oldValue, newValue) -> {
            imageView.setImage(new Image(person.getPhoto().toString(), 120, 120,
                    true, false));
        });
        //@@author
        person.moduleProperty().addListener((observable, oldValue, newValue) -> {
            modules.getChildren().clear();
            initModules(person);
        });
    }

    // @@author ahmadalkaff
    /**
     * Initialise the phones for each person
     */
    private void initPhones(ReadOnlyPerson person) {
        person.getPhones().forEach(phone -> {
            Label phoneLabel = new Label(phone.value);
            phones.getChildren().add(phoneLabel);
            phones.setHgap(GAP);
        });
    }

    /**
     * Initialise the emails for each person
     */
    private void initEmails(ReadOnlyPerson person) {
        person.getEmails().forEach(email -> {
            Label emailLabel = new Label(email.value);
            emails.getChildren().add(emailLabel);
            emails.setHgap(GAP);
        });
    }
    // @@author

    /**
     * Initialise the modules for each person
     */
    private void initModules(ReadOnlyPerson person) {
        person.getModules().forEach(module -> {
            Label moduleLabel = new Label(module.moduleName);
            // @@author tanchc
            int randNum = getRandom();
            moduleLabel.setStyle("-fx-background-color: " + getColorForModule(module.moduleName, randNum));
            //            if (randNum > 6) {
            //                moduleLabel.setStyle("-fx-text-fill: black");
            //            }
            // @@author
            modules.getChildren().add(moduleLabel);
            modules.setHgap(GAP);
        });
    }

    //@@author viviantan95
    /**
     *
     * Initialise the photo for each person
     */
    private void initPhoto(ReadOnlyPerson person) {
        imageView.setImage(new Image(person.getPhoto().toString(), 126, 126, true,
                false));
    }
    //@@author

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
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }


}
