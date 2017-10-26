package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRACKING_NUMBER;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.parcel.ReadOnlyParcel;

/**
 * A utility class for Parcel.
 */
public class ParcelUtil {

    /**
     * Returns an add command string for adding the {@code parcel}.
     */
    public static String getAddCommand(ReadOnlyParcel parcel) {
        return AddCommand.COMMAND_WORD + " " + getParcelDetails(parcel);
    }

    /**
     * Returns the part of command string for the given {@code parcel}'s details.
     */
    public static String getParcelDetails(ReadOnlyParcel parcel) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TRACKING_NUMBER + parcel.getTrackingNumber().value + " ");
        sb.append(PREFIX_NAME + parcel.getName().fullName + " ");
        sb.append(PREFIX_PHONE + parcel.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + parcel.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + parcel.getAddress().toString() + " ");
        sb.append(PREFIX_DELIVERY_DATE + parcel.getDeliveryDate().toString() + " ");
        sb.append(PREFIX_STATUS + parcel.getStatus().toString() + " ");
        parcel.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
