# darrinloh
###### \java\seedu\address\logic\commands\ModeCommandTest.java
``` java
    @Test
    public void execute_mode_command() {
        assertCommandSuccess(modeCommand, model, ModeCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        //correct input -> returns true
        assertTrue(ModeCommand.COMMAND_WORD.equals("mode"));

        //case sensitive -> returns false
        assertFalse(ModeCommand.COMMAND_WORD.equals("Mode"));
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withOrganisation(VALID_ORGANISATION_AMY).withRemark(VALID_REMARK_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + ORGANISATION_DESC_AMY + REMARK_DESC_AMY,
                new AddCommand(expectedPerson));

        //missing phone field -> accepted
        Person expectedPersonWithoutPhone = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(null)
                .withBirthday(VALID_BIRTHDAY_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withOrganisation(VALID_ORGANISATION_AMY).withRemark(VALID_REMARK_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_NULL
                + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + ORGANISATION_DESC_AMY
                + REMARK_DESC_AMY + TAG_DESC_FRIEND, new AddCommand(expectedPersonWithoutPhone));

        //missing birthday field -> accepted
        Person expectedPersonWithoutBirthday = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withBirthday(null).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withOrganisation(VALID_ORGANISATION_AMY).withRemark(VALID_REMARK_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + BIRTHDAY_DESC_NULL + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + ORGANISATION_DESC_AMY
                + REMARK_DESC_AMY + TAG_DESC_FRIEND, new AddCommand(expectedPersonWithoutBirthday));

        //missing email field -> accepted
        Person expectedPersonWithoutEmail = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY).withEmail(null).withAddress(VALID_ADDRESS_AMY)
                .withOrganisation(VALID_ORGANISATION_AMY).withRemark(VALID_REMARK_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + BIRTHDAY_DESC_AMY + EMAIL_DESC_NULL + ADDRESS_DESC_AMY + ORGANISATION_DESC_AMY
                + REMARK_DESC_AMY + TAG_DESC_FRIEND, new AddCommand(expectedPersonWithoutEmail));

        //missing address field -> accepted
        Person expectedPersonWithoutAddress = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY).withEmail(VALID_EMAIL_AMY).withAddress(null)
                .withOrganisation(VALID_ORGANISATION_AMY).withRemark(VALID_REMARK_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_NULL + ORGANISATION_DESC_AMY
                + REMARK_DESC_AMY + TAG_DESC_FRIEND, new AddCommand(expectedPersonWithoutAddress));

        //missing organisation field -> accepted
        Person expectedPersonWithoutOrganisation = new PersonBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withBirthday(VALID_BIRTHDAY_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withOrganisation(null).withRemark(VALID_REMARK_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + ORGANISATION_DESC_NULL
                + REMARK_DESC_AMY + TAG_DESC_FRIEND, new AddCommand(expectedPersonWithoutOrganisation));

        //missing remark field -> accepted
        Person expectedPersonWithoutRemark = new PersonBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withBirthday(VALID_BIRTHDAY_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withOrganisation(VALID_ORGANISATION_AMY).withRemark(null)
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + ORGANISATION_DESC_AMY
                + REMARK_DESC_NULL + TAG_DESC_FRIEND, new AddCommand(expectedPersonWithoutRemark));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + PHONE_DESC_BOB
                + BIRTHDAY_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + ORGANISATION_DESC_BOB
                + REMARK_DESC_BOB, expectedMessage);


        // all prefixes missing
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + VALID_PHONE_BOB
                + VALID_BIRTHDAY_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB + VALID_ORGANISATION_BOB
                + VALID_ADDRESS_BOB, expectedMessage);

    }
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
    @Test
    public void getUserPrefsFilePathTest() {
        assertEquals(getTempFilePath("prefs"), storageManager.getUserPrefsFilePath());
    }
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
    @Test
    public void backupAddressBookUrlTest() {
        String expectedUrl = storageManager.getAddressBookFilePath() + "-backup.xml";
        String actualUrl = storageManager.getBackUpAddressBookFilePath();
        assertEquals(expectedUrl, actualUrl);
    }
```
###### \java\seedu\address\ui\ModeChangeTest.java
``` java
    @Before
    public void setUp() {
        prefs = new UserPrefs();

    }

    @Test
    public void check_valid_css() {
        assertTrue(prefs.getTheme().contains(DARK_MODE));
    }

    @Test
    public void check_validCssAfterUpdating() {
        prefs.updateLastUsedThemeSetting(DARK_MODE);
        assertTrue(prefs.getTheme().contains(DARK_MODE));
    }

    @Test
    public void set_css() {
        prefs.setTheme(LIGHT_MODE);
        assertTrue(prefs.getTheme().contains(LIGHT_MODE));
    }
}
```
