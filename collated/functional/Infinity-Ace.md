# Infinity Ace
###### /java/seedu/address/logic/commands/AddCommand.java
``` java
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOK_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_NOK_NAME + "NOK NAME "
            + PREFIX_NOK_PHONE + "NOK PHONE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_NOK_NAME + "Peter Doe "
            + PREFIX_NOK_PHONE + "87654321 "
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOK_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.NokName;
import seedu.address.model.person.NokPhone;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_NOK_NAME + "NOK NAME] "
            + "[" + PREFIX_NOK_PHONE + "NOK PHONE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        NokName updatedNokName = editPersonDescriptor.getNokName().orElse(personToEdit.getNokName());
        NokPhone updatedNokPhone = editPersonDescriptor.getNokPhone().orElse(personToEdit.getNokPhone());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedNokName, updatedNokPhone,
                          updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private NokName nokName;
        private NokPhone nokPhone;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            this.name = toCopy.name;
            this.phone = toCopy.phone;
            this.email = toCopy.email;
            this.address = toCopy.address;
            this.nokName = toCopy.nokName;
            this.nokPhone = toCopy.nokPhone;
            this.tags = toCopy.tags;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.phone, this.email, this.address, this.nokName,
                                               this.nokPhone, this.tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setNokName(NokName nokName) {
            this.nokName = nokName;
        }

        public Optional<NokName> getNokName() {
            return Optional.ofNullable(nokName);
        }

        public void setNokPhone(NokPhone nokPhone) {
            this.nokPhone = nokPhone;
        }

        public Optional<NokPhone> getNokPhone() {
            return Optional.ofNullable(nokPhone);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = tags;
        }

        public Optional<Set<Tag>> getTags() {
            return Optional.ofNullable(tags);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getNokName().equals(e.getNokName())
                    && getNokPhone().equals(e.getNokPhone())
```
###### /java/seedu/address/logic/parser/AddCommandParser.java
``` java
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOK_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.NokName;
import seedu.address.model.person.NokPhone;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        Email email;
        Address address;
        NokName nokName;
        NokPhone nokPhone;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_AVATAR, PREFIX_NOK_NAME, PREFIX_NOK_PHONE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            Avatar avatar = ParserUtil.parseAvatar(argMultimap.getAvatarValue(PREFIX_AVATAR));

            // Gets the email of the new person if possible. If not, it creates a null value
            Optional<Email> optionalEmail = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL));
            if (optionalEmail.isPresent()) {
                email = optionalEmail.get();
            } else {
                email = new Email(null);
            }

            // Gets the address of the new person if possible. If not, it creates a null value
            Optional<Address> optionalAddress = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS));
            if (optionalAddress.isPresent()) {
                address = optionalAddress.get();
            } else {
                address = new Address(null);
            }

            // Gets the Nok Name of the new person if possible. If not, it creates a null value
            Optional<NokName> optionalNokName = ParserUtil.parseNokName(argMultimap.getValue(PREFIX_NOK_NAME));
            if (optionalNokName.isPresent()) {
                nokName = optionalNokName.get();
            } else {
                nokName = new NokName(null);
            }

            // Gets the Nok Name of the new person if possible. If not, it creates a null value
            Optional<NokPhone> optionalNokPhone = ParserUtil.parseNokPhone(argMultimap.getValue(PREFIX_NOK_PHONE));
            if (optionalNokPhone.isPresent()) {
                nokPhone = optionalNokPhone.get();
            } else {
                nokPhone = new NokPhone(null);
            }

            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            ReadOnlyPerson person = new Person(name, phone, email, address, avatar, nokName, nokPhone, tagList);
```
###### /java/seedu/address/logic/parser/CliSyntax.java
``` java
    public static final Prefix PREFIX_NOK_NAME = new Prefix("nokn/");
    public static final Prefix PREFIX_NOK_PHONE = new Prefix("nokp/");
```
###### /java/seedu/address/logic/parser/EditCommandParser.java
``` java
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOK_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                                           PREFIX_NOK_NAME, PREFIX_NOK_PHONE, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        try {
            ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).ifPresent(editPersonDescriptor::setName);
            ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).ifPresent(editPersonDescriptor::setPhone);
            ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).ifPresent(editPersonDescriptor::setEmail);
            ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).ifPresent(editPersonDescriptor::setAddress);
            ParserUtil.parseNokName(argMultimap.getValue(PREFIX_NOK_NAME)).ifPresent(editPersonDescriptor::setNokName);
            ParserUtil.parseNokPhone(argMultimap.getValue(PREFIX_NOK_PHONE))
                    .ifPresent(editPersonDescriptor::setNokPhone);
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
import seedu.address.model.person.NokName;
import seedu.address.model.person.NokPhone;
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
  public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> avatar} into an {@code Optional<Avatar>} if {@code avatar} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Avatar parseAvatar(String avatar) throws IllegalValueException, IOException {
        requireNonNull(avatar);
        return new Avatar(avatar);
    }

    /**
     * Parses a {@code Optional<String> nokName} into an {@code Optional<NokName>} if {@code nokName} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<NokName> parseNokName(Optional<String> nokName) throws IllegalValueException {
        return nokName.isPresent() ? Optional.of(new NokName(nokName.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> nokPhone} into an {@code Optional<NokPhone>} if {@code nokPhone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<NokPhone> parseNokPhone(Optional<String> nokPhone) throws IllegalValueException {
        return nokPhone.isPresent() ? Optional.of(new NokPhone(nokPhone.get())) : Optional.empty();
    }
```
###### /java/seedu/address/model/person/Address.java
``` java
package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address {

    public static final String MESSAGE_ADDRESS_CONSTRAINTS =
            "Person addresses can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ADDRESS_VALIDATION_REGEX = "[^\\s].*";
    public static final String ADDRESS_UNDEFINED_DEFAULT = "NIL";

    public final String value;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Address(String address) throws IllegalValueException {
        if (address == null) {
            this.value = ADDRESS_UNDEFINED_DEFAULT;
        } else {
            if (!isValidAddress(address)) {
                throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
            }
            this.value = address;
        }
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX) || test.equals(ADDRESS_UNDEFINED_DEFAULT);
    }
```
###### /java/seedu/address/model/person/Email.java
``` java
package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    public static final String MESSAGE_EMAIL_CONSTRAINTS =
            "Person emails should be 2 alphanumeric/period strings separated by '@'";
    public static final String EMAIL_VALIDATION_REGEX = "[\\w\\.]+@[\\w\\.]+";
    public static final String EMAIL_UNDEFINED_DEFAULT = "NIL";

    public final String value;

    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public Email(String email) throws IllegalValueException {
        if (email == null) {
            this.value = EMAIL_UNDEFINED_DEFAULT;
        } else {
            if (!isValidEmail(email)) {
                throw new IllegalValueException(MESSAGE_EMAIL_CONSTRAINTS);
            }
            this.value = email;
        }
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(EMAIL_VALIDATION_REGEX) || test.equals(EMAIL_UNDEFINED_DEFAULT);
    }
```

