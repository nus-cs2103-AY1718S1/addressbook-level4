package seedu.address.logic.parser;

//@@author wangyiming1019
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.tag.Tag.MESSAGE_TAG_CONSTRAINTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.model.tag.Tag;

public class AddTagCommandParserTest {

    private AddTagCommandParser parser = new AddTagCommandParser();

    @Test
    public void parseValidIndexAndTagSuccess() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);

        final String tagName = "friends";
        Tag tagToAdd = new Tag(tagName);
        Index targetIndex1 = INDEX_FIRST_PERSON;
        Index targetIndex2 = INDEX_SECOND_PERSON;
        String userInput = targetIndex1.getOneBased() + " " + targetIndex2.getOneBased() + " " + PREFIX_TAG.toString()
                + tagName;
        assertParseSuccess(parser, userInput, new AddTagCommand(tagToAdd, indexes));
    }

    @Test
    public void parseInvalidTagFailure() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);

        final String tagToAdd = "friends";
        Index targetIndex1 = INDEX_FIRST_PERSON;
        Index targetIndex2 = INDEX_SECOND_PERSON;

        String userInput = targetIndex1.getOneBased() + " " + targetIndex2.getOneBased() + " " + PREFIX_TAG.toString();
        assertParseFailure(parser, userInput, MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parseInvalidArgsFailure() throws Exception {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
    }
}


