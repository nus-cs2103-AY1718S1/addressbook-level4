//@@author tshradheya
package seedu.address.testutil;

import seedu.address.logic.commands.EmailCommand;
import seedu.address.model.email.Body;
import seedu.address.model.email.Service;
import seedu.address.model.email.Subject;
import seedu.address.model.person.PersonContainsTagPredicate;

/**
 * A utility class to help with building Email content
 */
public class EmailBuilder {

    public static final String DEFAULT_SERVICE = "gmail";
    public static final String DEFAULT_TAG = "friends";
    public static final String DEFAULT_SUBJECT = "hello";
    public static final String DEFAULT_BODY = "how are you?";

    private EmailCommand emailCommand;

    public EmailBuilder() {
        this.emailCommand = new EmailCommand(new PersonContainsTagPredicate(DEFAULT_TAG),
                new Subject(DEFAULT_SUBJECT), new Body(DEFAULT_BODY), new Service(DEFAULT_SERVICE));
    }

    public EmailBuilder(EmailCommand toCopy) {
        emailCommand = toCopy;
    }

    /** Assigns a service to email command */
    public EmailBuilder withService(Service service) {
        emailCommand.setService(service);
        return this;
    }

    /** Assigns a subject to email command */
    public EmailBuilder withSubject(Subject subject) {
        emailCommand.setSubject(subject);
        return this;
    }

    /** Assigns a body to email command */
    public EmailBuilder withBody(Body body) {
        emailCommand.setBody(body);
        return this;
    }

    /** Assigns a predicate to email command to represent recipients */
    public EmailBuilder withPredicate(PersonContainsTagPredicate personContainsTagPredicate) {
        emailCommand.setPredicate(personContainsTagPredicate);
        return this;
    }
    public EmailCommand build() {
        return this.emailCommand;
    }
}
