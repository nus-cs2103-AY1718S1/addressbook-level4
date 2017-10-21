package seedu.address.model.person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.function.Predicate;

import java.util.Date;

import seedu.address.commons.util.StringUtil;

public class CheckIfBirthday {

    private final String birthday;

    public CheckIfBirthday(Birthday bday){
        this.birthday = bday.toString();


    }
    public boolean birthdayList()throws ParseException {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(birthday);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return(((cal.get(Calendar.MONTH))== Calendar.getInstance().MONTH) &&
                ((cal.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().DAY_OF_MONTH)));
    }

}
