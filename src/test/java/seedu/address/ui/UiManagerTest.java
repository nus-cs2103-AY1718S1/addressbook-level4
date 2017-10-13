package seedu.address.ui;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Config;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UiManagerTest extends GuiUnitTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private UiManager ui;


    @Before
    public void setUp() {
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Model model = new ModelManager(getTypicalAddressBook(), userPrefs);
        Logic logic = new LogicManager(model);
        ui = new UiManager(logic, config, userPrefs);
    }

    @Test
    public void fatalErrorTest() {
        //If primary stage is null, Exception should be thrown
        thrown.expect(Exception.class);
        ui.start(null);

    }

}
