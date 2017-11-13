# danielbrzn
###### \java\seedu\address\commons\events\model\UserPrefsChangedEvent.java
``` java
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.UserPrefs;

/** Indicates the UserPrefs in the model has changed*/

public class UserPrefsChangedEvent extends BaseEvent {

    public final UserPrefs data;

    public UserPrefsChangedEvent(UserPrefs data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of aliases " + data.getAliasMap().size();
    }
}
```
###### \java\seedu\address\commons\events\ui\CloseProgressEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to close the progress window of a task.
 */
public class CloseProgressEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ShowProgressEvent.java
``` java
package seedu.address.commons.events.ui;

import javafx.beans.property.ReadOnlyDoubleProperty;
import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to show the progress window of a task.
 */
public class ShowProgressEvent extends BaseEvent {

    private ReadOnlyDoubleProperty progress;

    public ShowProgressEvent(ReadOnlyDoubleProperty progress) {
        this.progress = progress;
    }

    public ReadOnlyDoubleProperty getProgress() {
        return progress;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ShowUrlEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to show a URL in the BrowserPanel.
 */
public class ShowUrlEvent extends BaseEvent {

    private final String link;

    public ShowUrlEvent(String link) {
        this.link = link;
    }

    public String getUrl() {
        return link;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\util\AuthorizationUtil.java
``` java
package seedu.address.commons.util;

import java.io.IOException;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;
import com.google.api.client.util.Preconditions;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowUrlEvent;

/**
 * A utility class for OAuth authorization with Google
 */

public class AuthorizationUtil extends AuthorizationCodeInstalledApp {

    private final AuthorizationCodeFlow flow;
    private final VerificationCodeReceiver receiver;

    public AuthorizationUtil(AuthorizationCodeFlow flow, VerificationCodeReceiver receiver) {
        super(flow, receiver);
        this.flow = flow;
        this.receiver = receiver;
    }

