# PhuaJunJie
###### \main\java\seedu\address\commons\events\ui\GetRedirectUrlEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a selection change in the Person List Panel
 */
public class GetRedirectUrlEvent extends BaseEvent {

    private String reDirectUrl;

    public GetRedirectUrlEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    //Setter of redirect URL
    public void setRedirectUrl(String reDirectUrl) {
        this.reDirectUrl = reDirectUrl;
    }

    //Getter
    public String getReDirectUrl() {
        return reDirectUrl;
    }
}

```
###### \main\java\seedu\address\commons\events\ui\LoadLoginEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

```
###### \main\java\seedu\address\commons\events\ui\LoadLoginEvent.java
``` java
/**
 * Represents a selection change in the Person List Panel
 */
public class LoadLoginEvent extends BaseEvent {


    private final String authenticationUrl;

    public LoadLoginEvent(String authenticationUrl) {
        this.authenticationUrl = authenticationUrl;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getAuthenticationUrl() {
        return authenticationUrl;
    }
}
```
###### \main\java\seedu\address\commons\GoogleAuthenticator.java
``` java
/**
 * This class contains methods of the google Auth Api. For authentication after login.
 */
public class GoogleAuthenticator {

    private HttpTransport transport = new NetHttpTransport();
    private JacksonFactory jsonFactory = new JacksonFactory();

    private String clientId = "650819214900-b3m4dv6igjlf9q3nq9eqsbmspask57kp.apps.googleusercontent.com";
    private String clientSecret = "ttunyBEmZMrK_a9MH_qc1kus";
    private String redirectUrl = "https://contacts.google.com";

    private String scope1 = "https://www.googleapis.com/auth/contacts.readonly";
    private String scope2 = "https://www.googleapis.com/auth/plus.login";
    private String scope3 = "https://www.googleapis.com/auth/user.phonenumbers.read";
    private String scope4 = "https://www.googleapis.com/auth/contacts";
    private String scope5 = "https://mail.google.com/";


    private String authorizationUrl;


    //Constructor
    public GoogleAuthenticator() {
        this.authorizationUrl = new GoogleBrowserClientRequestUrl(clientId, redirectUrl,
                Arrays.asList(scope1, scope2, scope3, scope4, scope5)).build();
    }


    //Getter for Authorization URL for user login
    public String getAuthorizationUrl() {
        return authorizationUrl;
    }


    /**
     * This method obtains the token from the redirect URL after successful login
     */
    public String getToken() throws GoogleAuthException {
        String token;
        boolean indexToken;
        try {
            GetRedirectUrlEvent event = new GetRedirectUrlEvent();
            EventsCenter.getInstance().post(event);
            String url = event.getReDirectUrl();
            indexToken = checkValidTokenIndex(url.indexOf("token="));
            if (indexToken) {
                token = url.substring(url.indexOf("token=") + 6, url.indexOf("&"));
            } else {
                throw new StringIndexOutOfBoundsException();
            }
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            throw new GoogleAuthException("Authentication Failed. Please login again.");
        }
        return token;
    }

    /**
     * Checks if google login is valid
     * @returns true if valid (i.e. index is not -1) or false if not valid(i.e. index is -1)
     */
    public boolean checkValidTokenIndex(int index) {
        if (index == -1) {
            return false;
        }
        return true;
    }

    /**
     * Obtain google credentials from token
     */
    public GoogleCredential getCredential(String token) throws IOException {

        GoogleTokenResponse googleToken = new GoogleTokenResponse();
        googleToken.setAccessToken(token);

        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(transport)
                .setJsonFactory(jsonFactory)
                .setClientSecrets(clientId, clientSecret)
                .build()
                .setFromTokenResponse(googleToken);
        return credential;
    }

    /**
     * Build PeopleService using google credentials
     */
    public PeopleService buildPeopleService(GoogleCredential credential) {
        PeopleService peopleService =
                new PeopleService.Builder(transport, jsonFactory, credential).build();
        return peopleService;
    }

    /**
     * Obtain the list of Contacts from google
     */
    public List<Person> getConnections(PeopleService peopleService)  throws IOException {
        ListConnectionsResponse response;
        response = peopleService.people().connections().list("people/me")
                .setPersonFields("names,emailAddresses,phoneNumbers,addresses")
                .execute();
        List<Person> connections = response.getConnections();

        return connections;
    }
    //@author srishag
    /**
     * Obtain transport from google
     * @return transport
     */
    public HttpTransport getTransport() {
        return transport;
    }

    /**
     * Obtain JsonFactory from google
     * @return JsonFactory
     */
    public JacksonFactory getJsonFactory() {
        return jsonFactory;
    }
}
```
###### \main\java\seedu\address\commons\GoogleContactsBuilder.java
``` java
package seedu.address.commons;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.Person;

import seedu.address.logic.commands.exceptions.GoogleAuthException;

/**
 * This class builds a list of Person from google contacts
 */
public class GoogleContactsBuilder {

    private final GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
    private List<Person> personlist = new ArrayList<>();
    private PeopleService peopleService;


    //Returns a list of persons from the user's google contacts using the GoogleAuthenticator class
    public GoogleContactsBuilder() throws IOException, GoogleAuthException {
        String token = googleAuthenticator.getToken();
        GoogleCredential credential = googleAuthenticator.getCredential(token);
        PeopleService peopleService = googleAuthenticator.buildPeopleService(credential);
        this.peopleService = peopleService;
        this.personlist = googleAuthenticator.getConnections(peopleService);
    }

    //Returns PeopleService for export
    public PeopleService getPeopleService() {
        return peopleService;
    }

    //Returns list of contacts from Google contacts for import/sync
    public List<Person> getPersonlist() {
        return personlist;
    }
}
```
###### \main\java\seedu\address\commons\util\StringUtil.java
``` java
    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but only a partial word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "ab") == true
     *       containsWordIgnoreCase("ABc def", "D") == true
     *       containsWordIgnoreCase("ABc def", "Ac") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty
     */
    public static boolean containsAlphabetIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");
        for (String wordInSentence: wordsInPreppedSentence) {
            if (wordInSentence.toLowerCase().contains(preppedWord.toLowerCase())
                    && (wordInSentence.toLowerCase().indexOf(preppedWord.toLowerCase()) == 0)) {
                return true;
            }
        }
        return false;
    }
```
###### \main\java\seedu\address\logic\commands\exceptions\GoogleAuthException.java
``` java
package seedu.address.logic.commands.exceptions;

/**
 * Represents an error which occurs during authentication process
 */
public class GoogleAuthException extends Exception {
    public GoogleAuthException(String message) {
        super(message);
    }
}
```
###### \main\java\seedu\address\logic\commands\ExportCommand.java
``` java
package seedu.address.logic.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.api.services.people.v1.model.Address;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.PhoneNumber;

import seedu.address.commons.GoogleContactsBuilder;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.GoogleAuthException;
import seedu.address.model.person.GoogleId;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Exports contacts in addressbook to google contacts
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_ALIAS = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Export contacts to google"
            + "process\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD;

    private GoogleContactsBuilder builder;

    /**
     * Constructor for ExportCommand (Gets the Google Builder for exporting of contacts after authentication)
     */
    public ExportCommand() throws GoogleAuthException {
        try {
            builder = new GoogleContactsBuilder();
        } catch (IOException e) {
            throw new GoogleAuthException("Authentication Failed. Please login again.");
        }
    }

    /**
     * This constructor exists only for the sake of testing so as to pass through a dummy google builder.
     * Will not be used in the main implementation of the programme
     */
    public ExportCommand(GoogleContactsBuilder builder) {
        this.builder = builder;
    }

    @Override
    public CommandResult execute() throws GoogleAuthException, CommandException {
        int contactsExportedCount = 0;
        int errorExportCount = 0;
        List<ReadOnlyPerson> addressBookList = model.getAddressBook().getPersonList();
        Person googleContact;

        if (addressBookList.isEmpty()) {
            throw new CommandException("No contacts in addressbook to export");
        }

        for (ReadOnlyPerson addressContact : addressBookList) {
            if (addressContact.getGoogleId().value.equals("not GoogleContact")) {
                try {
                    googleContact = createGoogleContact(addressContact);
                    googleContact = builder.getPeopleService().people().createContact(googleContact).execute();
                    model.updatePerson(addressContact, getNewAddressBookContact(addressContact, googleContact));
                    contactsExportedCount++;
                } catch (IOException | NullPointerException e) {
                    throw new GoogleAuthException("Authentication Failed. Please login again.");
                } catch (IllegalValueException | PersonNotFoundException e) {
                    errorExportCount++;
                }
            }
        }
        String commandMessage = setCommandMessage(contactsExportedCount, errorExportCount);
        return new CommandResult(commandMessage);
    }

    /**
     * Creates a Person to add in google contact
     */
    public Person createGoogleContact(ReadOnlyPerson person)throws IOException {
        Person contactToCreate = new Person();
        List names = new ArrayList();
        List email = new ArrayList();
        List phone = new ArrayList();
        List address = new ArrayList();

        names.add(new Name().setGivenName(person.getName().fullName));
        email.add(new EmailAddress().setValue(person.getEmail().value));
        phone.add(new PhoneNumber().setValue(person.getPhone().value));
        address.add(new Address().setStreetAddress(person.getAddress().value));

        return contactToCreate.setNames(names).setEmailAddresses(email).setPhoneNumbers(phone).setAddresses(address);
    }

    /**
     * Creates a Person to update in the addressbook.
     * To update new attributes : google ID and google contact tag
     */
    public ReadOnlyPerson getNewAddressBookContact(ReadOnlyPerson contact, Person createdContact)
            throws IllegalValueException {

        GoogleId id = new GoogleId(createdContact.getResourceName().substring(8));
        Tag tag = new Tag("GoogleContact");
        Set<Tag> tags = new HashSet<>();
        for (Tag existingTags : contact.getTags()) {
            if (!existingTags.getTagName().equals("GoogleContact")) {
                tags.add(existingTags);
            }
        }
        tags.add(tag);

        return new seedu.address.model.person.Person(contact.getName(), contact.getPhone(),
                contact.getEmail(), contact.getAddress(), contact.getBirthday(),
                contact.getFacebookAddress(), tags, id);
    }

    /**
     * Creates a detailed message on the status of the sync
     */
    public String setCommandMessage(int contactsExportedCount, int errorExportCount) {

        String commandMessage = String.format(Messages.MESSAGE_EXPORT_CONTACT, contactsExportedCount);
        if (errorExportCount == 0) {
            commandMessage += "All contacts can be now found in google contact";
        } else {
            commandMessage += String.format(Messages.MESSAGE_EXPORT_ERROR, errorExportCount);
        }
        return commandMessage;
    }
}
```
###### \main\java\seedu\address\logic\commands\FindLettersCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.person.NameContainsAlphabetsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the characters.\
 * This function works without having the user to hit the "Enter" key
 * Keyword matching is case insensitive.
 */
