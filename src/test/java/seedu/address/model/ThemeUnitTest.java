package seedu.address.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ThemeUnitTest {
    @Test
    public void test_setThemeUnit() {

        // set current Theme unit to be Dark
        ThemeUnit.setCurrentThemeUnit(ThemeUnit.THEME_DARK_UNIT);
        assertTrue(ThemeUnit.getCurrentThemeUnit().equals(ThemeUnit.THEME_DARK_UNIT));

        // set current Theme unit to be Light
        ThemeUnit.setCurrentThemeUnit(ThemeUnit.THEME_LIGHT_UNIT);
        assertTrue(ThemeUnit.getCurrentThemeUnit().equals(ThemeUnit.THEME_LIGHT_UNIT));

    }
}
