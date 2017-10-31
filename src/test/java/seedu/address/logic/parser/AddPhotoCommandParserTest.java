//@@author wishingmaid
package seedu.address.logic.parser;

//import static org.junit.Assert.*;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
//import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

//import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddPhotoCommand;
//import seedu.address.model.person.Photo;

public class AddPhotoCommandParserTest {
    private AddPhotoCommandParser parser = new AddPhotoCommandParser();

        /*@Test
        public void parse_indexSpecified_failure() throws Exception {
            // Has no filepath, picture is default picture
            Index targetIndex = INDEX_FIRST_PERSON;
            String userInput = targetIndex.getOneBased() + " " + PREFIX_FILEPATH.toString();
            AddPhotoCommand expectedCommand = new AddPhotoCommand(INDEX_FIRST_PERSON, new Photo(""));
            assertParseSuccess(parser, userInput, expectedCommand);
            
            //Has a filepath that that is input from user
            Index newTargetIndex = INDEX_FIRST_PERSON;
            String newUserInput = newTargetIndex.getOneBased() + "C/Users/pictures/pic.png"
                    + PREFIX_FILEPATH.toString();
            Photo photo = new Photo("");
            photo.resetFilePath("C/Users/pictures/pic.png");
            AddPhotoCommand newExpectedCommand = new AddPhotoCommand(INDEX_FIRST_PERSON, photo);
            assertParseSuccess(parser, newUserInput, newExpectedCommand);


        }*/

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE);

        assertParseFailure(parser, AddPhotoCommand.COMMAND_WORD, expectedMessage);
    }

}

