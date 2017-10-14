package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ARTICLE_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

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
        sb.append(PREFIX_ARTICLE_NUMBER + parcel.getArticleNumber().value + " ");
        sb.append(PREFIX_NAME + parcel.getName().fullName + " ");
        sb.append(PREFIX_PHONE + parcel.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + parcel.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + parcel.getAddress().toString() + " ");
        parcel.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
