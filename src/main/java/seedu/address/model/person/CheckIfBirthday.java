package seedu.address.model.person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Predicate;

//@@author archthegit
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Birthday} matches today.
 */
public class CheckIfBirthday implements Predicate<ReadOnlyPerson> {

    public CheckIfBirthday(){ }

    /**
     * Method checks if month and day match
     */
    public boolean birthdayList(ReadOnlyPerson person)throws ParseException {
        String birthday = person.getBirthday().toString();
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(birthday);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return (((cal.get(Calendar.MONTH)) == Calendar.getInstance().get(Calendar.MONTH))
                && ((cal.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH))));
    }
    @Override
    public boolean test(ReadOnlyPerson person) {
        boolean val = false;
        try {
            val = birthdayList(person);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return val;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CheckIfBirthday); // instanceof handles nulls
    }

}