    /**
     * Authorizes HitMeUp to access user's protected data.
     *
     * @param userId user ID or {@code null} if not using a persisted credential store
     * @return credential
     */
    public Credential authorize(String userId) throws IOException {
        try {
            // open in Browser Panel
            String redirectUri = receiver.getRedirectUri();
            AuthorizationCodeRequestUrl authorizationUrl =
                    flow.newAuthorizationUrl().setRedirectUri(redirectUri);
            browse(authorizationUrl.build());
            // receive authorization code and exchange it for an access token
            String code = receiver.waitForCode();
            TokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectUri).execute();
            // credential is not stored, but returned to caller
            return flow.createAndStoreCredential(response, userId);
        } finally {
            receiver.stop();
        }
    }

    /**
     * Open a browser at the given URL using {@link seedu.address.ui.BrowserPanel}
     * @param url URL to browse
     */
    public static void browse(String url) {
        Preconditions.checkNotNull(url);

        EventsCenter.getInstance().post(new ShowUrlEvent(url));
    }
}
```
###### \java\seedu\address\commons\util\GoogleUtil.java
``` java
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
                if (!Phone.isValidPhone(phone)) {
                    return null;
                }
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
```
###### \java\seedu\address\logic\commands\AliasCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;

/**
 * Adds an alias for a command to the user preferences.
 */
public class AliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "alias";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an alias for a command. "
            + "Parameters: "
            + "ALIAS "
            + "COMMAND "
            + "Example: " + COMMAND_WORD + " "
            + "a "
            + "add ";

    public static final String MESSAGE_SUCCESS = "New alias added: %1$s for %2$s";
    public static final String MESSAGE_OVERRIDE = "Alias %1$s is now mapped to %2$s instead of %3$s";
    public static final String MESSAGE_INVALID_COMMAND = "Command entered is invalid.";
    public static final String MESSAGE_RESTRICTED_ALIAS = "Alias entered is a command name and cannot be mapped. "
            + "Choose a different alias.";
    private final String alias;
    private final String actualCommand;

    /**
     * Creates an AliasCommand to add the specified alias
     */
    public AliasCommand(String alias, String actualCommand) {
        this.alias = alias;
        this.actualCommand = actualCommand;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        if (AddressBookParser.checkValidCommand(actualCommand)) {
            if (AddressBookParser.checkValidCommand(alias)) {
                throw new CommandException(MESSAGE_RESTRICTED_ALIAS);
            }
            String mapping = model.getAlias(this.alias);
            model.addAlias(this.alias, actualCommand);
            if (mapping == null) {
                return new CommandResult(String.format(MESSAGE_SUCCESS, this.alias, actualCommand));
            } else {
                return new CommandResult(String.format(MESSAGE_OVERRIDE, this.alias, actualCommand, mapping));
            }
        } else {
            throw new CommandException(MESSAGE_INVALID_COMMAND);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AliasCommand // instanceof handles nulls
                && alias.equals(((AliasCommand) other).alias)
                && actualCommand.equals(((AliasCommand) other).actualCommand));
    }
}
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setTwitterName(UserName twitterName) {
            this.twitterName = twitterName;
        }

        public Optional<UserName> getTwitterName() {
            return Optional.ofNullable(twitterName);
        }

        public void setInstagramName(UserName instagramName) {
            this.instagramName = instagramName;
        }

        public Optional<UserName> getInstagramName() {
            return Optional.ofNullable(instagramName);
        }

```
###### \java\seedu\address\logic\commands\ImportCommand.java
``` java
package seedu.address.logic.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.services.people.v1.model.Person;

import javafx.concurrent.Task;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CloseProgressEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.ShowProgressEvent;
import seedu.address.commons.util.GoogleUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Imports contacts from the user's specified online service
 */
public class ImportCommand extends UndoableCommand {


    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports contacts from a specified online service. "
            + "Parameters: "
            + "SERVICE_NAME "
            + "Example: " + COMMAND_WORD + " "
            + "google ";

    public static final String MESSAGE_SUCCESS = "%1$s contacts imported. %2$s contacts failed to import.";
    public static final String MESSAGE_IN_PROGRESS = "Importing in progress. Please enter your credentials in the"
            + " Browser Panel. If you do not wish to continue with the import, you can type in other commands.";
    public static final String MESSAGE_INVALID_PEOPLE = "The contacts unable to be imported are: ";
    public static final String MESSAGE_FAILURE = "Failed to import contacts.";
    public static final String MESSAGE_FAILURE_EMPTY = "0 contacts imported as you have zero Google contacts.";
    public static final String MESSAGE_CONNECTION_FAILURE = "Failed to access Google. Check your internet connection or"
            + " try again in a few minutes.";
    public static final int ADDRESSBOOK_SIZE = 1000;
    public static final int FIRST_PERSON_INDEX = 0;

    private static ArrayList<String> invalidPeople;
    private static Credential credential;
    private static HttpTransport httpTransport;
    private static final Logger logger = LogsCenter.getLogger(ImportCommand.class);
    private final String service;

    private int peopleAdded;
    private int peopleNotAdded;

    /**
     * Creates an ImportCommand to add contacts from the specified service
     */
    public ImportCommand(String service) {
        this.service = service.toLowerCase();
        peopleAdded = 0;
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * Executes the Import Command using a Thread. The thread uses methods for authorization with Google and
     * subsequent retrieval of contacts from the service.
     *
     * Returns a CommandResult with an "In Progress" message as the results display will be updated upon
     * execution of the thread.
     */
    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        // Check for connectivity to Google
        if (!GoogleUtil.isReachable()) {
            throw new CommandException(MESSAGE_CONNECTION_FAILURE);
        }
        Thread thread = new Thread(() -> {
            try {
                credential = GoogleUtil.authorize(httpTransport);
                if (credential.equals(null)) {
                    EventsCenter.getInstance().post(new NewResultAvailableEvent(MESSAGE_FAILURE, true));
                    return;
                }
                // Retrieve a list of Persons
                List<Person> connections = GoogleUtil.retrieveContacts(credential, httpTransport);

                if (connections.equals(null)) {
                    EventsCenter.getInstance().post(new NewResultAvailableEvent(MESSAGE_FAILURE_EMPTY, false));
                    return;
                }

                // Import contacts into the application
                importContacts(connections);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        return new CommandResult(MESSAGE_IN_PROGRESS);
    }


    /**
     * Imports contacts into the application using the given {@code List<Person>}
     */
    public void importContacts(List<Person> connections) {

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                int amountToAdd = connections.size();
                invalidPeople = new ArrayList<String>();
                for (Person person : connections) {
                    seedu.address.model.person.Person toAdd = GoogleUtil.convertPerson(person);
                    if (toAdd == null) {
                        invalidPeople.add(person.getNames().get(FIRST_PERSON_INDEX).getDisplayName());
                        continue;
                    }
                    try {
                        model.addPerson(toAdd);
                        peopleAdded++;
                    } catch (DuplicatePersonException e) {
                        e.printStackTrace();
                    }
                    updateProgress(peopleAdded, amountToAdd);
                }
                return null;
            }
        };

        task.setOnSucceeded(t -> {
            EventsCenter.getInstance().post(new CloseProgressEvent());
            peopleNotAdded = connections.size() - peopleAdded;
            String result;
            if (peopleNotAdded > 0) {
                result = constructResultMessage(peopleNotAdded, false);
            } else {
                result = constructResultMessage(peopleNotAdded, true);
            }
            EventsCenter.getInstance().post(new NewResultAvailableEvent(result, false));
        });

        task.setOnFailed(t -> {
            EventsCenter.getInstance().post(new CloseProgressEvent());
            logger.warning(task.getException().toString());
            EventsCenter.getInstance().post(new NewResultAvailableEvent(String.format(MESSAGE_FAILURE), true));
        });

        EventsCenter.getInstance().post(new ShowProgressEvent(task.progressProperty()));
        Thread importThread = new Thread(task);
        importThread.start();
        try {
            importThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Constructs a message for the result
     */
    private String constructResultMessage(int peopleNotAdded, boolean isSuccessful) {
        StringBuilder sb = new StringBuilder();
        if (isSuccessful) {
            sb.append(String.format(MESSAGE_SUCCESS, peopleAdded, peopleNotAdded));

        } else {
            sb.append(String.format(MESSAGE_SUCCESS, peopleAdded, peopleNotAdded));
            sb.append(" ");
            sb.append(MESSAGE_INVALID_PEOPLE);
            sb.append(invalidPeople.toString());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && service.equals(((ImportCommand) other).service));
    }

}
```
###### \java\seedu\address\logic\commands\LocationCommand.java
``` java

package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowUrlEvent;
import seedu.address.commons.util.GoogleUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Accesses the person's location in Google Maps on the browser.
 */

public class LocationCommand extends Command {

    public static final String COMMAND_WORD = "location";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens up the location of the person at the selected"
            + " index in Google Maps. "
            + "Parameters: "
            + "INDEX "
            + "Example: " + COMMAND_WORD + " "
            + "1 ";

    public static final String GOOGLE_MAPS_URL_PREFIX = "https://www.google.com.sg/maps/place/";
    public static final String MESSAGE_SUCCESS = "Loaded location of %1$s";
    public static final String MESSAGE_NO_ADDRESS = "%1$s does not have an address.";
    public static final String MESSAGE_FAILURE = "Failed to load Google Maps. Check your internet connection.";

    private final Index index;

    public LocationCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        // Check if index is valid
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX + index.getOneBased());
        }

        // Check if Google is reachable
        if (!GoogleUtil.isReachable()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        ReadOnlyPerson current = lastShownList.get(index.getZeroBased());

        // Check if Person has an address
        if (current.getAddress().toString().length() == 0) {
            throw new CommandException(String.format(MESSAGE_NO_ADDRESS, current.getName().toString()));
        }

        String finalUrl = GOOGLE_MAPS_URL_PREFIX + parseAddressForUrl(current.getAddress());
        EventsCenter.getInstance().post(new ShowUrlEvent(finalUrl));
        return new CommandResult(String.format(MESSAGE_SUCCESS, current.getName().toString()));
    }

    /**
     * Parses address into a URL-appendable string
     */
    public String parseAddressForUrl(Address address) {
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        String[] addressArray = address.toString().split(" ");

        for (String part : addressArray) {
            sb.append(prefix);
            prefix = "+";
            sb.append(part);
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocationCommand // instanceof handles nulls
                && this.index.equals(((LocationCommand) other).index)); // state check
    }

}

```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
    /**
     * Fills the prefixes that have not been specified by the user with an empty string in the given
     * {@code ArgumentMultimap}.
     */
    private static void fillEmptyPrefixes(ArgumentMultimap argumentMultimap) {
        if (!argumentMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            argumentMultimap.put(PREFIX_EMAIL, "");
        }
        if (!argumentMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            argumentMultimap.put(PREFIX_ADDRESS, "");
        }
        if (!argumentMultimap.getValue(PREFIX_BIRTHDAY).isPresent()) {
            argumentMultimap.put(PREFIX_BIRTHDAY, "");
        }
        if (!argumentMultimap.getValue(PREFIX_TWITTER).isPresent()) {
            argumentMultimap.put(PREFIX_TWITTER, "");
        }
        if (!argumentMultimap.getValue(PREFIX_INSTAGRAM).isPresent()) {
            argumentMultimap.put(PREFIX_INSTAGRAM, "");
        }
        if (!argumentMultimap.getValue(PREFIX_DP).isPresent()) {
            argumentMultimap.put(PREFIX_DP, DEFAULT_DP);
        }
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
    /**
     * Checks if the input command is valid.
     *
     * @param command command part of user input
     * @return boolean determining if command entered is valid
     */
    public static boolean checkValidCommand(String command) {
        switch (command) {
        case AddCommand.COMMAND_WORD:
        case EditCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_WORD:
        case FindCommand.COMMAND_WORD:
        case ListCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_WORD:
        case ExitCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_WORD:
        case AliasCommand.COMMAND_WORD:
        case TagDeleteCommand.COMMAND_WORD:
        case TagEditCommand.COMMAND_WORD:
        case EmailCommand.COMMAND_WORD:
        case ImportCommand.COMMAND_WORD:
        case ExportCommand.COMMAND_WORD:
        case SocialCommand.COMMAND_WORD:
        case LocationCommand.COMMAND_WORD:
            return true;
        default:
            return false;
        }
    }

}
```
###### \java\seedu\address\logic\parser\AliasCommandParser.java
``` java
package seedu.address.logic.parser;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AliasCommand object
 */
