# OscarWang114
###### /java/seedu/address/logic/parser/AddCommandParserTest.java
``` java
    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withDateOfBirth(VALID_DOB_AMY)
                .withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + DOB_DESC_AMY, new AddCommand(expectedPerson));

        // missing phone prefix
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withEmptyPhone()
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withDateOfBirth(VALID_DOB_AMY)
                .withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + DOB_DESC_AMY, new AddCommand(expectedPerson));

        // missing email prefix
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmptyEmail().withAddress(VALID_ADDRESS_AMY).withDateOfBirth(VALID_DOB_AMY)
                .withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + ADDRESS_DESC_AMY + DOB_DESC_AMY, new AddCommand(expectedPerson));

        // missing address prefix
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withDateOfBirth(VALID_DOB_AMY)
                .withEmptyAddress().withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + DOB_DESC_AMY, new AddCommand(expectedPerson));

        // missing date of birth prefix
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withEmptyDateOfBirth().withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY, new AddCommand(expectedPerson));
    }
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmptyAddress() {
        this.person.setAddress(new Address());
        return this;
    }
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets an empty {@code Phone} for the {@code Person} that we are building.
     */
    public PersonBuilder withEmptyPhone() {
        this.person.setPhone(new Phone());
        return this;
    }
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets an empty {@code Email} for the {@code Person} that we are building.
     */
    public PersonBuilder withEmptyEmail() {
        this.person.setEmail(new Email());
        return this;
    }
```
###### /java/seedu/address/testutil/TypicalPersons.java
``` java
    public static final ReadOnlyPerson AMY_NO_PHONE = new PersonBuilder().withName(VALID_NAME_AMY).withEmptyPhone()
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withDateOfBirth(VALID_DOB_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final ReadOnlyPerson AMY_NO_EMAIL = new PersonBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withEmptyEmail().withAddress(VALID_ADDRESS_AMY)
            .withDateOfBirth(VALID_DOB_AMY).withTags(VALID_TAG_FRIEND)
            .build();
    public static final ReadOnlyPerson AMY_NO_ADDRESS = new PersonBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withEmptyAddress()
            .withDateOfBirth(VALID_DOB_AMY).withTags(VALID_TAG_FRIEND)
            .build();
```
###### /java/systemtests/AddCommandSystemTest.java
``` java
        /* Case: missing phone -> added */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DOB_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, AMY_NO_PHONE);

        /* Case: missing email -> added */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + ADDRESS_DESC_AMY + DOB_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, AMY_NO_EMAIL);

        /* Case: missing address -> added */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + DOB_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, AMY_NO_ADDRESS);
```
###### /java/systemtests/AddCommandSystemTest.java
``` java
        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DOB_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
```
