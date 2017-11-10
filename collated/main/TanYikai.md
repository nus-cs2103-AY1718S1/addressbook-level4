# TanYikai
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
/**
 * Lists all sorted persons in the address book to the user.
 */
public class SortCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "st";
    public static final String SHORT_MESSAGE_USAGE = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Sorted all persons";

    @Override
    public CommandResult executeUndoableCommand() {
        model.sortPersons();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * If {@code phone} is not present, {@code String Unspecified phone number} is given
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parseAddPhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.of(Phone.UNSPECIFIED);
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * If {@code address} is not present, {@code String Unspecified address} is given
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.of(Address.UNSPECIFIED);
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * If {@code email} is not present, {@code String Unspecified email} is given
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseAddEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.of(Email.UNSPECIFIED);
    }

    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(new Remark(remark.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * If {@code remark} is not present, {@code String Unspecified remark} is given
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseAddRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(new Remark(remark.get())) : Optional.of(Remark.UNSPECIFIED);
    }
```
###### \java\seedu\address\model\person\Address.java
``` java
    /**
     * The default Address constructor when address is not specified by the user
     */
    private Address() {
        value = "Unspecified address";
    }
```
###### \java\seedu\address\model\person\Phone.java
``` java
    /**
     * The default Phone constructor when phone is not specified by the user
     */
    private Phone() {
        value = "Unspecified phone number";
    }
```
###### \java\seedu\address\model\person\Remark.java
``` java
/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Remark {

    public static final Remark UNSPECIFIED = new Remark();
    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Person remarks can take any values, can even be blank";

    public final String value;

    private Remark() {
        value = "Unspecified remark";
    }

    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sorts the persons object in the list alphanumerically by name.
     */
    public void sort() {
        requireNonNull(internalList);
        Collections.sort(internalList);
    }
```
