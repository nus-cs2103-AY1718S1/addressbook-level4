package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ImportCommand.ImportType;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ImportCommand object.
 */
public class ImportCommandParser implements Parser<ImportCommand> {
    // Some messages ready to use.
    public static final String IMPORT_TYPE_NOT_FOUND = "The format of the data you want to import is "
            + "currently not supported";

    /* Regular expressions for validation. */
    private static final Pattern IMPORT_COMMAND_FORMAT = Pattern.compile("--(?<importType>\\S+)\\s+(?<path>.+)");
    private static final String ARG_BEGIN_WITH = "--\\S+";
    private static final String IMPORT_DEFAULT_TYPE = "--xml ";

    @Override
    public ImportCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Be default, import from .xml file if not specified by the user.
        if (!args.startsWith(ARG_BEGIN_WITH)) {
            args = IMPORT_DEFAULT_TYPE + args;
        }

        // Matches the import file type and import file path.
        final Matcher matcher = IMPORT_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }

        final String importType = matcher.group("importType").trim();
        if (!checkImportType(importType)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, IMPORT_TYPE_NOT_FOUND));
        }

        final ImportType enumImportType = toEnumType(importType);
        final String path = matcher.group("path").trim();

        return null;
    }

    private boolean checkImportType(String type) {
        return ImportCommand.TO_ENUM_IMPORT_TYPE.containsKey(type);
    }

    private ImportType toEnumType(String type) {
        return ImportCommand.TO_ENUM_IMPORT_TYPE.get(type);
    }
}
