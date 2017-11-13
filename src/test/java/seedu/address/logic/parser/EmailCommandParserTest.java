//@@author tshradheya
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.EmailBuilder.DEFAULT_BODY;
import static seedu.address.testutil.EmailBuilder.DEFAULT_SERVICE;
import static seedu.address.testutil.EmailBuilder.DEFAULT_SUBJECT;
import static seedu.address.testutil.EmailBuilder.DEFAULT_TAG;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.EmailCommand;
import seedu.address.model.email.Body;
import seedu.address.model.email.Service;
import seedu.address.model.email.Subject;
import seedu.address.model.person.PersonContainsTagPredicate;
import seedu.address.testutil.EmailBuilder;

public class EmailCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private EmailCommandParser emailCommandParser = new EmailCommandParser();

    @Test
    public void allFieldsPresent_success() throws IOException {
        EmailCommand emailCommand = new EmailBuilder().withPredicate(new PersonContainsTagPredicate(DEFAULT_TAG))
                .withSubject(new Subject(DEFAULT_SUBJECT)).withBody(new Body(DEFAULT_BODY))
                .withService(new Service(DEFAULT_SERVICE)).build();

        assertParseSuccess(emailCommandParser, EmailCommand.COMMAND_WORD + " s/"
                        + DEFAULT_SERVICE + " to/" + DEFAULT_TAG + " sub/" + DEFAULT_SUBJECT + " body/" + DEFAULT_BODY,
                new EmailCommand(new PersonContainsTagPredicate(DEFAULT_TAG), new Subject(DEFAULT_SUBJECT),
                        new Body(DEFAULT_BODY), new Service(DEFAULT_SERVICE)));
    }

    @Test
    public void optionalFieldsMissingSuccess() throws IOException {
        EmailCommand emailCommand = new EmailBuilder().withPredicate(new PersonContainsTagPredicate(DEFAULT_TAG))
                .withService(new Service(DEFAULT_SERVICE)).build();

        assertParseSuccess(emailCommandParser, EmailCommand.COMMAND_WORD + " s/"
                        + DEFAULT_SERVICE + " to/" + DEFAULT_TAG + " sub/" + " body/",
                new EmailCommand(new PersonContainsTagPredicate(DEFAULT_TAG), new Subject(""),
                        new Body(""), new Service(DEFAULT_SERVICE)));
    }

    @Test
    public void parse_compulsoryFieldMissingRecipients_failure() throws Exception {
        EmailCommand emailCommand = new EmailBuilder().withPredicate(new PersonContainsTagPredicate(DEFAULT_TAG)).build();

        assertParseFailure(emailCommandParser, EmailCommand.COMMAND_WORD + " s/"
                        + DEFAULT_SERVICE + " sub/" + " body/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_compulsoryFieldMissingService_failure() throws Exception {

        assertParseFailure(emailCommandParser, EmailCommand.COMMAND_WORD + " to/"
                        + DEFAULT_TAG + " sub/" + " body/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }
}
