package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.IMAGE_STORAGE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.IMAGE_STORAGE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BIRTHDATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NOT_FILLED;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHOTO_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHOTO_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHOTO_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIENDS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthdate;
import seedu.address.model.person.EmailAddress;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Before
    public void setup() {
        String imageFilePath = "data/images/";
        File imageFolder = new File(imageFilePath);

        if (!imageFolder.exists()) {
            imageFolder.mkdirs();
        } else {

        }

        try {
            Files.copy(Paths.get("default.jpeg"), Paths.get("data/images/amy@example.com.jpg"),
                    StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get("default.jpeg"), Paths.get("data/images/bob@example.com.jpg"),
                    StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get("default.jpeg"), Paths.get("data/images/alice@example.com.jpg"),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new AssertionError("Nope");
        }
    }
    @Test
    public void parseCommand_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmailAddress(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIENDS)
                .withBirthdate(VALID_BIRTHDATE_BOB).withPhoto(IMAGE_STORAGE_BOB).build();

        // multiple names - last name accepted - using command word
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_FRIEND + BIRTHDATE_DESC_BOB,
                new AddCommand(expectedPerson));

        // multiple names - last name accepted - using command alias
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_FRIEND + BIRTHDATE_DESC_BOB,
                new AddCommand(expectedPerson));

        // multiple phones - last phone accepted - using command word
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_FRIEND + BIRTHDATE_DESC_BOB,
                new AddCommand(expectedPerson));

        // multiple phones - last phone accepted - using command alias
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_FRIEND + BIRTHDATE_DESC_BOB,
                new AddCommand(expectedPerson));

        // multiple emails - last email accepted - using command word
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_FRIEND + BIRTHDATE_DESC_BOB,

                new AddCommand(expectedPerson));
        // multiple emails - last email accepted - using command alias
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_FRIEND + BIRTHDATE_DESC_BOB,
                new AddCommand(expectedPerson));

        // multiple addresses - last address accepted - using command word
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_FRIEND + BIRTHDATE_DESC_BOB,
                new AddCommand(expectedPerson));

        // multiple addresses - last address accepted - using command alias
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_FRIEND + BIRTHDATE_DESC_BOB,
                new AddCommand(expectedPerson));

        // multiple tags - all accepted - using command word
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmailAddress(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withBirthdate(VALID_BIRTHDATE_BOB)
                .withPhoto(IMAGE_STORAGE_BOB).withTags(VALID_TAG_FRIENDS, VALID_TAG_HUSBAND).build();

        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
                + BIRTHDATE_DESC_BOB, new AddCommand(expectedPersonMultipleTags));

        // multiple tags - all accepted - using command alias
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
                + BIRTHDATE_DESC_BOB, new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parseCommand_optionalFieldsMissing_success() {
        // zero tags - using command word
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmailAddress(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withPhoto(IMAGE_STORAGE_AMY)
                .withBirthdate(VALID_BIRTHDATE_AMY).build();

        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + PHOTO_DESC_AMY + BIRTHDATE_DESC_AMY,
                new AddCommand(expectedPerson));

        // missing phone - using command word
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(NOT_FILLED)
                .withEmailAddress(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withPhoto(IMAGE_STORAGE_AMY)
                .withBirthdate(VALID_BIRTHDATE_AMY).withTags().build();

        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + PHOTO_DESC_AMY + BIRTHDATE_DESC_AMY,
                new AddCommand(expectedPerson));

        // missing address - using command word
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmailAddress(VALID_EMAIL_AMY).withAddress(NOT_FILLED).withPhoto(IMAGE_STORAGE_AMY)
                .withBirthdate(VALID_BIRTHDATE_AMY).withTags().build();

        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + PHOTO_DESC_AMY + BIRTHDATE_DESC_AMY, new AddCommand(expectedPerson));


        // missing address - using command alias
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + PHOTO_DESC_AMY + BIRTHDATE_DESC_AMY, new AddCommand(expectedPerson));

        //@@author hengyu95


        // missing birthdate - using command word
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmailAddress(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withPhoto(IMAGE_STORAGE_AMY)
                .withBirthdate(NOT_FILLED).withTags().build();

        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                 + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + PHOTO_DESC_AMY, new AddCommand(expectedPerson));


        // missing birthdate - using command alias
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + PHOTO_DESC_AMY, new AddCommand(expectedPerson));
        //@@author

        // missing 2 fields (phone and address) - using command word
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(NOT_FILLED)
                .withEmailAddress(VALID_EMAIL_AMY).withAddress(NOT_FILLED).withPhoto(IMAGE_STORAGE_AMY)
                .withBirthdate(VALID_BIRTHDATE_AMY).withTags().build();

        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY + PHOTO_DESC_AMY
                + BIRTHDATE_DESC_AMY, new AddCommand(expectedPerson));
    }

    //@@author wenzongteo-reused
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

    //@@author
    @Test
    public void parseCommand_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing email prefix - using command word
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB
                + ADDRESS_DESC_BOB + PHOTO_DESC_BOB, expectedMessage);

        // all prefixes missing - using command word
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB
                + VALID_ADDRESS_BOB + VALID_PHOTO_BOB, expectedMessage);

        // missing name prefix - using command word
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + BIRTHDATE_DESC_BOB, expectedMessage);

        // missing name - using command word
        assertParseFailure(parser, AddCommand.COMMAND_WORD + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + PHOTO_DESC_BOB + BIRTHDATE_DESC_BOB, expectedMessage);
    }

    @Test
    public void parseCommand_invalidValue_failure() {
        // invalid name - using command word
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone - using command word
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + INVALID_PHONE_DESC
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_PHONE_CONSTRAINTS);



        // invalid phone - using command alias
        assertParseFailure(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + INVALID_PHONE_DESC
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_PHONE_CONSTRAINTS);

        //@@author hengyu95

        // invalid birthdate - using command word
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + INVALID_BIRTHDATE_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Birthdate.MESSAGE_BIRTHDATE_CONSTRAINTS);

        // invalid birthdate - using command alias
        assertParseFailure(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + INVALID_BIRTHDATE_DESC
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Birthdate.MESSAGE_BIRTHDATE_CONSTRAINTS);
        //@@author

        // invalid email - using command word
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB + PHOTO_DESC_BOB  + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                EmailAddress.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address - using command word
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + PHOTO_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag - using command word
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIENDS,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported - using command word
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC + PHOTO_DESC_BOB, Name.MESSAGE_NAME_CONSTRAINTS);
    }
}
