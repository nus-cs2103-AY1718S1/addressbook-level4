package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.predicate.AddressContainsKeywordsPredicate;
import seedu.address.model.person.predicate.ContainsTagsPredicate;
import seedu.address.model.person.predicate.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicate.PhoneContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    //@@author newalter
    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        Predicate<ReadOnlyPerson> component1 = new NameContainsKeywordsPredicate(Arrays.asList("alice", "bob"));
        Predicate<ReadOnlyPerson> component2 = new PhoneContainsKeywordsPredicate(Arrays.asList("88887777"));
        Predicate<ReadOnlyPerson> component3 = new EmailContainsKeywordsPredicate(Arrays.asList("alice@example.com"));
        Predicate<ReadOnlyPerson> component4 = new AddressContainsKeywordsPredicate(Arrays.asList("Clementi"));
        Predicate<ReadOnlyPerson> component5 = new ContainsTagsPredicate(Arrays.asList("family", "friends"));

        ArrayList<Predicate<ReadOnlyPerson>> predicates = new ArrayList<>();
        predicates.addAll(Arrays.asList(component1, component2, component3, component4, component5));
        FindCommand expectedFindCommand =
                new FindCommand(predicates);
        assertParseSuccess(parser,
                " n/Alice Bob e/alice@example.com a/Clementi t/family friends p/88887777", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "  n/Alice Bob \t e/alice@example.com "
                + "a/Clementi t/family \n friends \t p/88887777 \t ", expectedFindCommand);
    }

}
