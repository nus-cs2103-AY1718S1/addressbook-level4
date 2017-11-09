# renkai91
###### /src/main/java/seedu/address/logic/commands/EditCommand.java
``` java
        public void setBirthday(Birthday birthday) {
            this.birthday = birthday;
        }

        public Optional<Birthday> getBirthday() {
            return Optional.ofNullable(birthday);
        }
```
###### /src/main/java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> birthday} into an {@code Optional<Birthday>} if {@code Birthday} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Birthday> parseBirthday(Optional<String> birthday) throws IllegalValueException {
        requireNonNull(birthday);
        return birthday.isPresent() ? Optional.of(new Birthday(birthday.get())) : Optional.empty();
    }
```
###### /src/main/java/seedu/address/model/person/Birthday.java
``` java
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS = "Person's birthday should be in format: DD/MM/YYYY";

    public static final String BIRTHDAY_VALIDATION_REGEX = "\\d\\d/\\d\\d/\\d\\d\\d\\d";

    public final String value;

    /**
 * Validates given birthday.
 *
 * @throws IllegalValueException if given birthday address string is invalid.
 */

    public Birthday(String birthday) throws IllegalValueException {
        String trimmedBirthday = (birthday != null) ? birthday : "01/01/2001";

        if (birthday != null && !isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.value = trimmedBirthday;
    }

    /**
     * Returns if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        // instanceof handles nulls
        // state check
        return other == this || (other instanceof Birthday && this.value.equals(((Birthday) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /src/main/java/seedu/address/model/person/Person.java
``` java
    @Override
    public Address getAddress() {
        return address.get();
    }

    public void setBirthday(Birthday birthday) {
        this.birthday.set(requireNonNull(birthday));
    }

    @Override
    public ObjectProperty<Birthday> birthdayProperty() {
        return birthday;
    }

    @Override
    public Birthday getBirthday() {
        return birthday.get();
    }
```
###### /src/main/java/seedu/address/model/person/Person.java
``` java
    @Override
    public ObjectProperty<Picture> pictureProperty() {
        return picture;
    }
    @Override
    public Picture getPicture() {
        return picture.get();
```
###### /src/main/java/seedu/address/model/person/Picture.java
``` java
public class Picture {

    public static final int PIC_WIDTH = 100;
    public static final int PIC_HEIGHT = 100;

    public static final String BASE_URL = System.getProperty("user.dir") + "/src/main/resources/contact_images/";

    public static final String PLACEHOLDER_IMAG = "test1.png";

    private String pictureUrl;

    public Picture() {
        this.pictureUrl = BASE_URL + PLACEHOLDER_IMAG;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = BASE_URL + pictureUrl;
    }
}
```
###### /src/main/java/seedu/address/ui/PersonCard.java
``` java
    /**
     * Menu list option: add image
     * Raises PersonPanelOptionsDelete, handled by UIManager
     * Handle Delete user
     */
    @FXML
    public void handleAddImage() {
        FileChooser picChooser = new FileChooser();
        File selectedPic = picChooser.showOpenDialog(null);
        if (selectedPic != null) {
            try {
                person.getPicture().setPictureUrl(person.getName().toString() + person.getPhone().toString() + ".jpg");
                ImageIO.write(ImageIO.read(selectedPic), "jpg", new File(person.getPicture().getPictureUrl()));
                FileInputStream fileStream = new FileInputStream(person.getPicture().getPictureUrl());
                Image newPicture = new Image(fileStream);
                picture.setImage(newPicture);
            } catch (Exception e) {
                System.out.println(e + "Invalid File");
            }
        } else {
            System.out.println("Invalid File");
        }
    }
```
###### /src/main/java/seedu/address/ui/PersonCard.java
``` java
    /**
     * Initialize image for ever person
     */
    private void initImage() {
        try {
            File picFile = new File(person.getPicture().getPictureUrl());
            FileInputStream fileStream = new FileInputStream(picFile);
            Image personPicture = new Image(fileStream);
            picture.setFitHeight(person.getPicture().PIC_HEIGHT);
            picture.setFitWidth(person.getPicture().PIC_WIDTH);
            picture.setImage(personPicture);
            cardPane.getChildren().add(picture);
        } catch (Exception e) {
            System.out.println("Image not found");
        }
    }
```
###### /src/test/java/seedu/address/logic/parser/AddQuickCommandParserTest.java
``` java
        // multiple birthdays - last birthday accepted
        assertParseSuccess(parser, AddQuickCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + BIRTHDAY_DESC_AMY + BIRTHDAY_DESC_BOB + TAG_DESC_FRIEND,
                new AddQuickCommand(expectedPerson));
```
###### /src/test/java/seedu/address/logic/parser/AddQuickCommandParserTest.java
``` java
        // invalid birthday
        assertParseFailure(parser, AddQuickCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + INVALID_BIRTHDAY_DESC + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND,
                Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
```
###### /src/test/java/seedu/address/testutil/EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Birthday} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withBirthday(String birthday) {
        try {
            ParserUtil.parseBirthday(Optional.of(birthday)).ifPresent(descriptor::setBirthday);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("birthday is expected to be unique.");
        }
        return this;
    }
```
###### /src/test/java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets the {@code Birthday} of the {@code Person} that we are building.
     */
    public PersonBuilder withBirthday(String birthday) {
        try {
            this.person.setBirthday(new Birthday(birthday));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("birthday is expected to be unique.");
        }
        return this;
    }
```
###### /src/test/java/systemtests/AddQuickCommandSystemTest.java
``` java
        /* Case: add a person with all fields same as another person in the address book except birthday -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_BOB).withTags(VALID_TAG_FRIEND).build();
        command = AddQuickCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + BIRTHDAY_DESC_BOB + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);
```
###### /src/test/java/systemtests/AddQuickCommandSystemTest.java
``` java
        /* Case: invalid birthday -> rejected */
        command = AddQuickCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + INVALID_BIRTHDAY_DESC;
        assertCommandFailure(command, Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
```
###### /src/test/java/systemtests/EditCommandSystemTest.java
``` java
//  /* Case: invalid birthday -> rejected */
//        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
//                        + INVALID_BIRTHDAY_DESC,
//                Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
```
