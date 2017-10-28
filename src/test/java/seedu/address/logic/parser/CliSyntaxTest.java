package seedu.address.logic.parser;

import static org.junit.Assert.assertFalse;
import static seedu.address.logic.parser.CliSyntax.POSSIBLE_SORT_ARGUMENTS;
import static seedu.address.logic.parser.CliSyntax.POSTFIX_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.POSTFIX_DESCENDING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import org.junit.Test;

public class CliSyntaxTest {

    @Test
    public void assertValidSortArgument() {
        assert isValidSortArgument(new SortArgument(PREFIX_NAME.toString()));
        assert isValidSortArgument(new SortArgument(PREFIX_PHONE.toString()));
        assert isValidSortArgument(new SortArgument(PREFIX_EMAIL.toString()));
        assert isValidSortArgument(new SortArgument(PREFIX_ADDRESS.toString()));
        assert isValidSortArgument(new SortArgument(PREFIX_REMARK.toString()));
        assert isValidSortArgument(new SortArgument(PREFIX_NAME.concat(POSTFIX_ASCENDING)));
        assert isValidSortArgument(new SortArgument(PREFIX_PHONE.concat(POSTFIX_ASCENDING)));
        assert isValidSortArgument(new SortArgument(PREFIX_EMAIL.concat(POSTFIX_ASCENDING)));
        assert isValidSortArgument(new SortArgument(PREFIX_ADDRESS.concat(POSTFIX_ASCENDING)));
        assert isValidSortArgument(new SortArgument(PREFIX_REMARK.concat(POSTFIX_ASCENDING)));
        assert isValidSortArgument(new SortArgument(PREFIX_NAME.concat(POSTFIX_DESCENDING)));
        assert isValidSortArgument(new SortArgument(PREFIX_PHONE.concat(POSTFIX_DESCENDING)));
        assert isValidSortArgument(new SortArgument(PREFIX_EMAIL.concat(POSTFIX_DESCENDING)));
        assert isValidSortArgument(new SortArgument(PREFIX_ADDRESS.concat(POSTFIX_DESCENDING)));
        assert isValidSortArgument(new SortArgument(PREFIX_REMARK.concat(POSTFIX_DESCENDING)));

        assertFalse(isValidSortArgument(new SortArgument("\n")));
        assertFalse(isValidSortArgument(new SortArgument("abcd")));
        assertFalse(isValidSortArgument(new SortArgument("hello")));
        assertFalse(isValidSortArgument(new SortArgument("r3c4q")));
        assertFalse(isValidSortArgument(new SortArgument("d/")));
        assertFalse(isValidSortArgument(new SortArgument("pp/")));
    }

    private boolean isValidSortArgument(SortArgument sortArgument) {
        return POSSIBLE_SORT_ARGUMENTS.contains(sortArgument);
    }
}
