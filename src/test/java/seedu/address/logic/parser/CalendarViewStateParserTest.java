package seedu.address.logic.parser;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author kaiyu92
public class CalendarViewStateParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private UserPrefs userPrefs;
    private Model model;

    @Before
    public void setup() {
        userPrefs = new UserPrefs();
        model = new ModelManager();
    }

    @Test
    public void updateViewState_nullCalendar_throwsNullPointerException() throws Exception {
        CalendarViewStateParser parser = new CalendarViewStateParser(userPrefs, model, null);
        thrown.expect(NullPointerException.class);
        parser.updateViewState("Test");
    }
}
