package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static seedu.address.testutil.EventsUtil.postNow;

import org.junit.Before;
import org.junit.Test;
import org.kordamp.ikonli.feather.Feather;

import guitests.guihandles.CommandBoxIconHandle;
import seedu.address.commons.events.ui.CommandInputChangedEvent;

public class CommandBoxIconTest extends GuiUnitTest {

    private static final CommandInputChangedEvent COMMAND_INPUT_PARTIAL_ADD = new CommandInputChangedEvent("ad");
    private static final CommandInputChangedEvent COMMAND_INPUT_ADD = new CommandInputChangedEvent("add");

    private CommandBoxIconHandle commandBoxIconHandle;

    @Before
    public void setUp() {
        CommandBoxIcon commandBoxIcon = new CommandBoxIcon();
        uiPartRule.setUiPart(commandBoxIcon);

        commandBoxIconHandle = new CommandBoxIconHandle(getChildNode(commandBoxIcon.getRoot(),
                CommandBoxIconHandle.COMMAND_ICON_FIELD_ID));
    }

    @Test
    public void display() {
        // default result text
        guiRobot.pauseForHuman();
        assertNull(commandBoxIconHandle.getIconCode());

        // partial command entered
        postNow(COMMAND_INPUT_PARTIAL_ADD);
        guiRobot.pauseForHuman();
        assertNull(commandBoxIconHandle.getIconCode());

        // new command entered
        postNow(COMMAND_INPUT_ADD);
        guiRobot.pauseForHuman();
        assertEquals(Feather.FTH_PLUS, commandBoxIconHandle.getIconCode());
    }
}
