package seedu.address.logic.commands.configs;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

//@@author yunpengn
public class ConfigCommandTest {
    @Test
    public void configTypes_checkCount() {
        assertEquals(ConfigCommand.ConfigType.values().length, ConfigCommand.TO_ENUM_CONFIG_TYPE.size());
    }
}
