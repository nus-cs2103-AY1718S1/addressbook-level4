# Lenaldnwj
###### /java/seedu/address/logic/parser/AddCommandParser.java
``` java
    /**
     * Returns a appended String message stating that specific optional inputs are not recorded if user decides
     * to not enter any of the optional inputs.
     */
    public static String optionalInput(String input) {
        if (!input.contains("a/")) {
            input = input + " a/ (Address not recorded)";
        }
        if (!input.contains("e/")) {
            input = input + " e/ (Email not recorded)";
        }
        if (!input.contains("c/")) {
            input = input + " c/ (Postal code not recorded)";
        }
        if (!input.contains(" p/")) {
            input = input + " p/ (Student phone not recorded)";
        }
        return input;
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> parentPhone} into an {@code Optional<ParentPhone>} if {@code parentPhone}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<ParentPhone> parseParentPhone(Optional<String> parentPhone) throws IllegalValueException {
        requireNonNull(parentPhone);
        return parentPhone.isPresent() ? Optional.of(new ParentPhone(parentPhone.get())) : Optional.empty();
    }
```
###### /java/seedu/address/model/person/Email.java
``` java

    public static final String MESSAGE_EMAIL_CONSTRAINTS =
            "Person emails should be 2 alphanumeric/period strings separated by '@'";
    public static final String EMAIL_VALIDATION_REGEX = "([\\w\\.]+@[\\w\\.]+)|(\\(Email not recorded\\))";
    public final String value;

    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public Email(String email) throws IllegalValueException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!isValidEmail(trimmedEmail)) {
            throw new IllegalValueException(MESSAGE_EMAIL_CONSTRAINTS);
        }
        this.value = trimmedEmail;
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(EMAIL_VALIDATION_REGEX);
    }
```
###### /java/seedu/address/model/person/ParentPhone.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's ParentPhone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidParentPhone(String)}
 */
public class ParentPhone {

    public static final String MESSAGE_PARENTPHONE_CONSTRAINTS = "Parent numbers should be exactly 8 digits long";

    public static final String PARENTPHONE_VALIDATION_REGEX = "(\\d\\d\\d\\d\\d\\d\\d\\d)";

    public final String value;

    /**
     * Validates given ParentPhone.
     *
     * @throws IllegalValueException if given ParentPhone string is invalid.
     */
    public ParentPhone(String parentPhone) throws IllegalValueException {
        requireNonNull(parentPhone);
        String trimmedParentPhone = parentPhone.trim();
        if (!isValidParentPhone(trimmedParentPhone)) {
            throw new IllegalValueException(MESSAGE_PARENTPHONE_CONSTRAINTS);
        }
        this.value = trimmedParentPhone;
    }

