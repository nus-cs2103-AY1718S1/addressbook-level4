package seedu.address.security;

import org.junit.Assert;
import org.junit.Test;

public class SecurityUtilTest {

    @Test
    public void test_isPasswordValid() {
        Assert.assertTrue(SecurityUtil.isValidPassword("1234"));

        Assert.assertFalse(SecurityUtil.isValidPassword(null));
        Assert.assertFalse(SecurityUtil.isValidPassword(""));
        Assert.assertFalse(SecurityUtil.isValidPassword("12"));
        Assert.assertFalse(SecurityUtil.isValidPassword("dd"));
    }
}
