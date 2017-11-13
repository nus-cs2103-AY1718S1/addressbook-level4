package seedu.address.commons.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AppUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();



    @Test
    public void getImageExitingImage() {
        assertNotNull(AppUtil.getImage("/images/rolodex_icon_32.png"));
    }


    @Test
    public void getImageNullGivenThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        AppUtil.getImage(null);
    }

    @Test
    public void checkArgumentTrueNothingHappens() {
        AppUtil.checkArgument(true);
        AppUtil.checkArgument(true, "");
    }

    @Test
    public void checkArgumentFalseWithoutErrorMessageThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        AppUtil.checkArgument(false);
    }

    @Test
    public void checkArgumentFalseWithErrorMessageThrowsIllegalArgumentException() {
        String errorMessage = "error message";
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(errorMessage);
        AppUtil.checkArgument(false,  errorMessage);
    }
}
