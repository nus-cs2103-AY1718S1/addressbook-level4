# philemontan
###### \java\seedu\address\commons\events\logic\GoogleAuthenticationSuccessEvent.java
``` java
/**
 * An event fired indicating a successful GoogleCommand authentication
 * Attributes String commandType & String authCode are used to store information on the GoogleCommand subclass
 * firing this event, and the authentication token extracted from the URL, respectively
 */
public class GoogleAuthenticationSuccessEvent extends BaseEvent {
    private String commandType;
    private String authCode;

    public GoogleAuthenticationSuccessEvent(String inputCommandType, String inputAuthCode) {
        commandType = inputCommandType;
        authCode = inputAuthCode;
    }

    public String getCommandType() {
        return commandType;
    }
    public String getAuthCode () {
        return authCode;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\logic\GoogleCommandCompleteEvent.java
``` java
/**
 * An event fired indicating a GoogleCommand instance has successfully completed. Attributes redirectUrl and
 * commandType are provided by the firer, for identifying the the GoogleCommand subclass of the instance.
 */
public class GoogleCommandCompleteEvent extends BaseEvent {
    private String redirectUrl;
    private String commandType;

    public GoogleCommandCompleteEvent(String inputUrl, String inputCommandType) {
        redirectUrl = inputUrl;
        commandType = inputCommandType;
        EventsCenter.getInstance().post(new NewResultAvailableEvent(commandType
                + " has successfully completed", false));
    }
    public String getRedirectUrl() {
        return redirectUrl;
    }
    public String getCommandType() {
        return commandType;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\Oauth2BrowserRequestEvent.java
``` java
/**
 * This event is used by all Oauth2Command implementations to trigger the BrowserPanel authentication process
 */
public class Oauth2BrowserRequestEvent extends BaseEvent {
    public static final String INVALID_INPUT_MESSAGE = "This event must be created with a COMMAND_TYPE";
    private final String commandType;
    private final String requestUrl;

    public Oauth2BrowserRequestEvent(String inputType, String inputRequestUrl) throws IOException {
        if (inputType == null) {
            throw new IOException(INVALID_INPUT_MESSAGE);
        }
        this.requestUrl = inputRequestUrl;
        this.commandType = inputType;
    }

    public Oauth2BrowserRequestEvent() throws IOException {
        throw new IOException(INVALID_INPUT_MESSAGE);
    }

    public String getCommandType() {
        return this.commandType;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\exceptions\InvalidGooglePersonException.java
``` java
/**
 * This exception is thrown to indicate an invalid Google Person object in the process of being converted by the
 * GooglePersonConverterUtil
 */
public class InvalidGooglePersonException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public InvalidGooglePersonException(String message) {
        super(message);
    }

}
```
###### \java\seedu\address\commons\util\GooglePersonConverterUtil.java
``` java
/**This class provides the service of two-way conversion between Google Person(s) and Doc Person(s)
 * Both classes and their composed classes in each package have the same name;
 * Handling: seedu.address.model.person.Person will be imported, while Google classes have to be fully qualified.
 * Created by Philemon1 on 12/10/2017.
 */
public abstract class GooglePersonConverterUtil {
    public static final String DEFAULT_TAGS = "ImportedFromGoogle";
    public static final String DEFAULT_EMAIL = "INVALID_EMAIL@INVALID.COM";
    public static final String DEFAULT_ADDRESS = "INVALID_ADDRESS PLEASE UPDATE THIS";


