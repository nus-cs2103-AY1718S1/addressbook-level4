package seedu.address.model.task;

import java.util.Comparator;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;

/**
 * A task comparator that compares the days of deadline from today
 */
public class TaskComparator implements Comparator{
    @Override
    public int compare(Object obj, Object obj1) {
        Task d = (Task) obj;
        Task d1 = (Task) obj1;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date0 = "";
        String date1 = "";
        try {
            Date deadline0 = ParserUtil.parseDate(d.getDeadline().toString());
            Date deadline1 = ParserUtil.parseDate(d1.getDeadline().toString());
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            date0 = dateFormat.format(deadline0);
            date1 = dateFormat.format(deadline1);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        LocalDate deaddate0 = LocalDate.parse(date0, formatter);
        LocalDate deaddate1 = LocalDate.parse(date1, formatter);

        return deaddate0.getDayOfYear() - deaddate1.getDayOfYear();
    }
}
