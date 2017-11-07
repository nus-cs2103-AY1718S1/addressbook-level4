package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CommandBoxHintsHandle;
import javafx.scene.control.TextField;
import seedu.address.commons.events.ui.CommandInputChangedEvent;
import seedu.address.commons.util.TextUtil;

//@@author nicholaschuayunzhi
public class CommandBoxHintsTest extends GuiUnitTest {

    private CommandBoxHints commandBoxHints;
    private CommandBoxHintsHandle commandBoxHintsHandle;
    private TextField commandTextField;
    @Before
    public void setUp() {
        commandTextField = new TextField();
        guiRobot.interact(() -> commandBoxHints = new CommandBoxHints(commandTextField));
        uiPartRule.setUiPart(commandBoxHints);
        commandBoxHintsHandle = new CommandBoxHintsHandle(commandBoxHints.getRoot());
    }

    @Test
    public void display() {
        postNow(new CommandInputChangedEvent("add"));
        assertEquals(" n/name", commandBoxHintsHandle.getText());

        postNow(new CommandInputChangedEvent("add "));
        assertEquals("n/name", commandBoxHintsHandle.getText());

        postNow(new CommandInputChangedEvent("add n"));
        assertEquals("/name", commandBoxHintsHandle.getText());

        postNow(new CommandInputChangedEvent("add n/"));
        assertEquals("name", commandBoxHintsHandle.getText());

        postNow(new CommandInputChangedEvent(""));
        assertEquals("Enter Command Here", commandBoxHintsHandle.getText());
    }

    @Test
    public void set_prefWidth() {
        postNow(new CommandInputChangedEvent("add"));
        String hint = commandBoxHintsHandle.getText();
        TextField textField = commandBoxHintsHandle.getTextField();
        double width = TextUtil.computeTextWidth(textField.getFont(), hint, 0.0D) + 1;

        assertEquals(width, textField.getPrefWidth(), 0);

        postNow(new CommandInputChangedEvent("select"));
        String hint2 = commandBoxHintsHandle.getText();
        TextField textField2 = commandBoxHintsHandle.getTextField();
        double width2 = TextUtil.computeTextWidth(textField.getFont(), hint2, 0.0D) + 1;

        assertEquals(width2, textField.getPrefWidth(), 0);
    }

    @Test public void clickSetsCorrectcaretPosition() {
        inputTextAndClickHints("add");
        assertEquals(3, commandTextField.getCaretPosition());

        inputTextAndClickHints("test");
        assertEquals(4, commandTextField.getCaretPosition());
    }

    /**
     * set {@code input} on commandTextField, posts change event and calls handleOnClick of commandBoxHints
     */
    private void inputTextAndClickHints(String input) {
        commandTextField.setText(input);
        postNow(new CommandInputChangedEvent(input));

        try {
            Method handleOnClick = commandBoxHints.getClass().getDeclaredMethod("handleOnClick");
            handleOnClick.setAccessible(true);
            handleOnClick.invoke(commandBoxHints);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }
}
