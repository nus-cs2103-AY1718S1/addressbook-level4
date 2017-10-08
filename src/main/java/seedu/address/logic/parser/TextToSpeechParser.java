package seedu.address.logic.parser;

import seedu.address.logic.TextToSpeech;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new TextToSpeech object
 */
public class TextToSpeechParser {

    /**
     * Parses the given {@code String} of arguments in the context of the String of words
     * and returns an TextToSpeech object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TextToSpeech parse(String args) throws ParseException {
        return new TextToSpeech(args);
    }
}
