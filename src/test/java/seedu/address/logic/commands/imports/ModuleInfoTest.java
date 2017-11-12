package seedu.address.logic.commands.imports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.property.DateTime;

//@@author yunpengn
public class ModuleInfoTest {
    private static ModuleInfo info;
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/ConfigUtilTest/");
    private static final String SAMPLE_MODULE_JSON = "SampleModuleInfoJson.json";

    @BeforeClass
    public static void setUp() throws Exception {
        info = getSampleModule();
        assertNotNull(info);
    }

    @Test
    public void createModuleInfo_fromJsonUrl_checkCorrectness() throws Exception {
        assertEquals("CS1101S", info.getModuleCode());

        Date expectedDate = DateTime.parseDateTime("29112017 17:00");
        assertEquals(expectedDate, info.getExamDate());
    }

    @Test
    public void equals_returnTrue_checkCorrectness() throws Exception {
        ModuleInfo another = getSampleModule();
        assertEquals(info, info);
        assertEquals(info, another);
        assertNotEquals(info, "");
    }

    @Test
    public void toString_checkCorrectness() throws Exception {
        String expected = "Module Code: CS1101S\n"
                + "Module Title: Programming Methodology\n"
                + "Module Credit: 5\n"
                + "Examination Date: Wed Nov 29 17:00:00 SGT 2017";
        assertEquals(expected, info.toString());
    }

    @Test
    public void hashCode_checkCorrectness() {
        assertEquals("CS1101S".hashCode(), info.hashCode());
    }

    private static ModuleInfo getSampleModule() throws Exception {
        // Although this method returns an Optional, we do not want to make use of such wrapper here.
        return JsonUtil.readJsonFile(TEST_DATA_FOLDER + SAMPLE_MODULE_JSON, ModuleInfo.class).orElse(null);
    }
}
