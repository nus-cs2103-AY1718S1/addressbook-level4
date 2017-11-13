//@@author danielbrzn
package seedu.address.commons.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.Address;
import com.google.api.services.people.v1.model.Birthday;
import com.google.api.services.people.v1.model.Date;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.PhoneNumber;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.model.person.DisplayPic;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UserName;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.ui.MainWindow;

/**
 * Contains utility methods for Google APIs
 */

public class GoogleUtil {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final int HTTP_PORT = 80;
    private static final String GOOGLE_ADDRESS = "www.google.com";
    private static final String DIALING_PREFIX = "+";
    private static final String APPLICATION_NAME = "HitMeUp";

    /**
     * Returns true if Google is reachable
     */
    public static boolean isReachable() {
        Socket socket = new Socket();
        InetSocketAddress googleAddr = new InetSocketAddress(GOOGLE_ADDRESS, HTTP_PORT);

        try {
            socket.connect(googleAddr);
            return true;
        } catch (IOException ioe) {
            return false;
        }
    }

    /**
     * Creates an authorized {@code Credential} for the application to interact with
     * the Google People API
     */
    public static Credential authorize(HttpTransport httpTransport) throws IOException {
        // load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
            new InputStreamReader(ImportCommand.class.getResourceAsStream("/client_secrets.json")));

        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets,
                Collections.singleton("https://www.googleapis.com/auth/contacts.readonly")).build();

        // authorize
        return new AuthorizationUtil(flow, new LocalServerReceiver()).authorize("user");
    }

    /**
     * Retrieves a {@code List<Person>} of contacts from Google using the provided {@code Credential}
     */
    public static List<Person> retrieveContacts(Credential credential, HttpTransport httpTransport) {
        PeopleService peopleService =
                new PeopleService.Builder(httpTransport, JSON_FACTORY, credential)
                        .setApplicationName(APPLICATION_NAME).build();

        ListConnectionsResponse response = null;
        try {
            response = peopleService.people().connections().list("people/me")
                    .setPageSize(ImportCommand.ADDRESSBOOK_SIZE)
                    .setPersonFields("names,emailAddresses,phoneNumbers,addresses,birthdays")
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert response != null;
        return response.getConnections();
    }
    /**
     * Converts a {@code Person} from Google to a {@code seedu.address.model.person.Person}
     * Returns null if there is no name in the {@code Person}
     * Returns {@code seedu.address.model.person.Person} if there is at least a name
     */
    public static seedu.address.model.person.Person convertPerson(Person person) throws IllegalValueException {
        String name;
        String phone;
        String email;
        String birthday;
        String address;

        List<Name> names = person.getNames();
        List<PhoneNumber> numbers = person.getPhoneNumbers();
        List<EmailAddress> emailAddresses = person.getEmailAddresses();
        List<Birthday> birthdays = person.getBirthdays();
        List<Address> addresses = person.getAddresses();

        // get first value for each list
        if (names != null && names.size() > 0) {
            name = names.get(0).getDisplayName();
            if (!seedu.address.model.person.Name.isValidName(name)) {
                return null;
            }
        } else {
            return null;
        }

        if (numbers != null && numbers.size() > 0) {
            // Google phone numbers are stored in either canonical form or value
            try {
                phone = numbers.get(0).getCanonicalForm().replace(DIALING_PREFIX, "");
            } catch (NullPointerException npe) {
                phone = numbers.get(0).getValue().replace(DIALING_PREFIX, "");
            }
            if (!Phone.isValidPhone(phone)) {
                return null;
            }
        } else {
            return null;
        }
        if (emailAddresses != null && emailAddresses.size() > 0) {
            email = emailAddresses.get(0).getValue();
        } else {
            email = "";
        }
        if (birthdays != null && birthdays.size() > 0) {
            Date googleBirthday = birthdays.get(0).getDate();
            if (googleBirthday == null) {
                birthday = "";
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%02d", googleBirthday.getDay()));
                sb.append("/");
                sb.append(String.format("%02d", googleBirthday.getMonth()));
                sb.append("/");
                sb.append(googleBirthday.getYear());
                birthday = sb.toString();
            }
        } else {
            birthday = "";
        }
        if (addresses != null && addresses.size() > 0) {
            address = addresses.get(0).getFormattedValue();
        } else {
            address = "";
        }
        Set<Tag> defaultTags = SampleDataUtil.getTagSet("Google");

        seedu.address.model.person.Name nameAdd = new seedu.address.model.person.Name(name);
        seedu.address.model.person.Person toAdd;
        toAdd = new seedu.address.model.person.Person(nameAdd,
                new Phone(phone), new Email(email), new seedu.address.model.person.Address(address),
                new seedu.address.model.person.Birthday(birthday), new UserName(""), new UserName(""),
                new DisplayPic(MainWindow.DEFAULT_DP),
                defaultTags);
        return toAdd;
    }
}
