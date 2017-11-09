# Juxarius
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // autofilled address
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + DOB_DESC_BOB + ADDRESS_EMPTY_DESC + GENDER_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(EXCEPTION_EMPTYFIELD, PREFIX_ADDRESS.getPrefix()));
```
###### \java\seedu\address\logic\parser\AddLifeInsuranceCommandParserTest.java
``` java
public class AddLifeInsuranceCommandParserTest {

    private final AddLifeInsuranceCommandParser parser = new AddLifeInsuranceCommandParser();

    @Test
    public void testParse() {
        // parser works
        String command = " " + PREFIX_NAME + "Most Expensive Insurance Ever";
        assertCommandFail(parser, command);
        command += " " + PREFIX_OWNER + "Me";
        assertCommandFail(parser, command);
        command += " " + PREFIX_INSURED + "You";
        assertCommandFail(parser, command);
        command += " " + PREFIX_BENEFICIARY + "Us";
        assertCommandFail(parser, command);
        command += " " + PREFIX_PREMIUM + 493.34;
        assertCommandFail(parser, command);
        command += " " + PREFIX_CONTRACT + "contract.pdf";
        assertCommandFail(parser, command);
        command += " " + PREFIX_SIGNING_DATE + "01 11 2011";
        assertCommandFail(parser, command);
        command += " " + PREFIX_EXPIRY_DATE + "01 11 2020";
        assertCommandSuccess(parser, command);
    }

    /**
     * asserts the failure to parse command due to invalid number of arguments
     * @param parser
     * @param command
     */
    public void assertCommandFail(AddLifeInsuranceCommandParser parser, String command) {
        try {
            parser.parse(command);
            throw new IllegalArgumentException("Parser is not working as intended.");
        } catch (ParseException e) {
            assert(true);
        }
    }

    /**
     * asserts that the parser is able to successfully parse the command
     * @param parser
     * @param command
     */
    public void assertCommandSuccess(AddLifeInsuranceCommandParser parser, String command) {
        try {
            parser.parse(command);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Parser is not working as intended.");
        }
    }
}
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
###### \java\seedu\address\model\insurance\LifeInsuranceTest.java
``` java
public class LifeInsuranceTest {
    /*
    @Test
    public void constructors() {
        try {
            // creating with just Strings for name
            LifeInsurance insurance = new LifeInsurance("Amy", "Bob", "Bob",
                    523.34, "contract.pdf", "11 10 2013", "1 12 2019");
            LifeInsurance insurance2 = new LifeInsurance(ALICE, ELLE, FIONA, 500.0,
                    "Awesome Contract.pdf", "1 1 12", "13 Jan-17");
            LifeInsurance insurance3 = new LifeInsurance(insurance2);
            assertEqualsInsurance(insurance3, insurance2);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Construction should not be failing");
        }
    }

    /**
     * asserts that the 2 input insurances are identical
     * @param insurance1
     * @param insurance2
     */
    /*
    public void assertEqualsInsurance(ReadOnlyInsurance insurance1, ReadOnlyInsurance insurance2) {
        assertEquals(insurance1.getOwner(), insurance2.getOwner());
        assertEquals(insurance1.getBeneficiary(), insurance2.getBeneficiary());
        assertEquals(insurance1.getInsured(), insurance2.getInsured());
        assertEquals(insurance1.getPremium(), insurance2.getPremium());
        assertEquals(insurance1.getSigningDateString(), insurance2.getSigningDateString());
        assertEquals(insurance1.getExpiryDateString(), insurance2.getExpiryDateString());
    }*/
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
