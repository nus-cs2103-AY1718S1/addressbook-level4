//@@author erik0704
package seedu.address.commons.util;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;


public class DateUtil {
    public static Date createCurrentDate() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat newformat = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(new Date());
        try {

            Date now = newformat.parse(str);
            String newStr = newformat.format(now);
            Date date = newformat.parse(newStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return format.parse("05-05-05");
        }
    }
}
