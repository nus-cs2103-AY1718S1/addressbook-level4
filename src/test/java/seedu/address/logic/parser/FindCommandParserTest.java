package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.predicates.AddressContainsKeywordsPredicate;
import seedu.address.model.person.predicates.AnyContainsKeywordsPredicate;
import seedu.address.model.person.predicates.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.predicates.TagContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", MESSAGE_INVALID_COMMAND_FORMAT + FindCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new AnyContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgsWithPrefix_returnsFindCommand()
            throws IllegalValueException {
        FindCommand expectedFindCommand1 =
                new FindCommand(new NameContainsKeywordsPredicate(Collections.singletonList("Alice")));
        FindCommand expectedFindCommand2 =
                new FindCommand(new EmailContainsKeywordsPredicate(Collections.singletonList("Alice@gmail.com")));
        FindCommand expectedFindCommand3 =
                new FindCommand(new PhoneContainsKeywordsPredicate(Collections.singletonList("85355255")));
        String[] keywords = "123, Jurong West Ave 6, #08-111".trim().split("\\s+");
        FindCommand expectedFindCommand4 =
                new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList(keywords)));

        ObservableList<Tag> tagList = FXCollections.observableArrayList();
        tagList.add(new Tag("friends"));
        Set<Tag> tags = new HashSet<>(tagList);
        FindCommand expectedFindCommand5 =
                new FindCommand(new TagContainsKeywordsPredicate(tags));

        // with name prefix
        assertParseSuccess(parser, FindCommand.COMMAND_WORD + " n/Alice", expectedFindCommand1);

        //with an email prefix
        assertParseSuccess(parser, FindCommand.COMMAND_WORD + " e/Alice@gmail.com", expectedFindCommand2);

        //with an phone prefix
        assertParseSuccess(parser, FindCommand.COMMAND_WORD + " p/85355255", expectedFindCommand3);

        //with an address prefix
        assertParseSuccess(parser, FindCommand.COMMAND_WORD
                + " a/123, Jurong West Ave 6, #08-111", expectedFindCommand4);

        //with an tag prefix
        assertParseSuccess(parser, FindCommand.COMMAND_WORD + " t/friends", expectedFindCommand5);

    }

    @Test
    public void parse_validArgsWithMultiplePrefixes_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Collections.singletonList("Alice")));
        // search by searchTerm with highest priority: name
        assertParseSuccess(parser, FindCommand.COMMAND_WORD + " n/Alice p/98765432", expectedFindCommand);

    }

}