public class FindLettersCommand extends Command {

    public static final String COMMAND_WORD = "Sfind";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the sequential alphabets\n"
            + "Parameters: Sequence of characters [MORE_KEYWORDS]...\n";

    private final NameContainsAlphabetsPredicate predicate;

    public FindLettersCommand(NameContainsAlphabetsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForAlphabetListSummary(model.getFilteredPersonList().size()));
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindLettersCommand // instanceof handles nulls
                && this.predicate.equals(((FindLettersCommand) other).predicate)); // state check
    }
}
```
###### \main\java\seedu\address\logic\commands\ImportCommand.java
``` java
package seedu.address.logic.commands;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.api.services.people.v1.model.Person;

import seedu.address.commons.GoogleContactsBuilder;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.GoogleAuthException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.FacebookAddress;
import seedu.address.model.person.GoogleId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * Imports contacts to from google contacts to addressbook
 */
public class ImportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "import";
    public static final String COMMAND_ALIAS = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Import contacts from google"
            + "process\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD;

    private List<Person> googleContactsList;

    /**
     * Constructor for ImportCommand (Gets the Google Contact List after successful authentication)
     */
    public ImportCommand() throws GoogleAuthException {
        try {
            GoogleContactsBuilder builder = new GoogleContactsBuilder();
            this.googleContactsList = builder.getPersonlist();
        } catch (IOException e) {
            throw new GoogleAuthException("Authentication Failed. Please login again.");
        }
    }

    /**
     * This constructor exists only for the sake of testing so as to pass through a dummy google contacts list.
     * Will not be used in the main implementation of the programme
     */
    public ImportCommand(List<Person> connections) {
        this.googleContactsList = connections;
    }



    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        String commandMessage;
        String namesNotImported = "";
        int contactsImportedCount = 0;
        int errorImportsCount = 0;

        List<ReadOnlyPerson> addressBookList = model.getAddressBook().getPersonList();

        if (this.googleContactsList == null) {
            throw new CommandException("No contacts found in Google Contacts");
        }

        for (Person googlePerson : googleContactsList) {
            if (!this.ifContactExists(addressBookList, googlePerson)) {
                try {
                    model.addPerson(this.newPerson(googlePerson));
                    contactsImportedCount++;
                } catch (IllegalValueException | NullPointerException e) {
                    namesNotImported += " " + googlePerson.getNames().get(0).getGivenName() + ", ";
                    errorImportsCount++;
                }
            }
        }

        commandMessage = setCommandMessage(namesNotImported, contactsImportedCount, errorImportsCount,
                googleContactsList.size(), namesNotImported);
        return new CommandResult(commandMessage);
    }

    /**
     * Check if a particular google contact already exists in the addressbook
     * Returns true if already exists. Otherwise, false.
     */
    public boolean ifContactExists(List<ReadOnlyPerson> list, Person person) {
        for (ReadOnlyPerson readOnlyPerson : list) {
            if (person.getResourceName().substring(8).equals(readOnlyPerson.getGoogleId().value)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Creates a person in addressBook based on the contact in google contact
     */
    public ReadOnlyPerson newPerson(Person person) throws IllegalValueException, NullPointerException {
        Name name = new Name(person.getNames().get(0).getGivenName());
        Email email = new Email(person.getEmailAddresses().get(0).getValue());
        Phone phone = new Phone(person.getPhoneNumbers().get(0).getValue().replace(" ", ""));
        Address address = new Address(person.getAddresses().get(0).getStreetAddress());
        GoogleId id = new GoogleId(person.getResourceName().substring(8));
        Birthday birthday = new Birthday("");
        FacebookAddress facebookAddress = new FacebookAddress("");

        Tag tag = new Tag("GoogleContact");
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);

        return new seedu.address.model.person.Person(name, phone, email, address, birthday, facebookAddress, tags, id);

    }
    /**
     * Creates a detailed message on the status of the import
     */
    public String setCommandMessage(String notImported, int contactsImported, int errorImports, int size,
                                    String namesNotImported) {
        int existedContacts = size - contactsImported - errorImports;
        String commandMessage;
        commandMessage = String.format(Messages.MESSAGE_IMPORT_CONTACT, contactsImported,
                size - contactsImported) + "\n";

        if (size > contactsImported) {
            commandMessage += String.format(Messages.MESSAGE_IMPORT_STATUS, existedContacts, errorImports) + "\n";
        }

        if (errorImports > 0) {
            commandMessage += "Please check the format of the following google contacts : "
                    + notImported.substring(0, namesNotImported.length() - 2);
        }
        return commandMessage;
    }
}

```
###### \main\java\seedu\address\logic\commands\LoginCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.commons.GoogleAuthenticator;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.LoadLoginEvent;

/**
 * For loading the authentication/login page
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";
    public static final String COMMAND_ALIAS = "lg";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Directs the user to google's login page for the authentication"
            + "process\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD;

    private final GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();

    @Override
    public CommandResult execute() {
        String authenticationUrl = googleAuthenticator.getAuthorizationUrl();
        EventsCenter.getInstance().post(new LoadLoginEvent(authenticationUrl));

        return new CommandResult(Messages.LOGIN_MESSAGE);
    }
}
```
###### \main\java\seedu\address\logic\commands\SyncCommand.java
``` java
package seedu.address.logic.commands;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.api.services.people.v1.model.Person;

import javafx.collections.ObservableList;
import seedu.address.commons.GoogleContactsBuilder;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.GoogleAuthException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.FacebookAddress;
import seedu.address.model.person.GoogleId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Syncs all existing google contacts in the addressbook with the contacts in google contacts
 * Contacts in google contacts takes higher precedence.
 */
public class SyncCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sync";
    public static final String COMMAND_ALIAS = "sync";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sync contacts from google"
            + "process\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD;
    private String commandMessage = "";
    private String namesNotSynced = "";
    private int contactsSyncedCount = 0;
    private int errorSyncedCount = 0;

    private List<Person> googleContactsList;

    /**
     * Constructor for SyncCommand (Gets the Google Contact List after successful authentication)
     */
    public SyncCommand() throws GoogleAuthException {
        try {
            GoogleContactsBuilder builder = new GoogleContactsBuilder();
            this.googleContactsList = builder.getPersonlist();
        } catch (IOException e) {
            throw new GoogleAuthException("Authentication Failed. Please login again.");
        }
    }

    /**
     * This constructor exists only for the sake of testing so as to pass through a dummy google contacts list.
     * Will not be used in the main implementation of the programme
     */
    public SyncCommand(List<Person> connections) {
        this.googleContactsList = connections;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        boolean exists = false;

        ObservableList<ReadOnlyPerson> personlist = model.getAddressBook().getPersonList();
        if (personlist.isEmpty()) {
            throw new CommandException("No contacts in addressbook to sync");
        }


        for (ReadOnlyPerson addressPerson : personlist) {
            if ((googleContactsList != null)) {
                for (Person googlePerson : googleContactsList) {
                    exists = updateAddressBook(googlePerson, addressPerson);
                    if (exists == true) {
                        break;
                    }
                }
            }

            //Removes google contact status from addressbook Contact if it does not exists in google contacts
            if ((addressPerson.getGoogleId().value != "not GoogleContact") && (!exists)) {
                try {
                    model.updatePerson(addressPerson, removeGoogleContactStatus(addressPerson));
                    contactsSyncedCount++;
                } catch (IllegalValueException | NullPointerException | PersonNotFoundException e) {
                    errorSyncedCount++;
                }
            }
        }

        commandMessage = setCommandMessage(namesNotSynced, contactsSyncedCount, errorSyncedCount);

        return new CommandResult(commandMessage);
    }

    /**
     * First, the method runs a check to see if the addressbook contact exists in google contacts
     * Updates the contact in addressbook with the new contact in google contact if they are found to be different
     * Returns true if contact cant be found in google contacts
     */
    public boolean updateAddressBook(Person googlePerson, ReadOnlyPerson addressPerson) {
        if (googlePerson.getResourceName().substring(8).equals(addressPerson.getGoogleId().value)) {
            try {
                if (!addressPerson.isSameStateAs(convertToAddress(googlePerson, addressPerson))) {
                    model.updatePerson(addressPerson, convertToAddress(googlePerson, addressPerson));
                    contactsSyncedCount++;
                }
            } catch (IllegalValueException | NullPointerException | PersonNotFoundException e) {
                errorSyncedCount++;
                namesNotSynced += googlePerson.getNames().get(0).getGivenName() + ", ";
            }
            return true;
        }
        return false;
    }

    /**
     * Creates a person in addressBook based on the contact in google contact
     */
    public ReadOnlyPerson convertToAddress(Person person, ReadOnlyPerson addressPerson)
            throws IllegalValueException, NullPointerException {

        Name name = new Name(person.getNames().get(0).getGivenName());
        Phone phone = new Phone(person.getPhoneNumbers().get(0).getValue().replace(" ", ""));
        Email email = new Email(person.getEmailAddresses().get(0).getValue());
        Address address = new Address(person.getAddresses().get(0).getStreetAddress());
        GoogleId id = new GoogleId(person.getResourceName().substring(8));
        Birthday birthday = new Birthday("");
        FacebookAddress facebookAddress = new FacebookAddress("");

        Set<Tag> tags = addressPerson.getTags();

        return new seedu.address.model.person.Person(name, phone, email, address, birthday, facebookAddress, tags, id);
    }

    /**
     * Creates a new person without its previous google contact status
     * This method is used if a person has a google contact status but cannot be found in google contacts
     */
    public ReadOnlyPerson removeGoogleContactStatus(ReadOnlyPerson contact)
            throws IllegalValueException, NullPointerException {

        Name name = new Name(contact.getName().fullName);
        Phone phone = new Phone(contact.getPhone().value);
        Email email = new Email(contact.getEmail().value);
        Address address = new Address(contact.getAddress().value);
        GoogleId id = new GoogleId("not GoogleContact");
        Set<Tag> tags = new HashSet<>();
        for (Tag existingTags : contact.getTags()) {
            if (!existingTags.getTagName().equals("GoogleContact")) {
                tags.add(existingTags);
            }
        }

        return new seedu.address.model.person.Person(name, phone, email, address,
                contact.getBirthday(), contact.getFacebookAddress(), tags, id);
    }


    /**
     * Creates a detailed message on the status of the sync
     */
    public String setCommandMessage(String namesNotSynced, int contactsSyncedCount, int errorSyncedCount) {
        String commandMessage;

        commandMessage = String.format(Messages.MESSAGE_SYNC_CONTACT, contactsSyncedCount, errorSyncedCount);
        if (errorSyncedCount > 0) {
            commandMessage += "\n" + "Please check the format of the following google contacts : "
                    + namesNotSynced.substring(0, namesNotSynced.length() - 2);
        }
        return commandMessage;
    }
}
```
###### \main\java\seedu\address\logic\parser\FindLettersCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindLettersCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsAlphabetsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindLettersCommandParser implements Parser<FindLettersCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindLettersCommand
     * and returns an FindLettersCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindLettersCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindLettersCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindLettersCommand(new NameContainsAlphabetsPredicate(Arrays.asList(nameKeywords)));
    }

}
```
###### \main\java\seedu\address\model\person\GoogleId.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's Google ID in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGoogleId(String)}
 */
public class GoogleId {


    public static final String MESSAGE_GOOGLEID_CONSTRAINTS =
            "GoogleID can only contain numbers, and should be at least 3 digits long";
    public static final String GOOGLEID_VALIDATION_REGEX = "[^\\s].*";
    public final String value;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public GoogleId(String id) throws IllegalValueException {
        requireNonNull(id);
        String trimmedId = id.trim();
        if (!isValidGoogleId(trimmedId)) {
            throw new IllegalValueException(MESSAGE_GOOGLEID_CONSTRAINTS);
        }
        this.value = trimmedId;
    }

    /**
     * Returns true if a given string is a valid person Google ID.
     */
    public static boolean isValidGoogleId(String test) {
        return test.matches(GOOGLEID_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GoogleId // instanceof handles nulls
                && this.value.equals(((GoogleId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \main\java\seedu\address\model\person\NameContainsAlphabetsPredicate.java
``` java
package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsAlphabetsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public NameContainsAlphabetsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsAlphabetIgnoreCase(person.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsAlphabetsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsAlphabetsPredicate) other).keywords)); // state check
    }

}
```
###### \main\java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void loadLoginUrl(LoadLoginEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPage(event.getAuthenticationUrl());
    }

    @Subscribe
    private void getRedirectUrlEvent (GetRedirectUrlEvent event) {
        logger.info((LogsCenter.getEventHandlingLogMessage(event)));
        event.setRedirectUrl(browser.getEngine().getLocation());
    }
}
```
###### \main\java\seedu\address\ui\CommandBox.java
``` java
    /**
     * Handles the key release event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyRelease() {
        String commandText = commandTextField.getText();
        int textLength = commandTextField.getLength();
        //Starts the generation search results with each character typed if command find is entered
        if ((textLength > 5) && (commandText.substring(0, 5).equals("find "))) {
            try {
                CommandResult commandResult = logic.execute(findWordCommand + commandText.substring(5));
                raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
            } catch (CommandException | ParseException | GoogleAuthException e) {
                logger.info("No smart searches");
            }
        }

        if ((textLength == 5) && (commandText.substring(0, 5).equals("find "))) {
            try {
                raise(new NewResultAvailableEvent(""));
                logic.execute("list");
            } catch (CommandException | ParseException | GoogleAuthException e) {
                logger.info("No List from smart search");
            }
        }
    }

```
###### \test\java\seedu\address\commons\GoogleAuthenticatorTest.java
``` java
package seedu.address.commons;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.exceptions.GoogleAuthException;

public class GoogleAuthenticatorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private GoogleAuthenticator authenticator = new GoogleAuthenticator();

    /**
     * Checks if Login url generated is in the valid format
     */
    @Test
    public void execute_obtain_loginUrl() {
        assertTrue(authenticator.getAuthorizationUrl().contains(
                "https://accounts.google.com/o/oauth2/auth?client_id"));
    }

    /**
     * Checks if user is authenticated. In this case no token is generated and null exception is thrown.
     */
    @Test
    public void execute_get_invalidToken() throws Exception {
        thrown.expect(GoogleAuthException.class);
        authenticator.getToken();
    }

    /**
     * Checks if user is authenticated. In this case no google contacts list is generated and null exception is thrown.
     */
    @Test
    public void execute_get_invalidGooglePersonList() throws Exception {
        thrown.expect(NullPointerException.class);
        authenticator.getConnections(null);
    }
}
```
###### \test\java\seedu\address\logic\commands\ExportCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.BERNICE;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.api.services.people.v1.model.Person;

