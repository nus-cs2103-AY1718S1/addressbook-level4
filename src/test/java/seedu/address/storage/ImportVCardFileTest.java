package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.ImportAnalysis;


public class ImportVCardFileTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ImportAnalysis importAnalysis = new ImportAnalysis();

    //@@author stan789
    @Test
    public void getPersonFromFile_withoutBegin_throwsIoException() throws IOException {
        ImportVCardFile importVCardFile = new ImportVCardFile(
                Paths.get("src/test/data/VCardFileTest/contacts_without_begin.vcf"), importAnalysis);
        thrown.expect(IOException.class);
        importVCardFile.getPersonFromFile();

    }

    //@@author stan789
    @Test
    public void getPersonFromFile_withoutEnd_throwsIoException() throws IOException {
        ImportVCardFile importVCardFile = new ImportVCardFile(
                Paths.get("src/test/data/VCardFileTest/contacts_without_end.vcf"), importAnalysis);
        thrown.expect(IOException.class);
        importVCardFile.getPersonFromFile();

    }

    //@@author stan789
    @Test
    public void getPersonFromFile_someWithoutEnd_throwsIoException() throws IOException {
        ImportVCardFile importVCardFile = new ImportVCardFile(
                Paths.get("src/test/data/VCardFileTest/contacts_some_without_end.vcf"), importAnalysis);
        thrown.expect(IOException.class);
        importVCardFile.getPersonFromFile();

    }

    //@@author stan789
    @Test
    public void getPersonFromFile_someWithoutBegin_throwsIoException() throws IOException {
        ImportVCardFile importVCardFile = new ImportVCardFile(
                Paths.get("src/test/data/VCardFileTest/contacts_some_without_begin.vcf"), importAnalysis);
        thrown.expect(IOException.class);
        importVCardFile.getPersonFromFile();

    }

}