public class AliasCommandParser implements Parser<AliasCommand> {

    private static final int INDEX_ALIAS = 0;
    private static final int INDEX_COMMAND = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the AliasCommand
     * and returns an AliasCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public AliasCommand parse(String userInput) throws ParseException {
        String[] args = userInput.trim().split(" ");

        try {
            String userAlias = args[INDEX_ALIAS];
            String command = args[INDEX_COMMAND];

            return new AliasCommand(userAlias, command);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AliasCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\ImportCommandParser.java
``` java
package seedu.address.logic.parser;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public ImportCommand parse(String userInput) throws ParseException {
        String trimmedInput = userInput.trim();

        // unexpected input
        if (trimmedInput.contains(" ") || trimmedInput.length() == 0 || !trimmedInput.contains("google")) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    ImportCommand.MESSAGE_USAGE));
        } else {
            return new ImportCommand(trimmedInput);
        }
    }
}
```
###### \java\seedu\address\logic\parser\LocationCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.LocationCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LocationCommand object
 */
public class LocationCommandParser implements Parser<LocationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LocationCommand
     * and returns a LocationCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public LocationCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new LocationCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LocationCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> twitterUserName} into an {@code Optional<UserName>} if {@code twitterUserName}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<UserName> parseTwitterName(Optional<String> twitterUserName) throws IllegalValueException {
        requireNonNull(twitterUserName);
        return twitterUserName.isPresent() ? Optional.of(new UserName(twitterUserName.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> twitterUserName} into an {@code Optional<UserName>} if {@code twitterUserName}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<UserName> parseInstagramName(Optional<String> instagramUserName)
            throws IllegalValueException {
        requireNonNull(instagramUserName);
        return instagramUserName.isPresent() ? Optional.of(new UserName(instagramUserName.get())) : Optional.empty();
    }

```
###### \java\seedu\address\model\Model.java
``` java
    /** Clears existing aliases from UserPrefs and replaces with the provided new data */
    void resetAlias(HashMap<String, String> prevAliasMap);

```
###### \java\seedu\address\model\Model.java
``` java
    /** Returns the User Preferences */
    UserPrefs getUserPrefs();

```
###### \java\seedu\address\model\Model.java
``` java
    /** Adds the given alias */
    void addAlias(String alias, String command);

```
###### \java\seedu\address\model\Model.java
``` java
    /** Gets the command mapping for an alias */
    String getAlias(String alias);

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void resetAlias(HashMap<String, String> prevAliasMap) {
        userPrefs.resetAlias(prevAliasMap);
        indicateUserPrefsChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public UserPrefs getUserPrefs() {
        return userPrefs;
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void addAlias(String alias, String command) {
        userPrefs.addAlias(alias, command);
        indicateUserPrefsChanged();
    }

    @Override
    public String getAlias(String alias) {
        return userPrefs.getAlias(alias);
    }


    /** Raises an event to indicate the model has changed */
    private void indicateUserPrefsChanged() {
        raise(new UserPrefsChangedEvent(userPrefs));
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    @Override
    public ObjectProperty<UserName> twitterNameProperty() {
        return twitterName;
    }

    @Override
    public UserName getTwitterName() {
        return twitterName.get();
    }

    public void setTwitterName(UserName twitterName) {
        this.twitterName.set(requireNonNull(twitterName));
    }

    @Override
    public ObjectProperty<UserName> instagramNameProperty() {
        return instagramName;
    }

    @Override
    public UserName getInstagramName() {
        return instagramName.get();
    }

    public void setInstagramName(UserName instagramName) {
        this.instagramName.set(requireNonNull(instagramName));
    }

```
###### \java\seedu\address\model\person\UserName.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's social media username in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUserName(String)}
 */
public class UserName {

    public static final String MESSAGE_USERNAME_CONSTRAINTS =
            "Social media username should only contain alphanumeric characters, underscores or a period character.";

    /*
     * The first character of the username must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * It should only contain alphanumeric characters, underscores or a period character
     */
    public static final String USERNAME_VALIDATION_REGEX = "[\\w\\.]+";

    public final String value;

    /**
     * Validates given username.
     *
     * @throws IllegalValueException if given username string is invalid.
     */
    public UserName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedUserName = name.trim();
        if (trimmedUserName.length() != 0 && !isValidUserName(trimmedUserName)) {
            throw new IllegalValueException(MESSAGE_USERNAME_CONSTRAINTS);
        }
        this.value = trimmedUserName;
    }

    /**
     * Returns true if a given string is a valid social media username.
     */
    public static boolean isValidUserName(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserName // instanceof handles nulls
                && this.value.equals(((UserName) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\UserPrefs.java
``` java
    public void resetAlias(HashMap<String, String> prevAliasMap) {
        this.aliasMap = prevAliasMap;
    }

```
###### \java\seedu\address\model\UserPrefs.java
``` java
    public HashMap<String, String> getAliasMap() {
        return aliasMap;
    }

    public void addAlias(String alias, String command) {
        aliasMap.put(alias, command);
    }

    public String getAlias(String alias) {
        return aliasMap.get(alias);
    }

```
###### \java\seedu\address\storage\Storage.java
``` java
    /**
     * Saves the current version of the User Preferences to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleUserPrefsChangedEvent(UserPrefsChangedEvent upce);
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    @Subscribe
    public void handleUserPrefsChangedEvent(UserPrefsChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveUserPrefs(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

```
###### \java\seedu\address\ui\BrowserWindow.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;

/**
 * Controller for a help page
 */
public class BrowserWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Authentication";

    @FXML
    private WebView browser;

    private final Stage dialogStage;

    public BrowserWindow(String authorizationUrl) {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(true); //TODO: set a more appropriate initial size
        FxViewUtil.setStageIcon(dialogStage, ICON);

        browser.getEngine().load(authorizationUrl);
    }

    /**
     * Shows the authorization window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing authorization page for importing contacts.");
        dialogStage.showAndWait();
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java

    /**
     * Opens the provided URL in the built-in browser
     * @param link is a URL to be opened in the BrowserPanel
     */
    private void handleUrl(String link) {
        browserPanel.loadPage(link);
    }

    /**
     * Opens the progress window.
     */
    @FXML
    private void handleProgress(ReadOnlyDoubleProperty progress) {
        Platform.runLater(() -> {
            pWindow = new ProgressWindow(progress);
            pWindow.show();
        });

    }

    /**
     * Closes the progress window.
     */
    @FXML
    private void handleCloseProgress() {
        pWindow.getDialogStage().close();
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java

    @Subscribe
    private void handleShowUrlEvent(ShowUrlEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleUrl(event.getUrl());
    }

    @Subscribe
    private void handleSocialEvent(SocialRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleSocial(event.getUserName(), event.getSocialMediaLink());
    }

    @Subscribe
    private void handleShowProgressEvent(ShowProgressEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleProgress(event.getProgress());

    }

    @Subscribe
    private void handleCloseProgressEvent(CloseProgressEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleCloseProgress();

    }

```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<PersonCard> {

        @Override
        protected void updateItem(PersonCard person, boolean empty) {
            super.updateItem(person, empty);

            Platform.runLater(() -> {
                if (empty || person == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(person.getRoot());
                }

            });

        }
    }
}
```
###### \java\seedu\address\ui\ProgressWindow.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a progress window
 */
public class ProgressWindow extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(ProgressWindow.class);
    private static final String FXML = "ProgressWindow.fxml";
    private static final String TITLE = "Import Progress";

    private final Stage dialogStage;

    @FXML
    private ProgressBar progBar;

    private ReadOnlyDoubleProperty progress;

    public ProgressWindow(ReadOnlyDoubleProperty progress) {
        super(FXML);
        this.progress = progress;
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.initStyle(StageStyle.UNDECORATED);
        dialogStage.setAlwaysOnTop(true);
        progBar = (ProgressBar) scene.lookup("#progressBar");
        bindListeners(progress);
    }

    /**
     * Binds the progress bar to a provided {@code ReadOnlyDoubleProperty}
     * so that it will be notified of any changes.
     */
    private void bindListeners(ReadOnlyDoubleProperty progress) {
        progBar.progressProperty().bind(progress);
    }

    /**
     * Shows a progress window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing progress window.");
        dialogStage.show();
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public ReadOnlyDoubleProperty getProgress() {
        return progress;
    }

}
```
###### \resources\view\ProgressWindow.fxml
``` fxml

<?import javafx.scene.control.ProgressBar?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.text.Font?>
<?import javafx.scene.text.Text?>

<AnchorPane prefHeight="125.0" prefWidth="250.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <Text layoutX="25.0" layoutY="55.0" strokeType="OUTSIDE" strokeWidth="0.0" text="Adding Contacts">
         <font>
            <Font size="25.0" />
         </font>
      </Text>
      <ProgressBar id="progressBar" fx:id="progressBar" layoutX="25.0" layoutY="63.0" prefWidth="200.0" progress="0.0" />
   </children>
</AnchorPane>
```
