//@@author A0162268B
package seedu.address.logic.parser.event;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Format: dd/mm/yyyy
 */
public class DateParser {

    public int[] parse(String date) throws ParseException {
        int[] dateInfo = new int[3];

        try {
            dateInfo[0] = Integer.parseInt(date.substring(0, 2));
            dateInfo[1] = Integer.parseInt(date.substring(3, 5));
            dateInfo[2] = Integer.parseInt(date.substring(6, 10));
        } catch (Exception e) {
            throw new ParseException(e.getMessage());
        }
        return dateInfo;
    }
}

