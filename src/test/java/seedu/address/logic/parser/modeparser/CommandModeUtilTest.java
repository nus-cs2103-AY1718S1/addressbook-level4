package seedu.address.logic.parser.modeparser;

import org.junit.Assert;
import org.junit.Test;

public class CommandModeUtilTest {

    @Test
    public void test_getModePrefix_returnNotDefault() {
        String modePrefix = "-a";
        Assert.assertEquals(modePrefix, CommandModeUtil.getModePrefix(modePrefix + " " + "ALice"));

        Assert.assertEquals(modePrefix, CommandModeUtil.getModePrefix(modePrefix + "   "));

        Assert.assertEquals(modePrefix, CommandModeUtil.getModePrefix(modePrefix));
    }

    @Test
    public void test_getModePrefix_returnDefault() {
        // Empty string
        Assert.assertEquals(CommandModeUtil.DEFAULT_MODE_PREFIX, CommandModeUtil.getModePrefix(""));

        // No prefix
        Assert.assertEquals(CommandModeUtil.DEFAULT_MODE_PREFIX, CommandModeUtil.getModePrefix("Alice Bob"));

        // Wrong prefix
        Assert.assertEquals(CommandModeUtil.DEFAULT_MODE_PREFIX, CommandModeUtil.getModePrefix("--a"));

        // Wrong prefix position
        Assert.assertEquals(CommandModeUtil.DEFAULT_MODE_PREFIX, CommandModeUtil.getModePrefix("Alic -a"));
    }

    @Test
    public void test_getModeArgs() {
        String args;
        // Empty string
        args = "";
        Assert.assertEquals("", CommandModeUtil.getModeArgs(CommandModeUtil.getModePrefix(args), args));

        // Empty modeArgs
        args = "-a";
        Assert.assertEquals("", CommandModeUtil.getModeArgs(CommandModeUtil.getModePrefix(args), args));

        // Empty modeArgs with whitespaces
        args = "-a     ";
        Assert.assertEquals("", CommandModeUtil.getModeArgs(CommandModeUtil.getModePrefix(args), args));

        // Has modeArgs
        args = "-a Alice";
        Assert.assertEquals("Alice", CommandModeUtil.getModeArgs(CommandModeUtil.getModePrefix(args), args));

        // ModeArgs with more whitespaces
        args = "-a   Alice   ";
        Assert.assertEquals("Alice", CommandModeUtil.getModeArgs(CommandModeUtil.getModePrefix(args), args));

        // Incorrect mode prefix
        args = "--a";
        Assert.assertEquals(args, CommandModeUtil.getModeArgs(CommandModeUtil.getModePrefix(args), args));

        // Incorrect mode prefix position
        args = "Alice -a";
        Assert.assertEquals(args, CommandModeUtil.getModeArgs(CommandModeUtil.getModePrefix(args), args));
    }
}