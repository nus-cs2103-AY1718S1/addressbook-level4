package seedu.address.model.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;

//@@author tpq95
/**
 * A task comparator that compares the days of deadline from today
 */
public class TaskComparator implements Comparator {
    private final String dateNull = "30-12-2999";

    @Override
    public int compare(Object obj, Object obj1) {
        Task d = (Task) obj;
        Task d1 = (Task) obj1;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date0 = "";
        String date1 = "";
        int value = 0;

        try {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            if (!d.getDeadline().isEmpty()) {
                Date deadline0 = ParserUtil.parseDate(d.getDeadline().toString());
                date0 = dateFormat.format(deadline0);
            } else {
                date0 = dateNull;
            }
            if (!d1.getDeadline().isEmpty()) {
                Date deadline1 = ParserUtil.parseDate(d1.getDeadline().toString());
                date1 = dateFormat.format(deadline1);
            } else {
                date1 = dateNull;
            }
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        LocalDate deaddate0 = LocalDate.parse(date0, formatter);
        LocalDate deaddate1 = LocalDate.parse(date1, formatter);
        value = deaddate0.compareTo(deaddate1);

        return value;
    }
}
