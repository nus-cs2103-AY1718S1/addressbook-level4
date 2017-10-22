//@@author A0162268B
package seedu.address.logic.parser.event;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Format: dd/mm/yyyy timestart-timeend
 */
public class DateParser {

    public int[] parse(String date) throws ParseException {
        int[] dateInfo = new int[5];

        try {
            dateInfo[0] = Integer.parseInt(date.substring(0, 2));
            dateInfo[1] = Integer.parseInt(date.substring(3, 5));
            dateInfo[2] = Integer.parseInt(date.substring(6, 10));
            dateInfo[3] = Integer.parseInt(date.substring(11, 15));
            dateInfo[4] = Integer.parseInt(date.substring(16, 20));
        } catch (Exception e) {
            throw new ParseException(e.getMessage());
        }
        return dateInfo;
    }
}

