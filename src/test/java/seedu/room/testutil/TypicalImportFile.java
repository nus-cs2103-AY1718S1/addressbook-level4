package seedu.room.testutil;

import static seedu.room.testutil.TypicalPersons.ALICE;
import static seedu.room.testutil.TypicalPersons.BENSON;
import static seedu.room.testutil.TypicalPersons.CARL;
import static seedu.room.testutil.TypicalPersons.DANIEL;
import static seedu.room.testutil.TypicalPersons.ELLE;
import static seedu.room.testutil.TypicalPersons.FIONA;
import static seedu.room.testutil.TypicalPersons.GEORGE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.room.logic.commands.ImportCommand;
import seedu.room.model.ResidentBook;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.person.exceptions.DuplicatePersonException;

//@@author blackroxs

/**
 * A utility class containing a list of {@code Person} objects to be used in tests for import.
 */
public class TypicalImportFile {
    public static final ReadOnlyPerson AMY = new PersonBuilder().withName("Amy Parker")
            .withRoom("08-115").withEmail("amy_parker123@example.com")
            .withPhone("84455255")
            .withTags("level8").build();
    public static final ReadOnlyPerson BERNARD = new PersonBuilder().withName("Bernard Maddison")
            .withRoom("02-203")
            .withEmail("bmad@example.com").withPhone("98885432")
            .withTags("owesMoney", "professor").build();
    public static final ReadOnlyPerson CARLO = new PersonBuilder().withName("Carlo Henn").withPhone("95222563")
            .withEmail("carlo.h@example.com").withRoom("04-300C").build();
    public static final ReadOnlyPerson DANIELLE = new PersonBuilder().withName("Danielle Chua").withPhone("82252533")
            .withEmail("chua_jj_danielle@example.com").withRoom("06-120").build();

    public static final String TYPICAL_IMPORT_SUCCESS_MESSAGE = ImportCommand.MESSAGE_SUCCESS
            + " Added: Amy Parker, Bernard Maddison, Carlo Henn, Danielle Chua";

    private TypicalImportFile() {
    } // prevents instantiation

    /**
     * @return an {@code ResidentBook} with typical person not found in original list.
     */
    public static ResidentBook getTypicalImportFile() {
        ResidentBook importFile = new ResidentBook();
        for (ReadOnlyPerson person : getTypicalPersons()) {
            try {
                importFile.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return importFile;
    }

    public static ResidentBook getCombinedResult() {
        ResidentBook importFile = new ResidentBook();
        for (ReadOnlyPerson person : getCombinedList()) {
            try {
                importFile.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return importFile;
    }

    public static List<ReadOnlyPerson> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(AMY, BERNARD, CARLO, DANIELLE));
    }

    public static List<ReadOnlyPerson> getCombinedList() {
        return new ArrayList<>(Arrays.asList(AMY, BERNARD, CARLO, DANIELLE, ALICE, BENSON,
                CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

}
