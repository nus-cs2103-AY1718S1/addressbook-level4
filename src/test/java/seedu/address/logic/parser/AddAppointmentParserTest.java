package seedu.address.logic.parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.parser.exceptions.ParseException;

import static org.junit.Assert.fail;

public class AddAppointmentParserTest {

    private AddAppointmentParser parser = new AddAppointmentParser();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void prefixesNotPresent() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse("Alice 2018/02/10 10:10");
    }

    @Test
    public void illegalExpression() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse("n/@@@@ d/2018/02/10 10:10");
    }

    @Test
    public void parseDateExpression() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse("n/Alice d/20180210 1010");

        thrown.expect(ParseException.class);
        parser.parse("n/Alice d/2018/0210 1010");

        thrown.expect(ParseException.class);
        parser.parse("n/Alice d/2018/02/10 1010");

        thrown.expect(ParseException.class);
        parser.parse("n/Alice d/2018/02/10 101:0");

        try {
            parser.parse("n/Alice d/2018/02/10 10:10");
        } catch (ParseException e) {
            fail(e.getMessage());
        }
    }
}
