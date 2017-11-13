package seedu.address.model.person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Predicate;

//@@author hymss
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Birthday month and day} matches date of current day with respect to
 * user.
 */

public class CheckBirthdays implements Predicate<ReadOnlyPerson> {

    public CheckBirthdays() {

    }

    /**
     * Checks if a person's birthday falls on the current day.
     *
     * @param person
     * @return boolean
     * @throws ParseException
     */

    public boolean showBirthdays(ReadOnlyPerson person) throws ParseException {
        String birthday = person.getBirthday().toString();
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(birthday);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (((calendar.get(Calendar.MONTH)) == Calendar.getInstance().get(Calendar.MONTH))
                && ((calendar.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH))));
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        boolean index = false;
        try {
            index = showBirthdays(person);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CheckBirthdays); // instanceof handles nulls
    }
}