    /**
     * Returns true if a given string is a valid parentPhone name.
     */
    public static boolean isValidParentPhone(String test) {
        return test.matches(PARENTPHONE_VALIDATION_REGEX);
    }
    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ParentPhone // instanceof handles nulls
                && this.value.equals(((ParentPhone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


}
```
###### /java/seedu/address/model/person/Person.java
``` java
    public void setParentPhone(ParentPhone parentPhone) {
        this.parentPhone.set(requireNonNull(parentPhone));
    }

    @Override
    public ObjectProperty<ParentPhone> parentPhoneProperty() {
        return parentPhone;
    }

    @Override
    public ParentPhone getParentPhone() {
        return parentPhone.get();
    }
```
###### /java/seedu/address/model/person/Phone.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {

    public static final String MESSAGE_PHONE_CONSTRAINTS =
            "Phone numbers can only contain numbers, and should be exactly 8 digits";
    public static final String PHONE_VALIDATION_REGEX = "(\\d\\d\\d\\d\\d\\d\\d\\d)"
            + "|(\\(Student phone not recorded\\))";

    public final String value;

    /**
     * Validates the student phone number.
     *
     * @throws IllegalValueException if student phone string is of invalid format.
     */
    public Phone(String phone) throws IllegalValueException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!isValidPhone(trimmedPhone)) {
            throw new IllegalValueException(MESSAGE_PHONE_CONSTRAINTS);
        }
        this.value = trimmedPhone;
    }

    /**
     * Returns true if a given string is a valid student phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(PHONE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone // instanceof handles nulls
                && this.value.equals(((Phone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/ui/ExtendedPersonCard.java
``` java
    /**
     * Displays text description of the icons when cherBook starts up
     */
    protected void loadIconDescriptionOnStartUp() {
        name.setText("Name of student");
        phone.setText("Phone number of student");
        parentPhone.setText("Phone number of parent");
        address.setText("Address of student");
        formClass.setText("Form class of student");
        grades.setText("Grades of student");
        postalCode.setText("Postal code of student");
        email.setText("Email of student");
        remark.setText("Remark of student");
    }

```
###### /java/seedu/address/ui/PersonCard.java
``` java
    private static final String FXML = "PersonListCard.fxml";

    private static HashMap<String, String> currentTagColors = new HashMap<String, String>();

    private static String assignedColor;

    private static ArrayList<String> usedColors = new ArrayList<>();

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
    @FXML
    private Label phone;
    @FXML
    private Label parentPhone;
    @FXML
    private Label formClass;
    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initialiseTags(person);
        bindListeners(person);
    }

    /**
     * This method takes in the tagName, and returns the color associated with that tagName
     * If the if the tag has no associated color, a unique random color will be assigned to the tag.
=     *
     * @param tagName is the String name of the tag
     * @return the color associated to the tagName
     */
    public static String obtainTagColors(String tagName) {
        if (!currentTagColors.containsKey(tagName)) {
            do {
                Random random = new Random();
                final float hue = random.nextFloat();
                final float saturation = 0.65f + random.nextFloat()
                        * (0.90f - 0.65f);
                final float luminance = 0.60f + random.nextFloat()
                        * (0.90f - 0.60f);

                Color color = Color.getHSBColor(hue, saturation, luminance);

                Formatter hexRepresentation = new Formatter(new StringBuffer("#"));
                hexRepresentation.format("%02X", color.getRed());
                hexRepresentation.format("%02X", color.getGreen());
                hexRepresentation.format("%02X", color.getBlue());
                assignedColor = hexRepresentation.toString();
            } while (usedColors.contains(assignedColor));

            usedColors.add(assignedColor);
            currentTagColors.put(tagName, assignedColor);
        }
        return currentTagColors.get(tagName);
    }

    /**
     * To access private String assignedColor for testing
     */
    public String getAssignedTagColor() {
        return this.assignedColor;
    }

    /**
     * To access private ArrayList usedColor for testing
     */
    public ArrayList getUsedColor() {
        return this.usedColors;
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        parentPhone.textProperty().bind(Bindings.convert(person.parentPhoneProperty()));
        formClass.textProperty().bind(Bindings.convert(person.formClassProperty()));
        tags.getChildren().clear();
        initialiseTags(person);
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
    /**
     * Initialise the {@code person} tags
     *
     * @param person Person to be assigned tag colour.
     */
    private void initialiseTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);

            tagLabel.setStyle("-fx-background-color: " + obtainTagColors(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

}
```
###### /resources/view/PersonListCard.fxml
``` fxml
      <Label fx:id="phone" lineSpacing="10.0" styleClass="cell_small_label" text="\$phone">
        <graphic>
          <ImageView>
            <image>
              <Image url="@../images/studentPhone.png" />
            </image>
          </ImageView>
        </graphic>
        <padding>
          <Insets bottom="5.0" />
        </padding>
        <font>
          <Font size="14.0" />
        </font>
      </Label>

      <Label fx:id="parentPhone" lineSpacing="10.0" styleClass="cell_small_label" text="\$parentPhone">
        <graphic>
          <ImageView>
            <image>
              <Image url="@../images/parentPhone.png" />
            </image>
          </ImageView>
        </graphic>
        <padding>
          <Insets bottom="5.0" />
        </padding>
        <font>
          <Font size="14.0" />
        </font>
      </Label>

      <Label fx:id="formClass" lineSpacing="10.0" styleClass="cell_small_label" text="\$formClass">
        <graphic>
          <ImageView>
            <image>
              <Image url="@../images/formClass.png" />
            </image>
          </ImageView>
        </graphic>
        <padding>
          <Insets bottom="5.0" />
        </padding>
        <font>
          <Font size="14.0" />
        </font>
      </Label>

    </VBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
  </GridPane>
</HBox>
```
