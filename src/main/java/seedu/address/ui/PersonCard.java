package seedu.address.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static String[] colors = { "red", "blue", "orange", "brown", "purple", "black", "gray", "maroon", "coral",
        "blueviolet", "slategrey", "darkseagreen", "darkturquoise", "darkkhaki", "firebrick", "darkcyan" };
    private static HashMap<String, String> tagColors = new HashMap<>();
    private static int tagNumber = 0;

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
    private HBox cardPane2;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initFavouriteLabel(person);
        initBirthdayLabel(person);
        initTags(person);
        bindListeners(person);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        person.favouriteProperty().addListener((observable, oldValue, newValue) -> initFavouriteLabel(person));
        //birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        person.birthdayProperty().addListener((observable, oldValue, newValue) -> initBirthdayLabel(person));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
    }

    /**
     * Sets the colour of a favourite label based on its favourite status
     */
    private void initFavouriteLabel(ReadOnlyPerson person) {
        boolean favouriteStatus = person.getFavourite().getFavourite();
        String textToDisplay = favouriteStatus ? "Fav" : "";
        Label favouriteLabel = new Label(textToDisplay);
        if (favouriteStatus) {
            favouriteLabel.setStyle("-fx-background-color: orangered");
        } else {
            favouriteLabel.setStyle("-fx-background-color: cornflowerblue");
        }
        cardPane.getChildren().add(favouriteLabel);
    }

    /**
     * Binds the birthday string together for each contact to display in a better format
     * so that it is clearer for the user.
     */
    private void initBirthdayLabel(ReadOnlyPerson person) {
        String initialBirthday = person.getBirthday().getBirthdayNumber();
        String birthdayToDisplay = Birthday.DEFAULT_BIRTHDAY;

        if (initialBirthday.length() >= 4 && !initialBirthday.equals(Birthday.DEFAULT_BIRTHDAY)) {
            birthdayToDisplay = generateBirthdayMsg(initialBirthday);
        }

        Label birthdayLabel = new Label(birthdayToDisplay);
        cardPane2.getChildren().add(birthdayLabel);

    }

    /**
     * Returns the birthday in the format needed for display.
     */
    private String generateBirthdayMsg (String initialBirthday) {
        HashMap<String, String> findSelectedMonth = initializeMonthHashMap();
        List<String> splitDates = new ArrayList<>();
        String birthdayToDisplay;

        for (int start = 0; start < 4; start += 2) {
            splitDates.add(initialBirthday.substring(start, start + 2));
        }

        birthdayToDisplay = splitDates.get(0) + " " + findSelectedMonth.get(splitDates.get(1))
                + " " + initialBirthday.substring(4, initialBirthday.length());

        return birthdayToDisplay;
    }

    /**
     * Initialize a @HashMap for every integer to the correct month.
     */
    private HashMap<String, String> initializeMonthHashMap () {

        HashMap<String, String> monthFormat = new HashMap<>();
        monthFormat.put("01", "Jan");
        monthFormat.put("02", "Feb");
        monthFormat.put("03", "Mar");
        monthFormat.put("04", "Apr");
        monthFormat.put("05", "May");
        monthFormat.put("06", "Jun");
        monthFormat.put("07", "Jul");
        monthFormat.put("08", "Aug");
        monthFormat.put("09", "Sep");
        monthFormat.put("10", "Oct");
        monthFormat.put("11", "Nov");
        monthFormat.put("12", "Dec");

        return monthFormat;
    }

    /**
     * Binds the individual tags shown for each contact to a different color
     * so that it is clearer for the user.
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach((Tag tag) -> {
            Label tagLabel = new Label(tag.tagName);
            if (tag.tagName.equalsIgnoreCase("friends") || tag.tagName.equalsIgnoreCase("friend")) {
                tagLabel.setStyle("-fx-background-color: green");
            } else {
                tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            }
            tags.getChildren().add(tagLabel);
        });
    }

    private String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[tagNumber]);
            tagNumber++;
        }
        if (tagNumber >= colors.length) {
            tagNumber = 0;
        }

        return tagColors.get(tagValue);
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
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
