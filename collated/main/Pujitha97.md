# Pujitha97
###### \java\seedu\address\logic\commands\AddCommand.java
``` java
            + "[" + PREFIX_DOB + "DATE OF BIRTH] "
            + "[" + PREFIX_GENDER + "GENDER] "
```
###### \java\seedu\address\logic\commands\AddCommand.java
``` java
        public void setDateOfBirth(DateOfBirth dateofbirth) {
            this.dateOfBirth = dateofbirth;
        }

        public DateOfBirth getDateOfBirth() {
            return dateOfBirth;
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Gender getGender() {
            return gender;
        }
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
            + "[" + PREFIX_DOB + "DATE OF BIRTH] "
            + "[" + PREFIX_GENDER + "GENDER] "
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        DateOfBirth updatedDateOfBirth = editPersonDescriptor.getDateOfBirth().orElse(personToEdit.getDateOfBirth());
        Gender updatedGender = editPersonDescriptor.getGender().orElse(personToEdit.getGender());
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setDateOfBirth(DateOfBirth dob) {
            this.dob = dob;
        }

        public Optional<DateOfBirth> getDateOfBirth() {
            return Optional.ofNullable(dob);
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
            ParserUtil.parseDateOfBirth(argMultimap.getValue(PREFIX_DOB))
                    .ifPresent(addPersonOptionalFieldDescriptor::setDateOfBirth);
            ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER))
                    .ifPresent(addPersonOptionalFieldDescriptor::setGender);

            final DateOfBirth dob = addPersonOptionalFieldDescriptor.getDateOfBirth();
            final Gender gender = addPersonOptionalFieldDescriptor.getGender();
```
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
    public static final Prefix PREFIX_DOB = new Prefix("d/");
```
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
    public static final Prefix PREFIX_GENDER = new Prefix("g/");
```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
            ParserUtil.parseDateOfBirth(argMultimap.getValue(PREFIX_DOB))
                    .ifPresent(editPersonDescriptor::setDateOfBirth);
            ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER))
                    .ifPresent(editPersonDescriptor::setGender);
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> dob} into an {@code Optional<Date of Birth>} if {@code dob} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<DateOfBirth> parseDateOfBirth(Optional<String> dob) throws IllegalValueException {
        requireNonNull(dob);
        return dob.isPresent() ? Optional.of(new DateOfBirth(dob.get())) : Optional.empty();
    }
    /**
     * Parses a {@code Optional<String> gender} into an {@code Optional<Gender>} if {@code gender} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Gender> parseGender(Optional<String> gender) throws IllegalValueException {
        requireNonNull(gender);
        return gender.isPresent() ? Optional.of(new Gender(gender.get())) : Optional.empty();
    }
```
###### \java\seedu\address\model\person\DateOfBirth.java
``` java
    /**
     * Validates given Date of Birth.
     *
     * @throws IllegalValueException if given date of birth string is invalid.
     */
    public DateOfBirth(String dob) throws IllegalValueException {
        requireNonNull(dob);
        if (dob.isEmpty()) {
            throw new EmptyFieldException(PREFIX_DOB);
        }
        if (!isValidDateOfBirth(dob)) {
            throw new IllegalValueException(MESSAGE_DOB_CONSTRAINTS);
        }
```
###### \java\seedu\address\model\person\DateOfBirth.java
``` java
    /**
     * Returns true if a given string is a valid person date of birth.
     */
    public static boolean isValidDateOfBirth(String test) {
        return test.matches(DOB_VALIDATION_REGEX);
    }
```
###### \java\seedu\address\model\person\DateOfBirth.java
``` java
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateOfBirth // instanceof handles nulls
                && this.dateOfBirth.equals(((DateOfBirth) other).dateOfBirth)); // state check
    }

    @Override
    public int hashCode() {
        return dateOfBirth.hashCode();
    }
}
//@author
```
###### \java\seedu\address\model\person\Gender.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.EmptyFieldException;
/**
 * Represents a Person's gender in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */
public class Gender {

    /**
     * Different ENUM constants available for gender type
     */
    enum GenderType {
        NOT_SPECIFIED, MALE, FEMALE, OTHER
    }

    public static final String MESSAGE_GENDER_CONSTRAINTS =
            "Person's gender can take either Male , Female or Other and it should not be blank";

    /*
     * The first character of the gender must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    private static final String GENDER_VALIDATION_REGEX = "(?i)\\b(female|f|male|m|other|o|"
            + "notspecified|not_specified|not specified)\\b";

    public final GenderType value;
    private boolean genderset;

    /**
     * Initialise a Gender object with value of empty String. This can ONLY be used in the default field of
     * {@code AddPersonOptionalFieldDescriptor}
     */
    public Gender() {
        this.genderset = false;
        this.value = GenderType.NOT_SPECIFIED;
    }

    /**
     * Validates given gender.
     *
     * @throws IllegalValueException if given gender string is invalid.
     */
    public Gender(String gen) throws IllegalValueException {
        requireNonNull(gen);
        if (gen.isEmpty()) {
            throw new EmptyFieldException(PREFIX_GENDER);
        }
        if (!isValidGender(gen)) {
            throw new IllegalValueException(MESSAGE_GENDER_CONSTRAINTS);
        }
        switch (gen.toLowerCase()) {
        case ("female"):
        case ("f"):
            this.value = GenderType.FEMALE;
            break;
        case ("male"):
        case ("m"):
            this.value = GenderType.MALE;
            break;
        case ("other"):
        case("o"):
            this.value = GenderType.OTHER;
            break;
        default:
            this.value = GenderType.NOT_SPECIFIED;
        }
        this.genderset = true;
    }

    /**
     * Returns true if a given string is a valid person gender.
     */
    public static boolean isValidGender(String test) {
        return test.matches(GENDER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return genderset ? value.toString() : "";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Gender // instanceof handles nulls
                && this.value.equals(((Gender) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setDateOfBirth(DateOfBirth dob) {
        this.dob.set(requireNonNull(dob));
    }

    @Override
    public ObjectProperty<DateOfBirth> dobProperty() {
        return dob;
    }

    @Override
    public DateOfBirth getDateOfBirth() {
        return dob.get();
    }

    public void setGender(Gender gender) {
        this.gender.set(requireNonNull(gender));
    }

    @Override
    public ObjectProperty<Gender> genderProperty() {
        return gender;
    }

    @Override
    public Gender getGender() {
        return gender.get();
    }
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    ObjectProperty<DateOfBirth> dobProperty();
    DateOfBirth getDateOfBirth();
    ObjectProperty<Gender> genderProperty();
    Gender getGender();
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    @XmlElement(required = true)
    private String dob;
    @XmlElement(required = true)
    private String gender;
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        gender = source.getGender().toString();
        dob = source.getDateOfBirth().toString();
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        final DateOfBirth dob = this.dob.equals("") ? new DateOfBirth() : new DateOfBirth(this.dob);
        final Gender gender = this.gender.equals("") ? new Gender() : new Gender(this.gender);
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    @FXML
    private Label dob;
    @FXML
    private Label gender;
```
###### \java\seedu\address\ui\PersonCard.java
``` java
        dob.textProperty().bind(Bindings.convert(person.dobProperty()));
        gender.textProperty().bind(Bindings.convert(person.genderProperty()));
```
###### \java\seedu\address\ui\ProfilePanel.java
``` java
    @FXML
    private Label dob;
    @FXML
    private Label gender;
```
###### \java\seedu\address\ui\ProfilePanel.java
``` java
        dob.textProperty().bind(Bindings.convert(person.dobProperty()));
        gender.textProperty().bind(Bindings.convert(person.genderProperty()));
```
