# chrisboo
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
        FindPersonDescriptor firstPerson = new FindPersonDescriptor();
        FindPersonDescriptor secondPerson = new FindPersonDescriptor();

        try {
            firstPerson.setName(new Name("Amy"));
            secondPerson.setName(new Name("Bob"));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        FindCommand findFirstCommand = new FindCommand(firstPerson);
        FindCommand findSecondCommand = new FindCommand(secondPerson);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPerson);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // missing phone prefix
        Person expectedPersonWithoutPhone = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(EMPTY_PHONE)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY)
                .withWebsite(VALID_WEBSITE_AMY).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY, new AddCommand(expectedPersonWithoutPhone));

        // missing email prefix
        Person expectedPersonWithoutEmail = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(EMPTY_EMAIL).withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY)
                .withWebsite(VALID_WEBSITE_AMY).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + PHONE_DESC_AMY + ADDRESS_DESC_AMY
                + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY, new AddCommand(expectedPersonWithoutEmail));

        // missing address prefix
        Person expectedPersonWithoutAddress = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(EMPTY_ADDRESS).withBirthday(VALID_BIRTHDAY_AMY)
                .withWebsite(VALID_WEBSITE_AMY).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY, new AddCommand(expectedPersonWithoutAddress));
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_find() throws Exception {
        Person person = new PersonBuilder().build();
        FindPersonDescriptor descriptor = new FindPersonDescriptorBuilder(person).build();
        FindCommand command = (FindCommand) parser.parseCommand(FindCommand.COMMAND_WORD + " "
            + PersonUtil.getPersonDetails(person));
        assertEquals(new FindCommand(descriptor), command);
    }
```
###### \java\seedu\address\logic\parser\FindCommandParserTest.java
``` java
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", FindCommand.MESSAGE_NO_FIELD_PROVIDED);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindPersonDescriptor person = new FindPersonDescriptor();

        try {
            person.setName(new Name("Alice"));
            person.setPhone(new Phone("123456"));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        FindCommand expectedFindCommand = new FindCommand(person);
        assertParseSuccess(parser, " n/Alice p/123456", expectedFindCommand);
    }
```
###### \java\seedu\address\model\person\BirthdayTest.java
``` java
    @Test
    public void isValidBirthday() {
        // invalid Birthdays
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday(""));  // blank
        assertFalse(Birthday.isValidBirthday("1 January 1999")); // valid date but invalid format
        assertFalse(Birthday.isValidBirthday("32/01/1999"));     // invalid date but valid format

        // valid Birthdays
        assertTrue(Birthday.isValidBirthday(null));
        assertTrue(Birthday.isValidBirthday("29/02/2000")); // test leap year
        assertTrue(Birthday.isValidBirthday("31/12/1993"));
        assertTrue(Birthday.isValidBirthday("01/01/1994"));
        assertTrue(Birthday.isValidBirthday("1/1/1994"));
    }
```
###### \java\seedu\address\testutil\FindPersonDescriptorBuilder.java
``` java
package seedu.address.testutil;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindCommand.FindPersonDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class to help with building FindPersonDescriptor objects.
 */
public class FindPersonDescriptorBuilder {

    private FindPersonDescriptor descriptor;

    public FindPersonDescriptorBuilder() {
        descriptor = new FindPersonDescriptor();
    }

