package seedu.address.logic.commands.imports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_INFO_JSON_URL;

import java.net.URL;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import seedu.address.commons.util.JsonUtil;
import seedu.address.model.property.DateTime;

public class ModuleInfoTest {
    private static ModuleInfo info;

    @BeforeClass
    public static void setUp() throws Exception {
        info = JsonUtil.fromJsonUrl(new URL(VALID_MODULE_INFO_JSON_URL), ModuleInfo.class);
        assertNotNull(info);
    }

    @Test
    public void createModuleInfo_fromJsonUrl_checkCorrectness() throws Exception {
        assertEquals("CS1101S", info.getModuleCode());

        Date expectedDate = DateTime.formatDateTime("29112017 17:00");
        // assertEquals(expectedDate, info.getExamDate());

        String expected = "Module Code: CS1101S\n"
                + "Module Title: Programming Methodology\n"
                + "Module Credit: 5\n"
                + "Examination Date: Wed Nov 29 17:00:00 SGT 2017";
        assertEquals(expected, info.toString());
    }

    @Test
    public void equals_returnTrue_checkCorrectness() throws Exception {
        ModuleInfo another = JsonUtil.fromJsonUrl(new URL(VALID_MODULE_INFO_JSON_URL), ModuleInfo.class);
        assertEquals(info, info);
        assertEquals(info, another);
    }

    @Test
    public void toString_checkCorrectness() throws Exception {
        String expected = "Module Code: CS1101S\n"
                + "Module Title: Programming Methodology\n"
                + "Module Credit: 5\n"
                + "Examination Date: Wed Nov 29 17:00:00 SGT 2017";
        assertEquals(expected, info.toString());
    }
}
