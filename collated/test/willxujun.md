# willxujun
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        //empty (unknown) email value
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(UNKNOWN_EMAIL).withAddress(VALID_ADDRESS_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + UNKNOWN_EMAIL_DESC + ADDRESS_DESC_AMY, new AddCommand(expectedPerson));

        //empty (unknown) address value
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(UNKNOWN_ADDRESS).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + UNKNOWN_ADDRESS_DESC, new AddCommand(expectedPerson));
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        /* Case: find multiple persons in address book, 2 keywords -> 0 persons found because of new AND search*/
        command = FindCommand.COMMAND_WORD + " Benson Daniel";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
```