    public FindPersonDescriptorBuilder(FindPersonDescriptor descriptor) {
        this.descriptor = new FindPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code FindPersonDescriptor} with fields containing {@code person}'s details
     */
    public FindPersonDescriptorBuilder(ReadOnlyPerson person) {
        descriptor = new FindPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setBirthday(person.getBirthday());
        descriptor.setWebsite(person.getWebsite());
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code FindPersonDescriptor} that we are building.
     */
    public FindPersonDescriptorBuilder withName(String name) {
        try {
            ParserUtil.parseName(Optional.of(name)).ifPresent(descriptor::setName);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code FindPersonDescriptor} that we are building.
     */
    public FindPersonDescriptorBuilder withPhone(String phone) {
        try {
            ParserUtil.parsePhone(Optional.of(phone)).ifPresent(descriptor::setPhone);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code FindPersonDescriptor} that we are building.
     */
    public FindPersonDescriptorBuilder withEmail(String email) {
        try {
            ParserUtil.parseEmail(Optional.of(email)).ifPresent(descriptor::setEmail);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("email is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code FindPersonDescriptor} that we are building.
     */
    public FindPersonDescriptorBuilder withAddress(String address) {
        try {
            ParserUtil.parseAddress(Optional.of(address)).ifPresent(descriptor::setAddress);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Birthday} of the {@code FindPersonDescriptor} that we are building.
     */
    public FindPersonDescriptorBuilder withBirthday(String birthday) {
        try {
            ParserUtil.parseBirthday(Optional.of(birthday)).ifPresent(descriptor::setBirthday);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Birthday should be id dd/MM/yyyy format");
        }
        return this;
    }

    /**
     * Sets the {@code Website} of the {@code FindPersonDescriptor} that we are building.
     */
    public FindPersonDescriptorBuilder withWebsite(String website) {
        try {
            ParserUtil.parseWebsite(Optional.of(website)).ifPresent(descriptor::setWebsite);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Website is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code FindPersonDescriptor}
     * that we are building.
     */
    public FindPersonDescriptorBuilder withTags(String... tags) {
        try {
            descriptor.setTags(ParserUtil.parseTags(Arrays.asList(tags)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    public FindPersonDescriptor build() {
        return descriptor;
    }
}
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: add a person with all fields same as another person in the address book except phone -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except email -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except address -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_BOB).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_BOB
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except birthday -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_BOB).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_BOB + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except website -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(VALID_WEBSITE_BOB)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_BOB + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: missing phone -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(EMPTY_PHONE).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing email -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(EMPTY_EMAIL)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing address -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(EMPTY_ADDRESS).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: missing birthday -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(EMPTY_BIRTHDAY).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY
            + ADDRESS_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: invalid website -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + INVALID_WEBSITE_DESC;
        assertCommandFailure(command, Website.MESSAGE_WEBSITE_CONSTRAINTS);
```
###### \java\systemtests\FindCommandSystemTest.java
``` java

        /* Test: find with one attribute */

        /* Case: find a person not in address book
         * -> 0 persons found
         */
        command = FindCommand.COMMAND_WORD + " n/Mark";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by exact full name
         * -> 1 person found
         */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + CARL.getName();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by exact phone number
         * -> 1 person found
         */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_PHONE + CARL.getPhone();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by exact email
         * -> 1 person found
         */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_EMAIL + CARL.getEmail();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by exact address
         * -> 1 person found
         */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_ADDRESS + CARL.getAddress();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by exact website
         * -> 1 person found
         */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_WEBSITE + CARL.getWebsite();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by exact birthday
         * -> 1 person found
         */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_BIRTHDAY + CARL.getBirthday();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Test: find with one attribute with extra constraints */

        /*
         * Case: find a person in address book by name (non case-sensitive)
         * -> 1 person found
         */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "cARL kUrz";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by incomplete name but complete keyword
         * -> 1 person found
         */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "cArL";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by incomplete name but complete keyword
         * -> 1 person found
         */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "kUrZ";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by incomplete name but substring keyword
         * -> 1 person found
         */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "KUr";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by incomplete name and incorrect keyword
         * -> 1 person found
         */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "Krz";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by incomplete phone number (a substring)
         * -> 1 person found
         */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_PHONE + "35256";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by incomplete phone number (a permutation)
         * -> 0 person found
         */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_PHONE + "25639535";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Test: find with multiple attributes */

        /*
         * Case: find a person in address book by exact name and exact phone number
         * -> 1 person found
         */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + BENSON.getName() + " "
                                                 + PREFIX_PHONE + BENSON.getPhone();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by incomplete name but exact phone number
         * -> 1 person found
         */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "Meier" + " " + PREFIX_PHONE + BENSON.getPhone();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Test: find multiple persons */

        /* Case: find multiple persons in address book by name
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "Meier";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "Meier";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, non case-sensitive
         * -> 2 person found
         */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is substring of name -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "Mei";
        ModelHelper.setFilteredList(expectedModel, DANIEL, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, name is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "Meiers";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " n/Benson Daniel";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords in reversed order -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " n/Daniel Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords with 1 repeat -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " n/Daniel Benson Daniel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " n/Daniel Benson NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Test: display screen */

        /* Case: find person where person list is not displaying the person we are finding -> 1 person found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + CARL.getName();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

```
