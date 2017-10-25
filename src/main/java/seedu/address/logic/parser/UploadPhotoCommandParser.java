package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.StringTokenizer;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UploadPhotoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Photo;

/**
 * Parses input arguments and creates a new UploadPhotoCommand object
 */
public class UploadPhotoCommandParser implements Parser<UploadPhotoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UploadPhotoCommand
     * and returns a UploadPhotoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UploadPhotoCommand parse(String args) throws ParseException {
        try {
            StringTokenizer st = new StringTokenizer(args);
            Index index = ParserUtil.parseIndex(st.nextToken());

            String photoPath;
            if (st.hasMoreTokens()) {
                photoPath = st.nextToken();
            } else {
                photoPath = "..\\addressbook4\\docs\\images\\default_photo.png";
            }

            while (st.hasMoreTokens()) {
                photoPath += " ";
                photoPath += st.nextToken();
            }

            Photo photoToUpload = new Photo(photoPath);
            return new UploadPhotoCommand(index, photoToUpload);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UploadPhotoCommand.MESSAGE_USAGE));
        }
    }

}