import seedu.address.commons.GoogleContactsBuilder;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.GoogleAuthException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalGoogleContactsList;

public class ExportCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private List<Person> googleList;

    @Before
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
        googleList = new ArrayList<>();
    }

    /**
     * Checks if Login is authenticated. In this case it is not and GoogleAuthException is thrown.
     */
    @Test
    public void execute_require_login() throws Exception {
        thrown.expect(GoogleAuthException.class);
        new ExportCommand();
    }

    /**
     * Test for exporting an empty addressbook. Throws Command exception for empty addressbook
     */
    @Test
    public void execute_commandFailure_noContacts() throws Exception {
        thrown.expect(CommandException.class);
        ExportCommand command = prepareCommand(this.model);
        command.execute();
    }

    /**
     * Test for exporting an empty addressbook when authentication is lost.
     */
    @Test
    public void execute_commandFailure_noAuthenticationToExport() throws Exception {
        thrown.expect(GoogleAuthException.class);
        model.addPerson(BERNICE);
        ExportCommand command = prepareCommand(this.model);
        command.execute();
    }


    /**
     * Test for no importing of contacts to addressbook as all contacts in addressbook are already in google contacts.
     */
    @Test
    public void execute_commandFailure_allGoogleContacts() throws Exception {
        model.addPerson(TypicalGoogleContactsList.FREEDYADDRESSBOOK);

        ExportCommand command = prepareCommand(this.model);
        String expectedMessage = "0 contact/s exported!     All contacts can be now found in google contact";
        assertCommandFailure(command, expectedMessage, model);
    }

    /**
     * Test for converting a addressbook contact into a google contact
     */
    @Test
    public void execute_updateAddressBookContactToGoogle() throws Exception {
        model.addPerson(BERNICE);

        ExportCommand command = prepareCommand(this.model);
        Person aliceGoogleContact = command.createGoogleContact(BERNICE);
        assertEquals(aliceGoogleContact, TypicalGoogleContactsList.BERNICE);
    }
    /**
     * Test for changes in contact after being exported to google
     */
    @Test
    public void execute_exportContactChanges() throws Exception {
        ExportCommand command = prepareCommand(this.model);
        ReadOnlyPerson editedAlice =
                command.getNewAddressBookContact(BERNICE, TypicalGoogleContactsList.BERNICEWITHGOOGLEID);
        assertEquals(editedAlice, TypicalGoogleContactsList.BERNICEADDRESSBOOK);
    }

    /**
     * Test detailed messages for export command.
     */
    @Test
    public void execute_exportDetailedMessages() throws Exception {
        ExportCommand command = prepareCommand(this.model);
        String messageSuccess = command.setCommandMessage(1, 0);
        String messageFailure = command.setCommandMessage(0, 1);
        assertEquals(messageSuccess, "1 contact/s exported!     "
                + "All contacts can be now found in google contact");
        assertEquals(messageFailure, "0 contact/s exported!     1 contact/s failed to export!");
    }

    /**
     * Preparing import command by using a test-only constructor (Using a stub google person list)
     */
    private ExportCommand prepareCommand(Model model) throws CommandException {
        GoogleContactsBuilder builder = null;
        ExportCommand command = new ExportCommand(builder);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts if a command unsuccessfully executed
     */
    private void assertCommandFailure(Command command, String expectedMessage, Model modelstub)
            throws CommandException, GoogleAuthException {
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(modelstub.getAddressBook(), model.getAddressBook());
    }
}
```
###### \test\java\seedu\address\logic\commands\FindLettersCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_ALPHABET_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_NO_ALPHABET_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsAlphabetsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) for {@code FindLettersCommand}.
 */
public class FindLettersCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsAlphabetsPredicate firstPredicate =
                new NameContainsAlphabetsPredicate(Collections.singletonList("first"));
        NameContainsAlphabetsPredicate secondPredicate =
                new NameContainsAlphabetsPredicate(Collections.singletonList("second"));

        FindLettersCommand findFirstCommand = new FindLettersCommand(firstPredicate);
        FindLettersCommand findSecondCommand = new FindLettersCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindLettersCommand findFirstCommandCopy = new FindLettersCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }
    //Test when no input by user
    @Test
    public void executeZeroAlphabetsNoPersonFound() {
        String expectedMessage = String.format(MESSAGE_NO_ALPHABET_LISTED_OVERVIEW);
        FindLettersCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }
    //Test when user inputs partial names
    @Test
    public void executeMultipleAlphabetsMultiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_ALPHABET_LISTED_OVERVIEW, 2);
        FindLettersCommand command = prepareCommand("Ku");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, FIONA));
    }
    //Test when user inputs full names
    @Test
    public void executeMultipleKeywordsMultiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_ALPHABET_LISTED_OVERVIEW, 3);
        FindLettersCommand command = prepareCommand("Kurz Elle Kunz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindLettersCommand prepareCommand(String userInput) {
        FindLettersCommand command =
                new FindLettersCommand(new NameContainsAlphabetsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     */
    private void assertCommandSuccess(FindLettersCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());

    }
}
```
###### \test\java\seedu\address\logic\commands\ImportCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.api.services.people.v1.model.Person;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.GoogleAuthException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalGoogleContactsList;

