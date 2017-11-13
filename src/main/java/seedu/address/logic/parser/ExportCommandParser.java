package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author HanYaodong
/**
 * Parses input argument to an index list and file path and creates a new ImportCommand.
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    public static final String MISSING_FILE_PATH = "Missing file path!\n";

    private static final Pattern INDEXES_AND_FILEPATH =
        Pattern.compile("(?<oneBasedIndexListString>.(((\\d)*(,)*(\\s)*(-)*)+));(?<filePath>.*)");

    @Override
    public ExportCommand parse(String args) throws ParseException {
        final Matcher matcher = INDEXES_AND_FILEPATH.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
        final String oneBasedIndexListString = matcher.group("oneBasedIndexListString");
        final String filePath = matcher.group("filePath").trim();

        // parse indexes
        List<Index> indexes;
        try {
            indexes = ParserUtil.parseRangeIndexList(oneBasedIndexListString);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        // check filePath
        if (filePath.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MISSING_FILE_PATH + ExportCommand.MESSAGE_USAGE));
        }

        return new ExportCommand(indexes, addXmlExtensionToFilePath(filePath));
    }

    /**
     *  Add ".xml" extension if input filePath do not have one.
     */
    private String addXmlExtensionToFilePath(String filePath) {

        if (!filePath.contains(".")) {
            return filePath + ".xml";
        }
        String extension = "";
        if (filePath.charAt(filePath.lastIndexOf('.') - 1) != '/'
            && filePath.charAt(filePath.lastIndexOf('.') - 1) != '\\') {
            extension = filePath.substring(filePath.lastIndexOf('.') + 1, filePath.length());
        }
        if (!extension.equalsIgnoreCase("xml")) {
            return filePath + ".xml";
        }
        return filePath;
    }
}
