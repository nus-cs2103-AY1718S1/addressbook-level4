package seedu.address.logic.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleBrowserClientRequestUrl;

import com.google.api.services.people.v1.model.ContactGroup;
import com.google.api.services.people.v1.model.CreateContactGroupRequest;
import com.google.api.services.people.v1.model.ModifyContactGroupMembersRequest;
import com.google.common.eventbus.Subscribe;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.logic.GoogleAuthenticationSuccessEvent;
import seedu.address.commons.events.logic.GoogleCommandCompleteEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.util.GooglePersonConverterUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author philemontan
/**
 * Purpose: Exports DoC's contacts to Google Contacts, with the OAuth2 protocol against the Google People API.
 * Inherits from {@link seedu.address.logic.commands.GoogleCommand},{@link seedu.address.logic.commands.Oauth2Command}
 */
public class ExportCommand extends GoogleCommand {
    public static final String COMMAND_WORD = "export";

    //This scope includes write access to a users' Google Contacts
    public static final String ACCESS_SCOPE = "https://www.googleapis.com/auth/contacts";

    private static final String GOOGLE_CONTACTS_GROUP_VIEW = "https://contacts.google.com/label/";

    public ExportCommand() {
        super(COMMAND_WORD, ACCESS_SCOPE);
        EventsCenter.getInstance().registerHandler(this);
    }

    /**
     * Begins the authentication process with the {@link seedu.address.ui.BrowserPanel}
     * @return message on success
     * @throws CommandException on failure to trigger the process
     */
    @Override
    public CommandResult execute() throws CommandException {
        try {
            triggerBrowserAuth();
        } catch (IOException e) {
            throw new CommandException(TRIGGER_BROWSER_AUTH_FAILED_MESSAGE);
        }
        return new CommandResult(TRIGGER_BROWSER_AUTH_SUCCESS_MESSAGE);
    }

    /**
     * Event listener for a successful authentication
     * @param event should be fired from the {@link seedu.address.ui.BrowserPanel} once it retrieves the authentication
     * code
     */
    @Override
    @Subscribe
    protected void handleAuthenticationSuccessEvent(GoogleAuthenticationSuccessEvent event) {
        if (!commandTypeCheck(event.getCommandType())) {
            return;
        }

        if (getCommandCompleted()) {
            return;
        }

        setupCredentials(event.getAuthCode());
        setupPeopleService();
        String contactGroupId = getContactGroupId();
        startBackgroundConversionAndHttpCalls(contactGroupId);
        EventsCenter.getInstance().post(new GoogleCommandCompleteEvent(
                GOOGLE_CONTACTS_GROUP_VIEW + contactGroupId.split("/")[1], getCommandType()));
        setCommandCompleted();
    }

    /**
     * This method tries to retrieve the contactGroupId of an existing contact group in the user's Google Contacts,
     * with the name {@link seedu.address.logic.commands.GoogleCommand#CONTACT_GROUP_NAME_ON_GOOGLE}. If it cannot be
     * fetched, it creates it instead.
     * @return contactGroupId String of an existing or new group.
     */
    private String getContactGroupId() {
        String contactGroupId = retrieveExistingContractGroupResourceName();
        if (contactGroupId == null) {
            contactGroupId = createNewContactGroup();
        }
        return contactGroupId;
    }

    /**
     * Helper Method, executed when no existing Contact Group with the name
     * {@link seedu.address.logic.commands.GoogleCommand#CONTACT_GROUP_NAME_ON_GOOGLE} is found
     * @return the ResourceName String of the newly created Contact Group ID
     */
    private String createNewContactGroup() {
        String contactGroupId = null;
        try {
            contactGroupId = peopleService.contactGroups().create(
                    new CreateContactGroupRequest()
                            .setContactGroup(new ContactGroup().setName(CONTACT_GROUP_NAME_ON_GOOGLE)))
                    .execute().getResourceName();
        } catch (IOException E) {
            EventsCenter.getInstance().post(new NewResultAvailableEvent(FAILED_CONNECTION_MESSAGE, true));
            setCommandCompleted();
        }
        return contactGroupId;
    }

    /**
     * Helper Method, tries to fetch the ResourceName String of an existing Contact Group named:
     * {@link seedu.address.logic.commands.GoogleCommand#CONTACT_GROUP_NAME_ON_GOOGLE}
     * @return the ResourceName String of the existing Contact Group if it can be fetched, else, null
     */
    private String retrieveExistingContractGroupResourceName() {
        String contactGroupId = null;
        //Fetch all Contact Groups
        List<ContactGroup> contactGroupList = new ArrayList<>();
        try {
            contactGroupList = peopleService.contactGroups().list().execute().getContactGroups();
        } catch (IOException E) {
            EventsCenter.getInstance().post(new NewResultAvailableEvent(FAILED_CONNECTION_MESSAGE, true));
            setCommandCompleted();
        }
        for (ContactGroup c : contactGroupList) {
            if (c.getFormattedName().equals(CONTACT_GROUP_NAME_ON_GOOGLE)) {
                contactGroupId = c.getResourceName();
                break;
            }
        }
        return contactGroupId;
    }

    @Override
    public String getAuthenticationUrl() {
        return new GoogleBrowserClientRequestUrl(getClientId(), getRedirectUrl(),
                Arrays.asList(ACCESS_SCOPE)).build();
    }

    private boolean commandTypeCheck(String inputCommandType) {
        return inputCommandType.equals(SERVICE_SOURCE + "_" + COMMAND_WORD);
    }

    public String getAccessScope() {
        return accessScope;
    }

    private void startBackgroundConversionAndHttpCalls(String contactGroupId) {
        ExportBackgroundService exportBackgroundService = new ExportBackgroundService(contactGroupId);
        exportBackgroundService.start();
    }

    /**
     * This class extends the {@link javafx.concurrent.Service} class, to run the intensive conversion and http calls
     * in a separate background thread.
     */
    class ExportBackgroundService extends Service {
        private String contactGroupId;

        public ExportBackgroundService(String inputContactGroupId) {
            contactGroupId = inputContactGroupId;
        }

        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    //Conversion calls
                    List<ReadOnlyPerson> docPersonList = model.getAddressBook().getPersonList();
                    List<com.google.api.services.people.v1.model.Person> googlePersonList =
                            GooglePersonConverterUtil.listDocToGooglePersonConversion(docPersonList);

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
                            EventsCenter.getInstance().post(new NewResultAvailableEvent(FAILED_CONNECTION_MESSAGE,
                                    true));
                            setCommandCompleted();
                        }
                    }
                    return null;
                }
            };
        }
    }

}


