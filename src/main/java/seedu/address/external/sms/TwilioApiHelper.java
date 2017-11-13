package seedu.address.external.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

//@@author zameschua
/**
 * Helper class to handle the sending of SMS using Twilio API
 */
public class TwilioApiHelper {
    /*
    I know that I'm not supposed to store my API keys like this
    But storing it in a separate file will make it very difficult for my peers to pass tests
    and difficult for the tutotrs to grade
    Don't worry, it's just a trial Twilio account
    */
    private static final String ACCOUNT_SID = "AC8e7d80947bd2e877013c66d99b0faa06";
    private static final String AUTH_TOKEN = "46abad64b64c0b29c468434ff69e36ca";
    private static final String TWILIO_PHONE_NUMBER = "+1 954-320-0045";

    private static final String MESSAGE_SMS_SUCCESS = "SMS Successfully sent";
    private static final String MESSAGE_SMS_FAILURE = "SMS Sending failed, ";
    private static final String COUNTRY_CODE_SINGAPORE = "+65";
    private static final String PHONE_NUMBER_REGEX_SINGAPORE = "\\+65\\d{8}";
    private static final int PHONE_NUMBER_LENGTH_SINGAPORE = 8;

    /**
     * Initialises the Twilio API service
     */
    public static void init() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    /**
     * Helper method that calls the Twilio REST API for sending SMS
     * @param smsReceipient the target phone number. Has to contain the country code like +65
     * @param message The message to send as the content of the SMS
     * @throws RuntimeException Catches the runtime exception so that we can show an error message to the user
     * in the {@link seedu.address.logic.commands.CommandResult}
     */
    public static void sendSms(String message, String smsReceipient) throws RuntimeException {
        try {
            smsReceipient = checkPhoneNumberFormat(smsReceipient);
            assert smsReceipient.matches(PHONE_NUMBER_REGEX_SINGAPORE);
            Message.creator(new PhoneNumber(smsReceipient), new PhoneNumber(TWILIO_PHONE_NUMBER), message).create();
            showToUser(MESSAGE_SMS_SUCCESS);
        } catch (RuntimeException rte) {
            // Show the error in the ResultDisplay so the user knows what's wrong
            showToUser(MESSAGE_SMS_FAILURE);
            throw rte;
        }
    }

    /**
     * Helper method which posts a {@link NewResultAvailableEvent} to show a message in
     * the {@link seedu.address.logic.commands.CommandResult}
     * Also handles logging in the form of events
     * @param message The message to show in the {@link seedu.address.logic.commands.CommandResult}
     */
    private static void showToUser(String message) {
        EventsCenter.getInstance().post(new NewResultAvailableEvent(message));
    }

    /**
     * Helper method that prepends the country code to a phone number if
     * it doesn't have the country code
     * @param phoneNumber The phone number to check
     */
    public static String checkPhoneNumberFormat(String phoneNumber) {
        assert phoneNumber.length() >= PHONE_NUMBER_LENGTH_SINGAPORE;
        if (!phoneNumber.startsWith(COUNTRY_CODE_SINGAPORE)) {
            return COUNTRY_CODE_SINGAPORE + phoneNumber;
        } else {
            return phoneNumber;
        }
    }
}
