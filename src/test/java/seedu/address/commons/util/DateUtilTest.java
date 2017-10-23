package seedu.address.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DateUtilTest {

    @Test
    public void check_validDate_success() {
        assertTrue(DateUtil.isValid("15-01-1997"));
        assertTrue(DateUtil.isValid("15-01-1997"));
    }

    @Test
    public void check_invalidDate_failure() {
        assertFalse(DateUtil.isValid("29-02-2017"));
        assertFalse(DateUtil.isValid("31-04"));
    }

    @Test
    public void check_invalidDateFormat_failure() {
        assertFalse(DateUtil.isValid("2017-02-29"));
        assertFalse(DateUtil.isValid("04-31"));
    }
}
