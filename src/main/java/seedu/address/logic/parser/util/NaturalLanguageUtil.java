package seedu.address.logic.parser.util;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.property.exceptions.PropertyNotFoundException;

//@@author yunpengn
/**
 * Utilizes the Natty library to parse datetime representation in human natural language.
 */
public class NaturalLanguageUtil {
    private static Parser nattyParser = new Parser();

    /**
     * Parses a given string representation in human natural language of datetime.
     */
    public static Optional<Date> parseSingleDateTime(String value)
            throws IllegalValueException, PropertyNotFoundException {
        List<DateGroup> groups = nattyParser.parse(value);

        if (groups.isEmpty() || groups.get(0).getDates().isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(groups.get(0).getDates().get(0));
        }
    }
}
