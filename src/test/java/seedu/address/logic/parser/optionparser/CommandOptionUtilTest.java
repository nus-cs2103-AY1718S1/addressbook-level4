package seedu.address.logic.parser.optionparser;

import org.junit.Assert;
import org.junit.Test;

public class CommandOptionUtilTest {

    @Test
    public void test_getOptionPrefix_returnNotDefault() {
        String optionPrefix = "-a";
        Assert.assertEquals(optionPrefix, CommandOptionUtil.getOptionPrefix(optionPrefix + " " + "ALice"));

        Assert.assertEquals(optionPrefix, CommandOptionUtil.getOptionPrefix(optionPrefix + "   "));

        Assert.assertEquals(optionPrefix, CommandOptionUtil.getOptionPrefix(optionPrefix));
    }

    @Test
    public void test_getOptionPrefix_returnDefault() {
        // Empty string
        Assert.assertEquals(CommandOptionUtil.DEFAULT_OPTION_PREFIX, CommandOptionUtil.getOptionPrefix(""));

        // No prefix
        Assert.assertEquals(CommandOptionUtil.DEFAULT_OPTION_PREFIX, CommandOptionUtil.getOptionPrefix("Alice Bob"));

        // Wrong prefix
        Assert.assertEquals(CommandOptionUtil.DEFAULT_OPTION_PREFIX, CommandOptionUtil.getOptionPrefix("--a"));

        // Wrong prefix position
        Assert.assertEquals(CommandOptionUtil.DEFAULT_OPTION_PREFIX, CommandOptionUtil.getOptionPrefix("Alic -a"));
    }

    @Test
    public void test_getOptionArgs() {
        String args;
        // Empty string
        args = "";
        Assert.assertEquals("", CommandOptionUtil.getOptionArgs(CommandOptionUtil.getOptionPrefix(args), args));

        // Empty optionArgs
        args = "-a";
        Assert.assertEquals("", CommandOptionUtil.getOptionArgs(CommandOptionUtil.getOptionPrefix(args), args));

        // Empty optionArgs with whitespaces
        args = "-a     ";
        Assert.assertEquals("", CommandOptionUtil.getOptionArgs(CommandOptionUtil.getOptionPrefix(args), args));

        // Has optionArgs
        args = "-a Alice";
        Assert.assertEquals("Alice", CommandOptionUtil.getOptionArgs(CommandOptionUtil.getOptionPrefix(args), args));

        // OptionArgs with more whitespaces
        args = "-a   Alice   ";
        Assert.assertEquals("Alice", CommandOptionUtil.getOptionArgs(CommandOptionUtil.getOptionPrefix(args), args));

        // Incorrect option prefix
        args = "--a";
        Assert.assertEquals(args, CommandOptionUtil.getOptionArgs(CommandOptionUtil.getOptionPrefix(args), args));

        // Incorrect option prefix position
        args = "Alice -a";
        Assert.assertEquals(args, CommandOptionUtil.getOptionArgs(CommandOptionUtil.getOptionPrefix(args), args));
    }
}
