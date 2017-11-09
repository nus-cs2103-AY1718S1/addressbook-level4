# renkai91
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
        public void setBirthday(Birthday birthday) {
            this.birthday = birthday;
        }

        public Optional<Birthday> getBirthday() {
            return Optional.ofNullable(birthday);
        }
```
###### /java/seedu/address/logic/parser/ParserUtil.java
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
###### /java/seedu/address/model/person/Birthday.java
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
###### /java/seedu/address/model/person/Person.java
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
###### /java/seedu/address/model/person/Person.java
``` java
    @Override
    public ObjectProperty<Picture> pictureProperty() {
        return picture;
    }
    @Override
    public Picture getPicture() {
        return picture.get();
```
###### /java/seedu/address/model/person/Picture.java
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
###### /java/seedu/address/ui/PersonCard.java
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
###### /java/seedu/address/ui/PersonCard.java
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
