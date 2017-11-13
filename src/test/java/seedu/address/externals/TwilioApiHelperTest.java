package seedu.address.externals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.external.sms.TwilioApiHelper;

//@@author zameschua
/**
 * Contains tests for {@link TwilioApiHelper}
 * Note that test case for valid phone number is done via exploratory testing
 * Because it sends an actual SMS via Twilio's API
 */
public class TwilioApiHelperTest {
    private static final String STUB_MESSAGE = "This is a stub message";
    private static final String STUB_PHONE_NUMBER_INVALID = "1";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_invalidPhoneNumber_throwsAssertionException() {
        thrown.expect(AssertionError.class);
        TwilioApiHelper.init();
        TwilioApiHelper.sendSms(STUB_MESSAGE, STUB_PHONE_NUMBER_INVALID);
    }
}