    /**
     * Conversion: (Single) Google Person -> DoC Person
     * @param person input parameter of a single Google Person
     * @return the converted DoC version of the Google Person
     * @throws InvalidGooglePersonException if the Google Person instance has a null name or phone
     */
    public static Person singleGoogleToDocPersonConversion(com.google.api.services.people.v1.model.Person person) throws
            InvalidGooglePersonException {
        //Property declarations for the DoC Person
        String tempName = null;
        String tempPhoneNumber = null;
        String tempEmailAddress = null;
        String tempAddress = null;
        seedu.address.model.person.Person tempPerson;

        //Try block to extract required properties
        try {
            tempName = person.getNames().get(0).getDisplayName();
            tempPhoneNumber = person.getPhoneNumbers().get(0).getValue();
            tempEmailAddress = person.getEmailAddresses().get(0).getValue();
            tempAddress = person.getAddresses().get(0).getFormattedValue();
        } catch (IndexOutOfBoundsException | NullPointerException E) {
            if (tempName == null | tempPhoneNumber == null) {
                throw new InvalidGooglePersonException("Name and Phone number cannot be null");
            }
        }

        //Process Name and Number in accordance to DoC Name and Number regex, throws an exception if any input is null
        tempName = processName(tempName);
        tempPhoneNumber = processNumber(tempPhoneNumber);

        //Setting optional properties to default constants if null
        if (tempEmailAddress == null) {
            tempEmailAddress = DEFAULT_EMAIL;
        }
        if (tempAddress == null) {
            tempAddress = DEFAULT_ADDRESS;
        }

        //Instantiating DoC person to return
        try {
            Name nameObj = new Name(tempName);
            Email emailAddressObj = new Email(tempEmailAddress);
            Phone phoneObj = new Phone(tempPhoneNumber);
            Address addressObj = new Address(tempAddress);
            tempPerson = new seedu.address.model.person.Person(nameObj,
                    phoneObj, emailAddressObj, addressObj, SampleDataUtil.getTagSet(DEFAULT_TAGS));
            return tempPerson;
        } catch (IllegalValueException e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * Conversion: (Single) DoC Person -> Google Person
     * Note: Google Person Photo is read only and cannot be set from the Google People API
     * @param person input parameter of a single Google Person
     * @return the converted Google version of the input DoC Person
     */
    public static com.google.api.services.people.v1.model.Person singleDocToGooglePersonConversion(
            ReadOnlyPerson person) {
        /**
         * Creating Google Person properties, excluding tags
         */
        //Google Name
        com.google.api.services.people.v1.model.Name googleName = new com.google.api.services.people.v1.model.Name()
                .setGivenName(person.getName().fullName).setDisplayName(person.getName().fullName);
        //Google Phone Number
        com.google.api.services.people.v1.model.PhoneNumber googleNumber =
                new com.google.api.services.people.v1.model.PhoneNumber().setValue(person.getPhone().value);
        //Google Email Address
        com.google.api.services.people.v1.model.EmailAddress googleEmail =
                new com.google.api.services.people.v1.model.EmailAddress()
                .setValue(person.getEmail().value);
        //Google Address
        com.google.api.services.people.v1.model.Address googleAddress =
                new com.google.api.services.people.v1.model.Address().setFormattedValue(person.getAddress().value);
        //Google Person
        com.google.api.services.people.v1.model.Person tempPerson =
                new com.google.api.services.people.v1.model.Person();


        /**
         * Creating Lists from single properties, to fulfil Google Person requirements
         */
        List<com.google.api.services.people.v1.model.Name> googleNameList = makeListFromOne(googleName);
        List<com.google.api.services.people.v1.model.PhoneNumber> googlePhoneNumberList = makeListFromOne(googleNumber);
        List<com.google.api.services.people.v1.model.EmailAddress> googleEmailAddressList =
                makeListFromOne(googleEmail);
        List<com.google.api.services.people.v1.model.Address> googleAddressList = makeListFromOne(googleAddress);

        /**
         * Creating a List of Google UserDefined objects, to use as DoC Person Tags
         */
        List<com.google.api.services.people.v1.model.UserDefined> googleTagList =
                new ArrayList<>();

        for (Tag t : person.getTags()) {
            com.google.api.services.people.v1.model.UserDefined tempGoogleTag =
                    new com.google.api.services.people.v1.model.UserDefined();
            tempGoogleTag.setKey("tag");
            tempGoogleTag.setValue(t.tagName);
            googleTagList.add(tempGoogleTag);
        }

        /**
         * Finalizing the return Google Person object
         */
        tempPerson.setNames(googleNameList);
        tempPerson.setPhoneNumbers(googlePhoneNumberList);
        tempPerson.setEmailAddresses(googleEmailAddressList);
        tempPerson.setAddresses(googleAddressList);
        tempPerson.setUserDefined(googleTagList);

        return tempPerson;
    }

    /**
     * Conversion: (List) Google Person -> Doc person
     * @param googlePersonList
     * @return the converted list of DoC person
     * @throws InvalidGooglePersonException
     */
    public static List<Person> listGoogleToDoCPersonConversion(List<com.google.api.services.people.v1.model.Person>
                                                               googlePersonList) {
        ArrayList<Person> docPersonList = new ArrayList<>();

        for (com.google.api.services.people.v1.model.Person p : googlePersonList) {
            try {
                Person tempDocPerson = singleGoogleToDocPersonConversion(p);
                docPersonList.add(tempDocPerson);
            } catch (InvalidGooglePersonException e) {
                //Invalid Google Person (No name or no number) shall be ignored
                continue;
            }
        }
        return docPersonList;
    }

    /**
     * Conversion: (List) DoC Person -> Google Person
     * @param docPersonList the input list of DoC Person to be converted
     * @return the converted list of Google Person
     */
    public static List<com.google.api.services.people.v1.model.Person> listDocToGooglePersonConversion(
            List<ReadOnlyPerson> docPersonList) {
        ArrayList<com.google.api.services.people.v1.model.Person> googlePersonList = new ArrayList<>();

        for (ReadOnlyPerson p: docPersonList) {
            com.google.api.services.people.v1.model.Person tempGooglePerson = singleDocToGooglePersonConversion(p);
            googlePersonList.add(tempGooglePerson);
        }
        return googlePersonList;
    }


    /**
     * Helper method that returns an ArrayList of generic type E created with a single E instance.
     * This is required when instantiating a Google Person
     */
    public static  <E> List<E> makeListFromOne(E singlePropertyInput) {
        ArrayList<E> tempList = new ArrayList<>();
        tempList.add(singlePropertyInput);
        return tempList;
    }

    /**
     * Processes the retrieved name from Google Contacts, according to DoC's acceptable Name regex
     * @param tempName the retrieved name from Google Contacts
     * @return the processed String result
     * @throws InvalidGooglePersonException if input name is null
     */
    public static String processName(String tempName) throws InvalidGooglePersonException {
        if (tempName == null) {
            throw new InvalidGooglePersonException("DoC does not accept Google Persons with null name");
        }
        return tempName.replaceAll("[^a-zA-Z0-9]", " ");
    }

    /**
     * Helper method that removes all characters not in the allowed regex for a model.person.Person.Phone's value
     * @param tempNumber
     * @return the corrected phone number in the form of a String
     */
    public static String processNumber(String tempNumber) throws InvalidGooglePersonException {
        if (tempNumber == null) {
            throw new InvalidGooglePersonException("DoC does not accept Google Persons with null phone");
        }
        /**
         * Current implementation of the Phone class cannot handle country codes. We will only handle(remove)
         * Singaporean country codes for now, and all other non-digit characters.
         */
        if (tempNumber.contains("+65")) {
            tempNumber = tempNumber.replace("+65", "");
        }
        return tempNumber.replaceAll("[^0-9]", "");
    }
}
```
###### \java\seedu\address\logic\commands\ExportCommand.java
``` java
/**
 * Purpose: Exports DoC's contacts to Google Contacts, with the OAuth2 protocol against the Google People API.
 * Inherits from GoogleCommand & Oauth2Command
 */
public class ExportCommand extends GoogleCommand {
    public static final String COMMAND_WORD = "export";

    //Scope includes write access to a users' Google Contacts
    public static final String ACCESS_SCOPE = "https://www.googleapis.com/auth/contacts";

    private static final String googleContactsGroupView = "https://contacts.google.com/label/";
    private PeopleService peopleService;

    public ExportCommand() {
        super(COMMAND_WORD, ACCESS_SCOPE);
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public CommandResult execute() throws CommandException {
        //Fires an event to the BrowserPanel
        try {
            triggerBrowserAuth();
        } catch (IOException e) {
            throw new CommandException("Failed to trigger browser auth");
        }
        return new CommandResult("Authentication in process");
    }

    /**
     * Event listener for a successful authentication
     * @param event Should be fired from the BrowserPanel, with an authcode
     */
    @Override
    @Subscribe
    protected void handleAuthenticationSuccessEvent(GoogleAuthenticationSuccessEvent event) {
        if (!getCommandCompleted()) {
            //Fire event to alert status bar of conversion process
            EventsCenter.getInstance().post(
                    new NewResultAvailableEvent("Successfully authenticated - Conversion in process now",
                            false));

            if (!commandTypeCheck(event.getCommandType())) {
                return;
            }
            //set up credentials
            setupCredentials(event.getAuthCode());

            //set up people service
            peopleService = new PeopleService.Builder(httpTransport, jsonFactory, credential)
                    .setApplicationName("CS2103T - Doc")
                    .build();

            //Conversion calls
            List<ReadOnlyPerson> docPersonList = model.getAddressBook().getPersonList();
            List<com.google.api.services.people.v1.model.Person> googlePersonList =
                    GooglePersonConverterUtil.listDocToGooglePersonConversion(docPersonList);

            //Set contactGroupId for exporting to a specific contact group: `Imported From DoC`
            String contactGroupId = retrieveExistingContractGroupResourceName();

            //Create a new contact group titled `Imported From DoC`, if still not set
            if (contactGroupId == null) {
                contactGroupId = createNewContactGroup();
            }

            //HTTP calls - exporting
            for (com.google.api.services.people.v1.model.Person p : googlePersonList) {
                try {
                    //create Contact
                    String newPersonId;
                    newPersonId = peopleService.people().createContact(p).execute().getResourceName();

                    //set Contact's group
                    peopleService.contactGroups().members().modify(contactGroupId,
                            new ModifyContactGroupMembersRequest()
                                    .setResourceNamesToAdd(GooglePersonConverterUtil
                                            .makeListFromOne(newPersonId)))
                            .execute();
                } catch (IOException E) {
                    System.out.println(E);
                }
            }
            EventsCenter.getInstance().post(new GoogleCommandCompleteEvent(
                    googleContactsGroupView + contactGroupId.split("/")[1], commandType));
            setCommandCompleted();
        }
    }

    /**
     * Helper Method, executed when no existing Contact Group with the name: "ImportedFromGoogle" is found
     * @return the ResourceName String of the newly created Contact Group ID
     */
    private String createNewContactGroup() {
        String contactGroupId = null;
        try {
            contactGroupId = peopleService.contactGroups().create(
                    new CreateContactGroupRequest()
                            .setContactGroup(new ContactGroup().setName("Imported From DoC")))
                    .execute().getResourceName();
        } catch (IOException e7) {
            System.out.println(e7);
            assert true : "google server error";
        }
        return contactGroupId;
    }

    /**
     * Helper Method, tries to fetch the ResourceName String of an existing Contact Group named: "ImportedFromGoogle"
     * @return the ResourceName String of the existing Contact Group
     */
    private String retrieveExistingContractGroupResourceName() {
        String contactGroupId = null;
        //Fetch all Contact Groups
        List<ContactGroup> contactGroupList = new ArrayList<>();
        try {
            contactGroupList = peopleService.contactGroups().list().execute().getContactGroups();
        } catch (IOException e4) {
            System.out.println(e4);
        }
        for (ContactGroup c : contactGroupList) {
            if (c.getFormattedName().equals("Imported From DoC")) {
                contactGroupId = c.getResourceName();
                break;
            }
        }
        return contactGroupId;
    }

    @Override
    public String getAuthenticationUrl() {
        return new GoogleBrowserClientRequestUrl(CLIENT_ID, getRedirectUrl(), Arrays.asList(getAccessScope())).build();
    }

    private boolean commandTypeCheck(String inputCommandType) {
        return inputCommandType.equals("GOOGLE_export");
    }

    public String getAccessScope() {
        return accessScope;
    }

}
```
###### \java\seedu\address\logic\commands\GoogleCommand.java
``` java
/**
 * This class inherits from the Oauth2Command class and is the parent class for all commands making calls to
 * Google's APIs.
 * Created by Philemon1 on 21/10/2017.
 */
public abstract class GoogleCommand extends Oauth2Command {
    protected static final String CLIENT_ID =
            "591065149112-69ikmid17q2trahg28gip4o8srmo47pv.apps.googleusercontent.com";
    private static final String SERVICE_SOURCE = "GOOGLE";
    protected String accessScope;
    protected TokenResponse authToken;
    protected GoogleCredential credential;
    protected HttpTransport httpTransport;
    protected JacksonFactory jsonFactory;

    protected GoogleCommand(String googleCommandType, String inputAccessScope)  {
        super(SERVICE_SOURCE + "_" + googleCommandType);
        accessScope = inputAccessScope;
        httpTransport = new NetHttpTransport();
        jsonFactory = new JacksonFactory();
    }
    /**Instantiates the GoogleCredentials for OAuth2 requests.
     * This is the final step in the OAuth2 protocol for Google APIs
     * @param authCode
     */
    protected void setupCredentials(String authCode) {
        authToken = new TokenResponse();
        authToken.setAccessToken(authCode);
        credential = new GoogleCredential().setFromTokenResponse(authToken);
    }
}
```
###### \java\seedu\address\logic\commands\ImportCommand.java
``` java
/**
 * Purpose: Imports contacts from Google Contacts, fulfilling Google's OAuth2 protocol.
 * Limit of contacts retrieved set at : 1000
 * Created by Philemon1 on 11/10/2017.
 */
public class ImportCommand extends GoogleCommand {
    public static final String COMMAND_WORD = "import";

    //Scope includes read-only access to a users' Google Contacts
    public static final String ACCESS_SCOPE = "https://www.googleapis.com/auth/contacts.readonly";

    protected PeopleService peopleService;

    public ImportCommand() {
        super(COMMAND_WORD, ACCESS_SCOPE);
        EventsCenter.getInstance().registerHandler(this);
    }


    @Override
    public CommandResult execute() throws CommandException {
        //Fires an event to the BrowserPanel
        try {
            triggerBrowserAuth();
        } catch (IOException e) {
            throw new CommandException("Failed to trigger browser auth");
        }
        return new CommandResult("Authentication in process");
    }

    /**
     * Event listener for a successful authentication
     * @param event Should be fired from the BrowserPanel, with an authcode
     */
    @Override
    @Subscribe
    protected void handleAuthenticationSuccessEvent(GoogleAuthenticationSuccessEvent event) {
        if (!getCommandCompleted()) {
            //Fire event to alert status bar of conversion process
            EventsCenter.getInstance().post(
                    new NewResultAvailableEvent("Successfully authenticated - Conversion in process now",
                            false));

            //Incoming Google Person List
            List<com.google.api.services.people.v1.model.Person> googlePersonList = new ArrayList<>();

            //List of converted DoC person
            List<Person> docPersonList = new ArrayList<>();

            if (!commandTypeCheck(event.getCommandType())) {
                return;
            }

            //set up credentials
            setupCredentials(event.getAuthCode());

            //set up people service
            peopleService = new PeopleService.Builder(httpTransport, jsonFactory, credential)
                    .setApplicationName("CS2103T - Doc")
                    .build();

            //HTTP calls
            try {
                ListConnectionsResponse response = peopleService.people().connections().list("people/me")
                        .setPersonFields("names,emailAddresses,phoneNumbers,addresses")
                        .setPageSize(1000)
                        .execute();
                googlePersonList = response.getConnections();
            } catch (IOException e) {
                System.out.print(e);
            }
            //Conversion call
            docPersonList = GooglePersonConverterUtil.listGoogleToDoCPersonConversion(googlePersonList);

            //Adding to the model
            for (Person p : docPersonList) {
                try {
                    model.addPerson(p);
                } catch (DuplicatePersonException e) {
                    //Duplicate persons shall be ignored
                    continue;
                }
            }
            EventsCenter.getInstance().post(new GoogleCommandCompleteEvent(
                    "https://contacts.google.com/", commandType));
            setCommandCompleted();
        }

    }
    @Override
    public String getAuthenticationUrl() {
        return new GoogleBrowserClientRequestUrl(CLIENT_ID, getRedirectUrl(), Arrays.asList(getAccessScope())).build();
    }
    public String getAccessScope() {
        return accessScope;
    }
    private boolean commandTypeCheck(String inputCommandType) {
        return inputCommandType.equals("GOOGLE_import");
    }
}
```
###### \java\seedu\address\logic\commands\Oauth2Command.java
``` java
/**
 * This class is the parent class for all commands requiring OAuth2 authentication using the BrowserPanel.
 * This class is abstract, and requires child classes to define the commandType.
 * Child classes are also expected to implement the event listener: handleAuthenticationSuccessEvent()
 * Created by Philemon1 on 21/10/2017.
 */
public abstract class Oauth2Command extends Command {
    public static final String INVALID_COMMAND_TYPE_MESSAGE = "The COMMAND_TYPE cannot be null";
    private static final String REDIRECT_URL = "https://cs2103tdummyendpoint.herokuapp.com";
    protected final String commandType;
    private boolean commandCompleted;

    protected Oauth2Command(String inputType)  {
        if (!inputTypeValid(inputType)) {
            assert true : "Child classes of Oauth2Command must provide a valid command type in the format:"
                   + " SERVICEPROVIDER_functionality";
        }
        commandType = inputType;
        commandCompleted = false;
    }

    protected Oauth2Command() {
        this(null);
    }

    public static String getRedirectUrl() {
        return REDIRECT_URL;
    }

    public void setCommandCompleted() {
        commandCompleted = true;
    }
    public boolean getCommandCompleted() {
        return commandCompleted;
    }

    /**
     * Main common functionality for all Oauth2Command childs classes. Fires an event intended for the BrowserPanel,
     * triggering it to start the UI authentication process within the BrowserPanel
     * @throws IOException
     */
    protected void triggerBrowserAuth() throws IOException {
        try {
            Oauth2BrowserRequestEvent trigger = new Oauth2BrowserRequestEvent(commandType, getAuthenticationUrl());
            EventsCenter.getInstance().post(trigger);
        } catch (IOException E) {
            throw E;
        }
    }

    /**
     * Event listener to be implemented by child classes
     */
    @Subscribe
    protected abstract void handleAuthenticationSuccessEvent(GoogleAuthenticationSuccessEvent event);

    /**
     * All child classes should provide this URL based on their scope required
     * @return the authentication URL, based on the scope required of the command
     */
    public abstract String getAuthenticationUrl ();

    private boolean inputTypeValid(String inputType) {
        return inputType != null && inputType.charAt(inputType.length() - 1) != '_';
    }
}
```
###### \java\seedu\address\logic\commands\UnknownCommand.java
``` java
/**
 * Created by Philemon1 on 29/10/2017.
 * This class is used to process all unknown commands, i.e user input that does not match any existing COMMAND_WORD
 * this is done by searching for COMMAND_WORDs with a Levenshtein distance <= the set ACCEPTABLE_LEVENSHTEIN_DISTANCE,
 * away from the user input command word
 *
 * The Levenshtein distance calculator implementation is adapted entirely from:
 * https://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Java
 * Full credits have been given in the method comment:{@link #levenshteinDistance(CharSequence, CharSequence)}
 */
public class UnknownCommand extends Command {
    /**
     * These constants can be varied to allow wider similarity detection, which will come at the cost of performance.
     * They should not be chosen arbitrarily. For example,
     * the ACCEPTABLE_LEVENSHTEIN_DISTANCE should be less than the shortest system-recognized COMMAND_WORD available,
     * although this is not a strict rule.
     * the ACCEPTABLE_MAXIMUM_COMMAND_WORD_LENGTH should be computed by summing the longest system-recognized
     * COMMAND_WORD available, and the set ACCEPTABLE_LEVENSHTEIN_DISTANCE, to prevent needless checking
     */
    private static final int ACCEPTABLE_LEVENSHTEIN_DISTANCE = 2;
    private static final int ACCETABLE_MAXIMUM_COMMAND_WORD_LENGTH = 18;

    //A constant String[] of all known COMMAND WORDS; this list should be extended when new Command types are created
    private static final String[] ALL_COMMAND_WORDS = {
        AddCommand.COMMAND_WORD, AddEventCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD,
        DeleteEventCommand.COMMAND_WORD, DeleteProfilePicCommand.COMMAND_WORD, EditCommand.COMMAND_WORD,
        EditEventCommand.COMMAND_WORD, EmailCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD, ExportCommand.COMMAND_WORD,
        FindCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, ImportCommand.COMMAND_WORD,
        ListCommand.COMMAND_WORD, LocationCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD, RemoveTagCommand.COMMAND_WORD,
        SelectCommand.COMMAND_WORD, SortCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD,
        UpdateProfilePicCommand.COMMAND_WORD
    };

    private String commandWord;
    private String arguments;
    private String promptToUser;
    private Command suggestedCommand;

    public UnknownCommand(String inputCommandWord, String inputArguments) {
        commandWord = inputCommandWord;
        arguments = inputArguments;
    }


    @Override
    public CommandResult execute() throws CommandException {
        return new CommandResult(promptToUser);
    }

    public Command getSuggestedCommand() {
        return suggestedCommand;
    }


    /**
     * This method initiates the computation of Levenshtein distance between the user input commandWord, and each of
     * the system-recognized COMMAND_WORDs, in the constant String[] ALL_COMMAND_WORDS.
     * This method also sets the suggested command, and updates the PROMPT_TO_USER accordingly, if any matches are
     * found.
     * This method will only check for user-entered commandWord <= ACCETABLE_MAXIMUM_COMMAND_WORD_LENGTH in length
     * In the case of equal Levenshtein distance, the first encountered in the order of delcaration in the
     * String[] ALL_COMMAND_WORDS, will be used.
     * This method should always be executed before execute() method is called
     * @return true if minimum distance found is <= the ACCEPTABLE_LEVENSHTEIN_DISTANCE constant set, else false
     */
    public boolean suggestionFound() throws ParseException {
        int min = ACCEPTABLE_LEVENSHTEIN_DISTANCE + 1;
        int tempDistance;
        String closestCommandWord = null;

        //Checks for invalid commandWord length, i.e 18 and above (To prevent performance issues)
        if (invalidLengthDetected()) {
            return false;
        }

        /**
         * Iterates through all known COMMAND_WORDs, and find the smallest possible Levenshtein distance.
         * In the case of equal Levenshtein distance, the first encountered in the order of delcaration in the String[]
         * ALL_COMMAND_WORDS, will be used.
         */
        for (String s: ALL_COMMAND_WORDS) {
            tempDistance = levenshteinDistance(s, commandWord);
            if (tempDistance < min) {
                min = tempDistance;
                closestCommandWord = s;
            }
        }
        //An unchanged min indicates that no acceptable substitute has been found
        if (min == ACCEPTABLE_LEVENSHTEIN_DISTANCE + 1) {
            return false;
        } else {
            setSuggestedCommand(closestCommandWord);
            promptToUser = "Did you mean: " + closestCommandWord + arguments + " ?" + "\n" + "Respond: "
                    + "'yes' or 'y' to accept the suggested command." + "\n"
                    + "Suggested command will be discarded otherwise.";
            return true;
        }
    }

    /**
     * This method checks for an invalid commandWord length
     * @return true if commandWord.length is more than the ACCEPTABLE_MAXIMUM_COMMAND_WORD_LENGTH
     * @throws ParseException if user input command length is 0
     */
    private boolean invalidLengthDetected () {
        if (commandWord.length() > ACCETABLE_MAXIMUM_COMMAND_WORD_LENGTH) {
            return true;
        }
        if (commandWord.length() == 0) {
            assert true : "Unexpected behaviour: Empty input has gone through the AddressBookParser parseCommand()"
                    + "matcher check";
        }
        return false;
    }

```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        /**
         * Executes if the system has prompted the user for a typo-correction suggestion previously,
         * If the user accepts the suggestion with a response of "yes" or "y", we will execute the generated suggested
         * command.
         * Upon any other response, we will reset the unknownCommand instance, and parse the command normally with the
         * switch
         */
        if (correctionPrompted) {
            if (userAcceptsSuggestion(commandWord)) {
                Command suggestedCommand = unknownCommand.getSuggestedCommand();
                resetCorrectionChecker();
                return suggestedCommand;
            } else {
                resetCorrectionChecker();
            }
        }
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        default:
            unknownCommand = new UnknownCommand(commandWord, arguments);
            /**
             * initiate the similarity checking logic. If suggestionFound() returns false, we will reset the
             * unknownCommand to null, as no matches were found
             */
            if (unknownCommand.suggestionFound()) {
                correctionPrompted = true;
                return unknownCommand;
            } else {
                unknownCommand = null;
            }
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
    private boolean userAcceptsSuggestion(String commandWord) {
        return commandWord.equals("yes") || commandWord.equals("y");
    }

    private void resetCorrectionChecker() {
        unknownCommand = null;
        correctionPrompted = false;
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    private String currentUrl = "";
    private ChangeListener currentUrlListener;
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    /**
     * Handles an Oauth2BrowserRequestEvent sent by the execution of a command requiring the authentication against
     * the OAuth2 protocol
     * @param event
     */
    @Subscribe
    private void handleOauth2BrowserRequestEvent(Oauth2BrowserRequestEvent event) {
        loadPage(event.getRequestUrl());
        resetUrlListener();
        currentUrlListener = new UrlListener(event.getCommandType());
        browser.getEngine().locationProperty().addListener(currentUrlListener);
    }

    /**
     * Resets any current listeners of the URL
     */
    private void resetUrlListener() {
        if (currentUrlListener != null) {
            browser.getEngine().locationProperty().removeListener(currentUrlListener);
            currentUrlListener = null;
        }
    }


    /**
     * Implements the functional interface ChangeListener. Lambda not used, due to the need to reset the url listener
     */
    private class UrlListener implements ChangeListener {
        private String commandType;
        UrlListener(String inputCommandType) {
            commandType = inputCommandType;
        }
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            currentUrl = (String) newValue;
            if (authSuccessUrlDetected(currentUrl)) {
                EventsCenter.getInstance().post(new GoogleAuthenticationSuccessEvent(commandType,
                        currentUrl.split("=")[1].split("&")[0]));
            }
        }
    }

    /**
     * Checks if Authentication is successful -> current domain reflects the set redirect endpoint
     * @param currentUrl
     * @return
     */
    private boolean authSuccessUrlDetected(String currentUrl) {
        System.out.println(currentUrl);
        return currentUrl.contains("access_token=");
    }

    /**
     * Reloads page according to the completed Google Command
     */
    @Subscribe
    private void handleGoogleCOmmandCompleteEvent(GoogleCommandCompleteEvent event) {
        loadPage(event.getRedirectUrl());
    }
```
