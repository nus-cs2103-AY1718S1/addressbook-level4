package seedu.address.logic.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleBrowserClientRequestUrl;

import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.common.eventbus.Subscribe;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.logic.GoogleAuthenticationSuccessEvent;
import seedu.address.commons.events.logic.GoogleCommandCompleteEvent;

import seedu.address.commons.util.GooglePersonConverterUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

import seedu.address.model.person.exceptions.DuplicatePersonException;

//@@author philemontan
/**
 * Purpose: Import contacts from Google Contacts to DoC, with the OAuth2 protocol against the Google People API.
 * Limit of contacts retrieved set at : 2000, which is the People API's inherent limit
 * of UI
 */
public class ImportCommand extends GoogleCommand {
    public static final String COMMAND_WORD = "import";
    public static final int CONTACT_RETRIEVAL_LIMIT = 2000;

    //This scope includes read-only access to a users' Google Contacts
    public static final String ACCESS_SCOPE = "https://www.googleapis.com/auth/contacts.readonly";

    private static final String GOOGLE_CONTACTS_DEFAULT_VIEW = "https://contacts.google.com/";

    public ImportCommand() {
        super(COMMAND_WORD, ACCESS_SCOPE);
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public CommandResult execute() throws CommandException {
        //Fires an event to the BrowserPanel
        try {
            triggerBrowserAuth();
        } catch (IOException E) {
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
        ImportBackgroundService importBackgroundService = setupImportBackgroundService();
        importBackgroundService.start();
        EventsCenter.getInstance().post(new GoogleCommandCompleteEvent(
                GOOGLE_CONTACTS_DEFAULT_VIEW, getCommandType()));
        setCommandCompleted();
    }

    /**
     * Creates and primes an ImportBackgroundService to update the Model once background task has completed
     * @return the primed Service
     */
    private ImportBackgroundService setupImportBackgroundService() {
        ImportBackgroundService importBackgroundService = new ImportBackgroundService();
        importBackgroundService.setOnSucceeded(event1 -> {
            for (Person p : importBackgroundService.producedDocPersonList) {
                try {
                    model.addPerson(p);
                } catch (DuplicatePersonException e) {
                    //Duplicate persons shall be ignored
                    continue;
                }
            }
        });
        return importBackgroundService;
    }

    @Override
    public String getAuthenticationUrl() {
        return new GoogleBrowserClientRequestUrl(getClientId(), getRedirectUrl(),
                Arrays.asList(ACCESS_SCOPE)).build();
    }

    private boolean commandTypeCheck(String inputCommandType) {
        return inputCommandType.equals(SERVICE_SOURCE + "_" + COMMAND_WORD);
    }

    /**
     * This class extends the {@link javafx.concurrent.Service} class, to run the intensive conversion and http calls
     * in a separate background thread.
     */
    class ImportBackgroundService extends Service {
        private List<Person> producedDocPersonList;

        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    List<com.google.api.services.people.v1.model.Person> incomingGooglePersonList = new ArrayList<>();
                    //HTTP calls
                    try {
                        ListConnectionsResponse response = peopleService.people().connections().list("people/me")
                                .setPersonFields("names,emailAddresses,phoneNumbers,addresses")
                                .setPageSize(CONTACT_RETRIEVAL_LIMIT)
                                .execute();
                        incomingGooglePersonList = response.getConnections();
                    } catch (IOException e) {
                        System.out.print(e);
                    }
                    //Conversion call
                    producedDocPersonList = GooglePersonConverterUtil
                            .listGoogleToDoCPersonConversion(incomingGooglePersonList);
                    return null;
                }
            };
        }
    }
}
