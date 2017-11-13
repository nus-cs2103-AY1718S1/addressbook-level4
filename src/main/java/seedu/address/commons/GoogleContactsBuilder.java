//@@author PhuaJunJie
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
