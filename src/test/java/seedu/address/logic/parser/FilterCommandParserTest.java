package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.NameAndTagsContainsKeywordsPredicate;

//@@author chuaweiwen
public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() throws Exception {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws Exception {
        // No name specified after name prefix -> fail
        assertParseFailure(parser, " n/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));

        // No tag specified after tag prefix -> fail
        assertParseFailure(parser, " t/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));

        // Name specified but no tag specified after tag prefix -> fail
        assertParseFailure(parser, " n/name t/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " t/ n/name", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));

        // Tag specified but no name specified after name prefix -> fail
        assertParseFailure(parser, " n/ t/tag", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " t/tag n/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));

        // Prefix not used -> fail
        assertParseFailure(parser, " Alice", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() throws Exception {
        List<String> nameKeywords = new ArrayList<>();
        List<String> tagKeywords = new ArrayList<>();

        // with name but without tag -> success
        nameKeywords.add("Alice");
        FilterCommand expectedFilterCommandWithoutName =
                new FilterCommand(new NameAndTagsContainsKeywordsPredicate(nameKeywords, tagKeywords));
        assertParseSuccess(parser, " n/Alice", expectedFilterCommandWithoutName);

        nameKeywords.clear();

        // with tag but wihout name -> success
        tagKeywords.add("friends");
        FilterCommand expectedFilterCommandWithoutTag =
                new FilterCommand(new NameAndTagsContainsKeywordsPredicate(nameKeywords, tagKeywords));
        assertParseSuccess(parser, " t/friends", expectedFilterCommandWithoutTag);

        // with both name and tag -> success
        nameKeywords.add("Alice");
        FilterCommand expectedFilterCommandWithAllArgs =
                new FilterCommand(new NameAndTagsContainsKeywordsPredicate(nameKeywords, tagKeywords));
        assertParseSuccess(parser, " n/Alice t/friends", expectedFilterCommandWithAllArgs);

        // with both name and tag but different order -> success
        assertParseSuccess(parser, " t/friends n/Alice", expectedFilterCommandWithAllArgs);

        nameKeywords.clear();

        // with more than one tag -> success
        tagKeywords.add("colleagues");
        FilterCommand expectedFilterCommandWithTwoTags =
                new FilterCommand(new NameAndTagsContainsKeywordsPredicate(nameKeywords, tagKeywords));
        assertParseSuccess(parser, " t/friends t/colleagues", expectedFilterCommandWithTwoTags);
        assertParseSuccess(parser, " t/friends colleagues", expectedFilterCommandWithTwoTags);

        tagKeywords.clear();

        // with more than one name -> success
        nameKeywords.add("Alice");
        nameKeywords.add("Pauline");
        FilterCommand expectedFilterCommandWithTwoNames =
                new FilterCommand(new NameAndTagsContainsKeywordsPredicate(nameKeywords, tagKeywords));
        assertParseSuccess(parser, " n/Alice n/Pauline", expectedFilterCommandWithTwoNames);
        assertParseSuccess(parser, " n/Alice Pauline", expectedFilterCommandWithTwoNames);
    }

}
//@@author
