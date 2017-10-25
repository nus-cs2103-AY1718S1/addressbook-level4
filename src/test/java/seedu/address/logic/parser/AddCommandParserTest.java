package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_DATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_DATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TRACKING_NUMBER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TRACKING_NUMBER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TRACKING_NUMBER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRACKING_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRACKING_NUMBER_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.parcel.Address;
import seedu.address.model.parcel.DeliveryDate;
import seedu.address.model.parcel.Email;
import seedu.address.model.parcel.Name;
import seedu.address.model.parcel.Parcel;
import seedu.address.model.parcel.Phone;
import seedu.address.model.parcel.Status;
import seedu.address.model.parcel.TrackingNumber;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ParcelBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Parcel expectedParcel = new ParcelBuilder().withTrackingNumber(VALID_TRACKING_NUMBER_BOB)
                .withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withDeliveryDate(VALID_DELIVERY_DATE_BOB).withStatus(VALID_STATUS_BOB)
                .withTags(VALID_TAG_FRIEND).build();

        // multiple tracking number - last tracking number accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + TRACKING_NUMBER_DESC_AMY
                + TRACKING_NUMBER_DESC_BOB + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DELIVERY_DATE_DESC_BOB + STATUS_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedParcel));

        // multiple names - last name accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + TRACKING_NUMBER_DESC_BOB + NAME_DESC_AMY
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_DATE_DESC_BOB
                + STATUS_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedParcel));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + TRACKING_NUMBER_DESC_BOB + NAME_DESC_BOB
                + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + STATUS_DESC_BOB
                + DELIVERY_DATE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedParcel));

        // multiple emails - last email accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + TRACKING_NUMBER_DESC_BOB + NAME_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_DATE_DESC_BOB
                + STATUS_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedParcel));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + TRACKING_NUMBER_DESC_BOB + NAME_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + DELIVERY_DATE_DESC_BOB
                + STATUS_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedParcel));

        // multiple delivery dates - last delivery date accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + TRACKING_NUMBER_DESC_BOB + NAME_DESC_BOB
               + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_DATE_DESC_AMY
               + DELIVERY_DATE_DESC_BOB + STATUS_DESC_BOB + TAG_DESC_FRIEND,
               new AddCommand(expectedParcel));

        // multiple status - last status accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + TRACKING_NUMBER_DESC_BOB + NAME_DESC_BOB
               + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_DATE_DESC_BOB
               + STATUS_DESC_AMY + STATUS_DESC_BOB + TAG_DESC_FRIEND,
               new AddCommand(expectedParcel));

        // multiple tags - all accepted
        Parcel expectedParcelMultipleTags = new ParcelBuilder().withTrackingNumber(VALID_TRACKING_NUMBER_BOB)
                .withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withDeliveryDate(VALID_DELIVERY_DATE_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + TRACKING_NUMBER_DESC_BOB + NAME_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_DATE_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND,
                new AddCommand(expectedParcelMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags and no status
        Parcel expectedParcel = new ParcelBuilder().withTrackingNumber(VALID_TRACKING_NUMBER_AMY)
                .withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withDeliveryDate(VALID_DELIVERY_DATE_AMY).withStatus("PENDING")
                .withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + TRACKING_NUMBER_DESC_AMY + NAME_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + DELIVERY_DATE_DESC_AMY,
                new AddCommand(expectedParcel));

        // no phone number
        Parcel expectedParcelDefaultPhone = new ParcelBuilder().withTrackingNumber(VALID_TRACKING_NUMBER_BOB)
                .withName(VALID_NAME_BOB).withPhone(Phone.PHONE_DEFAULT_VALUE).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withDeliveryDate(VALID_DELIVERYDATE_BOB).withStatus(VALID_STATUS_BOB)
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + TRACKING_NUMBER_DESC_BOB + NAME_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERYDATE_DESC_BOB
                        + STATUS_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedParcelDefaultPhone));

        // no email
        Parcel expectedParcelDefaultEmail = new ParcelBuilder().withTrackingNumber(VALID_TRACKING_NUMBER_BOB)
                .withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).withEmail(Email.EMAIL_DEFAULT_VALUE)
                .withAddress(VALID_ADDRESS_BOB).withDeliveryDate(VALID_DELIVERYDATE_BOB).withStatus(VALID_STATUS_BOB)
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + TRACKING_NUMBER_DESC_BOB + NAME_DESC_BOB
                        + PHONE_DESC_BOB + ADDRESS_DESC_BOB + DELIVERYDATE_DESC_BOB
                        + STATUS_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedParcelDefaultEmail));


    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing tracking number prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_TRACKING_NUMBER_BOB + NAME_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_DATE_DESC_BOB, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + TRACKING_NUMBER_DESC_BOB + VALID_NAME_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_DATE_DESC_BOB, expectedMessage);
      
        // missing address prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + TRACKING_NUMBER_DESC_BOB + NAME_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB + DELIVERY_DATE_DESC_BOB, expectedMessage);

        // missing delivery date prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + TRACKING_NUMBER_DESC_BOB + NAME_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + VALID_DELIVERY_DATE_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddCommand.COMMAND_WORD + TRACKING_NUMBER_DESC_BOB + VALID_NAME_BOB
                + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB + VALID_DELIVERY_DATE_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid tracking number
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_TRACKING_NUMBER_DESC + INVALID_NAME_DESC
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_DATE_DESC_BOB + STATUS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                TrackingNumber.MESSAGE_TRACKING_NUMBER_CONSTRAINTS);

        // invalid name
        assertParseFailure(parser, AddCommand.COMMAND_WORD + TRACKING_NUMBER_DESC_BOB + INVALID_NAME_DESC
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_DATE_DESC_BOB + STATUS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, AddCommand.COMMAND_WORD + TRACKING_NUMBER_DESC_BOB + NAME_DESC_BOB
                + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_DATE_DESC_BOB + STATUS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, AddCommand.COMMAND_WORD + TRACKING_NUMBER_DESC_BOB + NAME_DESC_BOB
                + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB + DELIVERY_DATE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, AddCommand.COMMAND_WORD + TRACKING_NUMBER_DESC_BOB + NAME_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC + DELIVERY_DATE_DESC_BOB + STATUS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid delivery date
        assertParseFailure(parser, AddCommand.COMMAND_WORD + TRACKING_NUMBER_DESC_BOB + NAME_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + INVALID_DELIVERY_DATE_DESC + STATUS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                DeliveryDate.MESSAGE_DELIVERY_DATE_CONSTRAINTS);

        // invalid status
        assertParseFailure(parser, AddCommand.COMMAND_WORD + TRACKING_NUMBER_DESC_BOB + NAME_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_DATE_DESC_BOB + INVALID_STATUS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Status.MESSAGE_STATUS_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, AddCommand.COMMAND_WORD + TRACKING_NUMBER_DESC_BOB + NAME_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_DATE_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddCommand.COMMAND_WORD + TRACKING_NUMBER_DESC_BOB + INVALID_NAME_DESC
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC + INVALID_DELIVERY_DATE_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);
    }
}
