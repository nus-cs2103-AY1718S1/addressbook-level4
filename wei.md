# wei
###### /main/java/seedu/address/model/person/Birthday.java
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
        String trimmedBirthday = birthday;
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
###### /main/java/seedu/address/model/person/Picture.java
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
