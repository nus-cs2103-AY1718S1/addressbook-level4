# wenzongteo-reused
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
    @Test
    public void parseAlias_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmailAddress(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIENDS)
                .withBirthdate(VALID_BIRTHDATE_BOB).withPhoto(IMAGE_STORAGE_BOB).build();

        // multiple names - last name accepted - using command alias
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_FRIEND + BIRTHDATE_DESC_BOB,
                new AddCommand(expectedPerson));

        // multiple phones - last phone accepted - using command alias
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_FRIEND + BIRTHDATE_DESC_BOB,
                new AddCommand(expectedPerson));

        // multiple emails - last email accepted - using command alias
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_FRIEND + BIRTHDATE_DESC_BOB,
                new AddCommand(expectedPerson));

        // multiple addresses - last address accepted - using command alias
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_FRIEND + BIRTHDATE_DESC_BOB,
                new AddCommand(expectedPerson));

        // multiple tags - all accepted - using command alias
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmailAddress(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withBirthdate(VALID_BIRTHDATE_BOB)
                .withPhoto(IMAGE_STORAGE_BOB).withTags(VALID_TAG_FRIENDS, VALID_TAG_HUSBAND).build();

        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
                + BIRTHDATE_DESC_BOB, new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parseAlias_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmailAddress(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withPhoto(IMAGE_STORAGE_AMY)
                .withBirthdate(VALID_BIRTHDATE_AMY).build();

        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + ADDRESS_DESC_AMY + PHOTO_DESC_AMY + BIRTHDATE_DESC_AMY,
                new AddCommand(expectedPerson));

        // missing phone
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(NOT_FILLED)
                .withEmailAddress(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withPhoto(IMAGE_STORAGE_AMY)
                .withBirthdate(VALID_BIRTHDATE_AMY).withTags().build();

        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + PHOTO_DESC_AMY + BIRTHDATE_DESC_AMY,
                new AddCommand(expectedPerson));

        // missing address
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmailAddress(VALID_EMAIL_AMY).withAddress(NOT_FILLED).withPhoto(IMAGE_STORAGE_AMY)
                .withBirthdate(VALID_BIRTHDATE_AMY).withTags().build();

        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + PHOTO_DESC_AMY + BIRTHDATE_DESC_AMY, new AddCommand(expectedPerson));

        // missing 2 fields (phone and address)
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(NOT_FILLED)
                .withEmailAddress(VALID_EMAIL_AMY).withAddress(NOT_FILLED).withPhoto(IMAGE_STORAGE_AMY)
                .withBirthdate(VALID_BIRTHDATE_AMY).withTags().build();

        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + EMAIL_DESC_AMY + PHOTO_DESC_AMY
                + BIRTHDATE_DESC_AMY, new AddCommand(expectedPerson));
    }

    @Test
    public void parseAlias_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing email prefix - using command alias
        assertParseFailure(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB
                + ADDRESS_DESC_BOB + PHOTO_DESC_BOB, expectedMessage);

        // all prefixes missing - using command alias
        assertParseFailure(parser, AddCommand.COMMAND_ALIAS + VALID_NAME_BOB + VALID_PHONE_BOB
                + VALID_EMAIL_BOB + VALID_ADDRESS_BOB + VALID_PHOTO_BOB, expectedMessage);

        // missing name prefix - using command alias
        assertParseFailure(parser, AddCommand.COMMAND_ALIAS + VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + BIRTHDATE_DESC_BOB, expectedMessage);

        // missing name - using command alias
        assertParseFailure(parser, AddCommand.COMMAND_ALIAS + PHONE_DESC_BOB  + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + BIRTHDATE_DESC_BOB, expectedMessage);
    }

    @Test
    public void parseAlias_invalidValue_failure() {
        // invalid name - using command alias
        assertParseFailure(parser, AddCommand.COMMAND_ALIAS + INVALID_NAME_DESC + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone - using command alias
        assertParseFailure(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + INVALID_PHONE_DESC
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email - using command alias
        assertParseFailure(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_BOB
                        + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                EmailAddress.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address - using command alias
        assertParseFailure(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_ADDRESS_DESC + PHOTO_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag - using command alias
        assertParseFailure(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIENDS,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported - using command alias
        assertParseFailure(parser, AddCommand.COMMAND_ALIAS + INVALID_NAME_DESC + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC + PHOTO_DESC_BOB , Name.MESSAGE_NAME_CONSTRAINTS);
    }

```
