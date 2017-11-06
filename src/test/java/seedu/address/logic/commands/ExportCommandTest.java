package seedu.address.logic.commands;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.LogicManager;
import seedu.address.model.AddressBook;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.PersonBuilder;


//@@author limshunyong
public class ExportCommandTest {

    private LogicManager logic;
    private ModelManager model;

    @Before
    public void setup() throws Exception {
        AddressBook ab = new AddressBook();
        model = new ModelManager(ab, new UserPrefs());
        logic = new LogicManager(model);

    }

    @Test
    public void execute_export_emptyList() throws Exception {

        CommandResult r = logic.execute("export");
        assertEquals(ExportCommand.MESSAGE_EMPTY_AB, r.feedbackToUser);

    }

    @Test
    public void execute_export_fail() throws Exception {
        PersonBuilder testPerson = new PersonBuilder();

        model.addPerson(testPerson.build());

        // to throw IOException
        File f = new File("output.vcf");
        Boolean cannotWrite = f.setWritable(false);

        CommandResult r = logic.execute("export");
        assertEquals(ExportCommand.MESSAGE_FAIL, r.feedbackToUser);

    }

    @Test
    public void execute_export_success() throws Exception {
        PersonBuilder testPerson = new PersonBuilder();

        model.addPerson(testPerson.build());

        // reset file permission from execute_export_fail()
        File f = new File("output.vcf");
        Boolean canWrite = f.setWritable(true);

        CommandResult r = logic.execute("export");
        assertEquals(ExportCommand.MESSAGE_SUCCESS, r.feedbackToUser);


        Path f1 = Paths.get("output.vcf");
        Path f2 = Paths.get("testExport.vcf");

        byte[] file1 = Files.readAllBytes(f1);
        byte[] file2 = Files.readAllBytes(f2);

        assertArrayEquals(file1, file2);

    }

}
