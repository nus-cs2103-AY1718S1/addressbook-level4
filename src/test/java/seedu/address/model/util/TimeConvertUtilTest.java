//@@author Hailinx
package seedu.address.model.util;

import static seedu.address.model.util.TimeConvertUtil.EMPTY_STRING;
import static seedu.address.model.util.TimeConvertUtil.convertStringToTime;
import static seedu.address.model.util.TimeConvertUtil.convertTimeToString;
import static seedu.address.testutil.TodoItemUtil.EARLY_TIME_ONE;

import org.junit.Assert;
import org.junit.Test;

public class TimeConvertUtilTest {

    @Test
    public void test() {
        Assert.assertEquals(convertTimeToString(EARLY_TIME_ONE), "01-01-2017 12:00");

        Assert.assertTrue(convertTimeToString(null).equals(EMPTY_STRING));

        Assert.assertEquals(convertStringToTime("01-01-2017 12:00"), EARLY_TIME_ONE);

        Assert.assertNull(convertStringToTime(""));

        Assert.assertNull(convertStringToTime(null));
    }
}
