# Juxarius
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // autofilled address
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + DOB_DESC_BOB + ADDRESS_EMPTY_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(EXCEPTION_EMPTYFIELD, PREFIX_ADDRESS.getPrefix()));
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_DELLALL;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withDelTags("all").build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\systemtests\EditCommandSystemTest.java
``` java
        /* Case: delete a single tag -> existing tags untouched */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + DELTAG_DESC_HUSBAND;
        ReadOnlyPerson personToEdit = getModel().getFilteredPersonList().get(index.getZeroBased());
        editedPerson = new PersonBuilder(personToEdit).withTags(VALID_TAG_FRIEND).build();
        assertCommandSuccess(command, index, editedPerson);

        /* Case: add a single tag without deleting existing tags -> existing tags untouched */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + TAG_DESC_HUSBAND;
        editedPerson = new PersonBuilder(personToEdit).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertCommandSuccess(command, index, editedPerson);

        /* Case: process multiple tag add and deletion -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + DELTAG_ALL + TAG_DESC_HUSBAND;
        editedPerson = new PersonBuilder(personToEdit).withTags(VALID_TAG_HUSBAND).build();
        assertCommandSuccess(command, index, editedPerson);

        /* Case: clear tags -> cleared */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + DELTAG_ALL;
        editedPerson = new PersonBuilder(personToEdit).withTags().build();
        assertCommandSuccess(command, index, editedPerson);

        /* ----------------------------------- Performing autofill operations --------------------------------------- */

        /* Case: Autofill name -> filled */
        index = INDEX_SECOND_PERSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_NAME;
        assertAutofillSuccess(command, index);

        /* Case: Autofill phone -> filled */
        index = INDEX_SECOND_PERSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_PHONE;
        assertAutofillSuccess(command, index);

        /* Case: Autofill email -> filled */
        index = INDEX_SECOND_PERSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_EMAIL;
        assertAutofillSuccess(command, index);

        /* Case: Autofill address -> filled */
        index = INDEX_SECOND_PERSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_ADDRESS;
        assertAutofillSuccess(command, index);

        /* Case: Autofill dob -> filled */
        index = INDEX_SECOND_PERSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_DOB;
        assertAutofillSuccess(command, index);

        /* Case: Autofill tags -> filled */
        index = INDEX_SECOND_PERSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG;
        assertAutofillSuccess(command, index);

        /* Case: Autofill tags -> filled */
        index = INDEX_SECOND_PERSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_DELTAG;
        assertAutofillSuccess(command, index);

        /* Case: Autofill multiple fields -> filled */
        index = INDEX_SECOND_PERSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_PHONE + " " + PREFIX_ADDRESS
                + " " + PREFIX_TAG;
        assertAutofillSuccess(command, index);
```
###### \java\systemtests\EditCommandSystemTest.java
``` java
    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays the autofilled command.<br>
     * 2. Asserts that the result display box displays autofilled result message.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertAutofillSuccess(String command, Index toEdit) {
        Model expectedModel = getModel();
        ReadOnlyPerson person = expectedModel.getAddressBook().getPersonList().get(toEdit.getZeroBased());
        executeCommand(command);
        for (Prefix prefix : PREFIXES_PERSON) {
            String prefixInConcern = prefix.getPrefix();
            if (command.contains(prefixInConcern)) {
                String replacementText = prefixInConcern + person.getDetailByPrefix(prefix) + " ";
                command = command.replaceFirst(prefixInConcern, replacementText);
            }
        }
        if (command.contains(PREFIX_TAG.getPrefix())) {
            String formattedTags = PREFIX_TAG.getPrefix()
                    + person.getDetailByPrefix(PREFIX_TAG).replaceAll(" ", " t/") + " ";
            command = command.replaceFirst(PREFIX_TAG.getPrefix(), formattedTags);
        }
        if (command.contains(PREFIX_DELTAG.getPrefix())) {
            String formattedTags = PREFIX_DELTAG.getPrefix()
                    + person.getDetailByPrefix(PREFIX_DELTAG).replaceAll(" ", " t/") + " ";
            command = command.replaceFirst(PREFIX_DELTAG.getPrefix(), formattedTags);
        }
        assertApplicationDisplaysExpected(command.trim(), "Autofilled!", expectedModel);
        assertCommandBoxShowsDefaultStyle();
    }
```
