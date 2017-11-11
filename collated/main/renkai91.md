# renkai91
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
                if (person.getPicture().checkJarResourcePath()) {
                    ImageIO.write(ImageIO.read(selectedPic), "jpg", new File(person.getPicture().getJarPictureUrl()));
                    FileInputStream fileStream = new FileInputStream(person.getPicture().getJarPictureUrl());
                    Image newPicture = new Image(fileStream);
                    picture.setImage(newPicture);
                } else {
                    ImageIO.write(ImageIO.read(selectedPic), "jpg", new File(person.getPicture().getPictureUrl()));
                    FileInputStream fileStream = new FileInputStream(person.getPicture().getPictureUrl());
                    Image newPicture = new Image(fileStream);
                    picture.setImage(newPicture);
                }
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
            try {
                InputStream in = this.getClass().getResourceAsStream(person.getPicture().getJarPictureUrl());
                person.getPicture().setJarResourcePath();
                Image personPicture = new Image(in);
                picture.setImage(personPicture);
            } catch (Exception e) {
                File picFile = new File(person.getPicture().getPictureUrl());
                FileInputStream fileStream = new FileInputStream(picFile);
                Image personPicture = new Image(fileStream);
                picture.setImage(personPicture);
            }
            picture.setFitHeight(person.getPicture().PIC_HEIGHT);
            picture.setFitWidth(person.getPicture().PIC_WIDTH);

            cardPane.getChildren().add(picture);
        } catch (Exception e) {
            System.out.println("Image not found");
        }
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
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
        public void setBirthday(Birthday birthday) {
            this.birthday = birthday;
        }

        public Optional<Birthday> getBirthday() {
            return Optional.ofNullable(birthday);
        }
```
###### /java/seedu/address/model/person/Picture.java
``` java
public class Picture {

    public static final int PIC_WIDTH = 100;
    public static final int PIC_HEIGHT = 100;

    public static final String BASE_URL = System.getProperty("user.dir") + "/data/contact_images/";

    public static final String PLACEHOLDER_IMAGE = System.getProperty("user.dir") + "/src/main/resources/test1.png";

    public static final String BASE_JAR_URL = System.getProperty("user.dir");

    public static final String PLACEHOLDER_JAR_URL = "/images/test1.png";

    private String pictureUrl;
    private String jarPictureUrl;
    private boolean jarResourcePath;

    public Picture() {
        this.pictureUrl = PLACEHOLDER_IMAGE;
        this.jarPictureUrl = PLACEHOLDER_JAR_URL;
        this.jarResourcePath = false;

    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getJarPictureUrl() {
        return jarPictureUrl;
    }
    public void setJarResourcePath() {
        this.jarResourcePath = true;
    }
    public boolean checkJarResourcePath() {
        return this.jarResourcePath;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = BASE_URL + pictureUrl;
        this.jarPictureUrl = BASE_JAR_URL + pictureUrl;
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
    }
```
###### /java/seedu/address/model/person/Birthday.java
``` java
/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS = "Person's birthday should be in format: DD/MM/YYYY";
    public static final String MESSAGE_BIRTHDAY2_CONSTRAINTS = "Year must be greater than 0000";
    public static final String MESSAGE_BIRTHDAY3_CONSTRAINTS = "Month must be between 01 to 12";
    public static final String MESSAGE_BIRTHDAY4_CONSTRAINTS = "Day must be between 01 to 31";
    public static final String MESSAGE_BIRTHDAY5_CONSTRAINTS = "Only month 01, 03, 05, 07, 08, 10, 12 have 31 days";
    public static final String MESSAGE_BIRTHDAY6_CONSTRAINTS = "Feb do not have 30 or 31 days";
    public static final String MESSAGE_BIRTHDAY7_CONSTRAINTS = "Not leap year, got Feb no 29,31 or 31 days";

    public static final String BIRTHDAY_VALIDATION_REGEX = "\\d\\d/\\d\\d/\\d\\d\\d\\d";
    public static final String NO_BIRTHDAY_DEFAULT = "00000000";
    public final String value;
    /*** * Validates given birthday.
    * *
    *
    * @throws IllegalValueException if given birthday address string is invalid.
    */

    public Birthday(String birthday) throws IllegalValueException {
        String trimmedBirthday = (birthday != null) ? birthday : "01/01/1991";

        if (isValidBirthday(trimmedBirthday) && !birthday.equals("No birthday")) {
            String yes2 = trimmedBirthday.replaceAll("[/]", "");

            isValidBirthdayValue(yes2);

            if (yes2.equals(NO_BIRTHDAY_DEFAULT)) {
                this.value = "No birthday";
            } else {
                this.value = trimmedBirthday;
            }
        } else if (birthday.equals("No birthday")) {
            this.value = "No birthday";
        } else {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
    }

    /**
     * Returns if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
    }

    /**
     * Check validity of input values
     */

    public static void isValidBirthdayValue (String birthdayString) throws IllegalValueException {

        if (birthdayString.equals(NO_BIRTHDAY_DEFAULT)) {
            return;
        }

        int result = Integer.parseInt(birthdayString);
        int year = result % 10000;
        int month = ((result % 1000000) - year) / 10000;
        int day = result / 1000000;

        if (year < 1) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY2_CONSTRAINTS);
        } else if (month > 12 || month < 1) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY3_CONSTRAINTS);
        } else if (day > 31 || day < 1) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY4_CONSTRAINTS);
        }


        if ((day == 31) && ((month == 4) || (month == 6) || (month == 9) || (month == 11))) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY5_CONSTRAINTS);
        } else if (month == 2) {
            //leap year
            if ((year % 4) == 0) {
                if ((day == 30) || (day == 31)) {
                    throw new IllegalValueException(MESSAGE_BIRTHDAY6_CONSTRAINTS);
                }
            } else {
                if ((day == 29) || (day == 30) || (day == 31)) {
                    throw new IllegalValueException(MESSAGE_BIRTHDAY7_CONSTRAINTS);
                }
            }
        }
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
