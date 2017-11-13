//@@author HanYaodong
package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A class to access Person data stored as an xml file on a hard disk.
 */
public class XmlPersonListStorage implements PersonListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlPersonListStorage.class);

    private String filePath;

    public XmlPersonListStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getPersonListFilePath() {
        return this.filePath;
    }

    @Override
    public Optional<UniquePersonList> readPersonList()
        throws DataConversionException, FileNotFoundException,  DuplicatePersonException {
        return readPersonList(this.filePath);
    }

    @Override
    public Optional<UniquePersonList> readPersonList(String filePath)
        throws DataConversionException, FileNotFoundException, DuplicatePersonException {
        requireNonNull(filePath);

        File file = new File(filePath);
        if (!file.exists()) {
            logger.info("PersonList file " + filePath + " not found");
            throw new FileNotFoundException();
        }
        UniquePersonList persons;
        try {
            persons = XmlFileStorage.loadPersonListFromSaveFile(new File(filePath)).getPersons();
        } catch (DuplicatePersonException dpe) {
            logger.info("PersonList file " + filePath + " contains duplicated persons");
            throw dpe;
        }
        return Optional.of(persons);
    }

    @Override
    public void savePersonList(List<ReadOnlyPerson> persons) throws IOException {
        savePersonList(persons, this.filePath);
    }

    /**
     * Similar to {@link #savePersonList(List)}
     * @param filePath location of the data. Cannot be null
     */
    public void savePersonList(List<ReadOnlyPerson> persons, String filePath) throws IOException {
        requireNonNull(persons);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializablePersonList(persons));
    }

    /**
     * Similar to {@link #savePersonList(List)}
     * @param persons represents the {@link UniquePersonList} to be saved
     */
    public void savePersonList(UniquePersonList persons) throws IOException {
        savePersonList(persons, this.filePath);
    }

    /**
     * Similar to {@link #savePersonList(UniquePersonList)}
     * @param filePath location of the data. Cannot be null
     */
    public void savePersonList(UniquePersonList persons, String filePath) throws IOException {
        savePersonList(persons.asObservableList(), filePath);
    }
}
