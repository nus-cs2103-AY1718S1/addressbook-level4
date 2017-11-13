//@@author tshradheya
package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.email.Body;
import seedu.address.model.email.Service;
import seedu.address.model.email.Subject;
import seedu.address.model.person.PersonContainsTagPredicate;


public class EmailCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void processEmailTestFail() throws ParseException {
        thrown.expect(ParseException.class);
        EmailCommand command = new EmailCommand(new PersonContainsTagPredicate("friends"), new Subject("hello"),
                new Body("hi"), new Service("gmail"));
        command.processEmail("");
    }

    @Test
    public void checkServiceValid_throwsException() throws ParseException {
        thrown.expect(ParseException.class);
        EmailCommand command = new EmailCommand(new PersonContainsTagPredicate("friends"), new Subject("hello"),
                new Body("hi"), new Service("gmail"));
        command.checkServiceValid(new Service("notPresent"));
    }

    @Test
    public void executeParseExceptionThrown() {
        EmailCommand command = new EmailCommand(new PersonContainsTagPredicate("friend"), new Subject("hello"),
                new Body("hi"), new Service("non existing"));
        CommandResult result = command.execute();
        assertEquals(result.feedbackToUser, EmailCommand.MESSAGE_NOT_SENT);
    }

    @Test
    public void equals() {
        EmailCommand command = new EmailCommand(new PersonContainsTagPredicate("friends"), new Subject("hello"),
                new Body("hi"), new Service("gmail"));
        EmailCommand sameCommand = new EmailCommand(new PersonContainsTagPredicate("friends"), new Subject("hello"),
                new Body("hi"), new Service("gmail"));
        EmailCommand differentCommand = new EmailCommand(new PersonContainsTagPredicate("hmm"), new Subject("hi"),
                new Body("yo"), new Service("outlook"));

        assertTrue(command.equals(sameCommand));

        assertTrue(command.equals(command));

        assertFalse(command == null);

        assertFalse(sameCommand.equals(differentCommand));
    }


}
