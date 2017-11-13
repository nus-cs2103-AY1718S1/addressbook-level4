package seedu.address.model.person;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Comparator;

import seedu.address.logic.parser.Prefix;

//@@author sofarsophie
/**
 * A comparator for comparing two {@code ReadOnlyPerson} objects by a given field identified the given {@code Prefix}.
 */
public class ReadOnlyPersonComparator implements Comparator<ReadOnlyPerson> {

    private static Prefix compareByPrefix;

    public ReadOnlyPersonComparator(Prefix compareByPrefix) {
        this.compareByPrefix = compareByPrefix;
    }

    /**
     * Returns a {@code ReadOnlyPersonComparator} with the given {@code Prefix}.
     */
    public ReadOnlyPersonComparator compareByPrefix(Prefix compareByPrefix) {
        this.compareByPrefix = compareByPrefix;
        return this;
    }

    @Override
    public int compare(ReadOnlyPerson a, ReadOnlyPerson b) {
        if (compareByPrefix.equals(PREFIX_ADDRESS)) {
            return a.getAddress().compareTo(b.getAddress());
        } else if (compareByPrefix.equals(PREFIX_EMAIL)) {
            return a.getEmail().compareTo(b.getEmail());
        } else if (compareByPrefix.equals(PREFIX_PHONE)) {
            return a.getPhone().compareTo(b.getPhone());
        } else if (compareByPrefix.equals(PREFIX_GROUP)) {
            return a.getGroup().compareTo(b.getGroup());
        } else if (compareByPrefix.equals(PREFIX_NAME)) {
            return a.getName().compareTo(b.getName());
        } else {
            return 0;
        }
    }

}
//@@author
