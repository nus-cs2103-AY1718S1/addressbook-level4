package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(ReadOnlyPerson person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(ReadOnlyPerson person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        person.getPhones().stream().forEach(
            s -> sb.append(PREFIX_PHONE + s.value + " ")
        );
        sb.append(PREFIX_BIRTHDAY + person.getBirthday().value + " ");
        person.getEmails().stream().forEach(
            s -> sb.append(PREFIX_EMAIL + s.value + " ")
        );
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        person.getMods().stream().forEach(
            s -> sb.append(PREFIX_MOD + s.modName + " ")
        );
        return sb.toString();
    }
}
