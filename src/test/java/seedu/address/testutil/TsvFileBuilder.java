package seedu.address.testutil;

import java.io.IOException;
import java.util.ArrayList;

import seedu.address.logic.ContactTsvReader;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class to help with building TsvFile objects.
 */
public class TsvFileBuilder {
    public static final String DEFAULT_TSV_FILE_PATH = TypicalTsvFiles.PERFECT_TSV_FILE_PATH;

    private boolean isFileFound;
    private ArrayList<ReadOnlyPerson> toAddPeople;
    private ArrayList<Integer> failedEntries;

    public TsvFileBuilder() {
        ContactTsvReader contactTsvReader = new ContactTsvReader(DEFAULT_TSV_FILE_PATH);
        toAddPeople = new ArrayList<>();
        failedEntries = contactTsvReader.getFailedEntries();

        try {
            contactTsvReader.readContactFromFile();
            toAddPeople = contactTsvReader.getToAddPeople();
            failedEntries = contactTsvReader.getFailedEntries();
            isFileFound = true;
        } catch (IOException e) {
            isFileFound = false;
        } catch (ParseException pe) {
            throw new AssertionError("Default tsv file is invalid.");
        }
    }

    public boolean getIsFileFound() {
        return isFileFound;
    }

    public ArrayList<ReadOnlyPerson> getToAddPeople() {
        return toAddPeople;
    }

    public ArrayList<Integer> getFailedEntries() {
        return failedEntries;
    }
}
