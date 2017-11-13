# srishag
###### /java/guitests/guihandles/PersonCardHandle.java
``` java
    public String getBirthday() {
        return birthdayLabel.getText();
    }
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String EMAIL_SENDER = "me"; //unique userId as recognized by Google

    public static final String VALID_EMAIL_SUBJECT = "Meeting agenda for next week.";
    public static final String VALID_EMAIL_BODY = "See you next Monday at 10 am.//Thanks.";
```
###### /java/seedu/address/logic/commands/EditPersonDescriptorTest.java
``` java
        // different birthday -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withBirthday(VALID_BIRTHDAY_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));
```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java
    @Test
    public void parseBirthday_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseBirthday(null);
    }

    @Test
    public void parseBirthday_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseBirthday(Optional.of(INVALID_BIRTHDAY));
    }

    @Test
    public void parseBirthday_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseBirthday(Optional.empty()).isPresent());
    }

    @Test
    public void parseBirthday_validValue_returnsBirthday() throws Exception {
        Birthday expectedBirthday = new Birthday(VALID_BIRTHDAY);
        Optional<Birthday> actualBirthday = ParserUtil.parseBirthday(Optional.of(VALID_BIRTHDAY));

        assertEquals(expectedBirthday, actualBirthday.get());
    }
```
###### /java/seedu/address/model/person/BirthdayTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BirthdayTest {

    @Test
    public void isValidBirthday() {
        // invalid birthdays
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("9199")); // less than 8 numbers
        assertFalse(Birthday.isValidBirthday("birthday")); // non-numeric
        assertFalse(Birthday.isValidBirthday("0911a991")); // alphabets within digits
        assertFalse(Birthday.isValidBirthday("1203 1996")); // spaces within digits
        assertFalse(Birthday.isValidBirthday("15/09/1993")); // forward slash within digits
        assertFalse(Birthday.isValidBirthday("15.09.1993")); // fullstops within digits
        assertFalse(Birthday.isValidBirthday("99999999")); // invalid date

        // valid birthdays
        assertTrue(Birthday.isValidBirthday(""));
        assertTrue(Birthday.isValidBirthday("12111999")); // exactly 8 digits
    }
}
```
###### /java/seedu/address/testutil/EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Birthday} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withBirthday(String birthday) {
        try {
            ParserUtil.parseBirthday(Optional.of(birthday)).ifPresent(descriptor::setBirthday);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("birthday is expected to be unique.");
        }
        return this;
    }
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     *
     * Sets the {@code Birthday} of the {@code Person} that we are building.
     */
    public PersonBuilder withBirthday(String birthday) {
        try {
            this.person.setBirthday(new Birthday(birthday));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("birthday is expected to be unique.");
        }
        return this;
    }
```
###### /java/systemtests/AddCommandSystemTest.java
``` java
        /* Case: add a person with all fields same as another person in the address book except birthday -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_BOB)
                .withFacebookAddress(VALID_FACEBOOKADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + FACEBOOK_ADDRESS_DESC_AMY + BIRTHDAY_DESC_BOB + TAG_DESC_FRIEND;

        assertCommandSuccess(command, toAdd);
```
###### /java/systemtests/AddCommandSystemTest.java
``` java
        /* Case: invalid birthday -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + INVALID_BIRTHDAY_DESC;
        assertCommandFailure(command, Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
```
###### /java/systemtests/FindCommandSystemTest.java
``` java
        /* Case: find birthday of person in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getBirthday().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
```