public class ImportCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private List<Person> googleList;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        googleList = new ArrayList<>();
    }

    /**
     * Checks if Login is authenticated. In this case it is not as user is not authenticated
     * and GoogleAuthException is thrown.
     */
    @Test
    public void execute_require_login() throws Exception {
        thrown.expect(GoogleAuthException.class);
        new ImportCommand();
    }
    /**
     * Checks if Google contacts is empty. In this case it is empty and null exception is thrown.
     */
    @Test
    public void execute_empty_googleContacts() throws Exception {
        thrown.expect(NullPointerException.class);
        new ImportCommand(googleList).executeUndoableCommand();
    }

    /**
     * Test for normal importing of a google contact
     */
    @Test
    public void execute_commandSuccess_imported() throws Exception {
        googleList.add(TypicalGoogleContactsList.FREDDYGOOGLE);
        ImportCommand command = prepareCommand(googleList, this.model);

        Model modelstub = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelstub.addPerson(TypicalGoogleContactsList.FREEDYADDRESSBOOK);
        String expectedMessage = String.format(Messages.MESSAGE_IMPORT_CONTACT, 1, 0) + "\n";

        assertCommandSuccess(command, expectedMessage, modelstub);
    }

    /**
     * Test for invalid importing of a google contact due to invalid attributes
     */
    @Test
    public void execute_commandFailure_contactInvalidFormat() throws Exception {
        googleList.add(TypicalGoogleContactsList.MAYGOOGLE);
        ImportCommand command = prepareCommand(googleList, this.model);

        String expectedMessage = String.format(Messages.MESSAGE_IMPORT_CONTACT, 0, 1) + "\n"
                + String.format(Messages.MESSAGE_IMPORT_STATUS, 0, 1) + "\n"
                + "Please check the format of the following google contacts :  May";

        assertCommandFailure(command, expectedMessage, this.model);
    }

    /**
     * Test for importing a contact that already exists in the addressbook
     */
    @Test
    public void execute_commandFailure_contactExists() throws Exception {
        model.addPerson(TypicalGoogleContactsList.FREEDYADDRESSBOOK);
        googleList.add(TypicalGoogleContactsList.FREDDYGOOGLE);
        ImportCommand command = prepareCommand(googleList, model);

        String expectedMessage = String.format(Messages.MESSAGE_IMPORT_CONTACT, 0, 1) + "\n"
                + String.format(Messages.MESSAGE_IMPORT_STATUS, 1, 0) + "\n";

        assertCommandFailure(command, expectedMessage, model);
    }

    /**
     * Preparing import command by using a test-only constructor (Using a stub google person list)
     */
    private ImportCommand prepareCommand(List<Person> personList, Model model) throws CommandException {
        ImportCommand command = new ImportCommand(personList);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts if a command successfully executed
     */
    private void assertCommandSuccess(Command command, String expectedMessage, Model modelstub)
            throws CommandException, GoogleAuthException {
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(modelstub.getAddressBook(), model.getAddressBook());
    }

    /**
     * Asserts if a command unsuccessfully executed
     */
    private void assertCommandFailure(Command command, String expectedMessage, Model modelstub)
            throws CommandException, GoogleAuthException {
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(modelstub.getAddressBook(), model.getAddressBook());
    }
}
```
###### \test\java\seedu\address\logic\commands\SyncCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.api.services.people.v1.model.Person;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.GoogleAuthException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalGoogleContactsList;

```
###### \test\java\seedu\address\logic\commands\SyncCommandTest.java
``` java
public class SyncCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private List<Person> googleList;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        googleList = new ArrayList<>();
    }

    /**
     * Checks if Login is authenticated. In this case it is not.
     */
    @Test
    public void execute_require_login() throws Exception {
        thrown.expect(GoogleAuthException.class);
        new SyncCommand();
    }

    /**
     * Test for normal Syncing of a single google contact
     */
    @Test
    public void execute_syncSuccess() throws Exception {
        model.addPerson(TypicalGoogleContactsList.FREEDYADDRESSBOOK);
        googleList.add(TypicalGoogleContactsList.FREDDYSYNGOOGLE);
        SyncCommand command = prepareCommand(googleList, model);

        Model modelStub = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelStub.addPerson(TypicalGoogleContactsList.FREDDYSYNADDRESSBOOK);
        String expectedMessage = String.format(Messages.MESSAGE_SYNC_CONTACT, 1, 0);

        assertCommandSuccess(command, expectedMessage, modelStub);
    }

    /**
     * Test for invalid Syncing of a google contact due to invalid attributes
     */
    @Test
    public void execute_syncFailure_contactInvalidFormat() throws Exception {

        model.addPerson(TypicalGoogleContactsList.MAYADDRESSBOOK);
        googleList.add(TypicalGoogleContactsList.MAYGOOGLE);
        SyncCommand command = prepareCommand(googleList, model);

        String expectedMessage = String.format(Messages.MESSAGE_SYNC_CONTACT, 0, 1) + "\n"
                + "Please check the format of the following google contacts : May";

        assertCommandFailure(command, expectedMessage, model);
    }

    /**
     * Test for syncing a contact that is of no difference than the one in the addressbook
     */
    @Test
    public void execute_commandFailure_contactSimilar() throws Exception {
        model.addPerson(TypicalGoogleContactsList.FREEDYADDRESSBOOK);
        googleList.add(TypicalGoogleContactsList.FREDDYGOOGLE);
        SyncCommand command = prepareCommand(googleList, model);

        String expectedMessage = String.format(Messages.MESSAGE_SYNC_CONTACT, 0, 0);
        assertCommandFailure(command, expectedMessage, model);
    }

    /**
     * Test for syncing a contact that is no longer in the addressbook. The contact will lose its google contacts
     * status
     */
    @Test
    public void execute_commandFailure_contactNoLongerExists() throws Exception {
        model.addPerson(TypicalGoogleContactsList.FREEDYADDRESSBOOK);
        SyncCommand command = prepareCommand(googleList, model);

        Model modelStub = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelStub.addPerson(TypicalGoogleContactsList.FREEDYNOTGOOGLEADDRESSBOOK);
        String expectedMessage = String.format(Messages.MESSAGE_SYNC_CONTACT, 1, 0);

        assertCommandFailure(command, expectedMessage, modelStub);
    }

    /**
     * Preparing import command by using a test-only constructor (Using a stub google person list)
     */
    private SyncCommand prepareCommand(List<Person> personList, Model model) throws CommandException {
        SyncCommand command = new SyncCommand(personList);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts if a command successfully executed
     */
    private void assertCommandSuccess(Command command, String expectedMessage, Model modelstub)
            throws CommandException, GoogleAuthException {
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(modelstub.getAddressBook(), model.getAddressBook());
    }

    /**
     * Asserts if a command unsuccessfully executed
     */
    private void assertCommandFailure(Command command, String expectedMessage, Model modelstub)
            throws CommandException, GoogleAuthException {
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(modelstub.getAddressBook(), model.getAddressBook());
    }
}
```
###### \test\java\seedu\address\logic\parser\FindLettersCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindLettersCommand;
import seedu.address.model.person.NameContainsAlphabetsPredicate;

