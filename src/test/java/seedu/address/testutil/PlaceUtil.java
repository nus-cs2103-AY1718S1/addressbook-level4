
package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEBSITE;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.place.ReadOnlyPlace;

/**
 * A utility class for Place.
 */
public class PlaceUtil {

    /**
     * Returns an add command string for adding the {@code place}.
     */
    public static String getAddCommand(ReadOnlyPlace place) {
        return AddCommand.COMMAND_WORD + " " + getPlaceDetails(place);
    }

    /**
     * Returns the part of command string for the given {@code place}'s details.
     */
    public static String getPlaceDetails(ReadOnlyPlace place) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + place.getName().fullName + " ");
        sb.append(PREFIX_PHONE + place.getPhone().value + " ");
        sb.append(PREFIX_WEBSITE + place.getWebsite().value + " ");
        sb.append(PREFIX_ADDRESS + place.getAddress().value + " ");
        sb.append(PREFIX_POSTAL_CODE + place.getPostalCode().value + " ");
        place.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