###### /java/seedu/address/model/person/NokName.java
``` java
package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's Next-of-Kin's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNokName(String)}
 */
public class NokName {

    public static final String MESSAGE_NOK_NAME_CONSTRAINTS =
            "NOK names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NOK_NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String NOK_NAME_UNDEFINED_DEFAULT = "NIL";

    public final String value;

    /**
     * Validates given NOK name.
     *
     * @throws IllegalValueException if given NOK name string is invalid.
     */
    public NokName(String nokName) throws IllegalValueException {
        if (nokName == null) {
            this.value = NOK_NAME_UNDEFINED_DEFAULT;
        } else {
            if (!isValidNokName(nokName)) {
                throw new IllegalValueException(MESSAGE_NOK_NAME_CONSTRAINTS);
            }
            this.value = nokName;
        }
    }

    /**
     * Returns true if a given string is a valid NOK name.
     */
    public static boolean isValidNokName(String test) {
        return test.matches(NOK_NAME_VALIDATION_REGEX) || test.equals(NOK_NAME_UNDEFINED_DEFAULT);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NokName // instanceof handles nulls
                && this.value.equals(((NokName) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

```
###### /java/seedu/address/model/person/NokPhone.java
``` java
package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's Next-of-Kin's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNokPhone(String)}
 */
public class NokPhone {


    public static final String MESSAGE_NOK_PHONE_CONSTRAINTS =
            "NOK Phone numbers can only contain numbers, and should be at least 3 digits long";
    public static final String NOK_PHONE_VALIDATION_REGEX = "\\d{3,}";
    public static final String NOK_PHONE_UNDEFINED_DEFAULT = "NIL";

    public final String value;

    /**
     * Validates given NOK phone number.
     *
     * @throws IllegalValueException if given NOK phone string is invalid.
     */
    public NokPhone(String nokPhone) throws IllegalValueException {
        if (nokPhone == null) {
            this.value = NOK_PHONE_UNDEFINED_DEFAULT;
        } else {
            if (!isValidNokPhone(nokPhone)) {
                throw new IllegalValueException(MESSAGE_NOK_PHONE_CONSTRAINTS);
            }
            this.value = nokPhone;
        }
    }

    /**
     * Returns true if a given string is a valid NOK phone number.
     */
    public static boolean isValidNokPhone(String test) {
        return test.matches(NOK_PHONE_VALIDATION_REGEX) || test.equals(NOK_PHONE_UNDEFINED_DEFAULT);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NokPhone // instanceof handles nulls
                && this.value.equals(((NokPhone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

```
###### /java/seedu/address/model/person/Person.java
``` java
 private ObjectProperty<NokName> nokName;
    private ObjectProperty<NokPhone> nokPhone;

    private ObjectProperty<UniqueTagList> tags;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, NokName nokName, NokPhone nokPhone,
                  Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, nokName, nokPhone, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.nokName = new SimpleObjectProperty<>(nokName);
        this.nokPhone = new SimpleObjectProperty<>(nokPhone);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getNokName(),
                source.getNokPhone(), source.getTags());
    }

    public void setName(Name name) {
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<Name> nameProperty() {
        return name;
    }

    @Override
    public Name getName() {
        return name.get();
    }

    public void setPhone(Phone phone) {
        this.phone.set(requireNonNull(phone));
    }

    @Override
    public ObjectProperty<Phone> phoneProperty() {
        return phone;
    }

    @Override
    public Phone getPhone() {
        return phone.get();
    }

    public void setEmail(Email email) {
        this.email.set(requireNonNull(email));
    }

    @Override
    public ObjectProperty<Email> emailProperty() {
        return email;
    }

    @Override
    public Email getEmail() {
        return email.get();
    }

    public void setAddress(Address address) {
        this.address.set(requireNonNull(address));
    }

    @Override
    public ObjectProperty<Address> addressProperty() {
        return address;
    }

    @Override
    public Address getAddress() {
        return address.get();
    }

    public void setNokName(NokName nokName) {
        this.nokName.set(requireNonNull(nokName));
    }

    @Override
    public ObjectProperty<NokName> nokNameProperty() {
        return nokName;
    }

    @Override
    public NokName getNokName() {
        return nokName.get();
    }

    public void setNokPhone(NokPhone nokPhone) {
        this.nokPhone.set(requireNonNull(nokPhone));
    }

    @Override
    public ObjectProperty<NokPhone> nokPhoneProperty() {
        return nokPhone;
    }

    @Override
    public NokPhone getNokPhone() {
        return nokPhone.get();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.get().toSet());
    }

    public ObjectProperty<UniqueTagList> tagProperty() {
        return tags;
    }

    /**
     * Replaces this person's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Tag> replacement) {
        tags.set(new UniqueTagList(replacement));
    }

    /**
     * Removes a tag from this person's tag list.
     */
    public boolean removeTags(Tag tag) {
        Set<Tag> tagList = tags.get().toSet();
        if (tagList != null) {
            if (tagList.remove(tag)) {
                setTags(tagList);
                return true;
            }
            ;
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, nokName, nokPhone, tags);
```
###### /java/seedu/address/model/person/ReadOnlyPerson.java
``` java
ObjectProperty<NokName> nokNameProperty();
    NokName getNokName();
    ObjectProperty<NokPhone> nokPhoneProperty();
    NokPhone getNokPhone();
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyPerson other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getPhone().equals(this.getPhone())
                && other.getEmail().equals(this.getEmail())
                && other.getAddress().equals(this.getAddress())
                && other.getNokName().equals(this.getNokName())
                && other.getNokPhone().equals(this.getNokPhone()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Next-of-Kin Name: ")
                .append(getNokName())
                .append(" Next-of-Kin Phone: ")
                .append(getNokPhone())
```
###### /java/seedu/address/model/util/SampleDataUtil.java
``` java
import seedu.address.model.person.NokName;
import seedu.address.model.person.NokPhone;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new NokName("Peter Yeoh"),
                    new NokPhone("91516545"), getTagSet("friends")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new NokName("Denise Yu"),
                    new NokPhone("94894182"), getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new NokName("Charlie Oliveiro"),
                    new NokPhone("81454165"), getTagSet("neighbours")),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new NokName("Elliot Li"),
                    new NokPhone("94156418"), getTagSet("family")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new NokName("Juliet Ibrahim"),
                    new NokPhone("91854185"), getTagSet("classmates")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new NokName("Sakthiar Balakrishnan"),
                    new NokPhone("85648965"), getTagSet("colleagues"))
```
###### /java/seedu/address/storage/XmlAdaptedPerson.java
``` java
import seedu.address.model.person.NokName;
import seedu.address.model.person.NokPhone;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String nokName;
    @XmlElement(required = true)
    private String nokPhone;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(ReadOnlyPerson source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        nokName = source.getNokName().value;
        nokPhone = source.getNokPhone().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone);
        final Email email = new Email(this.email);
        final Address address = new Address(this.address);
        final NokName nokName = new NokName(this.nokName);
        final NokPhone nokPhone = new NokPhone(this.nokPhone);
        final Set<Tag> tags = new HashSet<>(personTags);
        return new Person(name, phone, email, address, nokName, nokPhone, tags);
```
###### /java/seedu/address/ui/PersonCard.java
``` java
@FXML
    private Label nokName;
    @FXML
    private Label nokPhone;
```
###### /java/seedu/address/ui/PersonCard.java
``` java
 String test = nokName.toString();                                           // Problem
        nokName.textProperty().bind(Bindings.convert(person.nokNameProperty()));
        nokPhone.textProperty().bind(Bindings.convert(person.nokPhoneProperty()));
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOK_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String DEFAULT_UNDEFINED = "Undefined";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_AVATAR_AMY = "default.png";
    public static final String VALID_AVATAR_BOB = "default.png";
    public static final String VALID_NOK_NAME_AMY = "Beth Bee";
    public static final String VALID_NOK_NAME_BOB = "Boyang Choo";
    public static final String VALID_NOK_PHONE_AMY = "12121212";
    public static final String VALID_NOK_PHONE_BOB = "21212121";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_TAG_HUSBAND = "husband";

    public static final String ADDRESS_DEFAULT_UNDEFINED = " " + PREFIX_ADDRESS + DEFAULT_UNDEFINED;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String AVATAR_DESC_AMY = " " + PREFIX_AVATAR + VALID_AVATAR_AMY;
    public static final String AVATAR_DESC_BOB = " " + PREFIX_AVATAR + VALID_AVATAR_BOB;
    public static final String EMAIL_DEFAULT_UNDEFINED = " " + PREFIX_EMAIL + DEFAULT_UNDEFINED;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NOK_NAME_DEFAULT_UNDEFINED = " " + PREFIX_NOK_NAME + DEFAULT_UNDEFINED;
    public static final String NOK_NAME_DESC_AMY = " " + PREFIX_NOK_NAME + VALID_NOK_NAME_AMY;
    public static final String NOK_NAME_DESC_BOB = " " + PREFIX_NOK_NAME + VALID_NOK_NAME_BOB;
    public static final String NOK_PHONE_DEFAULT_UNDEFINED = " " + PREFIX_NOK_PHONE + DEFAULT_UNDEFINED;
    public static final String NOK_PHONE_DESC_AMY = " " + PREFIX_NOK_PHONE + VALID_NOK_PHONE_AMY;
    public static final String NOK_PHONE_DESC_BOB = " " + PREFIX_NOK_PHONE + VALID_NOK_PHONE_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_AVATAR_DESC = " " + PREFIX_AVATAR + "notafilepath"; // invalid filepath
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_NOK_NAME_DESC = " " + PREFIX_NOK_NAME + "Jessie%"; // '%' not allowed in names
    public static final String INVALID_NOK_PHONE_DESC = " " + PREFIX_NOK_PHONE + "999m"; // 'm' not allowed in phones
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withAvatar(VALID_AVATAR_AMY).withNokName(VALID_NOK_NAME_AMY).withNokPhone(VALID_NOK_PHONE_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withAvatar(VALID_AVATAR_BOB).withNokName(VALID_NOK_NAME_BOB).withNokPhone(VALID_NOK_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
```
###### /java/seedu/address/testutil/EditPersonDescriptorBuilder.java
``` java
        descriptor.setNokName(person.getNokName());
        descriptor.setNokPhone(person.getNokPhone());
```
###### /java/seedu/address/testutil/EditPersonDescriptorBuilder.java
``` java
 /**
     * Sets the {@code NokName} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withNokName(String nokName) {
        try {
            ParserUtil.parseNokName(Optional.of(nokName)).ifPresent(descriptor::setNokName);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("NOK name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code NokPhone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withNokPhone(String nokPhone) {
        try {
            ParserUtil.parseNokPhone(Optional.of(nokPhone)).ifPresent(descriptor::setNokPhone);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("NOK phone is expected to be unique.");
        }
        return this;
    }
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
import seedu.address.model.person.NokName;
import seedu.address.model.person.NokPhone;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_NOK_NAME = "Beth Pauline";
    public static final String DEFAULT_NOK_PHONE = "84541946";
    public static final String DEFAULT_TAGS = "friends";

    private Person person;

    public PersonBuilder() {
        try {
            Name defaultName = new Name(DEFAULT_NAME);
            Phone defaultPhone = new Phone(DEFAULT_PHONE);
            Email defaultEmail = new Email(DEFAULT_EMAIL);
            Address defaultAddress = new Address(DEFAULT_ADDRESS);
            NokName defaultNokName = new NokName(DEFAULT_NOK_NAME);
            NokPhone defaultNokPhone = new NokPhone(DEFAULT_NOK_PHONE);
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
            this.person = new Person(defaultName, defaultPhone, defaultEmail, defaultAddress,
                                     defaultNokName, defaultNokPhone, defaultTags);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default person's details are invalid.");
        }
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(ReadOnlyPerson personToCopy) {
        this.person = new Person(personToCopy);
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        try {
            this.person.setName(new Name(name));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        try {
            this.person.setPhone(new Phone(phone));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        try {
            this.person.setEmail(new Email(email));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("email is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        try {
            this.person.setAddress(new Address(address));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code NokName} of the {@code Person} that we are building.
     */
    public PersonBuilder withNokName(String nokName) {
        try {
            this.person.setNokName(new NokName(nokName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("NOK name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code NokPhone} of the {@code Person} that we are building.
     */
    public PersonBuilder withNokPhone(String nokPhone) {
        try {
            this.person.setNokPhone(new NokPhone(nokPhone));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("NOK phone is expected to be unique.");
        }
        return this;
    }
```
###### /java/seedu/address/testutil/PersonUtil.java
``` java
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOK_PHONE;
```
###### /java/seedu/address/testutil/PersonUtil.java
``` java
        sb.append(PREFIX_NOK_NAME + person.getNokName().value + " ");
        sb.append(PREFIX_NOK_PHONE + person.getNokPhone().value + " ");
```
###### /java/seedu/address/testutil/TypicalPersons.java
``` java
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOK_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOK_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOK_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOK_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final ReadOnlyPerson ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255").withNokName("Bob Pauline").withNokPhone("81541854")
            .withTags("friends").build();
    public static final ReadOnlyPerson BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withNokName("Aleena Meier").withNokPhone("87654321")
            .withTags("owesMoney", "friends").build();
    public static final ReadOnlyPerson CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street")
            .withNokName("Carter Kurz").withNokPhone("81526415").build();
    public static final ReadOnlyPerson DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street")
            .withNokName("Ethan Meier").withNokPhone("91564164").build();
    public static final ReadOnlyPerson ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave")
            .withNokName("Adeleine Meyer").withNokPhone("84159455").build();
    public static final ReadOnlyPerson FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo")
            .withNokName("Cecilia Kunz").withNokPhone("91654865").build();
    public static final ReadOnlyPerson GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street")
            .withNokName("Gregory Best").withNokPhone("94518515").build();

    // Manually added
    public static final ReadOnlyPerson HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india")
            .withNokName("Hans Meier").withNokPhone("84185416").build();
    public static final ReadOnlyPerson IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave")
            .withNokName("Lena Mueller").withNokPhone("94152165").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyPerson AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withNokName(VALID_NOK_NAME_AMY).withNokPhone(VALID_NOK_PHONE_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final ReadOnlyPerson BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withNokName(VALID_NOK_NAME_BOB).withNokPhone(VALID_NOK_PHONE_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
```
###### /java/seedu/address/storage/AddressBookStorage.java
``` java
/**
     * Saves the given {@link ReadOnlyAddressBook} as backup at fixed temporary location.
     *
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException;
```
###### /java/seedu/address/storage/StorageManager.java
``` java
@Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath()
                .substring(0, addressBookStorage.getAddressBookFilePath().length() - 4) + "-backup.xml");
    }

    /**
     * Reads the backup of Address Book for testing purposes
     */
    public Optional<ReadOnlyAddressBook> readBackupAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath()
                .substring(0, addressBookStorage.getAddressBookFilePath().length() - 4) + "-backup.xml");
    }
```
###### /java/seedu/address/storage/XmlAddressBookStorage.java
``` java
logger.info("AddressBook file " + addressBookFile + " not found");
```
###### /java/seedu/address/storage/XmlAddressBookStorage.java
``` java
@Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath + "-backup.xml");
    }
```
###### /java/seedu/address/MainApp.java
``` java
    public static final Version VERSION = new Version(1, 5, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private static Storage backup;
    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;


    @Override
    public void init() throws Exception {
        logger.info("============================[ Initializing Address Book & Booklets ]==========================");
        super.init();

        config = initConfig(getApplicationParameter("config"));

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        AddressBookStorage addressBookStorage = new XmlAddressBookStorage(userPrefs.getAddressBookFilePath());
        storage = new StorageManager(addressBookStorage, userPrefsStorage);
        backup = storage;

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    private String getApplicationParameter(String parameterName) {
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
```
###### /java/seedu/address/MainApp.java
``` java
public static Storage getBackup() {
        return backup;
    }
```
###### /java/seedu/address/logic/commands/BackupCommand.java
``` java
package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.MainApp;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Create backup copy of address book.
 */
public class BackupCommand extends Command {
    public static final String COMMAND_WORD = "backup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates backup copy of address book.";
    public static final String MESSAGE_BACKUP_SUCCESS = "New backup created";
    public static final String MESSAGE_BACKUP_ERROR = "Error creating backup";

    @Override
    public CommandResult execute() throws CommandException {
        try {
            MainApp.getBackup().backupAddressBook(model.getAddressBook());
            return new CommandResult(String.format(MESSAGE_BACKUP_SUCCESS));
        } catch (IOException e) {
            return new CommandResult(String.format(MESSAGE_BACKUP_ERROR) + e.getMessage());
        }

    }
}
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
import seedu.address.logic.commands.BackupCommand;
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
//@@author Infinity-Ace
        case BackupCommand.COMMAND_WORD:
            return new BackupCommand();
        //@@author
```