public class FindLettersCommandParserTest {

    private FindLettersCommandParser parser = new FindLettersCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindLettersCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindLettersCommand expectedFindCommand = new FindLettersCommand(new
                NameContainsAlphabetsPredicate(Arrays.asList("Fiona", "Amelia")));
        assertParseSuccess(parser, "Fiona Amelia", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Fiona \n \t Amelia  \t", expectedFindCommand);
    }

}
```
###### \test\java\seedu\address\model\person\GoogleIdTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GoogleIdTest {

    @Test
    public void isValidId() {
        // invalid GoogleId
        assertFalse(GoogleId.isValidGoogleId("")); // empty string
        assertFalse(GoogleId.isValidGoogleId(" ")); // spaces only


        // valid GoogleId
        assertTrue(GoogleId.isValidGoogleId("1234567890")); // numbers
        assertTrue(GoogleId.isValidGoogleId("not GoogleContact")); // Letters
        assertTrue(GoogleId.isValidGoogleId("f2134324")); // alphanumeric

    }
}
```
###### \test\java\seedu\address\model\person\NameContainsAlphabetsPredicateTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsAlphabetsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateAlphabetList = Collections.singletonList("first");
        List<String> secondPredicateAlphabetList = Arrays.asList("first", "second");

        NameContainsAlphabetsPredicate firstPredicate = new NameContainsAlphabetsPredicate(firstPredicateAlphabetList);
        NameContainsAlphabetsPredicate secondPredicate =
                new NameContainsAlphabetsPredicate(secondPredicateAlphabetList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsAlphabetsPredicate firstPredicateCopy =
                new NameContainsAlphabetsPredicate(firstPredicateAlphabetList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testNameContainsAlphabetsReturnsTrue() {
        // One Alphabet
        NameContainsAlphabetsPredicate predicate = new NameContainsAlphabetsPredicate(Collections.singletonList("A"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Multiple Separated Alphabets
        predicate = new NameContainsAlphabetsPredicate(Arrays.asList("A", "B"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only One Matching Alphabet Sequence
        predicate = new NameContainsAlphabetsPredicate(Arrays.asList("Bob", "Car"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-Case Alphabets
        predicate = new NameContainsAlphabetsPredicate(Arrays.asList("bO", "aLIC"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Full Keywords
        predicate = new NameContainsAlphabetsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void testNameDoesNotContainAlphabetsReturnsFalse() {
        // Zero Keywords
        NameContainsAlphabetsPredicate predicate = new NameContainsAlphabetsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching Alphabets in Sequence
        predicate = new NameContainsAlphabetsPredicate(Arrays.asList("Dob"));
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new NameContainsAlphabetsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
```
###### \test\java\seedu\address\testutil\GoogleContactBuilder.java
``` java
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.api.services.people.v1.model.Address;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.PhoneNumber;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.person.FacebookAddress;
import seedu.address.model.person.GoogleId;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Builds Stub Google contact and addressbook contacts
 */
public class GoogleContactBuilder {
    private String nameDefault;
    private String phoneDefault;
    private String emailDefault;
    private String addressDefault;
    private String googleIdDefault;

    /**
     * Constructor
     */
    public GoogleContactBuilder(String name, String phone, String email, String address, String googleId) {
        this.nameDefault = name;
        this.phoneDefault = phone;
        this.emailDefault = email;
        this.addressDefault = address;
        this.googleIdDefault = googleId;
    }

    /**
     * Builds Stub Google contact
     */
    public Person buildGooglePerson() {
        Person contactToCreate = new Person();

        List names = new ArrayList();
        List email = new ArrayList();
        List phone = new ArrayList();
        List address = new ArrayList();

        names.add(new Name().setGivenName(nameDefault));
        email.add(new EmailAddress().setValue(emailDefault));
        phone.add(new PhoneNumber().setValue(phoneDefault));
        address.add(new Address().setStreetAddress(addressDefault));

        return contactToCreate.setNames(names).setEmailAddresses(email).setPhoneNumbers(phone)
                .setAddresses(address).setResourceName(googleIdDefault);
    }
    /**
     * Builds Stub Addressbook contact
     */
    public seedu.address.model.person.Person buildAddressBookPerson() {
        seedu.address.model.person.Person person;
        try {
            seedu.address.model.person.Name name = new seedu.address.model.person.Name(nameDefault);
            Phone phone = new Phone(phoneDefault);
            Email email = new Email(emailDefault);
            seedu.address.model.person.Address address = new seedu.address.model.person.Address(addressDefault);
            GoogleId id;
            if (googleIdDefault.length() != 0) {
                id = new GoogleId(googleIdDefault.substring(8));
            } else {
                id = new GoogleId("not GoogleContact");
            }

            Tag tag = new Tag("GoogleContact");
            Set<Tag> tags = new HashSet<>();
            tags.add(tag);
            FacebookAddress facebookAddress = new FacebookAddress("");
            seedu.address.model.person.Birthday birthday = new seedu.address.model.person.Birthday("");
            person = new seedu.address.model.person.Person(name, phone, email, address, birthday, facebookAddress,
                    tags, id);
        } catch (IllegalValueException e) {
            person = null;
        }
        return person;
    }
}
```
###### \test\java\seedu\address\testutil\TypicalGoogleContactsList.java
``` java
package seedu.address.testutil;

import com.google.api.services.people.v1.model.Person;

/**
 * List of Stub Google contacts and its version of the addressbook contact
 */
public class TypicalGoogleContactsList {
    public static final Person FREDDYGOOGLE = new GoogleContactBuilder("Freddy", "91234567",
            "freddy@hotmail.com", "Simei Blk 1 avenue 2",
            "1234567891011121310").buildGooglePerson();
    public static final Person MAYGOOGLE = new GoogleContactBuilder("May", "91234567",
            "may", "Simei Blk 15 avenue 21",
            "1234567891011121311").buildGooglePerson();
    public static final Person FREDDYSYNGOOGLE = new GoogleContactBuilder("Freddy", "90000000",
            "freddy@hotmail.com", "Simei Blk 1 avenue 2",
            "1234567891011121310").buildGooglePerson();

    public static final Person BERNICE = new GoogleContactBuilder("Alice Pauline",
            "85355255", "alice@example.com",
            "123, Jurong West Ave 6, #08-111", null).buildGooglePerson();

    public static final Person BERNICEWITHGOOGLEID = new GoogleContactBuilder("Alice Pauline",
            "85355255", "alice@example.com",
            "123, Jurong West Ave 6, #08-111", "12345678910111213112").buildGooglePerson();

    public static final seedu.address.model.person.Person FREEDYADDRESSBOOK = new GoogleContactBuilder("Freddy",
            "91234567", "freddy@hotmail.com", "Simei Blk 1 avenue 2",
            "1234567891011121310").buildAddressBookPerson();
    public static final seedu.address.model.person.Person MAYADDRESSBOOK = new GoogleContactBuilder("May",
            "91234567",
            "may@hotmail.com", "Simei Blk 15 avenue 21",
            "1234567891011121311").buildAddressBookPerson();
    public static final seedu.address.model.person.Person FREDDYSYNADDRESSBOOK = new GoogleContactBuilder(
            "Freddy", "90000000", "freddy@hotmail.com", "Simei Blk 1 avenue 2",
            "1234567891011121310").buildAddressBookPerson();
    public static final seedu.address.model.person.Person FREEDYNOTGOOGLEADDRESSBOOK = new GoogleContactBuilder(
            "Freddy", "91234567", "freddy@hotmail.com", "Simei Blk 1 avenue 2",
            "").buildAddressBookPerson();
    public static final seedu.address.model.person.Person BERNICEADDRESSBOOK = new GoogleContactBuilder(
            "Alice Pauline", "85355255", "alice@example.com",
            "123, Jurong West Ave 6, #08-111", "12345678910111213112").buildAddressBookPerson();

}
```
