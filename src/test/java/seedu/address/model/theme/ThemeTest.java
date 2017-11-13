//@@author ChenXiaoman
package seedu.address.model.theme;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ThemeTest {
    @Test
    public void isValidThemeName() throws Exception {
        // invalid theme names
        assertFalse(Theme.isValidThemeName("")); // empty string
        assertFalse(Theme.isValidThemeName(null)); // null value
        assertFalse(Theme.isValidThemeName("a")); // random words

        // valid theme names
        assertTrue(Theme.isValidThemeName("dark"));
        assertTrue(Theme.isValidThemeName("bright"));
    }


}
