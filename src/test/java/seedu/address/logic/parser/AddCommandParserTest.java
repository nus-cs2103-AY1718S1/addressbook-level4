package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSTALCODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEBSITE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.POSTAL_CODE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.POSTAL_CODE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTAL_CODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTAL_CODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.WEBSITE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.WEBSITE_DESC_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.place.Address;
import seedu.address.model.place.Name;
import seedu.address.model.place.Phone;
import seedu.address.model.place.Place;
import seedu.address.model.place.PostalCode;
import seedu.address.model.place.Website;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.PlaceBuilder;


public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Place expectedPlace = new PlaceBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withWebsite(VALID_WEBSITE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withPostalCode(VALID_POSTAL_CODE_BOB).withTags(VALID_TAG_FRIEND).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPlace));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                        + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPlace));
        //@@author aungmyin23
        // multiple postalcode - last postalcode accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_AMY
                        + POSTAL_CODE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPlace));

        // multiple website - last website accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + WEBSITE_DESC_AMY
                + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPlace));
        //@@author
        // multiple addresses - last address accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + WEBSITE_DESC_BOB
                + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPlace));

        // multiple tags - all accepted
        Place expectedPlaceMultipleTags = new PlaceBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withWebsite(VALID_WEBSITE_BOB).withAddress(VALID_ADDRESS_BOB).withPostalCode(VALID_POSTAL_CODE_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPlaceMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Place expectedPlace = new PlaceBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withWebsite(VALID_WEBSITE_AMY).withAddress(VALID_ADDRESS_AMY)
                .withPostalCode(VALID_POSTAL_CODE_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + WEBSITE_DESC_AMY + ADDRESS_DESC_AMY + POSTAL_CODE_DESC_AMY, new AddCommand(expectedPlace));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + PHONE_DESC_BOB
                + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_AMY, expectedMessage);
        //@@author aungmyin23
        // missing postalcode prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + VALID_POSTAL_CODE_BOB, expectedMessage);
        //@@author

        // all prefixes missing
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + VALID_PHONE_BOB
                + VALID_WEBSITE_BOB + VALID_ADDRESS_BOB + POSTAL_CODE_DESC_AMY, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB
                        + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + INVALID_PHONE_DESC
                        + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND,
                Phone.MESSAGE_PHONE_CONSTRAINTS);

        //@@author aungmyin23
        // invalid postalcode
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + INVALID_POSTALCODE_DESC + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND,
                PostalCode.MESSAGE_POSTAL_CODE_CONSTRAINTS);

        // invalid website
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + INVALID_WEBSITE_DESC + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND, Website.MESSAGE_WEBSITE_CONSTRAINTS);
        //@@author

        // invalid address
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + WEBSITE_DESC_BOB + INVALID_ADDRESS_DESC + POSTAL_CODE_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + WEBSITE_DESC_BOB
                        + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB
                + WEBSITE_DESC_BOB
                + INVALID_ADDRESS_DESC + POSTAL_CODE_DESC_BOB, Name.MESSAGE_NAME_CONSTRAINTS);
    }
}
